/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPosition_1113 implements Serializable {

    private static final long serialVersionUID = 2094626271;

    private Integer   id;
    private String    jobnumber;
    private Integer   companyId;
    private String    title;
    private String    province;
    private String    city;
    private String    department;
    private Integer   lJobid;
    private Timestamp publishDate;
    private Timestamp stopDate;
    private String    accountabilities;
    private String    experience;
    private String    requirement;
    private String    salary;
    private String    language;
    private Integer   jobGrade;
    private Byte      status;
    private Integer   visitnum;
    private String    lastvisit;
    private Integer   sourceId;
    private Timestamp updateTime;
    private String    businessGroup;
    private Byte      employmentType;
    private String    hrEmail;
    private String    benefits;
    private Byte      degree;
    private String    feature;
    private Byte      emailNotice;
    private Byte      candidateSource;
    private String    occupation;
    private Integer   isRecom;
    private String    industry;
    private Integer   hongbaoConfigId;
    private Integer   hongbaoConfigRecomId;
    private Integer   hongbaoConfigAppId;
    private Byte      emailResumeConf;
    private Integer   lPostingtargetid;
    private Byte      priority;
    private Integer   shareTplId;
    private String    district;
    private Short     count;
    private Integer   salaryTop;
    private Integer   salaryBottom;
    private Byte      experienceAbove;
    private Byte      degreeAbove;
    private Byte      managementExperience;
    private Byte      gender;
    private Integer   publisher;
    private Integer   appCvConfigId;
    private Short     source;
    private Byte      hbStatus;
    private Integer   childCompanyId;
    private Byte      age;
    private String    majorRequired;
    private String    workAddress;
    private String    keyword;
    private String    reportingTo;
    private Byte      isHiring;
    private Byte      underlings;
    private Byte      languageRequired;
    private Byte      targetIndustry;
    private Byte      currentStatus;
    private Integer   positionCode;
    private Integer   teamId;

    public JobPosition_1113() {}

    public JobPosition_1113(JobPosition_1113 value) {
        this.id = value.id;
        this.jobnumber = value.jobnumber;
        this.companyId = value.companyId;
        this.title = value.title;
        this.province = value.province;
        this.city = value.city;
        this.department = value.department;
        this.lJobid = value.lJobid;
        this.publishDate = value.publishDate;
        this.stopDate = value.stopDate;
        this.accountabilities = value.accountabilities;
        this.experience = value.experience;
        this.requirement = value.requirement;
        this.salary = value.salary;
        this.language = value.language;
        this.jobGrade = value.jobGrade;
        this.status = value.status;
        this.visitnum = value.visitnum;
        this.lastvisit = value.lastvisit;
        this.sourceId = value.sourceId;
        this.updateTime = value.updateTime;
        this.businessGroup = value.businessGroup;
        this.employmentType = value.employmentType;
        this.hrEmail = value.hrEmail;
        this.benefits = value.benefits;
        this.degree = value.degree;
        this.feature = value.feature;
        this.emailNotice = value.emailNotice;
        this.candidateSource = value.candidateSource;
        this.occupation = value.occupation;
        this.isRecom = value.isRecom;
        this.industry = value.industry;
        this.hongbaoConfigId = value.hongbaoConfigId;
        this.hongbaoConfigRecomId = value.hongbaoConfigRecomId;
        this.hongbaoConfigAppId = value.hongbaoConfigAppId;
        this.emailResumeConf = value.emailResumeConf;
        this.lPostingtargetid = value.lPostingtargetid;
        this.priority = value.priority;
        this.shareTplId = value.shareTplId;
        this.district = value.district;
        this.count = value.count;
        this.salaryTop = value.salaryTop;
        this.salaryBottom = value.salaryBottom;
        this.experienceAbove = value.experienceAbove;
        this.degreeAbove = value.degreeAbove;
        this.managementExperience = value.managementExperience;
        this.gender = value.gender;
        this.publisher = value.publisher;
        this.appCvConfigId = value.appCvConfigId;
        this.source = value.source;
        this.hbStatus = value.hbStatus;
        this.childCompanyId = value.childCompanyId;
        this.age = value.age;
        this.majorRequired = value.majorRequired;
        this.workAddress = value.workAddress;
        this.keyword = value.keyword;
        this.reportingTo = value.reportingTo;
        this.isHiring = value.isHiring;
        this.underlings = value.underlings;
        this.languageRequired = value.languageRequired;
        this.targetIndustry = value.targetIndustry;
        this.currentStatus = value.currentStatus;
        this.positionCode = value.positionCode;
        this.teamId = value.teamId;
    }

    public JobPosition_1113(
        Integer   id,
        String    jobnumber,
        Integer   companyId,
        String    title,
        String    province,
        String    city,
        String    department,
        Integer   lJobid,
        Timestamp publishDate,
        Timestamp stopDate,
        String    accountabilities,
        String    experience,
        String    requirement,
        String    salary,
        String    language,
        Integer   jobGrade,
        Byte      status,
        Integer   visitnum,
        String    lastvisit,
        Integer   sourceId,
        Timestamp updateTime,
        String    businessGroup,
        Byte      employmentType,
        String    hrEmail,
        String    benefits,
        Byte      degree,
        String    feature,
        Byte      emailNotice,
        Byte      candidateSource,
        String    occupation,
        Integer   isRecom,
        String    industry,
        Integer   hongbaoConfigId,
        Integer   hongbaoConfigRecomId,
        Integer   hongbaoConfigAppId,
        Byte      emailResumeConf,
        Integer   lPostingtargetid,
        Byte      priority,
        Integer   shareTplId,
        String    district,
        Short     count,
        Integer   salaryTop,
        Integer   salaryBottom,
        Byte      experienceAbove,
        Byte      degreeAbove,
        Byte      managementExperience,
        Byte      gender,
        Integer   publisher,
        Integer   appCvConfigId,
        Short     source,
        Byte      hbStatus,
        Integer   childCompanyId,
        Byte      age,
        String    majorRequired,
        String    workAddress,
        String    keyword,
        String    reportingTo,
        Byte      isHiring,
        Byte      underlings,
        Byte      languageRequired,
        Byte      targetIndustry,
        Byte      currentStatus,
        Integer   positionCode,
        Integer   teamId
    ) {
        this.id = id;
        this.jobnumber = jobnumber;
        this.companyId = companyId;
        this.title = title;
        this.province = province;
        this.city = city;
        this.department = department;
        this.lJobid = lJobid;
        this.publishDate = publishDate;
        this.stopDate = stopDate;
        this.accountabilities = accountabilities;
        this.experience = experience;
        this.requirement = requirement;
        this.salary = salary;
        this.language = language;
        this.jobGrade = jobGrade;
        this.status = status;
        this.visitnum = visitnum;
        this.lastvisit = lastvisit;
        this.sourceId = sourceId;
        this.updateTime = updateTime;
        this.businessGroup = businessGroup;
        this.employmentType = employmentType;
        this.hrEmail = hrEmail;
        this.benefits = benefits;
        this.degree = degree;
        this.feature = feature;
        this.emailNotice = emailNotice;
        this.candidateSource = candidateSource;
        this.occupation = occupation;
        this.isRecom = isRecom;
        this.industry = industry;
        this.hongbaoConfigId = hongbaoConfigId;
        this.hongbaoConfigRecomId = hongbaoConfigRecomId;
        this.hongbaoConfigAppId = hongbaoConfigAppId;
        this.emailResumeConf = emailResumeConf;
        this.lPostingtargetid = lPostingtargetid;
        this.priority = priority;
        this.shareTplId = shareTplId;
        this.district = district;
        this.count = count;
        this.salaryTop = salaryTop;
        this.salaryBottom = salaryBottom;
        this.experienceAbove = experienceAbove;
        this.degreeAbove = degreeAbove;
        this.managementExperience = managementExperience;
        this.gender = gender;
        this.publisher = publisher;
        this.appCvConfigId = appCvConfigId;
        this.source = source;
        this.hbStatus = hbStatus;
        this.childCompanyId = childCompanyId;
        this.age = age;
        this.majorRequired = majorRequired;
        this.workAddress = workAddress;
        this.keyword = keyword;
        this.reportingTo = reportingTo;
        this.isHiring = isHiring;
        this.underlings = underlings;
        this.languageRequired = languageRequired;
        this.targetIndustry = targetIndustry;
        this.currentStatus = currentStatus;
        this.positionCode = positionCode;
        this.teamId = teamId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobnumber() {
        return this.jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getLJobid() {
        return this.lJobid;
    }

    public void setLJobid(Integer lJobid) {
        this.lJobid = lJobid;
    }

    public Timestamp getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public Timestamp getStopDate() {
        return this.stopDate;
    }

    public void setStopDate(Timestamp stopDate) {
        this.stopDate = stopDate;
    }

    public String getAccountabilities() {
        return this.accountabilities;
    }

    public void setAccountabilities(String accountabilities) {
        this.accountabilities = accountabilities;
    }

    public String getExperience() {
        return this.experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getRequirement() {
        return this.requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getSalary() {
        return this.salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getJobGrade() {
        return this.jobGrade;
    }

    public void setJobGrade(Integer jobGrade) {
        this.jobGrade = jobGrade;
    }

    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getVisitnum() {
        return this.visitnum;
    }

    public void setVisitnum(Integer visitnum) {
        this.visitnum = visitnum;
    }

    public String getLastvisit() {
        return this.lastvisit;
    }

    public void setLastvisit(String lastvisit) {
        this.lastvisit = lastvisit;
    }

    public Integer getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getBusinessGroup() {
        return this.businessGroup;
    }

    public void setBusinessGroup(String businessGroup) {
        this.businessGroup = businessGroup;
    }

    public Byte getEmploymentType() {
        return this.employmentType;
    }

    public void setEmploymentType(Byte employmentType) {
        this.employmentType = employmentType;
    }

    public String getHrEmail() {
        return this.hrEmail;
    }

    public void setHrEmail(String hrEmail) {
        this.hrEmail = hrEmail;
    }

    public String getBenefits() {
        return this.benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public Byte getDegree() {
        return this.degree;
    }

    public void setDegree(Byte degree) {
        this.degree = degree;
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Byte getEmailNotice() {
        return this.emailNotice;
    }

    public void setEmailNotice(Byte emailNotice) {
        this.emailNotice = emailNotice;
    }

    public Byte getCandidateSource() {
        return this.candidateSource;
    }

    public void setCandidateSource(Byte candidateSource) {
        this.candidateSource = candidateSource;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getIsRecom() {
        return this.isRecom;
    }

    public void setIsRecom(Integer isRecom) {
        this.isRecom = isRecom;
    }

    public String getIndustry() {
        return this.industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getHongbaoConfigId() {
        return this.hongbaoConfigId;
    }

    public void setHongbaoConfigId(Integer hongbaoConfigId) {
        this.hongbaoConfigId = hongbaoConfigId;
    }

    public Integer getHongbaoConfigRecomId() {
        return this.hongbaoConfigRecomId;
    }

    public void setHongbaoConfigRecomId(Integer hongbaoConfigRecomId) {
        this.hongbaoConfigRecomId = hongbaoConfigRecomId;
    }

    public Integer getHongbaoConfigAppId() {
        return this.hongbaoConfigAppId;
    }

    public void setHongbaoConfigAppId(Integer hongbaoConfigAppId) {
        this.hongbaoConfigAppId = hongbaoConfigAppId;
    }

    public Byte getEmailResumeConf() {
        return this.emailResumeConf;
    }

    public void setEmailResumeConf(Byte emailResumeConf) {
        this.emailResumeConf = emailResumeConf;
    }

    public Integer getLPostingtargetid() {
        return this.lPostingtargetid;
    }

    public void setLPostingtargetid(Integer lPostingtargetid) {
        this.lPostingtargetid = lPostingtargetid;
    }

    public Byte getPriority() {
        return this.priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public Integer getShareTplId() {
        return this.shareTplId;
    }

    public void setShareTplId(Integer shareTplId) {
        this.shareTplId = shareTplId;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Short getCount() {
        return this.count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

    public Integer getSalaryTop() {
        return this.salaryTop;
    }

    public void setSalaryTop(Integer salaryTop) {
        this.salaryTop = salaryTop;
    }

    public Integer getSalaryBottom() {
        return this.salaryBottom;
    }

    public void setSalaryBottom(Integer salaryBottom) {
        this.salaryBottom = salaryBottom;
    }

    public Byte getExperienceAbove() {
        return this.experienceAbove;
    }

    public void setExperienceAbove(Byte experienceAbove) {
        this.experienceAbove = experienceAbove;
    }

    public Byte getDegreeAbove() {
        return this.degreeAbove;
    }

    public void setDegreeAbove(Byte degreeAbove) {
        this.degreeAbove = degreeAbove;
    }

    public Byte getManagementExperience() {
        return this.managementExperience;
    }

    public void setManagementExperience(Byte managementExperience) {
        this.managementExperience = managementExperience;
    }

    public Byte getGender() {
        return this.gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    public Integer getAppCvConfigId() {
        return this.appCvConfigId;
    }

    public void setAppCvConfigId(Integer appCvConfigId) {
        this.appCvConfigId = appCvConfigId;
    }

    public Short getSource() {
        return this.source;
    }

    public void setSource(Short source) {
        this.source = source;
    }

    public Byte getHbStatus() {
        return this.hbStatus;
    }

    public void setHbStatus(Byte hbStatus) {
        this.hbStatus = hbStatus;
    }

    public Integer getChildCompanyId() {
        return this.childCompanyId;
    }

    public void setChildCompanyId(Integer childCompanyId) {
        this.childCompanyId = childCompanyId;
    }

    public Byte getAge() {
        return this.age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getMajorRequired() {
        return this.majorRequired;
    }

    public void setMajorRequired(String majorRequired) {
        this.majorRequired = majorRequired;
    }

    public String getWorkAddress() {
        return this.workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getReportingTo() {
        return this.reportingTo;
    }

    public void setReportingTo(String reportingTo) {
        this.reportingTo = reportingTo;
    }

    public Byte getIsHiring() {
        return this.isHiring;
    }

    public void setIsHiring(Byte isHiring) {
        this.isHiring = isHiring;
    }

    public Byte getUnderlings() {
        return this.underlings;
    }

    public void setUnderlings(Byte underlings) {
        this.underlings = underlings;
    }

    public Byte getLanguageRequired() {
        return this.languageRequired;
    }

    public void setLanguageRequired(Byte languageRequired) {
        this.languageRequired = languageRequired;
    }

    public Byte getTargetIndustry() {
        return this.targetIndustry;
    }

    public void setTargetIndustry(Byte targetIndustry) {
        this.targetIndustry = targetIndustry;
    }

    public Byte getCurrentStatus() {
        return this.currentStatus;
    }

    public void setCurrentStatus(Byte currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getPositionCode() {
        return this.positionCode;
    }

    public void setPositionCode(Integer positionCode) {
        this.positionCode = positionCode;
    }

    public Integer getTeamId() {
        return this.teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("JobPosition_1113 (");

        sb.append(id);
        sb.append(", ").append(jobnumber);
        sb.append(", ").append(companyId);
        sb.append(", ").append(title);
        sb.append(", ").append(province);
        sb.append(", ").append(city);
        sb.append(", ").append(department);
        sb.append(", ").append(lJobid);
        sb.append(", ").append(publishDate);
        sb.append(", ").append(stopDate);
        sb.append(", ").append(accountabilities);
        sb.append(", ").append(experience);
        sb.append(", ").append(requirement);
        sb.append(", ").append(salary);
        sb.append(", ").append(language);
        sb.append(", ").append(jobGrade);
        sb.append(", ").append(status);
        sb.append(", ").append(visitnum);
        sb.append(", ").append(lastvisit);
        sb.append(", ").append(sourceId);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(businessGroup);
        sb.append(", ").append(employmentType);
        sb.append(", ").append(hrEmail);
        sb.append(", ").append(benefits);
        sb.append(", ").append(degree);
        sb.append(", ").append(feature);
        sb.append(", ").append(emailNotice);
        sb.append(", ").append(candidateSource);
        sb.append(", ").append(occupation);
        sb.append(", ").append(isRecom);
        sb.append(", ").append(industry);
        sb.append(", ").append(hongbaoConfigId);
        sb.append(", ").append(hongbaoConfigRecomId);
        sb.append(", ").append(hongbaoConfigAppId);
        sb.append(", ").append(emailResumeConf);
        sb.append(", ").append(lPostingtargetid);
        sb.append(", ").append(priority);
        sb.append(", ").append(shareTplId);
        sb.append(", ").append(district);
        sb.append(", ").append(count);
        sb.append(", ").append(salaryTop);
        sb.append(", ").append(salaryBottom);
        sb.append(", ").append(experienceAbove);
        sb.append(", ").append(degreeAbove);
        sb.append(", ").append(managementExperience);
        sb.append(", ").append(gender);
        sb.append(", ").append(publisher);
        sb.append(", ").append(appCvConfigId);
        sb.append(", ").append(source);
        sb.append(", ").append(hbStatus);
        sb.append(", ").append(childCompanyId);
        sb.append(", ").append(age);
        sb.append(", ").append(majorRequired);
        sb.append(", ").append(workAddress);
        sb.append(", ").append(keyword);
        sb.append(", ").append(reportingTo);
        sb.append(", ").append(isHiring);
        sb.append(", ").append(underlings);
        sb.append(", ").append(languageRequired);
        sb.append(", ").append(targetIndustry);
        sb.append(", ").append(currentStatus);
        sb.append(", ").append(positionCode);
        sb.append(", ").append(teamId);

        sb.append(")");
        return sb.toString();
    }
}
