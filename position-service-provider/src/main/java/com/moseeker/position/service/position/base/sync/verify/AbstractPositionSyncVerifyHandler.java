package com.moseeker.position.service.position.base.sync.verify;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.*;
import com.moseeker.common.providerutils.ExceptionUtils;
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

    @Autowired
    HRThirdPartyPositionDao thirdpartyPositionDao;

    protected abstract boolean checkVerifyParam(JSONObject jsonParam) throws BIZException;

    protected abstract void syncVerifyInfo(JSONObject jsonParam) throws BIZException;

    protected abstract void verifyHandler(JSONObject jsonParam) throws BIZException;

    /**
     * 处理爬虫端推送的需要验证的请求
     * @param param
     * @throws BIZException
     */
    @Override
    public void verifyHandler(String param) throws BIZException {
        try {
            checkVerifyParam(param);

            JsonVerifyParam verifyParam=JsonVerifyParam.newInstance(param);

            if(alreadyInRedis(verifyParam)){
                logger.error("该同步职位已经在验证了：{}",param);
                throw new RuntimeException("该同步职位已经在验证了");
            }
            verifyHandler(verifyParam);
        }catch (Exception e){
            timeoutHandler(param);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 接收验证完成的消息后做一些共通验证，然后调用渠道自己对验证完成消息的处理
     * @param info 验证消息
     * @throws BIZException
     */
    @Override
    public void syncVerifyInfo(String info) throws BIZException {
        try {
            checkVerifyParam(info);

            JsonVerifyParam jsonInfo=JsonVerifyParam.newInstance(info);

            //调用渠道自己对验证的处理
            syncVerifyInfo(jsonInfo);
        }catch (Exception e){
            timeoutHandler(info);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

    }

    /**
     * 判断是否超时
     * @param param
     * @return
     */
    @Override
    public boolean isTimeout(String param){
        JsonVerifyParam verifyParam=JsonVerifyParam.newInstance(param);
        String timeoutKey=redisTimeoutKey(verifyParam);
        boolean exist=redisClient.exists(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.POSITION_SYNC_VERIFY_TIMEOUT.toString(), timeoutKey);
        return !exist;
    }

    /**
     * 超时修改第三方职位，让用户再次同步
     * @param param
     * @throws BIZException
     */
    @Override
    public void timeoutHandler(String param) throws BIZException {
        if(StringUtils.isNullOrEmpty(param)){
            return;
        }

        JsonVerifyParam verifyParam=JsonVerifyParam.newInstance(param);

        if(!verifyParam.isValid()){
            return;
        }

        Query query=new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),verifyParam.getPositionId())
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.getName(),verifyParam.getAccountId())
                .and(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(),(short)0, ValueOp.NEQ))
                .buildQuery();
        HrThirdPartyPositionDO thirdPartyPosition = thirdPartyPositionDao.getSimpleData(query);

        if(thirdPartyPosition==null){
            return;
        }

        HrThirdPartyPositionDO data = new HrThirdPartyPositionDO();

        data.setId(thirdPartyPosition.getId());

        data.setIsSynchronization((byte) PositionSync.failed.getValue());

        data.setSyncFailReason("手机验证码超时");

        try {
            thirdpartyPositionDao.updateData(data,EmptyExtThirdPartyPosition.EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("读取职位同步队列后无法更新到数据库:{}", JSON.toJSONString(data));
            throw e;
        }
    }

    /**
     * 验证爬虫端发送的验证信息
     * @param param
     * @return
     * @throws BIZException
     */
    protected boolean checkVerifyParam(String param) throws BIZException {
        if(StringUtils.isNullOrEmpty(param)){
            logger.error("验证处理--参数为空：{}",param);
            throw new RuntimeException("验证处理--参数为空");
        }
        JsonVerifyParam verifyParam=JsonVerifyParam.newInstance(param);

        if(!verifyParam.isValid()){
            logger.error("验证处理--参数为空：{}",param);
            throw new RuntimeException("验证处理--参数为空");
        }

        ChannelType channelType=ChannelType.instaceFromInteger(verifyParam.getChannel());
        if(channelType==null){
            logger.error("验证处理--渠道错误：{}",param);
            throw new RuntimeException("验证处理--渠道错误");
        }

        JobPositionRecord jobPosition=positionDao.getPositionById(verifyParam.getPositionId());
        if(jobPosition == null){
            logger.error("验证处理--职位不存在：{}",param);
            throw new RuntimeException("验证处理--职位不存在");
        }

        Query query=new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),verifyParam.getPositionId())
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.getName(),verifyParam.getAccountId())
                .and(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(),(short)0, ValueOp.NEQ))
                .buildQuery();
        HrThirdPartyPositionDO thirdPartyPosition = thirdPartyPositionDao.getSimpleData(query);

        if(thirdPartyPosition==null){
            logger.error("验证处理--第三方职位不存在：{}",param);
            throw new RuntimeException("验证处理--第三方职位不存在");
        }

        //调用渠道自己对信息的验证，例如智联是手机号，需要手机验证码
        return checkVerifyParam(verifyParam);
    }


    /**
     * 是否已经在验证，没有则添加验证到redis
     * @param verifyParam
     * @return
     * @throws BIZException
     */
    protected boolean alreadyInRedis(JsonVerifyParam verifyParam) throws BIZException {
        String timeoutKey=redisTimeoutKey(verifyParam);
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
     * @param verifyParam
     * @return
     */
    protected String redisTimeoutKey(JsonVerifyParam verifyParam){
        StringBuilder keyBuilder=new StringBuilder();
        keyBuilder.append(verifyParam.getPositionId()).append("_")
                .append(verifyParam.getChannel()).append("_")
                .append(verifyParam.getAccountId());
        return keyBuilder.toString();
    }

    private static class JsonVerifyParam extends JSONObject{

        public static JsonVerifyParam newInstance(String param){
            JsonVerifyParam verifyParam=new JsonVerifyParam();
            JSONObject jsonObject=JSON.parseObject(param);
            verifyParam.putAll(jsonObject);
            return verifyParam;
        }

        public boolean isValid(){
            return containAccountId() && containChannel() && containPositionId();
        }

        public boolean containAccountId(){
            return containsKey("accountId");
        }
        public boolean containChannel(){
            return containsKey("channel");
        }
        public boolean containPositionId(){
            return containsKey("positionId");
        }

        public int getAccountId(){
            return getIntValue("accountId");
        }

        public int getChannel(){
            return getIntValue("channel");
        }

        public int getPositionId(){
            return getIntValue("positionId");
        }
    }
}
