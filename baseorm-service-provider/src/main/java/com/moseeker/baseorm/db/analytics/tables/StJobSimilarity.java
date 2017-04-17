/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.StJobSimilarityRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StJobSimilarity extends TableImpl<StJobSimilarityRecord> {

    private static final long serialVersionUID = -1951574156;

    /**
     * The reference instance of <code>analytics.st_job_similarity</code>
     */
    public static final StJobSimilarity ST_JOB_SIMILARITY = new StJobSimilarity();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StJobSimilarityRecord> getRecordType() {
        return StJobSimilarityRecord.class;
    }

    /**
     * The column <code>analytics.st_job_similarity.id</code>.
     */
    public final TableField<StJobSimilarityRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.st_job_similarity.pos_id</code>.
     */
    public final TableField<StJobSimilarityRecord, Integer> POS_ID = createField("pos_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>analytics.st_job_similarity.recom_id</code>.
     */
    public final TableField<StJobSimilarityRecord, Integer> RECOM_ID = createField("recom_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>analytics.st_job_similarity.rec_sim</code>. 相似度
     */
    public final TableField<StJobSimilarityRecord, Double> REC_SIM = createField("rec_sim", org.jooq.impl.SQLDataType.FLOAT, this, "相似度");

    /**
     * The column <code>analytics.st_job_similarity.department_id</code>. 部门id
     */
    public final TableField<StJobSimilarityRecord, Integer> DEPARTMENT_ID = createField("department_id", org.jooq.impl.SQLDataType.INTEGER, this, "部门id");

    /**
     * Create a <code>analytics.st_job_similarity</code> table reference
     */
    public StJobSimilarity() {
        this("st_job_similarity", null);
    }

    /**
     * Create an aliased <code>analytics.st_job_similarity</code> table reference
     */
    public StJobSimilarity(String alias) {
        this(alias, ST_JOB_SIMILARITY);
    }

    private StJobSimilarity(String alias, Table<StJobSimilarityRecord> aliased) {
        this(alias, aliased, null);
    }

    private StJobSimilarity(String alias, Table<StJobSimilarityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Analytics.ANALYTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<StJobSimilarityRecord, Integer> getIdentity() {
        return Keys.IDENTITY_ST_JOB_SIMILARITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StJobSimilarityRecord> getPrimaryKey() {
        return Keys.KEY_ST_JOB_SIMILARITY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StJobSimilarityRecord>> getKeys() {
        return Arrays.<UniqueKey<StJobSimilarityRecord>>asList(Keys.KEY_ST_JOB_SIMILARITY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StJobSimilarity as(String alias) {
        return new StJobSimilarity(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StJobSimilarity rename(String name) {
        return new StJobSimilarity(name, null);
    }
}
