/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.daos;


import com.moseeker.baseorm.db.historydb.tables.HrMedia_20170612;
import com.moseeker.baseorm.db.historydb.tables.records.HrMedia_20170612Record;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 模板媒体表，存储模板渲染的媒体信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrMedia_20170612Dao extends DAOImpl<HrMedia_20170612Record, com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612, Integer> {

    /**
     * Create a new HrMedia_20170612Dao without any configuration
     */
    public HrMedia_20170612Dao() {
        super(HrMedia_20170612.HR_MEDIA_20170612, com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612.class);
    }

    /**
     * Create a new HrMedia_20170612Dao with an attached configuration
     */
    public HrMedia_20170612Dao(Configuration configuration) {
        super(HrMedia_20170612.HR_MEDIA_20170612, com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612 object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612> fetchById(Integer... values) {
        return fetch(HrMedia_20170612.HR_MEDIA_20170612.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612 fetchOneById(Integer value) {
        return fetchOne(HrMedia_20170612.HR_MEDIA_20170612.ID, value);
    }

    /**
     * Fetch records that have <code>longtext IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612> fetchByLongtext(String... values) {
        return fetch(HrMedia_20170612.HR_MEDIA_20170612.LONGTEXT, values);
    }

    /**
     * Fetch records that have <code>attrs IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612> fetchByAttrs(String... values) {
        return fetch(HrMedia_20170612.HR_MEDIA_20170612.ATTRS, values);
    }

    /**
     * Fetch records that have <code>title IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612> fetchByTitle(String... values) {
        return fetch(HrMedia_20170612.HR_MEDIA_20170612.TITLE, values);
    }

    /**
     * Fetch records that have <code>sub_title IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612> fetchBySubTitle(String... values) {
        return fetch(HrMedia_20170612.HR_MEDIA_20170612.SUB_TITLE, values);
    }

    /**
     * Fetch records that have <code>link IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612> fetchByLink(String... values) {
        return fetch(HrMedia_20170612.HR_MEDIA_20170612.LINK, values);
    }

    /**
     * Fetch records that have <code>res_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrMedia_20170612> fetchByResId(Integer... values) {
        return fetch(HrMedia_20170612.HR_MEDIA_20170612.RES_ID, values);
    }
}
