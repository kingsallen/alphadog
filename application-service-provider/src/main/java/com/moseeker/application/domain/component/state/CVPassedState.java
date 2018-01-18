package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;
import com.moseeker.application.infrastructure.DaoManagement;

/**
 * Created by jack on 16/01/2018.
 */
public class CVPassedState extends ApplicationState {

    ApplicationStatus applicationStatus = ApplicationStatus.CVPassed;

    public CVPassedState(ApplicationEntity applicationEntity, DaoManagement daoManagement) {
        super(applicationEntity, daoManagement);
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
