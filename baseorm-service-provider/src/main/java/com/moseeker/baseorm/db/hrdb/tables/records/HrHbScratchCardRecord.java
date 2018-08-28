/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrHbScratchCard;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 新红包刮刮卡记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHbScratchCardRecord extends UpdatableRecordImpl<HrHbScratchCardRecord> implements Record10<Integer, Integer, String, Integer, BigDecimal, Integer, String, Timestamp, Integer, Byte> {

    private static final long serialVersionUID = 1948063870;

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.wechat_id</code>.
     */
    public void setWechatId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.wechat_id</code>.
     */
    public Integer getWechatId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.cardno</code>. 随机字符串
     */
    public void setCardno(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.cardno</code>. 随机字符串
     */
    public String getCardno() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.status</code>. 状态: 0：未拆开 1：已拆开
     */
    public void setStatus(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.status</code>. 状态: 0：未拆开 1：已拆开
     */
    public Integer getStatus() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.amount</code>. 红包金额： 0.00 表示不发红包
     */
    public void setAmount(BigDecimal value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.amount</code>. 红包金额： 0.00 表示不发红包
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.hb_config_id</code>.
     */
    public void setHbConfigId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.hb_config_id</code>.
     */
    public Integer getHbConfigId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.bagging_openid</code>. 聚合号的 openid
     */
    public void setBaggingOpenid(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.bagging_openid</code>. 聚合号的 openid
     */
    public String getBaggingOpenid() {
        return (String) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.hb_item_id</code>.
     */
    public void setHbItemId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.hb_item_id</code>.
     */
    public Integer getHbItemId() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_hb_scratch_card.tips</code>. 是否是小费 0:不是，1:是
     */
    public void setTips(Byte value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_hb_scratch_card.tips</code>. 是否是小费 0:不是，1:是
     */
    public Byte getTips() {
        return (Byte) get(9);
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
    public Row10<Integer, Integer, String, Integer, BigDecimal, Integer, String, Timestamp, Integer, Byte> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, String, Integer, BigDecimal, Integer, String, Timestamp, Integer, Byte> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.WECHAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.CARDNO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field5() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.HB_CONFIG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.BAGGING_OPENID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.HB_ITEM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field10() {
        return HrHbScratchCard.HR_HB_SCRATCH_CARD.TIPS;
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
        return getWechatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getCardno();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value5() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getHbConfigId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getBaggingOpenid();
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
    public Integer value9() {
        return getHbItemId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value10() {
        return getTips();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value2(Integer value) {
        setWechatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value3(String value) {
        setCardno(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value4(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value5(BigDecimal value) {
        setAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value6(Integer value) {
        setHbConfigId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value7(String value) {
        setBaggingOpenid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value8(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value9(Integer value) {
        setHbItemId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord value10(Byte value) {
        setTips(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbScratchCardRecord values(Integer value1, Integer value2, String value3, Integer value4, BigDecimal value5, Integer value6, String value7, Timestamp value8, Integer value9, Byte value10) {
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
     * Create a detached HrHbScratchCardRecord
     */
    public HrHbScratchCardRecord() {
        super(HrHbScratchCard.HR_HB_SCRATCH_CARD);
    }

    /**
     * Create a detached, initialised HrHbScratchCardRecord
     */
    public HrHbScratchCardRecord(Integer id, Integer wechatId, String cardno, Integer status, BigDecimal amount, Integer hbConfigId, String baggingOpenid, Timestamp createTime, Integer hbItemId, Byte tips) {
        super(HrHbScratchCard.HR_HB_SCRATCH_CARD);

        set(0, id);
        set(1, wechatId);
        set(2, cardno);
        set(3, status);
        set(4, amount);
        set(5, hbConfigId);
        set(6, baggingOpenid);
        set(7, createTime);
        set(8, hbItemId);
        set(9, tips);
    }
}
