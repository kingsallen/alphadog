/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.analytics.tables;


import com.moseeker.db.analytics.Analytics;
import com.moseeker.db.analytics.tables.records.StmAbapplyPidRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StmAbapplyPid extends TableImpl<StmAbapplyPidRecord> {

	private static final long serialVersionUID = -529045226;

	/**
	 * The reference instance of <code>analytics.stm_abapply_pid</code>
	 */
	public static final StmAbapplyPid STM_ABAPPLY_PID = new StmAbapplyPid();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<StmAbapplyPidRecord> getRecordType() {
		return StmAbapplyPidRecord.class;
	}

	/**
	 * The column <code>analytics.stm_abapply_pid.id</code>.
	 */
	public final TableField<StmAbapplyPidRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>analytics.stm_abapply_pid.ab_group</code>.
	 */
	public final TableField<StmAbapplyPidRecord, String> AB_GROUP = createField("ab_group", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>analytics.stm_abapply_pid.apply</code>. page：从我感兴趣投递，interest：从我感兴趣投递
	 */
	public final TableField<StmAbapplyPidRecord, String> APPLY = createField("apply", org.jooq.impl.SQLDataType.CLOB, this, "page：从我感兴趣投递，interest：从我感兴趣投递");

	/**
	 * The column <code>analytics.stm_abapply_pid.create_time</code>.
	 */
	public final TableField<StmAbapplyPidRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * The column <code>analytics.stm_abapply_pid.is_recom</code>.
	 */
	public final TableField<StmAbapplyPidRecord, Byte> IS_RECOM = createField("is_recom", org.jooq.impl.SQLDataType.TINYINT, this, "");

	/**
	 * The column <code>analytics.stm_abapply_pid.pid</code>.
	 */
	public final TableField<StmAbapplyPidRecord, String> PID = createField("pid", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * The column <code>analytics.stm_abapply_pid.recom</code>.
	 */
	public final TableField<StmAbapplyPidRecord, String> RECOM = createField("recom", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * Create a <code>analytics.stm_abapply_pid</code> table reference
	 */
	public StmAbapplyPid() {
		this("stm_abapply_pid", null);
	}

	/**
	 * Create an aliased <code>analytics.stm_abapply_pid</code> table reference
	 */
	public StmAbapplyPid(String alias) {
		this(alias, STM_ABAPPLY_PID);
	}

	private StmAbapplyPid(String alias, Table<StmAbapplyPidRecord> aliased) {
		this(alias, aliased, null);
	}

	private StmAbapplyPid(String alias, Table<StmAbapplyPidRecord> aliased, Field<?>[] parameters) {
		super(alias, Analytics.ANALYTICS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StmAbapplyPid as(String alias) {
		return new StmAbapplyPid(alias, this);
	}

	/**
	 * Rename this table
	 */
	public StmAbapplyPid rename(String name) {
		return new StmAbapplyPid(name, null);
	}
}
