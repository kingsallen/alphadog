package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbPositionBinding;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbPositionBindingRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbPositionBindingDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrHbPositionBindingDao extends JooqCrudImpl<HrHbPositionBindingDO, HrHbPositionBindingRecord> {

    public HrHbPositionBindingDao() {
        super(HrHbPositionBinding.HR_HB_POSITION_BINDING, HrHbPositionBindingDO.class);
    }

    public HrHbPositionBindingDao(TableImpl<HrHbPositionBindingRecord> table, Class<HrHbPositionBindingDO> hrHbPositionBindingDOClass) {
        super(table, hrHbPositionBindingDOClass);
    }
}
