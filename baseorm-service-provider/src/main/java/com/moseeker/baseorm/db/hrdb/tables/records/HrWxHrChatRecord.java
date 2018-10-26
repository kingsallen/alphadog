/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChat;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * IM聊天
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxHrChatRecord extends UpdatableRecordImpl<HrWxHrChatRecord> implements Record13<Integer, Integer, String, Integer, Byte, Byte, Timestamp, Byte, String, String, String, String, String> {

    private static final long serialVersionUID = -1109599774;

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.id</code>. ID
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.id</code>. ID
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.chatlist_id</code>. wx_hr_chat_list.id
     */
    public void setChatlistId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.chatlist_id</code>. wx_hr_chat_list.id
     */
    public Integer getChatlistId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.content</code>. 聊天内容
     */
    public void setContent(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.content</code>. 聊天内容
     */
    public String getContent() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.pid</code>. hr_position.id
     */
    public void setPid(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.pid</code>. hr_position.id
     */
    public Integer getPid() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.speaker</code>. 聊天的发起人，0：求职者(websocket)，1：HR(hr后台回复，或者sysplat 仟寻回复聚合号的求职者，或者chatbot自动回复)
     */
    public void setSpeaker(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.speaker</code>. 聊天的发起人，0：求职者(websocket)，1：HR(hr后台回复，或者sysplat 仟寻回复聚合号的求职者，或者chatbot自动回复)
     */
    public Byte getSpeaker() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.status</code>. 状态，0：有效，1：无效
     */
    public void setStatus(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.status</code>. 状态，0：有效，1：无效
     */
    public Byte getStatus() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.origin</code>. 来源 0 用户输入(包括求职者和HR)， 1 系统自动生成：欢迎语， 2 AI输入 
     */
    public void setOrigin(Byte value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.origin</code>. 来源 0 用户输入(包括求职者和HR)， 1 系统自动生成：欢迎语， 2 AI输入 
     */
    public Byte getOrigin() {
        return (Byte) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.msg_type</code>. 消息类型：html,qrcode,image,button,job,voice,cards,jobCard,citySelect,teamSelect,redisrect,jobSelect,employeeBind
     */
    public void setMsgType(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.msg_type</code>. 消息类型：html,qrcode,image,button,job,voice,cards,jobCard,citySelect,teamSelect,redisrect,jobSelect,employeeBind
     */
    public String getMsgType() {
        return (String) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.pic_url</code>. 图片url
     */
    public void setPicUrl(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.pic_url</code>. 图片url
     */
    public String getPicUrl() {
        return (String) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.btn_content</code>. 控件类信息,当字段msg_type值为为"button_radio"时,会保存json格式:"[{"content": "\u662f"}, {"content": "\u5426"}]"
     */
    public void setBtnContent(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.btn_content</code>. 控件类信息,当字段msg_type值为为"button_radio"时,会保存json格式:"[{"content": "\u662f"}, {"content": "\u5426"}]"
     */
    public String getBtnContent() {
        return (String) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.compound_content</code>. 聊天内容，表单、button等复合字段,保存为json格式
     */
    public void setCompoundContent(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.compound_content</code>. 聊天内容，表单、button等复合字段,保存为json格式
     */
    public String getCompoundContent() {
        return (String) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_wx_hr_chat.stats</code>. 数据统计时使用的参数
     */
    public void setStats(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_hr_chat.stats</code>. 数据统计时使用的参数
     */
    public String getStats() {
        return (String) get(12);
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
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Integer, String, Integer, Byte, Byte, Timestamp, Byte, String, String, String, String, String> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<Integer, Integer, String, Integer, Byte, Byte, Timestamp, Byte, String, String, String, String, String> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrWxHrChat.HR_WX_HR_CHAT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrWxHrChat.HR_WX_HR_CHAT.CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrWxHrChat.HR_WX_HR_CHAT.PID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return HrWxHrChat.HR_WX_HR_CHAT.SPEAKER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field6() {
        return HrWxHrChat.HR_WX_HR_CHAT.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return HrWxHrChat.HR_WX_HR_CHAT.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field8() {
        return HrWxHrChat.HR_WX_HR_CHAT.ORIGIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return HrWxHrChat.HR_WX_HR_CHAT.MSG_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return HrWxHrChat.HR_WX_HR_CHAT.PIC_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return HrWxHrChat.HR_WX_HR_CHAT.BTN_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return HrWxHrChat.HR_WX_HR_CHAT.COMPOUND_CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return HrWxHrChat.HR_WX_HR_CHAT.STATS;
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
        return getChatlistId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getPid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getSpeaker();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value6() {
        return getStatus();
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
    public Byte value8() {
        return getOrigin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getMsgType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getPicUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getBtnContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getCompoundContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getStats();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value2(Integer value) {
        setChatlistId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value3(String value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value4(Integer value) {
        setPid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value5(Byte value) {
        setSpeaker(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value6(Byte value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value7(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value8(Byte value) {
        setOrigin(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value9(String value) {
        setMsgType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value10(String value) {
        setPicUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value11(String value) {
        setBtnContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value12(String value) {
        setCompoundContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord value13(String value) {
        setStats(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatRecord values(Integer value1, Integer value2, String value3, Integer value4, Byte value5, Byte value6, Timestamp value7, Byte value8, String value9, String value10, String value11, String value12, String value13) {
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
        value11(value11);
        value12(value12);
        value13(value13);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrWxHrChatRecord
     */
    public HrWxHrChatRecord() {
        super(HrWxHrChat.HR_WX_HR_CHAT);
    }

    /**
     * Create a detached, initialised HrWxHrChatRecord
     */
    public HrWxHrChatRecord(Integer id, Integer chatlistId, String content, Integer pid, Byte speaker, Byte status, Timestamp createTime, Byte origin, String msgType, String picUrl, String btnContent, String compoundContent, String stats) {
        super(HrWxHrChat.HR_WX_HR_CHAT);

        set(0, id);
        set(1, chatlistId);
        set(2, content);
        set(3, pid);
        set(4, speaker);
        set(5, status);
        set(6, createTime);
        set(7, origin);
        set(8, msgType);
        set(9, picUrl);
        set(10, btnContent);
        set(11, compoundContent);
        set(12, stats);
    }
}
