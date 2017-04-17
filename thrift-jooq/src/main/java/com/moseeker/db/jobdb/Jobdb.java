/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.jobdb;


import com.moseeker.db.jobdb.tables.JobApplication;
import com.moseeker.db.jobdb.tables.JobApplicationConf;
import com.moseeker.db.jobdb.tables.JobApplicationStatusBeisen;
import com.moseeker.db.jobdb.tables.JobCustom;
import com.moseeker.db.jobdb.tables.JobOccupation;
import com.moseeker.db.jobdb.tables.JobOccupationRel;
import com.moseeker.db.jobdb.tables.JobPosition;
import com.moseeker.db.jobdb.tables.JobPositionCity;
import com.moseeker.db.jobdb.tables.JobPositionExt;
import com.moseeker.db.jobdb.tables.JobPositionShareTplConf;
import com.moseeker.db.jobdb.tables.JobPositionTopic;
import com.moseeker.db.jobdb.tables.JobResumeOther;

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

    private static final long serialVersionUID = -584292197;

    /**
     * The reference instance of <code>jobdb</code>
     */
    public static final Jobdb JOBDB = new Jobdb();

    /**
     * The table <code>jobdb.job_application</code>.
     */
    public final JobApplication JOB_APPLICATION = com.moseeker.db.jobdb.tables.JobApplication.JOB_APPLICATION;

    /**
     * 部门申请配置表
     */
    public final JobApplicationConf JOB_APPLICATION_CONF = com.moseeker.db.jobdb.tables.JobApplicationConf.JOB_APPLICATION_CONF;

    /**
     * 申请状态记录（ats北森）
     */
    public final JobApplicationStatusBeisen JOB_APPLICATION_STATUS_BEISEN = com.moseeker.db.jobdb.tables.JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN;

    /**
     * 职位自定义字段配置表
     */
    public final JobCustom JOB_CUSTOM = com.moseeker.db.jobdb.tables.JobCustom.JOB_CUSTOM;

    /**
     * 公司自定义职能表
     */
    public final JobOccupation JOB_OCCUPATION = com.moseeker.db.jobdb.tables.JobOccupation.JOB_OCCUPATION;

    /**
     * 职位与职能关系表
     */
    public final JobOccupationRel JOB_OCCUPATION_REL = com.moseeker.db.jobdb.tables.JobOccupationRel.JOB_OCCUPATION_REL;

    /**
     * The table <code>jobdb.job_position</code>.
     */
    public final JobPosition JOB_POSITION = com.moseeker.db.jobdb.tables.JobPosition.JOB_POSITION;

    /**
     * The table <code>jobdb.job_position_city</code>.
     */
    public final JobPositionCity JOB_POSITION_CITY = com.moseeker.db.jobdb.tables.JobPositionCity.JOB_POSITION_CITY;

    /**
     * 职位信息扩展表
     */
    public final JobPositionExt JOB_POSITION_EXT = com.moseeker.db.jobdb.tables.JobPositionExt.JOB_POSITION_EXT;

    /**
     * 职位分享描述配置模板
     */
    public final JobPositionShareTplConf JOB_POSITION_SHARE_TPL_CONF = com.moseeker.db.jobdb.tables.JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF;

    /**
     * 职位主题活动关系表
     */
    public final JobPositionTopic JOB_POSITION_TOPIC = com.moseeker.db.jobdb.tables.JobPositionTopic.JOB_POSITION_TOPIC;

    /**
     * 自定义简历副本记录表
     */
    public final JobResumeOther JOB_RESUME_OTHER = com.moseeker.db.jobdb.tables.JobResumeOther.JOB_RESUME_OTHER;

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
            JobApplicationConf.JOB_APPLICATION_CONF,
            JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN,
            JobCustom.JOB_CUSTOM,
            JobOccupation.JOB_OCCUPATION,
            JobOccupationRel.JOB_OCCUPATION_REL,
            JobPosition.JOB_POSITION,
            JobPositionCity.JOB_POSITION_CITY,
            JobPositionExt.JOB_POSITION_EXT,
            JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF,
            JobPositionTopic.JOB_POSITION_TOPIC,
            JobResumeOther.JOB_RESUME_OTHER);
    }
}
