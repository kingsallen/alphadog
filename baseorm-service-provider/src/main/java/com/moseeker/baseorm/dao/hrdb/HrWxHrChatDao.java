package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxHrChatDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxHrChatDao extends JooqCrudImpl<HrWxHrChatDO, HrWxHrChatRecord> {


    public HrWxHrChatDao(TableImpl<HrWxHrChatRecord> table, Class<HrWxHrChatDO> hrWxHrChatDOClass) {
        super(table, hrWxHrChatDOClass);
    }
}
