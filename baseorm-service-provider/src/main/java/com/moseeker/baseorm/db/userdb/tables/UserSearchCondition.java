/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserSearchConditionRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * 用户搜索条件(qx职位搜索)
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserSearchCondition extends TableImpl<UserSearchConditionRecord> {

    private static final long serialVersionUID = 443282593;

    /**
     * The reference instance of <code>userdb.user_search_condition</code>
     */
    public static final UserSearchCondition USER_SEARCH_CONDITION = new UserSearchCondition();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserSearchConditionRecord> getRecordType() {
        return UserSearchConditionRecord.class;
    }

    /**
     * The column <code>userdb.user_search_condition.id</code>.
     */
    public final TableField<UserSearchConditionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>userdb.user_search_condition.name</code>. 筛选项名称
     */
    public final TableField<UserSearchConditionRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "筛选项名称");

    /**
     * The column <code>userdb.user_search_condition.keywords</code>. 搜索关键字，可添加多个例如（["java", "php"]）
     */
    public final TableField<UserSearchConditionRecord, String> KEYWORDS = createField("keywords", org.jooq.impl.SQLDataType.VARCHAR.length(1000).nullable(false), this, "搜索关键字，可添加多个例如（[\"java\", \"php\"]）");

    /**
     * The column <code>userdb.user_search_condition.city_name</code>. 选择城市，可添加多个例如(["上海", "北京"])
     */
    public final TableField<UserSearchConditionRecord, String> CITY_NAME = createField("city_name", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "选择城市，可添加多个例如([\"上海\", \"北京\"])");

    /**
     * The column <code>userdb.user_search_condition.salary_top</code>. 薪资上限（k）
     */
    public final TableField<UserSearchConditionRecord, Integer> SALARY_TOP = createField("salary_top", org.jooq.impl.SQLDataType.INTEGER, this, "薪资上限（k）");

    /**
     * The column <code>userdb.user_search_condition.salary_bottom</code>. 薪资下限（k）
     */
    public final TableField<UserSearchConditionRecord, Integer> SALARY_BOTTOM = createField("salary_bottom", org.jooq.impl.SQLDataType.INTEGER, this, "薪资下限（k）");

    /**
     * The column <code>userdb.user_search_condition.salary_negotiable</code>. 薪资面议(0: 否，1：是)
     */
    public final TableField<UserSearchConditionRecord, Byte> SALARY_NEGOTIABLE = createField("salary_negotiable", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "薪资面议(0: 否，1：是)");

    /**
     * The column <code>userdb.user_search_condition.industry</code>. 所属行业，可添加多个例如(["计算机软件", "金融"])
     */
    public final TableField<UserSearchConditionRecord, String> INDUSTRY = createField("industry", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "所属行业，可添加多个例如([\"计算机软件\", \"金融\"])");

    /**
     * The column <code>userdb.user_search_condition.user_id</code>. user_user.id 用户id
     */
    public final TableField<UserSearchConditionRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "user_user.id 用户id");

    /**
     * The column <code>userdb.user_search_condition.disable</code>. 是否禁用(0: 不禁用，1: 禁用)
     */
    public final TableField<UserSearchConditionRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否禁用(0: 不禁用，1: 禁用)");

    /**
     * The column <code>userdb.user_search_condition.create_time</code>. 创建时间
     */
    public final TableField<UserSearchConditionRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>userdb.user_search_condition.update_time</code>. 修改时间
     */
    public final TableField<UserSearchConditionRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * Create a <code>userdb.user_search_condition</code> table reference
     */
    public UserSearchCondition() {
        this("user_search_condition", null);
    }

    /**
     * Create an aliased <code>userdb.user_search_condition</code> table reference
     */
    public UserSearchCondition(String alias) {
        this(alias, USER_SEARCH_CONDITION);
    }

    private UserSearchCondition(String alias, Table<UserSearchConditionRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserSearchCondition(String alias, Table<UserSearchConditionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "用户搜索条件(qx职位搜索)");
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
    public Identity<UserSearchConditionRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_SEARCH_CONDITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserSearchConditionRecord> getPrimaryKey() {
        return Keys.KEY_USER_SEARCH_CONDITION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserSearchConditionRecord>> getKeys() {
        return Arrays.<UniqueKey<UserSearchConditionRecord>>asList(Keys.KEY_USER_SEARCH_CONDITION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserSearchCondition as(String alias) {
        return new UserSearchCondition(alias, this);
    }

    /**
     * Rename this table
     */
    public UserSearchCondition rename(String name) {
        return new UserSearchCondition(name, null);
    }
}
