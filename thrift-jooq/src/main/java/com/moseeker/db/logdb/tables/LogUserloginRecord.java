/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.logdb.tables;


import com.moseeker.db.logdb.Keys;
import com.moseeker.db.logdb.Logdb;
import com.moseeker.db.logdb.tables.records.LogUserloginRecordRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;


/**
 * 用户登陆日志
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LogUserloginRecord extends TableImpl<LogUserloginRecordRecord> {

	private static final long serialVersionUID = -226500978;

	/**
	 * The reference instance of <code>logdb.log_userlogin_record</code>
	 */
	public static final LogUserloginRecord LOG_USERLOGIN_RECORD = new LogUserloginRecord();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<LogUserloginRecordRecord> getRecordType() {
		return LogUserloginRecordRecord.class;
	}

	/**
	 * The column <code>logdb.log_userlogin_record.id</code>. 主key
	 */
	public final TableField<LogUserloginRecordRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "主key");

	/**
	 * The column <code>logdb.log_userlogin_record.user_id</code>.
	 */
	public final TableField<LogUserloginRecordRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>logdb.log_userlogin_record.actiontype</code>. 1 登陆 2 登出
	 */
	public final TableField<LogUserloginRecordRecord, Integer> ACTIONTYPE = createField("actiontype", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "1 登陆 2 登出");

	/**
	 * The column <code>logdb.log_userlogin_record.ip</code>.
	 */
	public final TableField<LogUserloginRecordRecord, String> IP = createField("ip", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * The column <code>logdb.log_userlogin_record.create_time</code>.
	 */
	public final TableField<LogUserloginRecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>logdb.log_userlogin_record</code> table reference
	 */
	public LogUserloginRecord() {
		this("log_userlogin_record", null);
	}

	/**
	 * Create an aliased <code>logdb.log_userlogin_record</code> table reference
	 */
	public LogUserloginRecord(String alias) {
		this(alias, LOG_USERLOGIN_RECORD);
	}

	private LogUserloginRecord(String alias, Table<LogUserloginRecordRecord> aliased) {
		this(alias, aliased, null);
	}

	private LogUserloginRecord(String alias, Table<LogUserloginRecordRecord> aliased, Field<?>[] parameters) {
		super(alias, Logdb.LOGDB, aliased, parameters, "用户登陆日志");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<LogUserloginRecordRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_LOG_USERLOGIN_RECORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<LogUserloginRecordRecord> getPrimaryKey() {
		return Keys.KEY_LOG_USERLOGIN_RECORD_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<LogUserloginRecordRecord>> getKeys() {
		return Arrays.<UniqueKey<LogUserloginRecordRecord>>asList(Keys.KEY_LOG_USERLOGIN_RECORD_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogUserloginRecord as(String alias) {
		return new LogUserloginRecord(alias, this);
	}

	/**
	 * Rename this table
	 */
	public LogUserloginRecord rename(String name) {
		return new LogUserloginRecord(name, null);
	}
}