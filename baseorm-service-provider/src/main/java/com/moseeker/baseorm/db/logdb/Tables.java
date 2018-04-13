/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.logdb;


import com.moseeker.baseorm.db.logdb.tables.LogCronjob;
import com.moseeker.baseorm.db.logdb.tables.LogDeadLetter;
import com.moseeker.baseorm.db.logdb.tables.LogEmailSendrecord;
import com.moseeker.baseorm.db.logdb.tables.LogHrOperationRecord;
import com.moseeker.baseorm.db.logdb.tables.LogHrloginRecord;
import com.moseeker.baseorm.db.logdb.tables.LogJbEmailparseRecord;
import com.moseeker.baseorm.db.logdb.tables.LogResumeRecord;
import com.moseeker.baseorm.db.logdb.tables.LogScraperRecord;
import com.moseeker.baseorm.db.logdb.tables.LogSmsSendrecord;
import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailLog;
import com.moseeker.baseorm.db.logdb.tables.LogTalentpoolProfileFilterLog;
import com.moseeker.baseorm.db.logdb.tables.LogWxMenuRecord;
import com.moseeker.baseorm.db.logdb.tables.LogWxMessageRecord;
import com.moseeker.baseorm.db.logdb.tables.LogWxTemplateMessageSendrecord;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in logdb
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
     * The table <code>logdb.log_cronjob</code>.
     */
    public static final LogCronjob LOG_CRONJOB = com.moseeker.baseorm.db.logdb.tables.LogCronjob.LOG_CRONJOB;

    /**
     * 死信队列日志记录表
     */
    public static final LogDeadLetter LOG_DEAD_LETTER = com.moseeker.baseorm.db.logdb.tables.LogDeadLetter.LOG_DEAD_LETTER;

    /**
     * 短信发送记录表
     */
    public static final LogEmailSendrecord LOG_EMAIL_SENDRECORD = com.moseeker.baseorm.db.logdb.tables.LogEmailSendrecord.LOG_EMAIL_SENDRECORD;

    /**
     * hr每日登陆/使用统计表
     */
    public static final LogHrloginRecord LOG_HRLOGIN_RECORD = com.moseeker.baseorm.db.logdb.tables.LogHrloginRecord.LOG_HRLOGIN_RECORD;

    /**
     * HR操作记录表
     */
    public static final LogHrOperationRecord LOG_HR_OPERATION_RECORD = com.moseeker.baseorm.db.logdb.tables.LogHrOperationRecord.LOG_HR_OPERATION_RECORD;

    /**
     * 第三方简历回流email解析日志
     */
    public static final LogJbEmailparseRecord LOG_JB_EMAILPARSE_RECORD = com.moseeker.baseorm.db.logdb.tables.LogJbEmailparseRecord.LOG_JB_EMAILPARSE_RECORD;

    /**
     * The table <code>logdb.log_resume_record</code>.
     */
    public static final LogResumeRecord LOG_RESUME_RECORD = com.moseeker.baseorm.db.logdb.tables.LogResumeRecord.LOG_RESUME_RECORD;

    /**
     * c端导入日志明细表
     */
    public static final LogScraperRecord LOG_SCRAPER_RECORD = com.moseeker.baseorm.db.logdb.tables.LogScraperRecord.LOG_SCRAPER_RECORD;

    /**
     * 短信发送记录表
     */
    public static final LogSmsSendrecord LOG_SMS_SENDRECORD = com.moseeker.baseorm.db.logdb.tables.LogSmsSendrecord.LOG_SMS_SENDRECORD;

    /**
     * 邮件日志
     */
    public static final LogTalentpoolEmailLog LOG_TALENTPOOL_EMAIL_LOG = com.moseeker.baseorm.db.logdb.tables.LogTalentpoolEmailLog.LOG_TALENTPOOL_EMAIL_LOG;

    /**
     * 人才库简历过滤日志
     */
    public static final LogTalentpoolProfileFilterLog LOG_TALENTPOOL_PROFILE_FILTER_LOG = com.moseeker.baseorm.db.logdb.tables.LogTalentpoolProfileFilterLog.LOG_TALENTPOOL_PROFILE_FILTER_LOG;

    /**
     * 微信菜单操作日志表
     */
    public static final LogWxMenuRecord LOG_WX_MENU_RECORD = com.moseeker.baseorm.db.logdb.tables.LogWxMenuRecord.LOG_WX_MENU_RECORD;

    /**
     * 模板消息发送结果记录表
     */
    public static final LogWxMessageRecord LOG_WX_MESSAGE_RECORD = com.moseeker.baseorm.db.logdb.tables.LogWxMessageRecord.LOG_WX_MESSAGE_RECORD;

    /**
     * 模板消息发送结果记录
     */
    public static final LogWxTemplateMessageSendrecord LOG_WX_TEMPLATE_MESSAGE_SENDRECORD = com.moseeker.baseorm.db.logdb.tables.LogWxTemplateMessageSendrecord.LOG_WX_TEMPLATE_MESSAGE_SENDRECORD;
}
