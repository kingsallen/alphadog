package com.moseeker.chat.constant;

import java.io.File;

/**
 * 聊天语音相关常量
 *
 * @author cjm
 * @date 2018-05-16 19:45
 **/
public class ChatVoiceConstant {
    /**
     * 语音限制次数清零路径
     */
    public static final String VOICE_CLEAR_URL = "https://api.weixin.qq.com/cgi-bin/clear_quota?access_token=ACCESS_TOKEN";

    /**
     * redis中存储路径标识
     */
    public static final String VOICE_URL_IN_REDIS = "VOICE_URL:";
    /**
     * 语音本地存储路径
     */
    public static final String VOICE_LOCAL_URL = File.separator + "mnt"
            + File.separator + "nfs"
            + File.separator + "attachment"
            + File.separator + "imchatvoicefile";

    /**
     * 从redis中取语音路径的重试次数
     */
    public static final int GET_REDIS_VOICE_RETRY_TIMES = 10;

    public static final String VOICE_DOWNLOAD_FREQUENCY = "VOICE_DOWNLOAD_FREQUENCY:";

    public static final String VOICE_CLEAR_TIMES = "VOICE_CLEAR_TIMES:";

    public static final String WARN_EMAIL_ADDRESS_DEV = "cuijiaming@moseeker.com";

    public static final String[] WARN_EMAIL_ADDRESS_CS = {"cuijiaming@moseeker.com"};
}
