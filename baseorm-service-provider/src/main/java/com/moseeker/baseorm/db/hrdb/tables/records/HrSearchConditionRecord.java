/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrSearchCondition;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


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
public class HrSearchConditionRecord extends UpdatableRecordImpl<HrSearchConditionRecord> {

    private static final long serialVersionUID = 1614713910;

    /**
     * Setter for <code>hrdb.hr_search_condition.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.name</code>. 常用搜索条件名称，长度不超过12个字符
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.name</code>. 常用搜索条件名称，长度不超过12个字符
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.publisher</code>. 发布人id(user_hr_account.id)，多个发布人用,隔开
     */
    public void setPublisher(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.publisher</code>. 发布人id(user_hr_account.id)，多个发布人用,隔开
     */
    public String getPublisher() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.position_id</code>. 职位id,多个职位用,隔开
     */
    public void setPositionId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.position_id</code>. 职位id,多个职位用,隔开
     */
    public String getPositionId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.keyword</code>. 关键字
     */
    public void setKeyword(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.keyword</code>. 关键字
     */
    public String getKeyword() {
        return (String) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.submit_time</code>. 投递时间
     */
    public void setSubmitTime(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.submit_time</code>. 投递时间
     */
    public String getSubmitTime() {
        return (String) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.work_years</code>. 工作年限、工龄
     */
    public void setWorkYears(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.work_years</code>. 工作年限、工龄
     */
    public String getWorkYears() {
        return (String) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.city_name</code>. 现居住地
     */
    public void setCityName(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.city_name</code>. 现居住地
     */
    public String getCityName() {
        return (String) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.degree</code>. 学历
     */
    public void setDegree(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.degree</code>. 学历
     */
    public String getDegree() {
        return (String) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.past_position</code>. 曾任职务
     */
    public void setPastPosition(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.past_position</code>. 曾任职务
     */
    public String getPastPosition() {
        return (String) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.in_last_job_search_position</code>. 是否只在最近一份工作中搜索曾任职务(0:否，1:是)
     */
    public void setInLastJobSearchPosition(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.in_last_job_search_position</code>. 是否只在最近一份工作中搜索曾任职务(0:否，1:是)
     */
    public Integer getInLastJobSearchPosition() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.min_age</code>. 最小年龄
     */
    public void setMinAge(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.min_age</code>. 最小年龄
     */
    public Integer getMinAge() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.max_age</code>. 最大年龄
     */
    public void setMaxAge(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.max_age</code>. 最大年龄
     */
    public Integer getMaxAge() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.intention_city_name</code>. 期望工作地
     */
    public void setIntentionCityName(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.intention_city_name</code>. 期望工作地
     */
    public String getIntentionCityName() {
        return (String) get(13);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.sex</code>. 性别
     */
    public void setSex(Integer value) {
        set(14, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.sex</code>. 性别
     */
    public Integer getSex() {
        return (Integer) get(14);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.intention_salary_code</code>. 期望薪资
     */
    public void setIntentionSalaryCode(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.intention_salary_code</code>. 期望薪资
     */
    public String getIntentionSalaryCode() {
        return (String) get(15);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.company_name</code>. 公司名称
     */
    public void setCompanyName(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.company_name</code>. 公司名称
     */
    public String getCompanyName() {
        return (String) get(16);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.in_last_job_search_company</code>. 是否只在最近一份工作中搜索公司名称（0:否，1:是）
     */
    public void setInLastJobSearchCompany(Integer value) {
        set(17, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.in_last_job_search_company</code>. 是否只在最近一份工作中搜索公司名称（0:否，1:是）
     */
    public Integer getInLastJobSearchCompany() {
        return (Integer) get(17);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.hr_account_id</code>. 创建人id(user_hr_account.id)
     */
    public void setHrAccountId(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.hr_account_id</code>. 创建人id(user_hr_account.id)
     */
    public Integer getHrAccountId() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(19, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(19);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.update_time</code>. 简历更新时间选项（0：不限，1：最近一周，2：最近两周，3：最近一个月）
     */
    public void setUpdateTime(Integer value) {
        set(20, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.update_time</code>. 简历更新时间选项（0：不限，1：最近一周，2：最近两周，3：最近一个月）
     */
    public Integer getUpdateTime() {
        return (Integer) get(20);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.type</code>. 类型（0：候选人列表筛选条件，1：人才库列表筛选条件）
     */
    public void setType(Integer value) {
        set(21, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.type</code>. 类型（0：候选人列表筛选条件，1：人才库列表筛选条件）
     */
    public Integer getType() {
        return (Integer) get(21);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.candidate_source</code>. 0，社招，1，校招，2定向招聘
     */
    public void setCandidateSource(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.candidate_source</code>. 0，社招，1，校招，2定向招聘
     */
    public String getCandidateSource() {
        return (String) get(22);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.is_public</code>. 是否公开 1是
     */
    public void setIsPublic(Byte value) {
        set(23, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.is_public</code>. 是否公开 1是
     */
    public Byte getIsPublic() {
        return (Byte) get(23);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.origins</code>. 简历来源，申请来源，是否上传，多个之间用,隔开
     */
    public void setOrigins(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.origins</code>. 简历来源，申请来源，是否上传，多个之间用,隔开
     */
    public String getOrigins() {
        return (String) get(24);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.is_recommend</code>. 是否内推 1是
     */
    public void setIsRecommend(Byte value) {
        set(25, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.is_recommend</code>. 是否内推 1是
     */
    public Byte getIsRecommend() {
        return (Byte) get(25);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.tag_ids</code>. 标签id  -1 全部公开0我的收藏 其他则为tag_id，多个之间用逗号隔开
     */
    public void setTagIds(String value) {
        set(26, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.tag_ids</code>. 标签id  -1 全部公开0我的收藏 其他则为tag_id，多个之间用逗号隔开
     */
    public String getTagIds() {
        return (String) get(26);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.favorite_hrs</code>.
     */
    public void setFavoriteHrs(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.favorite_hrs</code>.
     */
    public String getFavoriteHrs() {
        return (String) get(27);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.city_code</code>.
     */
    public void setCityCode(String value) {
        set(28, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.city_code</code>.
     */
    public String getCityCode() {
        return (String) get(28);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.intention_city_code</code>.
     */
    public void setIntentionCityCode(String value) {
        set(29, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.intention_city_code</code>.
     */
    public String getIntentionCityCode() {
        return (String) get(29);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.position_key_word</code>. 职位搜索关键字
     */
    public void setPositionKeyWord(String value) {
        set(30, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.position_key_word</code>. 职位搜索关键字
     */
    public String getPositionKeyWord() {
        return (String) get(30);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.past_position_key_word</code>. 曾任职位的关键字
     */
    public void setPastPositionKeyWord(String value) {
        set(31, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.past_position_key_word</code>. 曾任职位的关键字
     */
    public String getPastPositionKeyWord() {
        return (String) get(31);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.past_company_key_word</code>. 曾任公司的关键字
     */
    public void setPastCompanyKeyWord(String value) {
        set(32, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.past_company_key_word</code>. 曾任公司的关键字
     */
    public String getPastCompanyKeyWord() {
        return (String) get(32);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.position_status</code>.
     */
    public void setPositionStatus(Integer value) {
        set(33, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.position_status</code>.
     */
    public Integer getPositionStatus() {
        return (Integer) get(33);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.start_submit_time</code>. 提交申请的开始时间
     */
    public void setStartSubmitTime(Timestamp value) {
        set(34, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.start_submit_time</code>. 提交申请的开始时间
     */
    public Timestamp getStartSubmitTime() {
        return (Timestamp) get(34);
    }

    /**
     * Setter for <code>hrdb.hr_search_condition.end_submit_time</code>. 提交申请的结束时间
     */
    public void setEndSubmitTime(Timestamp value) {
        set(35, value);
    }

    /**
     * Getter for <code>hrdb.hr_search_condition.end_submit_time</code>. 提交申请的结束时间
     */
    public Timestamp getEndSubmitTime() {
        return (Timestamp) get(35);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HrSearchConditionRecord
     */
    public HrSearchConditionRecord() {
        super(HrSearchCondition.HR_SEARCH_CONDITION);
    }

    /**
     * Create a detached, initialised HrSearchConditionRecord
     */
    public HrSearchConditionRecord(Integer id, String name, String publisher, String positionId, String keyword, String submitTime, String workYears, String cityName, String degree, String pastPosition, Integer inLastJobSearchPosition, Integer minAge, Integer maxAge, String intentionCityName, Integer sex, String intentionSalaryCode, String companyName, Integer inLastJobSearchCompany, Integer hrAccountId, Timestamp createTime, Integer updateTime, Integer type, String candidateSource, Byte isPublic, String origins, Byte isRecommend, String tagIds, String favoriteHrs, String cityCode, String intentionCityCode, String positionKeyWord, String pastPositionKeyWord, String pastCompanyKeyWord, Integer positionStatus, Timestamp startSubmitTime, Timestamp endSubmitTime) {
        super(HrSearchCondition.HR_SEARCH_CONDITION);

        set(0, id);
        set(1, name);
        set(2, publisher);
        set(3, positionId);
        set(4, keyword);
        set(5, submitTime);
        set(6, workYears);
        set(7, cityName);
        set(8, degree);
        set(9, pastPosition);
        set(10, inLastJobSearchPosition);
        set(11, minAge);
        set(12, maxAge);
        set(13, intentionCityName);
        set(14, sex);
        set(15, intentionSalaryCode);
        set(16, companyName);
        set(17, inLastJobSearchCompany);
        set(18, hrAccountId);
        set(19, createTime);
        set(20, updateTime);
        set(21, type);
        set(22, candidateSource);
        set(23, isPublic);
        set(24, origins);
        set(25, isRecommend);
        set(26, tagIds);
        set(27, favoriteHrs);
        set(28, cityCode);
        set(29, intentionCityCode);
        set(30, positionKeyWord);
        set(31, pastPositionKeyWord);
        set(32, pastCompanyKeyWord);
        set(33, positionStatus);
        set(34, startSubmitTime);
        set(35, endSubmitTime);
    }
}
