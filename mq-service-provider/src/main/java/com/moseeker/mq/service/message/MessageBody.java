package com.moseeker.mq.service.message;

import java.util.List;

/**
 * @ClassName MessageBody
 * @Description TODO
 * @Author jack
 * @Date 2019/3/27 10:03 AM
 * @Version 1.0
 */
public class MessageBody {

    private int id;
    private java.lang.String title;
    private java.lang.String sendCondition;
    private java.lang.String sendTime;
    private java.lang.String sendTo;
    private java.lang.String sample;
    private java.lang.String first;
    private java.lang.String priority;
    private java.lang.String remark;
    private byte status;
    private java.lang.String customFirst;
    private java.lang.String customRemark;
    private java.util.List<FlexibleField> flexibleFields;

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

    public List<FlexibleField> getFlexibleFields() {
        return flexibleFields;
    }

    public void setFlexibleFields(List<FlexibleField> flexibleFields) {
        this.flexibleFields = flexibleFields;
    }
}
