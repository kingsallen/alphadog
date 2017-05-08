package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHtml5Statistics;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHtml5StatisticsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHtml5StatisticsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrHtml5StatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrHtml5StatisticsDao extends JooqCrudImpl<HrHtml5StatisticsDO, HrHtml5StatisticsRecord> {

    public HrHtml5StatisticsDao() {
        super(HrHtml5Statistics.HR_HTML5_STATISTICS, HrHtml5StatisticsDO.class);
    }

    public HrHtml5StatisticsDao(TableImpl<HrHtml5StatisticsRecord> table, Class<HrHtml5StatisticsDO> hrHtml5StatisticsDOClass) {
        super(table, hrHtml5StatisticsDOClass);
    }
}
