package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrResourceRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrResourceDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrResourceDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrResourceDao extends JooqCrudImpl<HrResourceDO, HrResourceRecord> {


    public HrResourceDao(TableImpl<HrResourceRecord> table, Class<HrResourceDO> hrResourceDOClass) {
        super(table, hrResourceDOClass);
    }
}
