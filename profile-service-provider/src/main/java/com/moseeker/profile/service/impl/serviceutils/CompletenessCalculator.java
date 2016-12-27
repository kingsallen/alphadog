package com.moseeker.profile.service.impl.serviceutils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.types.UByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.ParamIllegalException;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;

public class CompletenessCalculator {
	
	Logger logger = LoggerFactory.getLogger(CompletenessCalculator.class);

	public int calculateUserUser(UserUserRecord userRecord, UserSettingsRecord settingsRecord, UserWxUserRecord wxuser)
			throws ParamIllegalException {
		int completeness = 0;
		if (userRecord == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_USERRECORD_LOST);
		}
		
//		if (!StringUtils.isNullOrEmpty(userRecord.getUsername())) {
//			completeness += 5;
//		}
		if (!StringUtils.isNullOrEmpty(userRecord.getEmail())) {
			completeness += 5;
		}
//		if (!StringUtils.isNullOrEmpty(userRecord.getHeadimg())) {
//			completeness += 3;
//		} else {
//			if (wxuser != null && !StringUtils.isNullOrEmpty(wxuser.getHeadimgurl())) {
//				completeness += 3;
//			}
//		}
//		if(settingsRecord != null && StringUtils.isNotNullOrEmpty(settingsRecord.getBannerUrl())) {
//			completeness += 1;
//		}
		//修改规则，姓名＋3
		if (!StringUtils.isNullOrEmpty(userRecord.getName())) {
			//completeness += 5;
			completeness += 3;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_USERUSER_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_USERUSER_MAXVALUE;
		}
		return completeness;
	}

	public int calculateProfileBasic(ProfileBasicRecord basicRecord,long mobile) throws ParamIllegalException {
		int completeness = 0;

		if (basicRecord == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEBASIC_LOST);
		}
		//此处修改规则，性别＋1
		if (basicRecord.getGender() != null && basicRecord.getGender().intValue() > 0) {
//			completeness += 3;
			completeness += 1;
		}
		//此处修改规则，国籍＋1
		if ((basicRecord.getNationalityCode() != null && basicRecord.getNationalityCode() > 0)
				|| !StringUtils.isNullOrEmpty(basicRecord.getNationalityName())) {
//			completeness += 2;
			completeness += 1;
		}
		//此处修改规则，座右铭不算入完整度计算
//		if (!StringUtils.isNullOrEmpty(basicRecord.getMotto())) {
//			completeness += 1;
//		}
		//此处修改规则，居住地＋1
		if ((basicRecord.getCityCode() != null && basicRecord.getCityCode() > 0)
				|| !StringUtils.isNullOrEmpty(basicRecord.getCityName())) {
//			completeness += 2;
			completeness += 1;
		}
		if (basicRecord.getBirth() != null) {
			completeness += 3;
		}
		//这里需要添加对手机好的判断，只要手机号和微信号两个中有一个就可以＋5
		if (StringUtils.isNotNullOrEmpty(basicRecord.getWeixin())||mobile!=0) {
//			completeness += 3;
			completeness += 5;
		}

		//此处修改规则qq不算入完整度
