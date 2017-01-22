package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class HrHbItemsDao extends BaseDaoImpl <HrHbItemsRecord, HrHbItems> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrHbItems.HR_HB_ITEMS;
    }
}
