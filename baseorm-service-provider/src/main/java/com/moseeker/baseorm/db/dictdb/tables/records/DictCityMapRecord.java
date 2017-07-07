/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.dictdb.tables.records;


import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 城市字典code映射表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictCityMapRecord extends UpdatableRecordImpl<DictCityMapRecord> implements Record6<Integer, Integer, String, Integer, Integer, Timestamp> {

    private static final long serialVersionUID = -747105942;

    /**
     * Setter for <code>dictdb.dict_city_map.id</code>. 主键id
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>dictdb.dict_city_map.id</code>. 主键id
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>dictdb.dict_city_map.CODE</code>. 千寻城市字典code
     */
    public void setCode(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>dictdb.dict_city_map.CODE</code>. 千寻城市字典code
     */
    public Integer getCode() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>dictdb.dict_city_map.code_other</code>.
     */
    public void setCodeOther(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>dictdb.dict_city_map.code_other</code>.
     */
    public String getCodeOther() {
        return (String) get(2);
    }

    /**
     * Setter for <code>dictdb.dict_city_map.channel</code>. 渠道 1 51job 2 猎聘 3 智联 4 linkedin
     */
    public void setChannel(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>dictdb.dict_city_map.channel</code>. 渠道 1 51job 2 猎聘 3 智联 4 linkedin
     */
    public Integer getChannel() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>dictdb.dict_city_map.STATUS</code>. 状态 0 是有效 1是无效
     */
    public void setStatus(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>dictdb.dict_city_map.STATUS</code>. 状态 0 是有效 1是无效
     */
    public Integer getStatus() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>dictdb.dict_city_map.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>dictdb.dict_city_map.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
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
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Integer, Integer, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, String, Integer, Integer, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DictCityMap.DICT_CITY_MAP.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return DictCityMap.DICT_CITY_MAP.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return DictCityMap.DICT_CITY_MAP.CODE_OTHER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return DictCityMap.DICT_CITY_MAP.CHANNEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return DictCityMap.DICT_CITY_MAP.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return DictCityMap.DICT_CITY_MAP.CREATE_TIME;
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
    public Integer value2() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCodeOther();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getChannel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCityMapRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCityMapRecord value2(Integer value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCityMapRecord value3(String value) {
        setCodeOther(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCityMapRecord value4(Integer value) {
        setChannel(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCityMapRecord value5(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCityMapRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictCityMapRecord values(Integer value1, Integer value2, String value3, Integer value4, Integer value5, Timestamp value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DictCityMapRecord
     */
    public DictCityMapRecord() {
        super(DictCityMap.DICT_CITY_MAP);
    }

    /**
     * Create a detached, initialised DictCityMapRecord
     */
    public DictCityMapRecord(Integer id, Integer code, String codeOther, Integer channel, Integer status, Timestamp createTime) {
        super(DictCityMap.DICT_CITY_MAP);

        set(0, id);
        set(1, code);
        set(2, codeOther);
        set(3, channel);
        set(4, status);
        set(5, createTime);
    }
}
