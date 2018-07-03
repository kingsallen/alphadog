package com.moseeker.position.constants.position.liepin;

import com.moseeker.common.util.ConfigPropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 猎聘api请求路径
 *
 * @author cjm
 * @date 2018-06-19 11:31
 **/
public class LiepinPositionOperateConstant {

    private static Logger logger = LoggerFactory.getLogger(LiepinPositionOperateConstant.class);

    public static String liepinPositionSync;
    public static String liepinPositionRepub;
    public static String liepinPositionEdit;
    public static String liepinPositionEnd;
    public static String liepinPositionGet;

    public static String liepinChannel;
    public static String liepinSecretKey;

    static{
        liepinPositionSync = getSettingProperty("liepin_position_sync_url");
        liepinPositionRepub = getSettingProperty("liepin_position_repub_url");
        liepinPositionEdit = getSettingProperty("liepin_position_edit_url");
        liepinPositionEnd = getSettingProperty("liepin_position_end_url");
        liepinPositionGet = getSettingProperty("liepin_position_get_url");
        liepinChannel = getSettingProperty("liepin_position_api_channel");
        liepinSecretKey = getSettingProperty("liepin_position_api_secretkey");
    }

    public static String getSettingProperty(String key){
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        try {
            propertiesUtils.loadResource("setting.properties");
            return propertiesUtils.get(key, String.class);
        } catch (Exception e) {
            logger.error("get liepin operate url properties error:{}",e);
        }
        return null;
    }

}
