package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationBatchEntity;
import com.moseeker.application.infrastructure.ApplicationRepository;

/**
 * Created by jack on 16/01/2018.
 */
public class HiredState extends ApplicationState {

    private ApplicationStatus applicationStatus = ApplicationStatus.Hired;

    public HiredState(ApplicationBatchEntity applicationBatchEntity, ApplicationRepository applicationRepository) {
        super(applicationBatchEntity, applicationRepository);
    }


    @Override
    public void refuse() {

    }

    @Override
    public ApplicationState pass() {
        return null;

    }

    @Override
    public ApplicationState backTo() {
        return null;
    }

    @Override
    void recover() {

    }

    @Override
    public ApplicationStatus getStatus() {
        return null;
    }
}
