/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables;


import com.moseeker.baseorm.db.dictdb.Dictdb;
import com.moseeker.baseorm.db.dictdb.Keys;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityLiepinRecord;

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
public class DictCityLiepin extends TableImpl<DictCityLiepinRecord> {

    private static final long serialVersionUID = 885184832;

    /**
     * The reference instance of <code>dictdb.dict_city_liepin</code>
     */
    public static final DictCityLiepin DICT_CITY_LIEPIN = new DictCityLiepin();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DictCityLiepinRecord> getRecordType() {
        return DictCityLiepinRecord.class;
    }

    /**
     * The column <code>dictdb.dict_city_liepin.id</code>.
     */
    public final TableField<DictCityLiepinRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>dictdb.dict_city_liepin.code</code>. 猎聘citycode
     */
    public final TableField<DictCityLiepinRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "猎聘citycode");

    /**
     * The column <code>dictdb.dict_city_liepin.cname</code>. 中文名称
     */
    public final TableField<DictCityLiepinRecord, String> CNAME = createField("cname", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "中文名称");

    /**
     * The column <code>dictdb.dict_city_liepin.ename</code>. 英语名称
     */
    public final TableField<DictCityLiepinRecord, String> ENAME = createField("ename", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "英语名称");

    /**
     * The column <code>dictdb.dict_city_liepin.flag</code>. 级别
     */
    public final TableField<DictCityLiepinRecord, Byte> FLAG = createField("flag", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "级别");

    /**
     * The column <code>dictdb.dict_city_liepin.pcode</code>. 父code
     */
    public final TableField<DictCityLiepinRecord, String> PCODE = createField("pcode", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "父code");

    /**
     * The column <code>dictdb.dict_city_liepin.seouri</code>. 猎聘dictcity字段，目前没用到
     */
    public final TableField<DictCityLiepinRecord, String> SEOURI = createField("seouri", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "猎聘dictcity字段，目前没用到");

    /**
     * The column <code>dictdb.dict_city_liepin.shortname</code>. 简称
     */
    public final TableField<DictCityLiepinRecord, String> SHORTNAME = createField("shortname", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "简称");

    /**
     * The column <code>dictdb.dict_city_liepin.othercode</code>. 猎聘对应的仟寻citycode
     */
    public final TableField<DictCityLiepinRecord, String> OTHERCODE = createField("othercode", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "猎聘对应的仟寻citycode");

    /**
     * Create a <code>dictdb.dict_city_liepin</code> table reference
     */
    public DictCityLiepin() {
        this("dict_city_liepin", null);
    }

    /**
     * Create an aliased <code>dictdb.dict_city_liepin</code> table reference
     */
    public DictCityLiepin(String alias) {
        this(alias, DICT_CITY_LIEPIN);
    }

    private DictCityLiepin(String alias, Table<DictCityLiepinRecord> aliased) {
        this(alias, aliased, null);
    }

    private DictCityLiepin(String alias, Table<DictCityLiepinRecord> aliased, Field<?>[] parameters) {
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
    public Identity<DictCityLiepinRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DICT_CITY_LIEPIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DictCityLiepinRecord> getPrimaryKey() {
        return Keys.KEY_DICT_CITY_LIEPIN_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DictCityLiepinRecord>> getKeys() {
        return Arrays.<UniqueKey<DictCityLiepinRecord>>asList(Keys.KEY_DICT_CITY_LIEPIN_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCityLiepin as(String alias) {
        return new DictCityLiepin(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public DictCityLiepin rename(String name) {
        return new DictCityLiepin(name, null);
    }
}
