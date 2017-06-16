package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.*;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.UrlUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 第三方渠道（比如51，智联）服务
 * <p>Company: MoSeeker</P>
 * <p>date: Nov 6, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @author wjf
 */
@Service
public class ChaosServiceImpl {

    Logger logger = LoggerFactory.getLogger(ChaosServiceImpl.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;


    @Autowired
    HRThirdPartyPositionDao thirdpartyPositionDao;


    /**
     * 获取Chaos访问路径
     *
     * @return
     * @throws Exception
     */
    private String getDomain() throws Exception {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        configUtils.loadResource("chaos.properties");
        return configUtils.get("chaos.domain", String.class);
    }

    /**
     * 将返回来的Json成功的信息装载到HrThirdPartyAccountDO
     * 出现错误直接抛出BizException
     *
     * @param opType 0 绑定 1 刷新账号信息
     * @param json
     * @param thirdPartyAccountDO
     * @throws Exception
     */
    private void fillHrThirdPartyAccount(int opType, String json, HrThirdPartyAccountDO thirdPartyAccountDO) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(json);
//			0 运行完成，返回结果
//			1 账号密码错误
//			2 网络超时
//			3 验证码错误，处理方式同异常
//			4 捕获异常，操作中断
        int status = jsonObject.getIntValue("status");

        String opName = opType == 0 ? "绑定":"刷新";

        if (status == 0) {
            thirdPartyAccountDO.setRemainNum(jsonObject.getIntValue("remain_number"));
            thirdPartyAccountDO.setRemainProfileNum(jsonObject.getIntValue("resume_number"));
        } else if (status == 1) {
            throw new BIZException(1, "账号或者密码错误！");
        } else if (status == 2) {
            throw new BIZException(2, opName+"超时了，请重试！");
        } else if (status == 3) {
            throw new BIZException(3, opName+"失败了，请重试！");
        } else if (status == 4) {
            throw new BIZException(4, opName+"失败了，请稍后重试！");
        } else {
            throw new BIZException(5, opName+"发生异常，请稍后重试！");
        }
    }


    /**
     * 绑定第三方账号
     *
     * @param hrThirdPartyAccount
     * @return
     */
    public HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount) throws Exception {
        logger.info("ChaosServiceImpl bind");
        String domain = getDomain();

        ChannelType chnnelType = ChannelType.instaceFromInteger(hrThirdPartyAccount.getChannel());
        String bindURI = chnnelType.getBindURI(domain);
        logger.info("ChaosServiceImpl bind bindURI:" + bindURI);
        String params = ChaosTool.getParams(hrThirdPartyAccount.getUsername(), hrThirdPartyAccount.getPassword(), hrThirdPartyAccount.getMembername(), chnnelType);
        logger.info("ChaosServiceImpl bind params:" + params);
        String data = UrlUtil.sendPost(bindURI, params, Constant.CONNECTION_TIME_OUT, Constant.READ_TIME_OUT);
        logger.info("ChaosServiceImpl bind data:" + data);
        //String data = "{\"status\":0,\"message\":\"success\", \"data\":{\"remain_number\":1,\"resume_number\":2}}";

        fillHrThirdPartyAccount(0, data, hrThirdPartyAccount);

        return hrThirdPartyAccount;
    }

    /**
     * 同步第三方账号
     *
     * @param hrThirdPartyAccount
     * @return
     */
    public HrThirdPartyAccountDO synchronization(HrThirdPartyAccountDO hrThirdPartyAccount) throws Exception {

        String domain = getDomain();
        ChannelType chnnelType = ChannelType.instaceFromInteger(hrThirdPartyAccount.getChannel());
        String synchronizationURI = chnnelType.getRemain(domain);
        String params = ChaosTool.getParams(hrThirdPartyAccount.getUsername(), hrThirdPartyAccount.getPassword(), hrThirdPartyAccount.getMembername(), chnnelType);
        logger.info("ChaosServiceImpl refresh refreshURI:" + synchronizationURI);
        String data = UrlUtil.sendPost(synchronizationURI, params, Constant.CONNECTION_TIME_OUT, Constant.READ_TIME_OUT);
        logger.info("ChaosServiceImpl refresh params:" + params);
        //String data = "{\"status\":0,\"message\":\"success\", \"data\":{\"remain_number\":1,\"resume_number\":2}}";
        fillHrThirdPartyAccount(1, data, hrThirdPartyAccount);
        return hrThirdPartyAccount;
    }

    /**
     * 同步职位到第三方
     *
     * @param positions
     * @return
     */
    public Response synchronizePosition(List<ThirdPartyPositionForSynchronizationWithAccount> positions) {

        try {
            if (positions != null && positions.size() > 0) {
                String email = "";
                try {
                    ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
                    configUtils.loadResource("chaos.properties");
                    email = configUtils.get("chaos.email", String.class);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    //do nothing
                }
                DateTime dt = new DateTime();
                int second = dt.getSecondOfDay();
                for (ThirdPartyPositionForSynchronizationWithAccount position : positions) {
                    position.getPosition_info().setEmail("cv_" + position.getPosition_id() + email);
                    String positionJson = JSON.toJSONString(position);
                    logger.info("synchronize position:" + positionJson);

                    redisClient.lpush(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_QUEUE.toString(), positionJson);
                    if (second < 60 * 60 * 24) {
                        redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(position.getPosition_id()), position.getAccount_id(), "1", 60 * 60 * 24 - second);
                    }
                }
                return ResponseUtils.success(null);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            }
        } catch (CacheConfigNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXHAUSTED);
        } finally {
            //do nothing
        }
    }

    public Response refreshPosition(ThirdPartyPositionForSynchronizationWithAccount position) {
        logger.info("refreshPosition:redis:{}", JSON.toJSONString(position));
        ThirdPartyPositionData p = new ThirdPartyPositionData();
        try {
            String positionJson = JSON.toJSONString(position);
            redisClient.lpush(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH_QUEUE.toString(), positionJson);
            logger.info("refreshPosition:redis:{}", position.getPosition_id());
            p.setChannel(Byte.valueOf(position.getChannel()));
            p.setPosition_id(Integer.valueOf(position.getPosition_id()));
            p.setIs_refresh((byte) PositionRefreshType.refreshing.getValue());
            p.setRefresh_time((new DateTime()).toString("yyyy-MM-dd HH:mm:ss"));
            p.setAccount_id(position.getAccount_id());
            thirdpartyPositionDao.upsertThirdPartyPosition(p);

            DateTime dt = new DateTime();
            int second = dt.getSecondOfDay();
            if (second < 60 * 60 * 24) {
                redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(position.getPosition_id()), String.valueOf(position.getAccount_id()), "1", 60 * 60 * 24 - second);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXHAUSTED);
        } finally {
            //do nothing
        }

        return ResponseUtils.success(p);
    }
}
