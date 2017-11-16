/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.campaigndb.tables.records;


import com.moseeker.baseorm.db.campaigndb.tables.CampaignRecommendCompany;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 推荐公司
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CampaignRecommendCompanyRecord extends UpdatableRecordImpl<CampaignRecommendCompanyRecord> implements Record6<Integer, Integer, Integer, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = 1513723453;

    /**
     * Setter for <code>campaigndb.campaign_recommend_company.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_recommend_company.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>campaigndb.campaign_recommend_company.company_id</code>. hr_company.id 公司id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_recommend_company.company_id</code>. hr_company.id 公司id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>campaigndb.campaign_recommend_company.weight</code>. 权重值
     */
    public void setWeight(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_recommend_company.weight</code>. 权重值
     */
    public Integer getWeight() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>campaigndb.campaign_recommend_company.disable</code>. 是否禁用(0: 启用，1: 禁用)
     */
    public void setDisable(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_recommend_company.disable</code>. 是否禁用(0: 启用，1: 禁用)
     */
    public Byte getDisable() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>campaigndb.campaign_recommend_company.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_recommend_company.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>campaigndb.campaign_recommend_company.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_recommend_company.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
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
    public Row6<Integer, Integer, Integer, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Integer, Integer, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY.WEIGHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY.UPDATE_TIME;
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
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getWeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value4() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignRecommendCompanyRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignRecommendCompanyRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignRecommendCompanyRecord value3(Integer value) {
        setWeight(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignRecommendCompanyRecord value4(Byte value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignRecommendCompanyRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignRecommendCompanyRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignRecommendCompanyRecord values(Integer value1, Integer value2, Integer value3, Byte value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached CampaignRecommendCompanyRecord
     */
    public CampaignRecommendCompanyRecord() {
        super(CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY);
    }

    /**
     * Create a detached, initialised CampaignRecommendCompanyRecord
     */
    public CampaignRecommendCompanyRecord(Integer id, Integer companyId, Integer weight, Byte disable, Timestamp createTime, Timestamp updateTime) {
        super(CampaignRecommendCompany.CAMPAIGN_RECOMMEND_COMPANY);

        set(0, id);
        set(1, companyId);
        set(2, weight);
        set(3, disable);
        set(4, createTime);
        set(5, updateTime);
    }
}
