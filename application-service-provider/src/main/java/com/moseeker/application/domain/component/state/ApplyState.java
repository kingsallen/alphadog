package com.moseeker.application.domain.component.state;

import com.moseeker.application.context.event.ViewApplicationListEvent;
import com.moseeker.application.domain.ApplicationEntity;
import com.moseeker.application.domain.pojo.ApplicationStatePojo;
import com.moseeker.application.infrastructure.DaoManagement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 投递申请，申请的初始状态
 * Created by jack on 16/01/2018.
 */
public class ApplyState extends ApplicationState {

    public ApplyState(ApplicationEntity applicationEntity, DaoManagement daoManagement) {
        super(applicationEntity, daoManagement);
        this.applicationStatus = ApplicationStatus.Apply;
    }

    @Override
    public void refuse() {

    }

    @Override
    public ApplicationState pass() {

        ApplicationState nextState = getNext();
        if (nextState != null) {
            List<ApplicationStatePojo> unViewedApplicationList = applicationEntity.getApplicationList()
                    .stream()
                    .filter(application -> !application.isViewed())
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
                daoManagement.getJobApplicationDao().viewApplication(unViewedApplicationList);

                ViewApplicationListEvent viewApplicationListEvent
                        = new ViewApplicationListEvent(unViewedApplicationList
                        .stream()
                        .map(applicationStatePojo -> applicationStatePojo.getId())
                        .collect(Collectors.toList()));
                applicationEntity.getApplicationContext().publishEvent(viewApplicationListEvent); //发布查看申请事件
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
