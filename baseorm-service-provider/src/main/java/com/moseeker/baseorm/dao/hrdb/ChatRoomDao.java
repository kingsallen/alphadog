package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 09/03/2017.
 */
@Repository
public class ChatRoomDao extends JooqCrudImpl<HrWxHrChatListDO, HrWxHrChatListRecord> {

    public ChatRoomDao() {
        super(HrWxHrChatList.HR_WX_HR_CHAT_LIST, HrWxHrChatListDO.class);
    }

    public ChatRoomDao(TableImpl<HrWxHrChatListRecord> table, Class<HrWxHrChatListDO> hrWxHrChatListDOClass) {
        super(table, hrWxHrChatListDOClass);
    }
}
