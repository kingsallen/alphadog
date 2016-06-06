/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.candidatedb.tables.records;


import com.moseeker.db.candidatedb.tables.JobResumeOther;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * 自定义简历副本记录表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobResumeOtherRecord extends UpdatableRecordImpl<JobResumeOtherRecord> implements Record4<UInteger, String, Timestamp, Timestamp> {

	private static final long serialVersionUID = 170305441;

	/**
	 * Setter for <code>candidatedb.job_resume_other.app_id</code>. job_application.id
	 */
	public void setAppId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>candidatedb.job_resume_other.app_id</code>. job_application.id
	 */
	public UInteger getAppId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>candidatedb.job_resume_other.other</code>. 自定义字段
	 */
	public void setOther(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>candidatedb.job_resume_other.other</code>. 自定义字段
	 */
	public String getOther() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>candidatedb.job_resume_other.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>candidatedb.job_resume_other.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>candidatedb.job_resume_other.update_time</code>. 更新时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>candidatedb.job_resume_other.update_time</code>. 更新时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<UInteger> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UInteger, String, Timestamp, Timestamp> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<UInteger, String, Timestamp, Timestamp> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return JobResumeOther.JOB_RESUME_OTHER.APP_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return JobResumeOther.JOB_RESUME_OTHER.OTHER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field3() {
		return JobResumeOther.JOB_RESUME_OTHER.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return JobResumeOther.JOB_RESUME_OTHER.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value1() {
		return getAppId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getOther();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value3() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getUpdateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobResumeOtherRecord value1(UInteger value) {
		setAppId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobResumeOtherRecord value2(String value) {
		setOther(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobResumeOtherRecord value3(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobResumeOtherRecord value4(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobResumeOtherRecord values(UInteger value1, String value2, Timestamp value3, Timestamp value4) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached JobResumeOtherRecord
	 */
	public JobResumeOtherRecord() {
		super(JobResumeOther.JOB_RESUME_OTHER);
	}

	/**
	 * Create a detached, initialised JobResumeOtherRecord
	 */
	public JobResumeOtherRecord(UInteger appId, String other, Timestamp createTime, Timestamp updateTime) {
		super(JobResumeOther.JOB_RESUME_OTHER);

		setValue(0, appId);
		setValue(1, other);
		setValue(2, createTime);
		setValue(3, updateTime);
	}
}