/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeSection;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeSectionRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 员工部门表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeSectionDao extends DAOImpl<HrEmployeeSectionRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection, Integer> {

    /**
     * Create a new HrEmployeeSectionDao without any configuration
     */
    public HrEmployeeSectionDao() {
        super(HrEmployeeSection.HR_EMPLOYEE_SECTION, com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection.class);
    }

    /**
     * Create a new HrEmployeeSectionDao with an attached configuration
     */
    public HrEmployeeSectionDao(Configuration configuration) {
        super(HrEmployeeSection.HR_EMPLOYEE_SECTION, com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection> fetchById(Integer... values) {
        return fetch(HrEmployeeSection.HR_EMPLOYEE_SECTION.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection fetchOneById(Integer value) {
        return fetchOne(HrEmployeeSection.HR_EMPLOYEE_SECTION.ID, value);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection> fetchByCompanyId(Integer... values) {
        return fetch(HrEmployeeSection.HR_EMPLOYEE_SECTION.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection> fetchByName(String... values) {
        return fetch(HrEmployeeSection.HR_EMPLOYEE_SECTION.NAME, values);
    }

    /**
     * Fetch records that have <code>priority IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection> fetchByPriority(Integer... values) {
        return fetch(HrEmployeeSection.HR_EMPLOYEE_SECTION.PRIORITY, values);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeSection> fetchByStatus(Byte... values) {
        return fetch(HrEmployeeSection.HR_EMPLOYEE_SECTION.STATUS, values);
    }
}
