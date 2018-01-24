package com.moseeker.application.domain;

import com.moseeker.application.domain.component.state.ApplicationState;
import com.moseeker.application.domain.component.state.ApplyState;
import com.moseeker.application.domain.pojo.Application;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.DaoManagement;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.common.exception.CommonException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 申请实体
 * Created by jack on 17/01/2018.
 */
public class ApplicationBatchEntity {

    private volatile List<Application> applicationList;     //申请编号集合
    private DaoManagement daoManagement;                    //DAO操作

    public ApplicationBatchEntity(DaoManagement daoManagement, List<Application> applicationList) throws CommonException {
        if (applicationList == null || applicationList.size() == 0 || daoManagement == null) {
            throw ApplicationException.APPLICATION_ENTITY_BUILD_FAILED;
        }
        this.daoManagement = daoManagement;
        this.applicationList = applicationList;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    /**
     * 简历被查看
     * @param hrEntity
     */
    public List<HrOperationRecord> viewed(HREntity hrEntity) {

        ApplicationState state = new ApplyState(this, daoManagement);
        List<Application> unViewedApplicationList = applicationList
                .stream()
                .filter(application -> !application.isViewed())                         //过滤已经查看过的
                .filter(application -> validateAuthority(hrEntity, application))        //过滤没有权限的申请
                .collect(Collectors.toList());
        applicationList = unViewedApplicationList;

        //生成HR操作申请的操作记录
        List<HrOperationRecord> operationRecordList = applicationList
                .stream()
                .map(application -> {
                    HrOperationRecord hrOperationRecord = new HrOperationRecord();
                    hrOperationRecord.setAdminId((long) hrEntity.getId());
                    hrOperationRecord.setCompanyId((long) hrEntity.getCompanyId());
                    hrOperationRecord.setAppId((long) application.getId());
                    hrOperationRecord.setOperateTplId(state.getNext().getStatus().getState());
                    return hrOperationRecord;
                })
                .collect(Collectors.toList());
        state.pass();
        return operationRecordList;
    }

    /**
     * 判断HR 是否有权限操作申请记录
     * @param hrEntity HR
     * @param application 申请记录
     * @return true 有权限；false 没有权限
     */
    private boolean validateAuthority(HREntity hrEntity, Application application) {
        Optional<Integer> optional = application.getHrId().stream().filter(id -> id.intValue() == hrEntity.getId()).findAny();
        return optional.isPresent();
    }
}
