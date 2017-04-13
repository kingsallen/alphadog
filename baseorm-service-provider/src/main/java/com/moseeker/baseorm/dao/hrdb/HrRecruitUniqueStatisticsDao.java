package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrRecruitUniqueStatistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRecruitUniqueStatisticsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrRecruitUniqueStatisticsDO;

/**
* @author xxx
* HrRecruitUniqueStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrRecruitUniqueStatisticsDao extends StructDaoImpl<HrRecruitUniqueStatisticsDO, HrRecruitUniqueStatisticsRecord, HrRecruitUniqueStatistics> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrRecruitUniqueStatistics.HR_RECRUIT_UNIQUE_STATISTICS;
   }
}
