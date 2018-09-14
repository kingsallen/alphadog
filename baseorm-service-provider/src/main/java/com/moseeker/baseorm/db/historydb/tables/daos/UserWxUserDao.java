/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.daos;


import com.moseeker.baseorm.db.historydb.tables.UserWxUser;
import com.moseeker.baseorm.db.historydb.tables.records.UserWxUserRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 微信用户表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserWxUserDao extends DAOImpl<UserWxUserRecord, com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser, Long> {

    /**
     * Create a new UserWxUserDao without any configuration
     */
    public UserWxUserDao() {
        super(UserWxUser.USER_WX_USER, com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser.class);
    }

    /**
     * Create a new UserWxUserDao with an attached configuration
     */
    public UserWxUserDao(Configuration configuration) {
        super(UserWxUser.USER_WX_USER, com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchById(Long... values) {
        return fetch(UserWxUser.USER_WX_USER.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser fetchOneById(Long value) {
        return fetchOne(UserWxUser.USER_WX_USER.ID, value);
    }

    /**
     * Fetch records that have <code>wechat_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByWechatId(Integer... values) {
        return fetch(UserWxUser.USER_WX_USER.WECHAT_ID, values);
    }

    /**
     * Fetch records that have <code>group_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByGroupId(Integer... values) {
        return fetch(UserWxUser.USER_WX_USER.GROUP_ID, values);
    }

    /**
     * Fetch records that have <code>sysuser_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchBySysuserId(Integer... values) {
        return fetch(UserWxUser.USER_WX_USER.SYSUSER_ID, values);
    }

    /**
     * Fetch records that have <code>is_subscribe IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByIsSubscribe(Byte... values) {
        return fetch(UserWxUser.USER_WX_USER.IS_SUBSCRIBE, values);
    }

    /**
     * Fetch records that have <code>openid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByOpenid(String... values) {
        return fetch(UserWxUser.USER_WX_USER.OPENID, values);
    }

    /**
     * Fetch records that have <code>nickname IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByNickname(String... values) {
        return fetch(UserWxUser.USER_WX_USER.NICKNAME, values);
    }

    /**
     * Fetch records that have <code>sex IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchBySex(Integer... values) {
        return fetch(UserWxUser.USER_WX_USER.SEX, values);
    }

    /**
     * Fetch records that have <code>city IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByCity(String... values) {
        return fetch(UserWxUser.USER_WX_USER.CITY, values);
    }

    /**
     * Fetch records that have <code>country IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByCountry(String... values) {
        return fetch(UserWxUser.USER_WX_USER.COUNTRY, values);
    }

    /**
     * Fetch records that have <code>province IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByProvince(String... values) {
        return fetch(UserWxUser.USER_WX_USER.PROVINCE, values);
    }

    /**
     * Fetch records that have <code>language IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByLanguage(String... values) {
        return fetch(UserWxUser.USER_WX_USER.LANGUAGE, values);
    }

    /**
     * Fetch records that have <code>headimgurl IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByHeadimgurl(String... values) {
        return fetch(UserWxUser.USER_WX_USER.HEADIMGURL, values);
    }

    /**
     * Fetch records that have <code>subscribe_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchBySubscribeTime(Timestamp... values) {
        return fetch(UserWxUser.USER_WX_USER.SUBSCRIBE_TIME, values);
    }

    /**
     * Fetch records that have <code>unsubscibe_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByUnsubscibeTime(Timestamp... values) {
        return fetch(UserWxUser.USER_WX_USER.UNSUBSCIBE_TIME, values);
    }

    /**
     * Fetch records that have <code>unionid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByUnionid(String... values) {
        return fetch(UserWxUser.USER_WX_USER.UNIONID, values);
    }

    /**
     * Fetch records that have <code>reward IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByReward(Integer... values) {
        return fetch(UserWxUser.USER_WX_USER.REWARD, values);
    }

    /**
     * Fetch records that have <code>auto_sync_info IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByAutoSyncInfo(Byte... values) {
        return fetch(UserWxUser.USER_WX_USER.AUTO_SYNC_INFO, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByCreateTime(Timestamp... values) {
        return fetch(UserWxUser.USER_WX_USER.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchByUpdateTime(Timestamp... values) {
        return fetch(UserWxUser.USER_WX_USER.UPDATE_TIME, values);
    }

    /**
     * Fetch records that have <code>source IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.UserWxUser> fetchBySource(Byte... values) {
        return fetch(UserWxUser.USER_WX_USER.SOURCE, values);
    }
}
