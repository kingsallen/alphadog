/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.records;


import com.moseeker.baseorm.db.jobdb.tables.JobApplication;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobApplicationRecord extends UpdatableRecordImpl<JobApplicationRecord> {

    private static final long serialVersionUID = 870422948;

    /**
     * Setter for <code>jobdb.job_application.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>jobdb.job_application.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>jobdb.job_application.wechat_id</code>. sys_wechat.id, 公众号ID
     */
    public void setWechatId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>jobdb.job_application.wechat_id</code>. sys_wechat.id, 公众号ID
     */
    public Integer getWechatId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>jobdb.job_application.position_id</code>. job_position.id, 职位ID
     */
    public void setPositionId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>jobdb.job_application.position_id</code>. job_position.id, 职位ID
     */
    public Integer getPositionId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>jobdb.job_application.recommender_id</code>. user_wx_user.id, 微信ID。现在已经废弃，推荐者信息请参考recommend_user_id
     */
    public void setRecommenderId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>jobdb.job_application.recommender_id</code>. user_wx_user.id, 微信ID。现在已经废弃，推荐者信息请参考recommend_user_id
     */
    public Integer getRecommenderId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>jobdb.job_application.submit_time</code>. 申请提交时间
     */
    public void setSubmitTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>jobdb.job_application.submit_time</code>. 申请提交时间
     */
    public Timestamp getSubmitTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>jobdb.job_application.status_id</code>. hr_points_conf.id, 申请状态ID
     */
    public void setStatusId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>jobdb.job_application.status_id</code>. hr_points_conf.id, 申请状态ID
     */
    public Integer getStatusId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>jobdb.job_application.l_application_id</code>. ATS的申请ID
     */
    public void setLApplicationId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>jobdb.job_application.l_application_id</code>. ATS的申请ID
     */
    public Integer getLApplicationId() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>jobdb.job_application.reward</code>. 当前申请的积分记录
     */
    public void setReward(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>jobdb.job_application.reward</code>. 当前申请的积分记录
     */
    public Integer getReward() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>jobdb.job_application.source_id</code>. 已废弃,job_source.id, 对应的ATS ID
     */
    public void setSourceId(Integer value) {
        set(8, value);
    }

    /**
     * Getter for <code>jobdb.job_application.source_id</code>. 已废弃,job_source.id, 对应的ATS ID
     */
    public Integer getSourceId() {
        return (Integer) get(8);
    }

    /**
     * Setter for <code>jobdb.job_application._create_time</code>. time stamp when record created
     */
    public void set_CreateTime(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>jobdb.job_application._create_time</code>. time stamp when record created
     */
    public Timestamp get_CreateTime() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>jobdb.job_application.applier_id</code>. sys_user.id, 用户ID
     */
    public void setApplierId(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>jobdb.job_application.applier_id</code>. sys_user.id, 用户ID
     */
    public Integer getApplierId() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>jobdb.job_application.interview_id</code>. app_interview.id, 面试ID
     */
    public void setInterviewId(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>jobdb.job_application.interview_id</code>. app_interview.id, 面试ID
     */
    public Integer getInterviewId() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>jobdb.job_application.resume_id</code>. mongodb collection application[id]
     */
    public void setResumeId(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>jobdb.job_application.resume_id</code>. mongodb collection application[id]
     */
    public String getResumeId() {
        return (String) get(12);
    }

    /**
     * Setter for <code>jobdb.job_application.ats_status</code>. 0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified, 6:excess apply times, 7 redundant,  8:failed, 9 failed and notified
     */
    public void setAtsStatus(Integer value) {
        set(13, value);
    }

    /**
     * Getter for <code>jobdb.job_application.ats_status</code>. 0:unuse, 1:waiting, 2:failed, 3:success, 4:position expire, 5:resume unqualified, 6:excess apply times, 7 redundant,  8:failed, 9 failed and notified
     */
    public Integer getAtsStatus() {
        return (Integer) get(13);
    }

    /**
     * Setter for <code>jobdb.job_application.applier_name</code>. 姓名或微信昵称
     */
    public void setApplierName(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>jobdb.job_application.applier_name</code>. 姓名或微信昵称
     */
    public String getApplierName() {
        return (String) get(14);
    }

    /**
     * Setter for <code>jobdb.job_application.disable</code>. 是否有效，0：有效，1：无效
     */
    public void setDisable(Integer value) {
        set(15, value);
    }

    /**
     * Getter for <code>jobdb.job_application.disable</code>. 是否有效，0：有效，1：无效
     */
    public Integer getDisable() {
        return (Integer) get(15);
    }

    /**
     * Setter for <code>jobdb.job_application.routine</code>. 申请来源 0:微信企业端 1:微信聚合端 10:pc端
     */
    public void setRoutine(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>jobdb.job_application.routine</code>. 申请来源 0:微信企业端 1:微信聚合端 10:pc端
     */
    public Integer getRoutine() {
        return (Integer) get(16);
    }

    /**
     * Setter for <code>jobdb.job_application.is_viewed</code>. 该申请是否被浏览，0：已浏览，1：未浏览
     */
    public void setIsViewed(Byte value) {
        set(17, value);
    }

    /**
     * Getter for <code>jobdb.job_application.is_viewed</code>. 该申请是否被浏览，0：已浏览，1：未浏览
     */
    public Byte getIsViewed() {
        return (Byte) get(17);
    }

    /**
     * Setter for <code>jobdb.job_application.not_suitable</code>. 是否不合适，0：合适，1：不合适
     */
    public void setNotSuitable(Byte value) {
        set(18, value);
    }

    /**
     * Getter for <code>jobdb.job_application.not_suitable</code>. 是否不合适，0：合适，1：不合适
     */
    public Byte getNotSuitable() {
        return (Byte) get(18);
    }

    /**
     * Setter for <code>jobdb.job_application.company_id</code>. company.id，公司表ID
     */
    public void setCompanyId(Integer value) {
        set(19, value);
    }

    /**
     * Getter for <code>jobdb.job_application.company_id</code>. company.id，公司表ID
     */
    public Integer getCompanyId() {
        return (Integer) get(19);
    }

    /**
     * Setter for <code>jobdb.job_application.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>jobdb.job_application.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(20);
    }

    /**
     * Setter for <code>jobdb.job_application.app_tpl_id</code>. 申请状态,hr_award_config_template.id
     */
    public void setAppTplId(Integer value) {
        set(21, value);
    }

    /**
     * Getter for <code>jobdb.job_application.app_tpl_id</code>. 申请状态,hr_award_config_template.id
     */
    public Integer getAppTplId() {
        return (Integer) get(21);
    }

    /**
     * Setter for <code>jobdb.job_application.proxy</code>. 是否是代理投递 0：正常数据，1：代理假投递
     */
    public void setProxy(Byte value) {
        set(22, value);
    }

    /**
     * Getter for <code>jobdb.job_application.proxy</code>. 是否是代理投递 0：正常数据，1：代理假投递
     */
    public Byte getProxy() {
        return (Byte) get(22);
    }

    /**
     * Setter for <code>jobdb.job_application.apply_type</code>. 投递区分， 0：profile投递， 1：email投递
     */
    public void setApplyType(Integer value) {
        set(23, value);
    }

    /**
     * Getter for <code>jobdb.job_application.apply_type</code>. 投递区分， 0：profile投递， 1：email投递
     */
    public Integer getApplyType() {
        return (Integer) get(23);
    }

    /**
     * Setter for <code>jobdb.job_application.email_status</code>. 0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；8: 包含特殊字体;  9，提取邮件失败
     */
    public void setEmailStatus(Integer value) {
        set(24, value);
    }

    /**
     * Getter for <code>jobdb.job_application.email_status</code>. 0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；8: 包含特殊字体;  9，提取邮件失败
     */
    public Integer getEmailStatus() {
        return (Integer) get(24);
    }

    /**
     * Setter for <code>jobdb.job_application.view_count</code>. profile浏览次数
     */
    public void setViewCount(Integer value) {
        set(25, value);
    }

    /**
     * Getter for <code>jobdb.job_application.view_count</code>. profile浏览次数
     */
    public Integer getViewCount() {
        return (Integer) get(25);
    }

    /**
     * Setter for <code>jobdb.job_application.recommender_user_id</code>. userdb.user_user.id 推荐人编号
     */
    public void setRecommenderUserId(Integer value) {
        set(26, value);
    }

    /**
     * Getter for <code>jobdb.job_application.recommender_user_id</code>. userdb.user_user.id 推荐人编号
     */
    public Integer getRecommenderUserId() {
        return (Integer) get(26);
    }

    /**
     * Setter for <code>jobdb.job_application.origin</code>. 申请来源 1 PC;2 企业号；4 聚合号； 8 51； 16 智联； 32 猎聘； 64 支付宝； 128 简历抽取； 256 员工代投 ; 512:程序导入（和黄经历导入）; 1024: email 申请; 2048:最佳东方;4096:一览英才;8192:JobsDB
     */
    public void setOrigin(Integer value) {
        set(27, value);
    }

    /**
     * Getter for <code>jobdb.job_application.origin</code>. 申请来源 1 PC;2 企业号；4 聚合号； 8 51； 16 智联； 32 猎聘； 64 支付宝； 128 简历抽取； 256 员工代投 ; 512:程序导入（和黄经历导入）; 1024: email 申请; 2048:最佳东方;4096:一览英才;8192:JobsDB
     */
    public Integer getOrigin() {
        return (Integer) get(27);
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
     * Create a detached JobApplicationRecord
     */
    public JobApplicationRecord() {
        super(JobApplication.JOB_APPLICATION);
    }

    /**
     * Create a detached, initialised JobApplicationRecord
     */
    public JobApplicationRecord(Integer id, Integer wechatId, Integer positionId, Integer recommenderId, Timestamp submitTime, Integer statusId, Integer lApplicationId, Integer reward, Integer sourceId, Timestamp _CreateTime, Integer applierId, Integer interviewId, String resumeId, Integer atsStatus, String applierName, Integer disable, Integer routine, Byte isViewed, Byte notSuitable, Integer companyId, Timestamp updateTime, Integer appTplId, Byte proxy, Integer applyType, Integer emailStatus, Integer viewCount, Integer recommenderUserId, Integer origin) {
        super(JobApplication.JOB_APPLICATION);

        set(0, id);
        set(1, wechatId);
        set(2, positionId);
        set(3, recommenderId);
        set(4, submitTime);
        set(5, statusId);
        set(6, lApplicationId);
        set(7, reward);
        set(8, sourceId);
        set(9, _CreateTime);
        set(10, applierId);
        set(11, interviewId);
        set(12, resumeId);
        set(13, atsStatus);
        set(14, applierName);
        set(15, disable);
        set(16, routine);
        set(17, isViewed);
        set(18, notSuitable);
        set(19, companyId);
        set(20, updateTime);
        set(21, appTplId);
        set(22, proxy);
        set(23, applyType);
        set(24, emailStatus);
        set(25, viewCount);
        set(26, recommenderUserId);
        set(27, origin);
    }
}
