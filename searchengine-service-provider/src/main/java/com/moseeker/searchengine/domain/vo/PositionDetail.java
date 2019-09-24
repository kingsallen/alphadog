package com.moseeker.searchengine.domain.vo;

import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrTeam;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;

/**
 * TODO
 *
 * @Author jack
 * @Date 2019/9/5 5:14 PM
 * @Version 1.0
 */
public class PositionDetail {

    private JobPosition position;

    private HrCompany company;

    private HrTeam team;

    public JobPosition getPosition() {
        return position;
    }

    public void setPosition(JobPosition position) {
        this.position = position;
    }

    public HrCompany getCompany() {
        return company;
    }

    public void setCompany(HrCompany company) {
        this.company = company;
    }

    public HrTeam getTeam() {
        return team;
    }

    public void setTeam(HrTeam team) {
        this.team = team;
    }
}
