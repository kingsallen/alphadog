package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbConfigDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrHbConfigDao extends JooqCrudImpl<HrHbConfigDO, HrHbConfigRecord> {

    public HrHbConfigDao(TableImpl<HrHbConfigRecord> table, Class<HrHbConfigDO> hrHbConfigDOClass) {
        super(table, hrHbConfigDOClass);
    }
}
