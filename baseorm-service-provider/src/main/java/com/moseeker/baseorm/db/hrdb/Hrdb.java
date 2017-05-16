/**
 * This class is generated by jOOQ
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
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Hrdb extends SchemaImpl {

	private static final long serialVersionUID = -1855564758;

	/**
	 * The reference instance of <code>hrdb</code>
	 */
	public static final Hrdb HRDB = new Hrdb();

	/**
	 * No further instances allowed
	 */
	private Hrdb() {
		super("hrdb");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
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
			HrOperationRecord.HR_OPERATION_RECORD,
			HrPointsConf.HR_POINTS_CONF,
			HrRecruitStatistics.HR_RECRUIT_STATISTICS,
			HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS,
			HrReferralStatistics.HR_REFERRAL_STATISTICS,
			HrResource.HR_RESOURCE,
			HrRuleStatistics.HR_RULE_STATISTICS,
			HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS,
			HrSearchCondition.HR_SEARCH_CONDITION,
			HrSuperaccountApply.HR_SUPERACCOUNT_APPLY,
			HrTalentpool.HR_TALENTPOOL,
			HrTeam.HR_TEAM,
			HrTeamMember.HR_TEAM_MEMBER,
			HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT,
			HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR,
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
