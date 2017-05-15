/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbScratchCardRecord;

import java.math.BigDecimal;
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
public class HrHbScratchCard extends TableImpl<HrHbScratchCardRecord> {

	private static final long serialVersionUID = 966497871;

	/**
	 * The reference instance of <code>hrdb.hr_hb_scratch_card</code>
	 */
	public static final HrHbScratchCard HR_HB_SCRATCH_CARD = new HrHbScratchCard();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrHbScratchCardRecord> getRecordType() {
		return HrHbScratchCardRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.id</code>.
	 */
	public final TableField<HrHbScratchCardRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.wechat_id</code>.
	 */
	public final TableField<HrHbScratchCardRecord, Integer> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.cardno</code>. 随机字符串
	 */
	public final TableField<HrHbScratchCardRecord, String> CARDNO = createField("cardno", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false), this, "随机字符串");

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.status</code>. 状态: 0：未拆开 1：已拆开
	 */
	public final TableField<HrHbScratchCardRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "状态: 0：未拆开 1：已拆开");

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.amount</code>. 红包金额： 0.00 表示不发红包
	 */
	public final TableField<HrHbScratchCardRecord, BigDecimal> AMOUNT = createField("amount", org.jooq.impl.SQLDataType.DECIMAL.precision(4, 2).nullable(false).defaulted(true), this, "红包金额： 0.00 表示不发红包");

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.hb_config_id</code>.
	 */
	public final TableField<HrHbScratchCardRecord, Integer> HB_CONFIG_ID = createField("hb_config_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.bagging_openid</code>. 聚合号的 openid
	 */
	public final TableField<HrHbScratchCardRecord, String> BAGGING_OPENID = createField("bagging_openid", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaulted(true), this, "聚合号的 openid");

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.create_time</code>.
	 */
	public final TableField<HrHbScratchCardRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.hb_item_id</code>.
	 */
	public final TableField<HrHbScratchCardRecord, Integer> HB_ITEM_ID = createField("hb_item_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>hrdb.hr_hb_scratch_card.tips</code>. 是否是小费 0:不是，1:是
	 */
	public final TableField<HrHbScratchCardRecord, Byte> TIPS = createField("tips", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否是小费 0:不是，1:是");

	/**
	 * Create a <code>hrdb.hr_hb_scratch_card</code> table reference
	 */
	public HrHbScratchCard() {
		this("hr_hb_scratch_card", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_hb_scratch_card</code> table reference
	 */
	public HrHbScratchCard(String alias) {
		this(alias, HR_HB_SCRATCH_CARD);
	}

	private HrHbScratchCard(String alias, Table<HrHbScratchCardRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrHbScratchCard(String alias, Table<HrHbScratchCardRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrHbScratchCardRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_HB_SCRATCH_CARD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrHbScratchCardRecord> getPrimaryKey() {
		return Keys.KEY_HR_HB_SCRATCH_CARD_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrHbScratchCardRecord>> getKeys() {
		return Arrays.<UniqueKey<HrHbScratchCardRecord>>asList(Keys.KEY_HR_HB_SCRATCH_CARD_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrHbScratchCard as(String alias) {
		return new HrHbScratchCard(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrHbScratchCard rename(String name) {
		return new HrHbScratchCard(name, null);
	}
}
