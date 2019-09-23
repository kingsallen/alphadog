package com.moseeker.baseorm.constant;

import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/9/7
 */
public enum SMSScene {
    SMS_SIGNUP(1, KeyIdentifier.SMS_SIGNUP, "登录验证码"),
    SMS_PWD_FORGOT(2, KeyIdentifier.SMS_PWD_FORGOT, "忘记密码"),
    SMS_CHANGEMOBILE_CODE(3, KeyIdentifier.SMS_CHANGEMOBILE_CODE, "修改手机号码"),
    SMS_RESETMOBILE_CODE(4, KeyIdentifier.SMS_RESETMOBILE_CODE, "重置手机号码"),
    SMS_VERIFY_MOBILE(5, KeyIdentifier.SMS_VERIFY_MOBILE, "认领卡片验证手机号码")
    ;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private int value;
    private KeyIdentifier keyIdentifier;
    private String description;

    private static Map<Integer, SMSScene> storage = new HashMap<>(5);
    static {
        for (SMSScene smsScene : values()) {
            storage.put(smsScene.getValue(), smsScene);
        }
    }

    SMSScene(int scene, KeyIdentifier keyIdentifier, String description) {
        this.value = scene;
        this.keyIdentifier = keyIdentifier;
        this.description = description;
    }

    /**
     * 保存验证码
     * @param countryCode 国家代码
     * @param mobile 手机号码
     * @param verifyCode 验证码
     * @param redisClient 缓存客户端
     */
    public void saveVerifyCode(String countryCode, String mobile, String verifyCode, RedisClient redisClient) {
        String pattern = StringUtils.defaultIfBlank(countryCode, "")+StringUtils.defaultIfBlank(mobile, "");
        logger.info("SMSScene saveVerifyCode keyIdentifier:{}, pattern:{}, verifyCode:{}", keyIdentifier, pattern, verifyCode);
        redisClient.set(AppId.APPID_ALPHADOG.getValue(), keyIdentifier.toString(), pattern, verifyCode);
    }

    /**
     * 验证验证码的有效性
     * @param countryCode 国家代码
     * @param mobile 手机号码
     * @param verifyCode 验证码
     * @param redisClient 缓存客户端
     * @return 校验结果 true：正确；false：不正确
     */
    public boolean validateVerifyCode(String countryCode, String mobile, String verifyCode, RedisClient redisClient) {
        if (StringUtils.isBlank(verifyCode)) {
            return false;
        }
        //简历上传小程序推荐来源的认领不需要校验手机号
        if(Constant.NO_VSCODE_CHECK.equals(verifyCode)){
            return true;
        }

        String pattern = StringUtils.defaultIfBlank(countryCode, "")+StringUtils.defaultIfBlank(mobile, "");

        logger.info("SMSScene validateVerifyCode keyIdentifier:{}, pattern:{}, verifyCode:{}", keyIdentifier, pattern, verifyCode);
        String codeInRedis = redisClient.get(AppId.APPID_ALPHADOG.getValue(), keyIdentifier.toString(), pattern);
        if (verifyCode.equals(codeInRedis)) {
            logger.info("SMSScene validateVerifyCode code equals!");
            redisClient.del(AppId.APPID_ALPHADOG.getValue(), keyIdentifier.toString(), pattern);
            return true;
        } else {
            logger.info("SMSScene validateVerifyCode code not equals!");

            //若redis中已不存在该验证码
            if(StringUtils.isBlank(codeInRedis)){
                redisClient.del(AppId.APPID_ALPHADOG.getValue(),KeyIdentifier.SMS_CODE_VERIFY_LIMIT.toString(),pattern);
                return false;
            }

            //添加新逻辑，验证码不能连续超过五次
            String countStrValue = redisClient.get(AppId.APPID_ALPHADOG.getValue(),KeyIdentifier.SMS_CODE_VERIFY_LIMIT.toString(),pattern);

            if(!StringUtils.isBlank(countStrValue)){
                Integer countIntValue = Integer.valueOf(countStrValue);
                if(countIntValue>=4){
                    redisClient.del(AppId.APPID_ALPHADOG.getValue(),keyIdentifier.toString(),pattern);
                    redisClient.del(AppId.APPID_ALPHADOG.getValue(),KeyIdentifier.SMS_CODE_VERIFY_LIMIT.toString(),pattern);
                }else{
                    redisClient.set(AppId.APPID_ALPHADOG.getValue(),KeyIdentifier.SMS_CODE_VERIFY_LIMIT.toString(),pattern,String.valueOf(countIntValue+1));
                }

            }else{
                redisClient.set(AppId.APPID_ALPHADOG.getValue(),KeyIdentifier.SMS_CODE_VERIFY_LIMIT.toString(),pattern,String.valueOf(1));
            }

            return false;
        }
    }

    /**
     * 根据场景编码初始化场景信息
     * @param value 场景编码
     * @return 场景
     */
    public static SMSScene instanceFromValue(int value) {
        return storage.get(value);
    }

    public int getValue() {
        return value;
    }

    public KeyIdentifier getKeyIdentifier() {
        return keyIdentifier;
    }

    public String getDescription() {
        return description;
    }
}
