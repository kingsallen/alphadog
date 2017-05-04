package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 09/03/2017.
 */
@Repository
public class ChatDao extends JooqCrudImpl<HrWxHrChatDO, HrWxHrChatRecord> {

    public ChatDao(TableImpl<HrWxHrChatRecord> table, Class<HrWxHrChatDO> hrWxHrChatDOClass) {
        super(table, hrWxHrChatDOClass);
    }
}
