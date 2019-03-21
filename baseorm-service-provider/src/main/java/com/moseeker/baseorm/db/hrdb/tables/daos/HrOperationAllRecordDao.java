/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrOperationAllRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationAllRecordRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 简历操作历史表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrOperationAllRecordDao extends DAOImpl<HrOperationAllRecordRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord, Integer> {

    /**
     * Create a new HrOperationAllRecordDao without any configuration
     */
    public HrOperationAllRecordDao() {
        super(HrOperationAllRecord.HR_OPERATION_ALL_RECORD, com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord.class);
    }

    /**
     * Create a new HrOperationAllRecordDao with an attached configuration
     */
    public HrOperationAllRecordDao(Configuration configuration) {
        super(HrOperationAllRecord.HR_OPERATION_ALL_RECORD, com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord> fetchById(Integer... values) {
        return fetch(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord fetchOneById(Integer value) {
        return fetchOne(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.ID, value);
    }

    /**
     * Fetch records that have <code>operator_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord> fetchByOperatorId(Integer... values) {
        return fetch(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.OPERATOR_ID, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord> fetchByCompanyId(Integer... values) {
        return fetch(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord> fetchByUserId(Integer... values) {
        return fetch(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.USER_ID, values);
    }

    /**
     * Fetch records that have <code>title IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord> fetchByTitle(String... values) {
        return fetch(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.TITLE, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord> fetchByDescription(String... values) {
        return fetch(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>opertaion_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord> fetchByOpertaionType(Integer... values) {
        return fetch(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.OPERTAION_TYPE, values);
    }

    /**
     * Fetch records that have <code>operator_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord> fetchByOperatorType(Integer... values) {
        return fetch(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.OPERATOR_TYPE, values);
    }

    /**
     * Fetch records that have <code>opt_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationAllRecord> fetchByOptTime(Timestamp... values) {
        return fetch(HrOperationAllRecord.HR_OPERATION_ALL_RECORD.OPT_TIME, values);
    }
}
