package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrReferralStatisticsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrReferralStatisticsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrReferralStatisticsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrReferralStatisticsDao extends JooqCrudImpl<HrReferralStatisticsDO, HrReferralStatisticsRecord> {


    public HrReferralStatisticsDao(TableImpl<HrReferralStatisticsRecord> table, Class<HrReferralStatisticsDO> hrReferralStatisticsDOClass) {
        super(table, hrReferralStatisticsDOClass);
    }
}
