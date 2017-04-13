package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrImporterMonitor;
import com.moseeker.baseorm.db.hrdb.tables.records.HrImporterMonitorRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;

/**
* @author xxx
* HrImporterMonitorDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrImporterMonitorDao extends StructDaoImpl<HrImporterMonitorDO, HrImporterMonitorRecord, HrImporterMonitor> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrImporterMonitor.HR_IMPORTER_MONITOR;
   }
}
