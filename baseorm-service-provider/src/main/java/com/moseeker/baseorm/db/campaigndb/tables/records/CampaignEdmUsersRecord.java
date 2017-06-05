/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.campaigndb.tables.records;


import com.moseeker.baseorm.db.campaigndb.tables.CampaignEdmUsers;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CampaignEdmUsersRecord extends UpdatableRecordImpl<CampaignEdmUsersRecord> implements Record6<Integer, Integer, Integer, Timestamp, Byte, Timestamp> {

    private static final long serialVersionUID = 1171898742;

    /**
     * Setter for <code>campaigndb.campaign_edm_users.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_edm_users.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>campaigndb.campaign_edm_users.campaign_id</code>.
     */
    public void setCampaignId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_edm_users.campaign_id</code>.
     */
    public Integer getCampaignId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>campaigndb.campaign_edm_users.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_edm_users.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>campaigndb.campaign_edm_users.send_time</code>.
     */
    public void setSendTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_edm_users.send_time</code>.
     */
    public Timestamp getSendTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>campaigndb.campaign_edm_users.send_status</code>. 0.  新建 1 发送成功, 2 发送失败. 3. 不符合发送条件(比如没有推荐职位)
     */
    public void setSendStatus(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_edm_users.send_status</code>. 0.  新建 1 发送成功, 2 发送失败. 3. 不符合发送条件(比如没有推荐职位)
     */
    public Byte getSendStatus() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>campaigndb.campaign_edm_users.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_edm_users.create_time</code>.
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
    public Row6<Integer, Integer, Integer, Timestamp, Byte, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, Timestamp, Byte, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CampaignEdmUsers.CAMPAIGN_EDM_USERS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return CampaignEdmUsers.CAMPAIGN_EDM_USERS.CAMPAIGN_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return CampaignEdmUsers.CAMPAIGN_EDM_USERS.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return CampaignEdmUsers.CAMPAIGN_EDM_USERS.SEND_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return CampaignEdmUsers.CAMPAIGN_EDM_USERS.SEND_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return CampaignEdmUsers.CAMPAIGN_EDM_USERS.CREATE_TIME;
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
        return getCampaignId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getSendTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getSendStatus();
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
    public CampaignEdmUsersRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignEdmUsersRecord value2(Integer value) {
        setCampaignId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignEdmUsersRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignEdmUsersRecord value4(Timestamp value) {
        setSendTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignEdmUsersRecord value5(Byte value) {
        setSendStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignEdmUsersRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignEdmUsersRecord values(Integer value1, Integer value2, Integer value3, Timestamp value4, Byte value5, Timestamp value6) {
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
     * Create a detached CampaignEdmUsersRecord
     */
    public CampaignEdmUsersRecord() {
        super(CampaignEdmUsers.CAMPAIGN_EDM_USERS);
    }

    /**
     * Create a detached, initialised CampaignEdmUsersRecord
     */
    public CampaignEdmUsersRecord(Integer id, Integer campaignId, Integer userId, Timestamp sendTime, Byte sendStatus, Timestamp createTime) {
        super(CampaignEdmUsers.CAMPAIGN_EDM_USERS);

        set(0, id);
        set(1, campaignId);
        set(2, userId);
        set(3, sendTime);
        set(4, sendStatus);
        set(5, createTime);
    }
}