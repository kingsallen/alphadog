/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxTemplateMessageRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 微信模板消息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxTemplateMessageDao extends DAOImpl<HrWxTemplateMessageRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage, Integer> {

    /**
     * Create a new HrWxTemplateMessageDao without any configuration
     */
    public HrWxTemplateMessageDao() {
        super(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage.class);
    }

    /**
     * Create a new HrWxTemplateMessageDao with an attached configuration
     */
    public HrWxTemplateMessageDao(Configuration configuration) {
        super(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchById(Integer... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage fetchOneById(Integer value) {
        return fetchOne(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.ID, value);
    }

    /**
     * Fetch records that have <code>sys_template_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchBySysTemplateId(Integer... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.SYS_TEMPLATE_ID, values);
    }

    /**
     * Fetch records that have <code>wx_template_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchByWxTemplateId(String... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WX_TEMPLATE_ID, values);
    }

    /**
     * Fetch records that have <code>display IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchByDisplay(Integer... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.DISPLAY, values);
    }

    /**
     * Fetch records that have <code>priority IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchByPriority(Integer... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.PRIORITY, values);
    }

    /**
     * Fetch records that have <code>wechat_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchByWechatId(Integer... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WECHAT_ID, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchByDisable(Integer... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.DISABLE, values);
    }

    /**
     * Fetch records that have <code>url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchByUrl(String... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.URL, values);
    }

    /**
     * Fetch records that have <code>topcolor IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchByTopcolor(String... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.TOPCOLOR, values);
    }

    /**
     * Fetch records that have <code>first IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchByFirst(String... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.FIRST, values);
    }

    /**
     * Fetch records that have <code>remark IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxTemplateMessage> fetchByRemark(String... values) {
        return fetch(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.REMARK, values);
    }
}