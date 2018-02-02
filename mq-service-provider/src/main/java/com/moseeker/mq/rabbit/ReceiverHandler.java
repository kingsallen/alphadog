package com.moseeker.mq.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.common.annotation.iface.CounterInfo;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.log.ELKLog;
import com.moseeker.common.log.LogVO;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.MessageTemplateEntity;
import com.moseeker.entity.PersonaRecomEntity;
import com.moseeker.entity.pojos.Data;
import com.moseeker.mq.service.impl.TemplateMsgProducer;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lucky8987 on 17/8/3.
 */
@Component
@PropertySource("classpath:common.properties")
public class ReceiverHandler {

    private static Logger log = LoggerFactory.getLogger(ReceiverHandler.class);

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private LogDeadLetterDao logDeadLetterDao;

    @Autowired
    private MessageTemplateEntity messageTemplateEntity;

    @Autowired
    private TemplateMsgProducer templateMsgProducer;

    @Autowired
    private Environment env;

    @Autowired
    private PersonaRecomEntity personaRecomEntity;


    @RabbitListener(queues = "#{addAwardQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void addAwardHandler(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            employeeEntity.addAwardBefore(jsonObject.getIntValue("employeeId"), jsonObject.getIntValue("companyId"),
                    jsonObject.getIntValue("positionId"), jsonObject.getIntValue("templateId"), jsonObject.getIntValue("berecomUserId"), jsonObject.getIntValue("applicationId"));
        } catch (Exception e) {
            // 错误日志记录到数据库 的 log_dead_letter 表中
            LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
            logDeadLetterDO.setAppid(Integer.valueOf(message.getMessageProperties().getAppId()));
            logDeadLetterDO.setErrorLog(e.getMessage());
            logDeadLetterDO.setMsg(msgBody);
            logDeadLetterDO.setExchangeName(message.getMessageProperties().getReceivedExchange());
            logDeadLetterDO.setRoutingKey(message.getMessageProperties().getReceivedRoutingKey());
            logDeadLetterDO.setQueueName(message.getMessageProperties().getConsumerQueue());
            logDeadLetterDao.addData(logDeadLetterDO);
            log.error(e.getMessage(), e);
        }
    }
    /*
      智能画像数据推送的微信模板
     */
    @RabbitListener(queues = "#{sendTemplateQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerMessageTemplate(Message message, Channel channel){
        String msgBody = "{}";
        long startTime=new Date().getTime();
        LogVO logVo=this.handlerLogVO();
        try{
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("rabitmq的参数是========"+jsonObject.toJSONString());
            int userId=jsonObject.getIntValue("user_id");
            int companyId=jsonObject.getIntValue("company_id");
            int type=jsonObject.getIntValue("type");
            int templateId;
            logVo.setReq_params(jsonObject.toJSONString());
            logVo.setAppid(4);
            logVo.setUser_id(userId);
            if(type!=0){
                switch (type) {
                    case 1: templateId = Constant.FANS_PROFILE_COMPLETION;logVo.setEvent("FANS_PROFILE_COMPLETION"); break;
                    case 2: templateId = Constant.FANS_RECOM_POSITION;logVo.setEvent("FANS_RECOM_POSITION"); break;
                    case 3: templateId = Constant.EMPLOYEE_RECOM_POSITION;logVo.setEvent("EMPLOYEE_RECOM_POSITION"); break;
                    case 4: templateId = Constant.EMPLOYEE_PROFILE_COMPLETION;logVo.setEvent("EMPLOYEE_PROFILE_COMPLETION"); break;
                    default: templateId = 0;
                }
                String url=jsonObject.getString("url");
                if(StringUtils.isEmpty(url)){
                    url=handlerUrl(type);
                }
                String enable_qx_retry=jsonObject.getString("enable_qx_retry");
                MessageTemplateNoticeStruct messageTemplate=messageTemplateEntity.handlerTemplate(userId,companyId,templateId,type,url);
                log.info("messageTemplate========"+JSONObject.toJSONString(messageTemplate));
                if(messageTemplate!=null){
                    if(StringUtils.isNotEmpty(enable_qx_retry)){
                        messageTemplate.setEnable_qx_retry(Byte.parseByte(enable_qx_retry));
                    }
                    templateMsgProducer.messageTemplateNotice(messageTemplate);
                    if(type==2){
                        personaRecomEntity.updateIsSendPersonaRecom(userId,companyId,0,1,20);
                    }
                    if(type==3){
                        personaRecomEntity.updateIsSendPersonaRecom(userId,companyId,1,1,20);
                    }
                    logVo.setStatus_code(0);
                }else{
                    this.handleTemplateLogDeadLetter(message,msgBody,"没有查到模板所需的具体内容");
                    logVo.setStatus_code(1);

                }
            }else{
                logVo.setStatus_code(2);
            }
        }catch(Exception e){
            this.handleTemplateLogDeadLetter(message,msgBody,"没有查到模板所需的具体内容");
            log.error(e.getMessage(), e);
            logVo.setStatus_code(1);
        }finally{
            long endTime=new Date().getTime();
            logVo.setOpt_time(endTime-startTime);
            ELKLog.ELK_LOG.log(logVo);
        }
    }
    /*
      数据短传来数据，本初做处理，
      1，把该user_id原有的职位迁移到history当中
      2，插入新的数据
     */
    @RabbitListener(queues = "#{personaRecomQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handlerPersonRecom(Message message, Channel channel){
        String msgBody = "{}";
        try{
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("推送职位的rabitmq的参数是========"+jsonObject.toJSONString());
            int userId=jsonObject.getIntValue("user_id");
            String positionIds=jsonObject.getString("position_ids");
            int companyId=jsonObject.getIntValue("company_id");
            int type=jsonObject.getIntValue("type");
            if(userId!=0&&StringUtils.isNotEmpty(positionIds)){
                int result=personaRecomEntity.handlePersonaRecomData(userId,positionIds,companyId,type);
            }

        }catch(Exception e){
            this.handleTemplateLogDeadLetter(message,msgBody,"插入推荐职位数据失败");
            log.error(e.getMessage(), e);
        }
    }
    /*
      处理异常消息的队列
     */
    private void handleTemplateLogDeadLetter(Message message,String msgBody,String errorMessage){
        LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
        logDeadLetterDO.setAppid(NumberUtils.toInt(message.getMessageProperties().getAppId(), 0));
        logDeadLetterDO.setErrorLog(errorMessage);
        logDeadLetterDO.setMsg(msgBody);
        logDeadLetterDO.setExchangeName(StringUtils.defaultIfBlank(message.getMessageProperties().getReceivedExchange(), ""));
        logDeadLetterDO.setRoutingKey(StringUtils.defaultIfBlank(message.getMessageProperties().getReceivedRoutingKey(), ""));
        logDeadLetterDO.setQueueName(StringUtils.defaultIfBlank(message.getMessageProperties().getConsumerQueue(), ""));
        logDeadLetterDao.addData(logDeadLetterDO);
    }
    /*
      处理url
      */
    private String handlerUrl(int type){
        String url="";
        if(type==1){
            url=env.getProperty("message.template.fans.url");
        }else if(type==2){
            url=env.getProperty("message.template.recom.url");
        }else if(type==4){
            url=env.getProperty("message.template.new.employee.url");
        }else if(type==3){
            url=env.getProperty("message.template.recom.employee.url");
        }else if(type==5){
            url=env.getProperty("message.template.delivery.applier.url");
        }
        return url;

    }


    private LogVO handlerLogVO(){
        LogVO log=new LogVO();
        log.setAppid(4);
        log.setReq_uri(this.getClass().getName()+"_handlerMessageTemplate");
        log.setReq_time(new Date());
        log.setRefer("wechat_template_message");
        return log;
    }

}
