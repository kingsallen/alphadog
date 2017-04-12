/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * 团队信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTeamRecord extends UpdatableRecordImpl<HrTeamRecord> implements Record15<Integer, String, String, String, Integer, String, Integer, Timestamp, Timestamp, Integer, String, Integer, String, Integer, String> {

    private static final long serialVersionUID = -1551545176;

    /**
     * Setter for <code>hrdb.hr_team.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_team.name</code>. 团队/部门名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.name</code>. 团队/部门名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_team.summary</code>. 职能概述
     */
    public void setSummary(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.summary</code>. 职能概述
     */
    public String getSummary() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_team.description</code>. 团队介绍
     */
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.description</code>. 团队介绍
     */
    public String getDescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_team.show_order</code>. 团队显示顺序
     */
    public void setShowOrder(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.show_order</code>. 团队显示顺序
     */
    public Integer getShowOrder() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_team.jd_media</code>. 成员一天信息hr_media.id: [1, 23, 32]
     */
    public void setJdMedia(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.jd_media</code>. 成员一天信息hr_media.id: [1, 23, 32]
     */
    public String getJdMedia() {
        return (String) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_team.company_id</code>. 团队所在母公司
     */
    public void setCompanyId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.company_id</code>. 团队所在母公司
     */
    public Integer getCompanyId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_team.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_team.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_team.is_show</code>. 当前团队在列表等处是否显示, 0:不显示, 1:显示
     */
    public void setIsShow(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.is_show</code>. 当前团队在列表等处是否显示, 0:不显示, 1:显示
     */
    public Integer getIsShow() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_team.slogan</code>. 团队标语
     */
    public void setSlogan(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.slogan</code>. 团队标语
     */
    public String getSlogan() {
        return (String) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_team.res_id</code>. 团队主图片hr_resource.id
     */
    public void setResId(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.res_id</code>. 团队主图片hr_resource.id
     */
    public Integer getResId() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_team.team_detail</code>. 团队详情页配置hr_media.id: [1, 23, 32]
     */
    public void setTeamDetail(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.team_detail</code>. 团队详情页配置hr_media.id: [1, 23, 32]
     */
    public String getTeamDetail() {
        return (String) get(12);
    }

    /**
     * Setter for <code>hrdb.hr_team.disable</code>. 0是正常 1是删除
     */
    public void setDisable(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.disable</code>. 0是正常 1是删除
     */
    public Integer getDisable() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>hrdb.hr_team.sub_title</code>. 团队小标题
     */
    public void setSubTitle(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>hrdb.hr_team.sub_title</code>. 团队小标题
     */
    public String getSubTitle() {
        return (String) get(14);
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
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, String, String, String, Integer, String, Integer, Timestamp, Timestamp, Integer, String, Integer, String, Integer, String> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, String, String, String, Integer, String, Integer, Timestamp, Timestamp, Integer, String, Integer, String, Integer, String> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrTeam.HR_TEAM.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrTeam.HR_TEAM.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrTeam.HR_TEAM.SUMMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrTeam.HR_TEAM.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrTeam.HR_TEAM.SHOW_ORDER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return HrTeam.HR_TEAM.JD_MEDIA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrTeam.HR_TEAM.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrTeam.HR_TEAM.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return HrTeam.HR_TEAM.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return HrTeam.HR_TEAM.IS_SHOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return HrTeam.HR_TEAM.SLOGAN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return HrTeam.HR_TEAM.RES_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return HrTeam.HR_TEAM.TEAM_DETAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return HrTeam.HR_TEAM.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return HrTeam.HR_TEAM.SUB_TITLE;
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
    public String value15() {
        return getSubTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value3(String value) {
        setSummary(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value4(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value5(Integer value) {
        setShowOrder(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value6(String value) {
        setJdMedia(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value7(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value9(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value10(Integer value) {
        setIsShow(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value11(String value) {
        setSlogan(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value12(Integer value) {
        setResId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value13(String value) {
        setTeamDetail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value14(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord value15(String value) {
        setSubTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeamRecord values(Integer value1, String value2, String value3, String value4, Integer value5, String value6, Integer value7, Timestamp value8, Timestamp value9, Integer value10, String value11, Integer value12, String value13, Integer value14, String value15) {
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
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrTeamRecord
     */
    public HrTeamRecord() {
        super(HrTeam.HR_TEAM);
    }

    /**
     * Create a detached, initialised HrTeamRecord
     */
    public HrTeamRecord(Integer id, String name, String summary, String description, Integer showOrder, String jdMedia, Integer companyId, Timestamp createTime, Timestamp updateTime, Integer isShow, String slogan, Integer resId, String teamDetail, Integer disable, String subTitle) {
        super(HrTeam.HR_TEAM);

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
        set(14, subTitle);
    }
}
