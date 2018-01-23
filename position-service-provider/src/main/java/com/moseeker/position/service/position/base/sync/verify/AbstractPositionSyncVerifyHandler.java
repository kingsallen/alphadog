package com.moseeker.position.service.position.base.sync.verify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public abstract class AbstractPositionSyncVerifyHandler implements PositionSyncVerifyHandler<String,String> {
    Logger logger= LoggerFactory.getLogger(AbstractPositionSyncVerifyHandler.class);

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    @Resource(name = "cacheClient")
    protected RedisClient redisClient;

    protected abstract JSONObject checkVerifyParam(JSONObject jsonParam) throws BIZException;

    protected abstract void syncVerifyInfo(JSONObject jsonParam) throws BIZException;

    protected abstract void verifyHandler(JSONObject jsonParam) throws BIZException;

    /**
     * 接收验证完成的消息后做一些共通验证，然后调用渠道自己对验证完成消息的处理
     * @param info 验证消息
     * @throws BIZException
     */
    @Override
    public void syncVerifyInfo(String info) throws BIZException {
        JSONObject jsonInfo=checkVerifyParam(info);

        //调用渠道自己对验证的处理
        syncVerifyInfo(jsonInfo);
    }

    /**
     * 处理爬虫端推送的需要验证的请求
     * @param param
     * @throws BIZException
     */
    @Override
    public void verifyHandler(String param) throws BIZException {


        try {
            JSONObject jsonParam=checkVerifyParam(param);

            int accountId=jsonParam.getIntValue("accountId");
            int channel=jsonParam.getIntValue("channel");
            int positionId=jsonParam.getIntValue("positionId");

            if(alreadyInRedis(positionId,channel,accountId)){
                logger.error("该同步职位已经在验证了：{}",param);
                throw new RuntimeException("该同步职位已经在验证了");
            }
            verifyHandler(jsonParam);
        }catch (Exception e){

        }

    }

    /**
     * 验证爬虫端发送的验证信息
     * @param param
     * @return
     * @throws BIZException
     */
    protected JSONObject checkVerifyParam(String param) throws BIZException {
        if(StringUtils.isNullOrEmpty(param)){
            logger.error("验证处理--参数为空：{}",param);
            throw new RuntimeException("验证处理--参数为空");
        }
        JSONObject jsonParam=JSON.parseObject(param);

        Integer accountId=jsonParam.getInteger("accountId");
        Integer channel=jsonParam.getInteger("channel");
        Integer positionId=jsonParam.getInteger("positionId");

        if(accountId==null || channel==null || positionId==null){
            logger.error("验证处理--参数为空：{}",param);
            throw new RuntimeException("验证处理--参数为空");
        }

        ChannelType channelType=ChannelType.instaceFromInteger(channel);
        if(channelType==null){
            logger.error("验证处理--渠道错误：{}",param);
            throw new RuntimeException("验证处理--渠道错误");
        }

        JobPositionRecord jobPosition=positionDao.getPositionById(positionId);
        if(jobPosition == null){
            logger.error("验证处理--职位不存在：{}",param);
            throw new RuntimeException("验证处理--职位不存在");
        }

        Query query=new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),positionId)
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.getName(),accountId)
                .and(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(),(short)0, ValueOp.NEQ))
                .buildQuery();
        HrThirdPartyPositionDO thirdPartyPosition = thirdPartyPositionDao.getSimpleData(query);

        if(thirdPartyPosition==null){
            logger.error("验证处理--第三方职位不存在：{}",param);
            throw new RuntimeException("验证处理--第三方职位不存在");
        }

        //调用渠道自己对信息的验证，例如智联是手机号，需要手机验证码
        return checkVerifyParam(jsonParam);
    }


    /**
     * 是否已经在验证，没有则添加验证到redis
     * @param positionId    职位ID
     * @param channel   渠道号
     * @param accountId     第三方账号ID
     * @return
     * @throws BIZException
     */
    protected boolean alreadyInRedis(int positionId,int channel,int accountId) throws BIZException {
        String timeoutKey=redisTimeoutKey(positionId,channel,accountId);
        long check= redisClient.incrIfNotExist(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(), timeoutKey);
        if (check>1) {
            //同步中
            return true;
        }
        redisClient.expire(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(), timeoutKey , timeoutSeconds());
        return false;
    }

    /**
     * 获取超时时间，方便子类覆盖
     * @return
     */
    protected int timeoutSeconds(){
        return 60;
    }

    /**
     * 生成redis中超时Key
     * @param positionId    职位ID
     * @param channel   渠道号
     * @param accountId     第三方账号ID
     * @return
     */
    protected String redisTimeoutKey(int positionId,int channel,int accountId){
        StringBuilder keyBuilder=new StringBuilder();
        keyBuilder.append(positionId).append("_")
                .append(channel).append("_")
                .append(accountId);
        return keyBuilder.toString();
    }
}
