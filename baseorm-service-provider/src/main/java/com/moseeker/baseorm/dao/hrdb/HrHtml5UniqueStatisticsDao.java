package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrHtml5UniqueStatistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHtml5UniqueStatisticsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHtml5UniqueStatisticsDO;

/**
* @author xxx
* HrHtml5UniqueStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrHtml5UniqueStatisticsDao extends StructDaoImpl<HrHtml5UniqueStatisticsDO, HrHtml5UniqueStatisticsRecord, HrHtml5UniqueStatistics> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrHtml5UniqueStatistics.HR_HTML5_UNIQUE_STATISTICS;
   }
}
