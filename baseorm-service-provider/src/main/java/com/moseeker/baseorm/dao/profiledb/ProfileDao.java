package com.moseeker.baseorm.dao.profiledb;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.profiledb.tables.*;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserThirdpartyUser;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.position.struct.JobPositionExt;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.profile.struct.*;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;
import com.moseeker.thrift.gen.profile.struct.ProfileOther;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by moseeker on 2017/3/13.
 */
@Service
public class ProfileDao extends JooqCrudImpl<ProfileProfileDO, ProfileProfileRecord> {

    public ProfileDao(TableImpl<ProfileProfileRecord> table, Class<ProfileProfileDO> profileProfileDOClass) {
        super(table, profileProfileDOClass);
    }

    private String getDownloadUrlByUserId(String downloadApi, String password, int userid) {
        String url = null;
        if (StringUtils.isNotNullOrEmpty(downloadApi)) {
            Map<String, Object> params = new HashMap<String, Object>() {{
                put("user_id", userid);
                put("password", password);
            }};
            try {
                String content = HttpClient.sendPost(downloadApi, JSON.toJSONString(params));
                Map<String, Object> mp = JsonToMap.parseJSON2Map(content);
                Object link = mp.get("downloadlink");
                if (link != null) {
                    url = link.toString();
                }
            } catch (ConnectException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    long last = 0;

    private void printQueryTime(String tag) {
        long current = System.currentTimeMillis();
        logger.info(tag + (current - last));
        last = current;
    }

    private boolean showEmptyKey = false;

    private void buildMap(Map map, String key, Object object) {
        if (showEmptyKey) {
            map.put(key, object);
        } else if (object != null) {
            if (object instanceof Map) {
                if (((Map) object).size() > 0) {
                    map.put(key, object);
                }
            } else if (object instanceof Collection) {
                if (((Collection) object).size() > 0) {
                    map.put(key, object);
                }
            } else {
                map.put(key, object);
            }
        }
    }

    public Map<String, Object> getRelatedDataByJobApplication(DSLContext create, com.moseeker.thrift.gen.application.struct.JobApplication application, String downloadApi, String password, boolean recommender, boolean dl_url_required) {

        last = System.currentTimeMillis();

        Map<String, Object> map = new HashMap<>();
        //all from jobdb.job_application
        buildMap(map, "job_application", application);

        if (application.getPosition_id() != 0) {
            //job_number and title from jobdb.job_position
            Position position = create
                    .select(JobPosition.JOB_POSITION.JOBNUMBER, JobPosition.JOB_POSITION.TITLE)
                    .from(JobPosition.JOB_POSITION)
                    .where(JobPosition.JOB_POSITION.ID.eq((int) application.getPosition_id()))
                    .fetchAnyInto(Position.class);
            buildMap(map, "job_position", position);
            printQueryTime(application.getId() + ":job_position-----------:");

            //extra from jobdb.job_position_ext # custom job fields in JSON format
            JobPositionExt positionExt = create
                    .select()
                    .from(com.moseeker.baseorm.db.jobdb.tables.JobPositionExt.JOB_POSITION_EXT)
                    .where(com.moseeker.baseorm.db.jobdb.tables.JobPositionExt.JOB_POSITION_EXT.PID.eq((int) application.getPosition_id()))
                    .fetchAnyInto(JobPositionExt.class);
            buildMap(map, "job_position_ext", positionExt);
            printQueryTime(application.getId() + ":job_position_ext-----------:");
        }

        //other from jobdb.job_resume_other # custom résumé fields in JSON format
        JobResumeOther resumeOther = create
                .select()
                .from(com.moseeker.baseorm.db.jobdb.tables.JobResumeOther.JOB_RESUME_OTHER)
                .where(com.moseeker.baseorm.db.jobdb.tables.JobResumeOther.JOB_RESUME_OTHER.APP_ID.eq((int) application.getId()))
                .fetchAnyInto(JobResumeOther.class);
        buildMap(map, "job_resume_other", resumeOther);
        printQueryTime(application.getId() + ":job_resume_other-----------:");

        if (application.getApplier_id() != 0) {
            //all from userdb.user_user
            User user = create
                    .select()
                    .from(UserUser.USER_USER)
                    .where(UserUser.USER_USER.ID.eq((int) application.getApplier_id()))
                    .fetchAnyInto(User.class);
            buildMap(map, "user_user", user);
            printQueryTime(application.getId() + ":user_user-----------:");

            //all from profiledb.user_thirdparty_user # ATS login
            ThirdPartyUser thirdPartyUser = create
                    .select()
                    .from(UserThirdpartyUser.USER_THIRDPARTY_USER)
                    .where(UserThirdpartyUser.USER_THIRDPARTY_USER.USER_ID.eq((int) application.getApplier_id()))
                    .fetchAnyInto(ThirdPartyUser.class);
            buildMap(map, "user_thirdparty_user", thirdPartyUser);
            printQueryTime(application.getId() + ":user_thirdparty_user-----------:");

            //all from profiledb.profile_profile
            Profile profile = create
                    .select()
                    .from(ProfileProfile.PROFILE_PROFILE)
                    .where(ProfileProfile.PROFILE_PROFILE.USER_ID.eq((int)(application.getApplier_id())))
                    .fetchAnyInto(Profile.class);
            buildMap(map, "profile_profile", profile);
            printQueryTime(application.getId() + ":profile_profile-----------:");
            if (profile != null) {
                //all from profiledb.profile_attachment
                Attachment profile_attachment = create
                        .select()
                        .from(ProfileAttachment.PROFILE_ATTACHMENT)
                        .where(ProfileAttachment.PROFILE_ATTACHMENT.PROFILE_ID.eq((int)(profile.getId())))
                        .fetchAnyInto(Attachment.class);
                buildMap(map, "profile_attachment", profile_attachment);
                printQueryTime(application.getId() + ":profile_attachment-----------:");

                //all from profiledb.profile_award
                List<Awards> profile_award = create
                        .select()
                        .from(ProfileAwards.PROFILE_AWARDS)
                        .where(ProfileAwards.PROFILE_AWARDS.PROFILE_ID.eq(profile.getId()))
                        .fetchInto(Awards.class);
                buildMap(map, "profile_award", profile_award);
                printQueryTime(application.getId() + ":profile_award-----------:");

                //all from profiledb.profile_credentials ORDER most recent first by start date
                List<Credentials> profile_credentials = create
                        .select()
                        .from(ProfileCredentials.PROFILE_CREDENTIALS)
                        .where(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID.eq(profile.getId()))
                        .orderBy(ProfileCredentials.PROFILE_CREDENTIALS.GET_DATE.desc())
                        .fetchInto(Credentials.class);
                buildMap(map, "profile_credentials", profile_credentials);
                printQueryTime(application.getId() + ":profile_credentials-----------:");

                //all from profiledb.profile_educations ORDER most recent first by start date
                List<Education> profile_educations = create
                        .select()
                        .from(ProfileEducation.PROFILE_EDUCATION)
                        .where(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID.eq(profile.getId()))
                        .orderBy(ProfileEducation.PROFILE_EDUCATION.START.desc())
                        .fetchInto(Education.class);
                buildMap(map, "profile_educations", profile_educations);
                printQueryTime(application.getId() + ":profile_educations-----------:");

                //all from profiledb.profile_import
                ProfileImport profile_import = create
                        .select()
                        .from(com.moseeker.baseorm.db.profiledb.tables.ProfileImport.PROFILE_IMPORT)
                        .where(com.moseeker.baseorm.db.profiledb.tables.ProfileImport.PROFILE_IMPORT.PROFILE_ID.eq(profile.getId()))
                        .fetchAnyInto(ProfileImport.class);
                buildMap(map, "profile_import", profile_import);
                printQueryTime(application.getId() + ":profile_import-----------:");

                //all from profiledb.profile_intention
                Intention profile_intention = create
                        .select()
                        .from(ProfileIntention.PROFILE_INTENTION)
                        .where(ProfileIntention.PROFILE_INTENTION.PROFILE_ID.eq(profile.getId()))
                        .fetchAnyInto(Intention.class);
                buildMap(map, "profile_intention", profile_intention);
                printQueryTime(application.getId() + ":profile_intention-----------:");

                if (profile_intention != null) {
                    //all from profiledb.profile_intention_city
                    IntentionCity profile_intention_city = create
                            .select()
                            .from(ProfileIntentionCity.PROFILE_INTENTION_CITY)
                            .where(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.eq(profile_intention.getId()))
                            .fetchAnyInto(IntentionCity.class);
                    buildMap(map, "profile_intention_city", profile_intention_city);
                    printQueryTime(application.getId() + ":profile_intention_city-----------:");

                    //all from profiledb.profile_intention_industry
                    IntentionIndustry profile_intention_industry = create
                            .select()
                            .from(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY)
                            .where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.eq(profile_intention.getId()))
                            .fetchAnyInto(IntentionIndustry.class);
                    buildMap(map, "profile_intention_industry", profile_intention_industry);
                    printQueryTime(application.getId() + ":profile_intention_industry-----------:");

                    //all from profiledb.profile_intention_position
                    IntentionPosition profile_intention_position = create
                            .select()
                            .from(ProfileIntentionPosition.PROFILE_INTENTION_POSITION)
                            .where(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.eq(profile_intention.getId()))
                            .fetchAnyInto(IntentionPosition.class);
                    buildMap(map, "profile_intention_position", profile_intention_position);
                    printQueryTime(application.getId() + ":profile_intention_position-----------:");
                }

                //all from profiledb.profile_language
                List<Language> profile_language = create
                        .select()
                        .from(ProfileLanguage.PROFILE_LANGUAGE)
                        .where(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID.eq(profile.getId()))
                        .fetchInto(Language.class);
                buildMap(map, "profile_language", profile_language);
                printQueryTime(application.getId() + ":profile_language-----------:");

                //all from profiledb.profile_other
                ProfileOther profile_other = create
                        .select()
                        .from(com.moseeker.baseorm.db.profiledb.tables.ProfileOther.PROFILE_OTHER)
                        .where(com.moseeker.baseorm.db.profiledb.tables.ProfileOther.PROFILE_OTHER.PROFILE_ID.eq(profile.getId()))
                        .fetchAnyInto(ProfileOther.class);
                buildMap(map, "profile_other", profile_other);
                printQueryTime(application.getId() + ":profile_other-----------:");

                //all from profiledb.profile_projectexp ORDER most recent first by start date
                List<ProjectExp> profile_projectexp = create
                        .select()
                        .from(ProfileProjectexp.PROFILE_PROJECTEXP)
                        .where(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID.eq(profile.getId()))
                        .fetchInto(ProjectExp.class);
                buildMap(map, "profile_projectexp", profile_projectexp);
                printQueryTime(application.getId() + ":profile_projectexp-----------:");

                //all from profiledb.profile_skills
                List<Skill> profile_skills = create
                        .select()
                        .from(ProfileSkill.PROFILE_SKILL)
                        .where(ProfileSkill.PROFILE_SKILL.PROFILE_ID.eq(profile.getId()))
                        .fetchInto(Skill.class);
                buildMap(map, "profile_skills", profile_skills);
                printQueryTime(application.getId() + ":profile_skills-----------:");

                //all from profiledb.profile_workexp
                List<WorkExp> profile_workexp = create
                        .select()
                        .from(ProfileWorkexp.PROFILE_WORKEXP)
                        .where(ProfileWorkexp.PROFILE_WORKEXP.PROFILE_ID.eq(profile.getId()))
                        .fetchInto(WorkExp.class);
                buildMap(map, "profile_workexp", profile_workexp);
                printQueryTime(application.getId() + ":profile_workexp-----------:");

            }

            if (dl_url_required) {
                String url = getDownloadUrlByUserId(downloadApi, password, (int) application.getApplier_id());
                buildMap(map, "download_url", url == null ? "" : url);
            }
        }

        if (recommender && application.getRecommender_user_id() != 0) {
            //user_employee.disable=0, activation=0, status=0
            UserEmployeeStruct employee = create
                    .select()
                    .from(UserEmployee.USER_EMPLOYEE)
                    .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq((int) application.getRecommender_user_id()))
                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0))
                    .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0))
                    .and(UserEmployee.USER_EMPLOYEE.STATUS.eq(0))
                    .fetchAnyInto(UserEmployeeStruct.class);


            User recommenderUser = create
                    .select()
                    .from(UserUser.USER_USER)
                    .where(UserUser.USER_USER.ID.eq((int) application.getRecommender_user_id()))
                    .fetchAnyInto(User.class);
            Map<String, Object> recommenderMap;
            if (recommenderUser != null) {
                recommenderMap = BeanUtils.object2Map(recommenderUser);
            } else {
                recommenderMap = new HashMap<>();
            }
            if(employee != null) {
                recommenderMap.put("employeeid", employee.getEmployeeid());
                recommenderMap.put("custom_field", employee.getCustom_field());
            }
            buildMap(map, "recommender", recommenderMap);
            printQueryTime(application.getId() + ":recommender-----------:");
        }

        return map;

    }

    public Response getResourceByApplication(String downloadApi, String password, int companyId, int sourceId, int atsStatus, boolean recommender, boolean dl_url_required) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Set<Map<String, Object>> datas = create
                    .select()
                    .from(JobApplication.JOB_APPLICATION)
                    .where(JobApplication.JOB_APPLICATION.COMPANY_ID.eq(companyId))
                    .and(JobApplication.JOB_APPLICATION.SOURCE_ID.eq(sourceId))
                    .and(JobApplication.JOB_APPLICATION.ATS_STATUS.eq(atsStatus))
                    .and(JobApplication.JOB_APPLICATION.EMAIL_STATUS.eq(0))
                    .fetchInto(com.moseeker.thrift.gen.application.struct.JobApplication.class)
                    .stream()
                    .map(application -> getRelatedDataByJobApplication(create, application, downloadApi, password, recommender, dl_url_required))
                    .collect(Collectors.toSet());
            return ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(datas));
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