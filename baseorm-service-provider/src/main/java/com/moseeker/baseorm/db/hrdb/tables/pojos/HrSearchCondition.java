/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 候选人列表常用筛选项
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrSearchCondition implements Serializable {

    private static final long serialVersionUID = 1896549539;

    private Integer   id;
    private String    name;
    private String    publisher;
    private String    positionId;
    private String    keyword;
    private String    submitTime;
    private String    workYears;
    private String    cityName;
    private String    degree;
    private String    pastPosition;
    private Integer   inLastJobSearchPosition;
    private Integer   minAge;
    private Integer   maxAge;
    private String    intentionCityName;
    private Integer   sex;
    private String    intentionSalaryCode;
    private String    companyName;
    private Integer   inLastJobSearchCompany;
    private Integer   hrAccountId;
    private Timestamp createTime;
    private Integer   updateTime;
    private Integer   type;
    private String    candidateSource;
    private Byte      isPublic;
    private String    origins;
    private Byte      isRecommend;
    private String    tagIds;
    private String    favoriteHrs;
    private String    cityCode;
    private String    intentionCityCode;
    private Integer   positionStatus;
    private String    positionKeyWord;
    private String    pastPositionKeyWord;
    private String    pastCompanyKeyWord;
    private Timestamp startSubmitTime;
    private Timestamp endSubmitTime;
    private String    hasAttachment;
    private String    departmentIds;
    private String    departmentNames;

    public HrSearchCondition() {}

    public HrSearchCondition(HrSearchCondition value) {
        this.id = value.id;
        this.name = value.name;
        this.publisher = value.publisher;
        this.positionId = value.positionId;
        this.keyword = value.keyword;
        this.submitTime = value.submitTime;
        this.workYears = value.workYears;
        this.cityName = value.cityName;
        this.degree = value.degree;
        this.pastPosition = value.pastPosition;
        this.inLastJobSearchPosition = value.inLastJobSearchPosition;
        this.minAge = value.minAge;
        this.maxAge = value.maxAge;
        this.intentionCityName = value.intentionCityName;
        this.sex = value.sex;
        this.intentionSalaryCode = value.intentionSalaryCode;
        this.companyName = value.companyName;
        this.inLastJobSearchCompany = value.inLastJobSearchCompany;
        this.hrAccountId = value.hrAccountId;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.type = value.type;
        this.candidateSource = value.candidateSource;
        this.isPublic = value.isPublic;
        this.origins = value.origins;
        this.isRecommend = value.isRecommend;
        this.tagIds = value.tagIds;
        this.favoriteHrs = value.favoriteHrs;
        this.cityCode = value.cityCode;
        this.intentionCityCode = value.intentionCityCode;
        this.positionStatus = value.positionStatus;
        this.positionKeyWord = value.positionKeyWord;
        this.pastPositionKeyWord = value.pastPositionKeyWord;
        this.pastCompanyKeyWord = value.pastCompanyKeyWord;
        this.startSubmitTime = value.startSubmitTime;
        this.endSubmitTime = value.endSubmitTime;
        this.hasAttachment = value.hasAttachment;
        this.departmentIds = value.departmentIds;
        this.departmentNames = value.departmentNames;
    }

    public HrSearchCondition(
        Integer   id,
        String    name,
        String    publisher,
        String    positionId,
        String    keyword,
        String    submitTime,
        String    workYears,
        String    cityName,
        String    degree,
        String    pastPosition,
        Integer   inLastJobSearchPosition,
        Integer   minAge,
        Integer   maxAge,
        String    intentionCityName,
        Integer   sex,
        String    intentionSalaryCode,
        String    companyName,
        Integer   inLastJobSearchCompany,
        Integer   hrAccountId,
        Timestamp createTime,
        Integer   updateTime,
        Integer   type,
        String    candidateSource,
        Byte      isPublic,
        String    origins,
        Byte      isRecommend,
        String    tagIds,
        String    favoriteHrs,
        String    cityCode,
        String    intentionCityCode,
        Integer   positionStatus,
        String    positionKeyWord,
        String    pastPositionKeyWord,
        String    pastCompanyKeyWord,
        Timestamp startSubmitTime,
        Timestamp endSubmitTime,
        String    hasAttachment,
        String    departmentIds,
        String    departmentNames
    ) {
        this.id = id;
        this.name = name;
        this.publisher = publisher;
        this.positionId = positionId;
        this.keyword = keyword;
        this.submitTime = submitTime;
        this.workYears = workYears;
        this.cityName = cityName;
        this.degree = degree;
        this.pastPosition = pastPosition;
        this.inLastJobSearchPosition = inLastJobSearchPosition;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.intentionCityName = intentionCityName;
        this.sex = sex;
        this.intentionSalaryCode = intentionSalaryCode;
        this.companyName = companyName;
        this.inLastJobSearchCompany = inLastJobSearchCompany;
        this.hrAccountId = hrAccountId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.type = type;
        this.candidateSource = candidateSource;
        this.isPublic = isPublic;
        this.origins = origins;
        this.isRecommend = isRecommend;
        this.tagIds = tagIds;
        this.favoriteHrs = favoriteHrs;
        this.cityCode = cityCode;
        this.intentionCityCode = intentionCityCode;
        this.positionStatus = positionStatus;
        this.positionKeyWord = positionKeyWord;
        this.pastPositionKeyWord = pastPositionKeyWord;
        this.pastCompanyKeyWord = pastCompanyKeyWord;
        this.startSubmitTime = startSubmitTime;
        this.endSubmitTime = endSubmitTime;
        this.hasAttachment = hasAttachment;
        this.departmentIds = departmentIds;
        this.departmentNames = departmentNames;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPositionId() {
        return this.positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSubmitTime() {
        return this.submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getWorkYears() {
        return this.workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDegree() {
        return this.degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPastPosition() {
        return this.pastPosition;
    }

    public void setPastPosition(String pastPosition) {
        this.pastPosition = pastPosition;
    }

    public Integer getInLastJobSearchPosition() {
        return this.inLastJobSearchPosition;
    }

    public void setInLastJobSearchPosition(Integer inLastJobSearchPosition) {
        this.inLastJobSearchPosition = inLastJobSearchPosition;
    }

    public Integer getMinAge() {
        return this.minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getIntentionCityName() {
        return this.intentionCityName;
    }

    public void setIntentionCityName(String intentionCityName) {
        this.intentionCityName = intentionCityName;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIntentionSalaryCode() {
        return this.intentionSalaryCode;
    }

    public void setIntentionSalaryCode(String intentionSalaryCode) {
        this.intentionSalaryCode = intentionSalaryCode;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getInLastJobSearchCompany() {
        return this.inLastJobSearchCompany;
    }

    public void setInLastJobSearchCompany(Integer inLastJobSearchCompany) {
        this.inLastJobSearchCompany = inLastJobSearchCompany;
    }

    public Integer getHrAccountId() {
        return this.hrAccountId;
    }

    public void setHrAccountId(Integer hrAccountId) {
        this.hrAccountId = hrAccountId;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCandidateSource() {
        return this.candidateSource;
    }

    public void setCandidateSource(String candidateSource) {
        this.candidateSource = candidateSource;
    }

    public Byte getIsPublic() {
        return this.isPublic;
    }

    public void setIsPublic(Byte isPublic) {
        this.isPublic = isPublic;
    }

    public String getOrigins() {
        return this.origins;
    }

    public void setOrigins(String origins) {
        this.origins = origins;
    }

    public Byte getIsRecommend() {
        return this.isRecommend;
    }

    public void setIsRecommend(Byte isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getTagIds() {
        return this.tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getFavoriteHrs() {
        return this.favoriteHrs;
    }

    public void setFavoriteHrs(String favoriteHrs) {
        this.favoriteHrs = favoriteHrs;
    }

    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getIntentionCityCode() {
        return this.intentionCityCode;
    }

    public void setIntentionCityCode(String intentionCityCode) {
        this.intentionCityCode = intentionCityCode;
    }

    public Integer getPositionStatus() {
        return this.positionStatus;
    }

    public void setPositionStatus(Integer positionStatus) {
        this.positionStatus = positionStatus;
    }

    public String getPositionKeyWord() {
        return this.positionKeyWord;
    }

    public void setPositionKeyWord(String positionKeyWord) {
        this.positionKeyWord = positionKeyWord;
    }

    public String getPastPositionKeyWord() {
        return this.pastPositionKeyWord;
    }

    public void setPastPositionKeyWord(String pastPositionKeyWord) {
        this.pastPositionKeyWord = pastPositionKeyWord;
    }

    public String getPastCompanyKeyWord() {
        return this.pastCompanyKeyWord;
    }

    public void setPastCompanyKeyWord(String pastCompanyKeyWord) {
        this.pastCompanyKeyWord = pastCompanyKeyWord;
    }

    public Timestamp getStartSubmitTime() {
        return this.startSubmitTime;
    }

    public void setStartSubmitTime(Timestamp startSubmitTime) {
        this.startSubmitTime = startSubmitTime;
    }

    public Timestamp getEndSubmitTime() {
        return this.endSubmitTime;
    }

    public void setEndSubmitTime(Timestamp endSubmitTime) {
        this.endSubmitTime = endSubmitTime;
    }

    public String getHasAttachment() {
        return this.hasAttachment;
    }

    public void setHasAttachment(String hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    public String getDepartmentIds() {
        return this.departmentIds;
    }

    public void setDepartmentIds(String departmentIds) {
        this.departmentIds = departmentIds;
    }

    public String getDepartmentNames() {
        return this.departmentNames;
    }

    public void setDepartmentNames(String departmentNames) {
        this.departmentNames = departmentNames;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrSearchCondition (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(publisher);
        sb.append(", ").append(positionId);
        sb.append(", ").append(keyword);
        sb.append(", ").append(submitTime);
        sb.append(", ").append(workYears);
        sb.append(", ").append(cityName);
        sb.append(", ").append(degree);
        sb.append(", ").append(pastPosition);
        sb.append(", ").append(inLastJobSearchPosition);
        sb.append(", ").append(minAge);
        sb.append(", ").append(maxAge);
        sb.append(", ").append(intentionCityName);
        sb.append(", ").append(sex);
        sb.append(", ").append(intentionSalaryCode);
        sb.append(", ").append(companyName);
        sb.append(", ").append(inLastJobSearchCompany);
        sb.append(", ").append(hrAccountId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(type);
        sb.append(", ").append(candidateSource);
        sb.append(", ").append(isPublic);
        sb.append(", ").append(origins);
        sb.append(", ").append(isRecommend);
        sb.append(", ").append(tagIds);
        sb.append(", ").append(favoriteHrs);
        sb.append(", ").append(cityCode);
        sb.append(", ").append(intentionCityCode);
        sb.append(", ").append(positionStatus);
        sb.append(", ").append(positionKeyWord);
        sb.append(", ").append(pastPositionKeyWord);
        sb.append(", ").append(pastCompanyKeyWord);
        sb.append(", ").append(startSubmitTime);
        sb.append(", ").append(endSubmitTime);
        sb.append(", ").append(hasAttachment);
        sb.append(", ").append(departmentIds);
        sb.append(", ").append(departmentNames);

        sb.append(")");
        return sb.toString();
    }
}
