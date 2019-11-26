package com.moseeker.entity.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.dictdb.DictCollegeDao;
import com.moseeker.baseorm.dao.dictdb.DictIndustryDao;
import com.moseeker.baseorm.dao.dictdb.DictPositionDao;
import com.moseeker.baseorm.dao.profiledb.*;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.dictdb.tables.records.*;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.Constant.ProfileAttributeLengthLimit;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProfileUtils {
	protected Logger logger = LoggerFactory.getLogger(ProfileUtils.class);


	private final static int DEFAULT_FLAG=0;

	@Autowired
    private DictCollegeDao collegeDao;

	public List<ProfileWorksRecord> mapToWorksRecords(List<Map<String, Object>> works) {
		List<ProfileWorksRecord> worksRecords = new ArrayList<>();
		if (works != null && works.size() > 0) {
			works.forEach(work -> {
				ProfileWorksRecord record = BeanUtils.MapToRecord(work, ProfileWorksRecord.class);

				if (record != null) {
					subWorksMaxLimit(record);

					worksRecords.add(record);
				}
			});
		}
		return worksRecords;
	}

	/**
	 * 超过最大长度限制的字段，做截取操作
	 * @param record
	 */
	private void subWorksMaxLimit(ProfileWorksRecord record) {
		if(StringUtils.isNotBlank(record.getDescription())
				&& record.getDescription().length() > ProfileAttributeLengthLimit.WorksDescription.getLengthLimit()) {
//			record.setDescription(record.getDescription().substring(0, ProfileAttributeLengthLimit.WorksDescription.getLengthLimit()));
			record.setDescription(this.handlerOutLimitString(record.getDescription(),ProfileAttributeLengthLimit.WorksDescription.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getName()) && record.getName().length() > ProfileAttributeLengthLimit.WorksName.getLengthLimit()) {
//			record.setName(record.getName().substring(0, ProfileAttributeLengthLimit.WorksName.getLengthLimit()));
			record.setName(this.handlerOutLimitString(record.getName(),ProfileAttributeLengthLimit.WorksName.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getUrl()) && record.getUrl().length() > ProfileAttributeLengthLimit.WorksURL.getLengthLimit()) {
//			record.setUrl(record.getUrl().substring(0, ProfileAttributeLengthLimit.WorksURL.getLengthLimit()));
			record.setUrl(this.handlerOutLimitString(record.getUrl(),ProfileAttributeLengthLimit.WorksURL.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getCover()) && record.getCover().length() > ProfileAttributeLengthLimit.WorksCover.getLengthLimit()) {
//			record.setCover(record.getCover().substring(0, ProfileAttributeLengthLimit.WorksCover.getLengthLimit()));
			record.setCover(this.handlerOutLimitString(record.getCover(),ProfileAttributeLengthLimit.WorksCover.getLengthLimit(),DEFAULT_FLAG));
		}
	}

	/*
	    @author  zzt
	    @param outLimitString  --------超出界限的字符串
	    @param length          --------最大长度限制
	    @param flag            --------扩展字段，用于支持其他特殊的情况  0：是目前的使用，超出的部分取最大长度减3加上...
	    @return string
	 */
	private String handlerOutLimitString(String outLimitString,int length,int flag){
		String symbol="";
		if(flag==0){
			symbol="...";
			length=length-3;
			outLimitString=outLimitString.substring(0,length)+symbol;
		}
		return outLimitString;
	}

	public List<ProfileWorkexpEntity> mapToWorkexpRecords(List<Map<String, Object>> workexps, int source) {
        logger.info("profile resume works:{}",workexps);
		List<ProfileWorkexpEntity> workexpRecords = new ArrayList<>();
		if (workexps != null && workexps.size() > 0) {
			workexps.forEach(workexp -> {
                logger.info("profile resume work:{}",workexp);
				ProfileWorkexpEntity record = BeanUtils.MapToRecord(workexp, ProfileWorkexpEntity.class);
				if (record != null) {
					if (workexp.get("start_date") != null) {
						record.setStart(BeanUtils.convertToSQLDate(workexp.get("start_date")));
					} else if (workexp.get("startDate") != null) {
						record.setStart(BeanUtils.convertToSQLDate(workexp.get("startDate")));
					}
					if (workexp.get("end_date") != null) {
						record.setEnd(BeanUtils.convertToSQLDate(workexp.get("end_date")));
					} else if (workexp.get("endDate") != null) {
						record.setEnd(BeanUtils.convertToSQLDate(workexp.get("endDate")));
					}
					if(workexp.get("endUntilNow")==null ){
						record.setEndUntilNow((byte)0);
					}
					subWorkExpMaxLimit(record);

					if (workexp.get("company") != null) {
						@SuppressWarnings("unchecked")
						Map<String, Object> company = (Map<String, Object>) workexp.get("company");
						if (company != null) {
							HrCompanyRecord hrCompany = new HrCompanyRecord();
							if (company.get("company_name") != null) {
								String companyName=BeanUtils.converToString(company.get("company_name"));
								hrCompany.setName(companyName);
								if(StringUtils.isNotBlank(companyName)&&companyName.length()>ProfileAttributeLengthLimit.CompanyName.getLengthLimit()){
									hrCompany.setName(this.handlerOutLimitString(companyName,ProfileAttributeLengthLimit.CompanyName.getLengthLimit(),DEFAULT_FLAG));
								}
							} else if (company.get("companyName") != null) {
								String companyName=BeanUtils.converToString(company.get("companyName"));
								hrCompany.setName(companyName);
								if(StringUtils.isNotBlank(companyName)&&companyName.length()>ProfileAttributeLengthLimit.CompanyName.getLengthLimit()){
									hrCompany.setName(this.handlerOutLimitString(companyName,ProfileAttributeLengthLimit.CompanyName.getLengthLimit(),DEFAULT_FLAG));
								}
//                                hrCompany.setName(BeanUtils.converToString(company.get("companyName")));
							} else if (company.get("name") != null) {
								String companyName=BeanUtils.converToString(company.get("name"));
								hrCompany.setName(companyName);
								if(StringUtils.isNotBlank(companyName)&&companyName.length()>ProfileAttributeLengthLimit.CompanyName.getLengthLimit()){
									hrCompany.setName(this.handlerOutLimitString(companyName,ProfileAttributeLengthLimit.CompanyName.getLengthLimit(),DEFAULT_FLAG));
								}
							}
							if (company.get("company_industry") != null) {
								String companyIndustry=BeanUtils.converToString(company.get("company_industry"));
								hrCompany.setIndustry(companyIndustry);
								if(StringUtils.isNotBlank(companyIndustry)&&companyIndustry.length()>ProfileAttributeLengthLimit.CompanyIndustry.getLengthLimit()){
									hrCompany.setIndustry(this.handlerOutLimitString(companyIndustry,ProfileAttributeLengthLimit.CompanyIndustry.getLengthLimit(),DEFAULT_FLAG));
								}
//								hrCompany.setIndustry(BeanUtils.converToString(company.get("company_industry")));
							} else if (company.get("companyIndustry") != null) {
								String companyIndustry=BeanUtils.converToString(company.get("companyIndustry"));
								hrCompany.setIndustry(companyIndustry);
								if(StringUtils.isNotBlank(companyIndustry)&&companyIndustry.length()>ProfileAttributeLengthLimit.CompanyIndustry.getLengthLimit()){
									hrCompany.setIndustry(this.handlerOutLimitString(companyIndustry,ProfileAttributeLengthLimit.CompanyIndustry.getLengthLimit(),DEFAULT_FLAG));
								}
//                                hrCompany.setIndustry(BeanUtils.converToString(company.get("companyIndustry")));
							} else if (company.get("industry") != null) {
								String companyIndustry=BeanUtils.converToString(company.get("industry"));
								hrCompany.setIndustry(companyIndustry);
								if(StringUtils.isNotBlank(companyIndustry)&&companyIndustry.length()>ProfileAttributeLengthLimit.CompanyIndustry.getLengthLimit()){
									hrCompany.setIndustry(this.handlerOutLimitString(companyIndustry,ProfileAttributeLengthLimit.CompanyIndustry.getLengthLimit(),DEFAULT_FLAG));
								}
							}
							if (company.get("company_introduction") != null) {
								hrCompany.setIntroduction(BeanUtils.converToString(company.get("company_introduction")));
							} else if (company.get("companyIntroduction") != null) {
								hrCompany
										.setIntroduction(BeanUtils.converToString(company.get("companyIntroduction")));
							}
							if (company.get("scale") != null) {
								hrCompany.setScale(BeanUtils.converToByte(company.get("scale")));
							} else if ((company.get("companyScale") != null)) {
								hrCompany.setScale(BeanUtils.converToByte(company.get("companyScale")));
							} else if((company.get("company_scale") != null)){
								hrCompany.setScale(BeanUtils.converToByte(company.get("company_scale")));
							}
							if (company.get("company_property") != null) {
								hrCompany.setProperty(BeanUtils.converToByte(company.get("company_property")));
							} else if (company.get("companyProperty") != null) {
								hrCompany.setProperty(BeanUtils.converToByte(company.get("companyProperty")));
							} else if (company.get("property") != null) {
								hrCompany.setProperty(BeanUtils.converToByte(company.get("property")));
							}
							hrCompany.setType((byte)(Constant.COMPANY_TYPE_FREE));
							switch(source) {
								case Constant.PROFILE_SOURCE_WEIXIN_TEGETHER_IMPORT:
								case Constant.PROFILE_SOURCE_WEIXIN_COMPANY_IMPORT:
									hrCompany.setSource((byte)(Constant.COMPANY_SOURCE_WX_IMPORT));
									break;
								case Constant.PROFILE_SOURCE_PC_IMPORT:
									hrCompany.setSource((byte)(Constant.COMPANY_SOURCE_PC_IMPORT));
									break;
								default:
							}
							record.setCompany(hrCompany);
						}
					}

					ValidationMessage<ProfileWorkexpEntity> vm = ProfileValidation.verifyWorkExp(record);
					if(vm.isPass()) {
						workexpRecords.add(record);
					}
				}
			});
		}
		return workexpRecords;
	}

	/**
	 * 如果超过长度限制，则做截取
	 * @param record
	 */
	private void subWorkExpMaxLimit(ProfileWorkexpEntity record) {
		if(StringUtils.isNotBlank(record.getDescription()) && record.getDescription().length() > ProfileAttributeLengthLimit.WorkExpDescription.getLengthLimit()) {
//			record.setDescription(record.getDescription().substring(0, ProfileAttributeLengthLimit.WorkExpDescription.getLengthLimit()));
			record.setDescription(this.handlerOutLimitString(record.getDescription(),ProfileAttributeLengthLimit.WorkExpDescription.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getJob()) && record.getJob().length() > ProfileAttributeLengthLimit.WorkExpJob.getLengthLimit()) {
//			record.setJob(record.getJob().substring(0, ProfileAttributeLengthLimit.WorkExpJob.getLengthLimit()));
			record.setJob(this.handlerOutLimitString(record.getJob(),ProfileAttributeLengthLimit.WorkExpJob.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getIndustryName()) && record.getIndustryName().length() > ProfileAttributeLengthLimit.WorkExpIndustryName.getLengthLimit()) {
//			record.setIndustryName(record.getIndustryName().substring(0, ProfileAttributeLengthLimit.WorkExpIndustryName.getLengthLimit()));
			record.setIndustryName(this.handlerOutLimitString(record.getIndustryName(),ProfileAttributeLengthLimit.WorkExpIndustryName.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getDepartmentName()) && record.getDepartmentName().length() > ProfileAttributeLengthLimit.WorkExpDepartmentName.getLengthLimit()) {
//			record.setDepartmentName(record.getDepartmentName().substring(0, ProfileAttributeLengthLimit.WorkExpDepartmentName.getLengthLimit()));
			record.setDepartmentName(this.handlerOutLimitString(record.getDepartmentName(),ProfileAttributeLengthLimit.WorkExpDepartmentName.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getPositionName()) && record.getPositionName().length() > ProfileAttributeLengthLimit.WorkExpPositionName.getLengthLimit()) {
//			record.setPositionName(record.getPositionName().substring(0, ProfileAttributeLengthLimit.WorkExpPositionName.getLengthLimit()));
			record.setPositionName(this.handlerOutLimitString(record.getPositionName(),ProfileAttributeLengthLimit.WorkExpPositionName.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getCityName()) && record.getCityName().length() > ProfileAttributeLengthLimit.WorkExpCityName.getLengthLimit()) {
//			record.setCityName(record.getCityName().substring(0, ProfileAttributeLengthLimit.WorkExpCityName.getLengthLimit()));
			record.setCityName(this.handlerOutLimitString(record.getCityName(),ProfileAttributeLengthLimit.WorkExpCityName.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getReportTo()) && record.getReportTo().length() > ProfileAttributeLengthLimit.WorkExpReportTo.getLengthLimit()) {
//			record.setReportTo(record.getReportTo().substring(0, ProfileAttributeLengthLimit.WorkExpReportTo.getLengthLimit()));
			record.setReportTo(this.handlerOutLimitString(record.getReportTo(),ProfileAttributeLengthLimit.WorkExpReportTo.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getReference()) && record.getReference().length() > ProfileAttributeLengthLimit.WorkExpReference.getLengthLimit()) {
//			record.setReference(record.getReference().substring(0, ProfileAttributeLengthLimit.WorkExpReference.getLengthLimit()));
			record.setReference(this.handlerOutLimitString(record.getReference(),ProfileAttributeLengthLimit.WorkExpReference.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getResignReason()) && record.getResignReason().length() > ProfileAttributeLengthLimit.WorkExpResignReason.getLengthLimit()) {
//			record.setResignReason(record.getResignReason().substring(0, ProfileAttributeLengthLimit.WorkExpResignReason.getLengthLimit()));
			record.setResignReason(this.handlerOutLimitString(record.getResignReason(),ProfileAttributeLengthLimit.WorkExpResignReason.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getAchievement()) && record.getAchievement().length() > ProfileAttributeLengthLimit.WorkExpAchievement.getLengthLimit()) {
//			record.setAchievement(record.getAchievement().substring(0, ProfileAttributeLengthLimit.WorkExpAchievement.getLengthLimit()));
			record.setAchievement(this.handlerOutLimitString(record.getAchievement(),ProfileAttributeLengthLimit.WorkExpAchievement.getLengthLimit(),DEFAULT_FLAG));
		}
	}

	public List<ProfileSkillRecord> mapToSkillRecords(List<Map<String, Object>> skills) {
		List<ProfileSkillRecord> skillRecords = new ArrayList<>();
		if (skills != null && skills.size() > 0) {
			skills.forEach(skill -> {
				ProfileSkillRecord record = BeanUtils.MapToRecord(skill, ProfileSkillRecord.class);
				this.skillMaxLimit(record);
				ValidationMessage<ProfileSkillRecord> vm = ProfileValidation.verifySkill(record);
				if (record != null && vm.isPass()) {
					skillRecords.add(record);
				}
			});
		}
		return skillRecords;
	}
	/*
	 处理技巧的超长字段
	 */

	/*
        @author  zzt
        @param record
        简历中掌握的技巧的名称超过长度的截取操作
     */
	private  void  skillMaxLimit(ProfileSkillRecord record){
		if(StringUtils.isNotBlank(record.getName())&&record.getName().length()>ProfileAttributeLengthLimit.SkillName.getLengthLimit()){
			record.setName(this.handlerOutLimitString(record.getName(),ProfileAttributeLengthLimit.SkillName.getLengthLimit(),DEFAULT_FLAG));
		}
	}

	public List<ProfileProjectexpRecord> mapToProjectExpsRecords(List<Map<String, Object>> projectexps) {
	    logger.info("profile resume projects:{}",projectexps);
		List<ProfileProjectexpRecord> projectExpRecords = new ArrayList<>();
		if (projectexps != null && projectexps.size() > 0) {
			projectexps.forEach(projectexp -> {
                logger.info("profile resume project:{}",projectexp);
				ProfileProjectexpRecord record = BeanUtils.MapToRecord(projectexp, ProfileProjectexpRecord.class);
				if (record != null) {

					if (projectexp.get("start_date") != null) {
						record.setStart(BeanUtils.convertToSQLDate(projectexp.get("start_date")));
					} else if (projectexp.get("startDate") != null) {
						record.setStart(BeanUtils.convertToSQLDate(projectexp.get("startDate")));
					}
					if (projectexp.get("end_date") != null) {
						record.setEnd(BeanUtils.convertToSQLDate(projectexp.get("end_date")));
					} else if (projectexp.get("endDate") != null) {
						record.setEnd(BeanUtils.convertToSQLDate(projectexp.get("endDate")));
					}
					if(projectexp.get("endUntilNow")==null){
						record.setEndUntilNow((byte)0);
					}

					subProjectExpMaxLimit(record);

					ValidationMessage<ProfileProjectexpRecord> vm = ProfileValidation.verifyProjectExp(record);
					if(vm.isPass()) {
						projectExpRecords.add(record);
					}
				}
			});
		}
		return projectExpRecords;
	}
	/*
           @author  zzt
           @param record
           简历中项目经历的中字段长度的截取操作
        */
	private void subProjectExpMaxLimit(ProfileProjectexpRecord record) {
		if(StringUtils.isNotBlank(record.getDescription()) && record.getDescription().length() > ProfileAttributeLengthLimit.ProjectExpDescription.getLengthLimit()) {
//			record.setDescription(record.getDescription().substring(0, ProfileAttributeLengthLimit.ProjectExpDescription.getLengthLimit()));
			record.setDescription(this.handlerOutLimitString(record.getDescription(),ProfileAttributeLengthLimit.ProjectExpDescription.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getResponsibility()) && record.getResponsibility().length() > ProfileAttributeLengthLimit.ProjectExpResponsibility.getLengthLimit()) {
//			record.setResponsibility(record.getResponsibility().substring(0, ProfileAttributeLengthLimit.ProjectExpResponsibility.getLengthLimit())+"...");
			record.setResponsibility(this.handlerOutLimitString(record.getResponsibility(),ProfileAttributeLengthLimit.ProjectExpResponsibility.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getName()) && record.getName().length() > ProfileAttributeLengthLimit.ProjectExpName.getLengthLimit()) {
//			record.setName(record.getName().substring(0, ProfileAttributeLengthLimit.ProjectExpName.getLengthLimit()));
			record.setName(this.handlerOutLimitString(record.getName(),ProfileAttributeLengthLimit.ProjectExpName.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getCompanyName()) && record.getCompanyName().length() > ProfileAttributeLengthLimit.ProjectExpCompanyName.getLengthLimit()) {

//			record.setCompanyName(record.getCompanyName().substring(0, ProfileAttributeLengthLimit.ProjectExpCompanyName.getLengthLimit()));
			record.setCompanyName(this.handlerOutLimitString(record.getCompanyName(),ProfileAttributeLengthLimit.ProjectExpCompanyName.getLengthLimit(),DEFAULT_FLAG));
		}
		if (StringUtils.isNotBlank(record.getDevTool()) && record.getDevTool().length() > ProfileAttributeLengthLimit.ProjectExpDevTool.getLengthLimit()) {
//			record.setDevTool(record.getDevTool().substring(0, ProfileAttributeLengthLimit.ProjectExpDevTool.getLengthLimit()));
			record.setDevTool(this.handlerOutLimitString(record.getDevTool(),ProfileAttributeLengthLimit.ProjectExpDevTool.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getAchievement())&&record.getAchievement().length()>ProfileAttributeLengthLimit.ProjectExpAchievement.getLengthLimit()){
			record.setAchievement(this.handlerOutLimitString(record.getAchievement(),ProfileAttributeLengthLimit.ProjectExpAchievement.getLengthLimit(),DEFAULT_FLAG));
		}

		if(StringUtils.isNotBlank(record.getMember())&&record.getMember().length()>ProfileAttributeLengthLimit.ProjectExpMember.getLengthLimit()){
			record.setMember(this.handlerOutLimitString(record.getMember(),ProfileAttributeLengthLimit.ProjectExpMember.getLengthLimit(),DEFAULT_FLAG));
		}

		if(StringUtils.isNotBlank(record.getHardware())&&record.getHardware().length()>ProfileAttributeLengthLimit.ProjectExpHardWare.getLengthLimit()){
			record.setHardware(this.handlerOutLimitString(record.getHardware(),ProfileAttributeLengthLimit.ProjectExpHardWare.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getSoftware())&&record.getSoftware().length()>ProfileAttributeLengthLimit.ProjectSoftWare.getLengthLimit()){
			record.setSoftware(this.handlerOutLimitString(record.getSoftware(),ProfileAttributeLengthLimit.ProjectSoftWare.getLengthLimit(),DEFAULT_FLAG));
		}

		if(StringUtils.isNotBlank(record.getUrl())&&record.getUrl().length()>ProfileAttributeLengthLimit.ProjectExpURL.getLengthLimit()){
			record.setUrl(this.handlerOutLimitString(record.getUrl(),ProfileAttributeLengthLimit.ProjectExpURL.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getRole())&&record.getRole().length()>ProfileAttributeLengthLimit.ProjectExpRole.getLengthLimit()){
			record.setRole(this.handlerOutLimitString(record.getRole(),ProfileAttributeLengthLimit.ProjectExpRole.getLengthLimit(),DEFAULT_FLAG));
		}
	}

	public ProfileOtherRecord mapToOtherRecord(Map<String, Object> other, ProfileExtParam extParam) {
		ProfileOtherRecord otherRecord = null;
		if (other != null) {
			otherRecord = new ProfileOtherRecord();
			otherRecord.setOther(JSON.toJSONString(other));
			ValidationMessage<ProfileOtherRecord> vm = ProfileValidation.verifyCustomizeResume(otherRecord);
			if(!vm.isPass()) {
				otherRecord = null;
			}
		}
		return otherRecord;
	}

	public List<ProfileLanguageRecord> mapToLanguageRecord(List<Map<String, Object>> languages) {
		List<ProfileLanguageRecord> languageRecords = new ArrayList<>();
		if (languages != null && languages.size() > 0) {
			languages.forEach(language -> {
				ProfileLanguageRecord record = BeanUtils.MapToRecord(language, ProfileLanguageRecord.class);
				this.languageMaxLimit(record);
				ValidationMessage<ProfileLanguageRecord> vm = ProfileValidation.verifyLanguage(record);
				if (record != null && vm.isPass()) {
					languageRecords.add(record);
				}
			});
		}
		return languageRecords;
	}
	/*
         @author  zzt
         @param record
         语言名称最大长度限制处理
      */
	private void languageMaxLimit(ProfileLanguageRecord record){
		if(StringUtils.isNotBlank(record.getName())&&record.getName().length()>ProfileAttributeLengthLimit.LanguageName.getLengthLimit()) {
			record.setName(this.handlerOutLimitString(record.getName(), ProfileAttributeLengthLimit.LanguageName.getLengthLimit(), DEFAULT_FLAG));
		}
	}

	@SuppressWarnings("unchecked")
	public List<IntentionRecord> mapToIntentionRecord(List<Map<String, Object>> intentions) {
		List<IntentionRecord> intentionRecords = new ArrayList<>();
		if (intentions != null && intentions.size() > 0) {
			intentions.forEach(intention -> {
				IntentionRecord record = BeanUtils.MapToRecord(intention, IntentionRecord.class);
				record.getCities().clear();
				record.getPositions().clear();
				record.getIndustries().clear();
				if (record != null) {
					if (intention.get("cities") != null) {
						List<Map<String, Object>> cities = (List<Map<String, Object>>) intention.get("cities");
						if (cities != null && cities.size() > 0) {
							for (Map<String, Object> city : cities) {
								ProfileIntentionCityRecord cityRecord = BeanUtils.MapToRecord(city,
										ProfileIntentionCityRecord.class);
								if (cityRecord != null && ((cityRecord.getCityCode() != null&& cityRecord.getCityCode().intValue() != 0)|| StringUtils.isNotBlank(cityRecord.getCityName()))) {
									record.getCities().add(cityRecord);
								}
							}
						}
					}

					if (intention.get("positions") != null) {
						List<Map<String, Object>> positions = (List<Map<String, Object>>) intention.get("positions");
						if (positions != null && positions.size() > 0) {
							for (Map<String, Object> position : positions) {
								ProfileIntentionPositionRecord positionRecord = BeanUtils.MapToRecord(position,
										ProfileIntentionPositionRecord.class);
								if (positionRecord != null && ((positionRecord.getPositionCode() != null
										&& positionRecord.getPositionCode().intValue() != 0)
										|| StringUtils.isNotBlank(positionRecord.getPositionName()))) {
									record.getPositions().add(positionRecord);
//									break;
								}
							}
						}
					}

					if (intention.get("industries") != null) {
						List<Map<String, Object>> industries = (List<Map<String, Object>>) intention.get("industries");
						if (industries != null && industries.size() > 0) {
							for (Map<String, Object> industry : industries) {
								ProfileIntentionIndustryRecord industryRecord = BeanUtils.MapToRecord(industry,
										ProfileIntentionIndustryRecord.class);
								if (industryRecord != null && ((industryRecord.getIndustryCode() != null
										&& industryRecord.getIndustryCode().intValue() != 0)
										|| StringUtils.isNotBlank(industryRecord.getIndustryName()))) {
									record.getIndustries().add(industryRecord);
//									break;
								}
							}
						}
					}
					this.intentionMaxLimit(record);
					intentionRecords.add(record);

				}
			});
		}
		return intentionRecords;
	}


	/*
       @author  zzt
       @param record  IntentionRecord
       处理求职意向中的超出长度的字段
     */
	private void intentionMaxLimit(IntentionRecord record){
		if(StringUtils.isNotBlank(record.getTag())&&record.getTag().length()>ProfileAttributeLengthLimit.IntentionTag.getLengthLimit()){
			record.setTag(this.handlerOutLimitString(record.getTag(),ProfileAttributeLengthLimit.IntentionTag.getLengthLimit(),DEFAULT_FLAG));
		}
		if(record.getCities()!=null){
			List<ProfileIntentionCityRecord> cityList=record.getCities();
			for(ProfileIntentionCityRecord profileIntentionCityRecord:cityList){
				if(StringUtils.isNotBlank(profileIntentionCityRecord.getCityName())&&profileIntentionCityRecord.getCityName().length()>ProfileAttributeLengthLimit.IntentionCityName.getLengthLimit()){
					profileIntentionCityRecord.setCityName(this.handlerOutLimitString(profileIntentionCityRecord.getCityName(),ProfileAttributeLengthLimit.IntentionCityName.getLengthLimit(),DEFAULT_FLAG));
				}
			}
		}

		if(record.getIndustries()!=null){
			List<ProfileIntentionIndustryRecord> industryList=record.getIndustries();
			for(ProfileIntentionIndustryRecord industryRecord:industryList){
				if(StringUtils.isNotBlank(industryRecord.getIndustryName())&&industryRecord.getIndustryName().length()>ProfileAttributeLengthLimit.IntentionIndustryName.getLengthLimit()){
					industryRecord.setIndustryName(this.handlerOutLimitString(industryRecord.getIndustryName(),ProfileAttributeLengthLimit.IntentionIndustryName.getLengthLimit(),DEFAULT_FLAG));
				}
			}
		}
		if(record.getPositions()!=null){
			List<ProfileIntentionPositionRecord> positionList=record.getPositions();
			for(ProfileIntentionPositionRecord positionRecord:positionList){
				if(StringUtils.isNotBlank(positionRecord.getPositionName())&&positionRecord.getPositionName().length()>ProfileAttributeLengthLimit.IntentionPositionName.getLengthLimit()){
					positionRecord.setPositionName(this.handlerOutLimitString(positionRecord.getPositionName(),ProfileAttributeLengthLimit.IntentionPositionName.getLengthLimit(),DEFAULT_FLAG));
				}
			}
		}
	}


	public ProfileImportRecord mapToImportRecord(Map<String, Object> importMap) {
		ProfileImportRecord record = null;
		if (importMap != null) {
			record = BeanUtils.MapToRecord(importMap, ProfileImportRecord.class);
			if (importMap.get("data") != null) {
				record.setData(JSON.toJSONString(importMap.get("data")));
			}
			return record;
		}
		return record;
	}

	public List<ProfileEducationRecord> mapToEducationRecords(List<Map<String, Object>> educations, ProfileExtParam extParam) {
	    logger.info("prfile resume educations:{}",educations);
		List<ProfileEducationRecord> educationRecords = new ArrayList<>();
		if (educations != null && educations.size() > 0) {
			educations.forEach(education -> {
                logger.info("prfile resume education:{}",education);
				ProfileEducationRecord record = BeanUtils.MapToRecord(education, ProfileEducationRecord.class);
				if (record != null) {
//					if(StringUtils.isNotBlank(record.getDescription()) && record.getDescription().length() > Constant.DESCRIPTION_LENGTH) {
//						record.setDescription(record.getDescription().substring(0, Constant.DESCRIPTION_LENGTH));
//					}
					if (education.get("start_date") != null) {
						record.setStart(BeanUtils.convertToSQLDate(education.get("start_date")));
					} else if (education.get("startDate") != null) {
						record.setStart(BeanUtils.convertToSQLDate(education.get("startDate")));
					}

					if (education.get("end_date") != null) {
						record.setEnd(BeanUtils.convertToSQLDate(education.get("end_date")));
					} else if (education.get("endDate") != null) {
						record.setEnd(BeanUtils.convertToSQLDate(education.get("endDate")));
					}
					if(education.get("endUntilNow")==null){
						record.setEndUntilNow((byte)0);
					}
					if(record.getCollegeName() != null){
                        if(extParam.getCollegeMap().get(record.getCollegeName()) != null && record.getCountryId() == null ){
                            DictCollegeDO collegeDO = extParam.getCollegeMap().get(record.getCollegeName());
                            record.setCollegeCode(collegeDO.getCode());
                            record.setCollegeLogo(collegeDO.getLogo());
                            record.setCountryId(collegeDO.getCountry_code());
                        }
                    }

					ValidationMessage<ProfileEducationRecord> vm = ProfileValidation.verifyEducation(record);
                    logger.info("prfile resume education vm:{}",vm.isPass()+":"+vm.getResult());
					if(vm.isPass()) {
						this.EducationMaxLimit(record);
						educationRecords.add(record);
					}
				}
			});
		}
		return educationRecords;
	}
	/*
	   @author  zzt
       @param record  ProfileEducationRecord
	  处理教育经历的一些字段的最大长度限制
	 */
	private void EducationMaxLimit(ProfileEducationRecord record){
		if(StringUtils.isNotBlank(record.getDescription())&&record.getDescription().length()>ProfileAttributeLengthLimit.EducationDescription.getLengthLimit()){
			record.setDescription(this.handlerOutLimitString(record.getDescription(),ProfileAttributeLengthLimit.EducationDescription.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getCollegeName())&&record.getCollegeName().length()>ProfileAttributeLengthLimit.EducationCollegeName.getLengthLimit()){
			record.setCollegeName(this.handlerOutLimitString(record.getCollegeName(),ProfileAttributeLengthLimit.EducationCollegeName.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getCollegeLogo())&&record.getCollegeLogo().length()>ProfileAttributeLengthLimit.EducationCollegeLogo.getLengthLimit()){
			record.setCollegeLogo(this.handlerOutLimitString(record.getCollegeLogo(),ProfileAttributeLengthLimit.EducationCollegeLogo.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getMajorName())&&record.getMajorName().length()>ProfileAttributeLengthLimit.EducationMajorName.getLengthLimit()){
			record.setMajorName(this.handlerOutLimitString(record.getMajorName(),ProfileAttributeLengthLimit.EducationMajorName.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getStudyAbroadCountry())&&record.getStudyAbroadCountry().length()>ProfileAttributeLengthLimit.EducationStudyAbroadCountry.getLengthLimit()){
			record.setStudyAbroadCountry(this.handlerOutLimitString(record.getStudyAbroadCountry(),ProfileAttributeLengthLimit.EducationStudyAbroadCountry.getLengthLimit(),DEFAULT_FLAG));
		}
	}

	public List<ProfileCredentialsRecord> mapToCredentialsRecords(List<Map<String, Object>> credentials) {
		List<ProfileCredentialsRecord> credentialRecords = new ArrayList<>();
		if (credentials != null && credentials.size() > 0) {
			credentials.forEach(credential -> {
				ProfileCredentialsRecord record = BeanUtils.MapToRecord(credential, ProfileCredentialsRecord.class);
				ValidationMessage<ProfileCredentialsRecord> vm = ProfileValidation.verifyCredential(record);
				if (record != null && vm.isPass()) {
					this.credentialMaxLimit(record);
					credentialRecords.add(record);
				}
			});
		}
		return credentialRecords;
	}


	/*
	   @author  zzt
       @param record  ProfileCredentialsRecord
	   证书表超长字段处理
	 */
	private void credentialMaxLimit(ProfileCredentialsRecord record){
		if(StringUtils.isNotBlank(record.getName())&&record.getName().length()>ProfileAttributeLengthLimit.CredentialName.getLengthLimit()){
			record.setName(this.handlerOutLimitString(record.getName(),ProfileAttributeLengthLimit.CredentialName.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getCode())&&record.getCode().length()>ProfileAttributeLengthLimit.CredentialCode.getLengthLimit()){
			record.setCode(this.handlerOutLimitString(record.getCode(),ProfileAttributeLengthLimit.CredentialCode.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getOrganization())&&record.getOrganization().length()>ProfileAttributeLengthLimit.CredentialOrganization.getLengthLimit()){
			record.setOrganization(this.handlerOutLimitString(record.getOrganization(),ProfileAttributeLengthLimit.CredentialOrganization.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getScore())&&record.getScore().length()>ProfileAttributeLengthLimit.CredentialScore.getLengthLimit()){
			record.setScore(this.handlerOutLimitString(record.getScore(),ProfileAttributeLengthLimit.CredentialScore.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getUrl())&&record.getUrl().length()>ProfileAttributeLengthLimit.CredentialURL.getLengthLimit()){
			record.setUrl(this.handlerOutLimitString(record.getUrl(),ProfileAttributeLengthLimit.CredentialURL.getLengthLimit(),DEFAULT_FLAG));
		}
	}

	public List<ProfileAwardsRecord> mapToAwardsRecords(List<Map<String, Object>> awards) {
		List<ProfileAwardsRecord> awardsRecords = new ArrayList<>();
		if (awards != null && awards.size() > 0) {
			Iterator<Map<String, Object>> iterator = awards.iterator();
			while (iterator.hasNext()) {

				ProfileAwardsRecord record = BeanUtils.MapToRecord(iterator.next(), ProfileAwardsRecord.class);
				if (record != null) {
					ValidationMessage<ProfileAwardsRecord> validationMessage = ProfileValidation.verifyAward(record);
					if (!validationMessage.isPass()) {
						continue;
					}
//					if(StringUtils.isNotNullOrEmpty(record.getDescription()) && record.getDescription().length() > Constant.DESCRIPTION_LENGTH) {
//						record.setDescription(record.getDescription().substring(0, Constant.DESCRIPTION_LENGTH));
//					}
					this.awardsMaxLimit(record);
					awardsRecords.add(record);
				}
			}
		}
		return awardsRecords;
	}

	/*
       @author  zzt
       @param record  ProfileAwardsRecord
       处理获取奖项的部分中某些字段的最大长度
     */
	private  void awardsMaxLimit(ProfileAwardsRecord record){
		if(StringUtils.isNotBlank(record.getName())&&record.getName().length()>ProfileAttributeLengthLimit.AwardName.getLengthLimit()){
			record.setName(this.handlerOutLimitString(record.getName(),ProfileAttributeLengthLimit.AwardName.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getDescription())&&record.getDescription().length()>ProfileAttributeLengthLimit.AwardDescription.getLengthLimit()){
			record.setDescription(this.handlerOutLimitString(record.getDescription(),ProfileAttributeLengthLimit.AwardDescription.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getAwardWinningStatus())&&record.getAwardWinningStatus().length()>ProfileAttributeLengthLimit.AwardWinningStatus.getLengthLimit()){
			record.setAwardWinningStatus(this.handlerOutLimitString(record.getAwardWinningStatus(),ProfileAttributeLengthLimit.AwardWinningStatus.getLengthLimit(),DEFAULT_FLAG));
		}
	}

	public List<ProfileAttachmentRecord> mapToAttachmentRecords(List<Map<String, Object>> attachments) {
		List<ProfileAttachmentRecord> attchmentRecords = new ArrayList<>();
		if (attachments != null && attachments.size() > 0) {
			attachments.forEach(attachment -> {
				ProfileAttachmentRecord record = BeanUtils.MapToRecord(attachment, ProfileAttachmentRecord.class);
				if (record != null) {
//					if(StringUtils.isNotNullOrEmpty(record.getDescription()) && record.getDescription().length() > Constant.DESCRIPTION_LENGTH) {
//						record.setDescription(record.getDescription().substring(0, Constant.DESCRIPTION_LENGTH));
//					}
					this.attachmentMaxLimit(record);
					attchmentRecords.add(record);
				}
			});
		}
		return attchmentRecords;
	}
	/*
        @author  zzt
        @param record  ProfileAwardsRecord
        附件超长字段处理
      */
	private void attachmentMaxLimit(ProfileAttachmentRecord record){
		if(StringUtils.isNotBlank(record.getDescription())&&record.getDescription().length()>ProfileAttributeLengthLimit.AttachmentDescription.getLengthLimit()){
			record.setDescription(this.handlerOutLimitString(record.getDescription(),ProfileAttributeLengthLimit.AttachmentDescription.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getPath())&&record.getPath().length()>ProfileAttributeLengthLimit.AttachmentPath.getLengthLimit()){
			record.setPath(this.handlerOutLimitString(record.getPath(),ProfileAttributeLengthLimit.AttachmentPath.getLengthLimit(),DEFAULT_FLAG));
		}
		if(StringUtils.isNotBlank(record.getName())&&record.getName().length()>ProfileAttributeLengthLimit.AttachmentName.getLengthLimit()){
			record.setName(this.handlerOutLimitString(record.getName(),ProfileAttributeLengthLimit.AttachmentName.getLengthLimit(),DEFAULT_FLAG));
		}
	}

	public ProfileBasicRecord  mapToBasicRecord(Map<String, Object> basic, ProfileExtParam extParam) {
		ProfileBasicRecord record = null;
		if (basic != null) {
			record = BeanUtils.MapToRecord(basic, ProfileBasicRecord.class);

			// 领英传过来的国籍是iso_code，需要转换成对应id
			if ( (record.getNationalityCode() == null || record.getNationalityCode() == 0)
					&& (basic.get("iso_code_2") != null || basic.get("iso_code_3") != null) && extParam.getCountryDOList() != null) {

				List<DictCountryDO> countryDOList = extParam.getCountryDOList();

				if (basic.get("iso_code_2") != null) {
					Optional<DictCountryDO> optional = countryDOList
							.stream()
							.filter(dictCountryDO -> dictCountryDO.getIsoCode2().equals(String.valueOf(basic.get("iso_code_2")).toUpperCase()))
							.findAny();
					if (optional.isPresent()) {
						record.setNationalityCode(optional.get().getId());
						record.setNationalityName(optional.get().getName());
					}
				}
				if (basic.get("iso_code_3") != null) {
					Optional<DictCountryDO> optional = countryDOList
							.stream()
							.filter(dictCountryDO -> dictCountryDO.getIsoCode2().equals(String.valueOf(basic.get("iso_code_3")).toUpperCase()))
							.findAny();
					if (optional.isPresent()) {
						record.setNationalityCode(optional.get().getId());
						record.setNationalityName(optional.get().getName());
					}
				}
			}
			ValidationMessage<ProfileBasicRecord> validationMessage = ProfileValidation.verifyBasic(record);
			if (!validationMessage.isPass()) {
                record.setBirth(null);
			}

//			if(StringUtils.isNotNullOrEmpty(record.getSelfIntroduction()) && record.getSelfIntroduction().length() > Constant.DESCRIPTION_LENGTH) {
//				record.setSelfIntroduction(record.getSelfIntroduction().substring(0, Constant.DESCRIPTION_LENGTH));
//			}
			this.basicMaxLimit(record);
			return record;
		}
		return record;
	}

	/*
        @author  zzt
        @param record  ProfileAwardsRecord
        处理basic中的超长字段
      */
	private void basicMaxLimit(ProfileBasicRecord record){
	    logger.info("record :{}",record);
	    if(record != null ) {
            if (StringUtils.isNotBlank(record.getName()) && record.getName().length() > ProfileAttributeLengthLimit.BasicName.getLengthLimit()) {
                record.setName(this.handlerOutLimitString(record.getName(), ProfileAttributeLengthLimit.BasicName.getLengthLimit(), DEFAULT_FLAG));
            }
            if (StringUtils.isNotBlank(record.getNationalityName()) && record.getNationalityName().length() > ProfileAttributeLengthLimit.BasicNationalityName.getLengthLimit()) {
                record.setNationalityName(this.handlerOutLimitString(record.getNationalityName(), ProfileAttributeLengthLimit.BasicNationalityName.getLengthLimit(), DEFAULT_FLAG));
            }
            if (StringUtils.isNotBlank(record.getCityName()) && record.getCityName().length() > ProfileAttributeLengthLimit.BasicCityName.getLengthLimit()) {
                record.setCityName(this.handlerOutLimitString(record.getCityName(), ProfileAttributeLengthLimit.BasicCityName.getLengthLimit(), DEFAULT_FLAG));
            }
            if (StringUtils.isNotBlank(record.getWeixin()) && record.getWeixin().length() > ProfileAttributeLengthLimit.BasicWeiXin.getLengthLimit()) {
                record.setWeixin(this.handlerOutLimitString(record.getWeixin(), ProfileAttributeLengthLimit.BasicWeiXin.getLengthLimit(), DEFAULT_FLAG));
            }
            if (StringUtils.isNotBlank(record.getQq()) && record.getQq().length() > ProfileAttributeLengthLimit.BasicQQ.getLengthLimit()) {
                record.setQq(this.handlerOutLimitString(record.getQq(), ProfileAttributeLengthLimit.BasicQQ.getLengthLimit(), DEFAULT_FLAG));
            }
            if (StringUtils.isNotBlank(record.getMotto()) && record.getMotto().length() > ProfileAttributeLengthLimit.BasicMotto.getLengthLimit()) {
                record.setMotto(this.handlerOutLimitString(record.getMotto(), ProfileAttributeLengthLimit.BasicMotto.getLengthLimit(), DEFAULT_FLAG));
            }
            if (StringUtils.isNotBlank(record.getSelfIntroduction()) && record.getSelfIntroduction().length() > ProfileAttributeLengthLimit.BasicSelfIntroduction.getLengthLimit()) {
                record.setSelfIntroduction(this.handlerOutLimitString(record.getSelfIntroduction(), ProfileAttributeLengthLimit.BasicSelfIntroduction.getLengthLimit(), DEFAULT_FLAG));
            }
        }
	}

	public ProfileProfileRecord mapToProfileRecord(Map<String, Object> profile) {
		ProfileProfileRecord record = null;
		if (profile != null && (profile.get("user_id") != null || profile.get("userId") != null) ) {
			record = new ProfileProfileRecord();
			record.setUuid((String) profile.get("uuid"));
			if (profile.get("lang") != null) {
				byte lang = ((Integer)profile.get("lang")).byteValue();
				record.setLang(lang);
			}
			if (profile.get("source") != null) {
				record.setSource((Integer) profile.get("source"));
			}
			if (profile.get("completeness") != null) {
				byte completeness = ((Integer)profile.get("completeness")).byteValue();
				record.setCompleteness(completeness);
			}
			if (profile.get("userId") != null) {
				record.setUserId((Integer) profile.get("userId"));
			} else {
				record.setUserId((Integer) profile.get("user_id"));
			}
			if (profile.get("disable") != null) {
				record.setDisable(BeanUtils.converToByte(profile.get("disable")));
			} else {
				record.setDisable((byte)(1));
			}
			if(profile.get("origin") != null) {
				record.setOrigin((String)profile.get("origin"));
			}
			return record;
		}
		return record;
	}

	public List<Map<String, Object>> buildOthers(ProfileProfileRecord profileRecord, List<ProfileOtherRecord> records) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (profileRecord != null && records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("profile_id", record.getProfileId().intValue());
					map.put("other", record.getOther());
					if (record.getCreateTime() != null) {
						map.put("create_time", DateUtils.dateToShortTime(record.getCreateTime()));
					}
					if (record.getUpdateTime() != null) {
						map.put("update_time", DateUtils.dateToShortTime(record.getUpdateTime()));
					}
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	public List<Map<String, Object>> buildImports(ProfileProfileRecord profileRecord,
												  List<ProfileImportRecord> records) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (profileRecord != null && records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("source", record.getSource().intValue());
					if (record.getLastUpdateTime() != null) {
						map.put("last_update_time", DateUtils.dateToShortTime(record.getLastUpdateTime()));
					}
					map.put("account_id", record.getAccountId());
					map.put("resume_id", record.getResumeId());
					map.put("user_name", record.getUserName());
					if (record.getCreateTime() != null) {
						map.put("create_time", DateUtils.dateToShortTime(record.getCreateTime()));
					}
					if (record.getUpdateTime() != null) {
						map.put("update_time", DateUtils.dateToShortTime(record.getUpdateTime()));
					}
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	public List<Map<String, Object>> buildAttachments(ProfileProfileRecord profileRecord,
													  List<ProfileAttachmentRecord> records) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			if (records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					if (record.getProfileId() != null) {
						map.put("profile_id", record.getProfileId().intValue());
					}
					map.put("name", record.getName());
					map.put("path", record.getPath());
					if (record.getCreateTime() != null) {
						map.put("create_time", DateUtils.dateToShortTime(record.getCreateTime()));
					}
					if (record.getUpdateTime() != null) {
						map.put("update_time", DateUtils.dateToShortTime(record.getUpdateTime()));
					}
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	public UserUserRecord mapToUserUserRecord(Map<String, Object> map) {
		UserUserRecord record = null;
		if (map != null) {
			record = BeanUtils.MapToRecord(map, UserUserRecord.class);
			return record;
		}
		return record;
	}

	public List<Map<String, Object>> buildsIntentions(ProfileProfileRecord profileRecord, Query query,
													  List<DictConstantRecord> constantRecords, ProfileIntentionDao intentionDao, ProfileIntentionCityDao intentionCityDao,
													  ProfileIntentionIndustryDao intentionIndustryDao, ProfileIntentionPositionDao intentionPositionDao, DictCityDao dictCityDao,
													  DictIndustryDao dictIndustryDao, DictPositionDao dictPositionDao) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileIntentionRecord> records = intentionDao.getRecords(query);
			if (records != null && records.size() > 0) {
				QueryUtil dictQuery = new QueryUtil();
				dictQuery.setPageSize(Integer.MAX_VALUE);
				List<DictCityRecord> dictCities = dictCityDao.getRecords(dictQuery);
				List<DictIndustryRecord> dictIndustries = dictIndustryDao.getRecords(dictQuery);
				List<DictPositionRecord> dictPositions = dictPositionDao.getRecords(dictQuery);
				List<Integer> intentionIds = new ArrayList<>();
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<>();
					map.put("id", record.getId());
					map.put("worktype", record.getWorktype().intValue());
					map.put("worktype_name", "");
					for (DictConstantRecord constantRecord : constantRecords) {
						if (constantRecord.getParentCode() == 3105
								&& constantRecord.getCode() == record.getWorktype().intValue()) {
							map.put("worktype_name", constantRecord.getName());
							break;
						}
					}
					map.put("workstate", record.getWorkstate().intValue());
					map.put("workstate_name", "");
					for (DictConstantRecord constantRecord : constantRecords) {
						if (constantRecord.getParentCode() == 3102
								&& constantRecord.getCode() == record.getWorkstate().intValue()) {
							map.put("workstate_name", constantRecord.getName());
							break;
						}
					}
					map.put("salary_code", record.getSalaryCode().intValue());
					map.put("salary_code_name", "");
					for (DictConstantRecord constantRecord : constantRecords) {
						if (constantRecord.getParentCode() == 3114
								&& constantRecord.getCode() == record.getSalaryCode().intValue()) {
							map.put("salary_code_name", constantRecord.getName());
							break;
						}
					}
					map.put("tag", record.getTag());
					map.put("consider_venture_company_opportunities",
							record.getConsiderVentureCompanyOpportunities().intValue());
					map.put("consider_venture_company_opportunities_name", "");
					for (DictConstantRecord constantRecord : constantRecords) {
						if (constantRecord.getParentCode() == 3120
								&& constantRecord.getCode() == record.getSalaryCode().intValue()) {
							map.put("consider_venture_company_opportunities_name", constantRecord.getName());
							break;
						}
					}
					intentionIds.add(record.getId());
					list.add(map);
				});
				List<ProfileIntentionCityRecord> cityRecords = intentionCityDao.getIntentionCities(intentionIds);
				List<ProfileIntentionIndustryRecord> industryRecords = intentionIndustryDao
						.getIntentionIndustries(intentionIds);
				List<ProfileIntentionPositionRecord> positionRecords = intentionPositionDao
						.getIntentionPositions(intentionIds);
				list.forEach(map -> {
					if (cityRecords != null) {
						List<Map<String, Object>> cities = new ArrayList<>();
						cityRecords.stream().filter(ProfileUtils::activeIntentionCity).forEach(cityRecord -> {
							Map<String, Object> cityMap = new HashMap<>();
							cityMap.put("city_code", cityRecord.getCityCode());
							if(StringUtils.isBlank(cityRecord.getCityName())) {
								for(DictCityRecord dictCity : dictCities) {
									if(cityRecord.getCityCode().intValue() == dictCity.getCode().intValue()) {
										cityMap.put("city_name", dictCity.getName());
										break;
									}
								}
							} else {
								cityMap.put("city_name", cityRecord.getCityName());
							}
							cities.add(cityMap);
						});
						map.put("cities", cities);
					}
					if (industryRecords != null) {
						List<Map<String, Object>> industries = new ArrayList<>();
						industryRecords.stream().filter(ProfileUtils::activeIntentionIndustry).forEach(record -> {
							Map<String, Object> industryMap = new HashMap<>();
							industryMap.put("industry_code", record.getIndustryCode());
							if(StringUtils.isBlank(record.getIndustryName())) {

								for(DictIndustryRecord dictIndustry : dictIndustries) {
									if(dictIndustry.getCode().intValue() == record.getIndustryCode().intValue()) {
										industryMap.put("industry_name", dictIndustry.getName());
										break;
									}
								}
							} else {
								industryMap.put("industry_name", record.getIndustryName());
							}
							industries.add(industryMap);
						});
						map.put("industries", industries);
					}
					if (positionRecords != null) {
						List<Map<String, Object>> positions = new ArrayList<>();
						positionRecords.stream().filter(ProfileUtils::activeIntentionPosition).forEach(record -> {
							Map<String, Object> positionMap = new HashMap<>();
							if(StringUtils.isBlank(record.getPositionName())) {
								for(DictPositionRecord dictPosition : dictPositions) {
									if(dictPosition.getCode().intValue() == record.getPositionCode().intValue()) {
										positionMap.put("position_name", dictPosition.getName());
										break;
									}
								}
							} else {
								positionMap.put("position_name", record.getPositionName());
							}
							positionMap.put("position_code", record.getPositionCode().intValue());
							positions.add(positionMap);
						});
						map.put("positions", positions);
					}
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	/**
	 * 判断期望城市是否是有效的信息（线上存在一些城市code 为0 或者为空字符串并且城市名称也是为空字符串的无效数据）
	 * @param cityRecord 期望城市
	 * @return true 有效的数据；false 无效数据
	 */
	public static boolean activeIntentionCity(ProfileIntentionCityRecord cityRecord) {
		return  (cityRecord.getCityCode() != null && cityRecord.getCityCode() > 0)
				|| StringUtils.isNotBlank(cityRecord.getCityName());
	}

	/**
	 * 判断期望行业是否是有效信息（同期望城市）
	 * @param record 期望职能数据
	 * @return true 有效数据；false 无效数据
	 */
	public static boolean activeIntentionIndustry(ProfileIntentionIndustryRecord record) {
		return (record.getIndustryCode() != null && record.getIndustryCode() > 0)
				|| StringUtils.isNotBlank(record.getIndustryName());
	}

	public static boolean activeIntentionPosition(ProfileIntentionPositionRecord record) {
		return (record.getPositionCode() != null && record.getPositionCode() > 0)
				|| StringUtils.isNotBlank(record.getPositionName());
	}

	/**
	 * 更新用户信息
	 * @param userRecord 数据持久化的用户信息
	 * @param basicRecord 解析出来的简历基本信息
	 * @param crawlerUser 解析出来的用户信息
	 */
	public void updateUser(UserUserRecord userRecord, ProfileBasicRecord basicRecord, UserUserRecord crawlerUser) {
		if(userRecord != null && crawlerUser != null) {
			if ((userRecord.getMobile() == null || userRecord.getMobile() == 0) && crawlerUser != null && crawlerUser.getMobile() != null) {
				userRecord.setMobile(crawlerUser.getMobile());
			}
			if (StringUtils.isBlank(userRecord.getName()) && crawlerUser != null && StringUtils.isNotBlank(crawlerUser.getName())) {
				userRecord.setName(crawlerUser.getName());
			}
			if (StringUtils.isBlank(userRecord.getHeadimg()) && crawlerUser != null && StringUtils.isNotBlank(crawlerUser.getHeadimg())) {
				userRecord.setHeadimg(crawlerUser.getHeadimg());
			}
			if (StringUtils.isBlank(userRecord.getEmail()) && crawlerUser != null && StringUtils.isNotBlank(crawlerUser.getEmail())) {
				userRecord.setEmail(crawlerUser.getEmail());
			}
		}
		if(userRecord != null && basicRecord != null) {
			if (StringUtils.isBlank(userRecord.getName()) && basicRecord != null && StringUtils.isNotBlank(basicRecord.getName())) {
				userRecord.setName(basicRecord.getName());
			}
		}
	}

	public static Pagination getPagination(int totalRow, int pageNo1, int pageSize1, List list) {
		Pagination pagination = new Pagination();
		int pageNo = 1;
		int pageSize = 10;
		if (pageNo1 > 1) {
			pageNo = pageNo1;
		}
		if (pageSize1 > 0) {
			pageSize = pageSize1;
		}
		int totalPage = totalRow / pageSize;
		if (totalRow % pageSize != 0) {
			totalPage++;
		}

		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setTotalPage(totalPage);
		pagination.setTotalRow(totalRow);
		pagination.setResults(list);
		return pagination;
	}

	public static void main(String[] args) {
		JSONObject other = new JSONObject();
		other.put("height", 1);
		String str = JSON.toJSONString(other);
		System.out.println(str);
	}
}
