package com.moseeker.position.pojo;


import java.io.Serializable;

/**
 * Created by YYF
 *
 * Date: 2017/3/23
 *
 * Project_name :alphadog
 *
 * 用于返回批量更新职位时候提示信息
 */
public class JobPositionFailMessPojo implements Serializable {

    // 失败原因
    private String message;

    private Integer companyId;
    private Integer sourceId;
    private String jobNumber;

    private Integer jobPostionId;

    public Integer getJobPostionId() {
        return jobPostionId;
    }

    public void setJobPostionId(Integer jobPostionId) {
        this.jobPostionId = jobPostionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
}
