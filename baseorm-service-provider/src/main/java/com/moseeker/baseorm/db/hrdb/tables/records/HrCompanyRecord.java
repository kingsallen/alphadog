/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrCompany;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 公司表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyRecord extends UpdatableRecordImpl<HrCompanyRecord> implements Record22<Integer, Byte, String, String, Byte, String, Byte, String, String, String, String, String, String, Integer, Integer, Byte, Timestamp, Timestamp, Byte, String, String, Byte> {

    private static final long serialVersionUID = 110312599;

    /**
     * Setter for <code>hrdb.hr_company.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_company.type</code>. 公司区分(测试用:2,免费用户:1,企业用户:0)
     */
    public void setType(Byte value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.type</code>. 公司区分(测试用:2,免费用户:1,企业用户:0)
     */
    public Byte getType() {
        return (Byte) get(1);
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
    public void setScale(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.scale</code>. 公司规模, dict_constant.parent_code=1102
     */
    public Byte getScale() {
        return (Byte) get(4);
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
     * Setter for <code>hrdb.hr_company.property</code>. 公司性质 0:未填写 1:外商独资 3:国企 4:合资 5:民营公司 6:事业单位 7:上市公司 8:政府机关/非盈利机构 10:代表处 11:股份制企业 12:创业公司 13:其它
     */
    public void setProperty(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.property</code>. 公司性质 0:未填写 1:外商独资 3:国企 4:合资 5:民营公司 6:事业单位 7:上市公司 8:政府机关/非盈利机构 10:代表处 11:股份制企业 12:创业公司 13:其它
     */
    public Byte getProperty() {
        return (Byte) get(6);
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
    public void setParentId(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.parent_id</code>. 上级公司
     */
    public Integer getParentId() {
        return (Integer) get(13);
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
     * Setter for <code>hrdb.hr_company.disable</code>. 0:无效 1:有效, 删除子公司使用， 母公司目前没有禁用功能
     */
    public void setDisable(Byte value) {
        set(15, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.disable</code>. 0:无效 1:有效, 删除子公司使用， 母公司目前没有禁用功能
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
     * Setter for <code>hrdb.hr_company.source</code>.  添加来源 0:hr系统, 1:官网下载行业报告, 6:无线官网添加, 7:PC端 添加, 8:微信端添加, 9:PC导入, 10:微信端导入, 11 : 程序导入（和黄导入）
     */
    public void setSource(Byte value) {
        set(18, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.source</code>.  添加来源 0:hr系统, 1:官网下载行业报告, 6:无线官网添加, 7:PC端 添加, 8:微信端添加, 9:PC导入, 10:微信端导入, 11 : 程序导入（和黄导入）
     */
    public Byte getSource() {
        return (Byte) get(18);
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

    /**
     * Setter for <code>hrdb.hr_company.feature</code>. 公司福利特色， 由公司下的职位的福利特色每天跑脚本合并而来，目前供支付宝使用
     */
    public void setFeature(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.feature</code>. 公司福利特色， 由公司下的职位的福利特色每天跑脚本合并而来，目前供支付宝使用
     */
    public String getFeature() {
        return (String) get(20);
    }

    /**
     * Setter for <code>hrdb.hr_company.fortune</code>. 是否500强，0：不是 1：是
     */
    public void setFortune(Byte value) {
        set(21, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.fortune</code>. 是否500强，0：不是 1：是
     */
    public Byte getFortune() {
        return (Byte) get(21);
    }

    /**
     * Setter for <code>hrdb.hr_company.fortune_scale</code>. 五百强范围，1：世界 2：中国
     */
    public void setFortuneScale(Byte value) {
        set(22, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.fortune_scale</code>. 五百强范围，1：世界 2：中国
     */
    public Byte getFortuneScale() {
        return (Byte) get(22);
    }

    /**
     * Setter for <code>hrdb.hr_company.fortune_year</code>. 五百强评定年份
     */
    public void setFortuneYear(Date value) {
        set(23, value);
    }

    /**
     * Getter for <code>hrdb.hr_company.fortune_year</code>. 五百强评定年份
     */
    public Date getFortuneYear() {
        return (Date) get(23);
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
    public HrCompanyRecord(Integer id, Byte type, String name, String introduction, Byte scale, String address, Byte property, String industry, String homepage, String logo, String abbreviation, String impression, String banner, Integer parentId, Integer hraccountId, Byte disable, Timestamp createTime, Timestamp updateTime, Byte source, String slogan, String feature, Byte fortune, Byte fortuneScale, Date fortuneYear) {
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
        set(20, feature);
        set(21, fortune);
        set(22, fortuneScale);
        set(23, fortuneYear);
    }
}
