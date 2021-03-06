/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables;


import com.moseeker.baseorm.db.talentpooldb.Keys;
import com.moseeker.baseorm.db.talentpooldb.Talentpooldb;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyManualTagUserRecord;

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


/**
 * 企业手动标签与人才关联表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolCompanyManualTagUser extends TableImpl<TalentpoolCompanyManualTagUserRecord> {

    private static final long serialVersionUID = -1012195910;

    /**
     * The reference instance of <code>talentpooldb.talentpool_company_manual_tag_user</code>
     */
    public static final TalentpoolCompanyManualTagUser TALENTPOOL_COMPANY_MANUAL_TAG_USER = new TalentpoolCompanyManualTagUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TalentpoolCompanyManualTagUserRecord> getRecordType() {
        return TalentpoolCompanyManualTagUserRecord.class;
    }

    /**
     * The column <code>talentpooldb.talentpool_company_manual_tag_user.user_id</code>. 用户编号
     */
    public final TableField<TalentpoolCompanyManualTagUserRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "用户编号");

    /**
     * The column <code>talentpooldb.talentpool_company_manual_tag_user.tag_id</code>. 企业手动标签ID
     */
    public final TableField<TalentpoolCompanyManualTagUserRecord, Integer> TAG_ID = createField("tag_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "企业手动标签ID");

    /**
     * The column <code>talentpooldb.talentpool_company_manual_tag_user.create_time</code>. 创建时间
     */
    public final TableField<TalentpoolCompanyManualTagUserRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>talentpooldb.talentpool_company_manual_tag_user.update_time</code>.
     */
    public final TableField<TalentpoolCompanyManualTagUserRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>talentpooldb.talentpool_company_manual_tag_user</code> table reference
     */
    public TalentpoolCompanyManualTagUser() {
        this("talentpool_company_manual_tag_user", null);
    }

    /**
     * Create an aliased <code>talentpooldb.talentpool_company_manual_tag_user</code> table reference
     */
    public TalentpoolCompanyManualTagUser(String alias) {
        this(alias, TALENTPOOL_COMPANY_MANUAL_TAG_USER);
    }

    private TalentpoolCompanyManualTagUser(String alias, Table<TalentpoolCompanyManualTagUserRecord> aliased) {
        this(alias, aliased, null);
    }

    private TalentpoolCompanyManualTagUser(String alias, Table<TalentpoolCompanyManualTagUserRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "企业手动标签与人才关联表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Talentpooldb.TALENTPOOLDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TalentpoolCompanyManualTagUserRecord> getPrimaryKey() {
        return Keys.KEY_TALENTPOOL_COMPANY_MANUAL_TAG_USER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TalentpoolCompanyManualTagUserRecord>> getKeys() {
        return Arrays.<UniqueKey<TalentpoolCompanyManualTagUserRecord>>asList(Keys.KEY_TALENTPOOL_COMPANY_MANUAL_TAG_USER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TalentpoolCompanyManualTagUser as(String alias) {
        return new TalentpoolCompanyManualTagUser(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TalentpoolCompanyManualTagUser rename(String name) {
        return new TalentpoolCompanyManualTagUser(name, null);
    }
}
