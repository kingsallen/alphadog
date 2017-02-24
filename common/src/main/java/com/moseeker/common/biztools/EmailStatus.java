package com.moseeker.common.biztools;

/**
 * 邮箱状态
 * Created by jack on 21/02/2017.
 */
public enum EmailStatus {

    NOMAIL(0), NOT_ANSWER_EMAIL(1), ATTACHMENT_NOT_SUPPORT(2), ATTACHMENT_MORE_THEN_MAXIMUN(3), MAIL_NOT_FOUND(8), MAIL_PARSING_FAILED(9);

    private int value;
    private String message;

    private EmailStatus(int value) {
        this.value = value;
        init();
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return this.message;
    }

    private void init() {
        switch (value) {
            case 0: message = "有效投递"; break;
            case 1: message = "未收到回复邮件"; break;
            case 2: message = "件格式不支持"; break;
            case 3: message = "附件超过10M"; break;
            case 8: message = "Email简历读取失败"; break;
            case 9: message = "Email简历解析失败"; break;
            default: message = "";
        }
    }
}
