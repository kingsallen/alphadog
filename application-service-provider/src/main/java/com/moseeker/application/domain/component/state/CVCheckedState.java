package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;
import com.moseeker.application.infrastructure.DaoManagement;

/**
 * Created by jack on 18/01/2018.
 */
public class CVCheckedState extends ApplicationState {

    public CVCheckedState(ApplicationEntity applicationEntity, DaoManagement daoManagement) {
        super(applicationEntity, daoManagement);
        this.applicationStatus = ApplicationStatus.CVChecked;
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
        return applicationStatus;
    }
}
