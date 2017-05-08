package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxModule;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxModuleRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxModuleDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxModuleDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxModuleDao extends JooqCrudImpl<HrWxModuleDO, HrWxModuleRecord> {

    public HrWxModuleDao() {
        super(HrWxModule.HR_WX_MODULE, HrWxModuleDO.class);
    }

    public HrWxModuleDao(TableImpl<HrWxModuleRecord> table, Class<HrWxModuleDO> hrWxModuleDOClass) {
        super(table, hrWxModuleDOClass);
    }
}
