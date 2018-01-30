package com.moseeker.application.domain.component.state;

import com.moseeker.application.domain.ApplicationEntity;

import java.util.List;

/**
 * 投递申请，申请的初始状态
 * Created by jack on 16/01/2018.
 */
public class ApplyState extends ApplicationState {

    public ApplyState(ApplicationEntity applicationEntity) {
        super(applicationEntity, ApplicationStatus.Apply);
    }

    @Override
    public void refuse() {

    }

    @Override
    public ApplicationState pass() {

        this.applicationEntity.addViewNumber();
        if (applicationEntity.getState().getStatus().equals(ApplicationStatus.Apply)) {
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
    public ApplicationStatus getStatus() {
        return applicationStatus;
    }
}
