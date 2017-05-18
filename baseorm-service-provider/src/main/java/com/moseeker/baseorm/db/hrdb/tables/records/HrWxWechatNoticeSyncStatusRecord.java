/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrWxWechatNoticeSyncStatus;
import java.sql.Timestamp;
import javax.annotation.Generated;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 微信消息通知同步状态
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxWechatNoticeSyncStatusRecord extends UpdatableRecordImpl<HrWxWechatNoticeSyncStatusRecord> implements Record5<Integer, Integer, Integer, Integer, Timestamp> {

    private static final long serialVersionUID = -822320946;

    /**
     * Setter for <code>hrdb.hr_wx_wechat_notice_sync_status.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_notice_sync_status.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_notice_sync_status.wechat_id</code>. 所属公众号
     */
    public void setWechatId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_notice_sync_status.wechat_id</code>. 所属公众号
     */
    public Integer getWechatId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_notice_sync_status.status</code>. 同步状态 0:成功, 1:行业修改失败, 2:模板数量超出上限, 3:接口调用失败
     */
    public void setStatus(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_notice_sync_status.status</code>. 同步状态 0:成功, 1:行业修改失败, 2:模板数量超出上限, 3:接口调用失败
     */
    public Integer getStatus() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_notice_sync_status.count</code>. 同步状态提示应该删除信息的数量
     */
    public void setCount(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_notice_sync_status.count</code>. 同步状态提示应该删除信息的数量
     */
    public Integer getCount() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_wx_wechat_notice_sync_status.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_wx_wechat_notice_sync_status.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(4);
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
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Integer, Integer, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Integer, Integer, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.WECHAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.COUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.UPDATE_TIME;
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
    public Integer value3() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatNoticeSyncStatusRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatNoticeSyncStatusRecord value2(Integer value) {
        setWechatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatNoticeSyncStatusRecord value3(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatNoticeSyncStatusRecord value4(Integer value) {
        setCount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatNoticeSyncStatusRecord value5(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatNoticeSyncStatusRecord values(Integer value1, Integer value2, Integer value3, Integer value4, Timestamp value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrWxWechatNoticeSyncStatusRecord
     */
    public HrWxWechatNoticeSyncStatusRecord() {
        super(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS);
    }

    /**
     * Create a detached, initialised HrWxWechatNoticeSyncStatusRecord
     */
    public HrWxWechatNoticeSyncStatusRecord(Integer id, Integer wechatId, Integer status, Integer count, Timestamp updateTime) {
        super(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS);

        set(0, id);
        set(1, wechatId);
        set(2, status);
        set(3, count);
        set(4, updateTime);
    }
}
