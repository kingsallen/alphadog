/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb;


import com.moseeker.baseorm.db.referraldb.tables.EmployeeReferralRecord;
import com.moseeker.baseorm.db.referraldb.tables.HistoryReferralPositionRel;
import com.moseeker.baseorm.db.referraldb.tables.HrEmployeeCertConf;
import com.moseeker.baseorm.db.referraldb.tables.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.referraldb.tables.HrGroupCompany;
import com.moseeker.baseorm.db.referraldb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.referraldb.tables.HrImporterMonitor;
import com.moseeker.baseorm.db.referraldb.tables.ReferralApplicationStatusCount;
import com.moseeker.baseorm.db.referraldb.tables.ReferralCompanyConf;
import com.moseeker.baseorm.db.referraldb.tables.ReferralConnectionChain;
import com.moseeker.baseorm.db.referraldb.tables.ReferralConnectionLog;
import com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeNetworkResources;
import com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeRegisterLog;
import com.moseeker.baseorm.db.referraldb.tables.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.ReferralPositionBonus;
import com.moseeker.baseorm.db.referraldb.tables.ReferralPositionBonusStageDetail;
import com.moseeker.baseorm.db.referraldb.tables.ReferralPositionRel;
import com.moseeker.baseorm.db.referraldb.tables.ReferralProgress;
import com.moseeker.baseorm.db.referraldb.tables.ReferralRecomEvaluation;
import com.moseeker.baseorm.db.referraldb.tables.ReferralRecomHbPosition;
import com.moseeker.baseorm.db.referraldb.tables.ReferralSeekRecommend;
import com.moseeker.baseorm.db.referraldb.tables.ReferralUploadFiles;
import com.moseeker.baseorm.db.referraldb.tables.TestTable;
import com.moseeker.baseorm.db.referraldb.tables.UserEmployee;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in referraldb
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * 员工内推记录
     */
    public static final EmployeeReferralRecord EMPLOYEE_REFERRAL_RECORD = com.moseeker.baseorm.db.referraldb.tables.EmployeeReferralRecord.EMPLOYEE_REFERRAL_RECORD;

    /**
     * The table <code>referraldb.history_referral_position_rel</code>.
     */
    public static final HistoryReferralPositionRel HISTORY_REFERRAL_POSITION_REL = com.moseeker.baseorm.db.referraldb.tables.HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL;

    /**
     * 部门员工配置表
     */
    public static final HrEmployeeCertConf HR_EMPLOYEE_CERT_CONF = com.moseeker.baseorm.db.referraldb.tables.HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF;

    /**
     * 员工认证自定义字段表
     */
    public static final HrEmployeeCustomFields HR_EMPLOYEE_CUSTOM_FIELDS = com.moseeker.baseorm.db.referraldb.tables.HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS;

    /**
     * 集团公司
     */
    public static final HrGroupCompany HR_GROUP_COMPANY = com.moseeker.baseorm.db.referraldb.tables.HrGroupCompany.HR_GROUP_COMPANY;

    /**
     * 集团公司与公司的关系
     */
    public static final HrGroupCompanyRel HR_GROUP_COMPANY_REL = com.moseeker.baseorm.db.referraldb.tables.HrGroupCompanyRel.HR_GROUP_COMPANY_REL;

    /**
     * 企业用户导入数据异步处理监控操作表
     */
    public static final HrImporterMonitor HR_IMPORTER_MONITOR = com.moseeker.baseorm.db.referraldb.tables.HrImporterMonitor.HR_IMPORTER_MONITOR;

    /**
     * 红包活动职位申请状态统计
     */
    public static final ReferralApplicationStatusCount REFERRAL_APPLICATION_STATUS_COUNT = com.moseeker.baseorm.db.referraldb.tables.ReferralApplicationStatusCount.REFERRAL_APPLICATION_STATUS_COUNT;

    /**
     * The table <code>referraldb.referral_company_conf</code>.
     */
    public static final ReferralCompanyConf REFERRAL_COMPANY_CONF = com.moseeker.baseorm.db.referraldb.tables.ReferralCompanyConf.REFERRAL_COMPANY_CONF;

    /**
     * 人脉连连看链路表
     */
    public static final ReferralConnectionChain REFERRAL_CONNECTION_CHAIN = com.moseeker.baseorm.db.referraldb.tables.ReferralConnectionChain.REFERRAL_CONNECTION_CHAIN;

    /**
     * 用于记录人脉连连看当前连接状态（未开始 已完成 连接中）
     */
    public static final ReferralConnectionLog REFERRAL_CONNECTION_LOG = com.moseeker.baseorm.db.referraldb.tables.ReferralConnectionLog.REFERRAL_CONNECTION_LOG;

    /**
     * The table <code>referraldb.referral_employee_bonus_record</code>.
     */
    public static final ReferralEmployeeBonusRecord REFERRAL_EMPLOYEE_BONUS_RECORD = com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeBonusRecord.REFERRAL_EMPLOYEE_BONUS_RECORD;

    /**
     * 员工的雷达人脉top
     */
    public static final ReferralEmployeeNetworkResources REFERRAL_EMPLOYEE_NETWORK_RESOURCES = com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeNetworkResources.REFERRAL_EMPLOYEE_NETWORK_RESOURCES;

    /**
     * 员工认证取消认证操作记录
     */
    public static final ReferralEmployeeRegisterLog REFERRAL_EMPLOYEE_REGISTER_LOG = com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG;

    /**
     * 内推记录
     */
    public static final ReferralLog REFERRAL_LOG = com.moseeker.baseorm.db.referraldb.tables.ReferralLog.REFERRAL_LOG;

    /**
     * The table <code>referraldb.referral_position_bonus</code>.
     */
    public static final ReferralPositionBonus REFERRAL_POSITION_BONUS = com.moseeker.baseorm.db.referraldb.tables.ReferralPositionBonus.REFERRAL_POSITION_BONUS;

    /**
     * The table <code>referraldb.referral_position_bonus_stage_detail</code>.
     */
    public static final ReferralPositionBonusStageDetail REFERRAL_POSITION_BONUS_STAGE_DETAIL = com.moseeker.baseorm.db.referraldb.tables.ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL;

    /**
     * The table <code>referraldb.referral_position_rel</code>.
     */
    public static final ReferralPositionRel REFERRAL_POSITION_REL = com.moseeker.baseorm.db.referraldb.tables.ReferralPositionRel.REFERRAL_POSITION_REL;

    /**
     * 分享推荐进度页面用于存储候选人查看推荐进度时的申请状态
     */
    public static final ReferralProgress REFERRAL_PROGRESS = com.moseeker.baseorm.db.referraldb.tables.ReferralProgress.REFERRAL_PROGRESS;

    /**
     * 推荐人推荐理由信息
     */
    public static final ReferralRecomEvaluation REFERRAL_RECOM_EVALUATION = com.moseeker.baseorm.db.referraldb.tables.ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION;

    /**
     * 推荐类红包与被推荐职位关系表
     */
    public static final ReferralRecomHbPosition REFERRAL_RECOM_HB_POSITION = com.moseeker.baseorm.db.referraldb.tables.ReferralRecomHbPosition.REFERRAL_RECOM_HB_POSITION;

    /**
     * 候选人联系内推记录
     */
    public static final ReferralSeekRecommend REFERRAL_SEEK_RECOMMEND = com.moseeker.baseorm.db.referraldb.tables.ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND;

    /**
     * 用户上传文件记录表
     */
    public static final ReferralUploadFiles REFERRAL_UPLOAD_FILES = com.moseeker.baseorm.db.referraldb.tables.ReferralUploadFiles.REFERRAL_UPLOAD_FILES;

    /**
     * 测试
     */
    public static final TestTable TEST_TABLE = com.moseeker.baseorm.db.referraldb.tables.TestTable.TEST_TABLE;

    /**
     * The table <code>referraldb.user_employee</code>.
     */
    public static final UserEmployee USER_EMPLOYEE = com.moseeker.baseorm.db.referraldb.tables.UserEmployee.USER_EMPLOYEE;
}
