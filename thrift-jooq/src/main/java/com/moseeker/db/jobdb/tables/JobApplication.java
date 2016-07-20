/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.jobdb.tables;


import com.moseeker.db.jobdb.Jobdb;
import com.moseeker.db.jobdb.Keys;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;

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
public class JobApplication extends TableImpl<JobApplicationRecord> {

	private static final long serialVersionUID = -2139444642;

	/**
	 * The reference instance of <code>jobdb.job_application</code>
	 */
	public static final JobApplication JOB_APPLICATION = new JobApplication();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<JobApplicationRecord> getRecordType() {
		return JobApplicationRecord.class;
	}

	/**
	 * The column <code>jobdb.job_application.id</code>.
	 */
	public final TableField<JobApplicationRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>jobdb.job_application.wechat_id</code>. sys_wechat.id, 公众号ID
	 */
	public final TableField<JobApplicationRecord, UInteger> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "sys_wechat.id, 公众号ID");

	/**
	 * The column <code>jobdb.job_application.position_id</code>. hr_position.id, 职位ID
	 */
	public final TableField<JobApplicationRecord, UInteger> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "hr_position.id, 职位ID");

	/**
	 * The column <code>jobdb.job_application.recommender_id</code>. user_wx_user.id, 微信ID
	 */
	public final TableField<JobApplicationRecord, UInteger> RECOMMENDER_ID = createField("recommender_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "user_wx_user.id, 微信ID");

	/**
	 * The column <code>jobdb.job_application.submit_time</code>. 申请提交时间
	 */
	public final TableField<JobApplicationRecord, Timestamp> SUBMIT_TIME = createField("submit_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "申请提交时间");

	/**
	 * The column <code>jobdb.job_application.status_id</code>. hr_award_config.id, 申请状态ID
	 */
	public final TableField<JobApplicationRecord, UInteger> STATUS_ID = createField("status_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "hr_award_config.id, 申请状态ID");

	/**
	 * The column <code>jobdb.job_application.l_application_id</code>. ATS的申请ID
	 */
	public final TableField<JobApplicationRecord, UInteger> L_APPLICATION_ID = createField("l_application_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "ATS的申请ID");

	/**
	 * The column <code>jobdb.job_application.reward</code>. 当前申请的积分记录
	 */
	public final TableField<JobApplicationRecord, UInteger> REWARD = createField("reward", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "当前申请的积分记录");

	/**
	 * The column <code>jobdb.job_application.source_id</code>. job_source.id, 对应的ATS ID
	 */
	public final TableField<JobApplicationRecord, UInteger> SOURCE_ID = createField("source_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "job_source.id, 对应的ATS ID");

	/**
	 * The column <code>jobdb.job_application._create_time</code>. time stamp when record created
	 */
	public final TableField<JobApplicationRecord, Timestamp> _CREATE_TIME = createField("_create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "time stamp when record created");

	/**
	 * The column <code>jobdb.job_application.applier_id</code>. sys_user.id, 用户ID
	 */
	public final TableField<JobApplicationRecord, UInteger> APPLIER_ID = createField("applier_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "sys_user.id, 用户ID");

	/**
	 * The column <code>jobdb.job_application.interview_id</code>. app_interview.id, 面试ID
	 */
	public final TableField<JobApplicationRecord, UInteger> INTERVIEW_ID = createField("interview_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.defaulted(true), this, "app_interview.id, 面试ID");

	/**
	 * The column <code>jobdb.job_application.resume_id</code>. mongodb collection application[id]
	 */
	public final TableField<JobApplicationRecord, String> RESUME_ID = createField("resume_id", org.jooq.impl.SQLDataType.VARCHAR.length(24).nullable(false).defaulted(true), this, "mongodb collection application[id]");

	/**
	 * The column <code>jobdb.job_application.ats_status</code>. 0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified
	 */
	public final TableField<JobApplicationRecord, Integer> ATS_STATUS = createField("ats_status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified");

	/**
	 * The column <code>jobdb.job_application.applier_name</code>. 姓名或微信昵称
	 */
	public final TableField<JobApplicationRecord, String> APPLIER_NAME = createField("applier_name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaulted(true), this, "姓名或微信昵称");

	/**
	 * The column <code>jobdb.job_application.disable</code>. 是否有效，0：有效，1：无效
	 */
	public final TableField<JobApplicationRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "是否有效，0：有效，1：无效");

	/**
	 * The column <code>jobdb.job_application.routine</code>. 申请来源 0:微信企业端 1:微信聚合端 10:pc端
	 */
	public final TableField<JobApplicationRecord, Integer> ROUTINE = createField("routine", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "申请来源 0:微信企业端 1:微信聚合端 10:pc端");

	/**
	 * The column <code>jobdb.job_application.is_viewed</code>. 该申请是否被浏览，0：已浏览，1：未浏览
	 */
	public final TableField<JobApplicationRecord, Byte> IS_VIEWED = createField("is_viewed", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "该申请是否被浏览，0：已浏览，1：未浏览");

	/**
	 * The column <code>jobdb.job_application.not_suitable</code>. 是否不合适，0：合适，1：不合适
	 */
	public final TableField<JobApplicationRecord, Byte> NOT_SUITABLE = createField("not_suitable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaulted(true), this, "是否不合适，0：合适，1：不合适");

	/**
	 * The column <code>jobdb.job_application.company_id</code>. company.id，公司表ID
	 */
	public final TableField<JobApplicationRecord, UInteger> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "company.id，公司表ID");

	/**
	 * The column <code>jobdb.job_application.update_time</code>.
	 */
	public final TableField<JobApplicationRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>jobdb.job_application.app_tpl_id</code>. 申请状态,hr_award_config_template.id
	 */
	public final TableField<JobApplicationRecord, UInteger> APP_TPL_ID = createField("app_tpl_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaulted(true), this, "申请状态,hr_award_config_template.id");

	/**
	 * The column <code>jobdb.job_application.proxy</code>. 是否是代理投递 0：正常数据，1：代理假投递
	 */
	public final TableField<JobApplicationRecord, Byte> PROXY = createField("proxy", org.jooq.impl.SQLDataType.TINYINT.defaulted(true), this, "是否是代理投递 0：正常数据，1：代理假投递");

	/**
	 * The column <code>jobdb.job_application.apply_type</code>. 投递区分， 0：profile投递， 1：email投递
	 */
	public final TableField<JobApplicationRecord, Integer> APPLY_TYPE = createField("apply_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "投递区分， 0：profile投递， 1：email投递");

	/**
	 * The column <code>jobdb.job_application.email_status</code>. 0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；9，提取邮件失败
	 */
	public final TableField<JobApplicationRecord, Integer> EMAIL_STATUS = createField("email_status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；9，提取邮件失败");

	/**
	 * The column <code>jobdb.job_application.view_count</code>. profile浏览次数
	 */
	public final TableField<JobApplicationRecord, Integer> VIEW_COUNT = createField("view_count", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "profile浏览次数");

	/**
	 * Create a <code>jobdb.job_application</code> table reference
	 */
	public JobApplication() {
		this("job_application", null);
	}

	/**
	 * Create an aliased <code>jobdb.job_application</code> table reference
	 */
	public JobApplication(String alias) {
		this(alias, JOB_APPLICATION);
	}

	private JobApplication(String alias, Table<JobApplicationRecord> aliased) {
		this(alias, aliased, null);
	}

	private JobApplication(String alias, Table<JobApplicationRecord> aliased, Field<?>[] parameters) {
		super(alias, Jobdb.JOBDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<JobApplicationRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_JOB_APPLICATION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<JobApplicationRecord> getPrimaryKey() {
		return Keys.KEY_JOB_APPLICATION_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<JobApplicationRecord>> getKeys() {
		return Arrays.<UniqueKey<JobApplicationRecord>>asList(Keys.KEY_JOB_APPLICATION_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobApplication as(String alias) {
		return new JobApplication(alias, this);
	}

	/**
	 * Rename this table
	 */
	public JobApplication rename(String name) {
		return new JobApplication(name, null);
	}
}
