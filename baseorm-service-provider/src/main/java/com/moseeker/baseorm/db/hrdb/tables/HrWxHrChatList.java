/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.jooq.*;
import org.jooq.impl.TableImpl;


/**
 * IM聊天人关系
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxHrChatList extends TableImpl<HrWxHrChatListRecord> {

    private static final long serialVersionUID = -12922065;

    /**
     * The reference instance of <code>hrdb.hr_wx_hr_chat_list</code>
     */
    public static final HrWxHrChatList HR_WX_HR_CHAT_LIST = new HrWxHrChatList();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrWxHrChatListRecord> getRecordType() {
        return HrWxHrChatListRecord.class;
    }

    /**
     * The column <code>hrdb.hr_wx_hr_chat_list.id</code>. ID
     */
    public final TableField<HrWxHrChatListRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "ID");

    /**
     * The column <code>hrdb.hr_wx_hr_chat_list.sysuser_id</code>. sysuser.id
     */
    public final TableField<HrWxHrChatListRecord, Integer> SYSUSER_ID = createField("sysuser_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sysuser.id");

    /**
     * The column <code>hrdb.hr_wx_hr_chat_list.hraccount_id</code>. hr_account.id
     */
    public final TableField<HrWxHrChatListRecord, Integer> HRACCOUNT_ID = createField("hraccount_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_account.id");

    /**
     * The column <code>hrdb.hr_wx_hr_chat_list.create_time</code>. 创建时间
     */
    public final TableField<HrWxHrChatListRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_wx_hr_chat_list.wx_chat_time</code>. sysuser最近一次聊天时间
     */
    public final TableField<HrWxHrChatListRecord, Timestamp> WX_CHAT_TIME = createField("wx_chat_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "sysuser最近一次聊天时间");

    /**
     * The column <code>hrdb.hr_wx_hr_chat_list.hr_chat_time</code>. HR最近一次聊天时间
     */
    public final TableField<HrWxHrChatListRecord, Timestamp> HR_CHAT_TIME = createField("hr_chat_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "HR最近一次聊天时间");

    /**
     * The column <code>hrdb.hr_wx_hr_chat_list.update_time</code>. 更新时间
     */
    public final TableField<HrWxHrChatListRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>hrdb.hr_wx_hr_chat_list.hr_unread_count</code>. hr未读消息数量
     */
    public final TableField<HrWxHrChatListRecord, Integer> HR_UNREAD_COUNT = createField("hr_unread_count", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr未读消息数量");

    /**
     * The column <code>hrdb.hr_wx_hr_chat_list.user_unread_count</code>. C端用户未读消息数量
     */
    public final TableField<HrWxHrChatListRecord, Integer> USER_UNREAD_COUNT = createField("user_unread_count", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "C端用户未读消息数量");

    /**
     * Create a <code>hrdb.hr_wx_hr_chat_list</code> table reference
     */
    public HrWxHrChatList() {
        this("hr_wx_hr_chat_list", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_wx_hr_chat_list</code> table reference
     */
    public HrWxHrChatList(String alias) {
        this(alias, HR_WX_HR_CHAT_LIST);
    }

    private HrWxHrChatList(String alias, Table<HrWxHrChatListRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrWxHrChatList(String alias, Table<HrWxHrChatListRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "IM聊天人关系");
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
    public Identity<HrWxHrChatListRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_WX_HR_CHAT_LIST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrWxHrChatListRecord> getPrimaryKey() {
        return Keys.KEY_HR_WX_HR_CHAT_LIST_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrWxHrChatListRecord>> getKeys() {
        return Arrays.<UniqueKey<HrWxHrChatListRecord>>asList(Keys.KEY_HR_WX_HR_CHAT_LIST_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatList as(String alias) {
        return new HrWxHrChatList(alias, this);
    }

    /**
     * Rename this table
     */
    public HrWxHrChatList rename(String name) {
        return new HrWxHrChatList(name, null);
    }
}
