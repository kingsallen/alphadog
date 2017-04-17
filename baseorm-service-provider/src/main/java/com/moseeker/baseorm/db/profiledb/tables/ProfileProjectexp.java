/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.profiledb.tables;


import com.moseeker.baseorm.db.profiledb.Keys;
import com.moseeker.baseorm.db.profiledb.Profiledb;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;

import java.sql.Date;
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
 * Profile的项目经验
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileProjectexp extends TableImpl<ProfileProjectexpRecord> {

    private static final long serialVersionUID = -1741284397;

    /**
     * The reference instance of <code>profiledb.profile_projectexp</code>
     */
    public static final ProfileProjectexp PROFILE_PROJECTEXP = new ProfileProjectexp();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProfileProjectexpRecord> getRecordType() {
        return ProfileProjectexpRecord.class;
    }

    /**
     * The column <code>profiledb.profile_projectexp.id</code>. 主key
     */
    public final TableField<ProfileProjectexpRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>profiledb.profile_projectexp.profile_id</code>. profile.id
     */
    public final TableField<ProfileProjectexpRecord, Integer> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "profile.id");

    /**
     * The column <code>profiledb.profile_projectexp.start</code>. 起止时间-起 yyyy-mm-dd
     */
    public final TableField<ProfileProjectexpRecord, Date> START = createField("start", org.jooq.impl.SQLDataType.DATE, this, "起止时间-起 yyyy-mm-dd");

    /**
     * The column <code>profiledb.profile_projectexp.end</code>. 起止时间-止 yyyy-mm-dd
     */
    public final TableField<ProfileProjectexpRecord, Date> END = createField("end", org.jooq.impl.SQLDataType.DATE, this, "起止时间-止 yyyy-mm-dd");

    /**
     * The column <code>profiledb.profile_projectexp.end_until_now</code>. 是否至今 0：否 1：是
     */
    public final TableField<ProfileProjectexpRecord, Byte> END_UNTIL_NOW = createField("end_until_now", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否至今 0：否 1：是");

    /**
     * The column <code>profiledb.profile_projectexp.name</code>. 项目名称
     */
    public final TableField<ProfileProjectexpRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "项目名称");

    /**
     * The column <code>profiledb.profile_projectexp.company_name</code>. 公司名称
     */
    public final TableField<ProfileProjectexpRecord, String> COMPANY_NAME = createField("company_name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "公司名称");

    /**
     * The column <code>profiledb.profile_projectexp.is_it</code>. 是否IT项目, 0:没填写, 1:是, 2:否
     */
    public final TableField<ProfileProjectexpRecord, Byte> IS_IT = createField("is_it", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否IT项目, 0:没填写, 1:是, 2:否");

    /**
     * The column <code>profiledb.profile_projectexp.dev_tool</code>. 开发工具
     */
    public final TableField<ProfileProjectexpRecord, String> DEV_TOOL = createField("dev_tool", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "开发工具");

    /**
     * The column <code>profiledb.profile_projectexp.hardware</code>. 硬件环境
     */
    public final TableField<ProfileProjectexpRecord, String> HARDWARE = createField("hardware", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "硬件环境");

    /**
     * The column <code>profiledb.profile_projectexp.software</code>. 软件环境
     */
    public final TableField<ProfileProjectexpRecord, String> SOFTWARE = createField("software", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "软件环境");

    /**
     * The column <code>profiledb.profile_projectexp.url</code>. 项目网址
     */
    public final TableField<ProfileProjectexpRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "项目网址");

    /**
     * The column <code>profiledb.profile_projectexp.description</code>. 项目描述
     */
    public final TableField<ProfileProjectexpRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "项目描述");

    /**
     * The column <code>profiledb.profile_projectexp.role</code>. 项目角色
     */
    public final TableField<ProfileProjectexpRecord, String> ROLE = createField("role", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "项目角色");

    /**
     * The column <code>profiledb.profile_projectexp.responsibility</code>. 项目职责
     */
    public final TableField<ProfileProjectexpRecord, String> RESPONSIBILITY = createField("responsibility", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "项目职责");

    /**
     * The column <code>profiledb.profile_projectexp.achievement</code>. 项目业绩
     */
    public final TableField<ProfileProjectexpRecord, String> ACHIEVEMENT = createField("achievement", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "项目业绩");

    /**
     * The column <code>profiledb.profile_projectexp.member</code>. 项目成员
     */
    public final TableField<ProfileProjectexpRecord, String> MEMBER = createField("member", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "项目成员");

    /**
     * The column <code>profiledb.profile_projectexp.create_time</code>. 创建时间
     */
    public final TableField<ProfileProjectexpRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>profiledb.profile_projectexp.update_time</code>. 更新时间
     */
    public final TableField<ProfileProjectexpRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>profiledb.profile_projectexp</code> table reference
     */
    public ProfileProjectexp() {
        this("profile_projectexp", null);
    }

    /**
     * Create an aliased <code>profiledb.profile_projectexp</code> table reference
     */
    public ProfileProjectexp(String alias) {
        this(alias, PROFILE_PROJECTEXP);
    }

    private ProfileProjectexp(String alias, Table<ProfileProjectexpRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProfileProjectexp(String alias, Table<ProfileProjectexpRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Profile的项目经验");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Profiledb.PROFILEDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ProfileProjectexpRecord, Integer> getIdentity() {
        return Keys.IDENTITY_PROFILE_PROJECTEXP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProfileProjectexpRecord> getPrimaryKey() {
        return Keys.KEY_PROFILE_PROJECTEXP_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProfileProjectexpRecord>> getKeys() {
        return Arrays.<UniqueKey<ProfileProjectexpRecord>>asList(Keys.KEY_PROFILE_PROJECTEXP_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileProjectexp as(String alias) {
        return new ProfileProjectexp(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProfileProjectexp rename(String name) {
        return new ProfileProjectexp(name, null);
    }
}
