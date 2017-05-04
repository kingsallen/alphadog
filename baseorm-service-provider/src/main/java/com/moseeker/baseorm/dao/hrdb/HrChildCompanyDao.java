package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChildCompanyRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChildCompanyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrChildCompanyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrChildCompanyDao extends JooqCrudImpl<HrChildCompanyDO, HrChildCompanyRecord> {


    public HrChildCompanyDao(TableImpl<HrChildCompanyRecord> table, Class<HrChildCompanyDO> hrChildCompanyDOClass) {
        super(table, hrChildCompanyDOClass);
    }
}
