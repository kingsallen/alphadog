package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSuperaccountApplyRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrSuperaccountApplyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrSuperaccountApplyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrSuperaccountApplyDao extends JooqCrudImpl<HrSuperaccountApplyDO, HrSuperaccountApplyRecord> {


    public HrSuperaccountApplyDao(TableImpl<HrSuperaccountApplyRecord> table, Class<HrSuperaccountApplyDO> hrSuperaccountApplyDOClass) {
        super(table, hrSuperaccountApplyDOClass);
    }
}
