package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrImporterMonitor;
import com.moseeker.baseorm.db.hrdb.tables.records.HrImporterMonitorRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrImporterMonitorDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrImporterMonitorDao extends JooqCrudImpl<HrImporterMonitorDO, HrImporterMonitorRecord> {

    public HrImporterMonitorDao() {
        super(HrImporterMonitor.HR_IMPORTER_MONITOR, HrImporterMonitorDO.class);
    }

    public HrImporterMonitorDao(TableImpl<HrImporterMonitorRecord> table, Class<HrImporterMonitorDO> hrImporterMonitorDOClass) {
        super(table, hrImporterMonitorDOClass);
    }
}
