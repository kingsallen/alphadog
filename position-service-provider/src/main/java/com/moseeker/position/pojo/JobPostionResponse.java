package com.moseeker.position.pojo;

import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YYF
 *
 * Date: 2017/4/13
 *
 * Project_name :alphadog
 */
public class JobPostionResponse {

    private List<JobPositionFailMess> JobPositionFailMessPojolist;

    private Integer errorCounts;
    private Integer insertCounts;
    private Integer updateCounts;
    private Integer deleteCounts;
    private Integer totalCounts;

    private List<SyncFailMessPojo> syncFailMessPojolist;
    private Integer syncingCounts;
    private Integer notNeedSyncCounts;

    private List<ThirdPartyPositionForm> syncData;

    public List<ThirdPartyPositionForm> getSyncData() {
        return syncData;
    }

    public void setSyncData(List<ThirdPartyPositionForm> syncData) {
        this.syncData = syncData;
    }

    public List<SyncFailMessPojo> getSyncFailMessPojolist() {
        return syncFailMessPojolist;
    }

    public void setSyncFailMessPojolist(List<SyncFailMessPojo> syncFailMessPojolist) {
        this.syncFailMessPojolist = syncFailMessPojolist;
    }

    public Integer getSyncingCounts() {
        return syncingCounts;
    }

    public void setSyncingCounts(Integer syncingCounts) {
        this.syncingCounts = syncingCounts;
    }

    public Integer getNotNeedSyncCounts() {
        return notNeedSyncCounts;
    }

    public void setNotNeedSyncCounts(Integer notNeedSyncCounts) {
        this.notNeedSyncCounts = notNeedSyncCounts;
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public List<JobPositionFailMess> getJobPositionFailMessPojolist() {
        return JobPositionFailMessPojolist;
    }

    public void setJobPositionFailMessPojolist(List<JobPositionFailMess> jobPositionFailMessPojolist) {
        JobPositionFailMessPojolist = jobPositionFailMessPojolist;
    }


    public Integer getErrorCounts() {
        return errorCounts;
    }

    public void setErrorCounts(Integer errorCounts) {
        this.errorCounts = errorCounts;
    }

    public Integer getInsertCounts() {
        return insertCounts;
    }

    public void setInsertCounts(Integer insertCounts) {
        this.insertCounts = insertCounts;
    }

    public Integer getUpdateCounts() {
        return updateCounts;
    }

    public void setUpdateCounts(Integer updateCounts) {
        this.updateCounts = updateCounts;
    }

    public Integer getDeleteCounts() {
        return deleteCounts;
    }

    public void setDeleteCounts(Integer deleteCounts) {
        this.deleteCounts = deleteCounts;
    }
}
