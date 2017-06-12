package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrRuleUniqueStatistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRuleUniqueStatisticsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrRuleUniqueStatisticsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrRuleUniqueStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrRuleUniqueStatisticsDao extends JooqCrudImpl<HrRuleUniqueStatisticsDO, HrRuleUniqueStatisticsRecord> {

    public HrRuleUniqueStatisticsDao() {
        super(HrRuleUniqueStatistics.HR_RULE_UNIQUE_STATISTICS, HrRuleUniqueStatisticsDO.class);
    }

    public HrRuleUniqueStatisticsDao(TableImpl<HrRuleUniqueStatisticsRecord> table, Class<HrRuleUniqueStatisticsDO> hrRuleUniqueStatisticsDOClass) {
        super(table, hrRuleUniqueStatisticsDOClass);
    }
}
