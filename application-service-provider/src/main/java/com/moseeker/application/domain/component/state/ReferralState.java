package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;

/**
 * @Author: jack
 * @Date: 2018/9/29
 */
public class ReferralState extends ApplicationState {

    public ReferralState(ApplicationEntity applicationEntity) {
        super(applicationEntity, ApplicationStateRoute.EmployeeReferral);
    }

    @Override
    public void refuse() {

    }

    @Override
    public ApplicationState pass() {

        if (applicationEntity.getState().getStatus().equals(ApplicationStateRoute.EmployeeReferral) && !refuse) {
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
}
