package com.moseeker.position.service.position.base.sync;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.*;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 职位同步验证工具类
 */
@Component
public class PositionSyncVerifyHandlerUtil {
    Logger logger= LoggerFactory.getLogger(PositionSyncVerifyHandlerUtil.class);

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


    public String getParam(String redisKey){
        return redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(),redisKey);
    }

    public void delParam(String redisKey){
        redisClient.del(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(),redisKey);
    }

    public void setParam(String redisKey,String value){
        redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(),redisKey,value);
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
                .and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.DISABLE.getName(), 0)
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
