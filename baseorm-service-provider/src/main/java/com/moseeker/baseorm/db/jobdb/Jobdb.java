/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.jobdb;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobApplicationConf;
import com.moseeker.baseorm.db.jobdb.tables.JobApplicationStatusBeisen;
import com.moseeker.baseorm.db.jobdb.tables.JobCustom;
import com.moseeker.baseorm.db.jobdb.tables.JobOccupation;
import com.moseeker.baseorm.db.jobdb.tables.JobOccupationRel;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionExt;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionShareTplConf;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionTopic;
import com.moseeker.baseorm.db.jobdb.tables.JobResumeOther;


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
public class Jobdb extends SchemaImpl {

	private static final long serialVersionUID = -1437463576;

	/**
	 * The reference instance of <code>jobdb</code>
	 */
	public static final Jobdb JOBDB = new Jobdb();

	/**
	 * No further instances allowed
	 */
	private Jobdb() {
		super("jobdb");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			JobApplication.JOB_APPLICATION,
			JobApplicationConf.JOB_APPLICATION_CONF,
			JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN,
			JobCustom.JOB_CUSTOM,
			JobOccupation.JOB_OCCUPATION,
			JobOccupationRel.JOB_OCCUPATION_REL,
			JobPosition.JOB_POSITION,
			JobPositionCity.JOB_POSITION_CITY,
			JobPositionExt.JOB_POSITION_EXT,
			JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF,
			JobPositionTopic.JOB_POSITION_TOPIC,
			JobResumeOther.JOB_RESUME_OTHER);
	}
}
