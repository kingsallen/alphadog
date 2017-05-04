package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeePositionRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeePositionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrEmployeePositionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrEmployeePositionDao extends JooqCrudImpl<HrEmployeePositionDO, HrEmployeePositionRecord> {


    public HrEmployeePositionDao(TableImpl<HrEmployeePositionRecord> table, Class<HrEmployeePositionDO> hrEmployeePositionDOClass) {
        super(table, hrEmployeePositionDOClass);
    }
}
