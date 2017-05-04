package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrTeamDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrTeamDao extends JooqCrudImpl<HrTeamDO, HrTeamRecord> {


    public HrTeamDao(TableImpl<HrTeamRecord> table, Class<HrTeamDO> hrTeamDOClass) {
        super(table, hrTeamDOClass);
    }
}
