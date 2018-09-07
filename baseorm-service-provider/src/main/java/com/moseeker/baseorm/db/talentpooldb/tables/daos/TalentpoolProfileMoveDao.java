/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.daos;


import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMove;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecord;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 简历搬家操作记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileMoveDao extends DAOImpl<TalentpoolProfileMoveRecord, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove, Integer> {

    /**
     * Create a new TalentpoolProfileMoveDao without any configuration
     */
    public TalentpoolProfileMoveDao() {
        super(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove.class);
    }

    /**
     * Create a new TalentpoolProfileMoveDao with an attached configuration
     */
    public TalentpoolProfileMoveDao(Configuration configuration) {
        super(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchById(Integer... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove fetchOneById(Integer value) {
        return fetchOne(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.ID, value);
    }

    /**
     * Fetch records that have <code>hr_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByHrId(Integer... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.HR_ID, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByCompanyId(Integer... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>channel IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByChannel(Byte... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CHANNEL, values);
    }

    /**
     * Fetch records that have <code>company_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByCompanyName(String... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.COMPANY_NAME, values);
    }

    /**
     * Fetch records that have <code>crawl_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByCrawlType(Byte... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CRAWL_TYPE, values);
    }

    /**
     * Fetch records that have <code>start_date IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByStartDate(Date... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.START_DATE, values);
    }

    /**
     * Fetch records that have <code>end_date IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByEndDate(Date... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.END_DATE, values);
    }

    /**
     * Fetch records that have <code>crawl_num IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByCrawlNum(Integer... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CRAWL_NUM, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByStatus(Byte... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.STATUS, values);
    }

    /**
     * Fetch records that have <code>current_email_num IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByCurrentEmailNum(Integer... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CURRENT_EMAIL_NUM, values);
    }

    /**
     * Fetch records that have <code>total_email_num IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByTotalEmailNum(Integer... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.TOTAL_EMAIL_NUM, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByCreateTime(Timestamp... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByUpdateTime(Timestamp... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>version IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMove> fetchByVersion(Integer... values) {
        return fetch(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.VERSION, values);
    }
}
