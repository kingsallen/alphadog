package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class HrPointsConfDao extends BaseDaoImpl <HrPointsConfRecord, HrPointsConf> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrPointsConf.HR_POINTS_CONF;
    }
}
