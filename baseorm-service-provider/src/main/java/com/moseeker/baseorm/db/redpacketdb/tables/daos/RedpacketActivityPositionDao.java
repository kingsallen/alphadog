/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.redpacketdb.tables.daos;


import com.moseeker.baseorm.db.redpacketdb.tables.RedpacketActivityPosition;
import com.moseeker.baseorm.db.redpacketdb.tables.records.RedpacketActivityPositionRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 参与红包活动的职位记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RedpacketActivityPositionDao extends DAOImpl<RedpacketActivityPositionRecord, com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition, Integer> {

    /**
     * Create a new RedpacketActivityPositionDao without any configuration
     */
    public RedpacketActivityPositionDao() {
        super(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION, com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition.class);
    }

    /**
     * Create a new RedpacketActivityPositionDao with an attached configuration
     */
    public RedpacketActivityPositionDao(Configuration configuration) {
        super(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION, com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition> fetchById(Integer... values) {
        return fetch(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition fetchOneById(Integer value) {
        return fetchOne(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.ID, value);
    }

    /**
     * Fetch records that have <code>activity_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition> fetchByActivityId(Integer... values) {
        return fetch(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.ACTIVITY_ID, values);
    }

    /**
     * Fetch records that have <code>position_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition> fetchByPositionId(Integer... values) {
        return fetch(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.POSITION_ID, values);
    }

    /**
     * Fetch records that have <code>total_amount IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition> fetchByTotalAmount(Integer... values) {
        return fetch(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.TOTAL_AMOUNT, values);
    }

    /**
     * Fetch records that have <code>left_amount IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition> fetchByLeftAmount(Integer... values) {
        return fetch(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.LEFT_AMOUNT, values);
    }

    /**
     * Fetch records that have <code>enable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition> fetchByEnable(Byte... values) {
        return fetch(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.ENABLE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition> fetchByCreateTime(Timestamp... values) {
        return fetch(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition> fetchByUpdateTime(Timestamp... values) {
        return fetch(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>storage IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.redpacketdb.tables.pojos.RedpacketActivityPosition> fetchByStorage(Integer... values) {
        return fetch(RedpacketActivityPosition.REDPACKET_ACTIVITY_POSITION.STORAGE, values);
    }
}
