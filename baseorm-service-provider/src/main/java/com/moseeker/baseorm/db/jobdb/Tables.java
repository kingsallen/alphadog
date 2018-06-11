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
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCcmail;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionExt;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionHrCompanyFeature;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionLiepinMapping;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionProfileFilter;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionShareTplConf;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionTopic;
import com.moseeker.baseorm.db.jobdb.tables.JobResumeOther;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in jobdb
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
     * The table <code>jobdb.job_application</code>.
     */
    public static final JobApplication JOB_APPLICATION = JobApplication.JOB_APPLICATION;

    /**
     * MoSeeker与ATS渠道申请编号对应关系
     */
    public static final JobApplicationAts JOB_APPLICATION_ATS = JobApplicationAts.JOB_APPLICATION_ATS;

    /**
     * 职位自定义字段配置表
     */
    public static final JobCustom JOB_CUSTOM = JobCustom.JOB_CUSTOM;

    /**
     * 公司自定义职能表
     */
    public static final JobOccupation JOB_OCCUPATION = JobOccupation.JOB_OCCUPATION;

    /**
     * 首页广告位数据表设计
     */
    public static final JobPcAdvertisement JOB_PC_ADVERTISEMENT = JobPcAdvertisement.JOB_PC_ADVERTISEMENT;

    /**
     * 推荐列表数据库设计
     */
    public static final JobPcRecommendPositionsModule JOB_PC_RECOMMEND_POSITIONS_MODULE = JobPcRecommendPositionsModule.JOB_PC_RECOMMEND_POSITIONS_MODULE;

    /**
     * 推荐职位明细关系表
     */
    public static final JobPcRecommendPositionItem JOB_PC_RECOMMEND_POSITION_ITEM = JobPcRecommendPositionItem.JOB_PC_RECOMMEND_POSITION_ITEM;

    /**
     * 被举报职位数据表
     */
    public static final JobPcReported JOB_PC_REPORTED = JobPcReported.JOB_PC_REPORTED;

    /**
     * The table <code>jobdb.job_position</code>.
     */
    public static final JobPosition JOB_POSITION = JobPosition.JOB_POSITION;

    /**
     * The table <code>jobdb.job_position_ccmail</code>.
     */
    public static final JobPositionCcmail JOB_POSITION_CCMAIL = JobPositionCcmail.JOB_POSITION_CCMAIL;

    /**
     * The table <code>jobdb.job_position_city</code>.
     */
    public static final JobPositionCity JOB_POSITION_CITY = JobPositionCity.JOB_POSITION_CITY;

    /**
     * 职位信息扩展表
     */
    public static final JobPositionExt JOB_POSITION_EXT = JobPositionExt.JOB_POSITION_EXT;

    /**
     * 职位福利特色-关系表
     */
    public static final JobPositionHrCompanyFeature JOB_POSITION_HR_COMPANY_FEATURE = JobPositionHrCompanyFeature.JOB_POSITION_HR_COMPANY_FEATURE;

    /**
     * 职位发布到猎聘时，由于不同地区、职位名称在猎聘需用不同的id，而在仟寻只有一个id，所以此表用来生成向猎聘发布职位时需要的id
     */
    public static final JobPositionLiepinMapping JOB_POSITION_LIEPIN_MAPPING = JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING;

    /**
     * 简历筛选关联职位中间表
     */
    public static final JobPositionProfileFilter JOB_POSITION_PROFILE_FILTER = JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER;

    /**
     * 职位分享描述配置模板
     */
    public static final JobPositionShareTplConf JOB_POSITION_SHARE_TPL_CONF = JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF;

    /**
     * 职位主题活动关系表
     */
    public static final JobPositionTopic JOB_POSITION_TOPIC = JobPositionTopic.JOB_POSITION_TOPIC;

    /**
     * 自定义简历副本记录表
     */
    public static final JobResumeOther JOB_RESUME_OTHER = JobResumeOther.JOB_RESUME_OTHER;
}
