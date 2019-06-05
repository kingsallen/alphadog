package com.moseeker.mq.rabbit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.dao.logdb.LogDeadLetterDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.log.ELKLog;
import com.moseeker.common.log.LogVO;
import com.moseeker.common.log.ReqParams;
import com.moseeker.entity.SensorSend;
import com.moseeker.entity.*;
import com.moseeker.entity.pojo.mq.AIRecomParams;
import com.moseeker.entity.pojo.readpacket.RedPacketData;
import com.moseeker.mq.service.impl.TemplateMsgHttp;
import com.moseeker.mq.service.impl.TemplateMsgProducer;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
import com.rabbitmq.client.Channel;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

/**
 * Created by lucky8987 on 17/8/3.
 */
@Component
@PropertySource("classpath:common.properties")
public class ReceiverHandler {

    private Random random = new Random();

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

    @Autowired
    private TemplateMsgHttp templateMsgHttp;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedPacketEntity redPacketEntity;

    @Resource(name = "cacheClient")
    RedisClient redisClient;

    private SerializeConfig config = new SerializeConfig();

    public ReceiverHandler() {
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @Autowired
    private SensorSend sensorSend;

    @RabbitListener(queues = "#{employeeFirstRegisterQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void employeeFirstRegister(Message message) {
        try {
            //todo 组装红包发放所需数据，向约定的exchange发送消息
            try {
                String msgBody = new String(message.getBody(), "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(msgBody);
                log.info("bonusNotice jsonObject:{}", jsonObject);
                RedPacketData redPacketData = redPacketEntity.fetchRadPacketDataByEmployeeId(jsonObject.getInteger("id"));
                amqpTemplate.send(Constant.EMPLOYEE_FIRST_REGISTER_ADD_REDPACKET_EXCHANGE,
                        Constant.EMPLOYEE_FIRST_REGISTER_ADD_REDPACKET_ROUTINGKEY,
                        MessageBuilder.withBody(JSON.toJSONString(redPacketData,
                                config,
                                WriteNullListAsEmpty,
                                WriteNullStringAsEmpty,
                                WriteNullNumberAsZero,
                                WriteNullBooleanAsFalse,
                                WriteMapNullValue,
                                WriteNullNumberAsZero).getBytes())
                        .build());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "#{bonusNoticeQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void bonusNotice(Message message) {
        try {
            String msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("bonusNotice jsonObject:{}", jsonObject);
            templateMsgHttp.noticeEmployeeReferralBonus(jsonObject.getInteger("applicationId"),
                    jsonObject.getLong("operationTime"), jsonObject.getInteger("nextStage"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "#{demonstrationEmployeeRegisterQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void demonstrationEmployeeRegister(Message message) {
        try {
            log.info("元夕飞花令 ReceiverHandler demonstrationEmployeeRegister");
            String msgBody = new String(message.getBody(), "UTF-8");
            String companyId = env.getProperty("demonstration.company_id");
            int delay = Integer.valueOf(env.getProperty("demonstration.employee.register"));
            String positions = env.getProperty("demonstration.positions");
            String[] positionArray = positions.split(",");
            int index = random.nextInt(positionArray.length);
            String url = env.getProperty("demonstration.employee_referral.url");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("元夕飞花令 ReceiverHandler demonstrationEmployeeRegister jsonObject:{}",jsonObject);
            if (StringUtils.isNotBlank(companyId) && Integer.valueOf(companyId).intValue() == jsonObject.getInteger("company_id")) {
                log.info("元夕飞花令 ReceiverHandler demonstrationEmployeeRegister 特定公司");
                JSONObject params = new JSONObject();
                params.put("ai_template_type", 0);
                params.put("algorithm_name","feihualing_recom");
                params.put("company_id", Integer.valueOf(companyId));
                params.put("position_ids", positionArray[index]);
                params.put("template_id", Constant.EMPLOYEE_RECOM_POSITION);
                params.put("type", "3");
                params.put("user_id", jsonObject.getIntValue("user_id"));
                params.put("url", url);
                log.info("元夕飞花令 ReceiverHandler demonstrationEmployeeRegister params:{}", params);
                redisClient.zadd(AppId.APPID_ALPHADOG.getValue(),
                        KeyIdentifier.MQ_MESSAGE_NOTICE_TEMPLATE_DEMONSTRATION_DELAY.toString(),
                        delay*1000+System.currentTimeMillis(), params.toJSONString());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "#{demonstrationFollowWechatQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void demonstrationFollowWechat(Message message) {
        try {
            log.info("元夕飞花令 ReceiverHandler demonstrationFollowWechat");
            String msgBody = new String(message.getBody(), "UTF-8");
            String companyId = env.getProperty("demonstration.company_id");
            int delay = Integer.valueOf(env.getProperty("demonstration.follow.wechat"));
            String positions = env.getProperty("demonstration.positions");
            String[] positionArray = positions.split(",");
            int index = random.nextInt(positionArray.length);
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("元夕飞花令 ReceiverHandler demonstrationFollowWechat jsonObject:{}", jsonObject);
            templateMsgHttp.demonstrationFollowWechat(jsonObject.getIntValue("user_id"), jsonObject.getString("wechat_id"),
                    companyId, positionArray[index], delay, redisClient, env);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "#{sendSeekReferralTemplateQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void  seekReferralReceive(Message message){
        String msgBody = "{}";

        try {
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            Integer userId = jsonObject.getIntValue("user_id");
            Integer positionId = jsonObject.getIntValue("position_id");
            Integer referralId = jsonObject.getIntValue("referral_id");
            log.info("seekReferralReceive routingkey:{}", message.getMessageProperties().getReceivedRoutingKey());
            log.info("seekReferralReceive jsonObject:{}", jsonObject);
            if(Constant.EMPLOYEE_SEEK_REFERRAL_TEMPLATE.equals(message.getMessageProperties().getReceivedRoutingKey())) {
                Integer postUserId = jsonObject.getIntValue("post_user_id");
                Date now = new Date();
                long sendTime=  now.getTime();
                Map<String, Object> properties = new HashMap<String, Object>();
                properties.put("sendTime",sendTime);
                properties.put("templateId",Constant.REFERRAL_SEEK_REFERRAL);
                String distinctId = String.valueOf(postUserId);
                sensorSend.send(distinctId,"sendTemplateMessage",properties);
                templateMsgHttp.seekReferralTemplate(positionId, userId, postUserId, referralId, sendTime);
            }else if(Constant.EMPLOYEE_REFERRAL_EVALUATE.equals(message.getMessageProperties().getReceivedRoutingKey())){
                Integer applicationId= jsonObject.getIntValue("application_id");
                Integer employeeId= jsonObject.getIntValue("employee_id");
                Date now = new Date();
                now.getTime();
                Map<String, Object> properties = new HashMap<String, Object>();

                Date nowTime= new Date();
                long  sendTime= nowTime.getTime();
                properties.put("sendTime",sendTime);
                properties.put("templateId",Constant.REFERRA_RECOMMEND_EVALUATE);
                log.info("神策-----》》sendtime"+sendTime);
                templateMsgHttp.referralEvaluateTemplate(positionId, userId, applicationId, referralId, employeeId,sendTime);
                String distinctId = String.valueOf(userId);
               // sensorSend.send(distinctId,"sendSeekReferralTemplateMessage",properties);
                sensorSend.send(distinctId,"sendTemplateMessage",properties);
            }
        } catch (CommonException e) {
            log.info(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "#{redpacketTemplateQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void  redpacketTemplateReceive(Message message){
        String msgBody = "{}";

        try {
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            log.info("redpacketTemplateReceive routingkey:{}", message.getMessageProperties().getReceivedRoutingKey());
            if(Constant.BALANCE_CHARGE_ROUTINGKEY.equals(message.getMessageProperties().getReceivedRoutingKey())) {
                Integer companyId = jsonObject.getIntValue("company_id");
                Integer amount = jsonObject.getIntValue("amount");
                templateMsgHttp.redpacketAmountTemplate(companyId, amount);
            }

        } catch (CommonException e) {
            log.info(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @RabbitListener(queues = "#{referralRadarTenMinuteQueue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void  sendTenMinuteTemplate(Message message){
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(msgBody);
            templateMsgHttp.sendTenMinuteTemplate(jsonObject);
        } catch (CommonException e) {
            log.info(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

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
            try {
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
                if(StringUtils.isNotBlank(e.getMessage()) && e.getMessage().contains("重复的加积分操作")){
                    log.warn(e.getMessage(), e);
                }else{
                    log.error(e.getMessage(), e);
                }
            } catch (Exception e1) {
                log.error(e1.getMessage(), e1);
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
        long startTime=System.currentTimeMillis();
        LogVO logVo=this.handlerLogVO();
        try{
            log.info("message:{}", JSON.toJSONString(message));

            msgBody = new String(message.getBody(), "UTF-8");
            log.info("元夕飞花令 handlerMessageTemplate msgBody:{}", JSON.toJSONString(msgBody));
            if(message.getMessageProperties().getReceivedRoutingKey().equals(Constant.POSITION_SYNC_FAIL_ROUTINGKEY)){
                log.info("元夕飞花令 handlerMessageTemplate 职位同步失败");
                JSONObject jsonObject = JSONObject.parseObject(msgBody);
                int positionId = jsonObject.getIntValue("positionId");
                String msg = jsonObject.getString("message");
                int channal = jsonObject.getIntValue("channal");
                log.info("positionTemplateJson:{}", JSON.toJSONString(jsonObject));
                templateMsgHttp.positionSyncFailTemplate(positionId, msg, channal);
            }else {
                JSONObject jsonObject = JSONObject.parseObject(msgBody);
                log.info("元夕飞花令 handlerMessageTemplate 推荐 msgBody:{}", JSON.toJSONString(msgBody));
                int type = jsonObject.getIntValue("type");
                log.info("type========================:{}", type);
                this.addPropertyLogVO(logVo, jsonObject);
                this.handlerTempLateLog(logVo, type);
                if (type != 0) {
                    log.info("元夕飞花令 handlerMessageTemplate type!=0");
                    AIRecomParams params = this.initRecomParams(message);
                    MessageTemplateNoticeStruct messageTemplate = messageTemplateEntity.handlerTemplate(params);
                    log.info("元夕飞花令 handlerMessageTemplate messageTemplate========" + JSONObject.toJSONString(messageTemplate));
                    if (messageTemplate != null) {
                        templateMsgProducer.messageTemplateNotice(messageTemplate);
                        this.handlerPosition(params);
                        logVo.setStatus_code(0);
                        if(type==2){
                            String distinctId = String.valueOf(params.getUserId());
                            String templateId=String.valueOf(params.getTemplateId());
                            Map<String, Object> properties = new HashMap<String, Object>();
                            properties.put("templateId", templateId);
                            sensorSend.send(distinctId,"sendTemplateMessage",properties);
                        }
                    } else {
                        log.info("元夕飞花令 handlerMessageTemplate messageTemplate == null");
                        this.handleTemplateLogDeadLetter(message, msgBody, "没有查到模板所需的具体内容");
                        logVo.setStatus_code(1);
                    }
                } else {
                    log.info("元夕飞花令 handlerMessageTemplate type=0");
                    logVo.setStatus_code(2);
                }
            }
        }catch(Exception e){
            this.handleTemplateLogDeadLetter(message,msgBody,"没有查到模板所需的具体内容");
            log.error(e.getMessage(), e);
            logVo.setStatus_code(1);
        }finally{
            long endTime = System.currentTimeMillis();
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
        int aiTemplateId = jsonObject.getIntValue("ai_template_type");
        String algorithmName=jsonObject.getString("algorithm_name");
        AIRecomParams recomParams=new AIRecomParams(userId,wxId,companyId,type,positionIds,enableQxRetry,url,templateId,algorithmName,aiTemplateId);
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
