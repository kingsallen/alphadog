/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.profiledb.tables.records;


import com.moseeker.db.profiledb.tables.ProfileWorks;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * Profile的个人作品
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileWorksRecord extends UpdatableRecordImpl<ProfileWorksRecord> implements Record8<Integer, Integer, String, String, String, String, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1857190049;

    /**
     * Setter for <code>profiledb.profile_works.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>profiledb.profile_works.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>profiledb.profile_works.profile_id</code>. profile.id
     */
    public void setProfileId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>profiledb.profile_works.profile_id</code>. profile.id
     */
    public Integer getProfileId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>profiledb.profile_works.name</code>. 作品名称
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>profiledb.profile_works.name</code>. 作品名称
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>profiledb.profile_works.url</code>. 作品网址
     */
    public void setUrl(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>profiledb.profile_works.url</code>. 作品网址
     */
    public String getUrl() {
        return (String) get(3);
    }

    /**
     * Setter for <code>profiledb.profile_works.cover</code>. 作品封面
     */
    public void setCover(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>profiledb.profile_works.cover</code>. 作品封面
     */
    public String getCover() {
        return (String) get(4);
    }

    /**
     * Setter for <code>profiledb.profile_works.description</code>. 作品描述
     */
    public void setDescription(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>profiledb.profile_works.description</code>. 作品描述
     */
    public String getDescription() {
        return (String) get(5);
    }

    /**
     * Setter for <code>profiledb.profile_works.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>profiledb.profile_works.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>profiledb.profile_works.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>profiledb.profile_works.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, String, String, String, Timestamp, Timestamp> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, String, String, String, String, Timestamp, Timestamp> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ProfileWorks.PROFILE_WORKS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ProfileWorks.PROFILE_WORKS.PROFILE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ProfileWorks.PROFILE_WORKS.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ProfileWorks.PROFILE_WORKS.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ProfileWorks.PROFILE_WORKS.COVER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ProfileWorks.PROFILE_WORKS.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return ProfileWorks.PROFILE_WORKS.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return ProfileWorks.PROFILE_WORKS.UPDATE_TIME;
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
        return getProfileId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCover();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileWorksRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileWorksRecord value2(Integer value) {
        setProfileId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileWorksRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileWorksRecord value4(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileWorksRecord value5(String value) {
        setCover(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileWorksRecord value6(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileWorksRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileWorksRecord value8(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileWorksRecord values(Integer value1, Integer value2, String value3, String value4, String value5, String value6, Timestamp value7, Timestamp value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProfileWorksRecord
     */
    public ProfileWorksRecord() {
        super(ProfileWorks.PROFILE_WORKS);
    }

    /**
     * Create a detached, initialised ProfileWorksRecord
     */
    public ProfileWorksRecord(Integer id, Integer profileId, String name, String url, String cover, String description, Timestamp createTime, Timestamp updateTime) {
        super(ProfileWorks.PROFILE_WORKS);

        set(0, id);
        set(1, profileId);
        set(2, name);
        set(3, url);
        set(4, cover);
        set(5, description);
        set(6, createTime);
        set(7, updateTime);
    }
}
