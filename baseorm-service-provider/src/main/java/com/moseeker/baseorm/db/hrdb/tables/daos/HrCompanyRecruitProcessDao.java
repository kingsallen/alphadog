/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.daos;


import com.moseeker.baseorm.db.hrdb.tables.HrCompanyRecruitProcess;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecruitProcessRecord;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 公司招聘流程配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyRecruitProcessDao extends DAOImpl<HrCompanyRecruitProcessRecord, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess, Integer> {

    /**
     * Create a new HrCompanyRecruitProcessDao without any configuration
     */
    public HrCompanyRecruitProcessDao() {
        super(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess.class);
    }

    /**
     * Create a new HrCompanyRecruitProcessDao with an attached configuration
     */
    public HrCompanyRecruitProcessDao(Configuration configuration) {
        super(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS, com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess> fetchById(Integer... values) {
        return fetch(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess fetchOneById(Integer value) {
        return fetchOne(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS.ID, value);
    }

    /**
     * Fetch records that have <code>recruit_process_name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess> fetchByRecruitProcessName(String... values) {
        return fetch(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS.RECRUIT_PROCESS_NAME, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess> fetchByCompanyId(Integer... values) {
        return fetch(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>disable IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess> fetchByDisable(Integer... values) {
        return fetch(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS.DISABLE, values);
    }

    /**
     * Fetch records that have <code>process_order IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess> fetchByProcessOrder(Integer... values) {
        return fetch(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS.PROCESS_ORDER, values);
    }

    /**
     * Fetch records that have <code>create_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess> fetchByCreateTime(Timestamp... values) {
        return fetch(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS.CREATE_TIME, values);
    }

    /**
     * Fetch records that have <code>update_time IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyRecruitProcess> fetchByUpdateTime(Timestamp... values) {
        return fetch(HrCompanyRecruitProcess.HR_COMPANY_RECRUIT_PROCESS.UPDATE_TIME, values);
    }
}
