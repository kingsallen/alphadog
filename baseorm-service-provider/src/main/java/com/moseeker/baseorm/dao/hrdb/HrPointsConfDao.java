package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class HrPointsConfDao extends StructDaoImpl<HrPointsConfDO, HrPointsConfRecord, HrPointsConf> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrPointsConf.HR_POINTS_CONF;
    }
}
