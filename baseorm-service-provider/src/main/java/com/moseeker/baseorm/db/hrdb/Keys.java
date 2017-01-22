/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb;


import com.moseeker.baseorm.db.hrdb.tables.HrAppCvConf;
import com.moseeker.baseorm.db.hrdb.tables.HrChildCompany;
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
import com.moseeker.baseorm.db.hrdb.tables.HrOperationRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.baseorm.db.hrdb.tables.HrRecruitStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrRecruitUniqueStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrReferralStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrResource;
import com.moseeker.baseorm.db.hrdb.tables.HrRuleStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrRuleUniqueStatistics;
import com.moseeker.baseorm.db.hrdb.tables.HrSearchCondition;
import com.moseeker.baseorm.db.hrdb.tables.HrSuperaccountApply;
import com.moseeker.baseorm.db.hrdb.tables.HrTalentpool;
import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.hrdb.tables.HrTeamMember;
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
import com.moseeker.baseorm.db.hrdb.tables.records.HrAppCvConfRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChildCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCertConfRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCustomFieldsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeePositionRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeSectionRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrFeedbackRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbPositionBindingRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbScratchCardRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbSendRecordRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHtml5StatisticsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHtml5UniqueStatisticsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrImporterMonitorRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrMediaRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRecruitStatisticsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRecruitUniqueStatisticsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrReferralStatisticsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrResourceRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRuleStatisticsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRuleUniqueStatisticsRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSearchConditionRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSuperaccountApplyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTalentpoolRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamMemberRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTopicRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxBasicReplyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxImageReplyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxModuleRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNewsReplyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNoticeMessageRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxRuleRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxTemplateMessageRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatNoticeSyncStatusRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;


