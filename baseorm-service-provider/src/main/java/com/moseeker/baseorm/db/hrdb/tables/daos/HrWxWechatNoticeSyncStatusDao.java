/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrWxWechatNoticeSyncStatus;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatNoticeSyncStatusRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 微信消息通知同步状态
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxWechatNoticeSyncStatusDao extends DAOImpl<HrWxWechatNoticeSyncStatusRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus, Integer> {

    /**
     * Create a new HrWxWechatNoticeSyncStatusDao without any configuration
     */
    public HrWxWechatNoticeSyncStatusDao() {
        super(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus.class);
    }

    /**
     * Create a new HrWxWechatNoticeSyncStatusDao with an attached configuration
     */
    public HrWxWechatNoticeSyncStatusDao(Configuration configuration) {
        super(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus> fetchById(Integer... values) {
        return fetch(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus fetchOneById(Integer value) {
        return fetchOne(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.ID, value);
    }

    /**
     * Fetch records that have <code>wechat_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus> fetchByWechatId(Integer... values) {
        return fetch(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.WECHAT_ID, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus> fetchByStatus(Integer... values) {
        return fetch(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.STATUS, values);
    }

    /**
     * Fetch records that have <code>count IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus> fetchByCount(Integer... values) {
        return fetch(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.COUNT, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatNoticeSyncStatus> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.UPDATE_TIME, values);
    }
}