/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables;


import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.tables.records.UserProfileJobapplyRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * VIEW
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserProfileJobapply extends TableImpl<UserProfileJobapplyRecord> {

    private static final long serialVersionUID = 2061732227;

    /**
     * The reference instance of <code>jobdb.user_profile_jobapply</code>
     */
    public static final UserProfileJobapply USER_PROFILE_JOBAPPLY = new UserProfileJobapply();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserProfileJobapplyRecord> getRecordType() {
        return UserProfileJobapplyRecord.class;
    }

    /**
     * The column <code>jobdb.user_profile_jobapply.用户ID</code>. 主key
     */
    public final TableField<UserProfileJobapplyRecord, Integer> 用户ID = createField("用户ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "主key");

    /**
     * The column <code>jobdb.user_profile_jobapply.同上1</code>. sys_user.id, 用户ID
     */
    public final TableField<UserProfileJobapplyRecord, Integer> 同上1 = createField("同上1", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sys_user.id, 用户ID");

    /**
     * The column <code>jobdb.user_profile_jobapply.同上2</code>. 用户ID
     */
    public final TableField<UserProfileJobapplyRecord, Integer> 同上2 = createField("同上2", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "用户ID");

    /**
     * The column <code>jobdb.user_profile_jobapply.投递职位jobnumer</code>. 职位编号
     */
    public final TableField<UserProfileJobapplyRecord, String> 投递职位JOBNUMER = createField("投递职位jobnumer", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "职位编号");

    /**
     * The column <code>jobdb.user_profile_jobapply.同上3</code>. hr_position.id, 职位ID
     */
    public final TableField<UserProfileJobapplyRecord, Integer> 同上3 = createField("同上3", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "hr_position.id, 职位ID");

    /**
     * The column <code>jobdb.user_profile_jobapply.投递状态</code>. 0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified 6:excess apply times
     */
    public final TableField<UserProfileJobapplyRecord, Integer> 投递状态 = createField("投递状态", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified 6:excess apply times");

    /**
     * The column <code>jobdb.user_profile_jobapply.来源</code>. 0:社招 1：校招
     */
    public final TableField<UserProfileJobapplyRecord, Byte> 来源 = createField("来源", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:社招 1：校招");

    /**
     * The column <code>jobdb.user_profile_jobapply.ats来源是9</code>. 职位来源 0：Moseeker
     */
    public final TableField<UserProfileJobapplyRecord, Integer> ATS来源是9 = createField("ats来源是9", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职位来源 0：Moseeker");

    /**
     * The column <code>jobdb.user_profile_jobapply.姓名</code>. 姓名
     */
    public final TableField<UserProfileJobapplyRecord, String> 姓名 = createField("姓名", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "姓名");

    /**
     * Create a <code>jobdb.user_profile_jobapply</code> table reference
     */
    public UserProfileJobapply() {
        this("user_profile_jobapply", null);
    }

    /**
     * Create an aliased <code>jobdb.user_profile_jobapply</code> table reference
     */
    public UserProfileJobapply(String alias) {
        this(alias, USER_PROFILE_JOBAPPLY);
    }

    private UserProfileJobapply(String alias, Table<UserProfileJobapplyRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserProfileJobapply(String alias, Table<UserProfileJobapplyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "VIEW");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Jobdb.JOBDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserProfileJobapply as(String alias) {
        return new UserProfileJobapply(alias, this);
    }

    /**
     * Rename this table
     */

    public UserProfileJobapply rename(String name) {
        return new UserProfileJobapply(name, null);
    }
}
