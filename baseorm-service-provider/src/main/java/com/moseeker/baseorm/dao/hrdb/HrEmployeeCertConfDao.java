package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCertConfRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;


@Repository
public class HrEmployeeCertConfDao extends JooqCrudImpl<HrEmployeeCertConfDO, HrEmployeeCertConfRecord> {

    public HrEmployeeCertConfDao(TableImpl<HrEmployeeCertConfRecord> table, Class<HrEmployeeCertConfDO> hrEmployeeCertConfDOClass) {
        super(table, hrEmployeeCertConfDOClass);
    }
}
