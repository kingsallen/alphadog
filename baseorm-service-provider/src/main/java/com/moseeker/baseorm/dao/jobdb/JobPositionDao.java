package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.analytics.tables.StJobSimilarity;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.hrdb.tables.*;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.pojo.JobPositionPojo;
import com.moseeker.baseorm.pojo.RecommendedPositonPojo;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.PositionDetails;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.thrift.TException;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

@Repository
public class JobPositionDao extends JooqCrudImpl<JobPositionDO, JobPositionRecord> {

    public JobPositionDao() {
        super(JobPosition.JOB_POSITION, JobPositionDO.class);
    }

    public JobPositionDao(TableImpl<JobPositionRecord> table, Class<JobPositionDO> jobPositionDOClass) {
        super(table, jobPositionDOClass);
    }

    public List<JobPositionDO> getPositions(CommonQuery query) {
        List<JobPositionDO> positions = new ArrayList<>();
        try {
            List<JobPositionRecord> records = getRecords(QueryConvert.commonQueryConvertToQuery(query));
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

    public List<JobPositionDO> getPositions(Query query) {
        return this.getDatas(query);
    }

    public Position getPositionWithCityCode(Query query) {

        logger.info("JobPositionDao getPositionWithCityCode");

        Position position = new Position();
        JobPositionRecord record = this.getRecord(query);
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
        return position;
    }

    /**
     * 通过PositionId查询职位详情
     * 包含职位的团队信息
     */
    public PositionDetails positionDetails(Integer positionId) {
        PositionDetails positionDetails = new PositionDetails();
        JobPosition jp =JobPosition.JOB_POSITION;
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
        return positionDetails;
    }


    /**
     * 通过CompanyId查询热招职位列表
     * 包含职位的团队信息
     */
    public List<PositionDetails> hotPositionDetailsList(int company_id,int pageNum,int page_size) {
        List<PositionDetails> positionDetails = new ArrayList<>();

        try {
<<<<<<< HEAD
            JobPosition jp = (JobPosition) JobPosition.JOB_POSITION.as("jp");
=======
            JobPosition jp =JobPosition.JOB_POSITION;
>>>>>>> d07d16bf62c35140abe041118e1eae8c9fb26172
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
         
            // 通过CompanyId查询职位列表
            if (company_id!=0) {
                List<SortField<?>> fields = new ArrayList<>(2);
                fields.add(jp.PRIORITY.asc());
                fields.add(jp.IS_RECOM.asc());
                fields.add(jp.UPDATE_TIME.desc());
                record.where(jp.COMPANY_ID.equal(company_id)).and(jp.STATUS.equal((byte) 0)).orderBy(fields);
            }
            // 分页
            int page = 1;
            int per_page = 20;
            if (pageNum!=0) {
                page =pageNum;
            }
            per_page = page_size > 0 ? page_size : per_page;
            record.limit((page - 1) * per_page, per_page);
            positionDetails = record.fetchInto(PositionDetails.class);

            if (positionDetails != null && positionDetails.size() > 0) {
                // 查询职位图片信息
                SelectOnConditionStep<Record4<String, Integer, Integer, Integer>> recordRes = 
                		create.select(HrResource.HR_RESOURCE.RES_URL, JobPosition.JOB_POSITION.COMPANY_ID, JobPosition.JOB_POSITION.TEAM_ID, JobPosition.JOB_POSITION.ID)
                		.from(HrCmsPages.HR_CMS_PAGES).join(HrCmsModule.HR_CMS_MODULE)
                		.on(HrCmsPages.HR_CMS_PAGES.ID.equal(HrCmsModule.HR_CMS_MODULE.PAGE_ID))
                		.and(HrCmsModule.HR_CMS_MODULE.DISABLE.equal(0)).
                        join(HrCmsMedia.HR_CMS_MEDIA)
                        .on(HrCmsModule.HR_CMS_MODULE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.MODULE_ID))
                        .and(HrCmsMedia.HR_CMS_MEDIA.DISABLE.equal(0))
                        .join(HrResource.HR_RESOURCE)
                        .on(HrResource.HR_RESOURCE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.RES_ID))
                        .
                        and(HrResource.HR_RESOURCE.DISABLE.equal(0))
                        .and(HrResource.HR_RESOURCE.RES_TYPE.equal(0))
                        .and(HrResource.HR_RESOURCE.RES_URL.isNotNull()).join(JobPosition.JOB_POSITION).on(JobPosition.JOB_POSITION.TEAM_ID.equal(HrCmsPages.HR_CMS_PAGES.CONFIG_ID));
                // 通过CompanyId查询职位列表
                if (company_id!=0) {
                    recordRes.where(JobPosition.JOB_POSITION.COMPANY_ID.equal(Integer.valueOf(company_id)));
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
        } 
        return positionDetails;
    }

    /**
     * 通过PostionId查询相似职位列表
     * 包含职位的团队信息
     */
    public List<PositionDetails> similarityPositionDetailsList(int pid,int pageNum,int page_size) {
        List<PositionDetails> positionDetails = new ArrayList<>();
        try {

            if (pid!=0) {
                Result<? extends Record> positionAndCompanyRecords = create.select().from(JobPosition.JOB_POSITION)
                        .join(HrCompany.HR_COMPANY).on(JobPosition.JOB_POSITION.COMPANY_ID.equal(HrCompany.HR_COMPANY.ID))
                        .where(JobPosition.JOB_POSITION.ID.equal(pid)).fetch();
                if (positionAndCompanyRecords.size() == 0) {
                    return positionDetails;
                }

                Record positionAndCompanyRecord = positionAndCompanyRecords.get(0);
                int company_id = ((UInteger) positionAndCompanyRecord.getValue("company_id")).intValue();
                int company_type = (int)positionAndCompanyRecord.getValue("type"); // 公司区分(其它:2,免费用户:1,企业用户:0)
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
                            Result<Record1<Integer>> result = create.select(HrCompany.HR_COMPANY.ID).
                                    from(HrCompany.HR_COMPANY).where(HrCompany.HR_COMPANY.PARENT_ID.equal(Integer.valueOf(company_id))).fetch();
                            if (result != null && result.size() > 0) {
                                result.forEach(record -> {
                                    record.get(HrCompany.HR_COMPANY.ID);
                                    departmentIds.add(record.get(HrCompany.HR_COMPANY.ID).intValue());
                                });
                            }
                            departmentIds.add(company_id);
                            condition = condition.and(StJobSimilarity.ST_JOB_SIMILARITY.DEPARTMENT_ID.in(departmentIds));
                        }
                        // 安装匹配度排序
                        List<SortField<?>> fields = new ArrayList<>(2);
                        fields.add(StJobSimilarity.ST_JOB_SIMILARITY.REC_SIM.asc());
                        recomResults = create.select().from(StJobSimilarity.ST_JOB_SIMILARITY).where(condition).orderBy(fields).fetch();
                        List<Integer> pids = new ArrayList<>();
                        for (Record recomResult : recomResults) {
                            Integer positionId = ((Integer) recomResult.getValue("recom_id")).intValue();
                            if (positionId != pid) {  // 去掉回当前的职位ID
                                pids.add(positionId);
                            }
                        }
                        if (pids != null && pids.size() > 0) {
                            JobPosition jp =JobPosition.JOB_POSITION;
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
                            if (pageNum > 0) {
                                page =pageNum;
                            }
                            per_page = page_size > 0 ? page_size : per_page;
                            record.limit((page - 1) * per_page, per_page);
                            positionDetails = record.fetchInto(PositionDetails.class);
                            // 查询职位图片信息
                            SelectOnConditionStep<Record3<String, Integer, Integer>> recordRes = create.select(HrResource.HR_RESOURCE.RES_URL, JobPosition.JOB_POSITION.COMPANY_ID, JobPosition.JOB_POSITION.ID).from(HrCmsPages.HR_CMS_PAGES).join(HrCmsModule.HR_CMS_MODULE).on(HrCmsPages.HR_CMS_PAGES.ID.equal(HrCmsModule.HR_CMS_MODULE.PAGE_ID)).and(HrCmsModule.HR_CMS_MODULE.DISABLE.equal(0)).
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

        } 
        return positionDetails;
    }
    /*
     * 获取推荐职位
     */
    public List<RecommendedPositonPojo> getRecommendedPositions(int pid) {
		// pid -> company_type
		List<RecommendedPositonPojo> recommedPositoinsList = new ArrayList<>();
		// pid -> company_id
		/*
		 * Result<? extends Record> positionResults =
		 * create.select().from(JobPosition.JOB_POSITION).where(JobPosition.
		 * JOB_POSITION.ID.equal(pid)).fetch(); Record positionResult =
		 * positionResults.get(0); int company_id = ((UInteger)
		 * positionResult.getValue("company_id")).intValue(); // get company
		 * info Result<? extends Record> companyResults =
		 * create.select().from(HrCompany.HR_COMPANY).where(HrCompany.
		 * HR_COMPANY.ID.equal((int)(company_id))).fetch();
		 * Record companyResult = companyResults.get(0); int company_type =
		 * ((UByte) companyResult.getValue("type")).intValue();
		 * //公司区分(其它:2,免费用户:1,企业用户:0)
		 */
		Result<? extends Record> positionAndCompanyRecords = create.select().from(JobPosition.JOB_POSITION)
				.join(HrCompany.HR_COMPANY).on(JobPosition.JOB_POSITION.COMPANY_ID.equal(HrCompany.HR_COMPANY.ID))
				.where(JobPosition.JOB_POSITION.ID.equal(pid)).fetch();
		if (positionAndCompanyRecords.size() == 0) {
			return recommedPositoinsList;
		}
		Record positionAndCompanyRecord = positionAndCompanyRecords.get(0);
		int company_id = positionAndCompanyRecord.getValue(JobPosition.JOB_POSITION.COMPANY_ID);
		int company_type = Integer.parseInt(positionAndCompanyRecord.getValue("type").toString()); // 公司区分(其它:2,免费用户:1,企业用户:0)

		// get recom
		Result<? extends Record> recomResults;
		Condition condition = StJobSimilarity.ST_JOB_SIMILARITY.POS_ID.equal(pid);
		if (company_type == 0) {
			UserHrAccountRecord account = create.selectFrom(UserHrAccount.USER_HR_ACCOUNT).where(UserHrAccount.USER_HR_ACCOUNT.ID.equal(positionAndCompanyRecord.getValue(JobPosition.JOB_POSITION.PUBLISHER))).fetchOne();
			//如果是子账号，则查询子账号下的推荐职位；如果是主帐号，则查询公司下的所有职位
			if(account != null && account.getAccountType() != null && account.getAccountType().intValue() == 1) {
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
				Result<Record1<Integer>> result = create.select(HrCompany.HR_COMPANY.ID).from(HrCompany.HR_COMPANY).where(HrCompany.HR_COMPANY.PARENT_ID.equal((int)(company_id))).fetch();
				List<Integer> departmentIds = new ArrayList<>();
				if(result != null && result.size() > 0) {
					result.forEach(record -> {
						record.get(HrCompany.HR_COMPANY.ID);
						departmentIds.add(record.get(HrCompany.HR_COMPANY.ID).intValue());
					});
				}
				departmentIds.add(company_id);
				condition = condition.and(StJobSimilarity.ST_JOB_SIMILARITY.DEPARTMENT_ID.in(departmentIds)); 
			}
		}
		recomResults = create.select().from(StJobSimilarity.ST_JOB_SIMILARITY).where(condition).fetch();
		List<Integer> pids = new ArrayList<>();
		for (Record recomResult : recomResults) {
			pids.add(((Integer) recomResult.getValue("recom_id")).intValue());
		}

		/*
		 * public int pid; public String job_title; public int company_id;
		 * public String company_name; public String company_logo;
		 */
		// pids -> result
		recommedPositoinsList = create
				.select(JobPosition.JOB_POSITION.ID.as("pid"), JobPosition.JOB_POSITION.TITLE.as("job_title"),
						JobPosition.JOB_POSITION.COMPANY_ID.as("company_id"),
						JobPosition.JOB_POSITION.SALARY_TOP.as("salary_top"),
						JobPosition.JOB_POSITION.SALARY_BOTTOM.as("salary_bottom"),
						JobPosition.JOB_POSITION.CITY.as("job_city"),
						JobPosition.JOB_POSITION.PUBLISHER.as("publisher"),
						HrCompany.HR_COMPANY.ABBREVIATION.as("company_name"),
						HrCompany.HR_COMPANY.LOGO.as("company_logo"))
				.from(JobPosition.JOB_POSITION).join(HrCompany.HR_COMPANY)
				.on(HrCompany.HR_COMPANY.ID.equal(JobPosition.JOB_POSITION.COMPANY_ID))
				.where(JobPosition.JOB_POSITION.ID.in(pids)).fetch().into(RecommendedPositonPojo.class);
		/* 子公司职位需要返回子公司的公司简称和公司logo */
		recommedPositoinsList.forEach(position -> {
			/* 检查是否是子公司的职位 */
			int publisher = position.getPublisher();
			HrCompanyAccountRecord hrcompanyAccountrecord = create.selectFrom(HrCompanyAccount.HR_COMPANY_ACCOUNT)
					.where(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.equal(publisher)).fetchOne();
			if (hrcompanyAccountrecord != null && hrcompanyAccountrecord.getCompanyId() != null) {
				if (position.getCompany_id() != hrcompanyAccountrecord.getCompanyId()) {
					HrCompanyRecord subcompany = create.selectFrom(HrCompany.HR_COMPANY)
							.where(HrCompany.HR_COMPANY.ID.equal((int)(hrcompanyAccountrecord.getCompanyId()))).fetchOne();
					if (subcompany != null){
						position.setCompany_logo(subcompany.getLogo());
						position.setCompany_name(subcompany.getAbbreviation());							
					}
				}
			}
		});
			
		return recommedPositoinsList;
	}
    /*
     * 根据id获取position
     */
    public JobPositionPojo getPosition(int positionId) {
        List<JobPositionPojo> jobPositionPojoList = null;
        if (positionId > 0) {
            Condition condition = JobPosition.JOB_POSITION.ID.equal(positionId);
            jobPositionPojoList = create.select().from(JobPosition.JOB_POSITION)
                    .where(condition).fetchInto(JobPositionPojo.class);
        }
        return jobPositionPojoList != null ? jobPositionPojoList.get(0) : null;
    }
    /*
     * 获取position
     */
    public Position getPositionByQuery(Query query) throws TException {
		Position position = new Position();
		try {
			JobPositionRecord record = this.getRecord(query);
			if(record != null) {
				record.into(position);
				position.setHb_status(record.getHbStatus());
				position.setCompany_id(record.getCompanyId().intValue());
				position.setAccountabilities(record.getAccountabilities());
				return position;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return position;
	}



    public JobPositionRecord getPositionById(int positionId) {
        JobPositionRecord record = new JobPositionRecord();
        if(positionId > 0) {
            Result<JobPositionRecord> result = create.selectFrom(JobPosition.JOB_POSITION)
                    .where(JobPosition.JOB_POSITION.ID.equal(positionId))
                    .limit(1).fetch();
            if(result != null && result.size() > 0) {
                record = result.get(0);
            }
        }
        return record;
    }

	public int updatePosition(Position position) throws TException {
		int count = 0;
		if(position.getId() > 0) {
			JobPositionRecord record = (JobPositionRecord)BeanUtils.structToDB(position, JobPositionRecord.class);
			try {
				count = this.updateRecord(record);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return count;
	}
    public List<Integer> listPositionIdByUserId(int userId) {
        List<Integer> list = new ArrayList<>();
        UserEmployeeRecord employeeRecord = create.selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(userId)
                        .and(UserEmployee.USER_EMPLOYEE.DISABLE.equal((byte)0))
                        .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.equal((byte)0)))
                .fetchOne();
        if (employeeRecord != null) {
            Result<Record1<Integer>> result = create.select(JobPosition.JOB_POSITION.ID)
                    .from(JobPosition.JOB_POSITION)
                    .where(JobPosition.JOB_POSITION.COMPANY_ID.equal(employeeRecord.getCompanyId()))
                    .fetch();
            if(result != null && result.size() > 0) {
                result.forEach(record ->  {
                    list.add(record.value1());
                });
            }
        }
        return list;
    }
}
