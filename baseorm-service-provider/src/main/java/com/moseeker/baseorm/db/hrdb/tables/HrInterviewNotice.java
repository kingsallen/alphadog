/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrInterviewNoticeRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 面试提醒表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrInterviewNotice extends TableImpl<HrInterviewNoticeRecord> {

    private static final long serialVersionUID = -1666132916;

    /**
     * The reference instance of <code>hrdb.hr_interview_notice</code>
     */
    public static final HrInterviewNotice HR_INTERVIEW_NOTICE = new HrInterviewNotice();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrInterviewNoticeRecord> getRecordType() {
        return HrInterviewNoticeRecord.class;
    }

    /**
     * The column <code>hrdb.hr_interview_notice.id</code>.
     */
    public final TableField<HrInterviewNoticeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_interview_notice.interview_id</code>. 面试的id
     */
    public final TableField<HrInterviewNoticeRecord, Integer> INTERVIEW_ID = createField("interview_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "面试的id");

    /**
     * The column <code>hrdb.hr_interview_notice.receive_id</code>. 接收人id
     */
    public final TableField<HrInterviewNoticeRecord, Integer> RECEIVE_ID = createField("receive_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "接收人id");

    /**
     * The column <code>hrdb.hr_interview_notice.receive_type</code>. 接收人类型0 面试官 1求职者
     */
    public final TableField<HrInterviewNoticeRecord, Integer> RECEIVE_TYPE = createField("receive_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "接收人类型0 面试官 1求职者");

    /**
     * The column <code>hrdb.hr_interview_notice.notice_type</code>. 消息类型 0 是消息模板 1是邮件 2是短信
     */
    public final TableField<HrInterviewNoticeRecord, Integer> NOTICE_TYPE = createField("notice_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "消息类型 0 是消息模板 1是邮件 2是短信");

    /**
     * The column <code>hrdb.hr_interview_notice.notice_time</code>. 提醒时间
     */
    public final TableField<HrInterviewNoticeRecord, Timestamp> NOTICE_TIME = createField("notice_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "提醒时间");

    /**
     * The column <code>hrdb.hr_interview_notice.send</code>. 是否已经发送 0 未发送 1已发送
     */
    public final TableField<HrInterviewNoticeRecord, Integer> SEND = createField("send", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否已经发送 0 未发送 1已发送");

    /**
     * The column <code>hrdb.hr_interview_notice.fail_count</code>. 发送失败次数
     */
    public final TableField<HrInterviewNoticeRecord, Integer> FAIL_COUNT = createField("fail_count", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "发送失败次数");

    /**
     * The column <code>hrdb.hr_interview_notice.notice_scene</code>. 通知场景 1: 面试通知，2：取消面试通知
     */
    public final TableField<HrInterviewNoticeRecord, Integer> NOTICE_SCENE = createField("notice_scene", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "通知场景 1: 面试通知，2：取消面试通知");

    /**
     * The column <code>hrdb.hr_interview_notice.disable</code>. 状态：0 有效  1 无效 2 逻辑删除
     */
    public final TableField<HrInterviewNoticeRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态：0 有效  1 无效 2 逻辑删除");

    /**
     * The column <code>hrdb.hr_interview_notice.create_time</code>. 创建时间
     */
    public final TableField<HrInterviewNoticeRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_interview_notice.update_time</code>. 更新时间
     */
    public final TableField<HrInterviewNoticeRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>hrdb.hr_interview_notice</code> table reference
     */
    public HrInterviewNotice() {
        this("hr_interview_notice", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_interview_notice</code> table reference
     */
    public HrInterviewNotice(String alias) {
        this(alias, HR_INTERVIEW_NOTICE);
    }

    private HrInterviewNotice(String alias, Table<HrInterviewNoticeRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrInterviewNotice(String alias, Table<HrInterviewNoticeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "面试提醒表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Hrdb.HRDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<HrInterviewNoticeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_INTERVIEW_NOTICE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrInterviewNoticeRecord> getPrimaryKey() {
        return Keys.KEY_HR_INTERVIEW_NOTICE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrInterviewNoticeRecord>> getKeys() {
        return Arrays.<UniqueKey<HrInterviewNoticeRecord>>asList(Keys.KEY_HR_INTERVIEW_NOTICE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrInterviewNotice as(String alias) {
        return new HrInterviewNotice(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrInterviewNotice rename(String name) {
        return new HrInterviewNotice(name, null);
    }
}