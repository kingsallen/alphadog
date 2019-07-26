/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb;


import com.moseeker.baseorm.db.jobdb.tables.*;
import com.moseeker.baseorm.db.jobdb.tables.records.*;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>jobdb</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<JobApplicationRecord, Integer> IDENTITY_JOB_APPLICATION = Identities0.IDENTITY_JOB_APPLICATION;
    public static final Identity<JobCustomRecord, Integer> IDENTITY_JOB_CUSTOM = Identities0.IDENTITY_JOB_CUSTOM;
    public static final Identity<JobOccupationRecord, Integer> IDENTITY_JOB_OCCUPATION = Identities0.IDENTITY_JOB_OCCUPATION;
    public static final Identity<JobPcAdvertisementRecord, Integer> IDENTITY_JOB_PC_ADVERTISEMENT = Identities0.IDENTITY_JOB_PC_ADVERTISEMENT;
    public static final Identity<JobPcRecommendPositionsModuleRecord, Integer> IDENTITY_JOB_PC_RECOMMEND_POSITIONS_MODULE = Identities0.IDENTITY_JOB_PC_RECOMMEND_POSITIONS_MODULE;
    public static final Identity<JobPcRecommendPositionItemRecord, Integer> IDENTITY_JOB_PC_RECOMMEND_POSITION_ITEM = Identities0.IDENTITY_JOB_PC_RECOMMEND_POSITION_ITEM;
    public static final Identity<JobPcReportedRecord, Integer> IDENTITY_JOB_PC_REPORTED = Identities0.IDENTITY_JOB_PC_REPORTED;
    public static final Identity<JobPositionRecord, Integer> IDENTITY_JOB_POSITION = Identities0.IDENTITY_JOB_POSITION;
    public static final Identity<JobPosition_0413Record, Integer> IDENTITY_JOB_POSITION_0413 = Identities0.IDENTITY_JOB_POSITION_0413;
    public static final Identity<JobPosition_1030Record, Integer> IDENTITY_JOB_POSITION_1030 = Identities0.IDENTITY_JOB_POSITION_1030;
    public static final Identity<JobPositionCcmailRecord, Integer> IDENTITY_JOB_POSITION_CCMAIL = Identities0.IDENTITY_JOB_POSITION_CCMAIL;
    public static final Identity<JobPositionJob58MappingRecord, Integer> IDENTITY_JOB_POSITION_JOB58_MAPPING = Identities0.IDENTITY_JOB_POSITION_JOB58_MAPPING;
    public static final Identity<JobPositionLiepinMappingRecord, Integer> IDENTITY_JOB_POSITION_LIEPIN_MAPPING = Identities0.IDENTITY_JOB_POSITION_LIEPIN_MAPPING;
    public static final Identity<JobPositionShareTplConfRecord, Integer> IDENTITY_JOB_POSITION_SHARE_TPL_CONF = Identities0.IDENTITY_JOB_POSITION_SHARE_TPL_CONF;
    public static final Identity<JobApplicationAtsProcessPO, Integer> IDENTITY_JOB_APPLICATION_ATS_PROCESS = Identities0.IDENTITY_JOB_APPLICATION_ATS_PROCESS;
    public static final Identity<JobPositionAtsProcessPO, Integer> IDENTITY_JOB_POSITION_ATS_PROCESS = Identities0.IDENTITY_JOB_POSITION_ATS_PROCESS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<JobApplicationRecord> KEY_JOB_APPLICATION_PRIMARY = UniqueKeys0.KEY_JOB_APPLICATION_PRIMARY;
    public static final UniqueKey<JobApplicationAtsRecord> KEY_JOB_APPLICATION_ATS_PRIMARY = UniqueKeys0.KEY_JOB_APPLICATION_ATS_PRIMARY;
    public static final UniqueKey<JobCustomRecord> KEY_JOB_CUSTOM_PRIMARY = UniqueKeys0.KEY_JOB_CUSTOM_PRIMARY;
    public static final UniqueKey<JobOccupationRecord> KEY_JOB_OCCUPATION_PRIMARY = UniqueKeys0.KEY_JOB_OCCUPATION_PRIMARY;
    public static final UniqueKey<JobPcAdvertisementRecord> KEY_JOB_PC_ADVERTISEMENT_PRIMARY = UniqueKeys0.KEY_JOB_PC_ADVERTISEMENT_PRIMARY;
    public static final UniqueKey<JobPcRecommendPositionsModuleRecord> KEY_JOB_PC_RECOMMEND_POSITIONS_MODULE_PRIMARY = UniqueKeys0.KEY_JOB_PC_RECOMMEND_POSITIONS_MODULE_PRIMARY;
    public static final UniqueKey<JobPcRecommendPositionItemRecord> KEY_JOB_PC_RECOMMEND_POSITION_ITEM_PRIMARY = UniqueKeys0.KEY_JOB_PC_RECOMMEND_POSITION_ITEM_PRIMARY;
    public static final UniqueKey<JobPcReportedRecord> KEY_JOB_PC_REPORTED_PRIMARY = UniqueKeys0.KEY_JOB_PC_REPORTED_PRIMARY;
    public static final UniqueKey<JobPositionRecord> KEY_JOB_POSITION_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_PRIMARY;
    public static final UniqueKey<JobPosition_0413Record> KEY_JOB_POSITION_0413_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_0413_PRIMARY;
    public static final UniqueKey<JobPosition_1030Record> KEY_JOB_POSITION_1030_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_1030_PRIMARY;
    public static final UniqueKey<JobPositionCcmailRecord> KEY_JOB_POSITION_CCMAIL_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_CCMAIL_PRIMARY;
    public static final UniqueKey<JobPositionCityRecord> KEY_JOB_POSITION_CITY_IDX_PID_CODE = UniqueKeys0.KEY_JOB_POSITION_CITY_IDX_PID_CODE;
    public static final UniqueKey<JobPositionExtRecord> KEY_JOB_POSITION_EXT_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_EXT_PRIMARY;
    public static final UniqueKey<JobPositionHrCompanyFeatureRecord> KEY_JOB_POSITION_HR_COMPANY_FEATURE_PID_FID = UniqueKeys0.KEY_JOB_POSITION_HR_COMPANY_FEATURE_PID_FID;
    public static final UniqueKey<JobPositionJob58MappingRecord> KEY_JOB_POSITION_JOB58_MAPPING_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_JOB58_MAPPING_PRIMARY;
    public static final UniqueKey<JobPositionJob58MappingRecord> KEY_JOB_POSITION_JOB58_MAPPING_INFO_ID_INDEX = UniqueKeys0.KEY_JOB_POSITION_JOB58_MAPPING_INFO_ID_INDEX;
    public static final UniqueKey<JobPositionLiepinMappingRecord> KEY_JOB_POSITION_LIEPIN_MAPPING_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_LIEPIN_MAPPING_PRIMARY;
    public static final UniqueKey<JobPositionProfileFilterRecord> KEY_JOB_POSITION_PROFILE_FILTER_PID_FID = UniqueKeys0.KEY_JOB_POSITION_PROFILE_FILTER_PID_FID;
    public static final UniqueKey<JobPositionShareTplConfRecord> KEY_JOB_POSITION_SHARE_TPL_CONF_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_SHARE_TPL_CONF_PRIMARY;
    public static final UniqueKey<JobResumeOtherRecord> KEY_JOB_RESUME_OTHER_PRIMARY = UniqueKeys0.KEY_JOB_RESUME_OTHER_PRIMARY;
    public static final UniqueKey<JobApplicationAtsProcessPO> KEY_JOB_APPLICATION_ATS_PROCESS_PRIMARY = UniqueKeys0.KEY_JOB_APPLICATION_ATS_PROCESS_PRIMARY;
    public static final UniqueKey<JobApplicationAtsProcessPO> KEY_JOB_APPLICATION_ATS_PROCESS_JOB_APPLICATION_ATS_PROCESS_APP_ID_UINDEX = UniqueKeys0.KEY_JOB_APPLICATION_ATS_PROCESS_JOB_APPLICATION_ATS_PROCESS_APP_ID_UINDEX;
    public static final UniqueKey<JobPositionAtsProcessPO> KEY_JOB_POSITION_ATS_PROCESS_PRIMARY = UniqueKeys0.KEY_JOB_POSITION_ATS_PROCESS_PRIMARY;
    public static final UniqueKey<JobPositionAtsProcessPO> KEY_JOB_POSITION_ATS_PROCESS_JOB_POSITION_ATS_PROCESS_PID_PROCESS_ID_UINDEX = UniqueKeys0.KEY_JOB_POSITION_ATS_PROCESS_JOB_POSITION_ATS_PROCESS_PID_PROCESS_ID_UINDEX;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<JobApplicationRecord, Integer> IDENTITY_JOB_APPLICATION = createIdentity(JobApplication.JOB_APPLICATION, JobApplication.JOB_APPLICATION.ID);
        public static Identity<JobCustomRecord, Integer> IDENTITY_JOB_CUSTOM = createIdentity(JobCustom.JOB_CUSTOM, JobCustom.JOB_CUSTOM.ID);
        public static Identity<JobOccupationRecord, Integer> IDENTITY_JOB_OCCUPATION = createIdentity(JobOccupation.JOB_OCCUPATION, JobOccupation.JOB_OCCUPATION.ID);
        public static Identity<JobPcAdvertisementRecord, Integer> IDENTITY_JOB_PC_ADVERTISEMENT = createIdentity(JobPcAdvertisement.JOB_PC_ADVERTISEMENT, JobPcAdvertisement.JOB_PC_ADVERTISEMENT.ID);
        public static Identity<JobPcRecommendPositionsModuleRecord, Integer> IDENTITY_JOB_PC_RECOMMEND_POSITIONS_MODULE = createIdentity(JobPcRecommendPositionsModule.JOB_PC_RECOMMEND_POSITIONS_MODULE, JobPcRecommendPositionsModule.JOB_PC_RECOMMEND_POSITIONS_MODULE.ID);
        public static Identity<JobPcRecommendPositionItemRecord, Integer> IDENTITY_JOB_PC_RECOMMEND_POSITION_ITEM = createIdentity(JobPcRecommendPositionItem.JOB_PC_RECOMMEND_POSITION_ITEM, JobPcRecommendPositionItem.JOB_PC_RECOMMEND_POSITION_ITEM.ID);
        public static Identity<JobPcReportedRecord, Integer> IDENTITY_JOB_PC_REPORTED = createIdentity(JobPcReported.JOB_PC_REPORTED, JobPcReported.JOB_PC_REPORTED.ID);
        public static Identity<JobPositionRecord, Integer> IDENTITY_JOB_POSITION = createIdentity(JobPosition.JOB_POSITION, JobPosition.JOB_POSITION.ID);
        public static Identity<JobPosition_0413Record, Integer> IDENTITY_JOB_POSITION_0413 = createIdentity(JobPosition_0413.JOB_POSITION_0413, JobPosition_0413.JOB_POSITION_0413.ID);
        public static Identity<JobPosition_1030Record, Integer> IDENTITY_JOB_POSITION_1030 = createIdentity(JobPosition_1030.JOB_POSITION_1030, JobPosition_1030.JOB_POSITION_1030.ID);
        public static Identity<JobPositionCcmailRecord, Integer> IDENTITY_JOB_POSITION_CCMAIL = createIdentity(JobPositionCcmail.JOB_POSITION_CCMAIL, JobPositionCcmail.JOB_POSITION_CCMAIL.ID);
        public static Identity<JobPositionJob58MappingRecord, Integer> IDENTITY_JOB_POSITION_JOB58_MAPPING = createIdentity(JobPositionJob58Mapping.JOB_POSITION_JOB58_MAPPING, JobPositionJob58Mapping.JOB_POSITION_JOB58_MAPPING.ID);
        public static Identity<JobPositionLiepinMappingRecord, Integer> IDENTITY_JOB_POSITION_LIEPIN_MAPPING = createIdentity(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING, JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID);
        public static Identity<JobPositionShareTplConfRecord, Integer> IDENTITY_JOB_POSITION_SHARE_TPL_CONF = createIdentity(JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF, JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF.ID);
        public static Identity<JobApplicationAtsProcessPO, Integer> IDENTITY_JOB_APPLICATION_ATS_PROCESS = createIdentity(JobApplicationAtsProcess.JOB_APPLICATION_ATS_PROCESS, JobApplicationAtsProcess.JOB_APPLICATION_ATS_PROCESS.ID);
        public static Identity<JobPositionAtsProcessPO, Integer> IDENTITY_JOB_POSITION_ATS_PROCESS = createIdentity(JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS, JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<JobApplicationRecord> KEY_JOB_APPLICATION_PRIMARY = createUniqueKey(JobApplication.JOB_APPLICATION, "KEY_job_application_PRIMARY", JobApplication.JOB_APPLICATION.ID);
        public static final UniqueKey<JobApplicationAtsRecord> KEY_JOB_APPLICATION_ATS_PRIMARY = createUniqueKey(JobApplicationAts.JOB_APPLICATION_ATS, "KEY_job_application_ats_PRIMARY", JobApplicationAts.JOB_APPLICATION_ATS.APP_ID);
        public static final UniqueKey<JobCustomRecord> KEY_JOB_CUSTOM_PRIMARY = createUniqueKey(JobCustom.JOB_CUSTOM, "KEY_job_custom_PRIMARY", JobCustom.JOB_CUSTOM.ID);
        public static final UniqueKey<JobOccupationRecord> KEY_JOB_OCCUPATION_PRIMARY = createUniqueKey(JobOccupation.JOB_OCCUPATION, "KEY_job_occupation_PRIMARY", JobOccupation.JOB_OCCUPATION.ID);
        public static final UniqueKey<JobPcAdvertisementRecord> KEY_JOB_PC_ADVERTISEMENT_PRIMARY = createUniqueKey(JobPcAdvertisement.JOB_PC_ADVERTISEMENT, "KEY_job_pc_advertisement_PRIMARY", JobPcAdvertisement.JOB_PC_ADVERTISEMENT.ID);
        public static final UniqueKey<JobPcRecommendPositionsModuleRecord> KEY_JOB_PC_RECOMMEND_POSITIONS_MODULE_PRIMARY = createUniqueKey(JobPcRecommendPositionsModule.JOB_PC_RECOMMEND_POSITIONS_MODULE, "KEY_job_pc_recommend_positions_module_PRIMARY", JobPcRecommendPositionsModule.JOB_PC_RECOMMEND_POSITIONS_MODULE.ID);
        public static final UniqueKey<JobPcRecommendPositionItemRecord> KEY_JOB_PC_RECOMMEND_POSITION_ITEM_PRIMARY = createUniqueKey(JobPcRecommendPositionItem.JOB_PC_RECOMMEND_POSITION_ITEM, "KEY_job_pc_recommend_position_item_PRIMARY", JobPcRecommendPositionItem.JOB_PC_RECOMMEND_POSITION_ITEM.ID);
        public static final UniqueKey<JobPcReportedRecord> KEY_JOB_PC_REPORTED_PRIMARY = createUniqueKey(JobPcReported.JOB_PC_REPORTED, "KEY_job_pc_reported_PRIMARY", JobPcReported.JOB_PC_REPORTED.ID);
        public static final UniqueKey<JobPositionRecord> KEY_JOB_POSITION_PRIMARY = createUniqueKey(JobPosition.JOB_POSITION, "KEY_job_position_PRIMARY", JobPosition.JOB_POSITION.ID);
        public static final UniqueKey<JobPosition_0413Record> KEY_JOB_POSITION_0413_PRIMARY = createUniqueKey(JobPosition_0413.JOB_POSITION_0413, "KEY_job_position_0413_PRIMARY", JobPosition_0413.JOB_POSITION_0413.ID);
        public static final UniqueKey<JobPosition_1030Record> KEY_JOB_POSITION_1030_PRIMARY = createUniqueKey(JobPosition_1030.JOB_POSITION_1030, "KEY_job_position_1030_PRIMARY", JobPosition_1030.JOB_POSITION_1030.ID);
        public static final UniqueKey<JobPositionCcmailRecord> KEY_JOB_POSITION_CCMAIL_PRIMARY = createUniqueKey(JobPositionCcmail.JOB_POSITION_CCMAIL, "KEY_job_position_ccmail_PRIMARY", JobPositionCcmail.JOB_POSITION_CCMAIL.ID);
        public static final UniqueKey<JobPositionCityRecord> KEY_JOB_POSITION_CITY_IDX_PID_CODE = createUniqueKey(JobPositionCity.JOB_POSITION_CITY, "KEY_job_position_city_IDX_pid_code", JobPositionCity.JOB_POSITION_CITY.PID, JobPositionCity.JOB_POSITION_CITY.CODE);
        public static final UniqueKey<JobPositionExtRecord> KEY_JOB_POSITION_EXT_PRIMARY = createUniqueKey(JobPositionExt.JOB_POSITION_EXT, "KEY_job_position_ext_PRIMARY", JobPositionExt.JOB_POSITION_EXT.PID);
        public static final UniqueKey<JobPositionHrCompanyFeatureRecord> KEY_JOB_POSITION_HR_COMPANY_FEATURE_PID_FID = createUniqueKey(JobPositionHrCompanyFeature.JOB_POSITION_HR_COMPANY_FEATURE, "KEY_job_position_hr_company_feature_pid_fid", JobPositionHrCompanyFeature.JOB_POSITION_HR_COMPANY_FEATURE.PID, JobPositionHrCompanyFeature.JOB_POSITION_HR_COMPANY_FEATURE.FID);
        public static final UniqueKey<JobPositionJob58MappingRecord> KEY_JOB_POSITION_JOB58_MAPPING_PRIMARY = createUniqueKey(JobPositionJob58Mapping.JOB_POSITION_JOB58_MAPPING, "KEY_job_position_job58_mapping_PRIMARY", JobPositionJob58Mapping.JOB_POSITION_JOB58_MAPPING.ID);
        public static final UniqueKey<JobPositionJob58MappingRecord> KEY_JOB_POSITION_JOB58_MAPPING_INFO_ID_INDEX = createUniqueKey(JobPositionJob58Mapping.JOB_POSITION_JOB58_MAPPING, "KEY_job_position_job58_mapping_info_id_index", JobPositionJob58Mapping.JOB_POSITION_JOB58_MAPPING.INFO_ID);
        public static final UniqueKey<JobPositionLiepinMappingRecord> KEY_JOB_POSITION_LIEPIN_MAPPING_PRIMARY = createUniqueKey(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING, "KEY_job_position_liepin_mapping_PRIMARY", JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID);
        public static final UniqueKey<JobPositionProfileFilterRecord> KEY_JOB_POSITION_PROFILE_FILTER_PID_FID = createUniqueKey(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER, "KEY_job_position_profile_filter_pid_fid", JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER.PID, JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER.PFID);
        public static final UniqueKey<JobPositionShareTplConfRecord> KEY_JOB_POSITION_SHARE_TPL_CONF_PRIMARY = createUniqueKey(JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF, "KEY_job_position_share_tpl_conf_PRIMARY", JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF.ID);
        public static final UniqueKey<JobResumeOtherRecord> KEY_JOB_RESUME_OTHER_PRIMARY = createUniqueKey(JobResumeOther.JOB_RESUME_OTHER, "KEY_job_resume_other_PRIMARY", JobResumeOther.JOB_RESUME_OTHER.APP_ID);
        public static final UniqueKey<JobApplicationAtsProcessPO> KEY_JOB_APPLICATION_ATS_PROCESS_PRIMARY = createUniqueKey(JobApplicationAtsProcess.JOB_APPLICATION_ATS_PROCESS, "KEY_job_application_ats_process_PRIMARY", JobApplicationAtsProcess.JOB_APPLICATION_ATS_PROCESS.ID);
        public static final UniqueKey<JobApplicationAtsProcessPO> KEY_JOB_APPLICATION_ATS_PROCESS_JOB_APPLICATION_ATS_PROCESS_APP_ID_UINDEX = createUniqueKey(JobApplicationAtsProcess.JOB_APPLICATION_ATS_PROCESS, "KEY_job_application_ats_process_job_application_ats_process_app_id_uindex", JobApplicationAtsProcess.JOB_APPLICATION_ATS_PROCESS.APP_ID);
        public static final UniqueKey<JobPositionAtsProcessPO> KEY_JOB_POSITION_ATS_PROCESS_PRIMARY = createUniqueKey(JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS, "KEY_job_position_ats_process_PRIMARY", JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.ID);
        public static final UniqueKey<JobPositionAtsProcessPO> KEY_JOB_POSITION_ATS_PROCESS_JOB_POSITION_ATS_PROCESS_PID_PROCESS_ID_UINDEX = createUniqueKey(JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS, "KEY_job_position_ats_process_job_position_ats_process_pid_process_id_uindex", JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.PID, JobPositionAtsProcess.JOB_POSITION_ATS_PROCESS.PROCESS_ID);
    }
}
