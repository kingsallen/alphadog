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
     * 语音下载路径
     */
    public static final String VOICE_DOWNLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

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

    /**
     * redis中语音下载次数key修饰前缀
     */
    public static final String VOICE_DOWNLOAD_FREQUENCY = "VOICE_DOWNLOAD_FREQUENCY:";

    /**
     * redis中语音下载限制清零次数key修饰前缀
     */
    public static final String VOICE_CLEAR_TIMES = "VOICE_CLEAR_TIMES:";

    /**
     * 语音下载失败报警邮箱
     */
    public static final String WARN_EMAIL_ADDRESS_DEV = "cuijiaming@moseeker.com";

    /**
     * 语音下载次数达上限报警邮箱
     */
    public static final String[] WARN_EMAIL_ADDRESS_CS = {"clientsuccess@moseeker.com"};
}
