package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrCompanyAccountDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrCompanyAccountDao extends JooqCrudImpl<HrCompanyAccountDO, HrCompanyAccountRecord> {


    public HrCompanyAccountDao(TableImpl<HrCompanyAccountRecord> table, Class<HrCompanyAccountDO> hrCompanyAccountDOClass) {
        super(table, hrCompanyAccountDOClass);
    }
}
