package com.moseeker.application.service.agreegateid;

import java.util.List;

/**
 * HR浏览职位业务聚合根
 * Created by jack on 16/01/2018.
 */
public class ViewApplicationAggregateId {

    private int hrId;
    private List<Integer> applicationIds;

    public ViewApplicationAggregateId(int hrId, List<Integer> applicationIds) {
        this.hrId = hrId;
        this.applicationIds = applicationIds;
    }

    public int getHrId() {
        return hrId;
    }

    public void setHrId(int hrId) {
        this.hrId = hrId;
    }

    public List<Integer> getApplicationIds() {
        return applicationIds;
    }

    public void setApplicationIds(List<Integer> applicationIds) {
        this.applicationIds = applicationIds;
    }
}
