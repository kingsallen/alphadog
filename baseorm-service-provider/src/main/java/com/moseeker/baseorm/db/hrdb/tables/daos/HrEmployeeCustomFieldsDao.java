/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCustomFieldsRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 员工认证自定义字段表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeCustomFieldsDao extends DAOImpl<HrEmployeeCustomFieldsRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields, Integer> {

    /**
     * Create a new HrEmployeeCustomFieldsDao without any configuration
     */
    public HrEmployeeCustomFieldsDao() {
        super(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields.class);
    }

    /**
     * Create a new HrEmployeeCustomFieldsDao with an attached configuration
     */
    public HrEmployeeCustomFieldsDao(Configuration configuration) {
        super(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchById(Integer... values) {
        return fetch(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields fetchOneById(Integer value) {
        return fetchOne(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchByCompanyId(Integer... values) {
        return fetch(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>fname IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchByFname(String... values) {
        return fetch(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FNAME, values);
    }

    /**
     * Fetch records that have <code>fvalues IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchByFvalues(String... values) {
        return fetch(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FVALUES, values);
    }

    /**
     * Fetch records that have <code>forder IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchByForder(Integer... values) {
        return fetch(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.FORDER, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchByDisable(Byte... values) {
        return fetch(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE, values);
    }

    /**
     * Fetch records that have <code>mandatory IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchByMandatory(Integer... values) {
        return fetch(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.MANDATORY, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchByStatus(Integer... values) {
        return fetch(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS, values);
    }

    /**
     * Fetch records that have <code>option_type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> fetchByOptionType(Integer... values) {
        return fetch(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.OPTION_TYPE, values);
    }
}
