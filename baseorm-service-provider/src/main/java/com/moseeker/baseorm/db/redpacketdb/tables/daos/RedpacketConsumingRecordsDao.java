/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.redpacketdb.tables.daos;


import com.moseeker.baseorm.db.redpacketdb.tables.RedpacketConsumingRecords;
import com.moseeker.baseorm.db.redpacketdb.tables.records.RedpacketConsumingRecordsRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 账户变更记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RedpacketConsumingRecordsDao extends DAOImpl<RedpacketConsumingRecordsRecord, com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords, Integer> {

    /**
     * Create a new RedpacketConsumingRecordsDao without any configuration
     */
    public RedpacketConsumingRecordsDao() {
        super(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS, com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords.class);
    }

    /**
     * Create a new RedpacketConsumingRecordsDao with an attached configuration
     */
    public RedpacketConsumingRecordsDao(Configuration configuration) {
        super(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS, com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchById(Integer... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords fetchOneById(Integer value) {
        return fetchOne(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.ID, value);
    }

    /**
     * Fetch records that have <code>balance_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchByBalanceId(Integer... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.BALANCE_ID, values);
    }

    /**
     * Fetch records that have <code>amount IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchByAmount(Integer... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.AMOUNT, values);
    }

    /**
     * Fetch records that have <code>scene IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchByScene(Byte... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.SCENE, values);
    }

    /**
     * Fetch records that have <code>business_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchByBusinessId(Integer... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.BUSINESS_ID, values);
    }

    /**
     * Fetch records that have <code>bill_no IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchByBillNo(String... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.BILL_NO, values);
    }

    /**
     * Fetch records that have <code>description IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchByDescription(String... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.DESCRIPTION, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchByCreateTime(Timestamp... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchByUpdateTime(Timestamp... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>operation_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketConsumingRecords> fetchByOperationId(Integer... values) {
        return fetch(RedpacketConsumingRecords.REDPACKET_CONSUMING_RECORDS.OPERATION_ID, values);
    }
}