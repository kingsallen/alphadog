package com.moseeker.position.dao.impl;


import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.hrdb.tables.HrTeam;
import com.moseeker.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.position.dao.HrTeamDao;

import org.springframework.stereotype.Repository;

@Repository
public class HrTeamDaoImpl extends BaseDaoImpl<HrTeamRecord, HrTeam> implements HrTeamDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrTeam.HR_TEAM;
    }

}
