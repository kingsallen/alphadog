/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.analytics.tables.records;


import com.moseeker.baseorm.db.analytics.tables.JdResumeSimilarity;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


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
public class JdResumeSimilarityRecord extends UpdatableRecordImpl<JdResumeSimilarityRecord> implements Record5<Integer, Integer, Integer, String, Double> {

    private static final long serialVersionUID = -479886944;

    /**
     * Setter for <code>analytics.jd_resume_similarity.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>analytics.jd_resume_similarity.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>analytics.jd_resume_similarity.hr_id</code>.
     */
    public void setHrId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>analytics.jd_resume_similarity.hr_id</code>.
     */
    public Integer getHrId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>analytics.jd_resume_similarity.jd_id</code>.
     */
    public void setJdId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>analytics.jd_resume_similarity.jd_id</code>.
     */
    public Integer getJdId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>analytics.jd_resume_similarity.resume_id</code>.
     */
    public void setResumeId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>analytics.jd_resume_similarity.resume_id</code>.
     */
    public String getResumeId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>analytics.jd_resume_similarity.sim_weight</code>.
     */
    public void setSimWeight(Double value) {
        set(4, value);
    }

    /**
     * Getter for <code>analytics.jd_resume_similarity.sim_weight</code>.
     */
    public Double getSimWeight() {
        return (Double) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Integer, String, Double> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Integer, Integer, Integer, String, Double> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return JdResumeSimilarity.JD_RESUME_SIMILARITY.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return JdResumeSimilarity.JD_RESUME_SIMILARITY.HR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return JdResumeSimilarity.JD_RESUME_SIMILARITY.JD_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return JdResumeSimilarity.JD_RESUME_SIMILARITY.RESUME_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Double> field5() {
        return JdResumeSimilarity.JD_RESUME_SIMILARITY.SIM_WEIGHT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getHrId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getJdId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getResumeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double value5() {
        return getSimWeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JdResumeSimilarityRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JdResumeSimilarityRecord value2(Integer value) {
        setHrId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JdResumeSimilarityRecord value3(Integer value) {
        setJdId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JdResumeSimilarityRecord value4(String value) {
        setResumeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JdResumeSimilarityRecord value5(Double value) {
        setSimWeight(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JdResumeSimilarityRecord values(Integer value1, Integer value2, Integer value3, String value4, Double value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JdResumeSimilarityRecord
     */
    public JdResumeSimilarityRecord() {
        super(JdResumeSimilarity.JD_RESUME_SIMILARITY);
    }

    /**
     * Create a detached, initialised JdResumeSimilarityRecord
     */
    public JdResumeSimilarityRecord(Integer id, Integer hrId, Integer jdId, String resumeId, Double simWeight) {
        super(JdResumeSimilarity.JD_RESUME_SIMILARITY);

        set(0, id);
        set(1, hrId);
        set(2, jdId);
        set(3, resumeId);
        set(4, simWeight);
    }
}
