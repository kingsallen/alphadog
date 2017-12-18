/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrTeamBackupChendi;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record14;
import org.jooq.Row14;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTeamBackupChendiRecord extends TableRecordImpl<HrTeamBackupChendiRecord> implements Record14<Integer, String, String, String, Integer, String, Integer, Timestamp, Timestamp, Integer, String, Integer, String, Integer> {

    private static final long serialVersionUID = 771338551;

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.name</code>. 团队/部门名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.name</code>. 团队/部门名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.summary</code>. 职能概述
     */
    public void setSummary(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.summary</code>. 职能概述
     */
    public String getSummary() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.description</code>. 团队介绍
     */
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.description</code>. 团队介绍
     */
    public String getDescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.show_order</code>. 团队显示顺序
     */
    public void setShowOrder(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.show_order</code>. 团队显示顺序
     */
    public Integer getShowOrder() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.jd_media</code>. 成员一天信息hr_media.id: [1, 23, 32]
     */
    public void setJdMedia(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.jd_media</code>. 成员一天信息hr_media.id: [1, 23, 32]
     */
    public String getJdMedia() {
        return (String) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.company_id</code>. 团队所在母公司
     */
    public void setCompanyId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.company_id</code>. 团队所在母公司
     */
    public Integer getCompanyId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.is_show</code>. 当前团队在列表等处是否显示, 0:不显示, 1:显示
     */
    public void setIsShow(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.is_show</code>. 当前团队在列表等处是否显示, 0:不显示, 1:显示
     */
    public Integer getIsShow() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.slogan</code>. 团队标语
     */
    public void setSlogan(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.slogan</code>. 团队标语
     */
    public String getSlogan() {
        return (String) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.res_id</code>. 团队主图片hr_resource.id
     */
    public void setResId(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.res_id</code>. 团队主图片hr_resource.id
     */
    public Integer getResId() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.team_detail</code>. 团队详情页配置hr_media.id: [1, 23, 32]
     */
    public void setTeamDetail(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.team_detail</code>. 团队详情页配置hr_media.id: [1, 23, 32]
     */
    public String getTeamDetail() {
        return (String) get(12);
    }

    /**
     * Setter for <code>hrdb.hr_team_backup_chendi.disable</code>. 0是正常 1是删除
     */
    public void setDisable(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>hrdb.hr_team_backup_chendi.disable</code>. 0是正常 1是删除
     */
    public Integer getDisable() {
        return (Integer) get(13);
    }

    // -------------------------------------------------------------------------
    // Record14 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, String, Integer, String, Integer, Timestamp, Timestamp, Integer, String, Integer, String, Integer> fieldsRow() {
        return (Row14) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row14<Integer, String, String, String, Integer, String, Integer, Timestamp, Timestamp, Integer, String, Integer, String, Integer> valuesRow() {
        return (Row14) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.SUMMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.SHOW_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.JD_MEDIA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.IS_SHOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.SLOGAN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.RES_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.TEAM_DETAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI.DISABLE;
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
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getSummary();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getShowOrder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getJdMedia();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getIsShow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getSlogan();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getResId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getTeamDetail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value3(String value) {
        setSummary(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value4(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value5(Integer value) {
        setShowOrder(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value6(String value) {
        setJdMedia(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value7(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value9(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value10(Integer value) {
        setIsShow(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value11(String value) {
        setSlogan(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value12(Integer value) {
        setResId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value13(String value) {
        setTeamDetail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord value14(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamBackupChendiRecord values(Integer value1, String value2, String value3, String value4, Integer value5, String value6, Integer value7, Timestamp value8, Timestamp value9, Integer value10, String value11, Integer value12, String value13, Integer value14) {
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
     * Create a detached HrTeamBackupChendiRecord
     */
    public HrTeamBackupChendiRecord() {
        super(HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI);
    }

    /**
     * Create a detached, initialised HrTeamBackupChendiRecord
     */
    public HrTeamBackupChendiRecord(Integer id, String name, String summary, String description, Integer showOrder, String jdMedia, Integer companyId, Timestamp createTime, Timestamp updateTime, Integer isShow, String slogan, Integer resId, String teamDetail, Integer disable) {
        super(HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI);

        set(0, id);
        set(1, name);
        set(2, summary);
        set(3, description);
        set(4, showOrder);
        set(5, jdMedia);
        set(6, companyId);
        set(7, createTime);
        set(8, updateTime);
        set(9, isShow);
        set(10, slogan);
        set(11, resId);
        set(12, teamDetail);
        set(13, disable);
    }
}
