/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.malldb.tables.daos;


import com.moseeker.baseorm.db.malldb.tables.MallOrderOperation;
import com.moseeker.baseorm.db.malldb.tables.records.MallOrderOperationRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 商品发放hr操作记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MallOrderOperationDao extends DAOImpl<MallOrderOperationRecord, com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation, Integer> {

    /**
     * Create a new MallOrderOperationDao without any configuration
     */
    public MallOrderOperationDao() {
        super(MallOrderOperation.MALL_ORDER_OPERATION, com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation.class);
    }

    /**
     * Create a new MallOrderOperationDao with an attached configuration
     */
    public MallOrderOperationDao(Configuration configuration) {
        super(MallOrderOperation.MALL_ORDER_OPERATION, com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation> fetchById(Integer... values) {
        return fetch(MallOrderOperation.MALL_ORDER_OPERATION.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation fetchOneById(Integer value) {
        return fetchOne(MallOrderOperation.MALL_ORDER_OPERATION.ID, value);
    }

    /**
     * Fetch records that have <code>hr_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation> fetchByHrId(Integer... values) {
        return fetch(MallOrderOperation.MALL_ORDER_OPERATION.HR_ID, values);
    }

    /**
     * Fetch records that have <code>order_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation> fetchByOrderId(String... values) {
        return fetch(MallOrderOperation.MALL_ORDER_OPERATION.ORDER_ID, values);
    }

    /**
     * Fetch records that have <code>operation_state IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation> fetchByOperationState(Byte... values) {
        return fetch(MallOrderOperation.MALL_ORDER_OPERATION.OPERATION_STATE, values);
    }

    /**
     * Fetch records that have <code>point_record_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation> fetchByPointRecordId(Integer... values) {
        return fetch(MallOrderOperation.MALL_ORDER_OPERATION.POINT_RECORD_ID, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation> fetchByCreateTime(Timestamp... values) {
        return fetch(MallOrderOperation.MALL_ORDER_OPERATION.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallOrderOperation> fetchByUpdateTime(Timestamp... values) {
        return fetch(MallOrderOperation.MALL_ORDER_OPERATION.UPDATE_TIME, values);
    }
}