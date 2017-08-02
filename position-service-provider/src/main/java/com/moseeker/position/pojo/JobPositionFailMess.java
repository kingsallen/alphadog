package com.moseeker.position.pojo;

/**
 * Created by YYF
 *
 * Date: 2017/4/13
 *
 * Project_name :alphadog
 */
public class JobPositionFailMess {

    // 失败原因
    private String message;
    private Integer status;
    private Integer companyId;
    private Integer sourceId;
    private String jobNumber;
    private String department;
    private Integer jobPostionId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

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
