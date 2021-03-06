/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.analytics.tables.records;


import com.moseeker.baseorm.db.analytics.tables.WebPlaDaily;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record15;
import org.jooq.Row15;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 每日浏览申请统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WebPlaDailyRecord extends UpdatableRecordImpl<WebPlaDailyRecord> implements Record15<Integer, Integer, Timestamp, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = -1646939417;

    /**
     * Setter for <code>analytics.web_pla_daily.id</code>. primary key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.id</code>. primary key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.cid</code>.
     */
    public void setCid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.cid</code>.
     */
    public Integer getCid() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.create_time</code>.
     */
    public void setCreateTime(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.create_time</code>.
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.p_pv</code>. 职位页面浏览量
     */
    public void setPPv(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.p_pv</code>. 职位页面浏览量
     */
    public Integer getPPv() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.p_uv</code>. 职位页面浏览用户量
     */
    public void setPUv(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.p_uv</code>. 职位页面浏览用户量
     */
    public Integer getPUv() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.p_s_pv</code>. 职位页面转发浏览量
     */
    public void setPSPv(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.p_s_pv</code>. 职位页面转发浏览量
     */
    public Integer getPSPv() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.p_s_uv</code>. 职位页面转发浏览用户量
     */
    public void setPSUv(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.p_s_uv</code>. 职位页面转发浏览用户量
     */
    public Integer getPSUv() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.l_pv</code>. 职位列表页面浏览量
     */
    public void setLPv(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.l_pv</code>. 职位列表页面浏览量
     */
    public Integer getLPv() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.l_uv</code>. 职位列表页面浏览用户量
     */
    public void setLUv(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.l_uv</code>. 职位列表页面浏览用户量
     */
    public Integer getLUv() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.l_s_pv</code>. 职位列表页面转发浏览量
     */
    public void setLSPv(Integer value) {
        set(9, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.l_s_pv</code>. 职位列表页面转发浏览量
     */
    public Integer getLSPv() {
        return (Integer) get(9);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.l_s_uv</code>. 职位列表页面转发浏览用户量
     */
    public void setLSUv(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.l_s_uv</code>. 职位列表页面转发浏览用户量
     */
    public Integer getLSUv() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.a_count</code>. 申请量
     */
    public void setACount(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.a_count</code>. 申请量
     */
    public Integer getACount() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.a_user</code>. 申请人数
     */
    public void setAUser(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.a_user</code>. 申请人数
     */
    public Integer getAUser() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.a_s_count</code>. 转发产生的申请量
     */
    public void setASCount(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.a_s_count</code>. 转发产生的申请量
     */
    public Integer getASCount() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>analytics.web_pla_daily.a_s_user</code>. 转发产生的申请人数
     */
    public void setASUser(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>analytics.web_pla_daily.a_s_user</code>. 转发产生的申请人数
     */
    public Integer getASUser() {
        return (Integer) get(14);
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
    // Record15 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, Integer, Timestamp, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row15) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row15<Integer, Integer, Timestamp, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> valuesRow() {
        return (Row15) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return WebPlaDaily.WEB_PLA_DAILY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return WebPlaDaily.WEB_PLA_DAILY.CID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return WebPlaDaily.WEB_PLA_DAILY.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return WebPlaDaily.WEB_PLA_DAILY.P_PV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return WebPlaDaily.WEB_PLA_DAILY.P_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return WebPlaDaily.WEB_PLA_DAILY.P_S_PV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return WebPlaDaily.WEB_PLA_DAILY.P_S_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return WebPlaDaily.WEB_PLA_DAILY.L_PV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field9() {
        return WebPlaDaily.WEB_PLA_DAILY.L_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field10() {
        return WebPlaDaily.WEB_PLA_DAILY.L_S_PV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field11() {
        return WebPlaDaily.WEB_PLA_DAILY.L_S_UV;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return WebPlaDaily.WEB_PLA_DAILY.A_COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return WebPlaDaily.WEB_PLA_DAILY.A_USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field14() {
        return WebPlaDaily.WEB_PLA_DAILY.A_S_COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field15() {
        return WebPlaDaily.WEB_PLA_DAILY.A_S_USER;
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
        return getCid();
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
    public Integer value4() {
        return getPPv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getPUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getPSPv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getPSUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getLPv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value9() {
        return getLUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value10() {
        return getLSPv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value11() {
        return getLSUv();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getACount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getAUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value14() {
        return getASCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value15() {
        return getASUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value2(Integer value) {
        setCid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value3(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value4(Integer value) {
        setPPv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value5(Integer value) {
        setPUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value6(Integer value) {
        setPSPv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value7(Integer value) {
        setPSUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value8(Integer value) {
        setLPv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value9(Integer value) {
        setLUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value10(Integer value) {
        setLSPv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value11(Integer value) {
        setLSUv(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value12(Integer value) {
        setACount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value13(Integer value) {
        setAUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value14(Integer value) {
        setASCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord value15(Integer value) {
        setASUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebPlaDailyRecord values(Integer value1, Integer value2, Timestamp value3, Integer value4, Integer value5, Integer value6, Integer value7, Integer value8, Integer value9, Integer value10, Integer value11, Integer value12, Integer value13, Integer value14, Integer value15) {
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
        value14(value14);
        value15(value15);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WebPlaDailyRecord
     */
    public WebPlaDailyRecord() {
        super(WebPlaDaily.WEB_PLA_DAILY);
    }

    /**
     * Create a detached, initialised WebPlaDailyRecord
     */
    public WebPlaDailyRecord(Integer id, Integer cid, Timestamp createTime, Integer pPv, Integer pUv, Integer pSPv, Integer pSUv, Integer lPv, Integer lUv, Integer lSPv, Integer lSUv, Integer aCount, Integer aUser, Integer aSCount, Integer aSUser) {
        super(WebPlaDaily.WEB_PLA_DAILY);

        set(0, id);
        set(1, cid);
        set(2, createTime);
        set(3, pPv);
        set(4, pUv);
        set(5, pSPv);
        set(6, pSUv);
        set(7, lPv);
        set(8, lUv);
        set(9, lSPv);
        set(10, lSUv);
        set(11, aCount);
        set(12, aUser);
        set(13, aSCount);
        set(14, aSUser);
    }
}
