/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.daos;


import com.moseeker.baseorm.db.dictdb.tables.Dict_58jobOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_58jobOccupationRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * 58职能表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Dict_58jobOccupationDao extends DAOImpl<Dict_58jobOccupationRecord, com.moseeker.baseorm.db.dictdb.tables.pojos.Dict_58jobOccupation, Integer> {

    /**
     * Create a new Dict_58jobOccupationDao without any configuration
     */
    public Dict_58jobOccupationDao() {
        super(Dict_58jobOccupation.DICT_58JOB_OCCUPATION, com.moseeker.baseorm.db.dictdb.tables.pojos.Dict_58jobOccupation.class);
    }

    /**
     * Create a new Dict_58jobOccupationDao with an attached configuration
     */
    public Dict_58jobOccupationDao(Configuration configuration) {
        super(Dict_58jobOccupation.DICT_58JOB_OCCUPATION, com.moseeker.baseorm.db.dictdb.tables.pojos.Dict_58jobOccupation.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(com.moseeker.baseorm.db.dictdb.tables.pojos.Dict_58jobOccupation object) {
        return object.getCode();
    }

    /**
     * Fetch records that have <code>code IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.dictdb.tables.pojos.Dict_58jobOccupation> fetchByCode(Integer... values) {
        return fetch(Dict_58jobOccupation.DICT_58JOB_OCCUPATION.CODE, values);
    }

    /**
     * Fetch a unique record that has <code>code = value</code>
     */
    public com.moseeker.baseorm.db.dictdb.tables.pojos.Dict_58jobOccupation fetchOneByCode(Integer value) {
        return fetchOne(Dict_58jobOccupation.DICT_58JOB_OCCUPATION.CODE, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.dictdb.tables.pojos.Dict_58jobOccupation> fetchByName(String... values) {
        return fetch(Dict_58jobOccupation.DICT_58JOB_OCCUPATION.NAME, values);
    }

    /**
     * Fetch records that have <code>pcode IN (values)</code>
     */
    public List<com.moseeker.baseorm.db.dictdb.tables.pojos.Dict_58jobOccupation> fetchByPcode(Integer... values) {
        return fetch(Dict_58jobOccupation.DICT_58JOB_OCCUPATION.PCODE, values);
    }
}
