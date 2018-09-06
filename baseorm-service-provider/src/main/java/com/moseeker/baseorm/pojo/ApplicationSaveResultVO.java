package com.moseeker.baseorm.pojo;

/**
 * Created by jack on 28/12/2017.
 */
public class ApplicationSaveResultVO {
    
    private int applicationId;
    private int positionId;
    private boolean create;
    private int applierId;

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

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getApplierId() {
        return applierId;
    }

    public void setApplierId(int applierId) {
        this.applierId = applierId;
    }
}
