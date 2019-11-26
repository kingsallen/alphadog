/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserChatPositionCardRecordRecord;

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
 * 员工和候选人聊天室的职位卡片显示记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserChatPositionCardRecord extends TableImpl<UserChatPositionCardRecordRecord> {

    private static final long serialVersionUID = 1566414305;

    /**
     * The reference instance of <code>userdb.user_chat_position_card_record</code>
     */
    public static final UserChatPositionCardRecord USER_CHAT_POSITION_CARD_RECORD = new UserChatPositionCardRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserChatPositionCardRecordRecord> getRecordType() {
        return UserChatPositionCardRecordRecord.class;
    }

    /**
     * The column <code>userdb.user_chat_position_card_record.id</code>. ID
     */
    public final TableField<UserChatPositionCardRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "ID");

    /**
     * The column <code>userdb.user_chat_position_card_record.position_id</code>. 职位ID
     */
    public final TableField<UserChatPositionCardRecordRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "职位ID");

    /**
     * The column <code>userdb.user_chat_position_card_record.room_id</code>. user_chat_room.id
     */
    public final TableField<UserChatPositionCardRecordRecord, Integer> ROOM_ID = createField("room_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "user_chat_room.id");

    /**
     * The column <code>userdb.user_chat_position_card_record.create_time</code>. 创建时间
     */
    public final TableField<UserChatPositionCardRecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>userdb.user_chat_position_card_record.update_time</code>. 修改时间
     */
    public final TableField<UserChatPositionCardRecordRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * Create a <code>userdb.user_chat_position_card_record</code> table reference
     */
    public UserChatPositionCardRecord() {
        this("user_chat_position_card_record", null);
    }

    /**
     * Create an aliased <code>userdb.user_chat_position_card_record</code> table reference
     */
    public UserChatPositionCardRecord(String alias) {
        this(alias, USER_CHAT_POSITION_CARD_RECORD);
    }

    private UserChatPositionCardRecord(String alias, Table<UserChatPositionCardRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserChatPositionCardRecord(String alias, Table<UserChatPositionCardRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "员工和候选人聊天室的职位卡片显示记录");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Userdb.USERDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserChatPositionCardRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_CHAT_POSITION_CARD_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserChatPositionCardRecordRecord> getPrimaryKey() {
        return Keys.KEY_USER_CHAT_POSITION_CARD_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserChatPositionCardRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<UserChatPositionCardRecordRecord>>asList(Keys.KEY_USER_CHAT_POSITION_CARD_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserChatPositionCardRecord as(String alias) {
        return new UserChatPositionCardRecord(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserChatPositionCardRecord rename(String name) {
        return new UserChatPositionCardRecord(name, null);
    }
}