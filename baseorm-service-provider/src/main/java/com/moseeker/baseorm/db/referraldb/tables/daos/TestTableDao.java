/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb.tables.daos;


import com.moseeker.baseorm.db.referraldb.tables.TestTable;
import com.moseeker.baseorm.db.referraldb.tables.records.TestTableRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 测试
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TestTableDao extends DAOImpl<TestTableRecord, com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable, Integer> {

    /**
     * Create a new TestTableDao without any configuration
     */
    public TestTableDao() {
        super(TestTable.TEST_TABLE, com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable.class);
    }

    /**
     * Create a new TestTableDao with an attached configuration
     */
    public TestTableDao(Configuration configuration) {
        super(TestTable.TEST_TABLE, com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable> fetchById(Integer... values) {
        return fetch(TestTable.TEST_TABLE.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable fetchOneById(Integer value) {
        return fetchOne(TestTable.TEST_TABLE.ID, value);
    }

    /**
     * Fetch records that have <code>employeeid IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable> fetchByEmployeeid(String... values) {
        return fetch(TestTable.TEST_TABLE.EMPLOYEEID, values);
    }

    /**
     * Fetch records that have <code>company_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable> fetchByCompanyId(Integer... values) {
        return fetch(TestTable.TEST_TABLE.COMPANY_ID, values);
    }

    /**
     * Fetch records that have <code>role_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable> fetchByRoleId(Integer... values) {
        return fetch(TestTable.TEST_TABLE.ROLE_ID, values);
    }

    /**
     * Fetch records that have <code>wxuser_id IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.TestTable> fetchByWxuserId(Integer... values) {
        return fetch(TestTable.TEST_TABLE.WXUSER_ID, values);
    }
}