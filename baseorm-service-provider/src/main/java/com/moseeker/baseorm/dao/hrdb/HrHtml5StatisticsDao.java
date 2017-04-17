package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrHtml5Statistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHtml5StatisticsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHtml5StatisticsDO;

/**
* @author xxx
* HrHtml5StatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrHtml5StatisticsDao extends StructDaoImpl<HrHtml5StatisticsDO, HrHtml5StatisticsRecord, HrHtml5Statistics> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrHtml5Statistics.HR_HTML5_STATISTICS;
   }
}
