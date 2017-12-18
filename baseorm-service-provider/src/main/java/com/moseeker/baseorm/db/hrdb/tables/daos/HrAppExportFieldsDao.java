/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrAppExportFields;
import com.moseeker.baseorm.db.hrdb.tables.records.HrAppExportFieldsRecord;

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
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAppExportFieldsDao extends DAOImpl<HrAppExportFieldsRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields, Integer> {

    /**
     * Create a new HrAppExportFieldsDao without any configuration
     */
    public HrAppExportFieldsDao() {
        super(HrAppExportFields.HR_APP_EXPORT_FIELDS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields.class);
    }

    /**
     * Create a new HrAppExportFieldsDao with an attached configuration
     */
    public HrAppExportFieldsDao(Configuration configuration) {
        super(HrAppExportFields.HR_APP_EXPORT_FIELDS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields> fetchById(Integer... values) {
        return fetch(HrAppExportFields.HR_APP_EXPORT_FIELDS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields fetchOneById(Integer value) {
        return fetchOne(HrAppExportFields.HR_APP_EXPORT_FIELDS.ID, value);
    }

    /**
     * Fetch records that have <code>field_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields> fetchByFieldName(String... values) {
        return fetch(HrAppExportFields.HR_APP_EXPORT_FIELDS.FIELD_NAME, values);
    }

    /**
     * Fetch records that have <code>field_title IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields> fetchByFieldTitle(String... values) {
        return fetch(HrAppExportFields.HR_APP_EXPORT_FIELDS.FIELD_TITLE, values);
    }

    /**
     * Fetch records that have <code>display_order IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields> fetchByDisplayOrder(Integer... values) {
        return fetch(HrAppExportFields.HR_APP_EXPORT_FIELDS.DISPLAY_ORDER, values);
    }

    /**
     * Fetch records that have <code>selected IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields> fetchBySelected(Integer... values) {
        return fetch(HrAppExportFields.HR_APP_EXPORT_FIELDS.SELECTED, values);
    }

    /**
     * Fetch records that have <code>showed IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields> fetchByShowed(Integer... values) {
        return fetch(HrAppExportFields.HR_APP_EXPORT_FIELDS.SHOWED, values);
    }

    /**
     * Fetch records that have <code>sample IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrAppExportFields> fetchBySample(String... values) {
        return fetch(HrAppExportFields.HR_APP_EXPORT_FIELDS.SAMPLE, values);
    }
}
