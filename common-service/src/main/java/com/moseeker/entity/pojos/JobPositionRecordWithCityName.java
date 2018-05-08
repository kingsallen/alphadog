package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;

/**
 *
 * 增加职位的英文名称
 *
 * Created by jack on 2018/4/19.
 */
public class JobPositionRecordWithCityName extends JobPositionRecord {

    private String cityEname;

    public String getCityEname() {
        return cityEname;
    }

    public void setCityEname(String cityEname) {
        this.cityEname = cityEname;
    }

    public static JobPositionRecordWithCityName clone(JobPositionRecord jobPositionRecord) {
        return jobPositionRecord.into(JobPositionRecordWithCityName.class);
    }
}
