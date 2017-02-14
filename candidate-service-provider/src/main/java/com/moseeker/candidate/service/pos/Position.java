package com.moseeker.candidate.service.pos;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.service.JobDBDao;
import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.thrift.TException;

import java.lang.reflect.InvocationTargetException;

/**
 * 职位
 * Created by jack on 10/02/2017.
 */
public class Position extends Model {

    JobDBDao.Iface jobdb = ServiceManager.SERVICEMANAGER
            .getService(JobDBDao.Iface.class);

    private int id; // optional
    private java.lang.String jobnumber; // optional
    private int companyId; // optional
    private java.lang.String title; // optional
    private java.lang.String city; // optional
    private java.lang.String department; // optional
    private int lJobid; // optional
    private java.lang.String publishDate; // optional
    private java.lang.String stopDate; // optional
    private java.lang.String accountabilities; // optional
    private java.lang.String experience; // optional
    private java.lang.String requirement; // optional
    private java.lang.String salary; // optional
    private java.lang.String language; // optional
    private java.lang.String jobGrade; // optional
    private int status; // optional
    private int visitnum; // optional
    private java.lang.String lastvisit; // optional
    private int source_id; // optional
    private java.lang.String updateTime; // optional
    private java.lang.String businessGroup; // optional
    private byte employmentType; // optional
    private java.lang.String hrEmail; // optional
    private java.lang.String benefits; // optional
    private byte degree; // optional
    private java.lang.String feature; // optional
    private boolean emailNotice; // optional
    private byte candidateSource; // optional
    private java.lang.String occupation; // optional
    private boolean isRecom; // optional
    private java.lang.String industry; // optional
    private int hongbaoConfigId; // optional
    private int hongbaoConfigRecomId; // optional
    private int hongbaoConfigAppId; // optional
    private boolean emailResumeConf; // optional
    private int lPostingTargetId; // optional
    private byte priority; // optional
    private int shareTplId; // optional
    private java.lang.String district; // optional
    private short count; // optional
    private int salaryTop; // optional
    private int salaryBottom; // optional
    private boolean experienceAbove; // optional
    private boolean degreeAbove; // optional
    private boolean managementExperience; // optional
    private byte gender; // optional
    private int publisher; // optional
    private int appCvConfigId; // optional
    private short source; // optional
    private byte hbStatus; // optional
    private int childCompanyId; // optional
    private byte age; // optional
    private java.lang.String majorRequired; // optional
    private java.lang.String workAddress; // optional
    private java.lang.String keyword; // optional
    private java.lang.String reportingTo; // optional
    private boolean isHiring; // optional
    private short underlings; // optional
    private boolean languageRequired; // optional
    private int targetIndustry; // optional
    private byte currentStatus; // optional
    private short positionCode; // optional
    private int teamId; // optional


    public Position(int ID) {
        this.id = id;
    }


