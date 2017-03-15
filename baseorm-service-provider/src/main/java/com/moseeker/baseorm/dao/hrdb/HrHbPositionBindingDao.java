package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrHbPositionBinding;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbPositionBindingRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class HrHbPositionBindingDao extends BaseDaoImpl <HrHbPositionBindingRecord, HrHbPositionBinding> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrHbPositionBinding.HR_HB_POSITION_BINDING;
    }
}
