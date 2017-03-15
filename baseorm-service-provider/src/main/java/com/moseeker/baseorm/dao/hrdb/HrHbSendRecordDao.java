package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrHbSendRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbSendRecordRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import org.springframework.stereotype.Service;

@Service
public class HrHbSendRecordDao extends BaseDaoImpl <HrHbSendRecordRecord, HrHbSendRecord> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrHbSendRecord.HR_HB_SEND_RECORD;
    }
}
