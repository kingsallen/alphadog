/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrRuleUniqueStatistics;

import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 微信图文传播去重信息统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrRuleUniqueStatisticsRecord extends UpdatableRecordImpl<HrRuleUniqueStatisticsRecord> implements Record8<Integer, Integer, String, Integer, Integer, Integer, Date, Integer> {

    private static final long serialVersionUID = 1177088594;

    /**
     * Setter for <code>hrdb.hr_rule_unique_statistics.id</code>. primary key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_rule_unique_statistics.id</code>. primary key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_rule_unique_statistics.wxrule_id</code>. wx_rule.id
     */
    public void setWxruleId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_rule_unique_statistics.wxrule_id</code>. wx_rule.id
     */
    public Integer getWxruleId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_rule_unique_statistics.menu_name</code>. 菜单名称
     */
    public void setMenuName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_rule_unique_statistics.menu_name</code>. 菜单名称
     */
    public String getMenuName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_rule_unique_statistics.type</code>. 0: wx_rule, 1: menu
     */
    public void setType(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_rule_unique_statistics.type</code>. 0: wx_rule, 1: menu
     */
    public Integer getType() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_rule_unique_statistics.company_id</code>. company.id
     */
    public void setCompanyId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_rule_unique_statistics.company_id</code>. company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_rule_unique_statistics.view_num_uv</code>. 浏览人数
     */
    public void setViewNumUv(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_rule_unique_statistics.view_num_uv</code>. 浏览人数
     */
    public Integer getViewNumUv() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_rule_unique_statistics.create_date</code>. 创建日期
     */
    public void setCreateDate(Date value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_rule_unique_statistics.create_date</code>. 创建日期
     */
    public Date getCreateDate() {
        return (Date) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_rule_unique_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
     */
    public void setInfoType(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_rule_unique_statistics.info_type</code>. 0: 日数据，1：周数据，2：月数据
     */
    public Integer getInfoType() {
        return (Integer) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, Integer, Integer, Integer, Date, Integer> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, Integer, Integer, Integer, Date, Integer> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.WXRULE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.MENU_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.VIEW_NUM_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field7() {
        return HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.CREATE_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.INFO_TYPE;
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
        return getWxruleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getMenuName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getViewNumUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value7() {
        return getCreateDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getInfoType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatisticsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatisticsRecord value2(Integer value) {
        setWxruleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatisticsRecord value3(String value) {
        setMenuName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatisticsRecord value4(Integer value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatisticsRecord value5(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatisticsRecord value6(Integer value) {
        setViewNumUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatisticsRecord value7(Date value) {
        setCreateDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatisticsRecord value8(Integer value) {
        setInfoType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrRuleUniqueStatisticsRecord values(Integer value1, Integer value2, String value3, Integer value4, Integer value5, Integer value6, Date value7, Integer value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrRuleUniqueStatisticsRecord
     */
    public HrRuleUniqueStatisticsRecord() {
        super(HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS);
    }

    /**
     * Create a detached, initialised HrRuleUniqueStatisticsRecord
     */
    public HrRuleUniqueStatisticsRecord(Integer id, Integer wxruleId, String menuName, Integer type, Integer companyId, Integer viewNumUv, Date createDate, Integer infoType) {
        super(HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS);

        set(0, id);
        set(1, wxruleId);
        set(2, menuName);
        set(3, type);
        set(4, companyId);
        set(5, viewNumUv);
        set(6, createDate);
        set(7, infoType);
    }
}
