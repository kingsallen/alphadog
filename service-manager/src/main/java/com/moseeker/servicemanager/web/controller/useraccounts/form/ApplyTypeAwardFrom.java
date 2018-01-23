package com.moseeker.servicemanager.web.controller.useraccounts.form;

import java.util.List;

/**
 *
 * 申请积分表单
 *
 * Created by jack on 23/01/2018.
 */
public class ApplyTypeAwardFrom {

    private List<Integer> applicationIds;
    private int eventType;

    public List<Integer> getApplicationIds() {
        return applicationIds;
    }

    public void setApplicationIds(List<Integer> applicationIds) {
        this.applicationIds = applicationIds;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
