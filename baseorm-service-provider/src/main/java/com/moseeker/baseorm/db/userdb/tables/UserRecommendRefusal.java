/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserRecommendRefusalRecord;

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
 * 用户拒绝推荐信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecommendRefusal extends TableImpl<UserRecommendRefusalRecord> {

    private static final long serialVersionUID = 1963666619;

    /**
     * The reference instance of <code>userdb.user_recommend_refusal</code>
     */
    public static final UserRecommendRefusal USER_RECOMMEND_REFUSAL = new UserRecommendRefusal();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecommendRefusalRecord> getRecordType() {
        return UserRecommendRefusalRecord.class;
    }

    /**
     * The column <code>userdb.user_recommend_refusal.id</code>. 主key
     */
    public final TableField<UserRecommendRefusalRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>userdb.user_recommend_refusal.user_id</code>. C端用户ID
     */
    public final TableField<UserRecommendRefusalRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "C端用户ID");

    /**
     * The column <code>userdb.user_recommend_refusal.wechat_id</code>. 公众号ID，表示用户在哪个公众号拒绝推送
     */
    public final TableField<UserRecommendRefusalRecord, Integer> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "公众号ID，表示用户在哪个公众号拒绝推送");

    /**
     * The column <code>userdb.user_recommend_refusal.refuse_time</code>. 拒绝推荐的时间
     */
    public final TableField<UserRecommendRefusalRecord, Timestamp> REFUSE_TIME = createField("refuse_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "拒绝推荐的时间");

    /**
     * The column <code>userdb.user_recommend_refusal.refuse_timeout</code>. 拒绝推荐过期时间,默认refuse_time+6个月
     */
    public final TableField<UserRecommendRefusalRecord, Timestamp> REFUSE_TIMEOUT = createField("refuse_timeout", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "拒绝推荐过期时间,默认refuse_time+6个月");

    /**
     * Create a <code>userdb.user_recommend_refusal</code> table reference
     */
    public UserRecommendRefusal() {
        this("user_recommend_refusal", null);
    }

    /**
     * Create an aliased <code>userdb.user_recommend_refusal</code> table reference
     */
    public UserRecommendRefusal(String alias) {
        this(alias, USER_RECOMMEND_REFUSAL);
    }

    private UserRecommendRefusal(String alias, Table<UserRecommendRefusalRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserRecommendRefusal(String alias, Table<UserRecommendRefusalRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "用户拒绝推荐信息表");
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
    public Identity<UserRecommendRefusalRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_RECOMMEND_REFUSAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserRecommendRefusalRecord> getPrimaryKey() {
        return Keys.KEY_USER_RECOMMEND_REFUSAL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserRecommendRefusalRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRecommendRefusalRecord>>asList(Keys.KEY_USER_RECOMMEND_REFUSAL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRecommendRefusal as(String alias) {
        return new UserRecommendRefusal(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserRecommendRefusal rename(String name) {
        return new UserRecommendRefusal(name, null);
    }
}
