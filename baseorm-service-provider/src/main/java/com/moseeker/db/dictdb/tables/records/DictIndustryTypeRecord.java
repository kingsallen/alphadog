/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.dictdb.tables.records;


import com.moseeker.db.dictdb.tables.DictIndustryType;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;
import org.jooq.types.UInteger;


/**
 * 行业一级分类字典表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictIndustryTypeRecord extends TableRecordImpl<DictIndustryTypeRecord> implements Record2<UInteger, String> {

    private static final long serialVersionUID = -109273635;

    /**
     * Setter for <code>dictdb.dict_industry_type.code</code>. 字典code
     */
    public void setCode(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>dictdb.dict_industry_type.code</code>. 字典code
     */
    public UInteger getCode() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>dictdb.dict_industry_type.name</code>. 字典name
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>dictdb.dict_industry_type.name</code>. 字典name
     */
    public String getName() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<UInteger, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<UInteger, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return DictIndustryType.DICT_INDUSTRY_TYPE.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return DictIndustryType.DICT_INDUSTRY_TYPE.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord value1(UInteger value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictIndustryTypeRecord values(UInteger value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DictIndustryTypeRecord
     */
    public DictIndustryTypeRecord() {
        super(DictIndustryType.DICT_INDUSTRY_TYPE);
    }

    /**
     * Create a detached, initialised DictIndustryTypeRecord
     */
    public DictIndustryTypeRecord(UInteger code, String name) {
        super(DictIndustryType.DICT_INDUSTRY_TYPE);

        set(0, code);
        set(1, name);
    }
}
