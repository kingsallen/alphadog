/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.daos;


import com.moseeker.baseorm.db.dictdb.tables.DictIndustry;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictIndustryDao extends DAOImpl<DictIndustryRecord, com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustry, Integer> {

    /**
     * Create a new DictIndustryDao without any configuration
     */
    public DictIndustryDao() {
        super(DictIndustry.DICT_INDUSTRY, com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustry.class);
    }

    /**
     * Create a new DictIndustryDao with an attached configuration
     */
    public DictIndustryDao(Configuration configuration) {
        super(DictIndustry.DICT_INDUSTRY, com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustry.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustry object) {
        return object.getCode();
    }

    /**
     * Fetch records that have <code>code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustry> fetchByCode(Integer... values) {
        return fetch(DictIndustry.DICT_INDUSTRY.CODE, values);
    }

    /**
     * Fetch a unique record that has <code>code = value</code>
     */
    public com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustry fetchOneByCode(Integer value) {
        return fetchOne(DictIndustry.DICT_INDUSTRY.CODE, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustry> fetchByName(String... values) {
        return fetch(DictIndustry.DICT_INDUSTRY.NAME, values);
    }

    /**
     * Fetch records that have <code>type IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.dictdb.tables.pojos.DictIndustry> fetchByType(Integer... values) {
        return fetch(DictIndustry.DICT_INDUSTRY.TYPE, values);
    }
}
