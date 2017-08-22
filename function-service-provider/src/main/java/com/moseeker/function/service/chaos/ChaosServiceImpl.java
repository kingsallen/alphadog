package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.UrlUtil;
import com.moseeker.function.service.chaos.position.Position51WithAccount;
import com.moseeker.function.service.chaos.position.PositionLiepinWithAccount;
import com.moseeker.function.service.chaos.position.PositionZhilianWithAccount;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        int status = jsonObject.getIntValue("status");

        String opName = opType == 0 ? "绑定" : "刷新";

        if (status == 0) {
            thirdPartyAccountDO.setBinding(Integer.valueOf(1).shortValue());
            thirdPartyAccountDO.setRemainNum(jsonObject.getJSONObject("data").getIntValue("remain_number"));
            thirdPartyAccountDO.setRemainProfileNum(jsonObject.getJSONObject("data").getIntValue("resume_number"));
        } else {
            String message = jsonObject.getString("message");

            if (status == 1) {
                thirdPartyAccountDO.setBinding(Integer.valueOf(4).shortValue());
                if (StringUtils.isNullOrEmpty(message)) {
                    message = "用户名或密码错误";
                }
            } else if (status == 100) {
                thirdPartyAccountDO.setBinding(Integer.valueOf(100).shortValue());
            } else if (status == 2 || status == 9) {
                thirdPartyAccountDO.setBinding(Integer.valueOf(opType == 0 ? 6 : 7).shortValue());
                if (StringUtils.isNullOrEmpty(message)) {
                    message = opName + "异常，请重试";
                }
            } else {
                thirdPartyAccountDO.setBinding(Integer.valueOf(5).shortValue());
                if (StringUtils.isNullOrEmpty(message)) {
                    message = "绑定错误，请重新绑定";
                }
            }

            thirdPartyAccountDO.setErrorMessage(message);
        }
    }


    private String postBind(int channel, String params) throws Exception {
        String domain = getDomain();

        ChannelType chnnelType = ChannelType.instaceFromInteger(channel);
        String bindURI = chnnelType.getBindURI(domain);
        String data = UrlUtil.sendPost(bindURI, params, Constant.CONNECTION_TIME_OUT, Constant.READ_TIME_OUT);
        return data;
    }

    /**
     * 绑定第三方账号
     *
     * @param hrThirdPartyAccount
     * @return
     */
    public HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        logger.info("ChaosServiceImpl bind");
//        String data = "{\"status\":100,\"message\":\"182****3365\", \"data\":{\"remain_number\":1,\"resume_number\":2}}";
        String data = postBind(hrThirdPartyAccount.getChannel(), ChaosTool.getParams(hrThirdPartyAccount, extras));
        fillHrThirdPartyAccount(0, data, hrThirdPartyAccount);

        return hrThirdPartyAccount;
    }


    /**
     * 确认发送短信验证码
     *
     * @param hrThirdPartyAccount
     * @return
     */
    public HrThirdPartyAccountDO bindConfirm(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras, boolean confirm) throws Exception {
        logger.info("ChaosServiceImpl bindConfirm");
//        String data = "{\"status\":0,\"message\":\"success\", \"data\":{\"remain_number\":1,\"resume_number\":2}}";
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.putAll(extras);
        paramsMap.put("confirm", confirm);
        String data = postBind(hrThirdPartyAccount.getChannel(), ChaosTool.getParams(hrThirdPartyAccount, paramsMap));

        JSONObject jsonObject = JSONObject.parseObject(data);

        int status = jsonObject.getIntValue("status");
        String message = jsonObject.getString("message");
        //发送成功
        if (status == 0 || status == 110) {
            return hrThirdPartyAccount;
        } else {
            throw new BIZException(status, message);
        }
    }

    /**
     * 发送短信验证码
     *
     * @param hrThirdPartyAccount
     * @return
     */
    public HrThirdPartyAccountDO bindMessage(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras, String code) throws Exception {
        logger.info("ChaosServiceImpl bindMessage");
//        String data = "{\"status\":0,\"message\":\"success\", \"data\":{\"remain_number\":1,\"resume_number\":2}}";
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.putAll(extras);
        paramsMap.put("code", code);
        String data = postBind(hrThirdPartyAccount.getChannel(), ChaosTool.getParams(hrThirdPartyAccount, paramsMap));
        JSONObject jsonObject = JSONObject.parseObject(data);
        int status = jsonObject.getIntValue("status");
        String message = jsonObject.getString("message");
        if (status == 0) {
            hrThirdPartyAccount.setBinding(Integer.valueOf(1).shortValue());
            hrThirdPartyAccount.setRemainNum(jsonObject.getJSONObject("data").getIntValue("remain_number"));
            hrThirdPartyAccount.setRemainProfileNum(jsonObject.getJSONObject("data").getIntValue("resume_number"));
        } else if (status == 112) {//验证码错误
            if (StringUtils.isNullOrEmpty(message)) {
                message = "验证码错误";
            }
            throw new BIZException(112, message);
        } else if (status == 111) {//验证码超时
            if (StringUtils.isNullOrEmpty(message)) {
                message = "验证码超时";
            }
            throw new BIZException(111, message);
        } else if (status == 1) {
            if (StringUtils.isNullOrEmpty(message)) {
                message = "用户名或密码错误";
            }
            throw new BIZException(1, message);
        } else if (status == 9) {
            //发送绑定失败的邮件
            hrThirdPartyAccount.setBinding(Integer.valueOf(6).shortValue());
        } else {
            if (StringUtils.isNullOrEmpty(message)) {
                message = "绑定失败，请重试";
            }
            throw new BIZException(1, message);
        }
        hrThirdPartyAccount.setErrorMessage(message);
        return hrThirdPartyAccount;
    }


    /**
     * 同步第三方账号
     *
     * @param hrThirdPartyAccount
     * @return
     */
    public HrThirdPartyAccountDO synchronization(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {

        String domain = getDomain();
        ChannelType chnnelType = ChannelType.instaceFromInteger(hrThirdPartyAccount.getChannel());
        String synchronizationURI = chnnelType.getRemainURI(domain);
        String params = ChaosTool.getParams(hrThirdPartyAccount, extras);
        String data = UrlUtil.sendPost(synchronizationURI, params, Constant.CONNECTION_TIME_OUT, Constant.READ_TIME_OUT);
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
            } else if (position.getChannel() == ChannelType.ZHILIAN.getValue()) {
                positionJson = JSON.toJSONString(PositionZhilianWithAccount.copyFromSyncPosition(position));
            } else if (position.getChannel() == ChannelType.JOB51.getValue()) {
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
