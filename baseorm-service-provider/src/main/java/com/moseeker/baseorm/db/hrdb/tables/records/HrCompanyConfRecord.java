/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 公司级别的配置信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyConfRecord extends UpdatableRecordImpl<HrCompanyConfRecord> {

    private static final long serialVersionUID = -441123434;

    /**
     * Setter for <code>hrdb.hr_company_conf.company_id</code>.
     */
    public void setCompanyId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.company_id</code>.
     */
    public Integer getCompanyId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.theme_id</code>. config_sys_theme.id
     */
    public void setThemeId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.theme_id</code>. config_sys_theme.id
     */
    public Integer getThemeId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.hb_throttle</code>. 全局每人每次红包活动可以获得的红包金额上限
     */
    public void setHbThrottle(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.hb_throttle</code>. 全局每人每次红包活动可以获得的红包金额上限
     */
    public Integer getHbThrottle() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.app_reply</code>. 申请提交成功回复信息
     */
    public void setAppReply(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.app_reply</code>. 申请提交成功回复信息
     */
    public String getAppReply() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.update_time</code>. 更新时间
     */
    public void setUpdateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.update_time</code>. 更新时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.employee_binding</code>. 员工认证自定义文案
     */
    public void setEmployeeBinding(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.employee_binding</code>. 员工认证自定义文案
     */
    public String getEmployeeBinding() {
        return (String) get(6);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.recommend_presentee</code>. 推荐候选人自定义文案
     */
    public void setRecommendPresentee(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.recommend_presentee</code>. 推荐候选人自定义文案
     */
    public String getRecommendPresentee() {
        return (String) get(7);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.recommend_success</code>. 推荐成功自定义文案
     */
    public void setRecommendSuccess(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.recommend_success</code>. 推荐成功自定义文案
     */
    public String getRecommendSuccess() {
        return (String) get(8);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.forward_message</code>. 转发职位自定义文案
     */
    public void setForwardMessage(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.forward_message</code>. 转发职位自定义文案
     */
    public String getForwardMessage() {
        return (String) get(9);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.application_count_limit</code>. 一个人在一个公司下每月申请次数限制
     */
    public void setApplicationCountLimit(Short value) {
        set(10, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.application_count_limit</code>. 一个人在一个公司下每月申请次数限制
     */
    public Short getApplicationCountLimit() {
        return (Short) get(10);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.school_application_count_limit</code>. 一个人在一个公司下每月校招职位申请次数限制
     */
    public void setSchoolApplicationCountLimit(Short value) {
        set(11, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.school_application_count_limit</code>. 一个人在一个公司下每月校招职位申请次数限制
     */
    public Short getSchoolApplicationCountLimit() {
        return (Short) get(11);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.job_custom_title</code>. 职位自定义字段标题
     */
    public void setJobCustomTitle(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.job_custom_title</code>. 职位自定义字段标题
     */
    public String getJobCustomTitle() {
        return (String) get(12);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.search_seq</code>. 搜索页页面设置顺序,3#1#2
     */
    public void setSearchSeq(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.search_seq</code>. 搜索页页面设置顺序,3#1#2
     */
    public String getSearchSeq() {
        return (String) get(13);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.search_img</code>. 搜索页页面设置背景图
     */
    public void setSearchImg(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.search_img</code>. 搜索页页面设置背景图
     */
    public String getSearchImg() {
        return (String) get(14);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.job_occupation</code>. 自定义字段名称
     */
    public void setJobOccupation(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.job_occupation</code>. 自定义字段名称
     */
    public String getJobOccupation() {
        return (String) get(15);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.teamname_custom</code>. 自定义部门别名
     */
    public void setTeamnameCustom(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.teamname_custom</code>. 自定义部门别名
     */
    public String getTeamnameCustom() {
        return (String) get(16);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.application_time</code>. newjd_status即新的jd页的生效时间，
     */
    public void setApplicationTime(Timestamp value) {
        set(17, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.application_time</code>. newjd_status即新的jd页的生效时间，
     */
    public Timestamp getApplicationTime() {
        return (Timestamp) get(17);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.newjd_status</code>. 新jd页去设置状态0是为开启，1是用户开启，2是审核通过（使用新jd），3撤销（返回基础版） 默认是0
     */
    public void setNewjdStatus(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.newjd_status</code>. 新jd页去设置状态0是为开启，1是用户开启，2是审核通过（使用新jd），3撤销（返回基础版） 默认是0
     */
    public Integer getNewjdStatus() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.hr_chat</code>. IM聊天开关，0：不开启，1：开启，2：开启+chatbot
     */
    public void setHrChat(Byte value) {
        set(19, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.hr_chat</code>. IM聊天开关，0：不开启，1：开启，2：开启+chatbot
     */
    public Byte getHrChat() {
        return (Byte) get(19);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.show_in_qx</code>. 公司信息、团队信息、职位信息在仟寻展示，0: 否， 1: 是
     */
    public void setShowInQx(Byte value) {
        set(20, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.show_in_qx</code>. 公司信息、团队信息、职位信息在仟寻展示，0: 否， 1: 是
     */
    public Byte getShowInQx() {
        return (Byte) get(20);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.employee_slug</code>. 员工自定义称谓
     */
    public void setEmployeeSlug(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.employee_slug</code>. 员工自定义称谓
     */
    public String getEmployeeSlug() {
        return (String) get(21);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.display_locale</code>. 公司页面语言，格式:IETF language tag
     */
    public void setDisplayLocale(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.display_locale</code>. 公司页面语言，格式:IETF language tag
     */
    public String getDisplayLocale() {
        return (String) get(22);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.talentpool_status</code>. 人才库状态表 0未开启，1开启普通人才库，2开启高端人才库
     */
    public void setTalentpoolStatus(Byte value) {
        set(23, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.talentpool_status</code>. 人才库状态表 0未开启，1开启普通人才库，2开启高端人才库
     */
    public Byte getTalentpoolStatus() {
        return (Byte) get(23);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.job51_salary_discuss</code>. 51薪资面议开关 0：未开启，1：开启
     */
    public void setJob51SalaryDiscuss(Byte value) {
        set(24, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.job51_salary_discuss</code>. 51薪资面议开关 0：未开启，1：开启
     */
    public Byte getJob51SalaryDiscuss() {
        return (Byte) get(24);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.veryeast_switch</code>. 最佳东方c 端简 导入开关 0：未开启，1：开启
     */
    public void setVeryeastSwitch(Byte value) {
        set(25, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.veryeast_switch</code>. 最佳东方c 端简 导入开关 0：未开启，1：开启
     */
    public Byte getVeryeastSwitch() {
        return (Byte) get(25);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.mall_switch</code>. 商城开关  0从未开通 1 已开通 2 开通过目前停用
     */
    public void setMallSwitch(Byte value) {
        set(26, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.mall_switch</code>. 商城开关  0从未开通 1 已开通 2 开通过目前停用
     */
    public Byte getMallSwitch() {
        return (Byte) get(26);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.mall_goods_method</code>. 商城商品领取规则
     */
    public void setMallGoodsMethod(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.mall_goods_method</code>. 商城商品领取规则
     */
    public String getMallGoodsMethod() {
        return (String) get(27);
    }

    /**
     * Setter for <code>hrdb.hr_company_conf.mall_goods_method_state</code>. 商城是否保存默认领取规则 0 取消默认规则 1 使用默认规则
     */
    public void setMallGoodsMethodState(Byte value) {
        set(28, value);
    }

    /**
     * Getter for <code>hrdb.hr_company_conf.mall_goods_method_state</code>. 商城是否保存默认领取规则 0 取消默认规则 1 使用默认规则
     */
    public Byte getMallGoodsMethodState() {
        return (Byte) get(28);
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
     * Create a detached HrCompanyConfRecord
     */
    public HrCompanyConfRecord() {
        super(HrCompanyConf.HR_COMPANY_CONF);
    }

    /**
     * Create a detached, initialised HrCompanyConfRecord
     */
    public HrCompanyConfRecord(Integer companyId, Integer themeId, Integer hbThrottle, String appReply, Timestamp createTime, Timestamp updateTime, String employeeBinding, String recommendPresentee, String recommendSuccess, String forwardMessage, Short applicationCountLimit, Short schoolApplicationCountLimit, String jobCustomTitle, String searchSeq, String searchImg, String jobOccupation, String teamnameCustom, Timestamp applicationTime, Integer newjdStatus, Byte hrChat, Byte showInQx, String employeeSlug, String displayLocale, Byte talentpoolStatus, Byte veryeastSwitch, Byte job51SalaryDiscuss, Byte mallSwitch, String mallGoodsMethod, Byte mallGoodsMethodState) {
        super(HrCompanyConf.HR_COMPANY_CONF);

        set(0, companyId);
        set(1, themeId);
        set(2, hbThrottle);
        set(3, appReply);
        set(4, createTime);
        set(5, updateTime);
        set(6, employeeBinding);
        set(7, recommendPresentee);
        set(8, recommendSuccess);
        set(9, forwardMessage);
        set(10, applicationCountLimit);
        set(11, schoolApplicationCountLimit);
        set(12, jobCustomTitle);
        set(13, searchSeq);
        set(14, searchImg);
        set(15, jobOccupation);
        set(16, teamnameCustom);
        set(17, applicationTime);
        set(18, newjdStatus);
        set(19, hrChat);
        set(20, showInQx);
        set(21, employeeSlug);
        set(22, displayLocale);
        set(23, talentpoolStatus);
        set(24, job51SalaryDiscuss);
        set(25, veryeastSwitch);
        set(26, mallSwitch);
        set(27, mallGoodsMethod);
        set(28, mallGoodsMethodState);
    }
}
