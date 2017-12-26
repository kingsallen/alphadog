/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 红包记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHbItemsDao extends DAOImpl<HrHbItemsRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems, Integer> {

    /**
     * Create a new HrHbItemsDao without any configuration
     */
    public HrHbItemsDao() {
        super(HrHbItems.HR_HB_ITEMS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems.class);
    }

    /**
     * Create a new HrHbItemsDao with an attached configuration
     */
    public HrHbItemsDao(Configuration configuration) {
        super(HrHbItems.HR_HB_ITEMS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchById(Integer... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems fetchOneById(Integer value) {
        return fetchOne(HrHbItems.HR_HB_ITEMS.ID, value);
    }

    /**
     * Fetch records that have <code>hb_config_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByHbConfigId(Integer... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.HB_CONFIG_ID, values);
    }

    /**
     * Fetch records that have <code>binding_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByBindingId(Integer... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.BINDING_ID, values);
    }

    /**
     * Fetch records that have <code>index IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByIndex(Integer... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.INDEX, values);
    }

    /**
     * Fetch records that have <code>amount IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByAmount(BigDecimal... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.AMOUNT, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByStatus(Byte... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.STATUS, values);
    }

    /**
     * Fetch records that have <code>wxuser_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByWxuserId(Integer... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.WXUSER_ID, values);
    }

    /**
     * Fetch records that have <code>open_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByOpenTime(Timestamp... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.OPEN_TIME, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByCreateTime(Timestamp... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>trigger_wxuser_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems> fetchByTriggerWxuserId(Integer... values) {
        return fetch(HrHbItems.HR_HB_ITEMS.TRIGGER_WXUSER_ID, values);
    }
}