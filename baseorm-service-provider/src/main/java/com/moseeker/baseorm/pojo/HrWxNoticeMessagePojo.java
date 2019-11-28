package com.moseeker.baseorm.pojo;

/**
 * Created by huangxia on 2019/11/5.
 */
public class HrWxNoticeMessagePojo {

    private int id;
    private String title;
    private String sendCondition;
    private String sendTime;
    private String sendTo;
    private String sample;
    private String first;
    private String priority;
    private String remark;
    private byte status;
    private String customFirst;
    private String customRemark;

    private Integer configId ;
    private String frequencyOptions ;
    private String frequencyValue ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendCondition() {
        return sendCondition;
    }

    public void setSendCondition(String sendCondition) {
        this.sendCondition = sendCondition;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getCustomFirst() {
        return customFirst;
    }

    public void setCustomFirst(String customFirst) {
        this.customFirst = customFirst;
    }

    public String getCustomRemark() {
        return customRemark;
    }

    public void setCustomRemark(String customRemark) {
        this.customRemark = customRemark;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getFrequencyOptions() {
        return frequencyOptions;
    }

    public void setFrequencyOptions(String frequencyOptions) {
        this.frequencyOptions = frequencyOptions;
    }

    public String getFrequencyValue() {
        return frequencyValue;
    }

    public void setFrequencyValue(String frequencyValue) {
        this.frequencyValue = frequencyValue;
    }
}
