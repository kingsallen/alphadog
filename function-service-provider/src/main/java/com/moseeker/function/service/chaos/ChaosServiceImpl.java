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
import com.moseeker.function.service.chaos.position.Position51WithAccount;
import com.moseeker.function.service.chaos.position.PositionLiepinWithAccount;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
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

    private String getConfigString(String key) throws Exception {
        ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
        configUtils.loadResource("chaos.properties");
        return configUtils.get(key, String.class);
    }

    /**
     * 获取Chaos访问路径
     *
     * @return
     * @throws Exception
     */
    private String getDomain() throws Exception {
        return getConfigString("chaos.domain");
    }

    /**
     * 将返回来的Json成功的信息装载到HrThirdPartyAccountDO
     * 出现错误直接抛出BizException
     *
     * @param opType              0 绑定 1 刷新账号信息
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

        String opName = opType == 0 ? "绑定" : "刷新";

        if (status == 0) {
            thirdPartyAccountDO.setRemainNum(jsonObject.getJSONObject("data").getIntValue("remain_number"));
            thirdPartyAccountDO.setRemainProfileNum(jsonObject.getJSONObject("data").getIntValue("resume_number"));
        } else if (status == 1) {
            throw new BIZException(1, "账号或者密码错误！");
        } else if (status == 2) {
            throw new BIZException(2, opName + "超时了，请重试！");
        } else if (status == 3) {
            throw new BIZException(3, opName + "失败了，请重试！");
        } else if (status == 4) {
            throw new BIZException(4, opName + "失败了，请稍后重试！");
        } else {
            throw new BIZException(5, opName + "发生异常，请稍后重试！");
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
        String synchronizationURI = chnnelType.getRemainURI(domain);
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
    public void synchronizePosition(List<ThirdPartyPositionForSynchronizationWithAccount> positions) throws Exception {
        if (positions == null || positions.size() == 0) {
            logger.warn("同步一个空的职位到第三方平台，跳过。");
            return;
        }

        String email = getConfigString("chaos.email");

        int second = new DateTime().getSecondOfDay();

        for (ThirdPartyPositionForSynchronizationWithAccount position : positions) {

            position.getPosition_info().setEmail("cv_" + position.getPosition_id() + email);

            String positionJson = null;

            if (position.getChannel() == ChannelType.LIEPIN.getValue()) {
                positionJson = JSON.toJSONString(PositionLiepinWithAccount.copyFromSyncPosition(position));
            } else if (position.getChannel() == ChannelType.JOB51.getValue() || position.getChannel() == ChannelType.ZHILIAN.getValue()) {
                positionJson = JSON.toJSONString(Position51WithAccount.copyFromSyncPosition(position));
            }

            logger.info("synchronize position:" + positionJson);

            if (positionJson == null) {
                logger.warn("不能识别的Channel类型:{}", position.getChannel());
                continue;
            }

            redisClient.lpush(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_QUEUE.toString(), positionJson);

            logger.info("成功将同步数据插入队列:{}", position.getPosition_id());

            if (second < 60 * 60 * 24) {
                redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(position.getPosition_id()), String.valueOf(position.getAccount_id()), "1", 60 * 60 * 24 - second);
            }
        }
    }

    public void refreshPosition(ThirdPartyPositionForSynchronizationWithAccount position) throws Exception {
        String positionJson = null;

        if (position.getChannel() == ChannelType.LIEPIN.getValue()) {
            positionJson = JSON.toJSONString(PositionLiepinWithAccount.copyFromSyncPosition(position));
        } else if (position.getChannel() == ChannelType.JOB51.getValue() || position.getChannel() == ChannelType.ZHILIAN.getValue()) {
            positionJson = JSON.toJSONString(Position51WithAccount.copyFromSyncPosition(position));
        }
        logger.info("refresh position:" + positionJson);

        redisClient.lpush(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH_QUEUE.toString(), positionJson);

        DateTime dt = new DateTime();
        int second = dt.getSecondOfDay();
        if (second < 60 * 60 * 24) {
            redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(position.getPosition_id()), String.valueOf(position.getAccount_id()), "1", 60 * 60 * 24 - second);
        }
    }
}
