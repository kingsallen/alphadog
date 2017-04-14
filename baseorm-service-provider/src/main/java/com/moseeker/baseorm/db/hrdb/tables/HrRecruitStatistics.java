/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRecruitStatisticsRecord;

import java.sql.Date;
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
 * 招聘数据次数统计表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrRecruitStatistics extends TableImpl<HrRecruitStatisticsRecord> {

	private static final long serialVersionUID = 1221821466;

	/**
	 * The reference instance of <code>hrdb.hr_recruit_statistics</code>
	 */
	public static final HrRecruitStatistics HR_RECRUIT_STATISTICS = new HrRecruitStatistics();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<HrRecruitStatisticsRecord> getRecordType() {
		return HrRecruitStatisticsRecord.class;
	}

	/**
	 * The column <code>hrdb.hr_recruit_statistics.id</code>. primary key
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "primary key");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.position_id</code>. hr_position.id
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "hr_position.id");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.company_id</code>. company.id
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "company.id");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.jd_pv</code>. JD 页浏览次数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> JD_PV = createField("jd_pv", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "JD 页浏览次数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.recom_jd_pv</code>. JD 页推荐浏览次数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> RECOM_JD_PV = createField("recom_jd_pv", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "JD 页推荐浏览次数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.fav_num</code>. 感兴趣的次数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> FAV_NUM = createField("fav_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "感兴趣的次数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.recom_fav_num</code>. 推荐感兴趣的次数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> RECOM_FAV_NUM = createField("recom_fav_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "推荐感兴趣的次数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.apply_num</code>. 投递次数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> APPLY_NUM = createField("apply_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "投递次数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.recom_apply_num</code>. 推荐投递次数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> RECOM_APPLY_NUM = createField("recom_apply_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "推荐投递次数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.create_date</code>. 创建日期
	 */
	public final TableField<HrRecruitStatisticsRecord, Date> CREATE_DATE = createField("create_date", org.jooq.impl.SQLDataType.DATE.nullable(false), this, "创建日期");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.after_review_num</code>. 评审通过人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> AFTER_REVIEW_NUM = createField("after_review_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "评审通过人数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.recom_after_review_num</code>. 推荐评审通过人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> RECOM_AFTER_REVIEW_NUM = createField("recom_after_review_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "推荐评审通过人数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.after_interview_num</code>. 面试通过人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> AFTER_INTERVIEW_NUM = createField("after_interview_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "面试通过人数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.recom_after_interview_num</code>. 推荐面试通过人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> RECOM_AFTER_INTERVIEW_NUM = createField("recom_after_interview_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "推荐面试通过人数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.on_board_num</code>. 入职人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> ON_BOARD_NUM = createField("on_board_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "入职人数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.recom_on_board_num</code>. 推荐入职人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> RECOM_ON_BOARD_NUM = createField("recom_on_board_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "推荐入职人数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.not_viewed_num</code>. 简历未查阅人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> NOT_VIEWED_NUM = createField("not_viewed_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "简历未查阅人数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.recom_not_viewed_num</code>. 推荐简历未查阅人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> RECOM_NOT_VIEWED_NUM = createField("recom_not_viewed_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "推荐简历未查阅人数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.not_qualified_num</code>. 简历不匹配人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> NOT_QUALIFIED_NUM = createField("not_qualified_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "简历不匹配人数");

	/**
	 * The column <code>hrdb.hr_recruit_statistics.recom_not_qualified_num</code>. 推荐简历不匹配人数
	 */
	public final TableField<HrRecruitStatisticsRecord, Integer> RECOM_NOT_QUALIFIED_NUM = createField("recom_not_qualified_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "推荐简历不匹配人数");

	/**
	 * Create a <code>hrdb.hr_recruit_statistics</code> table reference
	 */
	public HrRecruitStatistics() {
		this("hr_recruit_statistics", null);
	}

	/**
	 * Create an aliased <code>hrdb.hr_recruit_statistics</code> table reference
	 */
	public HrRecruitStatistics(String alias) {
		this(alias, HR_RECRUIT_STATISTICS);
	}

	private HrRecruitStatistics(String alias, Table<HrRecruitStatisticsRecord> aliased) {
		this(alias, aliased, null);
	}

	private HrRecruitStatistics(String alias, Table<HrRecruitStatisticsRecord> aliased, Field<?>[] parameters) {
		super(alias, Hrdb.HRDB, aliased, parameters, "招聘数据次数统计表");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<HrRecruitStatisticsRecord, Integer> getIdentity() {
		return Keys.IDENTITY_HR_RECRUIT_STATISTICS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<HrRecruitStatisticsRecord> getPrimaryKey() {
		return Keys.KEY_HR_RECRUIT_STATISTICS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<HrRecruitStatisticsRecord>> getKeys() {
		return Arrays.<UniqueKey<HrRecruitStatisticsRecord>>asList(Keys.KEY_HR_RECRUIT_STATISTICS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HrRecruitStatistics as(String alias) {
		return new HrRecruitStatistics(alias, this);
	}

	/**
	 * Rename this table
	 */
	public HrRecruitStatistics rename(String name) {
		return new HrRecruitStatistics(name, null);
	}
}
