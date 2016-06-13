/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.analytics.tables;


import com.moseeker.db.analytics.Analytics;
import com.moseeker.db.analytics.Keys;
import com.moseeker.db.analytics.tables.records.StmReqTypeRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 请求类型表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StmReqType extends TableImpl<StmReqTypeRecord> {

	private static final long serialVersionUID = -1565961417;

	/**
	 * The reference instance of <code>analytics.stm_req_type</code>
	 */
	public static final StmReqType STM_REQ_TYPE = new StmReqType();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<StmReqTypeRecord> getRecordType() {
		return StmReqTypeRecord.class;
	}

	/**
	 * The column <code>analytics.stm_req_type.id</code>. 主键
	 */
	public final TableField<StmReqTypeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主键");

	/**
	 * The column <code>analytics.stm_req_type.req_type</code>. 请求类型(post,get)
	 */
	public final TableField<StmReqTypeRecord, String> REQ_TYPE = createField("req_type", org.jooq.impl.SQLDataType.VARCHAR.length(10), this, "请求类型(post,get)");

	/**
	 * Create a <code>analytics.stm_req_type</code> table reference
	 */
	public StmReqType() {
		this("stm_req_type", null);
	}

	/**
	 * Create an aliased <code>analytics.stm_req_type</code> table reference
	 */
	public StmReqType(String alias) {
		this(alias, STM_REQ_TYPE);
	}

	private StmReqType(String alias, Table<StmReqTypeRecord> aliased) {
		this(alias, aliased, null);
	}

	private StmReqType(String alias, Table<StmReqTypeRecord> aliased, Field<?>[] parameters) {
		super(alias, Analytics.ANALYTICS, aliased, parameters, "请求类型表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<StmReqTypeRecord, Integer> getIdentity() {
		return Keys.IDENTITY_STM_REQ_TYPE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<StmReqTypeRecord> getPrimaryKey() {
		return Keys.KEY_STM_REQ_TYPE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<StmReqTypeRecord>> getKeys() {
		return Arrays.<UniqueKey<StmReqTypeRecord>>asList(Keys.KEY_STM_REQ_TYPE_PRIMARY, Keys.KEY_STM_REQ_TYPE_REQ_TYPE_UNIQUE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StmReqType as(String alias) {
		return new StmReqType(alias, this);
	}

	/**
	 * Rename this table
	 */
	public StmReqType rename(String name) {
		return new StmReqType(name, null);
	}
}
