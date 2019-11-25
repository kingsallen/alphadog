/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.malldb.tables.daos;


import com.moseeker.baseorm.db.malldb.tables.MallMailAddress;
import com.moseeker.baseorm.db.malldb.tables.records.MallMailAddressRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 邮寄地址信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MallMailAddressDao extends DAOImpl<MallMailAddressRecord, com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress, Integer> {

    /**
     * Create a new MallMailAddressDao without any configuration
     */
    public MallMailAddressDao() {
        super(MallMailAddress.MALL_MAIL_ADDRESS, com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress.class);
    }

    /**
     * Create a new MallMailAddressDao with an attached configuration
     */
    public MallMailAddressDao(Configuration configuration) {
        super(MallMailAddress.MALL_MAIL_ADDRESS, com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchById(Integer... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress fetchOneById(Integer value) {
        return fetchOne(MallMailAddress.MALL_MAIL_ADDRESS.ID, value);
    }

    /**
     * Fetch records that have <code>user_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchByUserId(Integer... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.USER_ID, values);
    }

    /**
     * Fetch records that have <code>addressee IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchByAddressee(String... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.ADDRESSEE, values);
    }

    /**
     * Fetch records that have <code>mobile IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchByMobile(String... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.MOBILE, values);
    }

    /**
     * Fetch records that have <code>province IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchByProvince(Integer... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.PROVINCE, values);
    }

    /**
     * Fetch records that have <code>city IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchByCity(Integer... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.CITY, values);
    }

    /**
     * Fetch records that have <code>region IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchByRegion(Integer... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.REGION, values);
    }

    /**
     * Fetch records that have <code>address IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchByAddress(String... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.ADDRESS, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchByCreateTime(Timestamp... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallMailAddress> fetchByUpdateTime(Timestamp... values) {
        return fetch(MallMailAddress.MALL_MAIL_ADDRESS.UPDATE_TIME, values);
    }
}
