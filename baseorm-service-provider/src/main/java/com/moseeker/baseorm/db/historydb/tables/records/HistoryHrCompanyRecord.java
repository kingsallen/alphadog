/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.records;


import com.moseeker.baseorm.db.historydb.tables.HistoryHrCompany;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record19;
import org.jooq.Row19;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 公司表归档表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryHrCompanyRecord extends UpdatableRecordImpl<HistoryHrCompanyRecord> implements Record19<Integer, Byte, String, String, Byte, String, Byte, String, String, String, String, String, String, Integer, Integer, Byte, Timestamp, Timestamp, Byte> {

    private static final long serialVersionUID = -1623446988;

    /**
     * Setter for <code>historydb.history_hr_company.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>historydb.history_hr_company.type</code>. 公司区分(其它:2,免费用户:1,企业用户:0)
     */
    public void setType(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.type</code>. 公司区分(其它:2,免费用户:1,企业用户:0)
     */
    public Byte getType() {
        return (Byte) get(1);
    }

    /**
     * Setter for <code>historydb.history_hr_company.name</code>. 公司注册名称
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.name</code>. 公司注册名称
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>historydb.history_hr_company.introduction</code>. 公司介绍
     */
    public void setIntroduction(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.introduction</code>. 公司介绍
     */
    public String getIntroduction() {
        return (String) get(3);
    }

    /**
     * Setter for <code>historydb.history_hr_company.scale</code>. 公司规模, dict_constant.parent_code=1102
     */
    public void setScale(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.scale</code>. 公司规模, dict_constant.parent_code=1102
     */
    public Byte getScale() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>historydb.history_hr_company.address</code>. 公司地址
     */
    public void setAddress(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.address</code>. 公司地址
     */
    public String getAddress() {
        return (String) get(5);
    }

    /**
     * Setter for <code>historydb.history_hr_company.property</code>. 公司性质 0:没选择 1:国有 2:三资 3:集体 4:私有
     */
    public void setProperty(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.property</code>. 公司性质 0:没选择 1:国有 2:三资 3:集体 4:私有
     */
    public Byte getProperty() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>historydb.history_hr_company.industry</code>. 所属行业
     */
    public void setIndustry(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.industry</code>. 所属行业
     */
    public String getIndustry() {
        return (String) get(7);
    }

    /**
     * Setter for <code>historydb.history_hr_company.homepage</code>. 公司主页
     */
    public void setHomepage(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.homepage</code>. 公司主页
     */
    public String getHomepage() {
        return (String) get(8);
    }

    /**
     * Setter for <code>historydb.history_hr_company.logo</code>. 公司logo的网络cdn地址
     */
    public void setLogo(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.logo</code>. 公司logo的网络cdn地址
     */
    public String getLogo() {
        return (String) get(9);
    }

    /**
     * Setter for <code>historydb.history_hr_company.abbreviation</code>. 公司简称
     */
    public void setAbbreviation(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.abbreviation</code>. 公司简称
     */
    public String getAbbreviation() {
        return (String) get(10);
    }

    /**
     * Setter for <code>historydb.history_hr_company.impression</code>. json格式的企业印象
     */
    public void setImpression(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.impression</code>. json格式的企业印象
     */
    public String getImpression() {
        return (String) get(11);
    }

    /**
     * Setter for <code>historydb.history_hr_company.banner</code>. json格式的企业banner
     */
    public void setBanner(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.banner</code>. json格式的企业banner
     */
    public String getBanner() {
        return (String) get(12);
    }

    /**
     * Setter for <code>historydb.history_hr_company.parent_id</code>. 上级公司
     */
    public void setParentId(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.parent_id</code>. 上级公司
     */
    public Integer getParentId() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>historydb.history_hr_company.hraccount_id</code>. 公司联系人, hr_account.id
     */
    public void setHraccountId(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.hraccount_id</code>. 公司联系人, hr_account.id
     */
    public Integer getHraccountId() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>historydb.history_hr_company.disable</code>. 0:无效 1:有效, 删除子公司使用， 母公司目前没有禁用功能
     */
    public void setDisable(Byte value) {
        set(15, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.disable</code>. 0:无效 1:有效, 删除子公司使用， 母公司目前没有禁用功能
     */
    public Byte getDisable() {
        return (Byte) get(15);
    }

    /**
     * Setter for <code>historydb.history_hr_company.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(16, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(16);
    }

    /**
     * Setter for <code>historydb.history_hr_company.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(17, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(17);
    }

    /**
     * Setter for <code>historydb.history_hr_company.source</code>. 添加来源 {"0":"hr系统", "9":"profile添加"}
     */
    public void setSource(Byte value) {
        set(18, value);
    }

    /**
     * Getter for <code>historydb.history_hr_company.source</code>. 添加来源 {"0":"hr系统", "9":"profile添加"}
     */
    public Byte getSource() {
        return (Byte) get(18);
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
    // Record19 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, Byte, String, String, Byte, String, Byte, String, String, String, String, String, String, Integer, Integer, Byte, Timestamp, Timestamp, Byte> fieldsRow() {
        return (Row19) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row19<Integer, Byte, String, String, Byte, String, Byte, String, String, String, String, String, String, Integer, Integer, Byte, Timestamp, Timestamp, Byte> valuesRow() {
        return (Row19) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field2() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.INTRODUCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.SCALE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.PROPERTY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.INDUSTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.HOMEPAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.LOGO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.ABBREVIATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.IMPRESSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.BANNER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.HRACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field16() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field17() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field18() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field19() {
        return HistoryHrCompany.HISTORY_HR_COMPANY.SOURCE;
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
    public Byte value2() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getIntroduction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getScale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getProperty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getIndustry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getHomepage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getLogo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getAbbreviation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getImpression();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getBanner();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getParentId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value15() {
        return getHraccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value16() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value17() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value18() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value19() {
        return getSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value2(Byte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value4(String value) {
        setIntroduction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value5(Byte value) {
        setScale(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value6(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value7(Byte value) {
        setProperty(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value8(String value) {
        setIndustry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value9(String value) {
        setHomepage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value10(String value) {
        setLogo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value11(String value) {
        setAbbreviation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value12(String value) {
        setImpression(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value13(String value) {
        setBanner(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value14(Integer value) {
        setParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value15(Integer value) {
        setHraccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value16(Byte value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value17(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value18(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord value19(Byte value) {
        setSource(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryHrCompanyRecord values(Integer value1, Byte value2, String value3, String value4, Byte value5, String value6, Byte value7, String value8, String value9, String value10, String value11, String value12, String value13, Integer value14, Integer value15, Byte value16, Timestamp value17, Timestamp value18, Byte value19) {
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
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HistoryHrCompanyRecord
     */
    public HistoryHrCompanyRecord() {
        super(HistoryHrCompany.HISTORY_HR_COMPANY);
    }

    /**
     * Create a detached, initialised HistoryHrCompanyRecord
     */
    public HistoryHrCompanyRecord(Integer id, Byte type, String name, String introduction, Byte scale, String address, Byte property, String industry, String homepage, String logo, String abbreviation, String impression, String banner, Integer parentId, Integer hraccountId, Byte disable, Timestamp createTime, Timestamp updateTime, Byte source) {
        super(HistoryHrCompany.HISTORY_HR_COMPANY);

        set(0, id);
        set(1, type);
        set(2, name);
        set(3, introduction);
        set(4, scale);
        set(5, address);
        set(6, property);
        set(7, industry);
        set(8, homepage);
        set(9, logo);
        set(10, abbreviation);
        set(11, impression);
        set(12, banner);
        set(13, parentId);
        set(14, hraccountId);
        set(15, disable);
        set(16, createTime);
        set(17, updateTime);
        set(18, source);
    }
}
