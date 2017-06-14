package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrPointsConfDao extends JooqCrudImpl<HrPointsConfDO, HrPointsConfRecord> {

    public HrPointsConfDao() {
        super(HrPointsConf.HR_POINTS_CONF, HrPointsConfDO.class);
    }

    public HrPointsConfDao(TableImpl<HrPointsConfRecord> table, Class<HrPointsConfDO> hrPointsConfDOClass) {
        super(table, hrPointsConfDOClass);
    }
}
