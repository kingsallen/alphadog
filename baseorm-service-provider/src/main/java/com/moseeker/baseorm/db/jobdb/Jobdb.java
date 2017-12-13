/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb;


import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobApplicationAts;
import com.moseeker.baseorm.db.jobdb.tables.JobCustom;
import com.moseeker.baseorm.db.jobdb.tables.JobOccupation;
import com.moseeker.baseorm.db.jobdb.tables.JobPcAdvertisement;
import com.moseeker.baseorm.db.jobdb.tables.JobPcRecommendPositionItem;
import com.moseeker.baseorm.db.jobdb.tables.JobPcRecommendPositionsModule;
import com.moseeker.baseorm.db.jobdb.tables.JobPcReported;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionExt;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionRecomRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionShareTplConf;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionTopic;
import com.moseeker.baseorm.db.jobdb.tables.JobResumeOther;
import com.moseeker.baseorm.db.jobdb.tables.UserProfileJobapply;

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
public class Jobdb extends SchemaImpl {

    private static final long serialVersionUID = 1754882701;

    /**
     * The reference instance of <code>jobdb</code>
     */
    public static final Jobdb JOBDB = new Jobdb();

    /**
     * The table <code>jobdb.job_application</code>.
     */
    public final JobApplication JOB_APPLICATION = com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION;

    /**
     * MoSeeker与ATS渠道申请编号对应关系
     */
    public final JobApplicationAts JOB_APPLICATION_ATS = com.moseeker.baseorm.db.jobdb.tables.JobApplicationAts.JOB_APPLICATION_ATS;

    /**
     * 职位自定义字段配置表
     */
    public final JobCustom JOB_CUSTOM = com.moseeker.baseorm.db.jobdb.tables.JobCustom.JOB_CUSTOM;

    /**
     * 公司自定义职能表
     */
    public final JobOccupation JOB_OCCUPATION = com.moseeker.baseorm.db.jobdb.tables.JobOccupation.JOB_OCCUPATION;

    /**
     * 首页广告位数据表设计
     */
    public final JobPcAdvertisement JOB_PC_ADVERTISEMENT = com.moseeker.baseorm.db.jobdb.tables.JobPcAdvertisement.JOB_PC_ADVERTISEMENT;

    /**
     * 推荐列表数据库设计
     */
    public final JobPcRecommendPositionsModule JOB_PC_RECOMMEND_POSITIONS_MODULE = com.moseeker.baseorm.db.jobdb.tables.JobPcRecommendPositionsModule.JOB_PC_RECOMMEND_POSITIONS_MODULE;

    /**
     * 推荐职位明细关系表
     */
    public final JobPcRecommendPositionItem JOB_PC_RECOMMEND_POSITION_ITEM = com.moseeker.baseorm.db.jobdb.tables.JobPcRecommendPositionItem.JOB_PC_RECOMMEND_POSITION_ITEM;

    /**
     * 被举报职位数据表
     */
    public final JobPcReported JOB_PC_REPORTED = com.moseeker.baseorm.db.jobdb.tables.JobPcReported.JOB_PC_REPORTED;

    /**
     * The table <code>jobdb.job_position</code>.
     */
    public final JobPosition JOB_POSITION = com.moseeker.baseorm.db.jobdb.tables.JobPosition.JOB_POSITION;

    /**
     * The table <code>jobdb.job_position_city</code>.
     */
    public final JobPositionCity JOB_POSITION_CITY = com.moseeker.baseorm.db.jobdb.tables.JobPositionCity.JOB_POSITION_CITY;

    /**
     * 职位信息扩展表
     */
    public final JobPositionExt JOB_POSITION_EXT = com.moseeker.baseorm.db.jobdb.tables.JobPositionExt.JOB_POSITION_EXT;

    /**
     * 智能画像职位推送记录，用于微信转发
     */
    public final JobPositionRecomRecord JOB_POSITION_RECOM_RECORD = com.moseeker.baseorm.db.jobdb.tables.JobPositionRecomRecord.JOB_POSITION_RECOM_RECORD;

    /**
     * 职位分享描述配置模板
     */
    public final JobPositionShareTplConf JOB_POSITION_SHARE_TPL_CONF = com.moseeker.baseorm.db.jobdb.tables.JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF;

    /**
     * 职位主题活动关系表
     */
    public final JobPositionTopic JOB_POSITION_TOPIC = com.moseeker.baseorm.db.jobdb.tables.JobPositionTopic.JOB_POSITION_TOPIC;

    /**
     * 自定义简历副本记录表
     */
    public final JobResumeOther JOB_RESUME_OTHER = com.moseeker.baseorm.db.jobdb.tables.JobResumeOther.JOB_RESUME_OTHER;

    /**
     * VIEW
     */
    public final UserProfileJobapply USER_PROFILE_JOBAPPLY = com.moseeker.baseorm.db.jobdb.tables.UserProfileJobapply.USER_PROFILE_JOBAPPLY;

    /**
     * No further instances allowed
     */
    private Jobdb() {
        super("jobdb", null);
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
            JobApplication.JOB_APPLICATION,
            JobApplicationAts.JOB_APPLICATION_ATS,
            JobCustom.JOB_CUSTOM,
            JobOccupation.JOB_OCCUPATION,
            JobPcAdvertisement.JOB_PC_ADVERTISEMENT,
            JobPcRecommendPositionsModule.JOB_PC_RECOMMEND_POSITIONS_MODULE,
            JobPcRecommendPositionItem.JOB_PC_RECOMMEND_POSITION_ITEM,
            JobPcReported.JOB_PC_REPORTED,
            JobPosition.JOB_POSITION,
            JobPositionCity.JOB_POSITION_CITY,
            JobPositionExt.JOB_POSITION_EXT,
            JobPositionRecomRecord.JOB_POSITION_RECOM_RECORD,
            JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF,
            JobPositionTopic.JOB_POSITION_TOPIC,
            JobResumeOther.JOB_RESUME_OTHER,
            UserProfileJobapply.USER_PROFILE_JOBAPPLY);
    }
}
