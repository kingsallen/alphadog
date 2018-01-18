package com.moseeker.application.infrastructure;

import com.moseeker.application.domain.constant.ApplicationViewStatus;
import com.moseeker.application.domain.pojo.Application;
import com.moseeker.application.domain.pojo.ApplicationStatePojo;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.common.constants.AbleFlag;
import org.jooq.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.using;

/**
 * 由于历史遗留问题导致 JobApplicationDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 * Created by jack on 16/01/2018.
 */
public class JobApplicationJOOQDao extends com.moseeker.baseorm.db.jobdb.tables.daos.JobApplicationDao {

    public JobApplicationJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 查找属于制定公司的申请数量
     * @param applicationIds 申请编号集合
     * @param companyId 公司编号
     * @return 属于公司的申请数量
     */
    public long countApplicationByIdsAndCompanyId(List<Integer> applicationIds, Integer companyId) {

        return using(configuration())
                .selectCount()
                .from(JobApplication.JOB_APPLICATION)
                .where(JobApplication.JOB_APPLICATION.ID.in(applicationIds))
                .and(JobApplication.JOB_APPLICATION.COMPANY_ID.eq(companyId))
                .fetchOne()
                .value1();
    }

    /**
     * 查找申请编号在指定编号集合中，并且职位属于制定职位集合中的申请数量
     * @param applicationIds 申请编号集合
     * @param positionIdList 职位编号集合
     * @return 属于搜索条件的申请数量
     */
    public long countApplicationByIdsAndPositionIds(List<Integer> applicationIds, List<Integer> positionIdList) {
        return using(configuration())
                .selectCount()
                .from(JobApplication.JOB_APPLICATION)
                .where(JobApplication.JOB_APPLICATION.ID.in(applicationIds))
                .and(JobApplication.JOB_APPLICATION.POSITION_ID.in(positionIdList))
                .fetchOne()
                .value1();
    }

    /**
     * 将申请记录标记为已查看状态
     * @param unViewedApplicationList 需要被标记为已经查看的申请记录
     */
    public void viewApplication(List<ApplicationStatePojo> unViewedApplicationList) {
        if (unViewedApplicationList != null && unViewedApplicationList.size() > 0) {
            unViewedApplicationList.forEach(application -> {
                using(configuration())
                        .update(JobApplication.JOB_APPLICATION)
                        .set(JobApplication.JOB_APPLICATION.IS_VIEWED, (byte) ApplicationViewStatus.VIEWED.getStatus())
                        .set(JobApplication.JOB_APPLICATION.APP_TPL_ID, application.getState())
                        .where(JobApplication.JOB_APPLICATION.ID.eq(application.getId()))
                        .and(JobApplication.JOB_APPLICATION.APP_TPL_ID.eq(application.getPreState()))
                        .execute();
            });
        }
    }

    /**
     * 根据ID集合查找申请信息
     * 只返回 ID，app_tpl_id, company_id, is_viewed, position_id, view_count
     * @param applicationIds 申请编号
     * @return 申请集合 如果没有查到任何信息则返回空集合
     */
    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication> fetchActiveApplicationByIdList(List<Integer> applicationIds) {
        if (applicationIds != null && applicationIds.size() > 0) {
            return using(configuration())
                    .select(JobApplication.JOB_APPLICATION.ID, JobApplication.JOB_APPLICATION.APP_TPL_ID,
                            JobApplication.JOB_APPLICATION.COMPANY_ID, JobApplication.JOB_APPLICATION.IS_VIEWED,
                            JobApplication.JOB_APPLICATION.POSITION_ID, JobApplication.JOB_APPLICATION.VIEW_COUNT)
                    .from(JobApplication.JOB_APPLICATION)
                    .where(JobApplication.JOB_APPLICATION.ID.in(applicationIds))
                    .and(JobApplication.JOB_APPLICATION.DISABLE.eq(AbleFlag.OLDENABLE.getValue()))
                    .fetchInto(com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication.class);
        } else {
            return new ArrayList<>();
        }
    }
}
