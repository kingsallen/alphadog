/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.analytics.tables;


import com.moseeker.db.analytics.Analytics;
import com.moseeker.db.analytics.Keys;
import com.moseeker.db.analytics.tables.records.StmRecomRecord;

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
public class StmRecom extends TableImpl<StmRecomRecord> {

	private static final long serialVersionUID = 1854829042;

	/**
	 * The reference instance of <code>analytics.stm_recom</code>
	 */
	public static final StmRecom STM_RECOM = new StmRecom();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<StmRecomRecord> getRecordType() {
		return StmRecomRecord.class;
	}

	/**
	 * The column <code>analytics.stm_recom.id</code>.
	 */
	public final TableField<StmRecomRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>analytics.stm_recom.recom</code>.
	 */
	public final TableField<StmRecomRecord, String> RECOM = createField("recom", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * The column <code>analytics.stm_recom.viewer_id</code>.
	 */
	public final TableField<StmRecomRecord, String> VIEWER_ID = createField("viewer_id", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

	/**
	 * The column <code>analytics.stm_recom.create_time</code>.
	 */
	public final TableField<StmRecomRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>analytics.stm_recom.click_from</code>. 来自, 0:未知, 朋友圈(timeline ) 1, 微信群(groupmessage) 2, 个人消息(singlemessage) 3
	 */
	public final TableField<StmRecomRecord, Integer> CLICK_FROM = createField("click_from", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "来自, 0:未知, 朋友圈(timeline ) 1, 微信群(groupmessage) 2, 个人消息(singlemessage) 3");

	/**
	 * The column <code>analytics.stm_recom.pid</code>.
	 */
	public final TableField<StmRecomRecord, Integer> PID = createField("pid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * Create a <code>analytics.stm_recom</code> table reference
	 */
	public StmRecom() {
		this("stm_recom", null);
	}

	/**
	 * Create an aliased <code>analytics.stm_recom</code> table reference
	 */
	public StmRecom(String alias) {
		this(alias, STM_RECOM);
	}

	private StmRecom(String alias, Table<StmRecomRecord> aliased) {
		this(alias, aliased, null);
	}

	private StmRecom(String alias, Table<StmRecomRecord> aliased, Field<?>[] parameters) {
		super(alias, Analytics.ANALYTICS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<StmRecomRecord, Integer> getIdentity() {
		return Keys.IDENTITY_STM_RECOM;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<StmRecomRecord> getPrimaryKey() {
		return Keys.KEY_STM_RECOM_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<StmRecomRecord>> getKeys() {
		return Arrays.<UniqueKey<StmRecomRecord>>asList(Keys.KEY_STM_RECOM_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StmRecom as(String alias) {
		return new StmRecom(alias, this);
	}

	/**
	 * Rename this table
	 */
	public StmRecom rename(String name) {
		return new StmRecom(name, null);
	}
}
