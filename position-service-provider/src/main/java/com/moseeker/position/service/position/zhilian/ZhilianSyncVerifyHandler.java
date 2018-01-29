package com.moseeker.position.service.position.zhilian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.position.service.position.base.sync.verify.AbstractPositionSyncVerifyHandler;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandlerUtil;
import com.moseeker.position.service.position.base.sync.verify.MobileVeifyHandler;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ZhilianSyncVerifyHandler extends AbstractPositionSyncVerifyHandler{
    Logger logger= LoggerFactory.getLogger(ZhilianSyncVerifyHandler.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private MobileVeifyHandler mobileVeifyHandler;

    @Autowired
    private PositionSyncVerifyHandlerUtil verifyHandlerUtil;

    /**
     * 发送需要手机验证码消息模板
     * @param jsonParam
     * @throws BIZException
     */
    @Override
    public void verifyHandler(JSONObject jsonParam) throws BIZException {
        logger.info("zhilian verifyHandler jsonParam:{}",jsonParam.toJSONString());
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
        mobileVeifyHandler.verifyHandler(channelType,userHrAccountDO,thirdPartyPosition,jsonParam);

        logger.info("zhilian verifyHandler success jsonParam:{}",jsonParam.toJSONString());
    }

    @Override
    public void syncVerifyInfo(JSONObject jsonInfo) throws BIZException{
        logger.info("zhilian syncVerifyInfo jsonInfo:{}",jsonInfo.toJSONString());
        //同一信息都是info，但是智联需要code
        jsonInfo.put("code",jsonInfo.get("info"));

        Integer accountId=jsonInfo.getInteger("accountId");

        String rountingKey=PositionSyncVerify.MOBILE_VERIFY_RESPONSE_ROUTING_KEY.replace("{}",accountId.toString());
        amqpTemplate.send(
                PositionSyncVerify.MOBILE_VERIFY_EXCHANGE
                , rountingKey
                , MessageBuilder.withBody(jsonInfo.toJSONString().getBytes()).build());

        if(jsonInfo.containsKey("paramId")){
            verifyHandlerUtil.setParam(jsonInfo.getString("paramId"),PositionSyncVerify.MOBILE_VERIFY_SUCCESS);
        }

    }


    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }

    /**
     * 验证手机号
     * @param jsonParam
     * @return
     */
    @Override
    protected boolean checkVerifyParam(JSONObject jsonParam) throws BIZException {
        logger.info("zhilian checkVerifyParam jsonInfo:{}",jsonParam.toJSONString());
        String mobile = jsonParam.getString("mobile");
        if(StringUtils.isNullOrEmpty(mobile)){
            logger.error("智联验证处理--手机号为空");
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_SYNC_EMPTY_MOBILE);
        }
        return true;
    }

    @Override
    protected boolean checkVerifyInfo(JSONObject jsonInfo) throws BIZException {
        logger.info("zhilian checkVerifyInfo jsonInfo:{}",jsonInfo.toJSONString());
        String code = jsonInfo.getString("info");
        if(StringUtils.isNullOrEmpty(code)){
            logger.error("智联验证处理--验证码为空");
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.POSITION_SYNC_EMPTY_MOBILE_CODE);
        }
        return true;
    }

    @Override
    public void timeoutHandler(String param) throws BIZException {
        super.timeoutHandler(param);

        logger.info("zhilian timeoutHandler param:{}",param);

        JSONObject jsonObject= JSON.parseObject(param);
        if(jsonObject.containsKey("paramId")) {
            String paramId=jsonObject.getString("paramId");
            String val=verifyHandlerUtil.getParam(paramId);

            if(!PositionSyncVerify.MOBILE_VERIFY_SUCCESS.equals(val)){
                verifyHandlerUtil.delParam(paramId);
            }
        }
    }
}





















