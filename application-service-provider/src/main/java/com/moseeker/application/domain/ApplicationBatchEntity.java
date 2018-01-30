package com.moseeker.application.domain;

import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.ApplicationRepository;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.common.exception.CommonException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 批量申请实体
 * Created by jack on 17/01/2018.
 */
public class ApplicationBatchEntity {

    private volatile List<ApplicationEntity> applicationList;     //申请编号集合
    private ApplicationRepository applicationRepository;    //DAO操作

    public ApplicationBatchEntity(ApplicationRepository applicationRepository, List<ApplicationEntity> applicationList) throws CommonException {
        if (applicationList == null || applicationList.size() == 0 || applicationRepository == null) {
            throw ApplicationException.APPLICATION_ENTITY_BUILD_FAILED;
        }
        this.applicationRepository = applicationRepository;
        this.applicationList = applicationList;
    }

    public List<ApplicationEntity> getApplicationList() {
        return applicationList;
    }

    /**
     * HR查看批量申请
     * @param hrEntity HR
     * @return HR查看申请的操作记录
     */
    public List<HrOperationRecord> viewed(HREntity hrEntity) {

        for (ApplicationEntity applicationEntity : applicationList) {
            applicationEntity.view(hrEntity);
        }

        List<ApplicationEntity> result = applicationRepository.updateApplications(applicationList);

        //生成HR操作申请的操作记录
        List<HrOperationRecord> operationRecordList = result
                .stream()
                .map(application -> {
                    HrOperationRecord hrOperationRecord = new HrOperationRecord();
                    hrOperationRecord.setAdminId((long) hrEntity.getId());
                    hrOperationRecord.setCompanyId((long) hrEntity.getCompanyId());
                    hrOperationRecord.setAppId((long) application.getId());
                    hrOperationRecord.setOperateTplId(application.getState().getStatus().getState());
                    return hrOperationRecord;
                })
                .collect(Collectors.toList());

        return operationRecordList;
    }
}
