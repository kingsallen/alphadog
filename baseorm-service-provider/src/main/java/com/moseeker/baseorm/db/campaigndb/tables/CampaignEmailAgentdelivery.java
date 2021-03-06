/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.campaigndb.tables;


import com.moseeker.baseorm.db.campaigndb.Campaigndb;
import com.moseeker.baseorm.db.campaigndb.Keys;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignEmailAgentdeliveryRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CampaignEmailAgentdelivery extends TableImpl<CampaignEmailAgentdeliveryRecord> {

    private static final long serialVersionUID = -115509642;

    /**
     * The reference instance of <code>campaigndb.campaign_email_agentdelivery</code>
     */
    public static final CampaignEmailAgentdelivery CAMPAIGN_EMAIL_AGENTDELIVERY = new CampaignEmailAgentdelivery();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CampaignEmailAgentdeliveryRecord> getRecordType() {
        return CampaignEmailAgentdeliveryRecord.class;
    }

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.id</code>.
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.company_id</code>. hr_company.id
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_company.id");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.position_id</code>. job_position.id
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "job_position.id");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.employee_id</code>. hr_employee.id
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, Integer> EMPLOYEE_ID = createField("employee_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_employee.id");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.friendname</code>. 推荐朋友姓名
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, String> FRIENDNAME = createField("friendname", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "推荐朋友姓名");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.email</code>. 推荐朋友的邮
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "推荐朋友的邮");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.status</code>. 该条记录的状态
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "该条记录的状态");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.fname</code>. 附件原始名称
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, String> FNAME = createField("fname", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "附件原始名称");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.uname</code>. 附件存储名称
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, String> UNAME = createField("uname", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "附件存储名称");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.code</code>. 发送邮件时携带的code
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, String> CODE = createField("code", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "发送邮件时携带的code");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.create_time</code>. 记录创建时间
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "记录创建时间");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.update_time</code>. 更新时间
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>campaigndb.campaign_email_agentdelivery.description</code>. 上传文件描述
     */
    public final TableField<CampaignEmailAgentdeliveryRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "上传文件描述");

    /**
     * Create a <code>campaigndb.campaign_email_agentdelivery</code> table reference
     */
    public CampaignEmailAgentdelivery() {
        this("campaign_email_agentdelivery", null);
    }

    /**
     * Create an aliased <code>campaigndb.campaign_email_agentdelivery</code> table reference
     */
    public CampaignEmailAgentdelivery(String alias) {
        this(alias, CAMPAIGN_EMAIL_AGENTDELIVERY);
    }

    private CampaignEmailAgentdelivery(String alias, Table<CampaignEmailAgentdeliveryRecord> aliased) {
        this(alias, aliased, null);
    }

    private CampaignEmailAgentdelivery(String alias, Table<CampaignEmailAgentdeliveryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Campaigndb.CAMPAIGNDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CampaignEmailAgentdeliveryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CAMPAIGN_EMAIL_AGENTDELIVERY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CampaignEmailAgentdeliveryRecord> getPrimaryKey() {
        return Keys.KEY_CAMPAIGN_EMAIL_AGENTDELIVERY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CampaignEmailAgentdeliveryRecord>> getKeys() {
        return Arrays.<UniqueKey<CampaignEmailAgentdeliveryRecord>>asList(Keys.KEY_CAMPAIGN_EMAIL_AGENTDELIVERY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignEmailAgentdelivery as(String alias) {
        return new CampaignEmailAgentdelivery(alias, this);
    }

    /**
     * Rename this table
     */

    public CampaignEmailAgentdelivery rename(String name) {
        return new CampaignEmailAgentdelivery(name, null);
    }
}
