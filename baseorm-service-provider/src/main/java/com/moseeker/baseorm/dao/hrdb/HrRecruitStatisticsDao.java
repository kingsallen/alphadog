package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrRecruitStatistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRecruitStatisticsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrRecruitStatisticsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrRecruitStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrRecruitStatisticsDao extends JooqCrudImpl<HrRecruitStatisticsDO, HrRecruitStatisticsRecord> {

    public HrRecruitStatisticsDao() {
        super(HrRecruitStatistics.HR_RECRUIT_STATISTICS, HrRecruitStatisticsDO.class);
    }

    public HrRecruitStatisticsDao(TableImpl<HrRecruitStatisticsRecord> table, Class<HrRecruitStatisticsDO> hrRecruitStatisticsDOClass) {
        super(table, hrRecruitStatisticsDOClass);
    }
}
