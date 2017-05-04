package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbScratchCardRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbScratchCardDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrHbScratchCardDao extends JooqCrudImpl<HrHbScratchCardDO, HrHbScratchCardRecord> {

    public HrHbScratchCardDao(TableImpl<HrHbScratchCardRecord> table, Class<HrHbScratchCardDO> hrHbScratchCardDOClass) {
        super(table, hrHbScratchCardDOClass);
    }
}
