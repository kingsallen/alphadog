package com.moseeker.baseorm.pojo;

/**
 * Created by jack on 28/12/2017.
 */
public class ApplicationSaveResultVO {
    
    private int applicationId;
    private boolean create;

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }
}
