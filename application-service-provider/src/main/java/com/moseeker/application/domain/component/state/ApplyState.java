package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;

/**
 * 投递申请，申请的初始状态
 * Created by jack on 16/01/2018.
 */
public class ApplyState extends ApplicationState {

    public ApplyState(ApplicationEntity applicationEntity) {
        super(applicationEntity, ApplicationStateRoute.Apply);
    }

    @Override
    public void refuse() {

    }

    @Override
    public ApplicationState pass() {

        if (applicationEntity.getState().getStatus().equals(ApplicationStateRoute.Apply)) {
            ApplicationState nextSate = this.getNext();
            applicationEntity.setState(nextSate);
            return nextSate;
        }
        return null;
    }

    @Override
    public ApplicationState backTo() {
        return null;
    }

    @Override
    public void recover() {

    }

    @Override
    public ApplicationStateRoute getStatus() {
        return applicationStateRoute;
    }
}
