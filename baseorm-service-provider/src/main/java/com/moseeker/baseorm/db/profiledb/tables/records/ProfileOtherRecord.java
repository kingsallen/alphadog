/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.profiledb.tables.records;


import com.moseeker.baseorm.db.profiledb.tables.ProfileOther;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户profile导入记录信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileOtherRecord extends UpdatableRecordImpl<ProfileOtherRecord> implements Record4<Integer, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = 177943346;

    /**
     * Setter for <code>profiledb.profile_other.profile_id</code>. profile.id
     */
    public void setProfileId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>profiledb.profile_other.profile_id</code>. profile.id
     */
    public Integer getProfileId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>profiledb.profile_other.other</code>. profile默认不显示字段
     */
    public void setOther(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>profiledb.profile_other.other</code>. profile默认不显示字段
     */
    public String getOther() {
        return (String) get(1);
    }

    /**
     * Setter for <code>profiledb.profile_other.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>profiledb.profile_other.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>profiledb.profile_other.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>profiledb.profile_other.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(3);
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
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Timestamp, Timestamp> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Timestamp, Timestamp> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ProfileOther.PROFILE_OTHER.PROFILE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ProfileOther.PROFILE_OTHER.OTHER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return ProfileOther.PROFILE_OTHER.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return ProfileOther.PROFILE_OTHER.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getProfileId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getOther();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileOtherRecord value1(Integer value) {
        setProfileId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileOtherRecord value2(String value) {
        setOther(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileOtherRecord value3(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileOtherRecord value4(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileOtherRecord values(Integer value1, String value2, Timestamp value3, Timestamp value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProfileOtherRecord
     */
    public ProfileOtherRecord() {
        super(ProfileOther.PROFILE_OTHER);
    }

    /**
     * Create a detached, initialised ProfileOtherRecord
     */
    public ProfileOtherRecord(Integer profileId, String other, Timestamp createTime, Timestamp updateTime) {
        super(ProfileOther.PROFILE_OTHER);

        set(0, profileId);
        set(1, other);
        set(2, createTime);
        set(3, updateTime);
    }
}
