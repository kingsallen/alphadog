package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrHbConfig;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class HrHbConfigDao extends BaseDaoImpl <HrHbConfigRecord, HrHbConfig> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrHbConfig.HR_HB_CONFIG;
    }
}
