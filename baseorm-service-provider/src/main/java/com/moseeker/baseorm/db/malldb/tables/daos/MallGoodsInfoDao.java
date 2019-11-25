/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.malldb.tables.daos;


import com.moseeker.baseorm.db.malldb.tables.MallGoodsInfo;
import com.moseeker.baseorm.db.malldb.tables.records.MallGoodsInfoRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 商品信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MallGoodsInfoDao extends DAOImpl<MallGoodsInfoRecord, com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo, Integer> {

    /**
     * Create a new MallGoodsInfoDao without any configuration
     */
    public MallGoodsInfoDao() {
        super(MallGoodsInfo.MALL_GOODS_INFO, com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo.class);
    }

    /**
     * Create a new MallGoodsInfoDao with an attached configuration
     */
    public MallGoodsInfoDao(Configuration configuration) {
        super(MallGoodsInfo.MALL_GOODS_INFO, com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchById(Integer... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo fetchOneById(Integer value) {
        return fetchOne(MallGoodsInfo.MALL_GOODS_INFO.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByCompanyId(Integer... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>title IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByTitle(String... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.TITLE, values);
    }

    /**
     * Fetch records that have <code>pic_url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByPicUrl(String... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.PIC_URL, values);
    }

    /**
     * Fetch records that have <code>credit IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByCredit(Integer... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.CREDIT, values);
    }

    /**
     * Fetch records that have <code>stock IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByStock(Integer... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.STOCK, values);
    }

    /**
     * Fetch records that have <code>exchange_num IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByExchangeNum(Integer... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.EXCHANGE_NUM, values);
    }

    /**
     * Fetch records that have <code>exchange_order IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByExchangeOrder(Integer... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.EXCHANGE_ORDER, values);
    }

    /**
     * Fetch records that have <code>detail IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByDetail(String... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.DETAIL, values);
    }

    /**
     * Fetch records that have <code>state IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByState(Byte... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.STATE, values);
    }

    /**
     * Fetch records that have <code>rule IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByRule(String... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.RULE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByCreateTime(Timestamp... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.malldb.tables.pojos.MallGoodsInfo> fetchByUpdateTime(Timestamp... values) {
        return fetch(MallGoodsInfo.MALL_GOODS_INFO.UPDATE_TIME, values);
    }
}