    @Override
    public void loadFromDB() {
        this.persist = true;
        QueryUtil query = new QueryUtil();
        query.addEqualFilter("id", String.valueOf(this.id));
        try {
            JobPositionDO positionDTO = jobdb.getPosition(query);
            if(positionDTO != null && positionDTO.getId() > 0) {
                this.exist = true;
                try {
                    BeanUtils.copyProperties(this, positionDTO);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                this.exist = false;
            }
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateToDB() {

    }

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getlJobid() {
        return lJobid;
    }

    public void setlJobid(int lJobid) {
        this.lJobid = lJobid;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getAccountabilities() {
        return accountabilities;
    }

    public void setAccountabilities(String accountabilities) {
        this.accountabilities = accountabilities;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(String jobGrade) {
        this.jobGrade = jobGrade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVisitnum() {
        return visitnum;
    }

    public void setVisitnum(int visitnum) {
        this.visitnum = visitnum;
    }

    public String getLastvisit() {
        return lastvisit;
    }

    public void setLastvisit(String lastvisit) {
        this.lastvisit = lastvisit;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBusinessGroup() {
        return businessGroup;
    }

    public void setBusinessGroup(String businessGroup) {
        this.businessGroup = businessGroup;
    }

    public byte getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(byte employmentType) {
        this.employmentType = employmentType;
    }

    public String getHrEmail() {
        return hrEmail;
    }

    public void setHrEmail(String hrEmail) {
        this.hrEmail = hrEmail;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public byte getDegree() {
        return degree;
    }

    public void setDegree(byte degree) {
        this.degree = degree;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public boolean isEmailNotice() {
        return emailNotice;
    }

    public void setEmailNotice(boolean emailNotice) {
        this.emailNotice = emailNotice;
    }

    public byte getCandidateSource() {
        return candidateSource;
    }

    public void setCandidateSource(byte candidateSource) {
        this.candidateSource = candidateSource;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public boolean isRecom() {
        return isRecom;
    }

    public void setRecom(boolean recom) {
        isRecom = recom;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getHongbaoConfigId() {
        return hongbaoConfigId;
    }

    public void setHongbaoConfigId(int hongbaoConfigId) {
        this.hongbaoConfigId = hongbaoConfigId;
    }

    public int getHongbaoConfigRecomId() {
        return hongbaoConfigRecomId;
    }

    public void setHongbaoConfigRecomId(int hongbaoConfigRecomId) {
        this.hongbaoConfigRecomId = hongbaoConfigRecomId;
    }

    public int getHongbaoConfigAppId() {
        return hongbaoConfigAppId;
    }

    public void setHongbaoConfigAppId(int hongbaoConfigAppId) {
        this.hongbaoConfigAppId = hongbaoConfigAppId;
    }

    public boolean isEmailResumeConf() {
        return emailResumeConf;
    }

    public void setEmailResumeConf(boolean emailResumeConf) {
        this.emailResumeConf = emailResumeConf;
    }

    public int getlPostingTargetId() {
        return lPostingTargetId;
    }

    public void setlPostingTargetId(int lPostingTargetId) {
        this.lPostingTargetId = lPostingTargetId;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public int getShareTplId() {
        return shareTplId;
    }

    public void setShareTplId(int shareTplId) {
        this.shareTplId = shareTplId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public short getCount() {
        return count;
    }

    public void setCount(short count) {
        this.count = count;
    }

    public int getSalaryTop() {
        return salaryTop;
    }

    public void setSalaryTop(int salaryTop) {
        this.salaryTop = salaryTop;
    }

    public int getSalaryBottom() {
        return salaryBottom;
    }

    public void setSalaryBottom(int salaryBottom) {
        this.salaryBottom = salaryBottom;
    }

    public boolean isExperienceAbove() {
        return experienceAbove;
    }

    public void setExperienceAbove(boolean experienceAbove) {
        this.experienceAbove = experienceAbove;
    }

    public boolean isDegreeAbove() {
        return degreeAbove;
    }

    public void setDegreeAbove(boolean degreeAbove) {
        this.degreeAbove = degreeAbove;
    }

    public boolean isManagementExperience() {
        return managementExperience;
    }

    public void setManagementExperience(boolean managementExperience) {
        this.managementExperience = managementExperience;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    public int getAppCvConfigId() {
        return appCvConfigId;
    }

    public void setAppCvConfigId(int appCvConfigId) {
        this.appCvConfigId = appCvConfigId;
    }

    public short getSource() {
        return source;
    }

    public void setSource(short source) {
        this.source = source;
    }

    public byte getHbStatus() {
        return hbStatus;
    }

    public void setHbStatus(byte hbStatus) {
        this.hbStatus = hbStatus;
    }

    public int getChildCompanyId() {
        return childCompanyId;
    }

    public void setChildCompanyId(int childCompanyId) {
        this.childCompanyId = childCompanyId;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getMajorRequired() {
        return majorRequired;
    }

    public void setMajorRequired(String majorRequired) {
        this.majorRequired = majorRequired;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(String reportingTo) {
        this.reportingTo = reportingTo;
    }

    public boolean isHiring() {
        return isHiring;
    }

    public void setHiring(boolean hiring) {
        isHiring = hiring;
    }

    public short getUnderlings() {
        return underlings;
    }

    public void setUnderlings(short underlings) {
        this.underlings = underlings;
    }

    public boolean isLanguageRequired() {
        return languageRequired;
    }

    public void setLanguageRequired(boolean languageRequired) {
        this.languageRequired = languageRequired;
    }

    public int getTargetIndustry() {
        return targetIndustry;
    }

    public void setTargetIndustry(int targetIndustry) {
        this.targetIndustry = targetIndustry;
    }

    public byte getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(byte currentStatus) {
        this.currentStatus = currentStatus;
    }

    public short getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(short positionCode) {
        this.positionCode = positionCode;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
