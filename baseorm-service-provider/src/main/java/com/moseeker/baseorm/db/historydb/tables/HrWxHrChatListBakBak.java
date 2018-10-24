/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HrWxHrChatListBakBakRecord;

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
 * IM聊天人关系
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxHrChatListBakBak extends TableImpl<HrWxHrChatListBakBakRecord> {

    private static final long serialVersionUID = 1787443495;

    /**
     * The reference instance of <code>historydb.hr_wx_hr_chat_list_bak_bak</code>
     */
    public static final HrWxHrChatListBakBak HR_WX_HR_CHAT_LIST_BAK_BAK = new HrWxHrChatListBakBak();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrWxHrChatListBakBakRecord> getRecordType() {
        return HrWxHrChatListBakBakRecord.class;
    }

    /**
     * The column <code>historydb.hr_wx_hr_chat_list_bak_bak.id</code>. ID
     */
    public final TableField<HrWxHrChatListBakBakRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "ID");

    /**
     * The column <code>historydb.hr_wx_hr_chat_list_bak_bak.sysuser_id</code>. sysuser.id
     */
    public final TableField<HrWxHrChatListBakBakRecord, Integer> SYSUSER_ID = createField("sysuser_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sysuser.id");

    /**
     * The column <code>historydb.hr_wx_hr_chat_list_bak_bak.hraccount_id</code>. hr_account.id
     */
    public final TableField<HrWxHrChatListBakBakRecord, Integer> HRACCOUNT_ID = createField("hraccount_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_account.id");

    /**
     * The column <code>historydb.hr_wx_hr_chat_list_bak_bak.status</code>. 状态，0：有效，1：无效
     */
    public final TableField<HrWxHrChatListBakBakRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "状态，0：有效，1：无效");

    /**
     * The column <code>historydb.hr_wx_hr_chat_list_bak_bak.create_time</code>. 创建时间
     */
    public final TableField<HrWxHrChatListBakBakRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>historydb.hr_wx_hr_chat_list_bak_bak.wx_chat_time</code>. sysuser最近一次聊天时间
     */
    public final TableField<HrWxHrChatListBakBakRecord, Timestamp> WX_CHAT_TIME = createField("wx_chat_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "sysuser最近一次聊天时间");

    /**
     * The column <code>historydb.hr_wx_hr_chat_list_bak_bak.hr_chat_time</code>. HR最近一次聊天时间
     */
    public final TableField<HrWxHrChatListBakBakRecord, Timestamp> HR_CHAT_TIME = createField("hr_chat_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "HR最近一次聊天时间");

    /**
     * The column <code>historydb.hr_wx_hr_chat_list_bak_bak.update_time</code>. 创建时间
     */
    public final TableField<HrWxHrChatListBakBakRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * Create a <code>historydb.hr_wx_hr_chat_list_bak_bak</code> table reference
     */
    public HrWxHrChatListBakBak() {
        this("hr_wx_hr_chat_list_bak_bak", null);
    }

    /**
     * Create an aliased <code>historydb.hr_wx_hr_chat_list_bak_bak</code> table reference
     */
    public HrWxHrChatListBakBak(String alias) {
        this(alias, HR_WX_HR_CHAT_LIST_BAK_BAK);
    }

    private HrWxHrChatListBakBak(String alias, Table<HrWxHrChatListBakBakRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrWxHrChatListBakBak(String alias, Table<HrWxHrChatListBakBakRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "IM聊天人关系");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Historydb.HISTORYDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<HrWxHrChatListBakBakRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_WX_HR_CHAT_LIST_BAK_BAK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrWxHrChatListBakBakRecord> getPrimaryKey() {
        return Keys.KEY_HR_WX_HR_CHAT_LIST_BAK_BAK_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrWxHrChatListBakBakRecord>> getKeys() {
        return Arrays.<UniqueKey<HrWxHrChatListBakBakRecord>>asList(Keys.KEY_HR_WX_HR_CHAT_LIST_BAK_BAK_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxHrChatListBakBak as(String alias) {
        return new HrWxHrChatListBakBak(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrWxHrChatListBakBak rename(String name) {
        return new HrWxHrChatListBakBak(name, null);
    }
}
