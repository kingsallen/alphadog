package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrCompanyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrCompanyDao extends JooqCrudImpl<HrCompanyDO, HrCompanyRecord> {


    public HrCompanyDao(TableImpl<HrCompanyRecord> table, Class<HrCompanyDO> hrCompanyDOClass) {
        super(table, hrCompanyDOClass);
    }
}
