package com.moseeker.position.pojo;

import com.moseeker.db.jobdb.tables.records.JobPositionRecord;

/**
 * Created by YYF
 *
 * Date: 2017/3/23
 *
 * Project_name :alphadog
 *
 * 用于返回批量更新职位时候提示信息
 */
public class UpdateJobPositionFailMessPojo {

    // 失败原因
    private String message;

    // 失败的数据
    private JobPositionRecord jobPositionRecord;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JobPositionRecord getJobPositionRecord() {
        return jobPositionRecord;
    }

    public void setJobPositionRecord(JobPositionRecord jobPositionRecord) {
        this.jobPositionRecord = jobPositionRecord;
    }
}
