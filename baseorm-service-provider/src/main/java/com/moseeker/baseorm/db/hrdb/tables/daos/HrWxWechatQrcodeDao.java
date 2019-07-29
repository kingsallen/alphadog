/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrWxWechatQrcode;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatQrcodeRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 微信公众号的场景二维码表(永久)
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxWechatQrcodeDao extends DAOImpl<HrWxWechatQrcodeRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode, Integer> {

    /**
     * Create a new HrWxWechatQrcodeDao without any configuration
     */
    public HrWxWechatQrcodeDao() {
        super(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode.class);
    }

    /**
     * Create a new HrWxWechatQrcodeDao with an attached configuration
     */
    public HrWxWechatQrcodeDao(Configuration configuration) {
        super(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE, com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode> fetchById(Integer... values) {
        return fetch(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode fetchOneById(Integer value) {
        return fetchOne(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.ID, value);
    }

    /**
     * Fetch records that have <code>wechat_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode> fetchByWechatId(Integer... values) {
        return fetch(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.WECHAT_ID, values);
    }

    /**
     * Fetch records that have <code>qrcode_url IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode> fetchByQrcodeUrl(String... values) {
        return fetch(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.QRCODE_URL, values);
    }

    /**
     * Fetch records that have <code>scene IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode> fetchByScene(String... values) {
        return fetch(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.SCENE, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode> fetchByCreateTime(Timestamp... values) {
        return fetch(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrWxWechatQrcode.HR_WX_WECHAT_QRCODE.UPDATE_TIME, values);
    }
}
