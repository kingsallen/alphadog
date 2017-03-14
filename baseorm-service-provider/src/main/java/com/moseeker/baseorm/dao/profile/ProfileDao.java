package com.moseeker.baseorm.dao.profile;

import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.profiledb.Tables;
import com.moseeker.baseorm.db.profiledb.tables.*;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserThirdpartyUser;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.JobPositionExt;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.profile.struct.*;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;
import com.moseeker.thrift.gen.profile.struct.ProfileOther;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by moseeker on 2017/3/13.
 */
@Service
public class ProfileDao extends BaseDaoImpl<ProfileProfileRecord, ProfileProfile> {
    @Override
    protected void initJOOQEntity() {
        tableLike = Tables.PROFILE_PROFILE;
    }

    public Map<String, Object> getRelatedDataByJobApplication(DSLContext create, com.moseeker.thrift.gen.application.struct.JobApplication application, boolean recommender) {
        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        //all from jobdb.job_application
        map.put("job_application", application);

        //job_number and title from jobdb.job_position
        Position position = create
                .select(JobPosition.JOB_POSITION.JOBNUMBER, JobPosition.JOB_POSITION.TITLE)
                .from(JobPosition.JOB_POSITION)
                .where(JobPosition.JOB_POSITION.ID.eq((int) application.getPosition_id()))
                .fetchAnyInto(Position.class);
        if (position != null) {
            map.put("job_position", position);
        }
        System.err.println(application.getId() + ":job_position-----------:" + (System.currentTimeMillis() - start));

        //extra from jobdb.job_position_ext # custom job fields in JSON format
        JobPositionExt positionExt = create
                .select()
                .from(com.moseeker.baseorm.db.jobdb.tables.JobPositionExt.JOB_POSITION_EXT)
                .where(com.moseeker.baseorm.db.jobdb.tables.JobPositionExt.JOB_POSITION_EXT.PID.eq((int) application.getPosition_id()))
                .fetchAnyInto(JobPositionExt.class);
        if (positionExt != null) {
            map.put("job_position_ext", positionExt);
        }
        System.err.println(application.getId() + ":job_position_ext-----------:" + (System.currentTimeMillis() - start));

        //other from jobdb.job_resume_other # custom résumé fields in JSON format
        JobResumeOther resumeOther = create
                .select()
                .from(com.moseeker.baseorm.db.jobdb.tables.JobResumeOther.JOB_RESUME_OTHER)
                .where(com.moseeker.baseorm.db.jobdb.tables.JobResumeOther.JOB_RESUME_OTHER.APP_ID.eq(UInteger.valueOf(application.getId())))
                .fetchAnyInto(JobResumeOther.class);
        if (resumeOther != null) {
            map.put("job_resume_other", resumeOther);
        }
        System.err.println(application.getId() + ":job_resume_other-----------:" + (System.currentTimeMillis() - start));

        //all from userdb.user_user
        User user = create
                .select()
                .from(UserUser.USER_USER)
                .where(UserUser.USER_USER.ID.eq(UInteger.valueOf(application.getApplier_id())))
                .fetchAnyInto(User.class);
        if (user != null) {
            map.put("user_user", user);
        }
        System.err.println(application.getId() + ":user_user-----------:" + (System.currentTimeMillis() - start));

        //all from profiledb.user_thirdparty_user # ATS login
        ThirdPartyUser thirdPartyUser = create
                .select()
                .from(UserThirdpartyUser.USER_THIRDPARTY_USER)
                .where(UserThirdpartyUser.USER_THIRDPARTY_USER.USER_ID.eq((int) application.getApplier_id()))
                .fetchAnyInto(ThirdPartyUser.class);
        if (thirdPartyUser != null) {
            map.put("user_thirdparty_user", thirdPartyUser);
        }
        System.err.println(application.getId() + ":user_thirdparty_user-----------:" + (System.currentTimeMillis() - start));

        //all from profiledb.profile_profile
        Profile profile = create
                .select()
                .from(ProfileProfile.PROFILE_PROFILE)
                .where(ProfileProfile.PROFILE_PROFILE.USER_ID.eq(UInteger.valueOf(application.getApplier_id())))
                .fetchAnyInto(Profile.class);
        if (profile != null) {
            map.put("profile_profile", profile);
            System.err.println(application.getId() + ":profile_profile-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_attachment
            Attachment profile_attachment = create
                    .select()
                    .from(ProfileAttachment.PROFILE_ATTACHMENT)
                    .where(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .fetchAnyInto(Attachment.class);
            if (profile_attachment != null) {
                map.put("profile_attachment", profile_attachment);
            }
            System.err.println(application.getId() + ":profile_attachment-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_award
            List<Awards> profile_award = create
                    .select()
                    .from(ProfileAwards.PROFILE_AWARDS)
                    .where(ProfileAwards.PROFILE_AWARDS.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .fetchInto(Awards.class);
            if (profile_award != null) {
                map.put("profile_award", profile_award);
            }
            System.err.println(application.getId() + ":profile_award-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_credentials ORDER most recent first by start date
            List<Credentials> profile_credentials = create
                    .select()
                    .from(ProfileCredentials.PROFILE_CREDENTIALS)
                    .where(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .orderBy(ProfileCredentials.PROFILE_CREDENTIALS.GET_DATE.desc())
                    .fetchInto(Credentials.class);
            if (profile_credentials != null) {
                map.put("profile_credentials", profile_credentials);
            }
            System.err.println(application.getId() + ":profile_credentials-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_educations ORDER most recent first by start date
            List<Education> profile_educations = create
                    .select()
                    .from(ProfileEducation.PROFILE_EDUCATION)
                    .where(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .orderBy(ProfileEducation.PROFILE_EDUCATION.START.desc())
                    .fetchInto(Education.class);
            if (profile_educations != null) {
                map.put("profile_educations", profile_educations);
            }
            System.err.println(application.getId() + ":profile_educations-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_import
            ProfileImport profile_import = create
                    .select()
                    .from(com.moseeker.baseorm.db.profiledb.tables.ProfileImport.PROFILE_IMPORT)
                    .where(com.moseeker.baseorm.db.profiledb.tables.ProfileImport.PROFILE_IMPORT.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .fetchAnyInto(ProfileImport.class);
            if (profile_import != null) {
                map.put("profile_import", profile_import);
            }
            System.err.println(application.getId() + ":profile_import-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_intention
            Intention profile_intention = create
                    .select()
                    .from(ProfileIntention.PROFILE_INTENTION)
                    .where(ProfileIntention.PROFILE_INTENTION.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .fetchAnyInto(Intention.class);
            if (profile_intention != null) {
                map.put("profile_intention", profile_intention);
            }
            System.err.println(application.getId() + ":profile_intention-----------:" + (System.currentTimeMillis() - start));

            if (profile_intention != null) {
                //all from profiledb.profile_intention_city
                IntentionCity profile_intention_city = create
                        .select()
                        .from(ProfileIntentionCity.PROFILE_INTENTION_CITY)
                        .where(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.eq(UInteger.valueOf(profile_intention.getId())))
                        .fetchAnyInto(IntentionCity.class);
                if (profile_intention_city != null) {
                    map.put("profile_intention_city", profile_intention_city);
                }
                System.err.println(application.getId() + ":profile_intention_city-----------:" + (System.currentTimeMillis() - start));

                //all from profiledb.profile_intention_industry
                IntentionIndustry profile_intention_industry = create
                        .select()
                        .from(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY)
                        .where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.eq(UInteger.valueOf(profile_intention.getId())))
                        .fetchAnyInto(IntentionIndustry.class);
                if (profile_intention_industry != null) {
                    map.put("profile_intention_industry", profile_intention_industry);
                }
                System.err.println(application.getId() + ":profile_intention_industry-----------:" + (System.currentTimeMillis() - start));

                //all from profiledb.profile_intention_position
                IntentionPosition profile_intention_position = create
                        .select()
                        .from(ProfileIntentionPosition.PROFILE_INTENTION_POSITION)
                        .where(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.eq(UInteger.valueOf(profile_intention.getId())))
                        .fetchAnyInto(IntentionPosition.class);
                if (profile_intention_position != null) {
                    map.put("profile_intention_position", profile_intention_position);
                }
                System.err.println(application.getId() + ":profile_intention_position-----------:" + (System.currentTimeMillis() - start));
            }

            //all from profiledb.profile_language
            List<Language> profile_language = create
                    .select()
                    .from(ProfileLanguage.PROFILE_LANGUAGE)
                    .where(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .fetchInto(Language.class);
            if (profile_language != null) {
                map.put("profile_language", profile_language);
            }
            System.err.println(application.getId() + ":profile_language-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_other
            ProfileOther profile_other = create
                    .select()
                    .from(com.moseeker.baseorm.db.profiledb.tables.ProfileOther.PROFILE_OTHER)
                    .where(com.moseeker.baseorm.db.profiledb.tables.ProfileOther.PROFILE_OTHER.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .fetchAnyInto(ProfileOther.class);
            if (profile_other != null) {
                map.put("profile_other", profile_other);
            }
            System.err.println(application.getId() + ":profile_other-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_projectexp ORDER most recent first by start date
            List<ProjectExp> profile_projectexp = create
                    .select()
                    .from(ProfileProjectexp.PROFILE_PROJECTEXP)
                    .where(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .fetchInto(ProjectExp.class);
            if (profile_projectexp != null) {
                map.put("profile_projectexp", profile_projectexp);
            }
            System.err.println(application.getId() + ":profile_projectexp-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_skills
            List<Skill> profile_skills = create
                    .select()
                    .from(ProfileSkill.PROFILE_SKILL)
                    .where(ProfileSkill.PROFILE_SKILL.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .fetchInto(Skill.class);
            if (profile_skills != null) {
                map.put("profile_skills", profile_skills);
            }
            System.err.println(application.getId() + ":profile_skills-----------:" + (System.currentTimeMillis() - start));

            //all from profiledb.profile_workexp
            List<WorkExp> profile_workexp = create
                    .select()
                    .from(ProfileWorkexp.PROFILE_WORKEXP)
                    .where(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID.eq(UInteger.valueOf(profile.getId())))
                    .fetchInto(WorkExp.class);
            if (profile_workexp != null) {
                map.put("profile_workexp", profile_workexp);
            }
            System.err.println(application.getId() + ":profile_workexp-----------:" + (System.currentTimeMillis() - start));

        }

        if (recommender) {
            //user_employee.disable=0, activation=0, status=0
            UserEmployeeStruct employee = create
                    .select()
                    .from(UserEmployee.USER_EMPLOYEE)
                    .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq((int) application.getRecommender_user_id()))
                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0))
                    .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                    .and(UserEmployee.USER_EMPLOYEE.STATUS.eq(0))
                    .fetchAnyInto(UserEmployeeStruct.class);

            if (employee != null) {

                User recommenderUser = create
                        .select()
                        .from(UserUser.USER_USER)
                        .where(UserUser.USER_USER.ID.eq(UInteger.valueOf(application.getRecommender_user_id())))
                        .fetchAnyInto(User.class);
                Map<String, Object> recommenderMap;
                if (recommenderUser != null) {
                    recommenderMap = BeanUtils.object2Map(recommenderUser);
                } else {
                    recommenderMap = new HashMap<>();
                }
                recommenderMap.put("employeeid", employee.getEmployeeid());
                recommenderMap.put("custom_field", employee.getCustom_field());
                map.put("recommender", recommenderMap);
                System.err.println(application.getId() + ":recommender-----------:" + (System.currentTimeMillis() - start));
            }
        }

        return map;

    }

    public Response getResourceByApplication(int companyId, int sourceId, int atsStatus, boolean recommender) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            long start = System.currentTimeMillis();
            System.err.println("start-----------:" + start);
            List<Map<String, Object>> datas = create
                    .select()
                    .from(JobApplication.JOB_APPLICATION)
                    .where(JobApplication.JOB_APPLICATION.COMPANY_ID.eq(UInteger.valueOf(companyId)))
                    .and(JobApplication.JOB_APPLICATION.SOURCE_ID.eq(UInteger.valueOf(sourceId)))
                    .and(JobApplication.JOB_APPLICATION.ATS_STATUS.eq(atsStatus))
                    .fetchInto(com.moseeker.thrift.gen.application.struct.JobApplication.class)
                    .stream()
                    .map(application -> getRelatedDataByJobApplication(create, application, recommender))
                    .collect(Collectors.toList());
            System.err.println("end-----------:" + (System.currentTimeMillis() - start));
            return ResponseUtils.success(datas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }

}
