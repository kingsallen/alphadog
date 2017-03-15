package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrHbScratchCard;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbScratchCardRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class HrHbScratchCardDao extends BaseDaoImpl <HrHbScratchCardRecord, HrHbScratchCard> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrHbScratchCard.HR_HB_SCRATCH_CARD;
    }
}
