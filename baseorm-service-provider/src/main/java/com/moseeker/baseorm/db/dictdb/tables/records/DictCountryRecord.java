/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.dictdb.tables.records;


import com.moseeker.baseorm.db.dictdb.tables.DictCountry;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 城市字典表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictCountryRecord extends UpdatableRecordImpl<DictCountryRecord> implements Record10<Integer, String, String, String, String, String, String, Byte, Integer, Short> {

    private static final long serialVersionUID = -1377879069;

    /**
     * Setter for <code>dictdb.dict_country.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>dictdb.dict_country.name</code>. 国家中文名称
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.name</code>. 国家中文名称
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>dictdb.dict_country.ename</code>. 国家英文名称
     */
    public void setEname(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.ename</code>. 国家英文名称
     */
    public String getEname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>dictdb.dict_country.iso_code_2</code>. iso_code_2
     */
    public void setIsoCode_2(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.iso_code_2</code>. iso_code_2
     */
    public String getIsoCode_2() {
        return (String) get(3);
    }

    /**
     * Setter for <code>dictdb.dict_country.iso_code_3</code>. iso_code_3
     */
    public void setIsoCode_3(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.iso_code_3</code>. iso_code_3
     */
    public String getIsoCode_3() {
        return (String) get(4);
    }

    /**
     * Setter for <code>dictdb.dict_country.code</code>. COUNTRY CODE
     */
    public void setCode(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.code</code>. COUNTRY CODE
     */
    public String getCode() {
        return (String) get(5);
    }

    /**
     * Setter for <code>dictdb.dict_country.icon_class</code>. 国旗样式
     */
    public void setIconClass(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.icon_class</code>. 国旗样式
     */
    public String getIconClass() {
        return (String) get(6);
    }

    /**
     * Setter for <code>dictdb.dict_country.hot_country</code>. 热门国家 0=否 1=是
     */
    public void setHotCountry(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.hot_country</code>. 热门国家 0=否 1=是
     */
    public Byte getHotCountry() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>dictdb.dict_country.continent_code</code>. 7大洲code, dict_constant.parent_code: 9103
     */
    public void setContinentCode(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.continent_code</code>. 7大洲code, dict_constant.parent_code: 9103
     */
    public Integer getContinentCode() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>dictdb.dict_country.priority</code>. 优先级
     */
    public void setPriority(Short value) {
        set(9, value);
    }

    /**
     * Getter for <code>dictdb.dict_country.priority</code>. 优先级
     */
    public Short getPriority() {
        return (Short) get(9);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, String, String, String, String, String, Byte, Integer, Short> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, String, String, String, String, String, String, Byte, Integer, Short> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DictCountry.DICT_COUNTRY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return DictCountry.DICT_COUNTRY.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return DictCountry.DICT_COUNTRY.ENAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DictCountry.DICT_COUNTRY.ISO_CODE_2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return DictCountry.DICT_COUNTRY.ISO_CODE_3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return DictCountry.DICT_COUNTRY.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return DictCountry.DICT_COUNTRY.ICON_CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return DictCountry.DICT_COUNTRY.HOT_COUNTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return DictCountry.DICT_COUNTRY.CONTINENT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Short> field10() {
        return DictCountry.DICT_COUNTRY.PRIORITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
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
    public String value3() {
        return getEname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getIsoCode_2();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getIsoCode_3();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getIconClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value8() {
        return getHotCountry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getContinentCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Short value10() {
        return getPriority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value3(String value) {
        setEname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value4(String value) {
        setIsoCode_2(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value5(String value) {
        setIsoCode_3(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value6(String value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value7(String value) {
        setIconClass(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value8(Byte value) {
        setHotCountry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value9(Integer value) {
        setContinentCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord value10(Short value) {
        setPriority(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCountryRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, String value7, Byte value8, Integer value9, Short value10) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DictCountryRecord
     */
    public DictCountryRecord() {
        super(DictCountry.DICT_COUNTRY);
    }

    /**
     * Create a detached, initialised DictCountryRecord
     */
    public DictCountryRecord(Integer id, String name, String ename, String isoCode_2, String isoCode_3, String code, String iconClass, Byte hotCountry, Integer continentCode, Short priority) {
        super(DictCountry.DICT_COUNTRY);

        set(0, id);
        set(1, name);
        set(2, ename);
        set(3, isoCode_2);
        set(4, isoCode_3);
        set(5, code);
        set(6, iconClass);
        set(7, hotCountry);
        set(8, continentCode);
        set(9, priority);
    }
}