//		if (StringUtils.isNotNullOrEmpty(basicRecord.getQq())) {
//			completeness += 3;
//		}
		//此处修改规则，自我介绍＋5
		if (!StringUtils.isNullOrEmpty(basicRecord.getSelfIntroduction())) {
//			completeness += 2;
			completeness += 5;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_BASIC_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_BASIC_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateProfileWorkexps(List<? extends ProfileWorkexpRecord> workexpRecords,List<ProfileEducationRecord> education,Date birth) {
		int completeness = 0;
		if(workexpRecords!=null&&workexpRecords.size()>0){
			if(education!=null&&education.size()>0){
				for(ProfileEducationRecord record:education){
					int isflag=record.getEndUntilNow().intValue();
					if(isflag==1){
						completeness=45;
						break;
					}
				}
				if(completeness>0){
					return completeness;
				}
				//有教育经历的工作经历的建立完整度计算
				completeness=getWorkCompletenessHasEducation(workexpRecords,education,birth);
				
					
								
			}else{
				if(birth!=null){
					//无教育经历的工作经历但是有出生日期的简历完整度计算
					completeness=getWorkCompletenessNoEducation(workexpRecords,birth);
				}else{
					//无教育经历的工作经历无出生日期的简历完整度计算---没有教育经历没有年龄的，有工作经历就给满分
					completeness=45;
				}
			}
		}
//		if(workexpRecords != null && workexpRecords.size() > 0) {
//			for(ProfileWorkexpRecord workexp : workexpRecords) {
//				if(completeness >= Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE) {
//					completeness = Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE;
//					break;
//				}
//				HrCompanyRecord companyRecord = null;
//				for(HrCompanyRecord company : companies) {
//					if(workexp.getCompanyId() != null && workexp.getCompanyId().intValue() == company.getId().intValue()) {
//						companyRecord = company;
//						break;
//					}
//				}
//				completeness = calculateProfileWorkexp(workexp, companyRecord, completeness);
//			}
//		}
		return completeness;
	}
	/*
	 * 有教育经历的，从教育结束后的第二年到目前的前一年，按照每年占的百分比来计算总分
	 * 
	 */
	@SuppressWarnings("deprecation")
	private int getWorkCompletenessHasEducation(List<? extends ProfileWorkexpRecord> workexpRecords,List<ProfileEducationRecord> education,Date birth){
		int completeness=0;
		Date endTime=null;
		SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
		for(ProfileEducationRecord record:education){
			if(record.getEndUntilNow().intValue()==1){
				return 45;
			}
			if(record.getEnd()!=null){
				if(endTime==null){
					endTime=record.getEnd();
				}
				if(endTime.before(record.getEnd())){
					endTime=record.getEnd();
				}
			}
		}
		if(endTime==null){
			return 0;
		}
		String date=format.format(new Date());
		int end=Integer.parseInt(format.format(endTime).split("-")[0]);
		int now=Integer.parseInt(date.toString().split("-")[0]);
		int period=now-2-end;
		int workTime=getWorkTime(workexpRecords);
		if(period<=0){
			period=1;
		}
		if(workTime==0){
			return 0;
		}else{
			completeness= Math.abs(workTime*45/period);
		}	
		if(completeness >= Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE;
		}
		return completeness;
	}
	/*
	 * 没有教育经历有年龄的，从22岁开始到目前的前一年，按照每年占的百分比来计算总分。年龄小于22岁且有工作经历的，直接给满分；
	 */
	private int getWorkCompletenessNoEducation(List<? extends ProfileWorkexpRecord> workexpRecords,Date birth){
		int completeness=0;
		SimpleDateFormat format= new SimpleDateFormat("yyyy-mm-dd");
		int startTime=Integer.parseInt(format.format(birth).split("-")[0])+22;
		String date=format.format(new Date());
		int now=Integer.parseInt(date.split("-")[0]);
		//年龄小于22岁且有工作经历的，直接给满分
		if(startTime>=now){
			completeness=45;
			return completeness;
		}
		//目前开始的前一年减去到22岁的年份得到时间段即为应该参加工作的总时间段
		int period=Math.abs(now-startTime);
		int time=getWorkTime(workexpRecords);
		if(time==0){
			return 0;
		}
		if(period<=0){
			period=1;
		}
		completeness=Math.abs(time*45/period);
		if(completeness >= Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE;
		}
		return completeness;
	}
	private List<Map<String,Integer>> convertList(List<? extends ProfileWorkexpRecord> workexpRecords){
		List<Map<String,Integer>> list=new ArrayList<Map<String,Integer>>();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd");
		for(ProfileWorkexpRecord record:workexpRecords){
			int endutil=record.getEndUntilNow().intValue();
			if(endutil==1){
				java.sql.Date date=new java.sql.Date(new Date().getTime());
				record.setEnd(date);
			}
			if(record.getStart()!=null&&record.getEnd()!=null){
				Map<String,Integer> map=new HashMap<String,Integer>();
				String start=format.format(record.getStart());
				String end=format.format(record.getEnd());
				int start1=Integer.parseInt(start.split("-")[0]);
				int end1=Integer.parseInt(end.split("-")[0]);
				map.put("start",start1);
				map.put("end",end1);
				list.add(map);
			}
		}
		if(list!=null&&list.size()>0){
			Map<String,Integer> temp=null;
			for(int i=0;i<list.size();i++){
				for(int j=i+1;j<list.size();j++){
					Map<String,Integer> map=list.get(i);
					int start1=map.get("start");
					Map<String,Integer> map1=list.get(j);
					int end2=map1.get("end");
					if(end2<=start1){
						temp=map;
						list.remove(i);
						list.add(i, map1);
						list.remove(j);
						list.add(j,temp);
						temp=null;
					}
				}
			}
		}
		return list;
	}
	//获取工作时长，用于计算建立完整度
	private int getWorkTime(List<? extends ProfileWorkexpRecord> workexpRecords){
		int time=0;
		if(workexpRecords!=null&&workexpRecords.size()>0){
			//将内部的工作经历按时间排序
			List<Map<String,Integer>> list=convertList(workexpRecords);
			/*
			 * 按照工作经历获取工作时常
			 */
			int startTime1=0;
			int endtime1=0;
			for(int i=0;i<list.size();i++){
				int start=list.get(i).get("start");
				int end=list.get(i).get("end");
				if(startTime1==0&&endtime1==0){
					startTime1=start;
					endtime1=end;
				}else{
					//如果上一段工作经历的结束时间和下一份工作经历的起始时间在同一年，那么合并两段经历
					if(endtime1==start){
						endtime1=end;
					}else{
					/* 如果上一段工作经历的结束时间和下一份工作经历的起始时间不在同一年，
					 * 那么计算出上一份工作的时长，如果起始和结束在同一年，那么＋1，
					 * 不再同一年，做差,
					 * 并且将startTime1，endtime1设置为本段工作经历的起始和结束时间
					 */
						if(endtime1==startTime1){
							time+=1;
						}else{
							time+=Math.abs(endtime1-startTime1)+1;
						}
						startTime1=start;
						endtime1=end;
					}
				}
			}
			time+=Math.abs(endtime1-startTime1)+1;
			if(time==0){
				time=1;
			}
		}
		return time;
	}
	public int calculateProfileWorkexp(ProfileWorkexpRecord workexpRecord, HrCompanyRecord company, int completeness)
			throws ParamIllegalException {
		if (workexpRecord == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEWORKEXP_LOST);
		}
		if (company != null) {
			if (!StringUtils.isNullOrEmpty(company.getName())) {
				completeness += 3;
			}
			if (!StringUtils.isNullOrEmpty(company.getLogo())) {
				completeness += 1;
			}
		}
		if (!StringUtils.isNullOrEmpty(workexpRecord.getDepartmentName())) {
			completeness += 1;
		}
		if (StringUtils.isNotNullOrEmpty(workexpRecord.getJob())) {
			completeness += 5;
		}
		if (workexpRecord.getStart() != null) {
			completeness += 2;
		}
		if (workexpRecord.getEnd() != null || (workexpRecord.getEndUntilNow() != null
				&& workexpRecord.getEndUntilNow().intValue() == 1)) {
			completeness += 2;
		}
		if (StringUtils.isNotNullOrEmpty(workexpRecord.getDescription())) {
			completeness += 1;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_WORKEXP_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateProfileEducations(List<ProfileEducationRecord> records) throws ParamIllegalException {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileEducationRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_EDUCATION_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_EDUCATION_MAXVALUE;
					break;
				}
				
				completeness = calculateProfileEducation(record, completeness);
			}
		}
		return completeness;
	}

	public int calculateProfileEducation(ProfileEducationRecord record, int completeness) throws ParamIllegalException {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEEDUCATION_LOST);
		}else{
			completeness += 10;
		}
	// 本处规则修改，教育经历只要有一段即给满分10分
