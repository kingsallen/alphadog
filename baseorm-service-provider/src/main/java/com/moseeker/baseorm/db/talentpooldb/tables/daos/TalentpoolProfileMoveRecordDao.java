/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.daos;


import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMoveRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecordRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 简历搬家chaos请求操作记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileMoveRecordDao extends DAOImpl<TalentpoolProfileMoveRecordRecord, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord, Integer> {

    /**
     * Create a new TalentpoolProfileMoveRecordDao without any configuration
     */
    public TalentpoolProfileMoveRecordDao() {
        super(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord.class);
    }

    /**
     * Create a new TalentpoolProfileMoveRecordDao with an attached configuration
     */
    public TalentpoolProfileMoveRecordDao(Configuration configuration) {
        super(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord> fetchById(Integer... values) {
        return fetch(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord fetchOneById(Integer value) {
        return fetchOne(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.ID, value);
    }

    /**
     * Fetch records that have <code>profile_move_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord> fetchByProfileMoveId(Integer... values) {
        return fetch(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.PROFILE_MOVE_ID, values);
    }

    /**
     * Fetch records that have <code>crawl_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord> fetchByCrawlType(Byte... values) {
        return fetch(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CRAWL_TYPE, values);
    }

    /**
     * Fetch records that have <code>crawl_num IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord> fetchByCrawlNum(Integer... values) {
        return fetch(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CRAWL_NUM, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord> fetchByStatus(Byte... values) {
        return fetch(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.STATUS, values);
    }

    /**
     * Fetch records that have <code>current_email_num IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord> fetchByCurrentEmailNum(Integer... values) {
        return fetch(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CURRENT_EMAIL_NUM, values);
    }

    /**
     * Fetch records that have <code>total_email_num IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord> fetchByTotalEmailNum(Integer... values) {
        return fetch(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.TOTAL_EMAIL_NUM, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord> fetchByCreateTime(Timestamp... values) {
        return fetch(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileMoveRecord> fetchByUpdateTime(Timestamp... values) {
        return fetch(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.UPDATE_TIME, values);
    }
}
