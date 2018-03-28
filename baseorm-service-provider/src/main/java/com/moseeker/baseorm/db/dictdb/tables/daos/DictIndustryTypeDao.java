/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.daos;


import com.moseeker.baseorm.db.dictdb.tables.DictIndustryType;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryTypeRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 行业一级分类字典表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictIndustryTypeDao extends DAOImpl<DictIndustryTypeRecord, com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustryType, Integer> {

    /**
     * Create a new DictIndustryTypeDao without any configuration
     */
    public DictIndustryTypeDao() {
        super(DictIndustryType.DICT_INDUSTRY_TYPE, com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustryType.class);
    }

    /**
     * Create a new DictIndustryTypeDao with an attached configuration
     */
    public DictIndustryTypeDao(Configuration configuration) {
        super(DictIndustryType.DICT_INDUSTRY_TYPE, com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustryType.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustryType object) {
        return object.getCode();
    }

    /**
     * Fetch records that have <code>code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustryType> fetchByCode(Integer... values) {
        return fetch(DictIndustryType.DICT_INDUSTRY_TYPE.CODE, values);
    }

    /**
     * Fetch a unique record that has <code>code = value</code>
     */
    public com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustryType fetchOneByCode(Integer value) {
        return fetchOne(DictIndustryType.DICT_INDUSTRY_TYPE.CODE, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustryType> fetchByName(String... values) {
        return fetch(DictIndustryType.DICT_INDUSTRY_TYPE.NAME, values);
    }
}
