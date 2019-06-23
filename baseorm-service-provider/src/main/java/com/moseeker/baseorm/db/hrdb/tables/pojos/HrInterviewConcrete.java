/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 面试安排表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewConcrete implements Serializable {

    private static final long serialVersionUID = 1905764581;

    private Integer   id;
    private Integer   companyId;
    private Integer   appId;
    private Integer   applierId;
    private Integer   hrId;
    private Integer   interviewerId;
    private Integer   interviewAddressId;
    private Integer   interviewType;
    private String    interviewRoundName;
    private Integer   interviewFeedbackSheetId;
    private Integer   appTplId;
    private Integer   concreteOrder;
    private Integer   interviewTemplateId;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer   finished;
    private Integer   passed;
    private Integer   disable;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String    contactName;
    private String    contactPhone;

    public HrInterviewConcrete() {}

    public HrInterviewConcrete(HrInterviewConcrete value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.appId = value.appId;
        this.applierId = value.applierId;
        this.hrId = value.hrId;
        this.interviewerId = value.interviewerId;
        this.interviewAddressId = value.interviewAddressId;
        this.interviewType = value.interviewType;
        this.interviewRoundName = value.interviewRoundName;
        this.interviewFeedbackSheetId = value.interviewFeedbackSheetId;
        this.appTplId = value.appTplId;
        this.concreteOrder = value.concreteOrder;
        this.interviewTemplateId = value.interviewTemplateId;
        this.startTime = value.startTime;
        this.endTime = value.endTime;
        this.finished = value.finished;
        this.passed = value.passed;
        this.disable = value.disable;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.contactName = value.contactName;
        this.contactPhone = value.contactPhone;
    }

    public HrInterviewConcrete(
        Integer   id,
        Integer   companyId,
        Integer   appId,
        Integer   applierId,
        Integer   hrId,
        Integer   interviewerId,
        Integer   interviewAddressId,
        Integer   interviewType,
        String    interviewRoundName,
        Integer   interviewFeedbackSheetId,
        Integer   appTplId,
        Integer   concreteOrder,
        Integer   interviewTemplateId,
        Timestamp startTime,
        Timestamp endTime,
        Integer   finished,
        Integer   passed,
        Integer   disable,
        Timestamp createTime,
        Timestamp updateTime,
        String    contactName,
        String    contactPhone
    ) {
        this.id = id;
        this.companyId = companyId;
        this.appId = appId;
        this.applierId = applierId;
        this.hrId = hrId;
        this.interviewerId = interviewerId;
        this.interviewAddressId = interviewAddressId;
        this.interviewType = interviewType;
        this.interviewRoundName = interviewRoundName;
        this.interviewFeedbackSheetId = interviewFeedbackSheetId;
        this.appTplId = appTplId;
        this.concreteOrder = concreteOrder;
        this.interviewTemplateId = interviewTemplateId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.finished = finished;
        this.passed = passed;
        this.disable = disable;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getAppId() {
        return this.appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getApplierId() {
        return this.applierId;
    }

    public void setApplierId(Integer applierId) {
        this.applierId = applierId;
    }

    public Integer getHrId() {
        return this.hrId;
    }

    public void setHrId(Integer hrId) {
        this.hrId = hrId;
    }

    public Integer getInterviewerId() {
        return this.interviewerId;
    }

    public void setInterviewerId(Integer interviewerId) {
        this.interviewerId = interviewerId;
    }

    public Integer getInterviewAddressId() {
        return this.interviewAddressId;
    }

    public void setInterviewAddressId(Integer interviewAddressId) {
        this.interviewAddressId = interviewAddressId;
    }

    public Integer getInterviewType() {
        return this.interviewType;
    }

    public void setInterviewType(Integer interviewType) {
        this.interviewType = interviewType;
    }

    public String getInterviewRoundName() {
        return this.interviewRoundName;
    }

    public void setInterviewRoundName(String interviewRoundName) {
        this.interviewRoundName = interviewRoundName;
    }

    public Integer getInterviewFeedbackSheetId() {
        return this.interviewFeedbackSheetId;
    }

    public void setInterviewFeedbackSheetId(Integer interviewFeedbackSheetId) {
        this.interviewFeedbackSheetId = interviewFeedbackSheetId;
    }

    public Integer getAppTplId() {
        return this.appTplId;
    }

    public void setAppTplId(Integer appTplId) {
        this.appTplId = appTplId;
    }

    public Integer getConcreteOrder() {
        return this.concreteOrder;
    }

    public void setConcreteOrder(Integer concreteOrder) {
        this.concreteOrder = concreteOrder;
    }

    public Integer getInterviewTemplateId() {
        return this.interviewTemplateId;
    }

    public void setInterviewTemplateId(Integer interviewTemplateId) {
        this.interviewTemplateId = interviewTemplateId;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getFinished() {
        return this.finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Integer getPassed() {
        return this.passed;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public Integer getDisable() {
        return this.disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrInterviewConcrete (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(appId);
        sb.append(", ").append(applierId);
        sb.append(", ").append(hrId);
        sb.append(", ").append(interviewerId);
        sb.append(", ").append(interviewAddressId);
        sb.append(", ").append(interviewType);
        sb.append(", ").append(interviewRoundName);
        sb.append(", ").append(interviewFeedbackSheetId);
        sb.append(", ").append(appTplId);
        sb.append(", ").append(concreteOrder);
        sb.append(", ").append(interviewTemplateId);
        sb.append(", ").append(startTime);
        sb.append(", ").append(endTime);
        sb.append(", ").append(finished);
        sb.append(", ").append(passed);
        sb.append(", ").append(disable);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(contactName);
        sb.append(", ").append(contactPhone);

        sb.append(")");
        return sb.toString();
    }
}
