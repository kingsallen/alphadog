/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb;


import com.moseeker.baseorm.db.hrdb.tables.*;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in hrdb
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * 人工智能系统配置表
     */
    public static final HrAiConf HR_AI_CONF = com.moseeker.baseorm.db.hrdb.tables.HrAiConf.HR_AI_CONF;

    /**
     * 企业申请简历校验配置
     */
    public static final HrAppCvConf HR_APP_CV_CONF = com.moseeker.baseorm.db.hrdb.tables.HrAppCvConf.HR_APP_CV_CONF;

    /**
     * 自定义简历模板导出字段表
     */
    public static final HrAppExportFields HR_APP_EXPORT_FIELDS = com.moseeker.baseorm.db.hrdb.tables.HrAppExportFields.HR_APP_EXPORT_FIELDS;

    /**
     * 聊天室未读消息
     */
    public static final HrChatUnreadCount HR_CHAT_UNREAD_COUNT = com.moseeker.baseorm.db.hrdb.tables.HrChatUnreadCount.HR_CHAT_UNREAD_COUNT;

    /**
     * 微信端新jd模块具体内容项
     */
    public static final HrCmsMedia HR_CMS_MEDIA = com.moseeker.baseorm.db.hrdb.tables.HrCmsMedia.HR_CMS_MEDIA;

    /**
     * 微信端新jd内容模块配置项
     */
    public static final HrCmsModule HR_CMS_MODULE = com.moseeker.baseorm.db.hrdb.tables.HrCmsModule.HR_CMS_MODULE;

    /**
     * 微信端新jd配置表
     */
    public static final HrCmsPages HR_CMS_PAGES = com.moseeker.baseorm.db.hrdb.tables.HrCmsPages.HR_CMS_PAGES;

    /**
     * 公司表
     */
    public static final HrCompany HR_COMPANY = com.moseeker.baseorm.db.hrdb.tables.HrCompany.HR_COMPANY;

    /**
     * 账号公司关联记录
     */
    public static final HrCompanyAccount HR_COMPANY_ACCOUNT = com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount.HR_COMPANY_ACCOUNT;

    /**
     * 公司级别的配置信息表
     */
    public static final HrCompanyConf HR_COMPANY_CONF = com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf.HR_COMPANY_CONF;

    /**
     * 公司和CS匹配表
     */
    public static final HrCompanyCs HR_COMPANY_CS = com.moseeker.baseorm.db.hrdb.tables.HrCompanyCs.HR_COMPANY_CS;

    /**
     * 企业邮件总量信息表
     */
    public static final HrCompanyEmailInfo HR_COMPANY_EMAIL_INFO = com.moseeker.baseorm.db.hrdb.tables.HrCompanyEmailInfo.HR_COMPANY_EMAIL_INFO;

    /**
     * 公司福利特色
     */
    public static final HrCompanyFeature HR_COMPANY_FEATURE = com.moseeker.baseorm.db.hrdb.tables.HrCompanyFeature.HR_COMPANY_FEATURE;

    /**
     * 部门员工配置表
     */
    public static final HrEmployeeCertConf HR_EMPLOYEE_CERT_CONF = com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF;

    /**
     * 员工认证自定义字段表
     */
    public static final HrEmployeeCustomFields HR_EMPLOYEE_CUSTOM_FIELDS = com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS;

    /**
     * 员工职能表
     */
    public static final HrEmployeePosition HR_EMPLOYEE_POSITION = com.moseeker.baseorm.db.hrdb.tables.HrEmployeePosition.HR_EMPLOYEE_POSITION;

    /**
     * 员工部门表
     */
    public static final HrEmployeeSection HR_EMPLOYEE_SECTION = com.moseeker.baseorm.db.hrdb.tables.HrEmployeeSection.HR_EMPLOYEE_SECTION;

    /**
     * HR反馈表
     */
    public static final HrFeedback HR_FEEDBACK = com.moseeker.baseorm.db.hrdb.tables.HrFeedback.HR_FEEDBACK;

    /**
     * The table <code>hrdb.hr_group_company</code>.
     */
    public static final HrGroupCompany HR_GROUP_COMPANY = com.moseeker.baseorm.db.hrdb.tables.HrGroupCompany.HR_GROUP_COMPANY;

    /**
     * The table <code>hrdb.hr_group_company_rel</code>.
     */
    public static final HrGroupCompanyRel HR_GROUP_COMPANY_REL = com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel.HR_GROUP_COMPANY_REL;

    /**
     * 红包配置表
     */
    public static final HrHbConfig HR_HB_CONFIG = com.moseeker.baseorm.db.hrdb.tables.HrHbConfig.HR_HB_CONFIG;

    /**
     * 红包记录表
     */
    public static final HrHbItems HR_HB_ITEMS = com.moseeker.baseorm.db.hrdb.tables.HrHbItems.HR_HB_ITEMS;

    /**
     * 红包配置和职位绑定表
     */
    public static final HrHbPositionBinding HR_HB_POSITION_BINDING = com.moseeker.baseorm.db.hrdb.tables.HrHbPositionBinding.HR_HB_POSITION_BINDING;

    /**
     * 新红包刮刮卡记录表
     */
    public static final HrHbScratchCard HR_HB_SCRATCH_CARD = com.moseeker.baseorm.db.hrdb.tables.HrHbScratchCard.HR_HB_SCRATCH_CARD;

    /**
     * 红包发送记录
     */
    public static final HrHbSendRecord HR_HB_SEND_RECORD = com.moseeker.baseorm.db.hrdb.tables.HrHbSendRecord.HR_HB_SEND_RECORD;

    /**
     * 专题传播统计次数表
     */
    public static final HrHtml5Statistics HR_HTML5_STATISTICS = com.moseeker.baseorm.db.hrdb.tables.HrHtml5Statistics.HR_HTML5_STATISTICS;

    /**
     * 专题传播统计去重信息表
     */
    public static final HrHtml5UniqueStatistics HR_HTML5_UNIQUE_STATISTICS = com.moseeker.baseorm.db.hrdb.tables.HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS;

    /**
     * 企业用户导入数据异步处理监控操作表
     */
    public static final HrImporterMonitor HR_IMPORTER_MONITOR = com.moseeker.baseorm.db.hrdb.tables.HrImporterMonitor.HR_IMPORTER_MONITOR;

    /**
     * nps打分推荐表
     */
    public static final HrNps HR_NPS = com.moseeker.baseorm.db.hrdb.tables.HrNps.HR_NPS;

    /**
     * hr推荐同行表
     */
    public static final HrNpsRecommend HR_NPS_RECOMMEND = com.moseeker.baseorm.db.hrdb.tables.HrNpsRecommend.HR_NPS_RECOMMEND;

    /**
     * hr申请状态操作记录
     */
    public static final HrOperationRecord HR_OPERATION_RECORD = com.moseeker.baseorm.db.hrdb.tables.HrOperationRecord.HR_OPERATION_RECORD;

    /**
     * 雇主积分规则配置表
     */
    public static final HrPointsConf HR_POINTS_CONF = com.moseeker.baseorm.db.hrdb.tables.HrPointsConf.HR_POINTS_CONF;

    /**
     * 招聘数据次数统计表
     */
    public static final HrRecruitStatistics HR_RECRUIT_STATISTICS = com.moseeker.baseorm.db.hrdb.tables.HrRecruitStatistics.HR_RECRUIT_STATISTICS;

    /**
     * 招聘数据去重信息统计表
     */
    public static final HrRecruitUniqueStatistics HR_RECRUIT_UNIQUE_STATISTICS = com.moseeker.baseorm.db.hrdb.tables.HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS;

    /**
     * 内推统计表
     */
    public static final HrReferralStatistics HR_REFERRAL_STATISTICS = com.moseeker.baseorm.db.hrdb.tables.HrReferralStatistics.HR_REFERRAL_STATISTICS;

    /**
     * 资源集合表
     */
    public static final HrResource HR_RESOURCE = com.moseeker.baseorm.db.hrdb.tables.HrResource.HR_RESOURCE;

    /**
     * 微信图文传播次数统计表
     */
    public static final HrRuleStatistics HR_RULE_STATISTICS = com.moseeker.baseorm.db.hrdb.tables.HrRuleStatistics.HR_RULE_STATISTICS;

    /**
     * 微信图文传播去重信息统计表
     */
    public static final HrRuleUniqueStatistics HR_RULE_UNIQUE_STATISTICS = com.moseeker.baseorm.db.hrdb.tables.HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS;

    /**
     * 候选人列表常用筛选项
     */
    public static final HrSearchCondition HR_SEARCH_CONDITION = com.moseeker.baseorm.db.hrdb.tables.HrSearchCondition.HR_SEARCH_CONDITION;

    /**
     * 升级超级账号申请表
     */
    public static final HrSuperaccountApply HR_SUPERACCOUNT_APPLY = com.moseeker.baseorm.db.hrdb.tables.HrSuperaccountApply.HR_SUPERACCOUNT_APPLY;

    /**
     * 人才库
     */
    public static final HrTalentpool HR_TALENTPOOL = com.moseeker.baseorm.db.hrdb.tables.HrTalentpool.HR_TALENTPOOL;

    /**
     * 团队信息
     */
    public static final HrTeam HR_TEAM = com.moseeker.baseorm.db.hrdb.tables.HrTeam.HR_TEAM;

    /**
     * The table <code>hrdb.hr_team_20180118_chendi</code>.
     */
    public static final HrTeam_20180118Chendi HR_TEAM_20180118_CHENDI = com.moseeker.baseorm.db.hrdb.tables.HrTeam_20180118Chendi.HR_TEAM_20180118_CHENDI;

    /**
     * 团队成员信息
     */
    public static final HrTeamMember HR_TEAM_MEMBER = com.moseeker.baseorm.db.hrdb.tables.HrTeamMember.HR_TEAM_MEMBER;

    /**
     * 第三方渠道帐号
     */
    public static final HrThirdPartyAccount HR_THIRD_PARTY_ACCOUNT = com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT;

    /**
     * 第三方账号和hr表关联表，账号分配表，取消分配将status置为0，重新分配不修改记录而是新加分配记录
     */
    public static final HrThirdPartyAccountHr HR_THIRD_PARTY_ACCOUNT_HR = com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR;

    /**
     * 第三方渠道同步的职位
     */
    public static final HrThirdPartyPosition HR_THIRD_PARTY_POSITION = com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition.HR_THIRD_PARTY_POSITION;

    /**
     * 雇主主题活动表
     */
    public static final HrTopic HR_TOPIC = com.moseeker.baseorm.db.hrdb.tables.HrTopic.HR_TOPIC;

    /**
     * 微信文本回复表
     */
    public static final HrWxBasicReply HR_WX_BASIC_REPLY = com.moseeker.baseorm.db.hrdb.tables.HrWxBasicReply.HR_WX_BASIC_REPLY;

    /**
     * IM聊天
     */
    public static final HrWxHrChat HR_WX_HR_CHAT = com.moseeker.baseorm.db.hrdb.tables.HrWxHrChat.HR_WX_HR_CHAT;

    /**
     * IM聊天人关系
     */
    public static final HrWxHrChatList HR_WX_HR_CHAT_LIST = com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatList.HR_WX_HR_CHAT_LIST;

    /**
     * 微信图片回复
     */
    public static final HrWxImageReply HR_WX_IMAGE_REPLY = com.moseeker.baseorm.db.hrdb.tables.HrWxImageReply.HR_WX_IMAGE_REPLY;

    /**
     * 微信模块表
     */
    public static final HrWxModule HR_WX_MODULE = com.moseeker.baseorm.db.hrdb.tables.HrWxModule.HR_WX_MODULE;

    /**
     * 微信图文回复表
     */
    public static final HrWxNewsReply HR_WX_NEWS_REPLY = com.moseeker.baseorm.db.hrdb.tables.HrWxNewsReply.HR_WX_NEWS_REPLY;

    /**
     * 微信消息通知, first和remark文案暂不使用
     */
    public static final HrWxNoticeMessage HR_WX_NOTICE_MESSAGE = com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE;

    /**
     * 微信回复规则表
     */
    public static final HrWxRule HR_WX_RULE = com.moseeker.baseorm.db.hrdb.tables.HrWxRule.HR_WX_RULE;

    /**
     * 微信模板消息
     */
    public static final HrWxTemplateMessage HR_WX_TEMPLATE_MESSAGE = com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE;

    /**
     * 微信公众号表
     */
    public static final HrWxWechat HR_WX_WECHAT = com.moseeker.baseorm.db.hrdb.tables.HrWxWechat.HR_WX_WECHAT;

    /**
     * The table <code>hrdb.hr_wx_wechat_20180127chendi</code>.
     */
    public static final HrWxWechat_20180127chendi HR_WX_WECHAT_20180127CHENDI = com.moseeker.baseorm.db.hrdb.tables.HrWxWechat_20180127chendi.HR_WX_WECHAT_20180127CHENDI;

    /**
     * 微信消息通知同步状态
     */
    public static final HrWxWechatNoticeSyncStatus HR_WX_WECHAT_NOTICE_SYNC_STATUS = com.moseeker.baseorm.db.hrdb.tables.HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS;
    /**
     * The table <code>hrdb.hr_wx_hr_chat_voice</code>.
     */
    public static final HrWxHrChatVoice HR_WX_HR_CHAT_VOICE = HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE;
}
