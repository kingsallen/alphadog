/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.jobdb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 职位福利特色-关系表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobPositionHrCompanyFeature implements Serializable {

    private static final long serialVersionUID = 1869110843;

    private Integer pid;
    private Integer fid;

    public JobPositionHrCompanyFeature() {}

    public JobPositionHrCompanyFeature(JobPositionHrCompanyFeature value) {
        this.pid = value.pid;
        this.fid = value.fid;
    }

    public JobPositionHrCompanyFeature(
        Integer pid,
        Integer fid
    ) {
        this.pid = pid;
        this.fid = fid;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getFid() {
        return this.fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("JobPositionHrCompanyFeature (");

        sb.append(pid);
        sb.append(", ").append(fid);

        sb.append(")");
        return sb.toString();
    }
}
