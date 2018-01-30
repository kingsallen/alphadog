package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;

/**
 * Created by jack on 16/01/2018.
 */
public class CVPassedState extends ApplicationState {

    public CVPassedState(ApplicationEntity applicationEntity) {
        super(applicationEntity, ApplicationStatus.CVPassed);
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
