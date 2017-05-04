package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrFeedbackRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrFeedbackDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrFeedbackDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrFeedbackDao extends JooqCrudImpl<HrFeedbackDO, HrFeedbackRecord> {


    public HrFeedbackDao(TableImpl<HrFeedbackRecord> table, Class<HrFeedbackDO> hrFeedbackDOClass) {
        super(table, hrFeedbackDOClass);
    }
}
