/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrWxRule;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxRuleRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 微信回复规则表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxRuleDao extends DAOImpl<HrWxRuleRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule, Integer> {

    /**
     * Create a new HrWxRuleDao without any configuration
     */
    public HrWxRuleDao() {
        super(HrWxRule.HR_WX_RULE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule.class);
    }

    /**
     * Create a new HrWxRuleDao with an attached configuration
     */
    public HrWxRuleDao(Configuration configuration) {
        super(HrWxRule.HR_WX_RULE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule> fetchById(Integer... values) {
        return fetch(HrWxRule.HR_WX_RULE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule fetchOneById(Integer value) {
        return fetchOne(HrWxRule.HR_WX_RULE.ID, value);
    }

    /**
     * Fetch records that have <code>wechat_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule> fetchByWechatId(Integer... values) {
        return fetch(HrWxRule.HR_WX_RULE.WECHAT_ID, values);
    }

    /**
     * Fetch records that have <code>cid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule> fetchByCid(Integer... values) {
        return fetch(HrWxRule.HR_WX_RULE.CID, values);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule> fetchByName(String... values) {
        return fetch(HrWxRule.HR_WX_RULE.NAME, values);
    }

    /**
     * Fetch records that have <code>module IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule> fetchByModule(String... values) {
        return fetch(HrWxRule.HR_WX_RULE.MODULE, values);
    }

    /**
     * Fetch records that have <code>displayorder IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule> fetchByDisplayorder(Integer... values) {
        return fetch(HrWxRule.HR_WX_RULE.DISPLAYORDER, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule> fetchByStatus(Byte... values) {
        return fetch(HrWxRule.HR_WX_RULE.STATUS, values);
    }

    /**
     * Fetch records that have <code>access_level IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule> fetchByAccessLevel(Integer... values) {
        return fetch(HrWxRule.HR_WX_RULE.ACCESS_LEVEL, values);
    }

    /**
     * Fetch records that have <code>keywords IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxRule> fetchByKeywords(String... values) {
        return fetch(HrWxRule.HR_WX_RULE.KEYWORDS, values);
    }
}
