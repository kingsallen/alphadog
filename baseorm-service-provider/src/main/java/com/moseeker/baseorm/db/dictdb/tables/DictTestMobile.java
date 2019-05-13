/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables;


import com.moseeker.baseorm.db.dictdb.Dictdb;
import com.moseeker.baseorm.db.dictdb.Keys;
import com.moseeker.baseorm.db.dictdb.tables.records.DictTestMobileRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictTestMobile extends TableImpl<DictTestMobileRecord> {

    private static final long serialVersionUID = -895069103;

    /**
     * The reference instance of <code>dictdb.dict_test_mobile</code>
     */
    public static final DictTestMobile DICT_TEST_MOBILE = new DictTestMobile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictTestMobileRecord> getRecordType() {
        return DictTestMobileRecord.class;
    }

    /**
     * The column <code>dictdb.dict_test_mobile.id</code>. 主key
     */
    public final TableField<DictTestMobileRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>dictdb.dict_test_mobile.mobile</code>. 测试人员手机号码
     */
    public final TableField<DictTestMobileRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "测试人员手机号码");

    /**
     * The column <code>dictdb.dict_test_mobile.name</code>. 测试人员姓名
     */
    public final TableField<DictTestMobileRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "测试人员姓名");

    /**
     * The column <code>dictdb.dict_test_mobile.create_time</code>.
     */
    public final TableField<DictTestMobileRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>dictdb.dict_test_mobile.update_time</code>.
     */
    public final TableField<DictTestMobileRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>dictdb.dict_test_mobile</code> table reference
     */
    public DictTestMobile() {
        this("dict_test_mobile", null);
    }

    /**
     * Create an aliased <code>dictdb.dict_test_mobile</code> table reference
     */
    public DictTestMobile(String alias) {
        this(alias, DICT_TEST_MOBILE);
    }

    private DictTestMobile(String alias, Table<DictTestMobileRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictTestMobile(String alias, Table<DictTestMobileRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Dictdb.DICTDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DictTestMobileRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DICT_TEST_MOBILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictTestMobileRecord> getPrimaryKey() {
        return Keys.KEY_DICT_TEST_MOBILE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictTestMobileRecord>> getKeys() {
        return Arrays.<UniqueKey<DictTestMobileRecord>>asList(Keys.KEY_DICT_TEST_MOBILE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictTestMobile as(String alias) {
        return new DictTestMobile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictTestMobile rename(String name) {
        return new DictTestMobile(name, null);
    }
}
