package com.moseeker.position.constants.position.job58;

import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.position.constants.position.liepin.LiepinPositionOperateConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cjm
 * @date 2018-11-27 9:25
 **/
public class Job58PositionOperateConstant {
    private static Logger logger = LoggerFactory.getLogger(Job58PositionOperateConstant.class);

    public static String job58PositionSync;
    public static String job58AppKey;
    public static String job58SecretKey;
    public static String job58PositionAddress;
    public static String job58ProfileEmail;

    static{
        job58PositionSync = getSettingProperty("58job_position_sync_url");
        job58SecretKey = getSettingProperty("58job_api_secret");
        job58AppKey = getSettingProperty("58job_api_app_key");
        job58PositionAddress = getSettingProperty("58job_position_workaddress");
        job58ProfileEmail = getSettingProperty("58job_profile_email");
    }

    public static String getSettingProperty(String key){
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        try {
            propertiesUtils.loadResource("setting.properties");
            return propertiesUtils.get(key, String.class);
        } catch (Exception e) {
            logger.error("get job58 operate url properties error:{}",e);
        }
        return null;
    }

}
