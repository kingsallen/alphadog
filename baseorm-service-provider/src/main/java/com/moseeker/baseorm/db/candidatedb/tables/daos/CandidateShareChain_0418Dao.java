/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.candidatedb.tables.daos;


import com.moseeker.baseorm.db.candidatedb.tables.CandidateShareChain_0418;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateShareChain_0418Record;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 链路信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidateShareChain_0418Dao extends DAOImpl<CandidateShareChain_0418Record, com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418, Integer> {

    /**
     * Create a new CandidateShareChain_0418Dao without any configuration
     */
    public CandidateShareChain_0418Dao() {
        super(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418, com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418.class);
    }

    /**
     * Create a new CandidateShareChain_0418Dao with an attached configuration
     */
    public CandidateShareChain_0418Dao(Configuration configuration) {
        super(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418, com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418 object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchById(Integer... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418 fetchOneById(Integer value) {
        return fetchOne(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.ID, value);
    }

    /**
     * Fetch records that have <code>position_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchByPositionId(Integer... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.POSITION_ID, values);
    }

    /**
     * Fetch records that have <code>root_recom_user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchByRootRecomUserId(Integer... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.ROOT_RECOM_USER_ID, values);
    }

    /**
     * Fetch records that have <code>root2_recom_user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchByRoot2RecomUserId(Integer... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.ROOT2_RECOM_USER_ID, values);
    }

    /**
     * Fetch records that have <code>recom_user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchByRecomUserId(Integer... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.RECOM_USER_ID, values);
    }

    /**
     * Fetch records that have <code>presentee_user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchByPresenteeUserId(Integer... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.PRESENTEE_USER_ID, values);
    }

    /**
     * Fetch records that have <code>depth IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchByDepth(Integer... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.DEPTH, values);
    }

    /**
     * Fetch records that have <code>parent_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchByParentId(Integer... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.PARENT_ID, values);
    }

    /**
     * Fetch records that have <code>click_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchByClickTime(Timestamp... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.CLICK_TIME, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.candidatedb.tables.pojos.CandidateShareChain_0418> fetchByCreateTime(Timestamp... values) {
        return fetch(CandidateShareChain_0418.CANDIDATE_SHARE_CHAIN_0418.CREATE_TIME, values);
    }
}
