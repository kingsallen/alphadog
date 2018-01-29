package com.moseeker.application.domain;

import com.moseeker.application.domain.component.state.ApplicationState;
import com.moseeker.application.domain.component.state.ApplyState;
import com.moseeker.application.domain.pojo.Application;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.ApplicationRepository;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 申请实体
 * Created by jack on 17/01/2018.
 */
public class ApplicationBatchEntity {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private volatile List<Application> applicationList;     //申请编号集合
    private volatile List<Application> executeList;         //需要被执行的集合
    private ApplicationRepository applicationRepository;    //DAO操作

    public ApplicationBatchEntity(ApplicationRepository applicationRepository, List<Application> applicationList) throws CommonException {
        if (applicationList == null || applicationList.size() == 0 || applicationRepository == null) {
            throw ApplicationException.APPLICATION_ENTITY_BUILD_FAILED;
        }
        this.applicationRepository = applicationRepository;
        this.applicationList = applicationList;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public List<Application> getExecuteList() {
        return executeList;
    }

    public void setExecuteList(List<Application> executeList) {
        this.executeList = executeList;
    }

    /**
     * 简历被查看
     * @param hrEntity
     */
    public List<HrOperationRecord> viewed(HREntity hrEntity) {

        applicationList = applicationList
            .stream()
            .filter(application -> validateAuthority(hrEntity, application))    //过滤没有权限的申请记录
            .collect(Collectors.toList());

        ApplicationState state = new ApplyState(this, applicationRepository);
        state.pass();
        //生成HR操作申请的操作记录
        List<HrOperationRecord> operationRecordList = null;
        operationRecordList = executeList
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
        return operationRecordList;
    }

    /**
     * 判断HR 是否有权限操作申请记录
     * @param hrEntity HR
     * @param application 申请记录
     * @return true 有权限；false 没有权限
     */
    private boolean validateAuthority(HREntity hrEntity, Application application) {
        Optional<Integer> optional = application.getHrId()
                .stream()
                .filter(id -> id.intValue() == hrEntity.getId())
                .findAny();
        return optional.isPresent();
    }
}
