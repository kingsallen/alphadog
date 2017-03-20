package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrTeam;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class HrTeamDao extends BaseDaoImpl<HrTeamRecord, HrTeam> {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrTeam.HR_TEAM;
    }

}
