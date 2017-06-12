package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxBasicReply;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxBasicReplyRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxBasicReplyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxBasicReplyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxBasicReplyDao extends JooqCrudImpl<HrWxBasicReplyDO, HrWxBasicReplyRecord> {

    public HrWxBasicReplyDao() {
        super(HrWxBasicReply.HR_WX_BASIC_REPLY, HrWxBasicReplyDO.class);
    }

    public HrWxBasicReplyDao(TableImpl<HrWxBasicReplyRecord> table, Class<HrWxBasicReplyDO> hrWxBasicReplyDOClass) {
        super(table, hrWxBasicReplyDOClass);
    }
}
