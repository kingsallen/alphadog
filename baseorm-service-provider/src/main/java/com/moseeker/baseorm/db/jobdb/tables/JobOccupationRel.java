/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.jobdb.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;

import com.moseeker.baseorm.db.jobdb.Jobdb;
import com.moseeker.baseorm.db.jobdb.Keys;
import com.moseeker.baseorm.db.jobdb.tables.records.JobOccupationRelRecord;


/**
 * 职位与职能关系表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobOccupationRel extends TableImpl<JobOccupationRelRecord> {

	private static final long serialVersionUID = -1705925525;

	/**
	 * The reference instance of <code>jobdb.job_occupation_rel</code>
	 */
	public static final JobOccupationRel JOB_OCCUPATION_REL = new JobOccupationRel();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JobOccupationRelRecord> getRecordType() {
		return JobOccupationRelRecord.class;
	}

	/**
	 * The column <code>jobdb.job_occupation_rel.pid</code>. 职位id
	 */
	public final TableField<JobOccupationRelRecord, Integer> PID = createField("pid", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "职位id");

	/**
	 * The column <code>jobdb.job_occupation_rel.code</code>. 职位职能code
	 */
	public final TableField<JobOccupationRelRecord, Integer> CODE = createField("code", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "职位职能code");

	/**
	 * Create a <code>jobdb.job_occupation_rel</code> table reference
	 */
	public JobOccupationRel() {
		this("job_occupation_rel", null);
	}

	/**
	 * Create an aliased <code>jobdb.job_occupation_rel</code> table reference
	 */
	public JobOccupationRel(String alias) {
		this(alias, JOB_OCCUPATION_REL);
	}

	private JobOccupationRel(String alias, Table<JobOccupationRelRecord> aliased) {
		this(alias, aliased, null);
	}

	private JobOccupationRel(String alias, Table<JobOccupationRelRecord> aliased, Field<?>[] parameters) {
		super(alias, Jobdb.JOBDB, aliased, parameters, "职位与职能关系表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JobOccupationRelRecord> getPrimaryKey() {
		return Keys.KEY_JOB_OCCUPATION_REL_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JobOccupationRelRecord>> getKeys() {
		return Arrays.<UniqueKey<JobOccupationRelRecord>>asList(Keys.KEY_JOB_OCCUPATION_REL_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobOccupationRel as(String alias) {
		return new JobOccupationRel(alias, this);
	}

	/**
	 * Rename this table
	 */
	public JobOccupationRel rename(String name) {
		return new JobOccupationRel(name, null);
	}
}
