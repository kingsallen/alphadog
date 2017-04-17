/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.StListRecord;

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
 * 记录joblist点击信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StList extends TableImpl<StListRecord> {

    private static final long serialVersionUID = -133251391;

    /**
     * The reference instance of <code>analytics.st_list</code>
     */
    public static final StList ST_LIST = new StList();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StListRecord> getRecordType() {
        return StListRecord.class;
    }

    /**
     * The column <code>analytics.st_list.id</code>.
     */
    public final TableField<StListRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.st_list.create_time</code>.
     */
    public final TableField<StListRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>analytics.st_list.wechat_id</code>. 职位id,对应hr_position的id
     */
    public final TableField<StListRecord, Integer> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGER, this, "职位id,对应hr_position的id");

    /**
     * The column <code>analytics.st_list.recom</code>. 转发人id，对应wx_group_user的id
     */
    public final TableField<StListRecord, String> RECOM = createField("recom", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "转发人id，对应wx_group_user的id");

    /**
     * The column <code>analytics.st_list.remote_ip</code>.
     */
    public final TableField<StListRecord, String> REMOTE_IP = createField("remote_ip", org.jooq.impl.SQLDataType.VARCHAR.length(16), this, "");

    /**
     * The column <code>analytics.st_list.viewer_id</code>. viewer_id，生成的访问者id
     */
    public final TableField<StListRecord, String> VIEWER_ID = createField("viewer_id", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "viewer_id，生成的访问者id");

    /**
     * Create a <code>analytics.st_list</code> table reference
     */
    public StList() {
        this("st_list", null);
    }

    /**
     * Create an aliased <code>analytics.st_list</code> table reference
     */
    public StList(String alias) {
        this(alias, ST_LIST);
    }

    private StList(String alias, Table<StListRecord> aliased) {
        this(alias, aliased, null);
    }

    private StList(String alias, Table<StListRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "记录joblist点击信息");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Analytics.ANALYTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<StListRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ST_LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StListRecord> getPrimaryKey() {
        return Keys.KEY_ST_LIST_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StListRecord>> getKeys() {
        return Arrays.<UniqueKey<StListRecord>>asList(Keys.KEY_ST_LIST_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StList as(String alias) {
        return new StList(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StList rename(String name) {
        return new StList(name, null);
    }
}
