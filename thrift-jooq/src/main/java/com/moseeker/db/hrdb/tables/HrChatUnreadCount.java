/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.hrdb.tables;


import com.moseeker.db.hrdb.Hrdb;
import com.moseeker.db.hrdb.Keys;
import com.moseeker.db.hrdb.tables.records.HrChatUnreadCountRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;


/**
 * 聊天室未读消息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrChatUnreadCount extends TableImpl<HrChatUnreadCountRecord> {

    private static final long serialVersionUID = 1153513400;

    /**
     * The reference instance of <code>hrdb.hr_chat_unread_count</code>
     */
    public static final HrChatUnreadCount HR_CHAT_UNREAD_COUNT = new HrChatUnreadCount();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrChatUnreadCountRecord> getRecordType() {
        return HrChatUnreadCountRecord.class;
    }

    /**
     * The column <code>hrdb.hr_chat_unread_count.room_id</code>. 聊天室编号
     */
    public final TableField<HrChatUnreadCountRecord, UInteger> ROOM_ID = createField("room_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "聊天室编号");

    /**
     * The column <code>hrdb.hr_chat_unread_count.hr_id</code>. HR编号 userdb.user_hr_account
     */
    public final TableField<HrChatUnreadCountRecord, Integer> HR_ID = createField("hr_id", org.jooq.impl.SQLDataType.INTEGER, this, "HR编号 userdb.user_hr_account");

    /**
     * The column <code>hrdb.hr_chat_unread_count.user_id</code>. 用户编号 userdb.user_user.id
     */
    public final TableField<HrChatUnreadCountRecord, UInteger> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED, this, "用户编号 userdb.user_user.id");

    /**
     * The column <code>hrdb.hr_chat_unread_count.wx_chat_time</code>. sysuser最近一次聊天时间
     */
    public final TableField<HrChatUnreadCountRecord, Timestamp> WX_CHAT_TIME = createField("wx_chat_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "sysuser最近一次聊天时间");

    /**
     * The column <code>hrdb.hr_chat_unread_count.hr_chat_time</code>. HR最近一次聊天时间
     */
    public final TableField<HrChatUnreadCountRecord, Timestamp> HR_CHAT_TIME = createField("hr_chat_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "HR最近一次聊天时间");

    /**
     * The column <code>hrdb.hr_chat_unread_count.hr_have_unread_msg</code>. HR是否有未读消息，0：没有，1有未读消息
     */
    public final TableField<HrChatUnreadCountRecord, Byte> HR_HAVE_UNREAD_MSG = createField("hr_have_unread_msg", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "HR是否有未读消息，0：没有，1有未读消息");

    /**
     * The column <code>hrdb.hr_chat_unread_count.user_have_unread_msg</code>. user是否有未读消息 ，0：没有，1有未读消息
     */
    public final TableField<HrChatUnreadCountRecord, Byte> USER_HAVE_UNREAD_MSG = createField("user_have_unread_msg", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "user是否有未读消息 ，0：没有，1有未读消息");

    /**
     * Create a <code>hrdb.hr_chat_unread_count</code> table reference
     */
    public HrChatUnreadCount() {
        this("hr_chat_unread_count", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_chat_unread_count</code> table reference
     */
    public HrChatUnreadCount(String alias) {
        this(alias, HR_CHAT_UNREAD_COUNT);
    }

    private HrChatUnreadCount(String alias, Table<HrChatUnreadCountRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrChatUnreadCount(String alias, Table<HrChatUnreadCountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "聊天室未读消息");
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
    public UniqueKey<HrChatUnreadCountRecord> getPrimaryKey() {
        return Keys.KEY_HR_CHAT_UNREAD_COUNT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrChatUnreadCountRecord>> getKeys() {
        return Arrays.<UniqueKey<HrChatUnreadCountRecord>>asList(Keys.KEY_HR_CHAT_UNREAD_COUNT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrChatUnreadCount as(String alias) {
        return new HrChatUnreadCount(alias, this);
    }

    /**
     * Rename this table
     */
    public HrChatUnreadCount rename(String name) {
        return new HrChatUnreadCount(name, null);
    }
}
