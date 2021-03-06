/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrTopic;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 雇主主题活动表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTopicRecord extends UpdatableRecordImpl<HrTopicRecord> implements Record10<Integer, Integer, String, String, String, Integer, Integer, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = -1388521035;

    /**
     * Setter for <code>hrdb.hr_topic.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_topic.company_id</code>. company.id, 部门ID
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.company_id</code>. company.id, 部门ID
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_topic.share_title</code>. 分享标题
     */
    public void setShareTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.share_title</code>. 分享标题
     */
    public String getShareTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_topic.share_logo</code>. 分享LOGO的相对路径
     */
    public void setShareLogo(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.share_logo</code>. 分享LOGO的相对路径
     */
    public String getShareLogo() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_topic.share_description</code>. 分享描述
     */
    public void setShareDescription(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.share_description</code>. 分享描述
     */
    public String getShareDescription() {
        return (String) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_topic.style_id</code>. config_sys_h5_style_tpl.id， 样式Id
     */
    public void setStyleId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.style_id</code>. config_sys_h5_style_tpl.id， 样式Id
     */
    public Integer getStyleId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_topic.creator</code>. hr_account.id
     */
    public void setCreator(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.creator</code>. hr_account.id
     */
    public Integer getCreator() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_topic.disable</code>. 是否有效  0：有效 1：无效
     */
    public void setDisable(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.disable</code>. 是否有效  0：有效 1：无效
     */
    public Byte getDisable() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_topic.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_topic.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_topic.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, String, String, String, Integer, Integer, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, String, String, String, Integer, Integer, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrTopic.HR_TOPIC.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrTopic.HR_TOPIC.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrTopic.HR_TOPIC.SHARE_TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrTopic.HR_TOPIC.SHARE_LOGO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return HrTopic.HR_TOPIC.SHARE_DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return HrTopic.HR_TOPIC.STYLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrTopic.HR_TOPIC.CREATOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return HrTopic.HR_TOPIC.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return HrTopic.HR_TOPIC.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return HrTopic.HR_TOPIC.UPDATE_TIME;
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
    public String value3() {
        return getShareTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getShareLogo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getShareDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getStyleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getCreator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value3(String value) {
        setShareTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value4(String value) {
        setShareLogo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value5(String value) {
        setShareDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value6(Integer value) {
        setStyleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value7(Integer value) {
        setCreator(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value8(Byte value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value9(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord value10(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTopicRecord values(Integer value1, Integer value2, String value3, String value4, String value5, Integer value6, Integer value7, Byte value8, Timestamp value9, Timestamp value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrTopicRecord
     */
    public HrTopicRecord() {
        super(HrTopic.HR_TOPIC);
    }

    /**
     * Create a detached, initialised HrTopicRecord
     */
    public HrTopicRecord(Integer id, Integer companyId, String shareTitle, String shareLogo, String shareDescription, Integer styleId, Integer creator, Byte disable, Timestamp createTime, Timestamp updateTime) {
        super(HrTopic.HR_TOPIC);

        set(0, id);
        set(1, companyId);
        set(2, shareTitle);
        set(3, shareLogo);
        set(4, shareDescription);
        set(5, styleId);
        set(6, creator);
        set(7, disable);
        set(8, createTime);
        set(9, updateTime);
    }
}
