/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.profiledb.tables.records;


import com.moseeker.baseorm.db.profiledb.tables.ProfileIntention;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * Profile的求职意向
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileIntentionRecord extends UpdatableRecordImpl<ProfileIntentionRecord> implements Record9<Integer, Integer, Byte, Byte, Byte, String, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = 646182710;

    /**
     * Setter for <code>profiledb.profile_intention.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>profiledb.profile_intention.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>profiledb.profile_intention.profile_id</code>. profile.id
     */
    public void setProfileId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>profiledb.profile_intention.profile_id</code>. profile.id
     */
    public Integer getProfileId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>profiledb.profile_intention.worktype</code>. 工作类型, {"0":"没选择", "1":"全职", "2":"兼职", "3":"实习"}
     */
    public void setWorktype(Byte value) {
        set(2, value);
    }

    /**
     * Getter for <code>profiledb.profile_intention.worktype</code>. 工作类型, {"0":"没选择", "1":"全职", "2":"兼职", "3":"实习"}
     */
    public Byte getWorktype() {
        return (Byte) get(2);
    }

    /**
     * Setter for <code>profiledb.profile_intention.workstate</code>. 当前是否在职状态, 0:未填写 1: 在职，看看新机会, 2: 在职，急寻新工作, 3:在职，暂无跳槽打算, 4:离职，正在找工作, 5:应届毕业生
     */
    public void setWorkstate(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>profiledb.profile_intention.workstate</code>. 当前是否在职状态, 0:未填写 1: 在职，看看新机会, 2: 在职，急寻新工作, 3:在职，暂无跳槽打算, 4:离职，正在找工作, 5:应届毕业生
     */
    public Byte getWorkstate() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>profiledb.profile_intention.salary_code</code>. 薪资code
     */
    public void setSalaryCode(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>profiledb.profile_intention.salary_code</code>. 薪资code
     */
    public Byte getSalaryCode() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>profiledb.profile_intention.tag</code>. 关键词，单个tag最多100个字符，以#隔开
     */
    public void setTag(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>profiledb.profile_intention.tag</code>. 关键词，单个tag最多100个字符，以#隔开
     */
    public String getTag() {
        return (String) get(5);
    }

    /**
     * Setter for <code>profiledb.profile_intention.consider_venture_company_opportunities</code>. 是否考虑创业公司机会 0：未填写 1:考虑 2:不考虑
     */
    public void setConsiderVentureCompanyOpportunities(Byte value) {
        set(6, value);
    }

    /**
     * Getter for <code>profiledb.profile_intention.consider_venture_company_opportunities</code>. 是否考虑创业公司机会 0：未填写 1:考虑 2:不考虑
     */
    public Byte getConsiderVentureCompanyOpportunities() {
        return (Byte) get(6);
    }

    /**
     * Setter for <code>profiledb.profile_intention.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>profiledb.profile_intention.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>profiledb.profile_intention.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>profiledb.profile_intention.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(8);
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
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, Byte, Byte, Byte, String, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Integer, Integer, Byte, Byte, Byte, String, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ProfileIntention.PROFILE_INTENTION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return ProfileIntention.PROFILE_INTENTION.PROFILE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field3() {
        return ProfileIntention.PROFILE_INTENTION.WORKTYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return ProfileIntention.PROFILE_INTENTION.WORKSTATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return ProfileIntention.PROFILE_INTENTION.SALARY_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ProfileIntention.PROFILE_INTENTION.TAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field7() {
        return ProfileIntention.PROFILE_INTENTION.CONSIDER_VENTURE_COMPANY_OPPORTUNITIES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return ProfileIntention.PROFILE_INTENTION.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return ProfileIntention.PROFILE_INTENTION.UPDATE_TIME;
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
    public Byte value3() {
        return getWorktype();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getWorkstate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getSalaryCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getTag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value7() {
        return getConsiderVentureCompanyOpportunities();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord value2(Integer value) {
        setProfileId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord value3(Byte value) {
        setWorktype(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord value4(Byte value) {
        setWorkstate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord value5(Byte value) {
        setSalaryCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord value6(String value) {
        setTag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord value7(Byte value) {
        setConsiderVentureCompanyOpportunities(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord value9(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionRecord values(Integer value1, Integer value2, Byte value3, Byte value4, Byte value5, String value6, Byte value7, Timestamp value8, Timestamp value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProfileIntentionRecord
     */
    public ProfileIntentionRecord() {
        super(ProfileIntention.PROFILE_INTENTION);
    }

    /**
     * Create a detached, initialised ProfileIntentionRecord
     */
    public ProfileIntentionRecord(Integer id, Integer profileId, Byte worktype, Byte workstate, Byte salaryCode, String tag, Byte considerVentureCompanyOpportunities, Timestamp createTime, Timestamp updateTime) {
        super(ProfileIntention.PROFILE_INTENTION);

        set(0, id);
        set(1, profileId);
        set(2, worktype);
        set(3, workstate);
        set(4, salaryCode);
        set(5, tag);
        set(6, considerVentureCompanyOpportunities);
        set(7, createTime);
        set(8, updateTime);
    }
}
