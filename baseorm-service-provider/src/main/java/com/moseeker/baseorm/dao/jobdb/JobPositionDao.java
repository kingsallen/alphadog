package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.hrdb.tables.*;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.analytics.tables.StJobSimilarity;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.db.userdb.tables.UserHrAccount;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.PositionDetails;

import org.jooq.*;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobPositionDao extends StructDaoImpl<JobPositionDO, JobPositionRecord, JobPosition> {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobPosition.JOB_POSITION;
    }


    public List<JobPositionDO> getPositions(CommonQuery query) {
        List<JobPositionDO> positions = new ArrayList<>();
        try {
            List<JobPositionRecord> records = getResources(query);
            if (records != null && records.size() > 0) {
                positions = records.stream().filter(record -> record != null)
                        .map(record -> BeanUtils.DBToStruct(JobPositionDO.class, record))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //do nothing
        }

        return positions;
    }

    public Position getPositionWithCityCode(CommonQuery query) {

        logger.info("JobPositionDao getPositionWithCityCode");

        Position position = new Position();
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

            JobPositionRecord record = this.getResource(query);
            if (record != null) {
                position = record.into(position);
                Map<Integer, String> citiesParam = new HashMap<Integer, String>();
                List<Integer> cityCodes = new ArrayList<>();
                Result<JobPositionCityRecord> cities = create.selectFrom(JobPositionCity.JOB_POSITION_CITY)
                        .where(JobPositionCity.JOB_POSITION_CITY.PID.equal(record.getId())).fetch();
                if (cities != null && cities.size() > 0) {
                    cities.forEach(city -> {
                        logger.info("code:{}", city.getCode());
                        if (city.getCode() != null) {
                            citiesParam.put(city.getCode(), null);
                            cityCodes.add(city.getCode());
                        }
                    });
                    logger.info("cityCodes:{}", cityCodes);
                    Result<DictCityRecord> dictDicties = create.selectFrom(DictCity.DICT_CITY).where(DictCity.DICT_CITY.CODE.in(cityCodes)).fetch();
                    if (dictDicties != null && dictDicties.size() > 0) {
                        dictDicties.forEach(dictCity -> {
                            citiesParam.entrySet().forEach(entry -> {
                                if (entry.getKey().intValue() == dictCity.getCode().intValue()) {
                                    logger.info("cityName:{}", dictCity.getName());
                                    entry.setValue(dictCity.getName());
                                }
                            });
                        });
                    }
                }

                position.setCompany_id(record.getCompanyId().intValue());
                position.setCities(citiesParam);
                citiesParam.forEach((cityCode, cityName) -> {
                    logger.info("cityCode:{}, cityName:{}", cityCode, cityName);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            // do nothing
        }
        return position;
    }

    /**
     * 通过PositionId查询职位详情
     * 包含职位的团队信息
     */
    public PositionDetails positionDetails(Integer positionId) {
        PositionDetails positionDetails = new PositionDetails();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            JobPosition jp = JobPosition.JOB_POSITION.as("jp");
            HrTeam ht = HrTeam.HR_TEAM.as("ht");
            Record record = create.select(
                    jp.ID.as("id"), jp.JOBNUMBER.as("jobnumber"), jp.COMPANY_ID.as("companyId"), jp.TITLE.as("title"), jp.CITY.as("city"), jp.DEPARTMENT.as("department"),
                    jp.L_JOBID.as("lJobid"), jp.PUBLISH_DATE.as("publishDate"), jp.STOP_DATE.as("stopDate"), jp.ACCOUNTABILITIES.as("accountabilities"), jp.EXPERIENCE.as("experience"), jp.REQUIREMENT.as("requirement"),
                    jp.SALARY.as("salary"), jp.LANGUAGE.as("language"), jp.JOB_GRADE.as("jobGrade"), jp.STATUS.as("status"), jp.VISITNUM.as("visitnum"), jp.LASTVISIT.as("lastvisit"),
                    jp.SOURCE_ID.as("source_id"), jp.UPDATE_TIME.as("updateTime"), jp.BUSINESS_GROUP.as("businessGroup"), jp.EMPLOYMENT_TYPE.as("employmentType"), jp.HR_EMAIL.as("hrEmail"), jp.BENEFITS.as("benefits"),
                    jp.DEGREE.as("degree"), jp.FEATURE.as("feature"), jp.EMAIL_NOTICE.as("emailNotice"), jp.CANDIDATE_SOURCE.as("candidateSource"), jp.OCCUPATION.as("occupation"), jp.IS_RECOM.as("isRecom"),
                    jp.INDUSTRY.as("industry"), jp.HONGBAO_CONFIG_ID.as("hongbaoConfigId"), jp.HONGBAO_CONFIG_RECOM_ID.as("hongbaoConfigRecomId"), jp.HONGBAO_CONFIG_APP_ID.as("hongbaoConfigAppId"), jp.EMAIL_RESUME_CONF.as("emailResumeConf"),
                    jp.L_POSTINGTARGETID.as("lPostingTargetId"),
                    jp.PRIORITY.as("priority"), jp.SHARE_TPL_ID.as("shareTplId"), jp.DISTRICT.as("district"), jp.COUNT.as("count"), jp.SALARY_TOP.as("salaryTop"), jp.SALARY_BOTTOM.as("salaryBottom"),
                    jp.EXPERIENCE_ABOVE.as("experienceAbove"), jp.DEGREE_ABOVE.as("degreeAbove"), jp.MANAGEMENT_EXPERIENCE.as("managementExperience"), jp.GENDER.as("gender"), jp.PUBLISHER.as("publisher"), jp.APP_CV_CONFIG_ID.as("appCvConfigId"),
                    jp.SOURCE.as("source"), jp.HB_STATUS.as("hbStatus"), jp.CHILD_COMPANY_ID.as("childCompanyId"), jp.AGE.as("age"), jp.MAJOR_REQUIRED.as("majorRequired"), jp.WORK_ADDRESS.as("workAddress"),
                    jp.KEYWORD.as("keyword"), jp.REPORTING_TO.as("reportingTo"), jp.IS_HIRING.as("isHiring"), jp.UNDERLINGS.as("underlings"), jp.LANGUAGE_REQUIRED.as("languageRequired"), jp.TARGET_INDUSTRY.as("targetIndustry"),
                    jp.CURRENT_STATUS.as("currentStatus"), jp.POSITION_CODE.as("positionCode"), ht.ID.as("teamId"), ht.NAME.as("teamName"), ht.DESCRIPTION.as("teamDescription")
            ).from(jp).leftJoin(ht).on(jp.TEAM_ID.equal(ht.ID))
                    .where(jp.ID.equal(positionId)).fetchAny();
            if (record != null) {
                Map recordMap = record.intoMap();
                positionDetails = BeanUtils.MapToRecord(recordMap, PositionDetails.class);
                Record recordRes = create.select(HrResource.HR_RESOURCE.RES_URL).from(HrCmsPages.HR_CMS_PAGES).join(HrCmsModule.HR_CMS_MODULE).on(HrCmsPages.HR_CMS_PAGES.ID.equal(HrCmsModule.HR_CMS_MODULE.PAGE_ID)).and(HrCmsModule.HR_CMS_MODULE.DISABLE.equal(0)).
                        join(HrCmsMedia.HR_CMS_MEDIA).on(HrCmsModule.HR_CMS_MODULE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.MODULE_ID)).and(HrCmsMedia.HR_CMS_MEDIA.DISABLE.equal(0)).join(HrResource.HR_RESOURCE).on(HrResource.HR_RESOURCE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.RES_ID)).
                        and(HrResource.HR_RESOURCE.DISABLE.equal(0)).and(HrResource.HR_RESOURCE.RES_TYPE.equal(0)).and(HrResource.HR_RESOURCE.RES_URL.isNotNull()).join(JobPosition.JOB_POSITION).on(JobPosition.JOB_POSITION.TEAM_ID.equal(HrCmsPages.HR_CMS_PAGES.CONFIG_ID)).
                        and(JobPosition.JOB_POSITION.ID.equal(positionDetails.getId())).where(HrCmsPages.HR_CMS_PAGES.DISABLE.equal(0)).and(HrCmsPages.HR_CMS_PAGES.TYPE.equal(3)).fetchAny();
                if (recordRes != null) {
                    positionDetails.setResUrl((String) recordRes.get("res_url"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return positionDetails;
    }


    /**
     * 通过CompanyId查询热招职位列表
     * 包含职位的团队信息
     */
    public List<PositionDetails> hotPositionDetailsList(CommonQuery commonQuery) {
        List<PositionDetails> positionDetails = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            JobPosition jp = JobPosition.JOB_POSITION.as("jp");
            HrTeam ht = HrTeam.HR_TEAM.as("ht");
            SelectOnConditionStep<Record> record = create.select(
                    jp.ID.as("id"), jp.JOBNUMBER.as("jobnumber"), jp.COMPANY_ID.as("companyId"), jp.TITLE.as("title"), jp.CITY.as("city"), jp.DEPARTMENT.as("department"),
                    jp.L_JOBID.as("lJobid"), jp.PUBLISH_DATE.as("publishDate"), jp.STOP_DATE.as("stopDate"), jp.ACCOUNTABILITIES.as("accountabilities"), jp.EXPERIENCE.as("experience"), jp.REQUIREMENT.as("requirement"),
                    jp.SALARY.as("salary"), jp.LANGUAGE.as("language"), jp.JOB_GRADE.as("jobGrade"), jp.STATUS.as("status"), jp.VISITNUM.as("visitnum"), jp.LASTVISIT.as("lastvisit"),
                    jp.SOURCE_ID.as("source_id"), jp.UPDATE_TIME.as("updateTime"), jp.BUSINESS_GROUP.as("businessGroup"), jp.EMPLOYMENT_TYPE.as("employmentType"), jp.HR_EMAIL.as("hrEmail"), jp.BENEFITS.as("benefits"),
                    jp.DEGREE.as("degree"), jp.FEATURE.as("feature"), jp.EMAIL_NOTICE.as("emailNotice"), jp.CANDIDATE_SOURCE.as("candidateSource"), jp.OCCUPATION.as("occupation"), jp.IS_RECOM.as("isRecom"),
                    jp.INDUSTRY.as("industry"), jp.HONGBAO_CONFIG_ID.as("hongbaoConfigId"), jp.HONGBAO_CONFIG_RECOM_ID.as("hongbaoConfigRecomId"), jp.HONGBAO_CONFIG_APP_ID.as("hongbaoConfigAppId"), jp.EMAIL_RESUME_CONF.as("emailResumeConf"),
                    jp.L_POSTINGTARGETID.as("lPostingTargetId"),
                    jp.PRIORITY.as("priority"), jp.SHARE_TPL_ID.as("shareTplId"), jp.DISTRICT.as("district"), jp.COUNT.as("count"), jp.SALARY_TOP.as("salaryTop"), jp.SALARY_BOTTOM.as("salaryBottom"),
                    jp.EXPERIENCE_ABOVE.as("experienceAbove"), jp.DEGREE_ABOVE.as("degreeAbove"), jp.MANAGEMENT_EXPERIENCE.as("managementExperience"), jp.GENDER.as("gender"), jp.PUBLISHER.as("publisher"), jp.APP_CV_CONFIG_ID.as("appCvConfigId"),
                    jp.SOURCE.as("source"), jp.HB_STATUS.as("hbStatus"), jp.CHILD_COMPANY_ID.as("childCompanyId"), jp.AGE.as("age"), jp.MAJOR_REQUIRED.as("majorRequired"), jp.WORK_ADDRESS.as("workAddress"),
                    jp.KEYWORD.as("keyword"), jp.REPORTING_TO.as("reportingTo"), jp.IS_HIRING.as("isHiring"), jp.UNDERLINGS.as("underlings"), jp.LANGUAGE_REQUIRED.as("languageRequired"), jp.TARGET_INDUSTRY.as("targetIndustry"),
                    jp.CURRENT_STATUS.as("currentStatus"), jp.POSITION_CODE.as("positionCode"), ht.ID.as("teamId"), ht.NAME.as("teamName"), ht.DESCRIPTION.as("teamDescription")
            ).from(jp).leftJoin(ht).on(jp.TEAM_ID.equal(ht.ID));

            Map hashMap = commonQuery.getEqualFilter();
            // 通过CompanyId查询职位列表
            if (hashMap.get("company_id") != null) {
                List<SortField<?>> fields = new ArrayList<>(2);
                fields.add(jp.UPDATE_TIME.desc());
                fields.add(jp.IS_RECOM.asc());
                record.where(jp.COMPANY_ID.equal(UInteger.valueOf(commonQuery.getEqualFilter().get("company_id")))).orderBy(fields);
            }
            // 分页
            int page = 1;
            int per_page = 20;
            if (commonQuery != null && commonQuery.getPage() > 0) {
                page = commonQuery.getPage();
            }
            per_page = commonQuery.getPer_page() > 0 ? commonQuery.getPer_page() : per_page;
            record.limit((page - 1) * per_page, per_page);
            positionDetails = record.fetchInto(PositionDetails.class);

            if (positionDetails != null && positionDetails.size() > 0) {
                // 查询职位图片信息
                SelectOnConditionStep<Record4<String, UInteger, Integer, Integer>> recordRes = create.select(HrResource.HR_RESOURCE.RES_URL, JobPosition.JOB_POSITION.COMPANY_ID, JobPosition.JOB_POSITION.TEAM_ID, JobPosition.JOB_POSITION.ID).from(HrCmsPages.HR_CMS_PAGES).join(HrCmsModule.HR_CMS_MODULE).on(HrCmsPages.HR_CMS_PAGES.ID.equal(HrCmsModule.HR_CMS_MODULE.PAGE_ID)).and(HrCmsModule.HR_CMS_MODULE.DISABLE.equal(0)).
                        join(HrCmsMedia.HR_CMS_MEDIA).on(HrCmsModule.HR_CMS_MODULE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.MODULE_ID)).and(HrCmsMedia.HR_CMS_MEDIA.DISABLE.equal(0)).join(HrResource.HR_RESOURCE).on(HrResource.HR_RESOURCE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.RES_ID)).
                        and(HrResource.HR_RESOURCE.DISABLE.equal(0)).and(HrResource.HR_RESOURCE.RES_TYPE.equal(0)).and(HrResource.HR_RESOURCE.RES_URL.isNotNull()).join(JobPosition.JOB_POSITION).on(JobPosition.JOB_POSITION.TEAM_ID.equal(HrCmsPages.HR_CMS_PAGES.CONFIG_ID));
                // 通过CompanyId查询职位列表
                if (hashMap.get("company_id") != null) {
                    recordRes.where(JobPosition.JOB_POSITION.COMPANY_ID.equal(UInteger.valueOf(commonQuery.getEqualFilter().get("company_id"))));
                    Map resMap = recordRes.groupBy(JobPosition.JOB_POSITION.COMPANY_ID, JobPosition.JOB_POSITION.ID).fetch().intoMap(JobPosition.JOB_POSITION.ID);
                    if (resMap != null && resMap.size() > 0) {
                        for (PositionDetails positionDetailsTemp : positionDetails) {
                            Record4<String, UInteger, Integer, Integer> record4 = (Record4<String, UInteger, Integer, Integer>) resMap.get(Integer.valueOf(positionDetailsTemp.getId()));
                            if (record4 != null) {
                                positionDetailsTemp.setResUrl(String.valueOf(record4.getValue("res_url")));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return positionDetails;
    }


    /**
     * 通过PostionId查询相似职位列表
     * 包含职位的团队信息
     */
    public List<PositionDetails> similarityPositionDetailsList(CommonQuery commonQuery) {
        List<PositionDetails> positionDetails = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            Map hashMap = commonQuery.getEqualFilter();
            if (hashMap.get("pid") != null) {
                Integer pid = Integer.valueOf((String) hashMap.get("pid"));
                Result<? extends Record> positionAndCompanyRecords = create.select().from(com.moseeker.db.jobdb.tables.JobPosition.JOB_POSITION)
                        .join(HrCompany.HR_COMPANY).on(com.moseeker.db.jobdb.tables.JobPosition.JOB_POSITION.COMPANY_ID.equal(HrCompany.HR_COMPANY.ID))
                        .where(com.moseeker.db.jobdb.tables.JobPosition.JOB_POSITION.ID.equal(pid)).fetch();
                if (positionAndCompanyRecords.size() == 0) {
                    return positionDetails;
                }

                Record positionAndCompanyRecord = positionAndCompanyRecords.get(0);
                int company_id = ((UInteger) positionAndCompanyRecord.getValue("company_id")).intValue();
                int company_type = ((UByte) positionAndCompanyRecord.getValue("type")).intValue(); // 公司区分(其它:2,免费用户:1,企业用户:0)
                Result<? extends Record> recomResults;
                Condition condition = StJobSimilarity.ST_JOB_SIMILARITY.POS_ID.equal(pid);
                if (company_type == 0) {
                    UserHrAccountRecord account = create.selectFrom(UserHrAccount.USER_HR_ACCOUNT).where(UserHrAccount.USER_HR_ACCOUNT.ID.equal
                            (positionAndCompanyRecord.getValue(JobPosition.JOB_POSITION.PUBLISHER))).fetchOne();
                    //如果是子账号，则查询子账号下的推荐职位；如果是主帐号，则查询公司下的所有职位
                    if (account != null) {
                        if (account.getAccountType() != null && account.getAccountType().intValue() == 1) { // 0 超级账号；1：子账号; 2：普通账号
                            /* 检查是否是子公司的职位 */
                            int publisher = (Integer) positionAndCompanyRecord.getValue("publisher");
                            HrCompanyAccountRecord record = create.selectFrom(HrCompanyAccount.HR_COMPANY_ACCOUNT)
                                    .where(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.equal(publisher)).fetchOne();
                            if (record != null && record.getCompanyId() != null) {
                                if (company_id != record.getCompanyId()) {
                                    company_id = record.getCompanyId();
                                }
                            }
                            condition = condition.and(StJobSimilarity.ST_JOB_SIMILARITY.DEPARTMENT_ID.equal(company_id));
                        } else {
                            List<Integer> departmentIds = new ArrayList<>();
                            Result<Record1<UInteger>> result = create.select(HrCompany.HR_COMPANY.ID).
                                    from(HrCompany.HR_COMPANY).where(HrCompany.HR_COMPANY.PARENT_ID.equal(UInteger.valueOf(company_id))).fetch();
                            if (result != null && result.size() > 0) {
                                result.forEach(record -> {
                                    record.get(HrCompany.HR_COMPANY.ID);
                                    departmentIds.add(record.get(HrCompany.HR_COMPANY.ID).intValue());
                                });
                            }
                            departmentIds.add(company_id);
                            condition = condition.and(StJobSimilarity.ST_JOB_SIMILARITY.DEPARTMENT_ID.in(departmentIds));
                        }
                        recomResults = create.select().from(StJobSimilarity.ST_JOB_SIMILARITY).where(condition).fetch();
                        List<Integer> pids = new ArrayList<>();
                        for (Record recomResult : recomResults) {
                            Integer positionId = ((Integer) recomResult.getValue("recom_id")).intValue();
                            if (positionId != pid) {  // 去掉回当前的职位ID
                                pids.add(positionId);
                            }
                        }

                        if (pids != null && pids.size() > 0) {
                            JobPosition jp = JobPosition.JOB_POSITION.as("jp");
                            HrTeam ht = HrTeam.HR_TEAM.as("ht");
                            SelectConditionStep<Record> record = create.select(
                                    jp.ID.as("id"), jp.JOBNUMBER.as("jobnumber"), jp.COMPANY_ID.as("companyId"), jp.TITLE.as("title"), jp.CITY.as("city"), jp.DEPARTMENT.as("department"),
                                    jp.L_JOBID.as("lJobid"), jp.PUBLISH_DATE.as("publishDate"), jp.STOP_DATE.as("stopDate"), jp.ACCOUNTABILITIES.as("accountabilities"), jp.EXPERIENCE.as("experience"), jp.REQUIREMENT.as("requirement"),
                                    jp.SALARY.as("salary"), jp.LANGUAGE.as("language"), jp.JOB_GRADE.as("jobGrade"), jp.STATUS.as("status"), jp.VISITNUM.as("visitnum"), jp.LASTVISIT.as("lastvisit"),
                                    jp.SOURCE_ID.as("source_id"), jp.UPDATE_TIME.as("updateTime"), jp.BUSINESS_GROUP.as("businessGroup"), jp.EMPLOYMENT_TYPE.as("employmentType"), jp.HR_EMAIL.as("hrEmail"), jp.BENEFITS.as("benefits"),
                                    jp.DEGREE.as("degree"), jp.FEATURE.as("feature"), jp.EMAIL_NOTICE.as("emailNotice"), jp.CANDIDATE_SOURCE.as("candidateSource"), jp.OCCUPATION.as("occupation"), jp.IS_RECOM.as("isRecom"),
                                    jp.INDUSTRY.as("industry"), jp.HONGBAO_CONFIG_ID.as("hongbaoConfigId"), jp.HONGBAO_CONFIG_RECOM_ID.as("hongbaoConfigRecomId"), jp.HONGBAO_CONFIG_APP_ID.as("hongbaoConfigAppId"), jp.EMAIL_RESUME_CONF.as("emailResumeConf"),
                                    jp.L_POSTINGTARGETID.as("lPostingTargetId"),
                                    jp.PRIORITY.as("priority"), jp.SHARE_TPL_ID.as("shareTplId"), jp.DISTRICT.as("district"), jp.COUNT.as("count"), jp.SALARY_TOP.as("salaryTop"), jp.SALARY_BOTTOM.as("salaryBottom"),
                                    jp.EXPERIENCE_ABOVE.as("experienceAbove"), jp.DEGREE_ABOVE.as("degreeAbove"), jp.MANAGEMENT_EXPERIENCE.as("managementExperience"), jp.GENDER.as("gender"), jp.PUBLISHER.as("publisher"), jp.APP_CV_CONFIG_ID.as("appCvConfigId"),
                                    jp.SOURCE.as("source"), jp.HB_STATUS.as("hbStatus"), jp.CHILD_COMPANY_ID.as("childCompanyId"), jp.AGE.as("age"), jp.MAJOR_REQUIRED.as("majorRequired"), jp.WORK_ADDRESS.as("workAddress"),
                                    jp.KEYWORD.as("keyword"), jp.REPORTING_TO.as("reportingTo"), jp.IS_HIRING.as("isHiring"), jp.UNDERLINGS.as("underlings"), jp.LANGUAGE_REQUIRED.as("languageRequired"), jp.TARGET_INDUSTRY.as("targetIndustry"),
                                    jp.CURRENT_STATUS.as("currentStatus"), jp.POSITION_CODE.as("positionCode"), ht.ID.as("teamId"), ht.NAME.as("teamName"), ht.DESCRIPTION.as("teamDescription")
                            ).from(jp).leftJoin(ht).on(jp.TEAM_ID.equal(ht.ID)).where(jp.ID.in(pids));
                            // 分页
                            int page = 1;
                            int per_page = 20;
                            if (commonQuery != null && commonQuery.getPage() > 0) {
                                page = commonQuery.getPage();
                            }
                            per_page = commonQuery.getPer_page() > 0 ? commonQuery.getPer_page() : per_page;
                            record.limit((page - 1) * per_page, per_page);
                            positionDetails = record.fetchInto(PositionDetails.class);
                            // 查询职位图片信息
                            SelectOnConditionStep<Record3<String, UInteger, Integer>> recordRes = create.select(HrResource.HR_RESOURCE.RES_URL, JobPosition.JOB_POSITION.COMPANY_ID, JobPosition.JOB_POSITION.ID).from(HrCmsPages.HR_CMS_PAGES).join(HrCmsModule.HR_CMS_MODULE).on(HrCmsPages.HR_CMS_PAGES.ID.equal(HrCmsModule.HR_CMS_MODULE.PAGE_ID)).and(HrCmsModule.HR_CMS_MODULE.DISABLE.equal(0)).
                                    join(HrCmsMedia.HR_CMS_MEDIA).on(HrCmsModule.HR_CMS_MODULE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.MODULE_ID)).and(HrCmsMedia.HR_CMS_MEDIA.DISABLE.equal(0)).join(HrResource.HR_RESOURCE).on(HrResource.HR_RESOURCE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.RES_ID)).
                                    and(HrResource.HR_RESOURCE.DISABLE.equal(0)).and(HrResource.HR_RESOURCE.RES_TYPE.equal(0)).and(HrResource.HR_RESOURCE.RES_URL.isNotNull()).join(JobPosition.JOB_POSITION).on(JobPosition.JOB_POSITION.TEAM_ID.equal(HrCmsPages.HR_CMS_PAGES.CONFIG_ID));
                            recordRes.where(JobPosition.JOB_POSITION.ID.in(pid));
                            Map resMap = recordRes.groupBy(JobPosition.JOB_POSITION.COMPANY_ID, JobPosition.JOB_POSITION.ID).fetch().intoMap(JobPosition.JOB_POSITION.ID);
                            if (resMap != null && resMap.size() > 0) {
                                for (PositionDetails position : positionDetails) {
                                    Record3<String, UInteger, Integer> record4 = (Record3<String, UInteger, Integer>) resMap.get(Integer.valueOf(position.getId()));
                                    if (record4 != null) {
                                        position.setResUrl(String.valueOf(record4.getValue("res_url")));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return positionDetails;
    }

    public List<Integer> listPositionIdByUserId(int userId) {
        List<Integer> list = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            UserEmployeeRecord employeeRecord = create.selectFrom(UserEmployee.USER_EMPLOYEE)
                    .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(userId)
                            .and(UserEmployee.USER_EMPLOYEE.DISABLE.equal((byte) 0))
                            .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.equal((byte) 0)))
                    .fetchOne();
            if (employeeRecord != null) {
                Result<Record1<Integer>> result = create.select(JobPosition.JOB_POSITION.ID)
                        .from(JobPosition.JOB_POSITION)
                        .where(JobPosition.JOB_POSITION.COMPANY_ID.equal(UInteger.valueOf(employeeRecord.getCompanyId())))
                        .fetch();
                if (result != null && result.size() > 0) {
                    result.forEach(record -> {
                        list.add(record.value1());
                    });
                }
            }


        } catch (Exception e) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e);
            }
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
        return list;
    }
}
