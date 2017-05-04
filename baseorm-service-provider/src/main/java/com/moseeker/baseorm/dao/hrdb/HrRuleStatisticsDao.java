package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRuleStatisticsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrRuleStatisticsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrRuleStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrRuleStatisticsDao extends JooqCrudImpl<HrRuleStatisticsDO, HrRuleStatisticsRecord> {


    public HrRuleStatisticsDao(TableImpl<HrRuleStatisticsRecord> table, Class<HrRuleStatisticsDO> hrRuleStatisticsDOClass) {
        super(table, hrRuleStatisticsDOClass);
    }
}
