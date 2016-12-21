/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;
import org.jooq.types.UShort;


/**
 * 公司级别的配置信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyConfRecord extends UpdatableRecordImpl<HrCompanyConfRecord> implements Record13<UInteger, Integer, Integer, String, Timestamp, Timestamp, String, String, String, String, UShort, String, String> {

    private static final long serialVersionUID = 1634268598;

    /**
     * Setter for <code>hrdb.hr_company_conf.company_id</code>.
     */
    public void setCompanyId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.company_id</code>.
     */
    public UInteger getCompanyId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.theme_id</code>. sys_theme id
     */
    public void setThemeId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.theme_id</code>. sys_theme id
     */
    public Integer getThemeId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.hb_throttle</code>. 全局每人每次红包活动可以获得的红包金额上限
     */
    public void setHbThrottle(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.hb_throttle</code>. 全局每人每次红包活动可以获得的红包金额上限
     */
    public Integer getHbThrottle() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.app_reply</code>. 申请提交成功回复信息
     */
    public void setAppReply(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.app_reply</code>. 申请提交成功回复信息
     */
    public String getAppReply() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.employee_binding</code>. 员工认证自定义文案
     */
    public void setEmployeeBinding(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.employee_binding</code>. 员工认证自定义文案
     */
    public String getEmployeeBinding() {
        return (String) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.recommend_presentee</code>. 推荐候选人自定义文案
     */
    public void setRecommendPresentee(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.recommend_presentee</code>. 推荐候选人自定义文案
     */
    public String getRecommendPresentee() {
        return (String) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.recommend_success</code>. 推荐成功自定义文案
     */
    public void setRecommendSuccess(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.recommend_success</code>. 推荐成功自定义文案
     */
    public String getRecommendSuccess() {
        return (String) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.forward_message</code>. 转发职位自定义文案
     */
    public void setForwardMessage(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.forward_message</code>. 转发职位自定义文案
     */
    public String getForwardMessage() {
        return (String) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.application_count_limit</code>. 一个人在一个公司下每月申请次数限制
     */
    public void setApplicationCountLimit(UShort value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.application_count_limit</code>. 一个人在一个公司下每月申请次数限制
     */
    public UShort getApplicationCountLimit() {
        return (UShort) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.job_occupation</code>. 自定义字段名称
     */
    public void setJobOccupation(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.job_occupation</code>. 自定义字段名称
     */
    public String getJobOccupation() {
        return (String) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.job_custom_title</code>. 职位自定义字段标题
     */
    public void setJobCustomTitle(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.job_custom_title</code>. 职位自定义字段标题
     */
    public String getJobCustomTitle() {
        return (String) get(12);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UInteger> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<UInteger, Integer, Integer, String, Timestamp, Timestamp, String, String, String, String, UShort, String, String> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<UInteger, Integer, Integer, String, Timestamp, Timestamp, String, String, String, String, UShort, String, String> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return HrCompanyConf.HR_COMPANY_CONF.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrCompanyConf.HR_COMPANY_CONF.THEME_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrCompanyConf.HR_COMPANY_CONF.HB_THROTTLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrCompanyConf.HR_COMPANY_CONF.APP_REPLY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HrCompanyConf.HR_COMPANY_CONF.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrCompanyConf.HR_COMPANY_CONF.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return HrCompanyConf.HR_COMPANY_CONF.EMPLOYEE_BINDING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return HrCompanyConf.HR_COMPANY_CONF.RECOMMEND_PRESENTEE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return HrCompanyConf.HR_COMPANY_CONF.RECOMMEND_SUCCESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return HrCompanyConf.HR_COMPANY_CONF.FORWARD_MESSAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UShort> field11() {
        return HrCompanyConf.HR_COMPANY_CONF.APPLICATION_COUNT_LIMIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return HrCompanyConf.HR_COMPANY_CONF.JOB_OCCUPATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return HrCompanyConf.HR_COMPANY_CONF.JOB_CUSTOM_TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getThemeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getHbThrottle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getAppReply();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getEmployeeBinding();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getRecommendPresentee();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getRecommendSuccess();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getForwardMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UShort value11() {
        return getApplicationCountLimit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getJobOccupation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getJobCustomTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value1(UInteger value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value2(Integer value) {
        setThemeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value3(Integer value) {
        setHbThrottle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value4(String value) {
        setAppReply(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value7(String value) {
        setEmployeeBinding(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value8(String value) {
        setRecommendPresentee(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value9(String value) {
        setRecommendSuccess(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value10(String value) {
        setForwardMessage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value11(UShort value) {
        setApplicationCountLimit(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value12(String value) {
        setJobOccupation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord value13(String value) {
        setJobCustomTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyConfRecord values(UInteger value1, Integer value2, Integer value3, String value4, Timestamp value5, Timestamp value6, String value7, String value8, String value9, String value10, UShort value11, String value12, String value13) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrCompanyConfRecord
     */
    public HrCompanyConfRecord() {
        super(HrCompanyConf.HR_COMPANY_CONF);
    }

    /**
     * Create a detached, initialised HrCompanyConfRecord
     */
    public HrCompanyConfRecord(UInteger companyId, Integer themeId, Integer hbThrottle, String appReply, Timestamp createTime, Timestamp updateTime, String employeeBinding, String recommendPresentee, String recommendSuccess, String forwardMessage, UShort applicationCountLimit, String jobOccupation, String jobCustomTitle) {
        super(HrCompanyConf.HR_COMPANY_CONF);

        set(0, companyId);
        set(1, themeId);
        set(2, hbThrottle);
        set(3, appReply);
        set(4, createTime);
        set(5, updateTime);
        set(6, employeeBinding);
        set(7, recommendPresentee);
        set(8, recommendSuccess);
        set(9, forwardMessage);
        set(10, applicationCountLimit);
        set(11, jobOccupation);
        set(12, jobCustomTitle);
    }
}