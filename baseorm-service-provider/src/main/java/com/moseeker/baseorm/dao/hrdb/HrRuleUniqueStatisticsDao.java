package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrRuleUniqueStatistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRuleUniqueStatisticsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrRuleUniqueStatisticsDO;

/**
* @author xxx
* HrRuleUniqueStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrRuleUniqueStatisticsDao extends StructDaoImpl<HrRuleUniqueStatisticsDO, HrRuleUniqueStatisticsRecord, HrRuleUniqueStatistics> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS;
   }
}
