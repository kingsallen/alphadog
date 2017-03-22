package com.moseeker.position.dao.impl;

import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;

import org.springframework.stereotype.Repository;

@Repository
public class HrTeamDaoImpl extends BaseDaoImpl<HrTeamRecord, HrTeam> {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrTeam.HR_TEAM;
    }

}
