package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationBatchEntity;
import com.moseeker.application.domain.pojo.ApplicationStatePojo;
import com.moseeker.application.infrastructure.ApplicationRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 投递申请，申请的初始状态
 * Created by jack on 16/01/2018.
 */
public class ApplyState extends ApplicationState {

    public ApplyState(ApplicationBatchEntity applicationBatchEntity, ApplicationRepository applicationRepository) {
        super(applicationBatchEntity, applicationRepository);
        this.applicationStatus = ApplicationStatus.Apply;
    }

    @Override
    public void refuse() {

    }

    @Override
    public ApplicationState pass() {

        ApplicationState nextState = getNext();
        if (nextState != null) {
            List<ApplicationStatePojo> unViewedApplicationList = applicationBatchEntity.getApplicationList()
                    .stream()
                    .map(application -> {
                        ApplicationStatePojo applicationState = new ApplicationStatePojo();
                        applicationState.setId(application.getId());
                        applicationState.setPreState(application.getStatus());
                        applicationState.setState(nextState.getStatus().getState());
                        applicationState.setView(0);
                        application.setViewed(true);
                        application.setStatus(nextState.getStatus().getState());
                        return applicationState;
                    })
                    .collect(Collectors.toList());
            if (unViewedApplicationList != null && unViewedApplicationList.size() > 0) {
                applicationRepository.viewApplication(unViewedApplicationList);
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
