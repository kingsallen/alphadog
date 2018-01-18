package com.moseeker.application.infrastructure;

import com.moseeker.application.domain.constant.ApplicationViewStatus;
import com.moseeker.application.domain.pojo.Application;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.common.constants.AbleFlag;
import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;

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
     *
     * 将申请记录标记为已查看状态
     * 如果已经浏览过的，浏览次数加一；如果是刚申请未浏览的，那么修改招聘进度，并且浏览次数加一
     * @param applicationList 需要被标记为已经查看的申请记录
     * @return 执行的记录
     */
    public List<Application> viewApplication(List<Application> applicationList) {

        List<Application> executeList = new ArrayList<>();
        if (applicationList != null && applicationList.size() > 0) {
            applicationList.forEach(application -> {

                if (application.isViewOnly()) {
                    using(configuration())
                            .update(JobApplication.JOB_APPLICATION)
                            .set(JobApplication.JOB_APPLICATION.VIEW_COUNT, JobApplication.JOB_APPLICATION.VIEW_COUNT.add(1))
                            .where(JobApplication.JOB_APPLICATION.ID.eq(application.getId()))
                            .execute();
                } else {
                    int execute = using(configuration())
                            .update(JobApplication.JOB_APPLICATION)
                            .set(JobApplication.JOB_APPLICATION.IS_VIEWED, (byte) ApplicationViewStatus.VIEWED.getStatus())
                            .set(JobApplication.JOB_APPLICATION.APP_TPL_ID, application.getNextStatus().getState())
                            .set(JobApplication.JOB_APPLICATION.VIEW_COUNT, JobApplication.JOB_APPLICATION.VIEW_COUNT.add(1))
                            .where(JobApplication.JOB_APPLICATION.ID.eq(application.getId()))
                            .and(JobApplication.JOB_APPLICATION.APP_TPL_ID.eq(application.getStatus().getState()))
                            .execute();
                    if (execute > 0) {
                        executeList.add(application);
                    }
                }
            });
        }
        return executeList;
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

    /**
     *
     * 根据申请编号查找申请编号和职位编号（查找申请和职位的关系）
     *
     * @param applicationIdList 申请编号集合
     * @return 申请和职位的关系
     */
    public List<Record2<Integer, Integer>> fetchPositionIdsByIdList(List<Integer> applicationIdList) {
        if (applicationIdList != null && applicationIdList.size() > 0) {
            List<Record2<Integer, Integer>> result = using(configuration())
                    .select(JobApplication.JOB_APPLICATION.ID, JobApplication.JOB_APPLICATION.POSITION_ID)
                    .from(JobApplication.JOB_APPLICATION)
                    .where(JobApplication.JOB_APPLICATION.ID.in(applicationIdList))
                    .fetch();
            if (result != null) {
                return result;
            }
        }
        return new ArrayList<>();
    }

    /**
     *
     * 查找申请所处的公司编号
     *
     * @param applicationIdList 申请编号
     * @return 公司编号集合
     */
    public List<Record2<Integer, Integer>> fetchCompanyIdListByApplicationIdList(List<Integer> applicationIdList) {
        if (applicationIdList != null && applicationIdList.size() > 0) {
            Result<Record2<Integer, Integer>> result = using(configuration())
                    .select(JobApplication.JOB_APPLICATION.ID, JobApplication.JOB_APPLICATION.COMPANY_ID)
                    .from(JobApplication.JOB_APPLICATION)
                    .where(JobApplication.JOB_APPLICATION.ID.in(applicationIdList))
                    .fetch();
            if (result != null && result.size() > 0) {
                return result;
            }
        }
        return new ArrayList<>();
    }

    /**
     *
     * 查找申请与申请人编号
     *
     * @param applicationIdList 申请编号集合
     * @return 申请和申请人集合
     */
    public List<Record2<Integer,Integer>> fetchApplierIdListByIdList(List<Integer> applicationIdList) {
        if (applicationIdList != null && applicationIdList.size() > 0) {
            Result<Record2<Integer, Integer>> result = using(configuration())
                    .select(JobApplication.JOB_APPLICATION.ID, JobApplication.JOB_APPLICATION.APPLIER_ID)
                    .from(JobApplication.JOB_APPLICATION)
                    .where(JobApplication.JOB_APPLICATION.ID.in(applicationIdList))
                    .fetch();
            if (result != null) {
                return result;
            }
        }
        return new ArrayList<>();
    }
}
