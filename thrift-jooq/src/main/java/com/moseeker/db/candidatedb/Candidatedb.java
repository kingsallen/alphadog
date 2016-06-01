/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.candidatedb;


import com.moseeker.db.candidatedb.tables.CandidateCompany;
import com.moseeker.db.candidatedb.tables.CandidatePosition;
import com.moseeker.db.candidatedb.tables.CandidatePositionShareRecord;
import com.moseeker.db.candidatedb.tables.CandidateRecomRecord;
import com.moseeker.db.candidatedb.tables.CandidateRemark;
import com.moseeker.db.candidatedb.tables.CandidateSuggestPosition;
import com.moseeker.db.candidatedb.tables.CandidateVJobPositionRecom;
import com.moseeker.db.candidatedb.tables.JobApplication;
import com.moseeker.db.candidatedb.tables.JobApplicationConf;
import com.moseeker.db.candidatedb.tables.JobApplicationStatusBeisen;
import com.moseeker.db.candidatedb.tables.JobPosition;
import com.moseeker.db.candidatedb.tables.JobPositionCity;
import com.moseeker.db.candidatedb.tables.JobPositionShareTplConf;
import com.moseeker.db.candidatedb.tables.JobPositionTopic;
import com.moseeker.db.candidatedb.tables.JobResumeBasic;
import com.moseeker.db.candidatedb.tables.JobResumeOther;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Candidatedb extends SchemaImpl {

	private static final long serialVersionUID = -675712953;

	/**
	 * The reference instance of <code>candidateDB</code>
	 */
	public static final Candidatedb CANDIDATEDB = new Candidatedb();

	/**
	 * No further instances allowed
	 */
	private Candidatedb() {
		super("candidateDB");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			CandidateCompany.CANDIDATE_COMPANY,
			CandidatePosition.CANDIDATE_POSITION,
			CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD,
			CandidateRecomRecord.CANDIDATE_RECOM_RECORD,
			CandidateRemark.CANDIDATE_REMARK,
			CandidateSuggestPosition.CANDIDATE_SUGGEST_POSITION,
			CandidateVJobPositionRecom.CANDIDATE_V_JOB_POSITION_RECOM,
			JobApplication.JOB_APPLICATION,
			JobApplicationConf.JOB_APPLICATION_CONF,
			JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN,
			JobPosition.JOB_POSITION,
			JobPositionCity.JOB_POSITION_CITY,
			JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF,
			JobPositionTopic.JOB_POSITION_TOPIC,
			JobResumeBasic.JOB_RESUME_BASIC,
			JobResumeOther.JOB_RESUME_OTHER);
	}
}
