package com.moseeker.application.infrastructure.pojo;

import java.util.Map;

/**
 *
 * 模板消息
 *
 * Created by jack on 23/01/2018.
 */
public class WXTemplateMsgPojo {

    private int userId; // optional
    private byte type; // optional
    private int sysTemplateId; // optional
    private String url; // optional
    private int companyId; // optional
    private java.util.Map<String,MessageTplDataCol> data; // optional
    private byte enableQxRetry; // optional                     //1表示  如果当前公司的模板消息发送失败（包括当前公司没有绑定公众号），则使用仟寻发送模板消息
    private long delay; // optional
    private String validators; // optional
    private String id; // optional
    private String validatorsParams; // optional

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getSysTemplateId() {
        return sysTemplateId;
    }

    public void setSysTemplateId(int sysTemplateId) {
        this.sysTemplateId = sysTemplateId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Map<String, MessageTplDataCol> getData() {
        return data;
    }

    public void setData(Map<String, MessageTplDataCol> data) {
        this.data = data;
    }

    public byte getEnableQxRetry() {
        return enableQxRetry;
    }

    public void setEnableQxRetry(byte enableQxRetry) {
        this.enableQxRetry = enableQxRetry;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public String getValidators() {
        return validators;
    }

    public void setValidators(String validators) {
        this.validators = validators;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValidatorsParams() {
        return validatorsParams;
    }

    public void setValidatorsParams(String validatorsParams) {
        this.validatorsParams = validatorsParams;
    }
}
