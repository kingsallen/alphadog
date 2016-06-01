/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.jobdb;


import com.moseeker.db.jobdb.tables.JobApplication;
import com.moseeker.db.jobdb.tables.JobApplicationConf;
import com.moseeker.db.jobdb.tables.JobApplicationStatusBeisen;
import com.moseeker.db.jobdb.tables.JobPosition;
import com.moseeker.db.jobdb.tables.JobPositionCity;
import com.moseeker.db.jobdb.tables.JobPositionShareTplConf;
import com.moseeker.db.jobdb.tables.JobPositionTopic;
import com.moseeker.db.jobdb.tables.JobResumeBasic;
import com.moseeker.db.jobdb.tables.JobResumeOther;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in jobDB
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

	/**
	 * The table jobDB.job_application
	 */
	public static final JobApplication JOB_APPLICATION = com.moseeker.db.jobdb.tables.JobApplication.JOB_APPLICATION;

	/**
	 * 部门申请配置表
	 */
	public static final JobApplicationConf JOB_APPLICATION_CONF = com.moseeker.db.jobdb.tables.JobApplicationConf.JOB_APPLICATION_CONF;

	/**
	 * 申请状态记录（ats北森）
	 */
	public static final JobApplicationStatusBeisen JOB_APPLICATION_STATUS_BEISEN = com.moseeker.db.jobdb.tables.JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN;

	/**
	 * The table jobDB.job_position
	 */
	public static final JobPosition JOB_POSITION = com.moseeker.db.jobdb.tables.JobPosition.JOB_POSITION;

	/**
	 * The table jobDB.job_position_city
	 */
	public static final JobPositionCity JOB_POSITION_CITY = com.moseeker.db.jobdb.tables.JobPositionCity.JOB_POSITION_CITY;

	/**
	 * 职位分享描述配置模板
	 */
	public static final JobPositionShareTplConf JOB_POSITION_SHARE_TPL_CONF = com.moseeker.db.jobdb.tables.JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF;

	/**
	 * 职位主题活动关系表
	 */
	public static final JobPositionTopic JOB_POSITION_TOPIC = com.moseeker.db.jobdb.tables.JobPositionTopic.JOB_POSITION_TOPIC;

	/**
	 * 申请简历简述表
	 */
	public static final JobResumeBasic JOB_RESUME_BASIC = com.moseeker.db.jobdb.tables.JobResumeBasic.JOB_RESUME_BASIC;

	/**
	 * 自定义简历副本记录表
	 */
	public static final JobResumeOther JOB_RESUME_OTHER = com.moseeker.db.jobdb.tables.JobResumeOther.JOB_RESUME_OTHER;
}
