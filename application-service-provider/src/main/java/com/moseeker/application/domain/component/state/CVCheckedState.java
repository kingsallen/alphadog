package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;

/**
 * Created by jack on 18/01/2018.
 */
public class CVCheckedState extends ApplicationState {

    public CVCheckedState(ApplicationEntity applicationEntity) {
        super(applicationEntity, ApplicationStateRoute.CVChecked);
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
}
