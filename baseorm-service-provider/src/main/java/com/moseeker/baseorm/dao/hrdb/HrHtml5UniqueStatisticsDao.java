package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHtml5UniqueStatisticsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHtml5UniqueStatisticsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrHtml5UniqueStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrHtml5UniqueStatisticsDao extends JooqCrudImpl<HrHtml5UniqueStatisticsDO, HrHtml5UniqueStatisticsRecord> {


    public HrHtml5UniqueStatisticsDao(TableImpl<HrHtml5UniqueStatisticsRecord> table, Class<HrHtml5UniqueStatisticsDO> hrHtml5UniqueStatisticsDOClass) {
        super(table, hrHtml5UniqueStatisticsDOClass);
    }
}
