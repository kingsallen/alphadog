/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.thirdpartydb.tables.daos;


import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyCompanyChannelConf;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyCompanyChannelConfRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 公司可同步渠道配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ThirdpartyCompanyChannelConfDao extends DAOImpl<ThirdpartyCompanyChannelConfRecord, com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyCompanyChannelConf, Integer> {

    /**
     * Create a new ThirdpartyCompanyChannelConfDao without any configuration
     */
    public ThirdpartyCompanyChannelConfDao() {
        super(ThirdpartyCompanyChannelConf.THIRDPARTY_COMPANY_CHANNEL_CONF, com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyCompanyChannelConf.class);
    }

    /**
     * Create a new ThirdpartyCompanyChannelConfDao with an attached configuration
     */
    public ThirdpartyCompanyChannelConfDao(Configuration configuration) {
        super(ThirdpartyCompanyChannelConf.THIRDPARTY_COMPANY_CHANNEL_CONF, com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyCompanyChannelConf.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyCompanyChannelConf object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyCompanyChannelConf> fetchById(Integer... values) {
        return fetch(ThirdpartyCompanyChannelConf.THIRDPARTY_COMPANY_CHANNEL_CONF.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyCompanyChannelConf fetchOneById(Integer value) {
        return fetchOne(ThirdpartyCompanyChannelConf.THIRDPARTY_COMPANY_CHANNEL_CONF.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyCompanyChannelConf> fetchByCompanyId(Integer... values) {
        return fetch(ThirdpartyCompanyChannelConf.THIRDPARTY_COMPANY_CHANNEL_CONF.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>channel IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyCompanyChannelConf> fetchByChannel(Integer... values) {
        return fetch(ThirdpartyCompanyChannelConf.THIRDPARTY_COMPANY_CHANNEL_CONF.CHANNEL, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyCompanyChannelConf> fetchByCreateTime(Timestamp... values) {
        return fetch(ThirdpartyCompanyChannelConf.THIRDPARTY_COMPANY_CHANNEL_CONF.CREATE_TIME, values);
    }
}
