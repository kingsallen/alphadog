/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class HrCompany extends TableImpl<HrCompanyRecord> {

    private static final long serialVersionUID = 963160144;

    /**
     * The reference instance of <code>hrdb.hr_company</code>
     */
    public static final HrCompany HR_COMPANY = new HrCompany();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrCompanyRecord> getRecordType() {
        return HrCompanyRecord.class;
    }

    /**
     * The column <code>hrdb.hr_company.id</code>.
     */
    public final TableField<HrCompanyRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_company.type</code>. 公司区分(其它:2,免费用户:1,企业用户:0)
     */
    public final TableField<HrCompanyRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.TINYINT)), this, "公司区分(其它:2,免费用户:1,企业用户:0)");

    /**
     * The column <code>hrdb.hr_company.name</code>. 公司注册名称
     */
    public final TableField<HrCompanyRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "公司注册名称");

    /**
     * The column <code>hrdb.hr_company.introduction</code>. 公司介绍
     */
    public final TableField<HrCompanyRecord, String> INTRODUCTION = createField("introduction", org.jooq.impl.SQLDataType.CLOB, this, "公司介绍");

    /**
     * The column <code>hrdb.hr_company.scale</code>. 公司规模, dict_constant.parent_code=1102
     */
    public final TableField<HrCompanyRecord, Byte> SCALE = createField("scale", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "公司规模, dict_constant.parent_code=1102");

    /**
     * The column <code>hrdb.hr_company.address</code>. 公司地址
     */
    public final TableField<HrCompanyRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "公司地址");

    /**
     * The column <code>hrdb.hr_company.property</code>. 公司性质 0:未填写 1:外商独资 3:国企 4:合资 5:民营公司 6:事业单位 7:上市公司 8:政府机关/非盈利机构 10:代表处 11:股份制企业 12:创业公司 13:其它
     */
    public final TableField<HrCompanyRecord, Byte> PROPERTY = createField("property", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "公司性质 0:未填写 1:外商独资 3:国企 4:合资 5:民营公司 6:事业单位 7:上市公司 8:政府机关/非盈利机构 10:代表处 11:股份制企业 12:创业公司 13:其它");

    /**
     * The column <code>hrdb.hr_company.industry</code>. 所属行业
     */
    public final TableField<HrCompanyRecord, String> INDUSTRY = createField("industry", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "所属行业");

    /**
     * The column <code>hrdb.hr_company.homepage</code>. 公司主页
     */
    public final TableField<HrCompanyRecord, String> HOMEPAGE = createField("homepage", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "公司主页");

    /**
     * The column <code>hrdb.hr_company.logo</code>. 公司logo的网络cdn地址
     */
    public final TableField<HrCompanyRecord, String> LOGO = createField("logo", org.jooq.impl.SQLDataType.CLOB, this, "公司logo的网络cdn地址");

    /**
     * The column <code>hrdb.hr_company.abbreviation</code>. 公司简称
     */
    public final TableField<HrCompanyRecord, String> ABBREVIATION = createField("abbreviation", org.jooq.impl.SQLDataType.VARCHAR.length(99).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "公司简称");

    /**
     * The column <code>hrdb.hr_company.impression</code>. json格式的企业印象
     */
    public final TableField<HrCompanyRecord, String> IMPRESSION = createField("impression", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "json格式的企业印象");

    /**
     * The column <code>hrdb.hr_company.banner</code>. json格式的企业banner
     */
    public final TableField<HrCompanyRecord, String> BANNER = createField("banner", org.jooq.impl.SQLDataType.VARCHAR.length(999).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "json格式的企业banner");

    /**
     * The column <code>hrdb.hr_company.parent_id</code>. 上级公司
     */
    public final TableField<HrCompanyRecord, Integer> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "上级公司");

    /**
     * The column <code>hrdb.hr_company.hraccount_id</code>. 公司联系人, hr_account.id
     */
    public final TableField<HrCompanyRecord, Integer> HRACCOUNT_ID = createField("hraccount_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "公司联系人, hr_account.id");

    /**
     * The column <code>hrdb.hr_company.disable</code>. 0:无效 1:有效, 删除子公司使用， 母公司目前没有禁用功能
     */
    public final TableField<HrCompanyRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.TINYINT)), this, "0:无效 1:有效, 删除子公司使用， 母公司目前没有禁用功能");

    /**
     * The column <code>hrdb.hr_company.create_time</code>. 创建时间
     */
    public final TableField<HrCompanyRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_company.update_time</code>. 更新时间
     */
    public final TableField<HrCompanyRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>hrdb.hr_company.source</code>. 添加来源 0:hr系统, 1:官网下载行业报告, 6:无线官网添加, 7:PC端 添加, 8:微信端添加, 9:PC导入, 10:微信端导入
     */
    public final TableField<HrCompanyRecord, Byte> SOURCE = createField("source", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "添加来源 0:hr系统, 1:官网下载行业报告, 6:无线官网添加, 7:PC端 添加, 8:微信端添加, 9:PC导入, 10:微信端导入");

    /**
     * The column <code>hrdb.hr_company.slogan</code>. 公司口号
     */
    public final TableField<HrCompanyRecord, String> SLOGAN = createField("slogan", org.jooq.impl.SQLDataType.VARCHAR.length(1024).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "公司口号");

    /**
     * The column <code>hrdb.hr_company.feature</code>. 公司福利特色， 由公司下的职位的福利特色每天跑脚本合并而来，目前供支付宝使用
     */
    public final TableField<HrCompanyRecord, String> FEATURE = createField("feature", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "公司福利特色， 由公司下的职位的福利特色每天跑脚本合并而来，目前供支付宝使用");

    /**
     * The column <code>hrdb.hr_company.is_top_500</code>. 是否世界500强，0：不是 1：是
     */
    public final TableField<HrCompanyRecord, Byte> IS_TOP_500 = createField("is_top_500", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否世界500强，0：不是 1：是");

    /**
     * Create a <code>hrdb.hr_company</code> table reference
     */
    public HrCompany() {
        this("hr_company", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_company</code> table reference
     */
    public HrCompany(String alias) {
        this(alias, HR_COMPANY);
    }

    private HrCompany(String alias, Table<HrCompanyRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrCompany(String alias, Table<HrCompanyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "公司表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Hrdb.HRDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<HrCompanyRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_COMPANY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrCompanyRecord> getPrimaryKey() {
        return Keys.KEY_HR_COMPANY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrCompanyRecord>> getKeys() {
        return Arrays.<UniqueKey<HrCompanyRecord>>asList(Keys.KEY_HR_COMPANY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompany as(String alias) {
        return new HrCompany(alias, this);
    }

    /**
     * Rename this table
     */
    public HrCompany rename(String name) {
        return new HrCompany(name, null);
    }
}
