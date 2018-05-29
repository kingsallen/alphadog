package com.moseeker.chat.exception;

/**
 * 语音错误枚举
 *
 * @author cjm
 * @date 2018-05-10 12:49
 **/
public enum VoiceErrorEnum {
    /**
     * 微信服务器暂无响应，请稍后重试
     */
    VOICE_DOWNLOAD_LIMIT_CLEAR_FAILED(91013,"微信服务器暂无响应，请稍后重试"),
    /**
     * 本条消息记录添加失败
     */
     CHAT_INFO_ADD_FAILED(91014,"本条消息记录添加失败"),
    /**
     * 由于alphadog还未将语音下载到本地导致小程序拉取不到语音本地路径
     */
    VOICE_PULL_BUSY(91015,"服务器繁忙，请稍后获取"),
    /**
     * 语音下载失败，发送报警邮件
     */
    VOICE_SEND_WARN_EMAIL(91021,"语音下载失败，发送报警邮件"),
    /**
     * 报警邮件已经发送，请勿重复发送
     */
    VOICE_EMAIL_SEND_ALREADY(91022,"报警邮件已经发送，请勿重复发送"),
    /**
     * 语音拉取请求参数错误
     */
    VOICE_PULL_PARAM_ERROR(91023,"语音拉取请求参数错误"),
    ;

    VoiceErrorEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
