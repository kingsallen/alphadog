package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationBatchEntity;
import com.moseeker.application.domain.pojo.Application;
import com.moseeker.application.infrastructure.ApplicationRepository;

import java.util.List;

/**
 * 投递申请，申请的初始状态
 * Created by jack on 16/01/2018.
 */
public class ApplyState extends ApplicationState {

    public ApplyState(ApplicationBatchEntity applicationBatchEntity, ApplicationRepository applicationRepository) {
        super(applicationBatchEntity, applicationRepository, ApplicationStatus.Apply);
    }

    @Override
    public void refuse() {

    }

    @Override
    public ApplicationState pass() {

        ApplicationState nextState = getNext();
        if (nextState != null) {
            applicationBatchEntity.getApplicationList()
                    .forEach(application -> {
                        if (application.getStatus().equals(ApplicationStatus.Apply)) {
                            application.setNextStatus(nextState.getStatus());
                            application.setViewOnly(false);
                        } else {
                            application.setViewOnly(true);
                        }
                    });
            if (applicationBatchEntity.getApplicationList() != null && applicationBatchEntity.getApplicationList().size() > 0) {
                List<Application> realExecuteList = applicationRepository.viewApplication(applicationBatchEntity.getApplicationList());
                applicationBatchEntity.setExecuteList(realExecuteList);
                }
        }
        return nextState;
    }

    @Override
    public ApplicationState backTo() {
        return null;
    }

    @Override
    public void recover() {

    }

    @Override
    public ApplicationStatus getStatus() {
        return applicationStatus;
    }
}
