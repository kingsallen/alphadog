/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNoticeMessageRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * 微信消息通知, first和remark文案暂不使用
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxNoticeMessage extends TableImpl<HrWxNoticeMessageRecord> {

    private static final long serialVersionUID = 1715464743;

    /**
     * The reference instance of <code>hrdb.hr_wx_notice_message</code>
     */
    public static final HrWxNoticeMessage HR_WX_NOTICE_MESSAGE = new HrWxNoticeMessage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrWxNoticeMessageRecord> getRecordType() {
        return HrWxNoticeMessageRecord.class;
    }

    /**
     * The column <code>hrdb.hr_wx_notice_message.id</code>. 主key
     */
    public final TableField<HrWxNoticeMessageRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

    /**
     * The column <code>hrdb.hr_wx_notice_message.wechat_id</code>. 所属公众号
     */
    public final TableField<HrWxNoticeMessageRecord, UInteger> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.field("10", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "所属公众号");

    /**
     * The column <code>hrdb.hr_wx_notice_message.notice_id</code>. sys_notice_message.id
     */
    public final TableField<HrWxNoticeMessageRecord, Integer> NOTICE_ID = createField("notice_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "sys_notice_message.id");

    /**
     * The column <code>hrdb.hr_wx_notice_message.first</code>. 消息模板first文案
     */
    public final TableField<HrWxNoticeMessageRecord, String> FIRST = createField("first", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "消息模板first文案");

    /**
     * The column <code>hrdb.hr_wx_notice_message.remark</code>. 消息模板remark文案
     */
    public final TableField<HrWxNoticeMessageRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "消息模板remark文案");

    /**
     * The column <code>hrdb.hr_wx_notice_message.status</code>. 是否开启, 1:开启, 0:关闭
     */
    public final TableField<HrWxNoticeMessageRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.TINYINT)), this, "是否开启, 1:开启, 0:关闭");

    /**
     * Create a <code>hrdb.hr_wx_notice_message</code> table reference
     */
    public HrWxNoticeMessage() {
        this("hr_wx_notice_message", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_wx_notice_message</code> table reference
     */
    public HrWxNoticeMessage(String alias) {
        this(alias, HR_WX_NOTICE_MESSAGE);
    }

    private HrWxNoticeMessage(String alias, Table<HrWxNoticeMessageRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrWxNoticeMessage(String alias, Table<HrWxNoticeMessageRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "微信消息通知, first和remark文案暂不使用");
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
    public Identity<HrWxNoticeMessageRecord, UInteger> getIdentity() {
        return Keys.IDENTITY_HR_WX_NOTICE_MESSAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrWxNoticeMessageRecord> getPrimaryKey() {
        return Keys.KEY_HR_WX_NOTICE_MESSAGE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrWxNoticeMessageRecord>> getKeys() {
        return Arrays.<UniqueKey<HrWxNoticeMessageRecord>>asList(Keys.KEY_HR_WX_NOTICE_MESSAGE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxNoticeMessage as(String alias) {
        return new HrWxNoticeMessage(alias, this);
    }

    /**
     * Rename this table
     */
    public HrWxNoticeMessage rename(String name) {
        return new HrWxNoticeMessage(name, null);
    }
}
