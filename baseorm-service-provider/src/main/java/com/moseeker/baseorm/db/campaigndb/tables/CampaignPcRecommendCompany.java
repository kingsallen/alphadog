/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.campaigndb.tables;


import com.moseeker.baseorm.db.campaigndb.Campaigndb;
import com.moseeker.baseorm.db.campaigndb.Keys;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPcRecommendCompanyRecord;

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
 * 逛公司页面仟寻推荐公司
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CampaignPcRecommendCompany extends TableImpl<CampaignPcRecommendCompanyRecord> {

    private static final long serialVersionUID = 2010056524;

    /**
     * The reference instance of <code>campaigndb.campaign_pc_recommend_company</code>
     */
    public static final CampaignPcRecommendCompany CAMPAIGN_PC_RECOMMEND_COMPANY = new CampaignPcRecommendCompany();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CampaignPcRecommendCompanyRecord> getRecordType() {
        return CampaignPcRecommendCompanyRecord.class;
    }

    /**
     * The column <code>campaigndb.campaign_pc_recommend_company.id</code>.
     */
    public final TableField<CampaignPcRecommendCompanyRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>campaigndb.campaign_pc_recommend_company.module_name</code>. 模块名
     */
    public final TableField<CampaignPcRecommendCompanyRecord, String> MODULE_NAME = createField("module_name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模块名");

    /**
     * The column <code>campaigndb.campaign_pc_recommend_company.module_description</code>. 模块描述
     */
    public final TableField<CampaignPcRecommendCompanyRecord, String> MODULE_DESCRIPTION = createField("module_description", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模块描述");

    /**
     * The column <code>campaigndb.campaign_pc_recommend_company.company_ids</code>. 公司的id格式:id1,id2,id3...
     */
    public final TableField<CampaignPcRecommendCompanyRecord, String> COMPANY_IDS = createField("company_ids", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "公司的id格式:id1,id2,id3...");

    /**
     * The column <code>campaigndb.campaign_pc_recommend_company.disable</code>. 是否可用，0 ：可用， 1 ： 不可用
     */
    public final TableField<CampaignPcRecommendCompanyRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否可用，0 ：可用， 1 ： 不可用");

    /**
     * The column <code>campaigndb.campaign_pc_recommend_company.create_time</code>.
     */
    public final TableField<CampaignPcRecommendCompanyRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>campaigndb.campaign_pc_recommend_company.update_time</code>.
     */
    public final TableField<CampaignPcRecommendCompanyRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>campaigndb.campaign_pc_recommend_company</code> table reference
     */
    public CampaignPcRecommendCompany() {
        this("campaign_pc_recommend_company", null);
    }

    /**
     * Create an aliased <code>campaigndb.campaign_pc_recommend_company</code> table reference
     */
    public CampaignPcRecommendCompany(String alias) {
        this(alias, CAMPAIGN_PC_RECOMMEND_COMPANY);
    }

    private CampaignPcRecommendCompany(String alias, Table<CampaignPcRecommendCompanyRecord> aliased) {
        this(alias, aliased, null);
    }

    private CampaignPcRecommendCompany(String alias, Table<CampaignPcRecommendCompanyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "逛公司页面仟寻推荐公司");
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
    public Identity<CampaignPcRecommendCompanyRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CAMPAIGN_PC_RECOMMEND_COMPANY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CampaignPcRecommendCompanyRecord> getPrimaryKey() {
        return Keys.KEY_CAMPAIGN_PC_RECOMMEND_COMPANY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CampaignPcRecommendCompanyRecord>> getKeys() {
        return Arrays.<UniqueKey<CampaignPcRecommendCompanyRecord>>asList(Keys.KEY_CAMPAIGN_PC_RECOMMEND_COMPANY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CampaignPcRecommendCompany as(String alias) {
        return new CampaignPcRecommendCompany(alias, this);
    }

    /**
     * Rename this table
     */
    public CampaignPcRecommendCompany rename(String name) {
        return new CampaignPcRecommendCompany(name, null);
    }
}
