package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.HrWxHrChatDO;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 09/03/2017.
 */
@Repository
public class ChatDao extends StructDaoImpl<HrWxHrChatDO, HrWxHrChatRecord, HrWxHrChat>{

    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrWxHrChat.HR_WX_HR_CHAT;
    }
}
