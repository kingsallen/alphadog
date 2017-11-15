/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.campaigndb.tables.records;


import com.moseeker.baseorm.db.campaigndb.tables.CampaignPcBanner;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CampaignPcBannerRecord extends UpdatableRecordImpl<CampaignPcBannerRecord> implements Record6<Integer, String, String, Byte, Timestamp, Timestamp> {

    private static final long serialVersionUID = 2073067193;

    /**
     * Setter for <code>campaigndb.campaign_pc_banner.id</code>. 主键
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_pc_banner.id</code>. 主键
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>campaigndb.campaign_pc_banner.href</code>. 图片链接
     */
    public void setHref(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_pc_banner.href</code>. 图片链接
     */
    public String getHref() {
        return (String) get(1);
    }

    /**
     * Setter for <code>campaigndb.campaign_pc_banner.img_url</code>. 图片地址
     */
    public void setImgUrl(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_pc_banner.img_url</code>. 图片地址
     */
    public String getImgUrl() {
        return (String) get(2);
    }

    /**
     * Setter for <code>campaigndb.campaign_pc_banner.disable</code>. 状态 0：可用，1：不可用
     */
    public void setDisable(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_pc_banner.disable</code>. 状态 0：可用，1：不可用
     */
    public Byte getDisable() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>campaigndb.campaign_pc_banner.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_pc_banner.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>campaigndb.campaign_pc_banner.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>campaigndb.campaign_pc_banner.update_time</code>. 更新时间
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
    public Row6<Integer, String, String, Byte, Timestamp, Timestamp> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, Byte, Timestamp, Timestamp> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return CampaignPcBanner.CAMPAIGN_PC_BANNER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return CampaignPcBanner.CAMPAIGN_PC_BANNER.HREF;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return CampaignPcBanner.CAMPAIGN_PC_BANNER.IMG_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field4() {
        return CampaignPcBanner.CAMPAIGN_PC_BANNER.DISABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return CampaignPcBanner.CAMPAIGN_PC_BANNER.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return CampaignPcBanner.CAMPAIGN_PC_BANNER.UPDATE_TIME;
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
        return getHref();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getImgUrl();
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
    public CampaignPcBannerRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignPcBannerRecord value2(String value) {
        setHref(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignPcBannerRecord value3(String value) {
        setImgUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignPcBannerRecord value4(Byte value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignPcBannerRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignPcBannerRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignPcBannerRecord values(Integer value1, String value2, String value3, Byte value4, Timestamp value5, Timestamp value6) {
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
     * Create a detached CampaignPcBannerRecord
     */
    public CampaignPcBannerRecord() {
        super(CampaignPcBanner.CAMPAIGN_PC_BANNER);
    }

    /**
     * Create a detached, initialised CampaignPcBannerRecord
     */
    public CampaignPcBannerRecord(Integer id, String href, String imgUrl, Byte disable, Timestamp createTime, Timestamp updateTime) {
        super(CampaignPcBanner.CAMPAIGN_PC_BANNER);

        set(0, id);
        set(1, href);
        set(2, imgUrl);
        set(3, disable);
        set(4, createTime);
        set(5, updateTime);
    }
}
