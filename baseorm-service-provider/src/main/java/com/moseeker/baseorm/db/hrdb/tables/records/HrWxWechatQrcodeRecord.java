/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrWxWechatQrcode;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 微信公众号的场景二维码表(永久)
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxWechatQrcodeRecord extends UpdatableRecordImpl<HrWxWechatQrcodeRecord> implements Record7<Integer, Integer, String, String, Timestamp, Timestamp, String> {

    private static final long serialVersionUID = -876602111;

    /**
     * Setter for <code>hrdb.hr_wx_wechat_qrcode.id</code>. 主键
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_qrcode.id</code>. 主键
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_qrcode.wechat_id</code>. hr_wx_wechat.id
     */
    public void setWechatId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_qrcode.wechat_id</code>. hr_wx_wechat.id
     */
    public Integer getWechatId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_qrcode.qrcode_url</code>. 该公众号场景二维码的url
     */
    public void setQrcodeUrl(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_qrcode.qrcode_url</code>. 该公众号场景二维码的url
     */
    public String getQrcodeUrl() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_qrcode.scene</code>. 场景值id
     */
    public void setScene(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_qrcode.scene</code>. 场景值id
     */
    public String getScene() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_qrcode.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_qrcode.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_qrcode.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_qrcode.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_qrcode.ticket</code>. 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
     */
    public void setTicket(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_qrcode.ticket</code>. 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
     */
    public String getTicket() {
        return (String) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Timestamp, Timestamp, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, Integer, String, String, Timestamp, Timestamp, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.WECHAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.QRCODE_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.SCENE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.TICKET;
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
        return getQrcodeUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getScene();
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
    public String value7() {
        return getTicket();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatQrcodeRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatQrcodeRecord value2(Integer value) {
        setWechatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatQrcodeRecord value3(String value) {
        setQrcodeUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatQrcodeRecord value4(String value) {
        setScene(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatQrcodeRecord value5(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatQrcodeRecord value6(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatQrcodeRecord value7(String value) {
        setTicket(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatQrcodeRecord values(Integer value1, Integer value2, String value3, String value4, Timestamp value5, Timestamp value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrWxWechatQrcodeRecord
     */
    public HrWxWechatQrcodeRecord() {
        super(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE);
    }

    /**
     * Create a detached, initialised HrWxWechatQrcodeRecord
     */
    public HrWxWechatQrcodeRecord(Integer id, Integer wechatId, String qrcodeUrl, String scene, Timestamp createTime, Timestamp updateTime, String ticket) {
        super(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE);

        set(0, id);
        set(1, wechatId);
        set(2, qrcodeUrl);
        set(3, scene);
        set(4, createTime);
        set(5, updateTime);
        set(6, ticket);
    }
}