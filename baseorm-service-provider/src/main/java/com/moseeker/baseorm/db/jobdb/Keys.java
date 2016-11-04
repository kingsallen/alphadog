/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.jobdb;


import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobApplicationConf;
import com.moseeker.baseorm.db.jobdb.tables.JobApplicationStatusBeisen;
import com.moseeker.baseorm.db.jobdb.tables.JobCustom;
import com.moseeker.baseorm.db.jobdb.tables.JobOccupationRel;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionExt;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionShareTplConf;
import com.moseeker.baseorm.db.jobdb.tables.JobResumeOther;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationConfRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationStatusBeisenRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobCustomRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobOccupationRelRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionExtRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionShareTplConfRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobResumeOtherRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.types.UInteger;


/**
 * A class modelling foreign key relationships between tables of the <code>jobdb</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<JobApplicationRecord, UInteger> IDENTITY_JOB_APPLICATION = Identities0.IDENTITY_JOB_APPLICATION;
    public static final Identity<JobApplicationConfRecord, Integer> IDENTITY_JOB_APPLICATION_CONF = Identities0.IDENTITY_JOB_APPLICATION_CONF;
    public static final Identity<JobApplicationStatusBeisenRecord, Integer> IDENTITY_JOB_APPLICATION_STATUS_BEISEN = Identities0.IDENTITY_JOB_APPLICATION_STATUS_BEISEN;
    public static final Identity<JobCustomRecord, Integer> IDENTITY_JOB_CUSTOM = Identities0.IDENTITY_JOB_CUSTOM;
    public static final Identity<JobPositionRecord, Integer> IDENTITY_JOB_POSITION = Identities0.IDENTITY_JOB_POSITION;
    public static final Identity<JobPositionShareTplConfRecord, Integer> IDENTITY_JOB_POSITION_SHARE_TPL_CONF = Identities0.IDENTITY_JOB_POSITION_SHARE_TPL_CONF;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<JobApplicationRecord> KEY_JOB_APPLICATION_PRIMARY = UniqueKeys0.KEY_JOB_APPLICATION_PRIMARY;
    public static final UniqueKey<JobApplicationConfRecord> KEY_JOB_APPLICATION_CONF_PRIMARY = UniqueKeys0.KEY_JOB_APPLICATION_CONF_PRIMARY;
    public static final UniqueKey<JobApplicationStatusBeisenRecord> KEY_JOB_APPLICATION_STATUS_BEISEN_PRIMARY = UniqueKeys0.KEY_JOB_APPLICATION_STATUS_BEISEN_PRIMARY;
    public static final UniqueKey<JobCustomRecord> KEY_JOB_CUSTOM_PRIMARY = UniqueKeys0.KEY_JOB_CUSTOM_PRIMARY;
    public static final UniqueKey<JobOccupationRelRecord> KEY_JOB_OCCUPATION_REL_PRIMARY = UniqueKeys0.KEY_JOB_OCCUPATION_REL_PRIMARY;
    public static final UniqueKey<JobPositionRecord> KEY_JOB_POSITION_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_PRIMARY;
    public static final UniqueKey<JobPositionExtRecord> KEY_JOB_POSITION_EXT_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_EXT_PRIMARY;
    public static final UniqueKey<JobPositionShareTplConfRecord> KEY_JOB_POSITION_SHARE_TPL_CONF_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_SHARE_TPL_CONF_PRIMARY;
    public static final UniqueKey<JobResumeOtherRecord> KEY_JOB_RESUME_OTHER_PRIMARY = UniqueKeys0.KEY_JOB_RESUME_OTHER_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<JobApplicationRecord, UInteger> IDENTITY_JOB_APPLICATION = createIdentity(JobApplication.JOB_APPLICATION, JobApplication.JOB_APPLICATION.ID);
        public static Identity<JobApplicationConfRecord, Integer> IDENTITY_JOB_APPLICATION_CONF = createIdentity(JobApplicationConf.JOB_APPLICATION_CONF, JobApplicationConf.JOB_APPLICATION_CONF.ID);
        public static Identity<JobApplicationStatusBeisenRecord, Integer> IDENTITY_JOB_APPLICATION_STATUS_BEISEN = createIdentity(JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN, JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN.ID);
        public static Identity<JobCustomRecord, Integer> IDENTITY_JOB_CUSTOM = createIdentity(JobCustom.JOB_CUSTOM, JobCustom.JOB_CUSTOM.ID);
        public static Identity<JobPositionRecord, Integer> IDENTITY_JOB_POSITION = createIdentity(JobPosition.JOB_POSITION, JobPosition.JOB_POSITION.ID);
        public static Identity<JobPositionShareTplConfRecord, Integer> IDENTITY_JOB_POSITION_SHARE_TPL_CONF = createIdentity(JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF, JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<JobApplicationRecord> KEY_JOB_APPLICATION_PRIMARY = createUniqueKey(JobApplication.JOB_APPLICATION, "KEY_job_application_PRIMARY", JobApplication.JOB_APPLICATION.ID);
        public static final UniqueKey<JobApplicationConfRecord> KEY_JOB_APPLICATION_CONF_PRIMARY = createUniqueKey(JobApplicationConf.JOB_APPLICATION_CONF, "KEY_job_application_conf_PRIMARY", JobApplicationConf.JOB_APPLICATION_CONF.ID);
        public static final UniqueKey<JobApplicationStatusBeisenRecord> KEY_JOB_APPLICATION_STATUS_BEISEN_PRIMARY = createUniqueKey(JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN, "KEY_job_application_status_beisen_PRIMARY", JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN.ID);
        public static final UniqueKey<JobCustomRecord> KEY_JOB_CUSTOM_PRIMARY = createUniqueKey(JobCustom.JOB_CUSTOM, "KEY_job_custom_PRIMARY", JobCustom.JOB_CUSTOM.ID);
        public static final UniqueKey<JobOccupationRelRecord> KEY_JOB_OCCUPATION_REL_PRIMARY = createUniqueKey(JobOccupationRel.JOB_OCCUPATION_REL, "KEY_job_occupation_rel_PRIMARY", JobOccupationRel.JOB_OCCUPATION_REL.PID);
        public static final UniqueKey<JobPositionRecord> KEY_JOB_POSITION_PRIMARY = createUniqueKey(JobPosition.JOB_POSITION, "KEY_job_position_PRIMARY", JobPosition.JOB_POSITION.ID);
        public static final UniqueKey<JobPositionExtRecord> KEY_JOB_POSITION_EXT_PRIMARY = createUniqueKey(JobPositionExt.JOB_POSITION_EXT, "KEY_job_position_ext_PRIMARY", JobPositionExt.JOB_POSITION_EXT.PID);
        public static final UniqueKey<JobPositionShareTplConfRecord> KEY_JOB_POSITION_SHARE_TPL_CONF_PRIMARY = createUniqueKey(JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF, "KEY_job_position_share_tpl_conf_PRIMARY", JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF.ID);
        public static final UniqueKey<JobResumeOtherRecord> KEY_JOB_RESUME_OTHER_PRIMARY = createUniqueKey(JobResumeOther.JOB_RESUME_OTHER, "KEY_job_resume_other_PRIMARY", JobResumeOther.JOB_RESUME_OTHER.APP_ID);
    }
}