/**
 * A class modelling foreign key relationships between tables of the <code>hrdb</code> 
 * schema
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<HrAppCvConfRecord, Integer> IDENTITY_HR_APP_CV_CONF = Identities0.IDENTITY_HR_APP_CV_CONF;
    public static final Identity<HrChildCompanyRecord, Integer> IDENTITY_HR_CHILD_COMPANY = Identities0.IDENTITY_HR_CHILD_COMPANY;
    public static final Identity<HrCompanyRecord, UInteger> IDENTITY_HR_COMPANY = Identities0.IDENTITY_HR_COMPANY;
    public static final Identity<HrEmployeeCertConfRecord, Integer> IDENTITY_HR_EMPLOYEE_CERT_CONF = Identities0.IDENTITY_HR_EMPLOYEE_CERT_CONF;
    public static final Identity<HrEmployeeCustomFieldsRecord, Integer> IDENTITY_HR_EMPLOYEE_CUSTOM_FIELDS = Identities0.IDENTITY_HR_EMPLOYEE_CUSTOM_FIELDS;
    public static final Identity<HrEmployeePositionRecord, Integer> IDENTITY_HR_EMPLOYEE_POSITION = Identities0.IDENTITY_HR_EMPLOYEE_POSITION;
    public static final Identity<HrEmployeeSectionRecord, Integer> IDENTITY_HR_EMPLOYEE_SECTION = Identities0.IDENTITY_HR_EMPLOYEE_SECTION;
    public static final Identity<HrFeedbackRecord, UInteger> IDENTITY_HR_FEEDBACK = Identities0.IDENTITY_HR_FEEDBACK;
    public static final Identity<HrHbConfigRecord, Integer> IDENTITY_HR_HB_CONFIG = Identities0.IDENTITY_HR_HB_CONFIG;
    public static final Identity<HrHbItemsRecord, Integer> IDENTITY_HR_HB_ITEMS = Identities0.IDENTITY_HR_HB_ITEMS;
    public static final Identity<HrHbPositionBindingRecord, Integer> IDENTITY_HR_HB_POSITION_BINDING = Identities0.IDENTITY_HR_HB_POSITION_BINDING;
    public static final Identity<HrHbScratchCardRecord, Integer> IDENTITY_HR_HB_SCRATCH_CARD = Identities0.IDENTITY_HR_HB_SCRATCH_CARD;
    public static final Identity<HrHbSendRecordRecord, Integer> IDENTITY_HR_HB_SEND_RECORD = Identities0.IDENTITY_HR_HB_SEND_RECORD;
    public static final Identity<HrHtml5StatisticsRecord, Integer> IDENTITY_HR_HTML5_STATISTICS = Identities0.IDENTITY_HR_HTML5_STATISTICS;
    public static final Identity<HrHtml5UniqueStatisticsRecord, Integer> IDENTITY_HR_HTML5_UNIQUE_STATISTICS = Identities0.IDENTITY_HR_HTML5_UNIQUE_STATISTICS;
    public static final Identity<HrImporterMonitorRecord, Integer> IDENTITY_HR_IMPORTER_MONITOR = Identities0.IDENTITY_HR_IMPORTER_MONITOR;
    public static final Identity<HrMediaRecord, Integer> IDENTITY_HR_MEDIA = Identities0.IDENTITY_HR_MEDIA;
    public static final Identity<HrOperationRecordRecord, Integer> IDENTITY_HR_OPERATION_RECORD = Identities0.IDENTITY_HR_OPERATION_RECORD;
    public static final Identity<HrPointsConfRecord, Integer> IDENTITY_HR_POINTS_CONF = Identities0.IDENTITY_HR_POINTS_CONF;
    public static final Identity<HrRecruitStatisticsRecord, Integer> IDENTITY_HR_RECRUIT_STATISTICS = Identities0.IDENTITY_HR_RECRUIT_STATISTICS;
    public static final Identity<HrRecruitUniqueStatisticsRecord, Integer> IDENTITY_HR_RECRUIT_UNIQUE_STATISTICS = Identities0.IDENTITY_HR_RECRUIT_UNIQUE_STATISTICS;
    public static final Identity<HrReferralStatisticsRecord, Integer> IDENTITY_HR_REFERRAL_STATISTICS = Identities0.IDENTITY_HR_REFERRAL_STATISTICS;
    public static final Identity<HrResourceRecord, Integer> IDENTITY_HR_RESOURCE = Identities0.IDENTITY_HR_RESOURCE;
    public static final Identity<HrRuleStatisticsRecord, Integer> IDENTITY_HR_RULE_STATISTICS = Identities0.IDENTITY_HR_RULE_STATISTICS;
    public static final Identity<HrRuleUniqueStatisticsRecord, Integer> IDENTITY_HR_RULE_UNIQUE_STATISTICS = Identities0.IDENTITY_HR_RULE_UNIQUE_STATISTICS;
    public static final Identity<HrSearchConditionRecord, Integer> IDENTITY_HR_SEARCH_CONDITION = Identities0.IDENTITY_HR_SEARCH_CONDITION;
    public static final Identity<HrSuperaccountApplyRecord, Integer> IDENTITY_HR_SUPERACCOUNT_APPLY = Identities0.IDENTITY_HR_SUPERACCOUNT_APPLY;
    public static final Identity<HrTalentpoolRecord, Integer> IDENTITY_HR_TALENTPOOL = Identities0.IDENTITY_HR_TALENTPOOL;
    public static final Identity<HrTeamRecord, Integer> IDENTITY_HR_TEAM = Identities0.IDENTITY_HR_TEAM;
    public static final Identity<HrTeamMemberRecord, Integer> IDENTITY_HR_TEAM_MEMBER = Identities0.IDENTITY_HR_TEAM_MEMBER;
    public static final Identity<HrThirdPartyAccountRecord, Integer> IDENTITY_HR_THIRD_PARTY_ACCOUNT = Identities0.IDENTITY_HR_THIRD_PARTY_ACCOUNT;
    public static final Identity<HrThirdPartyPositionRecord, Integer> IDENTITY_HR_THIRD_PARTY_POSITION = Identities0.IDENTITY_HR_THIRD_PARTY_POSITION;
    public static final Identity<HrTopicRecord, Integer> IDENTITY_HR_TOPIC = Identities0.IDENTITY_HR_TOPIC;
    public static final Identity<HrWxBasicReplyRecord, UInteger> IDENTITY_HR_WX_BASIC_REPLY = Identities0.IDENTITY_HR_WX_BASIC_REPLY;
    public static final Identity<HrWxHrChatRecord, UInteger> IDENTITY_HR_WX_HR_CHAT = Identities0.IDENTITY_HR_WX_HR_CHAT;
    public static final Identity<HrWxHrChatListRecord, UInteger> IDENTITY_HR_WX_HR_CHAT_LIST = Identities0.IDENTITY_HR_WX_HR_CHAT_LIST;
    public static final Identity<HrWxImageReplyRecord, Integer> IDENTITY_HR_WX_IMAGE_REPLY = Identities0.IDENTITY_HR_WX_IMAGE_REPLY;
    public static final Identity<HrWxModuleRecord, UByte> IDENTITY_HR_WX_MODULE = Identities0.IDENTITY_HR_WX_MODULE;
    public static final Identity<HrWxNewsReplyRecord, UInteger> IDENTITY_HR_WX_NEWS_REPLY = Identities0.IDENTITY_HR_WX_NEWS_REPLY;
    public static final Identity<HrWxNoticeMessageRecord, UInteger> IDENTITY_HR_WX_NOTICE_MESSAGE = Identities0.IDENTITY_HR_WX_NOTICE_MESSAGE;
    public static final Identity<HrWxRuleRecord, UInteger> IDENTITY_HR_WX_RULE = Identities0.IDENTITY_HR_WX_RULE;
    public static final Identity<HrWxTemplateMessageRecord, UInteger> IDENTITY_HR_WX_TEMPLATE_MESSAGE = Identities0.IDENTITY_HR_WX_TEMPLATE_MESSAGE;
    public static final Identity<HrWxWechatRecord, UInteger> IDENTITY_HR_WX_WECHAT = Identities0.IDENTITY_HR_WX_WECHAT;
    public static final Identity<HrWxWechatNoticeSyncStatusRecord, UInteger> IDENTITY_HR_WX_WECHAT_NOTICE_SYNC_STATUS = Identities0.IDENTITY_HR_WX_WECHAT_NOTICE_SYNC_STATUS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<HrAppCvConfRecord> KEY_HR_APP_CV_CONF_PRIMARY = UniqueKeys0.KEY_HR_APP_CV_CONF_PRIMARY;
    public static final UniqueKey<HrChildCompanyRecord> KEY_HR_CHILD_COMPANY_PRIMARY = UniqueKeys0.KEY_HR_CHILD_COMPANY_PRIMARY;
    public static final UniqueKey<HrCompanyRecord> KEY_HR_COMPANY_PRIMARY = UniqueKeys0.KEY_HR_COMPANY_PRIMARY;
    public static final UniqueKey<HrCompanyAccountRecord> KEY_HR_COMPANY_ACCOUNT_PRIMARY = UniqueKeys0.KEY_HR_COMPANY_ACCOUNT_PRIMARY;
    public static final UniqueKey<HrCompanyConfRecord> KEY_HR_COMPANY_CONF_PRIMARY = UniqueKeys0.KEY_HR_COMPANY_CONF_PRIMARY;
    public static final UniqueKey<HrEmployeeCertConfRecord> KEY_HR_EMPLOYEE_CERT_CONF_PRIMARY = UniqueKeys0.KEY_HR_EMPLOYEE_CERT_CONF_PRIMARY;
    public static final UniqueKey<HrEmployeeCustomFieldsRecord> KEY_HR_EMPLOYEE_CUSTOM_FIELDS_PRIMARY = UniqueKeys0.KEY_HR_EMPLOYEE_CUSTOM_FIELDS_PRIMARY;
    public static final UniqueKey<HrEmployeePositionRecord> KEY_HR_EMPLOYEE_POSITION_PRIMARY = UniqueKeys0.KEY_HR_EMPLOYEE_POSITION_PRIMARY;
    public static final UniqueKey<HrEmployeeSectionRecord> KEY_HR_EMPLOYEE_SECTION_PRIMARY = UniqueKeys0.KEY_HR_EMPLOYEE_SECTION_PRIMARY;
    public static final UniqueKey<HrFeedbackRecord> KEY_HR_FEEDBACK_PRIMARY = UniqueKeys0.KEY_HR_FEEDBACK_PRIMARY;
    public static final UniqueKey<HrHbConfigRecord> KEY_HR_HB_CONFIG_PRIMARY = UniqueKeys0.KEY_HR_HB_CONFIG_PRIMARY;
    public static final UniqueKey<HrHbItemsRecord> KEY_HR_HB_ITEMS_PRIMARY = UniqueKeys0.KEY_HR_HB_ITEMS_PRIMARY;
    public static final UniqueKey<HrHbPositionBindingRecord> KEY_HR_HB_POSITION_BINDING_PRIMARY = UniqueKeys0.KEY_HR_HB_POSITION_BINDING_PRIMARY;
    public static final UniqueKey<HrHbScratchCardRecord> KEY_HR_HB_SCRATCH_CARD_PRIMARY = UniqueKeys0.KEY_HR_HB_SCRATCH_CARD_PRIMARY;
    public static final UniqueKey<HrHbSendRecordRecord> KEY_HR_HB_SEND_RECORD_PRIMARY = UniqueKeys0.KEY_HR_HB_SEND_RECORD_PRIMARY;
    public static final UniqueKey<HrHtml5StatisticsRecord> KEY_HR_HTML5_STATISTICS_PRIMARY = UniqueKeys0.KEY_HR_HTML5_STATISTICS_PRIMARY;
    public static final UniqueKey<HrHtml5UniqueStatisticsRecord> KEY_HR_HTML5_UNIQUE_STATISTICS_PRIMARY = UniqueKeys0.KEY_HR_HTML5_UNIQUE_STATISTICS_PRIMARY;
    public static final UniqueKey<HrImporterMonitorRecord> KEY_HR_IMPORTER_MONITOR_PRIMARY = UniqueKeys0.KEY_HR_IMPORTER_MONITOR_PRIMARY;
    public static final UniqueKey<HrMediaRecord> KEY_HR_MEDIA_PRIMARY = UniqueKeys0.KEY_HR_MEDIA_PRIMARY;
    public static final UniqueKey<HrOperationRecordRecord> KEY_HR_OPERATION_RECORD_PRIMARY = UniqueKeys0.KEY_HR_OPERATION_RECORD_PRIMARY;
    public static final UniqueKey<HrPointsConfRecord> KEY_HR_POINTS_CONF_PRIMARY = UniqueKeys0.KEY_HR_POINTS_CONF_PRIMARY;
    public static final UniqueKey<HrPointsConfRecord> KEY_HR_POINTS_CONF_STATUS_NAME = UniqueKeys0.KEY_HR_POINTS_CONF_STATUS_NAME;
    public static final UniqueKey<HrRecruitStatisticsRecord> KEY_HR_RECRUIT_STATISTICS_PRIMARY = UniqueKeys0.KEY_HR_RECRUIT_STATISTICS_PRIMARY;
    public static final UniqueKey<HrRecruitUniqueStatisticsRecord> KEY_HR_RECRUIT_UNIQUE_STATISTICS_PRIMARY = UniqueKeys0.KEY_HR_RECRUIT_UNIQUE_STATISTICS_PRIMARY;
    public static final UniqueKey<HrReferralStatisticsRecord> KEY_HR_REFERRAL_STATISTICS_PRIMARY = UniqueKeys0.KEY_HR_REFERRAL_STATISTICS_PRIMARY;
    public static final UniqueKey<HrResourceRecord> KEY_HR_RESOURCE_PRIMARY = UniqueKeys0.KEY_HR_RESOURCE_PRIMARY;
    public static final UniqueKey<HrRuleStatisticsRecord> KEY_HR_RULE_STATISTICS_PRIMARY = UniqueKeys0.KEY_HR_RULE_STATISTICS_PRIMARY;
    public static final UniqueKey<HrRuleUniqueStatisticsRecord> KEY_HR_RULE_UNIQUE_STATISTICS_PRIMARY = UniqueKeys0.KEY_HR_RULE_UNIQUE_STATISTICS_PRIMARY;
    public static final UniqueKey<HrSearchConditionRecord> KEY_HR_SEARCH_CONDITION_PRIMARY = UniqueKeys0.KEY_HR_SEARCH_CONDITION_PRIMARY;
    public static final UniqueKey<HrSuperaccountApplyRecord> KEY_HR_SUPERACCOUNT_APPLY_PRIMARY = UniqueKeys0.KEY_HR_SUPERACCOUNT_APPLY_PRIMARY;
    public static final UniqueKey<HrTalentpoolRecord> KEY_HR_TALENTPOOL_PRIMARY = UniqueKeys0.KEY_HR_TALENTPOOL_PRIMARY;
    public static final UniqueKey<HrTeamRecord> KEY_HR_TEAM_PRIMARY = UniqueKeys0.KEY_HR_TEAM_PRIMARY;
    public static final UniqueKey<HrTeamMemberRecord> KEY_HR_TEAM_MEMBER_PRIMARY = UniqueKeys0.KEY_HR_TEAM_MEMBER_PRIMARY;
    public static final UniqueKey<HrThirdPartyAccountRecord> KEY_HR_THIRD_PARTY_ACCOUNT_PRIMARY = UniqueKeys0.KEY_HR_THIRD_PARTY_ACCOUNT_PRIMARY;
    public static final UniqueKey<HrThirdPartyAccountRecord> KEY_HR_THIRD_PARTY_ACCOUNT_IDX_CHANNEL_COMPYID = UniqueKeys0.KEY_HR_THIRD_PARTY_ACCOUNT_IDX_CHANNEL_COMPYID;
    public static final UniqueKey<HrThirdPartyPositionRecord> KEY_HR_THIRD_PARTY_POSITION_PRIMARY = UniqueKeys0.KEY_HR_THIRD_PARTY_POSITION_PRIMARY;
    public static final UniqueKey<HrTopicRecord> KEY_HR_TOPIC_PRIMARY = UniqueKeys0.KEY_HR_TOPIC_PRIMARY;
    public static final UniqueKey<HrWxBasicReplyRecord> KEY_HR_WX_BASIC_REPLY_PRIMARY = UniqueKeys0.KEY_HR_WX_BASIC_REPLY_PRIMARY;
    public static final UniqueKey<HrWxHrChatRecord> KEY_HR_WX_HR_CHAT_PRIMARY = UniqueKeys0.KEY_HR_WX_HR_CHAT_PRIMARY;
    public static final UniqueKey<HrWxHrChatListRecord> KEY_HR_WX_HR_CHAT_LIST_PRIMARY = UniqueKeys0.KEY_HR_WX_HR_CHAT_LIST_PRIMARY;
    public static final UniqueKey<HrWxImageReplyRecord> KEY_HR_WX_IMAGE_REPLY_PRIMARY = UniqueKeys0.KEY_HR_WX_IMAGE_REPLY_PRIMARY;
    public static final UniqueKey<HrWxModuleRecord> KEY_HR_WX_MODULE_PRIMARY = UniqueKeys0.KEY_HR_WX_MODULE_PRIMARY;
    public static final UniqueKey<HrWxNewsReplyRecord> KEY_HR_WX_NEWS_REPLY_PRIMARY = UniqueKeys0.KEY_HR_WX_NEWS_REPLY_PRIMARY;
    public static final UniqueKey<HrWxNoticeMessageRecord> KEY_HR_WX_NOTICE_MESSAGE_PRIMARY = UniqueKeys0.KEY_HR_WX_NOTICE_MESSAGE_PRIMARY;
    public static final UniqueKey<HrWxRuleRecord> KEY_HR_WX_RULE_PRIMARY = UniqueKeys0.KEY_HR_WX_RULE_PRIMARY;
    public static final UniqueKey<HrWxTemplateMessageRecord> KEY_HR_WX_TEMPLATE_MESSAGE_PRIMARY = UniqueKeys0.KEY_HR_WX_TEMPLATE_MESSAGE_PRIMARY;
    public static final UniqueKey<HrWxWechatRecord> KEY_HR_WX_WECHAT_PRIMARY = UniqueKeys0.KEY_HR_WX_WECHAT_PRIMARY;
    public static final UniqueKey<HrWxWechatNoticeSyncStatusRecord> KEY_HR_WX_WECHAT_NOTICE_SYNC_STATUS_PRIMARY = UniqueKeys0.KEY_HR_WX_WECHAT_NOTICE_SYNC_STATUS_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<HrAppCvConfRecord, Integer> IDENTITY_HR_APP_CV_CONF = createIdentity(HrAppCvConf.HR_APP_CV_CONF, HrAppCvConf.HR_APP_CV_CONF.ID);
        public static Identity<HrChildCompanyRecord, Integer> IDENTITY_HR_CHILD_COMPANY = createIdentity(HrChildCompany.HR_CHILD_COMPANY, HrChildCompany.HR_CHILD_COMPANY.ID);
        public static Identity<HrCompanyRecord, UInteger> IDENTITY_HR_COMPANY = createIdentity(HrCompany.HR_COMPANY, HrCompany.HR_COMPANY.ID);
        public static Identity<HrEmployeeCertConfRecord, Integer> IDENTITY_HR_EMPLOYEE_CERT_CONF = createIdentity(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF, HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.ID);
        public static Identity<HrEmployeeCustomFieldsRecord, Integer> IDENTITY_HR_EMPLOYEE_CUSTOM_FIELDS = createIdentity(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS, HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.ID);
        public static Identity<HrEmployeePositionRecord, Integer> IDENTITY_HR_EMPLOYEE_POSITION = createIdentity(HrEmployeePosition.HR_EMPLOYEE_POSITION, HrEmployeePosition.HR_EMPLOYEE_POSITION.ID);
        public static Identity<HrEmployeeSectionRecord, Integer> IDENTITY_HR_EMPLOYEE_SECTION = createIdentity(HrEmployeeSection.HR_EMPLOYEE_SECTION, HrEmployeeSection.HR_EMPLOYEE_SECTION.ID);
        public static Identity<HrFeedbackRecord, UInteger> IDENTITY_HR_FEEDBACK = createIdentity(HrFeedback.HR_FEEDBACK, HrFeedback.HR_FEEDBACK.ID);
        public static Identity<HrHbConfigRecord, Integer> IDENTITY_HR_HB_CONFIG = createIdentity(HrHbConfig.HR_HB_CONFIG, HrHbConfig.HR_HB_CONFIG.ID);
        public static Identity<HrHbItemsRecord, Integer> IDENTITY_HR_HB_ITEMS = createIdentity(HrHbItems.HR_HB_ITEMS, HrHbItems.HR_HB_ITEMS.ID);
        public static Identity<HrHbPositionBindingRecord, Integer> IDENTITY_HR_HB_POSITION_BINDING = createIdentity(HrHbPositionBinding.HR_HB_POSITION_BINDING, HrHbPositionBinding.HR_HB_POSITION_BINDING.ID);
        public static Identity<HrHbScratchCardRecord, Integer> IDENTITY_HR_HB_SCRATCH_CARD = createIdentity(HrHbScratchCard.HR_HB_SCRATCH_CARD, HrHbScratchCard.HR_HB_SCRATCH_CARD.ID);
        public static Identity<HrHbSendRecordRecord, Integer> IDENTITY_HR_HB_SEND_RECORD = createIdentity(HrHbSendRecord.HR_HB_SEND_RECORD, HrHbSendRecord.HR_HB_SEND_RECORD.ID);
        public static Identity<HrHtml5StatisticsRecord, Integer> IDENTITY_HR_HTML5_STATISTICS = createIdentity(HrHtml5Statistics.HR_HTML5_STATISTICS, HrHtml5Statistics.HR_HTML5_STATISTICS.ID);
        public static Identity<HrHtml5UniqueStatisticsRecord, Integer> IDENTITY_HR_HTML5_UNIQUE_STATISTICS = createIdentity(HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS, HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS.ID);
        public static Identity<HrImporterMonitorRecord, Integer> IDENTITY_HR_IMPORTER_MONITOR = createIdentity(HrImporterMonitor.HR_IMPORTER_MONITOR, HrImporterMonitor.HR_IMPORTER_MONITOR.ID);
        public static Identity<HrMediaRecord, Integer> IDENTITY_HR_MEDIA = createIdentity(HrMedia.HR_MEDIA, HrMedia.HR_MEDIA.ID);
        public static Identity<HrOperationRecordRecord, Integer> IDENTITY_HR_OPERATION_RECORD = createIdentity(HrOperationRecord.HR_OPERATION_RECORD, HrOperationRecord.HR_OPERATION_RECORD.ID);
        public static Identity<HrPointsConfRecord, Integer> IDENTITY_HR_POINTS_CONF = createIdentity(HrPointsConf.HR_POINTS_CONF, HrPointsConf.HR_POINTS_CONF.ID);
        public static Identity<HrRecruitStatisticsRecord, Integer> IDENTITY_HR_RECRUIT_STATISTICS = createIdentity(HrRecruitStatistics.HR_RECRUIT_STATISTICS, HrRecruitStatistics.HR_RECRUIT_STATISTICS.ID);
        public static Identity<HrRecruitUniqueStatisticsRecord, Integer> IDENTITY_HR_RECRUIT_UNIQUE_STATISTICS = createIdentity(HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS, HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.ID);
        public static Identity<HrReferralStatisticsRecord, Integer> IDENTITY_HR_REFERRAL_STATISTICS = createIdentity(HrReferralStatistics.HR_REFERRAL_STATISTICS, HrReferralStatistics.HR_REFERRAL_STATISTICS.ID);
        public static Identity<HrResourceRecord, Integer> IDENTITY_HR_RESOURCE = createIdentity(HrResource.HR_RESOURCE, HrResource.HR_RESOURCE.ID);
        public static Identity<HrRuleStatisticsRecord, Integer> IDENTITY_HR_RULE_STATISTICS = createIdentity(HrRuleStatistics.HR_RULE_STATISTICS, HrRuleStatistics.HR_RULE_STATISTICS.ID);
        public static Identity<HrRuleUniqueStatisticsRecord, Integer> IDENTITY_HR_RULE_UNIQUE_STATISTICS = createIdentity(HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS, HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.ID);
        public static Identity<HrSearchConditionRecord, Integer> IDENTITY_HR_SEARCH_CONDITION = createIdentity(HrSearchCondition.HR_SEARCH_CONDITION, HrSearchCondition.HR_SEARCH_CONDITION.ID);
        public static Identity<HrSuperaccountApplyRecord, Integer> IDENTITY_HR_SUPERACCOUNT_APPLY = createIdentity(HrSuperaccountApply.HR_SUPERACCOUNT_APPLY, HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.ID);
        public static Identity<HrTalentpoolRecord, Integer> IDENTITY_HR_TALENTPOOL = createIdentity(HrTalentpool.HR_TALENTPOOL, HrTalentpool.HR_TALENTPOOL.ID);
        public static Identity<HrTeamRecord, Integer> IDENTITY_HR_TEAM = createIdentity(HrTeam.HR_TEAM, HrTeam.HR_TEAM.ID);
        public static Identity<HrTeamMemberRecord, Integer> IDENTITY_HR_TEAM_MEMBER = createIdentity(HrTeamMember.HR_TEAM_MEMBER, HrTeamMember.HR_TEAM_MEMBER.ID);
        public static Identity<HrThirdPartyAccountRecord, Integer> IDENTITY_HR_THIRD_PARTY_ACCOUNT = createIdentity(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID);
        public static Identity<HrThirdPartyPositionRecord, Integer> IDENTITY_HR_THIRD_PARTY_POSITION = createIdentity(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION, HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.ID);
        public static Identity<HrTopicRecord, Integer> IDENTITY_HR_TOPIC = createIdentity(HrTopic.HR_TOPIC, HrTopic.HR_TOPIC.ID);
        public static Identity<HrWxBasicReplyRecord, UInteger> IDENTITY_HR_WX_BASIC_REPLY = createIdentity(HrWxBasicReply.HR_WX_BASIC_REPLY, HrWxBasicReply.HR_WX_BASIC_REPLY.ID);
        public static Identity<HrWxHrChatRecord, UInteger> IDENTITY_HR_WX_HR_CHAT = createIdentity(HrWxHrChat.HR_WX_HR_CHAT, HrWxHrChat.HR_WX_HR_CHAT.ID);
        public static Identity<HrWxHrChatListRecord, UInteger> IDENTITY_HR_WX_HR_CHAT_LIST = createIdentity(HrWxHrChatList.HR_WX_HR_CHAT_LIST, HrWxHrChatList.HR_WX_HR_CHAT_LIST.ID);
        public static Identity<HrWxImageReplyRecord, Integer> IDENTITY_HR_WX_IMAGE_REPLY = createIdentity(HrWxImageReply.HR_WX_IMAGE_REPLY, HrWxImageReply.HR_WX_IMAGE_REPLY.ID);
        public static Identity<HrWxModuleRecord, UByte> IDENTITY_HR_WX_MODULE = createIdentity(HrWxModule.HR_WX_MODULE, HrWxModule.HR_WX_MODULE.ID);
        public static Identity<HrWxNewsReplyRecord, UInteger> IDENTITY_HR_WX_NEWS_REPLY = createIdentity(HrWxNewsReply.HR_WX_NEWS_REPLY, HrWxNewsReply.HR_WX_NEWS_REPLY.ID);
        public static Identity<HrWxNoticeMessageRecord, UInteger> IDENTITY_HR_WX_NOTICE_MESSAGE = createIdentity(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE, HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.ID);
        public static Identity<HrWxRuleRecord, UInteger> IDENTITY_HR_WX_RULE = createIdentity(HrWxRule.HR_WX_RULE, HrWxRule.HR_WX_RULE.ID);
        public static Identity<HrWxTemplateMessageRecord, UInteger> IDENTITY_HR_WX_TEMPLATE_MESSAGE = createIdentity(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE, HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.ID);
        public static Identity<HrWxWechatRecord, UInteger> IDENTITY_HR_WX_WECHAT = createIdentity(HrWxWechat.HR_WX_WECHAT, HrWxWechat.HR_WX_WECHAT.ID);
        public static Identity<HrWxWechatNoticeSyncStatusRecord, UInteger> IDENTITY_HR_WX_WECHAT_NOTICE_SYNC_STATUS = createIdentity(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS, HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<HrAppCvConfRecord> KEY_HR_APP_CV_CONF_PRIMARY = createUniqueKey(HrAppCvConf.HR_APP_CV_CONF, "KEY_hr_app_cv_conf_PRIMARY", HrAppCvConf.HR_APP_CV_CONF.ID);
        public static final UniqueKey<HrChildCompanyRecord> KEY_HR_CHILD_COMPANY_PRIMARY = createUniqueKey(HrChildCompany.HR_CHILD_COMPANY, "KEY_hr_child_company_PRIMARY", HrChildCompany.HR_CHILD_COMPANY.ID);
        public static final UniqueKey<HrCompanyRecord> KEY_HR_COMPANY_PRIMARY = createUniqueKey(HrCompany.HR_COMPANY, "KEY_hr_company_PRIMARY", HrCompany.HR_COMPANY.ID);
        public static final UniqueKey<HrCompanyAccountRecord> KEY_HR_COMPANY_ACCOUNT_PRIMARY = createUniqueKey(HrCompanyAccount.HR_COMPANY_ACCOUNT, "KEY_hr_company_account_PRIMARY", HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID);
        public static final UniqueKey<HrCompanyConfRecord> KEY_HR_COMPANY_CONF_PRIMARY = createUniqueKey(HrCompanyConf.HR_COMPANY_CONF, "KEY_hr_company_conf_PRIMARY", HrCompanyConf.HR_COMPANY_CONF.COMPANY_ID);
        public static final UniqueKey<HrEmployeeCertConfRecord> KEY_HR_EMPLOYEE_CERT_CONF_PRIMARY = createUniqueKey(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF, "KEY_hr_employee_cert_conf_PRIMARY", HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.ID);
        public static final UniqueKey<HrEmployeeCustomFieldsRecord> KEY_HR_EMPLOYEE_CUSTOM_FIELDS_PRIMARY = createUniqueKey(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS, "KEY_hr_employee_custom_fields_PRIMARY", HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.ID);
        public static final UniqueKey<HrEmployeePositionRecord> KEY_HR_EMPLOYEE_POSITION_PRIMARY = createUniqueKey(HrEmployeePosition.HR_EMPLOYEE_POSITION, "KEY_hr_employee_position_PRIMARY", HrEmployeePosition.HR_EMPLOYEE_POSITION.ID);
        public static final UniqueKey<HrEmployeeSectionRecord> KEY_HR_EMPLOYEE_SECTION_PRIMARY = createUniqueKey(HrEmployeeSection.HR_EMPLOYEE_SECTION, "KEY_hr_employee_section_PRIMARY", HrEmployeeSection.HR_EMPLOYEE_SECTION.ID);
        public static final UniqueKey<HrFeedbackRecord> KEY_HR_FEEDBACK_PRIMARY = createUniqueKey(HrFeedback.HR_FEEDBACK, "KEY_hr_feedback_PRIMARY", HrFeedback.HR_FEEDBACK.ID);
        public static final UniqueKey<HrHbConfigRecord> KEY_HR_HB_CONFIG_PRIMARY = createUniqueKey(HrHbConfig.HR_HB_CONFIG, "KEY_hr_hb_config_PRIMARY", HrHbConfig.HR_HB_CONFIG.ID);
        public static final UniqueKey<HrHbItemsRecord> KEY_HR_HB_ITEMS_PRIMARY = createUniqueKey(HrHbItems.HR_HB_ITEMS, "KEY_hr_hb_items_PRIMARY", HrHbItems.HR_HB_ITEMS.ID);
        public static final UniqueKey<HrHbPositionBindingRecord> KEY_HR_HB_POSITION_BINDING_PRIMARY = createUniqueKey(HrHbPositionBinding.HR_HB_POSITION_BINDING, "KEY_hr_hb_position_binding_PRIMARY", HrHbPositionBinding.HR_HB_POSITION_BINDING.ID);
        public static final UniqueKey<HrHbScratchCardRecord> KEY_HR_HB_SCRATCH_CARD_PRIMARY = createUniqueKey(HrHbScratchCard.HR_HB_SCRATCH_CARD, "KEY_hr_hb_scratch_card_PRIMARY", HrHbScratchCard.HR_HB_SCRATCH_CARD.ID);
        public static final UniqueKey<HrHbSendRecordRecord> KEY_HR_HB_SEND_RECORD_PRIMARY = createUniqueKey(HrHbSendRecord.HR_HB_SEND_RECORD, "KEY_hr_hb_send_record_PRIMARY", HrHbSendRecord.HR_HB_SEND_RECORD.ID);
        public static final UniqueKey<HrHtml5StatisticsRecord> KEY_HR_HTML5_STATISTICS_PRIMARY = createUniqueKey(HrHtml5Statistics.HR_HTML5_STATISTICS, "KEY_hr_html5_statistics_PRIMARY", HrHtml5Statistics.HR_HTML5_STATISTICS.ID);
        public static final UniqueKey<HrHtml5UniqueStatisticsRecord> KEY_HR_HTML5_UNIQUE_STATISTICS_PRIMARY = createUniqueKey(HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS, "KEY_hr_html5_unique_statistics_PRIMARY", HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS.ID);
        public static final UniqueKey<HrImporterMonitorRecord> KEY_HR_IMPORTER_MONITOR_PRIMARY = createUniqueKey(HrImporterMonitor.HR_IMPORTER_MONITOR, "KEY_hr_importer_monitor_PRIMARY", HrImporterMonitor.HR_IMPORTER_MONITOR.ID);
        public static final UniqueKey<HrMediaRecord> KEY_HR_MEDIA_PRIMARY = createUniqueKey(HrMedia.HR_MEDIA, "KEY_hr_media_PRIMARY", HrMedia.HR_MEDIA.ID);
        public static final UniqueKey<HrOperationRecordRecord> KEY_HR_OPERATION_RECORD_PRIMARY = createUniqueKey(HrOperationRecord.HR_OPERATION_RECORD, "KEY_hr_operation_record_PRIMARY", HrOperationRecord.HR_OPERATION_RECORD.ID);
        public static final UniqueKey<HrPointsConfRecord> KEY_HR_POINTS_CONF_PRIMARY = createUniqueKey(HrPointsConf.HR_POINTS_CONF, "KEY_hr_points_conf_PRIMARY", HrPointsConf.HR_POINTS_CONF.ID);
        public static final UniqueKey<HrPointsConfRecord> KEY_HR_POINTS_CONF_STATUS_NAME = createUniqueKey(HrPointsConf.HR_POINTS_CONF, "KEY_hr_points_conf_status_name", HrPointsConf.HR_POINTS_CONF.COMPANY_ID, HrPointsConf.HR_POINTS_CONF.STATUS_NAME);
        public static final UniqueKey<HrRecruitStatisticsRecord> KEY_HR_RECRUIT_STATISTICS_PRIMARY = createUniqueKey(HrRecruitStatistics.HR_RECRUIT_STATISTICS, "KEY_hr_recruit_statistics_PRIMARY", HrRecruitStatistics.HR_RECRUIT_STATISTICS.ID);
        public static final UniqueKey<HrRecruitUniqueStatisticsRecord> KEY_HR_RECRUIT_UNIQUE_STATISTICS_PRIMARY = createUniqueKey(HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS, "KEY_hr_recruit_unique_statistics_PRIMARY", HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS.ID);
        public static final UniqueKey<HrReferralStatisticsRecord> KEY_HR_REFERRAL_STATISTICS_PRIMARY = createUniqueKey(HrReferralStatistics.HR_REFERRAL_STATISTICS, "KEY_hr_referral_statistics_PRIMARY", HrReferralStatistics.HR_REFERRAL_STATISTICS.ID);
        public static final UniqueKey<HrResourceRecord> KEY_HR_RESOURCE_PRIMARY = createUniqueKey(HrResource.HR_RESOURCE, "KEY_hr_resource_PRIMARY", HrResource.HR_RESOURCE.ID);
        public static final UniqueKey<HrRuleStatisticsRecord> KEY_HR_RULE_STATISTICS_PRIMARY = createUniqueKey(HrRuleStatistics.HR_RULE_STATISTICS, "KEY_hr_rule_statistics_PRIMARY", HrRuleStatistics.HR_RULE_STATISTICS.ID);
        public static final UniqueKey<HrRuleUniqueStatisticsRecord> KEY_HR_RULE_UNIQUE_STATISTICS_PRIMARY = createUniqueKey(HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS, "KEY_hr_rule_unique_statistics_PRIMARY", HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS.ID);
        public static final UniqueKey<HrSearchConditionRecord> KEY_HR_SEARCH_CONDITION_PRIMARY = createUniqueKey(HrSearchCondition.HR_SEARCH_CONDITION, "KEY_hr_search_condition_PRIMARY", HrSearchCondition.HR_SEARCH_CONDITION.ID);
        public static final UniqueKey<HrSuperaccountApplyRecord> KEY_HR_SUPERACCOUNT_APPLY_PRIMARY = createUniqueKey(HrSuperaccountApply.HR_SUPERACCOUNT_APPLY, "KEY_hr_superaccount_apply_PRIMARY", HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.ID);
        public static final UniqueKey<HrTalentpoolRecord> KEY_HR_TALENTPOOL_PRIMARY = createUniqueKey(HrTalentpool.HR_TALENTPOOL, "KEY_hr_talentpool_PRIMARY", HrTalentpool.HR_TALENTPOOL.ID);
        public static final UniqueKey<HrTeamRecord> KEY_HR_TEAM_PRIMARY = createUniqueKey(HrTeam.HR_TEAM, "KEY_hr_team_PRIMARY", HrTeam.HR_TEAM.ID);
        public static final UniqueKey<HrTeamMemberRecord> KEY_HR_TEAM_MEMBER_PRIMARY = createUniqueKey(HrTeamMember.HR_TEAM_MEMBER, "KEY_hr_team_member_PRIMARY", HrTeamMember.HR_TEAM_MEMBER.ID);
        public static final UniqueKey<HrThirdPartyAccountRecord> KEY_HR_THIRD_PARTY_ACCOUNT_PRIMARY = createUniqueKey(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT, "KEY_hr_third_party_account_PRIMARY", HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID);
        public static final UniqueKey<HrThirdPartyAccountRecord> KEY_HR_THIRD_PARTY_ACCOUNT_IDX_CHANNEL_COMPYID = createUniqueKey(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT, "KEY_hr_third_party_account_idx_channel_compyid", HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL, HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.COMPANY_ID);
        public static final UniqueKey<HrThirdPartyPositionRecord> KEY_HR_THIRD_PARTY_POSITION_PRIMARY = createUniqueKey(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION, "KEY_hr_third_party_position_PRIMARY", HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.ID);
        public static final UniqueKey<HrTopicRecord> KEY_HR_TOPIC_PRIMARY = createUniqueKey(HrTopic.HR_TOPIC, "KEY_hr_topic_PRIMARY", HrTopic.HR_TOPIC.ID);
        public static final UniqueKey<HrWxBasicReplyRecord> KEY_HR_WX_BASIC_REPLY_PRIMARY = createUniqueKey(HrWxBasicReply.HR_WX_BASIC_REPLY, "KEY_hr_wx_basic_reply_PRIMARY", HrWxBasicReply.HR_WX_BASIC_REPLY.ID);
        public static final UniqueKey<HrWxHrChatRecord> KEY_HR_WX_HR_CHAT_PRIMARY = createUniqueKey(HrWxHrChat.HR_WX_HR_CHAT, "KEY_hr_wx_hr_chat_PRIMARY", HrWxHrChat.HR_WX_HR_CHAT.ID);
        public static final UniqueKey<HrWxHrChatListRecord> KEY_HR_WX_HR_CHAT_LIST_PRIMARY = createUniqueKey(HrWxHrChatList.HR_WX_HR_CHAT_LIST, "KEY_hr_wx_hr_chat_list_PRIMARY", HrWxHrChatList.HR_WX_HR_CHAT_LIST.ID);
        public static final UniqueKey<HrWxImageReplyRecord> KEY_HR_WX_IMAGE_REPLY_PRIMARY = createUniqueKey(HrWxImageReply.HR_WX_IMAGE_REPLY, "KEY_hr_wx_image_reply_PRIMARY", HrWxImageReply.HR_WX_IMAGE_REPLY.ID);
        public static final UniqueKey<HrWxModuleRecord> KEY_HR_WX_MODULE_PRIMARY = createUniqueKey(HrWxModule.HR_WX_MODULE, "KEY_hr_wx_module_PRIMARY", HrWxModule.HR_WX_MODULE.ID);
        public static final UniqueKey<HrWxNewsReplyRecord> KEY_HR_WX_NEWS_REPLY_PRIMARY = createUniqueKey(HrWxNewsReply.HR_WX_NEWS_REPLY, "KEY_hr_wx_news_reply_PRIMARY", HrWxNewsReply.HR_WX_NEWS_REPLY.ID);
        public static final UniqueKey<HrWxNoticeMessageRecord> KEY_HR_WX_NOTICE_MESSAGE_PRIMARY = createUniqueKey(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE, "KEY_hr_wx_notice_message_PRIMARY", HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.ID);
        public static final UniqueKey<HrWxRuleRecord> KEY_HR_WX_RULE_PRIMARY = createUniqueKey(HrWxRule.HR_WX_RULE, "KEY_hr_wx_rule_PRIMARY", HrWxRule.HR_WX_RULE.ID);
        public static final UniqueKey<HrWxTemplateMessageRecord> KEY_HR_WX_TEMPLATE_MESSAGE_PRIMARY = createUniqueKey(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE, "KEY_hr_wx_template_message_PRIMARY", HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.ID);
        public static final UniqueKey<HrWxWechatRecord> KEY_HR_WX_WECHAT_PRIMARY = createUniqueKey(HrWxWechat.HR_WX_WECHAT, "KEY_hr_wx_wechat_PRIMARY", HrWxWechat.HR_WX_WECHAT.ID);
        public static final UniqueKey<HrWxWechatNoticeSyncStatusRecord> KEY_HR_WX_WECHAT_NOTICE_SYNC_STATUS_PRIMARY = createUniqueKey(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS, "KEY_hr_wx_wechat_notice_sync_status_PRIMARY", HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS.ID);
    }
}
