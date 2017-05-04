package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrAppCvConfRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrAppCvConfDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrAppCvConfDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrAppCvConfDao extends JooqCrudImpl<HrAppCvConfDO, HrAppCvConfRecord> {


    public HrAppCvConfDao(TableImpl<HrAppCvConfRecord> table, Class<HrAppCvConfDO> hrAppCvConfDOClass) {
        super(table, hrAppCvConfDOClass);
    }
}
