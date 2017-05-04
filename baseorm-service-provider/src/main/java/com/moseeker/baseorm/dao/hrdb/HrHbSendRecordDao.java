package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbSendRecordRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbSendRecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrHbSendRecordDao extends JooqCrudImpl<HrHbSendRecordDO, HrHbSendRecordRecord> {

    public HrHbSendRecordDao(TableImpl<HrHbSendRecordRecord> table, Class<HrHbSendRecordDO> hrHbSendRecordDOClass) {
        super(table, hrHbSendRecordDOClass);
    }
}
