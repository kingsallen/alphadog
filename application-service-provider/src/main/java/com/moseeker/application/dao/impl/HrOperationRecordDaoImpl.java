package com.moseeker.application.dao.impl;

import com.moseeker.application.dao.HrOperationRecordDao;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.hrdb.tables.HrOperationRecord;
import com.moseeker.db.hrdb.tables.records.HrOperationRecordRecord;
import org.springframework.stereotype.Repository;

/**
 * HR操作记录
 *
 * Created by zzh on 16/8/15.
 */
@Repository
public class HrOperationRecordDaoImpl extends BaseDaoImpl<HrOperationRecordRecord, HrOperationRecord>
        implements HrOperationRecordDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrOperationRecord.HR_OPERATION_RECORD;
    }

}
