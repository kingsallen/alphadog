/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.jobdb.tables.records;


import com.moseeker.db.jobdb.tables.JobPositionExt;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 职位信息扩展表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionExtRecord extends UpdatableRecordImpl<JobPositionExtRecord> implements Record5<Integer, String, Timestamp, Timestamp, Integer> {

	private static final long serialVersionUID = -385317328;

	/**
	 * Setter for <code>jobdb.job_position_ext.pid</code>. job_position.id
	 */
	public void setPid(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>jobdb.job_position_ext.pid</code>. job_position.id
	 */
	public Integer getPid() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>jobdb.job_position_ext.job_custom_id</code>. job_custom.id
	 */
	public void setJobCustomId(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>jobdb.job_position_ext.job_custom_id</code>. job_custom.id
	 */
	public String getJobCustomId() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>jobdb.job_position_ext.create_time</code>. 创建时间
	 */
	public void setCreateTime(Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>jobdb.job_position_ext.create_time</code>. 创建时间
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>jobdb.job_position_ext.update_time</code>. 修改时间
	 */
	public void setUpdateTime(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>jobdb.job_position_ext.update_time</code>. 修改时间
	 */
	public Timestamp getUpdateTime() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>jobdb.job_position_ext.job_occupation_id</code>. jobdb.job_occupation.id
	 */
	public void setJobOccupationId(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>jobdb.job_position_ext.job_occupation_id</code>. jobdb.job_occupation.id
	 */
	public Integer getJobOccupationId() {
		return (Integer) getValue(4);
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
	public Row5<Integer, String, Timestamp, Timestamp, Integer> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<Integer, String, Timestamp, Timestamp, Integer> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return JobPositionExt.JOB_POSITION_EXT.PID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return JobPositionExt.JOB_POSITION_EXT.JOB_CUSTOM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field3() {
		return JobPositionExt.JOB_POSITION_EXT.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return JobPositionExt.JOB_POSITION_EXT.UPDATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field5() {
		return JobPositionExt.JOB_POSITION_EXT.JOB_OCCUPATION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getPid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getJobCustomId();
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
	public Integer value5() {
		return getJobOccupationId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobPositionExtRecord value1(Integer value) {
		setPid(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobPositionExtRecord value2(String value) {
		setJobCustomId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobPositionExtRecord value3(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobPositionExtRecord value4(Timestamp value) {
		setUpdateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobPositionExtRecord value5(Integer value) {
		setJobOccupationId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobPositionExtRecord values(Integer value1, String value2, Timestamp value3, Timestamp value4, Integer value5) {
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
	 * Create a detached JobPositionExtRecord
	 */
	public JobPositionExtRecord() {
		super(JobPositionExt.JOB_POSITION_EXT);
	}

	/**
	 * Create a detached, initialised JobPositionExtRecord
	 */
	public JobPositionExtRecord(Integer pid, String jobCustomId, Timestamp createTime, Timestamp updateTime, Integer jobOccupationId) {
		super(JobPositionExt.JOB_POSITION_EXT);

		setValue(0, pid);
		setValue(1, jobCustomId);
		setValue(2, createTime);
		setValue(3, updateTime);
		setValue(4, jobOccupationId);
	}
}
