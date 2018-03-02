package com.moseeker.position.service.position.base.sync.verify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.*;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandlerUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
public class MobileVeifyHandler extends AbstractPositionSyncVerifyHandler{
    Logger logger= LoggerFactory.getLogger(MobileVeifyHandler.class);

    private static String PARAMID="paramId";

    @Autowired
    private PositionSyncVerifyHandlerUtil verifyHandlerUtil;
    @Autowired
    private UserWxUserDao userWxUserDao;
    @Autowired
    private HrWxTemplateMessageDao templateMessageDao;
    @Autowired
    private Environment env;
    @Autowired
    private LogWxMessageRecordDao wxMessageRecordDao;
    @Autowired
    private HrWxWechatDao wxWechatDao;
    @Resource(name = "cacheClient")
    protected RedisClient redisClient;
    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private AmqpTemplate amqpTemplate;


    /**
     * 发送需要手机验证码消息模板
     * @param jsonParam
     * @throws BIZException
     */
    @Override
    protected void receive(JSONObject jsonParam) throws BIZException {
        logger.info("verifyHandler jsonParam:{}",jsonParam.toJSONString());
        Integer accountId=jsonParam.getInteger("accountId");
        Integer channel=jsonParam.getInteger("channel");
        Integer positionId=jsonParam.getInteger("positionId");

        JobPositionRecord jobPosition=positionDao.getPositionById(positionId);

        Query query=new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),positionId)
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.getName(),accountId)
                .and(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(),(short)0, ValueOp.NEQ))
                .buildQuery();
        HrThirdPartyPositionDO thirdPartyPosition = thirdPartyPositionDao.getSimpleData(query);

        ChannelType channelType=ChannelType.instaceFromInteger(channel);

        UserHrAccountDO userHrAccountDO=userHrAccountDao.getValidAccount(jobPosition.getPublisher());

        //发送消息模板
        sendMobileCodeTemplate(channelType,userHrAccountDO,thirdPartyPosition,jsonParam);

        logger.info("verifyHandler success jsonParam:{}",jsonParam.toJSONString());
    }

    /**
     * 发送验证码
     * @param jsonInfo
     * @throws BIZException
     */
    @Override
    protected void handler(JSONObject jsonInfo) throws BIZException {
        logger.info("syncVerifyInfo jsonInfo:{}",jsonInfo.toJSONString());
        //统一信息都是info，但是智联需要code
        jsonInfo.put("code",jsonInfo.get("info"));

        Integer accountId=jsonInfo.getInteger("accountId");

        String rountingKey=PositionSyncVerify.MOBILE_VERIFY_RESPONSE_ROUTING_KEY.replace("{}",accountId.toString());
        amqpTemplate.send(
                PositionSyncVerify.MOBILE_VERIFY_EXCHANGE
                , rountingKey
                , MessageBuilder.withBody(jsonInfo.toJSONString().getBytes()).build());

        if(jsonInfo.containsKey("paramId")){
            verifyHandlerUtil.setParam(jsonInfo.getString(PARAMID),PositionSyncVerify.MOBILE_VERIFY_SUCCESS);
        }
    }

    /**
     * 验证手机号
     * @param jsonParam
     * @return
     */
    @Override
    protected boolean checkVerifyParam(JSONObject jsonParam) throws BIZException {
        logger.info("checkVerifyParam jsonInfo:{}",jsonParam.toJSONString());
        String mobile = jsonParam.getString("mobile");
        if(StringUtils.isNullOrEmpty(mobile)){
            logger.error("验证处理--手机号为空");
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_SYNC_EMPTY_MOBILE);
        }
        return true;
    }

    /**
     * 验证手机号
     * @param jsonInfo
     * @return
     */
    @Override
    protected boolean checkVerifyInfo(JSONObject jsonInfo) throws BIZException {
        logger.info("checkVerifyInfo jsonInfo:{}",jsonInfo.toJSONString());
        String code = jsonInfo.getString("info");
        if(StringUtils.isNullOrEmpty(code)){
            logger.error("验证处理--验证码为空");
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_SYNC_EMPTY_MOBILE_CODE);
        }
        return true;
    }

    @Override
    protected boolean isFinished(JSONObject jsonInfo) throws BIZException {
        if(jsonInfo.containsKey("paramId")) {
            String paramId = jsonInfo.getString(PARAMID);
            String val = verifyHandlerUtil.getParam(paramId);

            return PositionSyncVerify.MOBILE_VERIFY_SUCCESS.equals(val);
        }
        return false;
    }

    @Override
    protected void timeoutHandler(String info) throws BIZException {
        super.timeoutHandler(info);

        logger.info("timeoutHandler param:{}",info);

        JSONObject jsonObject= JSON.parseObject(info);
        if(jsonObject.containsKey("paramId")) {
            String paramId=jsonObject.getString(PARAMID);
            verifyHandlerUtil.delParam(paramId);
        }
    }

    public Response getVerifyParam(String param) throws BIZException {
        logger.info("get verify param : {}",param);
        if(StringUtils.isNullOrEmpty(param)){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        JSONObject jsonParamObj=JSON.parseObject(param);

        if(!jsonParamObj.containsKey("paramId")){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }

        String paramId=jsonParamObj.getString("paramId");

        String jsonParam=verifyHandlerUtil.getParam(paramId);

        if(PositionSyncVerify.MOBILE_VERIFY_SUCCESS.equals(jsonParam)){
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_SYNC_INFO_SENDED);
        }

        if(StringUtils.isNullOrEmpty(jsonParam)){
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_SYNC_VERIFY_TIMEOUT);
        }

        JSONObject jsonObject=JSON.parseObject(jsonParam);
        int channel=jsonObject.getIntValue("channel");

        ChannelType channelType=ChannelType.instaceFromInteger(channel);

        jsonObject.put("paramId",paramId);
        if(isTimeout(jsonObject.toJSONString())){
            timeoutHandler(jsonObject.toJSONString());
            return ResponseUtils.fail(ConstantErrorCodeMessage.POSITION_SYNC_VERIFY_TIMEOUT);
        }

        return ResponseUtils.success(jsonObject);
    }

    public void sendMobileCodeTemplate(ChannelType channelType, UserHrAccountDO userHrAccountDO , HrThirdPartyPositionDO thirdPartyPosition, Map<String,Object> param) throws BIZException {
        UserWxUserDO userWxUserDO=userWxUserDao.getWXUserById(userHrAccountDO.getWxuserId());

        HrWxWechatDO hrWxWechatDO=getMoseekerWxWechat();

        String openId=userWxUserDO.getOpenid();
        String url=deliveryUrl(hrWxWechatDO);

        //把参数缓存在redis中，key为uuid，微信模板的link带的是uuid，再调用/position/getSyncVerifyParam可以获取参数，隐藏参数
        String redisKey=setVerifyParam(param);
        String link=getCodeLink(redisKey);

        Query query=new Query.QueryBuilder()
                .where(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WECHAT_ID.getName(),hrWxWechatDO.getId())
                .and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.SYS_TEMPLATE_ID.getName(), Constant.POSITION_SYNC_VERIFY_INFO)
                .buildQuery();
        HrWxTemplateMessageDO template=templateMessageDao.getData(query);


        Map<String, Object> applierTemplate = new HashMap<>();
        Map<String,MessageTplDataCol> colMap =new HashMap<>();

        //消息头
        MessageTplDataCol first = new MessageTplDataCol();
        first.setColor("#173177");
        first.setValue("您好，您正在同步职位至"+channelType.getAlias()+"，需要您进行手机验证");
        colMap.put("first",first);

        //消息尾
        MessageTplDataCol remark = new MessageTplDataCol();
        remark.setColor("#173177");
        remark.setValue("请点击详情填写"+channelType.getAlias()+"发送的验证码，此验证码仅用于本次职位同步");
        colMap.put("remark",remark);

        //申请人
        MessageTplDataCol applier = new MessageTplDataCol();
        applier.setColor("#173177");
        applier.setValue(userHrAccountDO.getUsername());
        colMap.put("keyword1",applier);

        //申请时间
        MessageTplDataCol time=new MessageTplDataCol();
        time.setColor("#173177");
        time.setValue(thirdPartyPosition.getSyncTime());
        colMap.put("keyword2",time);;

        applierTemplate.put("data", colMap);
        applierTemplate.put("touser", openId);
        applierTemplate.put("template_id", template.getWxTemplateId());
        applierTemplate.put("url", link);
        applierTemplate.put("topcolor", template.getTopcolor());
        String result=null;
        try {
            logger.info("mobile verify send template url:{},template:{}",url,JSON.toJSONString(applierTemplate));
            result  = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            logger.info("mobile verify send template result:{}",result);
        }catch (ConnectException e){
            logger.error("发送手机验证码模板消息失败 param: {}",JSON.toJSONString(param));
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_SYNC_SEND_MOBILE_TEMPLATE_ERROR);
        }


        JSONObject params = JSON.parseObject(result);
        insertLogWxMessageRecord(hrWxWechatDO, template, openId, link, colMap ,params);

        if(params!= null && "0".equals(params.getString("errcode"))){
            return ;
        }
        throw new BIZException((int)params.get("errcode"), (String)params.get("errmsg"));
    }

    private HrWxWechatDO getMoseekerWxWechat(){
        String signature=env.getProperty("wechat.helper.signature");
        Query query=new Query.QueryBuilder()
                .where(HrWxWechat.HR_WX_WECHAT.SIGNATURE.getName(),signature)
                .buildQuery();

        return wxWechatDao.getData(query);
    }

    private String deliveryUrl(HrWxWechatDO hrWxWechatDO){
        String url=env.getProperty("message.template.delivery.url");
        url=url.replace("{}",hrWxWechatDO.getAccessToken());
        return url;

    }

    private String setVerifyParam(Map<String,Object> param) throws BIZException {
        if(param==null || param.isEmpty()){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        String redisKey= UUID.randomUUID().toString();

        redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(),redisKey,JSON.toJSONString(param));

        logger.info("set Verify Param redisKey:{},param:{}",redisKey,param);

        return redisKey;
    }


    private String getCodeLink(String redisKey){
        StringBuilder url=new StringBuilder(env.getProperty("sync.code.url"));

        url.append("?").append("paramId=").append(redisKey);

        return url.toString();
    }


    /**
     * 插入模板消息发送结果日志
     * @param hrWxWechatDO
     * @param template
     * @param openId
     * @param link
     * @param colMap
     * @param result
     * @return
     */
    public int insertLogWxMessageRecord(HrWxWechatDO hrWxWechatDO, HrWxTemplateMessageDO template,
                                        String openId, String link, Map<String, MessageTplDataCol> colMap, Map<String, Object> result){
        LogWxMessageRecordDO messageRecord = new LogWxMessageRecordDO();
        messageRecord.setTemplateId(template.getId());
        if(hrWxWechatDO != null) {
            messageRecord.setWechatId(hrWxWechatDO.getId());
        }
        if(result != null && result.get("msgid") != null) {
            messageRecord.setMsgid((long)result.get("msgid"));
        }else{
            messageRecord.setMsgid(0);
        }
        messageRecord.setOpenId(openId);
        messageRecord.setUrl(link);
        messageRecord.setTopcolor(template.getTopcolor());
        messageRecord.setJsondata(JSON.toJSONString(colMap));
        if(result != null && result.get("errcode") != null) {
            messageRecord.setErrcode((int) result.get("errcode"));
        }else{
            messageRecord.setErrcode(-3);
        }
        if(result != null && result.get("errmsg") != null) {
            messageRecord.setErrmsg((String)result.get("errmsg"));
        }else{
            messageRecord.setErrmsg("");
        }
        messageRecord.setSendtime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setUpdatetime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setSendtype(0);
        if(hrWxWechatDO != null) {
            messageRecord.setAccessToken(hrWxWechatDO.getAccessToken());
        }
        return wxMessageRecordDao.addData(messageRecord).getId();
    }


}
