package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.EmojiFilter;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.UrlUtil;
import com.moseeker.function.constants.BindThirdPart;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.function.service.chaos.position.Position51WithAccount;
import com.moseeker.function.service.chaos.position.PositionLiepinWithAccount;
import com.moseeker.function.service.chaos.position.PositionZhilianWithAccount;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import com.moseeker.function.config.AppConfig;


import javax.annotation.Resource;
import java.net.ConnectException;
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
@ContextConfiguration(classes = AppConfig.class)
public class ChaosServiceImpl {

    Logger logger = LoggerFactory.getLogger(ChaosServiceImpl.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Autowired
    private AmqpTemplate amqpTemplate;


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


    private String postBind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras, String routingKey) throws Exception {
        //推送需要绑定第三方账号的信息到rabbitMQ中
        String param=ChaosTool.getParams(hrThirdPartyAccount, extras);
        String account_Id=hrThirdPartyAccount.getId()+"";
        logger.info("准备推送"+account_Id+"数据到RabbitMQ的RoutingKey："+routingKey);
        amqpTemplate.send(BindThirdPart.BIND_EXCHANGE_NAME, routingKey, MessageBuilder.withBody(param.getBytes()).build());
        logger.info("推送RabbitMQ成功");

        //尝试从从redis中获取绑定结果,超时后推出
        String cacheKey=redisClient.genCacheKey(BindThirdPart.APP_ID, BindThirdPart.KEY_IDENTIFIER,account_Id);

        logger.info("准备从Redis获取"+routingKey+"结果");
        redisClient.existWithTimeOutCheck(cacheKey);

        String data=redisClient.get(BindThirdPart.APP_ID, BindThirdPart.KEY_IDENTIFIER,account_Id);
        logger.info("成功从Redis获取推送绑定结果");

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
        try {
//            String data = "{\"status\":100,\"message\":\"182****3365\", \"data\":{\"remain_number\":1,\"resume_number\":2}}";
            String data=postBind(hrThirdPartyAccount,extras, BindThirdPart.BIND_SEND_ROUTING_KEY);

            JSONObject jsonObject = JSONObject.parseObject(data);
            int status = jsonObject.getIntValue("status");

            if (status == 0) {
                hrThirdPartyAccount.setBinding((short)BindingStatus.GETINGINFO.getValue());
                logger.info("绑定成功，binding标志为"+hrThirdPartyAccount.getBinding());
            } else {
                String message = jsonObject.getString("message");

                if (status == 1) {
                    if (StringUtils.isNullOrEmpty(message)) {
                        message = BindThirdPart.BIND_UP_ERR_MSG;
                    }
                    throw new BIZException(1, message);
                } else if (status == 100) {
                    hrThirdPartyAccount.setBinding((short) BindingStatus.NEEDCODE.getValue());
                }  else if (status == 2) {
                    hrThirdPartyAccount.setBinding((short)BindingStatus.ERROR.getValue());
                    if (StringUtils.isNullOrEmpty(message)) {
                        message = BindThirdPart.BIND_EXP_MSG;
                    } else {
                        message = EmojiFilter.unicodeToUtf8(message);
                    }
                } else {
                    if (StringUtils.isNullOrEmpty(message)) {
                        message = BindThirdPart.BIND_ERR_MSG;
                    }
                    throw new BIZException(1, message);
                }
                hrThirdPartyAccount.setErrorMessage(message);
            }
        } catch (ConnectException e) {
            //绑定超时发送邮件
            hrThirdPartyAccount.setBinding((short)BindingStatus.ERROR.getValue());
            hrThirdPartyAccount.setErrorMessage(BindThirdPart.BIND_TIMEOUT_MSG);
        }


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

        String data=postBind(hrThirdPartyAccount,extras, BindThirdPart.BIND_CONFIRM_SEND_ROUTING_KEY);

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

        String data=postBind(hrThirdPartyAccount,extras, BindThirdPart.BIND_CODE_SEND_ROUTING_KEY);

        JSONObject jsonObject = JSONObject.parseObject(data);
        int status = jsonObject.getIntValue("status");
        String message = jsonObject.getString("message");
        if (status == 0) {
            hrThirdPartyAccount.setBinding((short)BindingStatus.BOUND.getValue());
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
            hrThirdPartyAccount.setBinding((short)BindingStatus.ERROR.getValue());
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
        JSONObject jsonObject = JSONObject.parseObject(data);
        int status = jsonObject.getIntValue("status");
        if (status == 0) {
            hrThirdPartyAccount.setBinding((short) BindingStatus.BOUND.getValue());
            hrThirdPartyAccount.setRemainNum(jsonObject.getJSONObject("data").getIntValue("remain_number"));
            hrThirdPartyAccount.setRemainProfileNum(jsonObject.getJSONObject("data").getIntValue("resume_number"));
        } else {
            String message = jsonObject.getString("message");

            if (status == 1) {
                if (StringUtils.isNullOrEmpty(message)) {
                    message = "用户名或密码错误";
                }
                hrThirdPartyAccount.setBinding((short) BindingStatus.INFOWRONG.getValue());

            } else if (status == 100) {
                hrThirdPartyAccount.setBinding((short) BindingStatus.NEEDCODE.getValue());
            } else if (status == 2) {
                hrThirdPartyAccount.setBinding((short)BindingStatus.REFRESHWRONG.getValue());
                if (StringUtils.isNullOrEmpty(message)) {
                    message = "刷新异常，请重试";
                } else {
                    message = EmojiFilter.unicodeToUtf8(message);
                }
            } else {
                hrThirdPartyAccount.setBinding((short)BindingStatus.FAIL.getValue());
                if (StringUtils.isNullOrEmpty(message)) {
                    message = "绑定错误，请重新绑定";
                }
            }

            hrThirdPartyAccount.setErrorMessage(message);
        }
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

            logger.info("synchronize positions before change :" + positions);
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

//            redisClient.lpush(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_SYNCHRONIZATION_QUEUE.toString(), positionJson);

            amqpTemplate.send(BindThirdPart.BIND_EXCHANGE_NAME, BindThirdPart.SYNC_POSITION_SEND_ROUTING_KEY, MessageBuilder.withBody(positionJson.getBytes()).build());

            logger.info("成功将同步数据插入队列:{}", position.getPosition_id());

            /*if (second < 60 * 60 * 24) {
                redisClient.set(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(position.getPosition_id()), String.valueOf(position.getAccount_id()), "1", 60 * 60 * 24 - second);
            }*/
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
