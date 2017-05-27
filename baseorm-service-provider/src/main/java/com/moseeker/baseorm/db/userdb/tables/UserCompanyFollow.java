/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserCompanyFollowRecord;

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
 * 公司关注表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCompanyFollow extends TableImpl<UserCompanyFollowRecord> {

    private static final long serialVersionUID = 1183169712;

    /**
     * The reference instance of <code>userdb.user_company_follow</code>
     */
    public static final UserCompanyFollow USER_COMPANY_FOLLOW = new UserCompanyFollow();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserCompanyFollowRecord> getRecordType() {
        return UserCompanyFollowRecord.class;
    }

    /**
     * The column <code>userdb.user_company_follow.id</code>. id
     */
    public final TableField<UserCompanyFollowRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "id");

    /**
     * The column <code>userdb.user_company_follow.company_id</code>. hr_company.id
     */
    public final TableField<UserCompanyFollowRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_company.id");

    /**
     * The column <code>userdb.user_company_follow.user_id</code>. user_user.id
     */
    public final TableField<UserCompanyFollowRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "user_user.id");

    /**
     * The column <code>userdb.user_company_follow.status</code>. 0: 关注 1：取消关注
     */
    public final TableField<UserCompanyFollowRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0: 关注 1：取消关注");

    /**
     * The column <code>userdb.user_company_follow.source</code>. 关注来源 0: 未知 1：微信端 2：PC 端
     */
    public final TableField<UserCompanyFollowRecord, Integer> SOURCE = createField("source", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "关注来源 0: 未知 1：微信端 2：PC 端");

    /**
     * The column <code>userdb.user_company_follow.create_time</code>. 关注时间
     */
    public final TableField<UserCompanyFollowRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "关注时间");

    /**
     * The column <code>userdb.user_company_follow.update_time</code>.
     */
    public final TableField<UserCompanyFollowRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>userdb.user_company_follow.unfollow_time</code>. 取消关注时间
     */
    public final TableField<UserCompanyFollowRecord, Timestamp> UNFOLLOW_TIME = createField("unfollow_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "取消关注时间");

    /**
     * Create a <code>userdb.user_company_follow</code> table reference
     */
    public UserCompanyFollow() {
        this("user_company_follow", null);
    }

    /**
     * Create an aliased <code>userdb.user_company_follow</code> table reference
     */
    public UserCompanyFollow(String alias) {
        this(alias, USER_COMPANY_FOLLOW);
    }

    private UserCompanyFollow(String alias, Table<UserCompanyFollowRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserCompanyFollow(String alias, Table<UserCompanyFollowRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "公司关注表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Userdb.USERDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserCompanyFollowRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_COMPANY_FOLLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserCompanyFollowRecord> getPrimaryKey() {
        return Keys.KEY_USER_COMPANY_FOLLOW_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserCompanyFollowRecord>> getKeys() {
        return Arrays.<UniqueKey<UserCompanyFollowRecord>>asList(Keys.KEY_USER_COMPANY_FOLLOW_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyFollow as(String alias) {
        return new UserCompanyFollow(alias, this);
    }

    /**
     * Rename this table
     */
    public UserCompanyFollow rename(String name) {
        return new UserCompanyFollow(name, null);
    }
}
