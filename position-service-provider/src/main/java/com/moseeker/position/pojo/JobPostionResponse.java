package com.moseeker.position.pojo;

import java.util.List;

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
