package com.moseeker.mq.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.log.ELKLog;
import com.moseeker.common.log.LogVO;
import com.moseeker.common.log.ReqParams;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.MessageTemplateEntity;
import com.moseeker.entity.PersonaRecomEntity;
import com.moseeker.entity.pojo.mq.AIRecomParams;
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

import java.io.UnsupportedEncodingException;
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
            log.info("addAwardHandler jsonObject:{}", jsonObject.toJSONString());
            employeeEntity.addAwardBefore(jsonObject.getIntValue("employeeId"),
                    jsonObject.getIntValue("companyId"), jsonObject.getIntValue("positionId"),
                    jsonObject.getIntValue("templateId"), jsonObject.getIntValue("berecomUserId"),
                    jsonObject.getIntValue("applicationId"));
        } catch (Exception e) {
            log.info("addAwardHandler exception e.message:{}", e.getMessage());
            // 错误日志记录到数据库 的 log_dead_letter 表中
            LogDeadLetterDO logDeadLetterDO = new LogDeadLetterDO();
            logDeadLetterDO.setAppid(Integer.valueOf(message.getMessageProperties().getAppId()));
            logDeadLetterDO.setErrorLog(e.getMessage());
            logDeadLetterDO.setMsg(msgBody);
            logDeadLetterDO.setExchangeName(message.getMessageProperties().getReceivedExchange());
            logDeadLetterDO.setRoutingKey(message.getMessageProperties().getReceivedRoutingKey());
            logDeadLetterDO.setQueueName(message.getMessageProperties().getConsumerQueue());
            logDeadLetterDao.addData(logDeadLetterDO);
            if(e.getMessage().contains("重复的加积分操作")){
                log.warn(e.getMessage(), e);
            }else{
                log.error(e.getMessage(), e);
            }

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
            int type=jsonObject.getIntValue("type");
            log.info("type========================:{}",type);
            this.addPropertyLogVO(logVo,jsonObject);
            this.handlerTempLateLog(logVo,type);
            if(type!=0){
                AIRecomParams params=this.initRecomParams(message);
                MessageTemplateNoticeStruct messageTemplate=messageTemplateEntity.handlerTemplate(params);
                log.info("messageTemplate========"+JSONObject.toJSONString(messageTemplate));
                if(messageTemplate!=null){
                    templateMsgProducer.messageTemplateNotice(messageTemplate);
                    this.handlerPosition(params);
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
    private void handlerPosition(AIRecomParams params){
        if(params.getType()==2){
            personaRecomEntity.updateIsSendPersonaRecom(params.getUserId(),params.getCompanyId(),0,1,20);
        }
        if(params.getType()==3){
            personaRecomEntity.updateIsSendPersonaRecom(params.getUserId(),params.getCompanyId(),1,1,20);
        }
    }
    /*
     初始化参数
     */
    private AIRecomParams initRecomParams(Message message) throws Exception {
        String msgBody = new String(message.getBody(), "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(msgBody);
        int userId=jsonObject.getIntValue("user_id");
        int wxId=jsonObject.getIntValue("wx_id");
        int companyId=jsonObject.getIntValue("company_id");
        int type=jsonObject.getIntValue("type");
        String positionIds=jsonObject.getString("position_ids");
        int templateId=this.getTemplateId(type);
        String enableQxRetry=jsonObject.getString("enable_qx_retry");
        String url=jsonObject.getString("url");
        if(StringUtils.isEmpty(url)){
            url=handlerUrl(type);
        }
        String algorithmName=jsonObject.getString("algorithm_name");
        AIRecomParams recomParams=new AIRecomParams(userId,wxId,companyId,type,positionIds,enableQxRetry,url,templateId,algorithmName);
        return recomParams;
    }

    /*
     获取发布模板
     */
    private int getTemplateId(int type){
        int templateId=0;
        switch (type) {
            case 1: templateId = Constant.FANS_PROFILE_COMPLETION; break;
            case 2: templateId = Constant.FANS_RECOM_POSITION; break;
            case 3: templateId = Constant.EMPLOYEE_RECOM_POSITION;; break;
            case 4: templateId = Constant.EMPLOYEE_PROFILE_COMPLETION; break;
            default: templateId = 0;
        }
        return templateId;
    }
    /*

     */
    private void handlerTempLateLog(LogVO logVO,int type){
        if(type==1){
            logVO.setEvent("FANS_PROFILE_COMPLETION");
        }else if(type==2){
            logVO.setEvent("FANS_RECOM_POSITION");
        }else if(type==3){
            logVO.setEvent("EMPLOYEE_RECOM_POSITION");
        }else if(type==4){
            logVO.setEvent("EMPLOYEE_PROFILE_COMPLETION");
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

    /*
      初始化log日志
     */
    private LogVO handlerLogVO(){
        LogVO log=new LogVO();
        log.setAppid(4);
        log.setReq_uri(this.getClass().getName()+"_handlerMessageTemplate");
        log.setReq_time(new Date());
        log.setRefer("wechat_template_message");
        return log;
    }

    private void addPropertyLogVO(LogVO logVo,JSONObject jsonObject){
        ReqParams params=new ReqParams();
        params.setCompany_id(jsonObject.getIntValue("company_id"));
        params.setUser_id(jsonObject.getIntValue("user_id"));
        params.setType(jsonObject.getIntValue("type"));
        params.setWx_id(jsonObject.getIntValue("wx_id"));
        logVo.setReq_params(jsonObject.toJSONString());
        logVo.setUser_id(jsonObject.getIntValue("user_id"));
        logVo.setRecom_params(params);
    }
}
