/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.StmResTypeRecord;

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
 * 返回类型表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StmResType extends TableImpl<StmResTypeRecord> {

    private static final long serialVersionUID = -654575617;

    /**
     * The reference instance of <code>analytics.stm_res_type</code>
     */
    public static final StmResType STM_RES_TYPE = new StmResType();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StmResTypeRecord> getRecordType() {
        return StmResTypeRecord.class;
    }

    /**
     * The column <code>analytics.stm_res_type.id</code>. 主键
     */
    public final TableField<StmResTypeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主键");

    /**
     * The column <code>analytics.stm_res_type.res_type</code>. 返回类型(html,xml)
     */
    public final TableField<StmResTypeRecord, String> RES_TYPE = createField("res_type", org.jooq.impl.SQLDataType.VARCHAR.length(10), this, "返回类型(html,xml)");

    /**
     * Create a <code>analytics.stm_res_type</code> table reference
     */
    public StmResType() {
        this("stm_res_type", null);
    }

    /**
     * Create an aliased <code>analytics.stm_res_type</code> table reference
     */
    public StmResType(String alias) {
        this(alias, STM_RES_TYPE);
    }

    private StmResType(String alias, Table<StmResTypeRecord> aliased) {
        this(alias, aliased, null);
    }

    private StmResType(String alias, Table<StmResTypeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "返回类型表");
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
    public Identity<StmResTypeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_STM_RES_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StmResTypeRecord> getPrimaryKey() {
        return Keys.KEY_STM_RES_TYPE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StmResTypeRecord>> getKeys() {
        return Arrays.<UniqueKey<StmResTypeRecord>>asList(Keys.KEY_STM_RES_TYPE_PRIMARY, Keys.KEY_STM_RES_TYPE_RES_TYPE_UNIQUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmResType as(String alias) {
        return new StmResType(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StmResType rename(String name) {
        return new StmResType(name, null);
    }
}
