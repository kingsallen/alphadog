/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb;


import com.moseeker.baseorm.db.hrdb.tables.HrAppCvConf;
import com.moseeker.baseorm.db.hrdb.tables.HrChatUnreadCount;
import com.moseeker.baseorm.db.hrdb.tables.HrChildCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsMedia;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsModule;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsPages;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCertConf;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeePosition;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeSection;
import com.moseeker.baseorm.db.hrdb.tables.HrFeedback;
import com.moseeker.baseorm.db.hrdb.tables.HrHbConfig;
import com.moseeker.baseorm.db.hrdb.tables.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.HrHbPositionBinding;
import com.moseeker.baseorm.db.hrdb.tables.HrHbScratchCard;
import com.moseeker.baseorm.db.hrdb.tables.HrHbSendRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrHtml5Statistics;
import com.moseeker.baseorm.db.hrdb.tables.HrHtml5UniqueStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrImporterMonitor;
import com.moseeker.baseorm.db.hrdb.tables.HrMedia;
import com.moseeker.baseorm.db.hrdb.tables.HrMediaBackupChendi;
import com.moseeker.baseorm.db.hrdb.tables.HrOperationRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.baseorm.db.hrdb.tables.HrRecruitStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrRecruitUniqueStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrReferralStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrResource;
import com.moseeker.baseorm.db.hrdb.tables.HrResourceBackupChendi;
import com.moseeker.baseorm.db.hrdb.tables.HrResourceOnline;
import com.moseeker.baseorm.db.hrdb.tables.HrRuleStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrRuleUniqueStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrSearchCondition;
import com.moseeker.baseorm.db.hrdb.tables.HrSuperaccountApply;
import com.moseeker.baseorm.db.hrdb.tables.HrTalentpool;
import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.hrdb.tables.HrTeamBackupChendi;
import com.moseeker.baseorm.db.hrdb.tables.HrTeamMember;
import com.moseeker.baseorm.db.hrdb.tables.HrTeamMemberBackupChendi;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.HrTopic;
import com.moseeker.baseorm.db.hrdb.tables.HrWxBasicReply;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChat;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.baseorm.db.hrdb.tables.HrWxImageReply;
import com.moseeker.baseorm.db.hrdb.tables.HrWxModule;
import com.moseeker.baseorm.db.hrdb.tables.HrWxNewsReply;
import com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.HrWxRule;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechatNoticeSyncStatus;
import com.moseeker.baseorm.db.hrdb.tables.HrdbHrHtml5Statistics;
import com.moseeker.baseorm.db.hrdb.tables.HrdbHrHtml5UniqueStatistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Hrdb extends SchemaImpl {

    private static final long serialVersionUID = -1758680583;

    /**
     * The reference instance of <code>hrdb</code>
     */
    public static final Hrdb HRDB = new Hrdb();

    /**
     * The table <code>hrdb.hrdb.hr_html5_statistics</code>.
     */
    public final HrdbHrHtml5Statistics HRDB_HR_HTML5_STATISTICS = HrdbHrHtml5Statistics.HRDB_HR_HTML5_STATISTICS;

    /**
     * The table <code>hrdb.hrdb.hr_html5_unique_statistics</code>.
     */
    public final HrdbHrHtml5UniqueStatistics HRDB_HR_HTML5_UNIQUE_STATISTICS = HrdbHrHtml5UniqueStatistics.HRDB_HR_HTML5_UNIQUE_STATISTICS;

    /**
     * 企业申请简历校验配置
     */
    public final HrAppCvConf HR_APP_CV_CONF = HrAppCvConf.HR_APP_CV_CONF;

    /**
     * 聊天室未读消息
     */
    public final HrChatUnreadCount HR_CHAT_UNREAD_COUNT = HrChatUnreadCount.HR_CHAT_UNREAD_COUNT;

    /**
     * 子公司表
     */
    public final HrChildCompany HR_CHILD_COMPANY = HrChildCompany.HR_CHILD_COMPANY;

    /**
     * 微信端新jd模块具体内容项
     */
    public final HrCmsMedia HR_CMS_MEDIA = HrCmsMedia.HR_CMS_MEDIA;

    /**
     * 微信端新jd内容模块配置项
     */
    public final HrCmsModule HR_CMS_MODULE = HrCmsModule.HR_CMS_MODULE;

    /**
     * 微信端新jd配置表
     */
    public final HrCmsPages HR_CMS_PAGES = HrCmsPages.HR_CMS_PAGES;

    /**
     * 公司表
     */
    public final HrCompany HR_COMPANY = HrCompany.HR_COMPANY;

    /**
     * 账号公司关联记录
     */
    public final HrCompanyAccount HR_COMPANY_ACCOUNT = HrCompanyAccount.HR_COMPANY_ACCOUNT;

    /**
     * 公司级别的配置信息表
     */
    public final HrCompanyConf HR_COMPANY_CONF = HrCompanyConf.HR_COMPANY_CONF;

    /**
     * 部门员工配置表
     */
    public final HrEmployeeCertConf HR_EMPLOYEE_CERT_CONF = HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF;

    /**
     * 员工认证自定义字段表
     */
    public final HrEmployeeCustomFields HR_EMPLOYEE_CUSTOM_FIELDS = HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS;

    /**
     * 员工职能表
     */
    public final HrEmployeePosition HR_EMPLOYEE_POSITION = HrEmployeePosition.HR_EMPLOYEE_POSITION;

    /**
     * 员工部门表
     */
    public final HrEmployeeSection HR_EMPLOYEE_SECTION = HrEmployeeSection.HR_EMPLOYEE_SECTION;

    /**
     * HR反馈表
     */
    public final HrFeedback HR_FEEDBACK = HrFeedback.HR_FEEDBACK;

    /**
     * 红包配置表
     */
    public final HrHbConfig HR_HB_CONFIG = HrHbConfig.HR_HB_CONFIG;

    /**
     * 红包记录表
     */
    public final HrHbItems HR_HB_ITEMS = HrHbItems.HR_HB_ITEMS;

    /**
     * 红包配置和职位绑定表
     */
    public final HrHbPositionBinding HR_HB_POSITION_BINDING = HrHbPositionBinding.HR_HB_POSITION_BINDING;

    /**
     * 新红包刮刮卡记录表
     */
    public final HrHbScratchCard HR_HB_SCRATCH_CARD = HrHbScratchCard.HR_HB_SCRATCH_CARD;

    /**
     * 红包发送记录
     */
    public final HrHbSendRecord HR_HB_SEND_RECORD = HrHbSendRecord.HR_HB_SEND_RECORD;

    /**
     * 专题传播统计次数表
     */
    public final HrHtml5Statistics HR_HTML5_STATISTICS = HrHtml5Statistics.HR_HTML5_STATISTICS;

    /**
     * 专题传播统计去重信息表
     */
    public final HrHtml5UniqueStatistics HR_HTML5_UNIQUE_STATISTICS = HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS;

    /**
     * 企业用户导入数据异步处理监控操作表
     */
    public final HrImporterMonitor HR_IMPORTER_MONITOR = HrImporterMonitor.HR_IMPORTER_MONITOR;

    /**
     * 模板媒体表，存储模板渲染的媒体信息
     */
    public final HrMedia HR_MEDIA = HrMedia.HR_MEDIA;

    /**
     * The table <code>hrdb.hr_media_backup_chendi</code>.
     */
    public final HrMediaBackupChendi HR_MEDIA_BACKUP_CHENDI = HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI;

    /**
     * hr申请状态操作记录
     */
    public final HrOperationRecord HR_OPERATION_RECORD = HrOperationRecord.HR_OPERATION_RECORD;

    /**
     * 雇主积分规则配置表
     */
    public final HrPointsConf HR_POINTS_CONF = HrPointsConf.HR_POINTS_CONF;

    /**
     * 招聘数据次数统计表
     */
    public final HrRecruitStatistics HR_RECRUIT_STATISTICS = HrRecruitStatistics.HR_RECRUIT_STATISTICS;

    /**
     * 招聘数据去重信息统计表
     */
    public final HrRecruitUniqueStatistics HR_RECRUIT_UNIQUE_STATISTICS = HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS;

    /**
     * 内推统计表
     */
    public final HrReferralStatistics HR_REFERRAL_STATISTICS = HrReferralStatistics.HR_REFERRAL_STATISTICS;

    /**
     * 资源集合表
     */
    public final HrResource HR_RESOURCE = HrResource.HR_RESOURCE;

    /**
     * The table <code>hrdb.hr_resource_backup_chendi</code>.
     */
    public final HrResourceBackupChendi HR_RESOURCE_BACKUP_CHENDI = HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI;

    /**
     * The table <code>hrdb.hr_resource_online</code>.
     */
    public final HrResourceOnline HR_RESOURCE_ONLINE = HrResourceOnline.HR_RESOURCE_ONLINE;

    /**
     * 微信图文传播次数统计表
     */
    public final HrRuleStatistics HR_RULE_STATISTICS = HrRuleStatistics.HR_RULE_STATISTICS;

    /**
     * 微信图文传播去重信息统计表
     */
    public final HrRuleUniqueStatistics HR_RULE_UNIQUE_STATISTICS = HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS;

    /**
     * 候选人列表常用筛选项
     */
    public final HrSearchCondition HR_SEARCH_CONDITION = HrSearchCondition.HR_SEARCH_CONDITION;

    /**
     * 升级超级账号申请表
     */
    public final HrSuperaccountApply HR_SUPERACCOUNT_APPLY = HrSuperaccountApply.HR_SUPERACCOUNT_APPLY;

    /**
     * 人才库
     */
    public final HrTalentpool HR_TALENTPOOL = HrTalentpool.HR_TALENTPOOL;

    /**
     * 团队信息
     */
    public final HrTeam HR_TEAM = HrTeam.HR_TEAM;

    /**
     * The table <code>hrdb.hr_team_backup_chendi</code>.
     */
    public final HrTeamBackupChendi HR_TEAM_BACKUP_CHENDI = HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI;

    /**
     * 团队成员信息
     */
    public final HrTeamMember HR_TEAM_MEMBER = HrTeamMember.HR_TEAM_MEMBER;

    /**
     * The table <code>hrdb.hr_team_member_backup_chendi</code>.
     */
    public final HrTeamMemberBackupChendi HR_TEAM_MEMBER_BACKUP_CHENDI = HrTeamMemberBackupChendi.HR_TEAM_MEMBER_BACKUP_CHENDI;

    /**
     * 第三方渠道帐号
     */
    public final HrThirdPartyAccount HR_THIRD_PARTY_ACCOUNT = HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT;

    /**
     * 第三方渠道同步的职位
     */
    public final HrThirdPartyPosition HR_THIRD_PARTY_POSITION = HrThirdPartyPosition.HR_THIRD_PARTY_POSITION;

    /**
     * 雇主主题活动表
     */
    public final HrTopic HR_TOPIC = HrTopic.HR_TOPIC;

    /**
     * 微信文本回复表
     */
    public final HrWxBasicReply HR_WX_BASIC_REPLY = HrWxBasicReply.HR_WX_BASIC_REPLY;

    /**
     * IM聊天
     */
    public final HrWxHrChat HR_WX_HR_CHAT = HrWxHrChat.HR_WX_HR_CHAT;

    /**
     * IM聊天人关系
     */
    public final HrWxHrChatList HR_WX_HR_CHAT_LIST = HrWxHrChatList.HR_WX_HR_CHAT_LIST;

    /**
     * 微信图片回复
     */
    public final HrWxImageReply HR_WX_IMAGE_REPLY = HrWxImageReply.HR_WX_IMAGE_REPLY;

    /**
     * 微信模块表
     */
    public final HrWxModule HR_WX_MODULE = HrWxModule.HR_WX_MODULE;

    /**
     * 微信图文回复表
     */
    public final HrWxNewsReply HR_WX_NEWS_REPLY = HrWxNewsReply.HR_WX_NEWS_REPLY;

    /**
     * 微信消息通知, first和remark文案暂不使用
     */
    public final HrWxNoticeMessage HR_WX_NOTICE_MESSAGE = HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE;

    /**
     * 微信回复规则表
     */
    public final HrWxRule HR_WX_RULE = HrWxRule.HR_WX_RULE;

    /**
     * 微信模板消息
     */
    public final HrWxTemplateMessage HR_WX_TEMPLATE_MESSAGE = HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE;

    /**
     * 微信公众号表
     */
    public final HrWxWechat HR_WX_WECHAT = HrWxWechat.HR_WX_WECHAT;

    /**
     * 微信消息通知同步状态
     */
    public final HrWxWechatNoticeSyncStatus HR_WX_WECHAT_NOTICE_SYNC_STATUS = HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS;

    /**
     * No further instances allowed
     */
    private Hrdb() {
        super("hrdb", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            HrdbHrHtml5Statistics.HRDB_HR_HTML5_STATISTICS,
            HrdbHrHtml5UniqueStatistics.HRDB_HR_HTML5_UNIQUE_STATISTICS,
            HrAppCvConf.HR_APP_CV_CONF,
            HrChatUnreadCount.HR_CHAT_UNREAD_COUNT,
            HrChildCompany.HR_CHILD_COMPANY,
            HrCmsMedia.HR_CMS_MEDIA,
            HrCmsModule.HR_CMS_MODULE,
            HrCmsPages.HR_CMS_PAGES,
            HrCompany.HR_COMPANY,
            HrCompanyAccount.HR_COMPANY_ACCOUNT,
            HrCompanyConf.HR_COMPANY_CONF,
            HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF,
            HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS,
            HrEmployeePosition.HR_EMPLOYEE_POSITION,
            HrEmployeeSection.HR_EMPLOYEE_SECTION,
            HrFeedback.HR_FEEDBACK,
            HrHbConfig.HR_HB_CONFIG,
            HrHbItems.HR_HB_ITEMS,
            HrHbPositionBinding.HR_HB_POSITION_BINDING,
            HrHbScratchCard.HR_HB_SCRATCH_CARD,
            HrHbSendRecord.HR_HB_SEND_RECORD,
            HrHtml5Statistics.HR_HTML5_STATISTICS,
            HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS,
            HrImporterMonitor.HR_IMPORTER_MONITOR,
            HrMedia.HR_MEDIA,
            HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI,
            HrOperationRecord.HR_OPERATION_RECORD,
            HrPointsConf.HR_POINTS_CONF,
            HrRecruitStatistics.HR_RECRUIT_STATISTICS,
            HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS,
            HrReferralStatistics.HR_REFERRAL_STATISTICS,
            HrResource.HR_RESOURCE,
            HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI,
            HrResourceOnline.HR_RESOURCE_ONLINE,
            HrRuleStatistics.HR_RULE_STATISTICS,
            HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS,
            HrSearchCondition.HR_SEARCH_CONDITION,
            HrSuperaccountApply.HR_SUPERACCOUNT_APPLY,
            HrTalentpool.HR_TALENTPOOL,
            HrTeam.HR_TEAM,
            HrTeamBackupChendi.HR_TEAM_BACKUP_CHENDI,
            HrTeamMember.HR_TEAM_MEMBER,
            HrTeamMemberBackupChendi.HR_TEAM_MEMBER_BACKUP_CHENDI,
            HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT,
            HrThirdPartyPosition.HR_THIRD_PARTY_POSITION,
            HrTopic.HR_TOPIC,
            HrWxBasicReply.HR_WX_BASIC_REPLY,
            HrWxHrChat.HR_WX_HR_CHAT,
            HrWxHrChatList.HR_WX_HR_CHAT_LIST,
            HrWxImageReply.HR_WX_IMAGE_REPLY,
            HrWxModule.HR_WX_MODULE,
            HrWxNewsReply.HR_WX_NEWS_REPLY,
            HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE,
            HrWxRule.HR_WX_RULE,
            HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE,
            HrWxWechat.HR_WX_WECHAT,
            HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS);
    }
}
