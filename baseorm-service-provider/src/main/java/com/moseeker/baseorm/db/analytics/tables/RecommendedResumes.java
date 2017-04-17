/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.RecommendedResumesRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
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
public class RecommendedResumes extends TableImpl<RecommendedResumesRecord> {

    private static final long serialVersionUID = -1245911389;

    /**
     * The reference instance of <code>analytics.recommended_resumes</code>
     */
    public static final RecommendedResumes RECOMMENDED_RESUMES = new RecommendedResumes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RecommendedResumesRecord> getRecordType() {
        return RecommendedResumesRecord.class;
    }

    /**
     * The column <code>analytics.recommended_resumes.jd_id</code>.
     */
    public final TableField<RecommendedResumesRecord, Integer> JD_ID = createField("jd_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.recommended_resumes.resume_id</code>.
     */
    public final TableField<RecommendedResumesRecord, String> RESUME_ID = createField("resume_id", org.jooq.impl.SQLDataType.VARCHAR.length(64), this, "");

    /**
     * The column <code>analytics.recommended_resumes.hr_id</code>.
     */
    public final TableField<RecommendedResumesRecord, Integer> HR_ID = createField("hr_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>analytics.recommended_resumes.liked</code>.
     */
    public final TableField<RecommendedResumesRecord, Byte> LIKED = createField("liked", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>analytics.recommended_resumes.viewed</code>.
     */
    public final TableField<RecommendedResumesRecord, Byte> VIEWED = createField("viewed", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>analytics.recommended_resumes.sim_weight</code>.
     */
    public final TableField<RecommendedResumesRecord, Double> SIM_WEIGHT = createField("sim_weight", org.jooq.impl.SQLDataType.FLOAT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.FLOAT)), this, "");

    /**
     * Create a <code>analytics.recommended_resumes</code> table reference
     */
    public RecommendedResumes() {
        this("recommended_resumes", null);
    }

    /**
     * Create an aliased <code>analytics.recommended_resumes</code> table reference
     */
    public RecommendedResumes(String alias) {
        this(alias, RECOMMENDED_RESUMES);
    }

    private RecommendedResumes(String alias, Table<RecommendedResumesRecord> aliased) {
        this(alias, aliased, null);
    }

    private RecommendedResumes(String alias, Table<RecommendedResumesRecord> aliased, Field<?>[] parameters) {
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
    public List<UniqueKey<RecommendedResumesRecord>> getKeys() {
        return Arrays.<UniqueKey<RecommendedResumesRecord>>asList(Keys.KEY_RECOMMENDED_RESUMES_SEARCHINDEX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RecommendedResumes as(String alias) {
        return new RecommendedResumes(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RecommendedResumes rename(String name) {
        return new RecommendedResumes(name, null);
    }
}
