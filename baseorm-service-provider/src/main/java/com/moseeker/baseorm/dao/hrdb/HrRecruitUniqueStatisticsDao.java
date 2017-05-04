package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRecruitUniqueStatisticsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrRecruitUniqueStatisticsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrRecruitUniqueStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrRecruitUniqueStatisticsDao extends JooqCrudImpl<HrRecruitUniqueStatisticsDO, HrRecruitUniqueStatisticsRecord> {


    public HrRecruitUniqueStatisticsDao(TableImpl<HrRecruitUniqueStatisticsRecord> table, Class<HrRecruitUniqueStatisticsDO> hrRecruitUniqueStatisticsDOClass) {
        super(table, hrRecruitUniqueStatisticsDOClass);
    }
}
