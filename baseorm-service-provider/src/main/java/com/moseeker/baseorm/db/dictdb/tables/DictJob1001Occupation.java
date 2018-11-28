/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables;


import com.moseeker.baseorm.db.dictdb.Dictdb;
import com.moseeker.baseorm.db.dictdb.Keys;
import com.moseeker.baseorm.db.dictdb.tables.records.DictJob1001OccupationRecord;

import java.sql.Timestamp;
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
 * 一览人才的职位表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictJob1001Occupation extends TableImpl<DictJob1001OccupationRecord> {

    private static final long serialVersionUID = 751694334;

    /**
     * The reference instance of <code>dictdb.dict_job1001_occupation</code>
     */
    public static final DictJob1001Occupation DICT_JOB1001_OCCUPATION = new DictJob1001Occupation();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictJob1001OccupationRecord> getRecordType() {
        return DictJob1001OccupationRecord.class;
    }

    /**
     * The column <code>dictdb.dict_job1001_occupation.code</code>. 职能id
     */
    public final TableField<DictJob1001OccupationRecord, Integer> CODE = createField("code", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "职能id");

    /**
     * The column <code>dictdb.dict_job1001_occupation.parent_id</code>. 父Id，上一级职能的ID
     */
    public final TableField<DictJob1001OccupationRecord, Integer> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "父Id，上一级职能的ID");

    /**
     * The column <code>dictdb.dict_job1001_occupation.name</code>. 职能名称
     */
    public final TableField<DictJob1001OccupationRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "职能名称");

    /**
     * The column <code>dictdb.dict_job1001_occupation.code_other</code>. 第三方职能id
     */
    public final TableField<DictJob1001OccupationRecord, String> CODE_OTHER = createField("code_other", org.jooq.impl.SQLDataType.VARCHAR.length(255).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "第三方职能id");

    /**
     * The column <code>dictdb.dict_job1001_occupation.level</code>. 职能级别 1是一级2是二级依次类推
     */
    public final TableField<DictJob1001OccupationRecord, Short> LEVEL = createField("level", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "职能级别 1是一级2是二级依次类推");

    /**
     * The column <code>dictdb.dict_job1001_occupation.status</code>. 只能状态 0 是有效 1是无效
     */
    public final TableField<DictJob1001OccupationRecord, Short> STATUS = createField("status", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "只能状态 0 是有效 1是无效");

    /**
     * The column <code>dictdb.dict_job1001_occupation.create_time</code>. 创建时间
     */
    public final TableField<DictJob1001OccupationRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>dictdb.dict_job1001_occupation.subsite</code>. 所属发布网站
     */
    public final TableField<DictJob1001OccupationRecord, String> SUBSITE = createField("subsite", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "所属发布网站");

    /**
     * Create a <code>dictdb.dict_job1001_occupation</code> table reference
     */
    public DictJob1001Occupation() {
        this("dict_job1001_occupation", null);
    }

    /**
     * Create an aliased <code>dictdb.dict_job1001_occupation</code> table reference
     */
    public DictJob1001Occupation(String alias) {
        this(alias, DICT_JOB1001_OCCUPATION);
    }

    private DictJob1001Occupation(String alias, Table<DictJob1001OccupationRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictJob1001Occupation(String alias, Table<DictJob1001OccupationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "一览人才的职位表");
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
    public UniqueKey<DictJob1001OccupationRecord> getPrimaryKey() {
        return Keys.KEY_DICT_JOB1001_OCCUPATION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictJob1001OccupationRecord>> getKeys() {
        return Arrays.<UniqueKey<DictJob1001OccupationRecord>>asList(Keys.KEY_DICT_JOB1001_OCCUPATION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictJob1001Occupation as(String alias) {
        return new DictJob1001Occupation(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictJob1001Occupation rename(String name) {
        return new DictJob1001Occupation(name, null);
    }
}
