/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables;


import com.moseeker.baseorm.db.dictdb.Dictdb;
import com.moseeker.baseorm.db.dictdb.Keys;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_58jobOccupationRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 58职能表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dict_58jobOccupation extends TableImpl<Dict_58jobOccupationRecord> {

    private static final long serialVersionUID = 850399210;

    /**
     * The reference instance of <code>dictdb.dict_58job_occupation</code>
     */
    public static final Dict_58jobOccupation DICT_58JOB_OCCUPATION = new Dict_58jobOccupation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<Dict_58jobOccupationRecord> getRecordType() {
        return Dict_58jobOccupationRecord.class;
    }

    /**
     * The column <code>dictdb.dict_58job_occupation.code</code>. 职能code
     */
    public final TableField<Dict_58jobOccupationRecord, Integer> CODE = createField("code", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "职能code");

    /**
     * The column <code>dictdb.dict_58job_occupation.name</code>. 职能名称
     */
    public final TableField<Dict_58jobOccupationRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "职能名称");

    /**
     * The column <code>dictdb.dict_58job_occupation.pcode</code>. 职能的父code，目前由于58给的是字符串，不支持父code，此字段备用
     */
    public final TableField<Dict_58jobOccupationRecord, Integer> PCODE = createField("pcode", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职能的父code，目前由于58给的是字符串，不支持父code，此字段备用");

    /**
     * Create a <code>dictdb.dict_58job_occupation</code> table reference
     */
    public Dict_58jobOccupation() {
        this("dict_58job_occupation", null);
    }

    /**
     * Create an aliased <code>dictdb.dict_58job_occupation</code> table reference
     */
    public Dict_58jobOccupation(String alias) {
        this(alias, DICT_58JOB_OCCUPATION);
    }

    private Dict_58jobOccupation(String alias, Table<Dict_58jobOccupationRecord> aliased) {
        this(alias, aliased, null);
    }

    private Dict_58jobOccupation(String alias, Table<Dict_58jobOccupationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "58职能表");
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
    public UniqueKey<Dict_58jobOccupationRecord> getPrimaryKey() {
        return Keys.KEY_DICT_58JOB_OCCUPATION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<Dict_58jobOccupationRecord>> getKeys() {
        return Arrays.<UniqueKey<Dict_58jobOccupationRecord>>asList(Keys.KEY_DICT_58JOB_OCCUPATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dict_58jobOccupation as(String alias) {
        return new Dict_58jobOccupation(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Dict_58jobOccupation rename(String name) {
        return new Dict_58jobOccupation(name, null);
    }
}
