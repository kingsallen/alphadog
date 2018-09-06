/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables;


import com.moseeker.baseorm.db.dictdb.Dictdb;
import com.moseeker.baseorm.db.dictdb.Keys;
import com.moseeker.baseorm.db.dictdb.tables.records.DictLiepinOccupationRecord;

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
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictLiepinOccupation extends TableImpl<DictLiepinOccupationRecord> {

    private static final long serialVersionUID = 819587127;

    /**
     * The reference instance of <code>dictdb.dict_liepin_occupation</code>
     */
    public static final DictLiepinOccupation DICT_LIEPIN_OCCUPATION = new DictLiepinOccupation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictLiepinOccupationRecord> getRecordType() {
        return DictLiepinOccupationRecord.class;
    }

    /**
     * The column <code>dictdb.dict_liepin_occupation.id</code>.
     */
    public final TableField<DictLiepinOccupationRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>dictdb.dict_liepin_occupation.code</code>. 仟寻生成的code，和仟寻的职位职能无关
     */
    public final TableField<DictLiepinOccupationRecord, Integer> CODE = createField("code", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "仟寻生成的code，和仟寻的职位职能无关");

    /**
     * The column <code>dictdb.dict_liepin_occupation.parent_id</code>. 上一级的职能id
     */
    public final TableField<DictLiepinOccupationRecord, Integer> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "上一级的职能id");

    /**
     * The column <code>dictdb.dict_liepin_occupation.other_code</code>. 猎聘的code，可能是字符串
     */
    public final TableField<DictLiepinOccupationRecord, String> OTHER_CODE = createField("other_code", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "猎聘的code，可能是字符串");

    /**
     * The column <code>dictdb.dict_liepin_occupation.level</code>. 级层
     */
    public final TableField<DictLiepinOccupationRecord, Integer> LEVEL = createField("level", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "级层");

    /**
     * The column <code>dictdb.dict_liepin_occupation.create_time</code>.
     */
    public final TableField<DictLiepinOccupationRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>dictdb.dict_liepin_occupation.update_time</code>.
     */
    public final TableField<DictLiepinOccupationRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>dictdb.dict_liepin_occupation.status</code>. 状态 0:有效，1:无效
     */
    public final TableField<DictLiepinOccupationRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态 0:有效，1:无效");

    /**
     * The column <code>dictdb.dict_liepin_occupation.name</code>.
     */
    public final TableField<DictLiepinOccupationRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>dictdb.dict_liepin_occupation.candidate_source</code>. 0:社招 1：校招
     */
    public final TableField<DictLiepinOccupationRecord, Byte> CANDIDATE_SOURCE = createField("candidate_source", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "0:社招 1：校招");

    /**
     * Create a <code>dictdb.dict_liepin_occupation</code> table reference
     */
    public DictLiepinOccupation() {
        this("dict_liepin_occupation", null);
    }

    /**
     * Create an aliased <code>dictdb.dict_liepin_occupation</code> table reference
     */
    public DictLiepinOccupation(String alias) {
        this(alias, DICT_LIEPIN_OCCUPATION);
    }

    private DictLiepinOccupation(String alias, Table<DictLiepinOccupationRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictLiepinOccupation(String alias, Table<DictLiepinOccupationRecord> aliased, Field<?>[] parameters) {
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
    public Identity<DictLiepinOccupationRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DICT_LIEPIN_OCCUPATION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictLiepinOccupationRecord> getPrimaryKey() {
        return Keys.KEY_DICT_LIEPIN_OCCUPATION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictLiepinOccupationRecord>> getKeys() {
        return Arrays.<UniqueKey<DictLiepinOccupationRecord>>asList(Keys.KEY_DICT_LIEPIN_OCCUPATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictLiepinOccupation as(String alias) {
        return new DictLiepinOccupation(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictLiepinOccupation rename(String name) {
        return new DictLiepinOccupation(name, null);
    }
}
