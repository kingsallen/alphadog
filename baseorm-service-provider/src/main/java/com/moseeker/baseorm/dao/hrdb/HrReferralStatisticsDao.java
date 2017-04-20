package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrReferralStatistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrReferralStatisticsRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrReferralStatisticsDO;

/**
* @author xxx
* HrReferralStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrReferralStatisticsDao extends StructDaoImpl<HrReferralStatisticsDO, HrReferralStatisticsRecord, HrReferralStatistics> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrReferralStatistics.HR_REFERRAL_STATISTICS;
   }
}
