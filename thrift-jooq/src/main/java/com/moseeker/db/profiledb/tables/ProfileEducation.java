/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.profiledb.tables;


import com.moseeker.db.profiledb.Keys;
import com.moseeker.db.profiledb.Profiledb;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;

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
 * Profile的教育经历
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileEducation extends TableImpl<ProfileEducationRecord> {

    private static final long serialVersionUID = -220425043;

    /**
     * The reference instance of <code>profiledb.profile_education</code>
     */
    public static final ProfileEducation PROFILE_EDUCATION = new ProfileEducation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProfileEducationRecord> getRecordType() {
        return ProfileEducationRecord.class;
    }

    /**
     * The column <code>profiledb.profile_education.id</code>. 主key
     */
    public final TableField<ProfileEducationRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>profiledb.profile_education.profile_id</code>. profile.id
     */
    public final TableField<ProfileEducationRecord, Integer> PROFILE_ID = createField("profile_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "profile.id");

    /**
     * The column <code>profiledb.profile_education.start</code>. 起止时间-起 yyyy-mm-dd
     */
    public final TableField<ProfileEducationRecord, Date> START = createField("start", org.jooq.impl.SQLDataType.DATE, this, "起止时间-起 yyyy-mm-dd");

    /**
     * The column <code>profiledb.profile_education.end</code>. 起止时间-止 yyyy-mm-dd
     */
    public final TableField<ProfileEducationRecord, Date> END = createField("end", org.jooq.impl.SQLDataType.DATE, this, "起止时间-止 yyyy-mm-dd");

    /**
     * The column <code>profiledb.profile_education.end_until_now</code>. 是否至今 0：否 1：是
     */
    public final TableField<ProfileEducationRecord, Byte> END_UNTIL_NOW = createField("end_until_now", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否至今 0：否 1：是");

    /**
     * The column <code>profiledb.profile_education.college_code</code>. 院校字典编码
     */
    public final TableField<ProfileEducationRecord, Integer> COLLEGE_CODE = createField("college_code", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "院校字典编码");

    /**
     * The column <code>profiledb.profile_education.college_name</code>. 院校名称
     */
    public final TableField<ProfileEducationRecord, String> COLLEGE_NAME = createField("college_name", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "院校名称");

    /**
     * The column <code>profiledb.profile_education.college_logo</code>. 院校LOGO, 用户上传
     */
    public final TableField<ProfileEducationRecord, String> COLLEGE_LOGO = createField("college_logo", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "院校LOGO, 用户上传");

    /**
     * The column <code>profiledb.profile_education.major_code</code>. 专业字典编码
     */
    public final TableField<ProfileEducationRecord, String> MAJOR_CODE = createField("major_code", org.jooq.impl.SQLDataType.CHAR.length(6).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.CHAR)), this, "专业字典编码");

    /**
     * The column <code>profiledb.profile_education.major_name</code>. 专业名称
     */
    public final TableField<ProfileEducationRecord, String> MAJOR_NAME = createField("major_name", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "专业名称");

    /**
     * The column <code>profiledb.profile_education.degree</code>. 学历 0:未选择 1: 初中及以下, 2:中专, 3:高中, 4: 大专, 5: 本科, 6: 硕士, 7: 博士, 8:博士以上, 9:其他
     */
    public final TableField<ProfileEducationRecord, Byte> DEGREE = createField("degree", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "学历 0:未选择 1: 初中及以下, 2:中专, 3:高中, 4: 大专, 5: 本科, 6: 硕士, 7: 博士, 8:博士以上, 9:其他");

    /**
     * The column <code>profiledb.profile_education.description</code>. 教育描述
     */
    public final TableField<ProfileEducationRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "教育描述");

    /**
     * The column <code>profiledb.profile_education.is_full</code>. 是否全日制 0:没填写, 1:是, 2:否
     */
    public final TableField<ProfileEducationRecord, Byte> IS_FULL = createField("is_full", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否全日制 0:没填写, 1:是, 2:否");

    /**
     * The column <code>profiledb.profile_education.is_unified</code>. 是否统招 0:没填写, 1:是, 2:否
     */
    public final TableField<ProfileEducationRecord, Byte> IS_UNIFIED = createField("is_unified", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否统招 0:没填写, 1:是, 2:否");

    /**
     * The column <code>profiledb.profile_education.is_study_abroad</code>. 是否海外学习经历 0:没填写, 1:是, 2:否
     */
    public final TableField<ProfileEducationRecord, Byte> IS_STUDY_ABROAD = createField("is_study_abroad", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否海外学习经历 0:没填写, 1:是, 2:否");

    /**
     * The column <code>profiledb.profile_education.study_abroad_country</code>. 海外留学国家
     */
    public final TableField<ProfileEducationRecord, String> STUDY_ABROAD_COUNTRY = createField("study_abroad_country", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "海外留学国家");

    /**
     * The column <code>profiledb.profile_education.create_time</code>. 创建时间
     */
    public final TableField<ProfileEducationRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>profiledb.profile_education.update_time</code>. 更新时间
     */
    public final TableField<ProfileEducationRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>profiledb.profile_education</code> table reference
     */
    public ProfileEducation() {
        this("profile_education", null);
    }

    /**
     * Create an aliased <code>profiledb.profile_education</code> table reference
     */
    public ProfileEducation(String alias) {
        this(alias, PROFILE_EDUCATION);
    }

    private ProfileEducation(String alias, Table<ProfileEducationRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProfileEducation(String alias, Table<ProfileEducationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Profile的教育经历");
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
    public Identity<ProfileEducationRecord, Integer> getIdentity() {
        return Keys.IDENTITY_PROFILE_EDUCATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProfileEducationRecord> getPrimaryKey() {
        return Keys.KEY_PROFILE_EDUCATION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProfileEducationRecord>> getKeys() {
        return Arrays.<UniqueKey<ProfileEducationRecord>>asList(Keys.KEY_PROFILE_EDUCATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileEducation as(String alias) {
        return new ProfileEducation(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProfileEducation rename(String name) {
        return new ProfileEducation(name, null);
    }
}
