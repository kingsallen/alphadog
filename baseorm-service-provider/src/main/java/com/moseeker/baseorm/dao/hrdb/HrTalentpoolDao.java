package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTalentpoolRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTalentpoolDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrTalentpoolDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrTalentpoolDao extends JooqCrudImpl<HrTalentpoolDO, HrTalentpoolRecord> {


    public HrTalentpoolDao(TableImpl<HrTalentpoolRecord> table, Class<HrTalentpoolDO> hrTalentpoolDOClass) {
        super(table, hrTalentpoolDOClass);
    }
}
