package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrRuleStatistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRuleStatisticsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrRuleStatisticsDO;

/**
* @author xxx
* HrRuleStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrRuleStatisticsDao extends StructDaoImpl<HrRuleStatisticsDO, HrRuleStatisticsRecord, HrRuleStatistics> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrRuleStatistics.HR_RULE_STATISTICS;
   }
}