//		if (record.getCollegeCode() != null && record.getCollegeCode() > 0) {
//			completeness += 6;
//		} else {
//			if (StringUtils.isNotNullOrEmpty(record.getCollegeName())) {
//				completeness += 5;
//			}
//			if (StringUtils.isNotNullOrEmpty(record.getCollegeLogo())) {
//				completeness += 1;
//			}
//		}
//		if ((StringUtils.isNotNullOrEmpty(record.getMajorCode()) && !record.getMajorCode().equals("0"))
//				|| StringUtils.isNotNullOrEmpty(record.getMajorName())) {
//			completeness += 3;
//		}
//		if (record.getDegree() != null && record.getDegree().intValue() > 0) {
//			completeness += 3;
//		}
//		if (record.getStart() != null) {
//			completeness += 2;
//		}
//		if (record.getEnd() != null || (record.getEndUntilNow() != null && record.getEndUntilNow().intValue() == 1)) {
//			completeness += 2;
//		}
//		if (StringUtils.isNotNullOrEmpty(record.getDescription())) {
//			completeness += 1;
//		}

		if(completeness >= Constant.PROFILER_COMPLETENESS_EDUCATION_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_EDUCATION_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateProjectexps(List<ProfileProjectexpRecord> records,List<? extends ProfileWorkexpRecord> workexpRecords) {
		int completeness = 0;
		//超过八年的，直接算10
		if(workexpRecords!=null&&workexpRecords.size()>0){
			int time=getWorkTime(workexpRecords);
			if(time>=8){
				completeness+=10;
			}
		}
		//没超过八年
		if(completeness==0){
			if(records != null && records.size() > 0) {
				for(ProfileProjectexpRecord record : records) {
					if(completeness >= Constant.PROFILER_COMPLETENESS_PROJECTEXP_MAXVALUE) {
						completeness = Constant.PROFILER_COMPLETENESS_PROJECTEXP_MAXVALUE;
						break;
					}
					try {
						completeness = calculateProjectexp(record, completeness);
					} catch (ParamIllegalException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return completeness;
	}

	public int calculateProjectexp(ProfileProjectexpRecord record,int completeness) throws ParamIllegalException {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEPROJECTEXP_LOST);
		}
//		if (record.getStart() != null) {
//			completeness += 1;
//		}
//		if (record.getEnd() != null || (record.getEndUntilNow() != null && record.getEndUntilNow().intValue() == 1)) {
//			completeness += 1;
//		}
//		if (StringUtils.isNotNullOrEmpty(record.getName())) {
//			completeness += 3;
//		}
//		if (StringUtils.isNotNullOrEmpty(record.getCompanyName())) {
//			completeness += 2;
//		}
//		if (StringUtils.isNotNullOrEmpty(record.getDescription())) {
//			completeness += 1;
//		}
//		if (StringUtils.isNotNullOrEmpty(record.getRole())) {
//			completeness += 3;
//		}
//		if (StringUtils.isNotNullOrEmpty(record.getMember())) {
//			completeness += 2;
//		}
		//修改规则，项目经历，有一段即给10，或者八年以上工作经验的直接给10
			completeness+=10;
			
		if(completeness >= Constant.PROFILER_COMPLETENESS_PROJECTEXP_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_PROJECTEXP_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateLanguages(List<ProfileLanguageRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileLanguageRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_LANGUAGE_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_LANGUAGE_MAXVALUE;
					break;
				}
				try {
					completeness = calculateLanguage(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}

	public int calculateLanguage(ProfileLanguageRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILELANGUAGE_LOST);
		}
		//本处修改规则，语种＋2
		if (StringUtils.isNotNullOrEmpty(record.getName())) {
//			completeness += 3;
			completeness += 2;
		}
//		if (record.getLevel() != null && record.getLevel().intValue() > 0) {
//			completeness += 1;
//		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_LANGUAGE_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_LANGUAGE_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateSkills(List<ProfileSkillRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileSkillRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_SKILL_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_SKILL_MAXVALUE;
					break;
				}
				try {
					completeness = calculateSkill(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}

	public int calculateSkill(ProfileSkillRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILESKILL_LOST);
		}
		if (StringUtils.isNotNullOrEmpty(record.getName())) {
			completeness += 1;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_SKILL_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_SKILL_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateCredentials(List<ProfileCredentialsRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileCredentialsRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_CREDENTIAL_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_CREDENTIAL_MAXVALUE;
					break;
				}
				try {
					completeness = calculateCredential(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}

	public int calculateCredential(ProfileCredentialsRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILECREDENTIALS_LOST);
		}
		//修改完整度规则，证书＋2
		if (StringUtils.isNotNullOrEmpty(record.getName())) {
//			completeness += 1;
			completeness += 2;
		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_CREDENTIAL_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_CREDENTIAL_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateAwards(List<ProfileAwardsRecord> records) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileAwardsRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_AWARD_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_AWARD_MAXVALUE;
					break;
				}
				try {
					completeness = calculateAward(record, completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}

	public int calculateAward(ProfileAwardsRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEAWARDS_LOST);
		}

		if (StringUtils.isNotNullOrEmpty(record.getName())) {
			completeness += 1;
		}
		//奔出修改规则，获奖时间不计入简历完整度
//		if (record.getRewardDate() != null) {
//			completeness += 1;
//		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_AWARD_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_AWARD_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateWorks(List<ProfileWorksRecord> records) {
		int completeness = 0;
		
		if(records != null && records.size() > 0) {
			for(ProfileWorksRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_WORKS_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_WORKS_MAXVALUE;
					break;
				}
				try {
					completeness = calculateWork(record,completeness);
				} catch (ParamIllegalException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return completeness;
	}
	
	public int calculateWork(ProfileWorksRecord record, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILEWORKS_LOST);
		}
		//修改规则，只有有个人网址＋1，其他的不计入到完整度
//		if (StringUtils.isNotNullOrEmpty(record.getCover())) {
//			completeness += 1;
//		}
		if(StringUtils.isNotNullOrEmpty(record.getUrl())) {
			completeness += 1;
		}
//		if(StringUtils.isNotNullOrEmpty(record.getDescription())) {
//			completeness += 1;
//		}
		if(completeness >= Constant.PROFILER_COMPLETENESS_WORKS_MAXVALUE) {
			completeness = Constant.PROFILER_COMPLETENESS_WORKS_MAXVALUE;
		}
		return completeness;
	}
	
	public int calculateIntentions(List<? extends ProfileIntentionRecord> records, List<ProfileIntentionCityRecord> cityRecords, List<ProfileIntentionPositionRecord> positionRecords) {
		int completeness = 0;
		if(records != null && records.size() > 0) {
			for(ProfileIntentionRecord record : records) {
				if(completeness >= Constant.PROFILER_COMPLETENESS_INTENTION_MAXVALUE) {
					completeness = Constant.PROFILER_COMPLETENESS_INTENTION_MAXVALUE;
					break;
				}
				ProfileIntentionCityRecord cityRecord = null;
				ProfileIntentionPositionRecord positionRecord = null;
				if(cityRecords != null && cityRecords.size() > 0) {
					for(ProfileIntentionCityRecord city: cityRecords) {
						if(city.getProfileIntentionId().intValue() == record.getId().intValue()) {
							cityRecord = city;
							break;
						}
					}
				}
				if(positionRecords != null && positionRecords.size() > 0) {
					for(ProfileIntentionPositionRecord position : positionRecords) {
						if(position.getProfileIntentionId().intValue() == record.getId().intValue()) {
							positionRecord = position;
							break;
						}
					}
				}
				completeness = calculateIntention(record, cityRecord, positionRecord, completeness);
			}
		}
		return completeness;
	}

	public int calculateIntention(ProfileIntentionRecord record, ProfileIntentionCityRecord cityRecord,
			ProfileIntentionPositionRecord positionRecord, int completeness) {
		if (record == null) {
			throw new ParamIllegalException(Constant.EXCEPTION_PROFILINTENTION_LOST);
		}
		if (record.getSalaryCode() != null && record.getSalaryCode().intValue() > 0) {
			completeness += 1;
		}
		if (record.getWorktype() != null && record.getWorktype().intValue() > 0) {
			completeness += 1;
		}
		if (cityRecord != null) {
			if ((cityRecord.getCityCode() != null && cityRecord.getCityCode().intValue() > 0)
					|| StringUtils.isNotNullOrEmpty(cityRecord.getCityName())) {
				completeness += 1;
			}
		}
		if (positionRecord != null) {
			if ((positionRecord.getPositionCode() != null && positionRecord.getPositionCode().intValue() > 0)
					|| StringUtils.isNotNullOrEmpty(positionRecord.getPositionName())) {
				completeness += 1;
			}
		}
		return completeness;
	}
}