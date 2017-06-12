package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxHrChatListDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxHrChatListDao extends JooqCrudImpl<HrWxHrChatListDO, HrWxHrChatListRecord> {

    public HrWxHrChatListDao() {
        super(HrWxHrChatList.HR_WX_HR_CHAT_LIST, HrWxHrChatListDO.class);
    }

    public HrWxHrChatListDao(TableImpl<HrWxHrChatListRecord> table, Class<HrWxHrChatListDO> hrWxHrChatListDOClass) {
        super(table, hrWxHrChatListDOClass);
    }
}
