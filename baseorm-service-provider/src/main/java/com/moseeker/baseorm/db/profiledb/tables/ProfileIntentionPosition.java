/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.profiledb.tables;


import com.moseeker.baseorm.db.profiledb.Keys;
import com.moseeker.baseorm.db.profiledb.Profiledb;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionPositionRecord;

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
 * Profile的求职意向-职能关系表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ProfileIntentionPosition extends TableImpl<ProfileIntentionPositionRecord> {

    private static final long serialVersionUID = -804316022;

    /**
     * The reference instance of <code>profiledb.profile_intention_position</code>
     */
    public static final ProfileIntentionPosition PROFILE_INTENTION_POSITION = new ProfileIntentionPosition();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProfileIntentionPositionRecord> getRecordType() {
        return ProfileIntentionPositionRecord.class;
    }

    /**
     * The column <code>profiledb.profile_intention_position.id</code>. 主key
     */
    public final TableField<ProfileIntentionPositionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>profiledb.profile_intention_position.profile_intention_id</code>. profile_intention.id
     */
    public final TableField<ProfileIntentionPositionRecord, Integer> PROFILE_INTENTION_ID = createField("profile_intention_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "profile_intention.id");

    /**
     * The column <code>profiledb.profile_intention_position.position_code</code>. 职能字典编码
     */
    public final TableField<ProfileIntentionPositionRecord, Integer> POSITION_CODE = createField("position_code", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "职能字典编码");

    /**
     * The column <code>profiledb.profile_intention_position.position_name</code>. 职能名称
     */
    public final TableField<ProfileIntentionPositionRecord, String> POSITION_NAME = createField("position_name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "职能名称");

    /**
     * Create a <code>profiledb.profile_intention_position</code> table reference
     */
    public ProfileIntentionPosition() {
        this("profile_intention_position", null);
    }

    /**
     * Create an aliased <code>profiledb.profile_intention_position</code> table reference
     */
    public ProfileIntentionPosition(String alias) {
        this(alias, PROFILE_INTENTION_POSITION);
    }

    private ProfileIntentionPosition(String alias, Table<ProfileIntentionPositionRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProfileIntentionPosition(String alias, Table<ProfileIntentionPositionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "Profile的求职意向-职能关系表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Profiledb.PROFILEDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ProfileIntentionPositionRecord, Integer> getIdentity() {
        return Keys.IDENTITY_PROFILE_INTENTION_POSITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProfileIntentionPositionRecord> getPrimaryKey() {
        return Keys.KEY_PROFILE_INTENTION_POSITION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProfileIntentionPositionRecord>> getKeys() {
        return Arrays.<UniqueKey<ProfileIntentionPositionRecord>>asList(Keys.KEY_PROFILE_INTENTION_POSITION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileIntentionPosition as(String alias) {
        return new ProfileIntentionPosition(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProfileIntentionPosition rename(String name) {
        return new ProfileIntentionPosition(name, null);
    }
}
