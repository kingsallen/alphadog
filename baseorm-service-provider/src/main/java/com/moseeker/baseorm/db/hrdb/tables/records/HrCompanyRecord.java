/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrCompany;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record20;
import org.jooq.Row20;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;


/**
 * 公司表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyRecord extends UpdatableRecordImpl<HrCompanyRecord> implements Record20<UInteger, UByte, String, String, UByte, String, UByte, String, String, String, String, String, String, UInteger, Integer, Byte, Timestamp, Timestamp, UByte, String> {

    private static final long serialVersionUID = 1498452664;

    /**
     * Setter for <code>hrdb.hr_company.id</code>.
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.id</code>.
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_company.type</code>. 公司区分(其它:2,免费用户:1,企业用户:0)
     */
    public void setType(UByte value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.type</code>. 公司区分(其它:2,免费用户:1,企业用户:0)
     */
    public UByte getType() {
        return (UByte) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_company.name</code>. 公司注册名称
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.name</code>. 公司注册名称
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_company.introduction</code>. 公司介绍
     */
    public void setIntroduction(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.introduction</code>. 公司介绍
     */
    public String getIntroduction() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_company.scale</code>. 公司规模, dict_constant.parent_code=1102
     */
    public void setScale(UByte value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.scale</code>. 公司规模, dict_constant.parent_code=1102
     */
    public UByte getScale() {
        return (UByte) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_company.address</code>. 公司地址
     */
    public void setAddress(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.address</code>. 公司地址
     */
    public String getAddress() {
        return (String) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_company.property</code>. 公司性质 0:没选择 1:国有 2:三资 3:集体 4:私有
     */
    public void setProperty(UByte value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.property</code>. 公司性质 0:没选择 1:国有 2:三资 3:集体 4:私有
     */
    public UByte getProperty() {
        return (UByte) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_company.industry</code>. 所属行业
     */
    public void setIndustry(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.industry</code>. 所属行业
     */
    public String getIndustry() {
        return (String) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_company.homepage</code>. 公司主页
     */
    public void setHomepage(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.homepage</code>. 公司主页
     */
    public String getHomepage() {
        return (String) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_company.logo</code>. 公司logo的网络cdn地址
     */
    public void setLogo(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.logo</code>. 公司logo的网络cdn地址
     */
    public String getLogo() {
        return (String) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_company.abbreviation</code>. 公司简称
     */
    public void setAbbreviation(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.abbreviation</code>. 公司简称
     */
    public String getAbbreviation() {
        return (String) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_company.impression</code>. json格式的企业印象
     */
    public void setImpression(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.impression</code>. json格式的企业印象
     */
    public String getImpression() {
        return (String) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_company.banner</code>. json格式的企业banner
     */
    public void setBanner(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.banner</code>. json格式的企业banner
     */
    public String getBanner() {
        return (String) get(12);
    }

    /**
     * Setter for <code>hrdb.hr_company.parent_id</code>. 上级公司
     */
    public void setParentId(UInteger value) {
        set(13, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.parent_id</code>. 上级公司
     */
    public UInteger getParentId() {
        return (UInteger) get(13);
    }

    /**
     * Setter for <code>hrdb.hr_company.hraccount_id</code>. 公司联系人, hr_account.id
     */
    public void setHraccountId(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.hraccount_id</code>. 公司联系人, hr_account.id
     */
    public Integer getHraccountId() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>hrdb.hr_company.disable</code>. 0:无效 1:有效
     */
    public void setDisable(Byte value) {
        set(15, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.disable</code>. 0:无效 1:有效
     */
    public Byte getDisable() {
        return (Byte) get(15);
    }

    /**
     * Setter for <code>hrdb.hr_company.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(16, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(16);
    }

    /**
     * Setter for <code>hrdb.hr_company.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(17, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(17);
    }

    /**
     * Setter for <code>hrdb.hr_company.source</code>. 添加来源 {"0":"hr系统", "8":"微信端添加" "9":"profile添加"}
     */
    public void setSource(UByte value) {
        set(18, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.source</code>. 添加来源 {"0":"hr系统", "8":"微信端添加" "9":"profile添加"}
     */
    public UByte getSource() {
        return (UByte) get(18);
    }

    /**
     * Setter for <code>hrdb.hr_company.slogan</code>. 公司口号
     */
    public void setSlogan(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.slogan</code>. 公司口号
     */
    public String getSlogan() {
        return (String) get(19);
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
    // Record20 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row20<UInteger, UByte, String, String, UByte, String, UByte, String, String, String, String, String, String, UInteger, Integer, Byte, Timestamp, Timestamp, UByte, String> fieldsRow() {
        return (Row20) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row20<UInteger, UByte, String, String, UByte, String, UByte, String, String, String, String, String, String, UInteger, Integer, Byte, Timestamp, Timestamp, UByte, String> valuesRow() {
        return (Row20) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return HrCompany.HR_COMPANY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UByte> field2() {
        return HrCompany.HR_COMPANY.TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrCompany.HR_COMPANY.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrCompany.HR_COMPANY.INTRODUCTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UByte> field5() {
        return HrCompany.HR_COMPANY.SCALE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return HrCompany.HR_COMPANY.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UByte> field7() {
        return HrCompany.HR_COMPANY.PROPERTY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return HrCompany.HR_COMPANY.INDUSTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return HrCompany.HR_COMPANY.HOMEPAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return HrCompany.HR_COMPANY.LOGO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return HrCompany.HR_COMPANY.ABBREVIATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return HrCompany.HR_COMPANY.IMPRESSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return HrCompany.HR_COMPANY.BANNER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field14() {
        return HrCompany.HR_COMPANY.PARENT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return HrCompany.HR_COMPANY.HRACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field16() {
        return HrCompany.HR_COMPANY.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field17() {
        return HrCompany.HR_COMPANY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field18() {
        return HrCompany.HR_COMPANY.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UByte> field19() {
        return HrCompany.HR_COMPANY.SOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field20() {
        return HrCompany.HR_COMPANY.SLOGAN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UByte value2() {
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
    public UByte value5() {
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
    public UByte value7() {
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
    public UInteger value14() {
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
    public UByte value19() {
        return getSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value20() {
        return getSlogan();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value2(UByte value) {
        setType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value4(String value) {
        setIntroduction(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value5(UByte value) {
        setScale(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value6(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value7(UByte value) {
        setProperty(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value8(String value) {
        setIndustry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value9(String value) {
        setHomepage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value10(String value) {
        setLogo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value11(String value) {
        setAbbreviation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value12(String value) {
        setImpression(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value13(String value) {
        setBanner(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value14(UInteger value) {
        setParentId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value15(Integer value) {
        setHraccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value16(Byte value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value17(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value18(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value19(UByte value) {
        setSource(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord value20(String value) {
        setSlogan(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyRecord values(UInteger value1, UByte value2, String value3, String value4, UByte value5, String value6, UByte value7, String value8, String value9, String value10, String value11, String value12, String value13, UInteger value14, Integer value15, Byte value16, Timestamp value17, Timestamp value18, UByte value19, String value20) {
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
        value20(value20);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrCompanyRecord
     */
    public HrCompanyRecord() {
        super(HrCompany.HR_COMPANY);
    }

    /**
     * Create a detached, initialised HrCompanyRecord
     */
    public HrCompanyRecord(UInteger id, UByte type, String name, String introduction, UByte scale, String address, UByte property, String industry, String homepage, String logo, String abbreviation, String impression, String banner, UInteger parentId, Integer hraccountId, Byte disable, Timestamp createTime, Timestamp updateTime, UByte source, String slogan) {
        super(HrCompany.HR_COMPANY);

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
        set(19, slogan);
    }
}