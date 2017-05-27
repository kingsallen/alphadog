/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.analytics.tables.records;


import com.moseeker.baseorm.db.analytics.tables.WebPositionShare;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 转发效果统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WebPositionShareRecord extends UpdatableRecordImpl<WebPositionShareRecord> implements Record14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Timestamp> {

    private static final long serialVersionUID = 2089553762;

    /**
     * Setter for <code>analytics.web_position_share.id</code>. primary key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.id</code>. primary key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>analytics.web_position_share.company_id</code>. 公司ID, sys_company.id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.company_id</code>. 公司ID, sys_company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>analytics.web_position_share.pv</code>. page view
     */
    public void setPv(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.pv</code>. page view
     */
    public Integer getPv() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>analytics.web_position_share.uv</code>. user view
     */
    public void setUv(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.uv</code>. user view
     */
    public Integer getUv() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>analytics.web_position_share.application</code>. 申请量
     */
    public void setApplication(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.application</code>. 申请量
     */
    public Integer getApplication() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>analytics.web_position_share.share_pv</code>. 转发产生的pv
     */
    public void setSharePv(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.share_pv</code>. 转发产生的pv
     */
    public Integer getSharePv() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>analytics.web_position_share.employee_spv</code>. 员工转发产生的pv
     */
    public void setEmployeeSpv(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.employee_spv</code>. 员工转发产生的pv
     */
    public Integer getEmployeeSpv() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>analytics.web_position_share.share_uv</code>. 转发产生的uv
     */
    public void setShareUv(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.share_uv</code>. 转发产生的uv
     */
    public Integer getShareUv() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>analytics.web_position_share.employee_suv</code>. 员工转发产生的uv
     */
    public void setEmployeeSuv(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.employee_suv</code>. 员工转发产生的uv
     */
    public Integer getEmployeeSuv() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>analytics.web_position_share.share_application</code>. 推荐产生的申请量
     */
    public void setShareApplication(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.share_application</code>. 推荐产生的申请量
     */
    public Integer getShareApplication() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>analytics.web_position_share.employee_sapplication</code>. 员工推荐产生的申请量
     */
    public void setEmployeeSapplication(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.employee_sapplication</code>. 员工推荐产生的申请量
     */
    public Integer getEmployeeSapplication() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>analytics.web_position_share.share_appuser</code>. 推荐产生的申请人
     */
    public void setShareAppuser(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.share_appuser</code>. 推荐产生的申请人
     */
    public Integer getShareAppuser() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>analytics.web_position_share.employee_sappuser</code>. 员工推荐产生的申请人
     */
    public void setEmployeeSappuser(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.employee_sappuser</code>. 员工推荐产生的申请人
     */
    public Integer getEmployeeSappuser() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>analytics.web_position_share.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>analytics.web_position_share.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(13);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Timestamp> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Timestamp> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return WebPositionShare.WEB_POSITION_SHARE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return WebPositionShare.WEB_POSITION_SHARE.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return WebPositionShare.WEB_POSITION_SHARE.PV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return WebPositionShare.WEB_POSITION_SHARE.UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return WebPositionShare.WEB_POSITION_SHARE.APPLICATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return WebPositionShare.WEB_POSITION_SHARE.SHARE_PV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return WebPositionShare.WEB_POSITION_SHARE.EMPLOYEE_SPV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return WebPositionShare.WEB_POSITION_SHARE.SHARE_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return WebPositionShare.WEB_POSITION_SHARE.EMPLOYEE_SUV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return WebPositionShare.WEB_POSITION_SHARE.SHARE_APPLICATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return WebPositionShare.WEB_POSITION_SHARE.EMPLOYEE_SAPPLICATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return WebPositionShare.WEB_POSITION_SHARE.SHARE_APPUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return WebPositionShare.WEB_POSITION_SHARE.EMPLOYEE_SAPPUSER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return WebPositionShare.WEB_POSITION_SHARE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getPv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getApplication();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getSharePv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getEmployeeSpv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getShareUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getEmployeeSuv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getShareApplication();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getEmployeeSapplication();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getShareAppuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getEmployeeSappuser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value3(Integer value) {
        setPv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value4(Integer value) {
        setUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value5(Integer value) {
        setApplication(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value6(Integer value) {
        setSharePv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value7(Integer value) {
        setEmployeeSpv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value8(Integer value) {
        setShareUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value9(Integer value) {
        setEmployeeSuv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value10(Integer value) {
        setShareApplication(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value11(Integer value) {
        setEmployeeSapplication(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value12(Integer value) {
        setShareAppuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value13(Integer value) {
        setEmployeeSappuser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord value14(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPositionShareRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Integer value5, Integer value6, Integer value7, Integer value8, Integer value9, Integer value10, Integer value11, Integer value12, Integer value13, Timestamp value14) {
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
        value14(value14);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WebPositionShareRecord
     */
    public WebPositionShareRecord() {
        super(WebPositionShare.WEB_POSITION_SHARE);
    }

    /**
     * Create a detached, initialised WebPositionShareRecord
     */
    public WebPositionShareRecord(Integer id, Integer companyId, Integer pv, Integer uv, Integer application, Integer sharePv, Integer employeeSpv, Integer shareUv, Integer employeeSuv, Integer shareApplication, Integer employeeSapplication, Integer shareAppuser, Integer employeeSappuser, Timestamp createTime) {
        super(WebPositionShare.WEB_POSITION_SHARE);

        set(0, id);
        set(1, companyId);
        set(2, pv);
        set(3, uv);
        set(4, application);
        set(5, sharePv);
        set(6, employeeSpv);
        set(7, shareUv);
        set(8, employeeSuv);
        set(9, shareApplication);
        set(10, employeeSapplication);
        set(11, shareAppuser);
        set(12, employeeSappuser);
        set(13, createTime);
    }
}
