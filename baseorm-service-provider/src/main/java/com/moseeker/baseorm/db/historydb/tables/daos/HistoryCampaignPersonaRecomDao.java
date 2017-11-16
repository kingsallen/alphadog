/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.daos;


import com.moseeker.baseorm.db.historydb.tables.HistoryCampaignPersonaRecom;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryCampaignPersonaRecomRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 推送职位历史表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryCampaignPersonaRecomDao extends DAOImpl<HistoryCampaignPersonaRecomRecord, com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom, Integer> {

    /**
     * Create a new HistoryCampaignPersonaRecomDao without any configuration
     */
    public HistoryCampaignPersonaRecomDao() {
        super(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM, com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom.class);
    }

    /**
     * Create a new HistoryCampaignPersonaRecomDao with an attached configuration
     */
    public HistoryCampaignPersonaRecomDao(Configuration configuration) {
        super(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM, com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom> fetchById(Integer... values) {
        return fetch(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom fetchOneById(Integer value) {
        return fetchOne(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM.ID, value);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom> fetchByUserId(Integer... values) {
        return fetch(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM.USER_ID, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom> fetchByCompanyId(Integer... values) {
        return fetch(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>position_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom> fetchByPositionId(Integer... values) {
        return fetch(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM.POSITION_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom> fetchByCreateTime(Timestamp... values) {
        return fetch(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>send_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom> fetchBySendTime(Timestamp... values) {
        return fetch(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM.SEND_TIME, values);
    }

    /**
     * Fetch records that have <code>is_send IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom> fetchByIsSend(Byte... values) {
        return fetch(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM.IS_SEND, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom> fetchByUpdateTime(Timestamp... values) {
        return fetch(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM.UPDATE_TIME, values);
    }
}
