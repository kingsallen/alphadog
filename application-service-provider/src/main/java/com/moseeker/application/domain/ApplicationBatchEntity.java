package com.moseeker.application.domain;

import com.moseeker.application.domain.component.state.*;
import com.moseeker.application.domain.pojo.Application;
import com.moseeker.application.exception.ApplicationException;
import com.moseeker.application.infrastructure.DaoManagement;
import com.moseeker.common.exception.CommonException;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * 申请实体
 * Created by jack on 17/01/2018.
 */
public class ApplicationBatchEntity {

    private List<Application> applicationList;          //申请编号集合
    private DaoManagement daoManagement;                //DAO操作
    private volatile ApplicationState applicationState;
    private final ApplicationContext applicationContext;

    public ApplicationBatchEntity(DaoManagement daoManagement, List<Application> applicationList, ApplicationContext applicationContext) throws CommonException {
        if (applicationList == null || applicationList.size() == 0 || daoManagement == null) {
            throw ApplicationException.APPLICATION_ENTITY_BUILD_FAILED;
        }
        this.daoManagement = daoManagement;
        this.applicationList = applicationList;
        this.applicationContext = applicationContext;

        switch (applicationList.get(0).getStatus()) {
            case 1: applicationState = new ApplyState(this, daoManagement);break;
            case 3: applicationState = new HiredState(this, daoManagement); break;
            case 6: applicationState = new CVCheckedState(this, daoManagement); break;
            case 10: applicationState = new CVPassedState(this, daoManagement); break;
            case 12: applicationState = new OfferedStatus(this, daoManagement); break;
            default:
        }
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    /**
     * 简历被查看
     */
    public void viewed() {
        this.applicationState = applicationState.pass();
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
