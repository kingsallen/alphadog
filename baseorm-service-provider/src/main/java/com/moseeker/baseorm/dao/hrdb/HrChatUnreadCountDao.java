package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrChatUnreadCount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChatUnreadCountRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChatUnreadCountDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 09/03/2017.
 */
@Repository
public class HrChatUnreadCountDao extends JooqCrudImpl<HrChatUnreadCountDO, HrChatUnreadCountRecord> {

    public HrChatUnreadCountDao() {
        super(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT, HrChatUnreadCountDO.class);
    }

    public HrChatUnreadCountDao(TableImpl<HrChatUnreadCountRecord> table, Class<HrChatUnreadCountDO> hrChatUnreadCountDOClass) {
        super(table, hrChatUnreadCountDOClass);
    }
}
