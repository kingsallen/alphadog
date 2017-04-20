package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsMedia;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsModule;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsPages;
import com.moseeker.baseorm.db.hrdb.tables.HrResource;
import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.PositionDetails;

import org.jooq.DSLContext;
import org.jooq.GroupField;
import org.jooq.Record;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.SelectOnConditionStep;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Service;

import java.sql.Connection;
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
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {

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
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
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
        }
        return positionDetails;
    }


    /**
     * 通过CompanyId和TeamId查询职位列表
     * 包含职位的团队信息
     */
    public List<PositionDetails> positionDetailsList(CommonQuery commonQuery) {
        try (Connection conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
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
                record.where(jp.COMPANY_ID.equal(UInteger.valueOf(commonQuery.getEqualFilter().get("company_id"))));
            }
            // 通过Team_id 查询职位列表
            if (hashMap.get("team_id") != null) {
                record.where(jp.TEAM_ID.equal(Integer.valueOf(commonQuery.getEqualFilter().get("team_id"))));
            }
            // 分页
            int page = 1;
            int per_page = 20;
            if (commonQuery != null && commonQuery.getPage() > 0) {
                page = commonQuery.getPage();
            }
            per_page = commonQuery.getPer_page() > 0 ? commonQuery.getPer_page() : per_page;
            record.limit((page - 1) * per_page, per_page);
            List<PositionDetails> positionDetails = record.fetchInto(PositionDetails.class);

            if (positionDetails != null && positionDetails.size() > 0) {
                // 查询职位图片信息
                SelectOnConditionStep<Record4<String, UInteger, Integer, Integer>> recordRes = create.select(HrResource.HR_RESOURCE.RES_URL, JobPosition.JOB_POSITION.COMPANY_ID, JobPosition.JOB_POSITION.TEAM_ID, JobPosition.JOB_POSITION.ID).from(HrCmsPages.HR_CMS_PAGES).join(HrCmsModule.HR_CMS_MODULE).on(HrCmsPages.HR_CMS_PAGES.ID.equal(HrCmsModule.HR_CMS_MODULE.PAGE_ID)).and(HrCmsModule.HR_CMS_MODULE.DISABLE.equal(0)).
                        join(HrCmsMedia.HR_CMS_MEDIA).on(HrCmsModule.HR_CMS_MODULE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.MODULE_ID)).and(HrCmsMedia.HR_CMS_MEDIA.DISABLE.equal(0)).join(HrResource.HR_RESOURCE).on(HrResource.HR_RESOURCE.ID.equal(HrCmsMedia.HR_CMS_MEDIA.RES_ID)).
                        and(HrResource.HR_RESOURCE.DISABLE.equal(0)).and(HrResource.HR_RESOURCE.RES_TYPE.equal(0)).and(HrResource.HR_RESOURCE.RES_URL.isNotNull()).join(JobPosition.JOB_POSITION).on(JobPosition.JOB_POSITION.TEAM_ID.equal(HrCmsPages.HR_CMS_PAGES.CONFIG_ID));
                // 通过CompanyId查询职位列表
                if (hashMap.get("company_id") != null) {
                    recordRes.where(JobPosition.JOB_POSITION.COMPANY_ID.equal(UInteger.valueOf(commonQuery.getEqualFilter().get("company_id"))));
                }
                // 通过Team_id 查询职位列表
                if (hashMap.get("team_id") != null) {
                    recordRes.where(JobPosition.JOB_POSITION.TEAM_ID.equal(Integer.valueOf(commonQuery.getEqualFilter().get("team_id"))));
                }
                Map resMap = recordRes.groupBy(JobPosition.JOB_POSITION.COMPANY_ID, JobPosition.JOB_POSITION.ID).fetch().intoMap(JobPosition.JOB_POSITION.ID);
                if (resMap != null && resMap.size() > 0) {
                    for (PositionDetails positionDetailsTemp : positionDetails) {
                        System.out.println(positionDetailsTemp.getId());
                        Record4<String, UInteger, Integer, Integer> record4 = (Record4<String, UInteger, Integer, Integer>) resMap.get(Integer.valueOf(positionDetailsTemp.getId()));
                        if (record4 != null) {
                            positionDetailsTemp.setResUrl(String.valueOf(record4.getValue("res_url")));
                        }
                    }
                }
            }
            return positionDetails;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {

        }
        return null;
    }
}
