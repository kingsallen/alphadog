/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.tables.records.HrdbHrHtml5UniqueStatisticsRecord;

import java.sql.Date;

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
public class HrdbHrHtml5UniqueStatistics extends TableImpl<HrdbHrHtml5UniqueStatisticsRecord> {

	private static final long serialVersionUID = -1583289794;

	/**
	 * The reference instance of <code>hrdb.hrdb.hr_html5_unique_statistics</code>
	 */
	public static final HrdbHrHtml5UniqueStatistics HRDB_HR_HTML5_UNIQUE_STATISTICS = new HrdbHrHtml5UniqueStatistics();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrdbHrHtml5UniqueStatisticsRecord> getRecordType() {
		return HrdbHrHtml5UniqueStatisticsRecord.class;
	}

	/**
	 * The column <code>hrdb.hrdb.hr_html5_unique_statistics.topic_id</code>.
	 */
	public final TableField<HrdbHrHtml5UniqueStatisticsRecord, Long> TOPIC_ID = createField("topic_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>hrdb.hrdb.hr_html5_unique_statistics.view_num_uv</code>.
	 */
	public final TableField<HrdbHrHtml5UniqueStatisticsRecord, Long> VIEW_NUM_UV = createField("view_num_uv", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>hrdb.hrdb.hr_html5_unique_statistics.recom_view_num_uv</code>.
	 */
	public final TableField<HrdbHrHtml5UniqueStatisticsRecord, Double> RECOM_VIEW_NUM_UV = createField("recom_view_num_uv", org.jooq.impl.SQLDataType.DOUBLE, this, "");

	/**
	 * The column <code>hrdb.hrdb.hr_html5_unique_statistics.company_id</code>.
	 */
	public final TableField<HrdbHrHtml5UniqueStatisticsRecord, Long> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * The column <code>hrdb.hrdb.hr_html5_unique_statistics.create_date</code>.
	 */
	public final TableField<HrdbHrHtml5UniqueStatisticsRecord, Date> CREATE_DATE = createField("create_date", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>hrdb.hrdb.hr_html5_unique_statistics.info_type</code>.
	 */
	public final TableField<HrdbHrHtml5UniqueStatisticsRecord, String> INFO_TYPE = createField("info_type", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * Create a <code>hrdb.hrdb.hr_html5_unique_statistics</code> table reference
	 */
	public HrdbHrHtml5UniqueStatistics() {
		this("hrdb.hr_html5_unique_statistics", null);
	}

	/**
	 * Create an aliased <code>hrdb.hrdb.hr_html5_unique_statistics</code> table reference
	 */
	public HrdbHrHtml5UniqueStatistics(String alias) {
		this(alias, HRDB_HR_HTML5_UNIQUE_STATISTICS);
	}

	private HrdbHrHtml5UniqueStatistics(String alias, Table<HrdbHrHtml5UniqueStatisticsRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrdbHrHtml5UniqueStatistics(String alias, Table<HrdbHrHtml5UniqueStatisticsRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrdbHrHtml5UniqueStatistics as(String alias) {
		return new HrdbHrHtml5UniqueStatistics(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrdbHrHtml5UniqueStatistics rename(String name) {
		return new HrdbHrHtml5UniqueStatistics(name, null);
	}
}
