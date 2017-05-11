/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.tables.records.HrResourceOnlineRecord;

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
public class HrResourceOnline extends TableImpl<HrResourceOnlineRecord> {

	private static final long serialVersionUID = -841061144;

	/**
	 * The reference instance of <code>hrdb.hr_resource_online</code>
	 */
	public static final HrResourceOnline HR_RESOURCE_ONLINE = new HrResourceOnline();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrResourceOnlineRecord> getRecordType() {
		return HrResourceOnlineRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_resource_online.id</code>.
	 */
	public final TableField<HrResourceOnlineRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>hrdb.hr_resource_online.res_url</code>. 资源链接
	 */
	public final TableField<HrResourceOnlineRecord, String> RES_URL = createField("res_url", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), this, "资源链接");

	/**
	 * The column <code>hrdb.hr_resource_online.res_type</code>. 0: image  1: video
	 */
	public final TableField<HrResourceOnlineRecord, Integer> RES_TYPE = createField("res_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "0: image  1: video");

	/**
	 * The column <code>hrdb.hr_resource_online.remark</code>. 备注资源
	 */
	public final TableField<HrResourceOnlineRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR.length(2048).nullable(false).defaulted(true), this, "备注资源");

	/**
	 * Create a <code>hrdb.hr_resource_online</code> table reference
	 */
	public HrResourceOnline() {
		this("hr_resource_online", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_resource_online</code> table reference
	 */
	public HrResourceOnline(String alias) {
		this(alias, HR_RESOURCE_ONLINE);
	}

	private HrResourceOnline(String alias, Table<HrResourceOnlineRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrResourceOnline(String alias, Table<HrResourceOnlineRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrResourceOnline as(String alias) {
		return new HrResourceOnline(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrResourceOnline rename(String name) {
		return new HrResourceOnline(name, null);
	}
}
