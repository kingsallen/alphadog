/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.daos;


import com.moseeker.baseorm.db.historydb.tables.HrAppExportFields180726;
import com.moseeker.baseorm.db.historydb.tables.records.HrAppExportFields180726Record;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 自定义简历模板导出字段表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAppExportFields180726Dao extends DAOImpl<HrAppExportFields180726Record, com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726, Integer> {

    /**
     * Create a new HrAppExportFields180726Dao without any configuration
     */
    public HrAppExportFields180726Dao() {
        super(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726, com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726.class);
    }

    /**
     * Create a new HrAppExportFields180726Dao with an attached configuration
     */
    public HrAppExportFields180726Dao(Configuration configuration) {
        super(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726, com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726 object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726> fetchById(Integer... values) {
        return fetch(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726 fetchOneById(Integer value) {
        return fetchOne(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726.ID, value);
    }

    /**
     * Fetch records that have <code>field_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726> fetchByFieldName(String... values) {
        return fetch(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726.FIELD_NAME, values);
    }

    /**
     * Fetch records that have <code>field_title IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726> fetchByFieldTitle(String... values) {
        return fetch(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726.FIELD_TITLE, values);
    }

    /**
     * Fetch records that have <code>display_order IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726> fetchByDisplayOrder(Integer... values) {
        return fetch(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726.DISPLAY_ORDER, values);
    }

    /**
     * Fetch records that have <code>selected IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726> fetchBySelected(Integer... values) {
        return fetch(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726.SELECTED, values);
    }

    /**
     * Fetch records that have <code>showed IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726> fetchByShowed(Integer... values) {
        return fetch(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726.SHOWED, values);
    }

    /**
     * Fetch records that have <code>sample IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.historydb.tables.pojos.HrAppExportFields180726> fetchBySample(String... values) {
        return fetch(HrAppExportFields180726.HR_APP_EXPORT_FIELDS180726.SAMPLE, values);
    }
}
