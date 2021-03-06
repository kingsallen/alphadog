/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNoticeMessageRecord;

import java.sql.Date;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 微信消息通知, first和remark文案暂不使用
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxNoticeMessageDao extends DAOImpl<HrWxNoticeMessageRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage, Integer> {

    /**
     * Create a new HrWxNoticeMessageDao without any configuration
     */
    public HrWxNoticeMessageDao() {
        super(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage.class);
    }

    /**
     * Create a new HrWxNoticeMessageDao with an attached configuration
     */
    public HrWxNoticeMessageDao(Configuration configuration) {
        super(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage> fetchById(Integer... values) {
        return fetch(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage fetchOneById(Integer value) {
        return fetchOne(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.ID, value);
    }

    /**
     * Fetch records that have <code>wechat_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage> fetchByWechatId(Integer... values) {
        return fetch(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID, values);
    }

    /**
     * Fetch records that have <code>notice_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage> fetchByNoticeId(Integer... values) {
        return fetch(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID, values);
    }

    /**
     * Fetch records that have <code>first IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage> fetchByFirst(String... values) {
        return fetch(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.FIRST, values);
    }

    /**
     * Fetch records that have <code>remark IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage> fetchByRemark(String... values) {
        return fetch(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.REMARK, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage> fetchByStatus(Byte... values) {
        return fetch(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.STATUS, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage> fetchByDisable(Byte... values) {
        return fetch(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE, values);
    }

    /**
     * Fetch records that have <code>send_frequency IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage> fetchBySendFrequency(String... values) {
        return fetch(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.SEND_FREQUENCY, values);
    }

    /**
     * Fetch records that have <code>sent_date IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage> fetchBySentDate(Date... values) {
        return fetch(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.SENT_DATE, values);
    }
}
