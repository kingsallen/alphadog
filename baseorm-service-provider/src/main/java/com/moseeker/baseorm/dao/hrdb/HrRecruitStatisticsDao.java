package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrRecruitStatistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRecruitStatisticsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrRecruitStatisticsDO;

/**
* @author xxx
* HrRecruitStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrRecruitStatisticsDao extends StructDaoImpl<HrRecruitStatisticsDO, HrRecruitStatisticsRecord, HrRecruitStatistics> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrRecruitStatistics.HR_RECRUIT_STATISTICS;
   }
}
