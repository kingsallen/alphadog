package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 09/03/2017.
 */
@Repository
public class ChatRoomDao extends StructDaoImpl<HrWxHrChatListDO, HrWxHrChatListRecord, HrWxHrChatList> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrWxHrChatList.HR_WX_HR_CHAT_LIST;
    }

    /*public List<UnReadSortRoomListDO> listUnReadSortRoom(int id, byte speaker, int pageNo, int pageSize) {
        List<UnReadSortRoomListDO> unReadSortRoomListDOList = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        sb.append(" select room.id as id, count(chat.id) as count ");

        sb.append(" from hrdb.hr_wx_hr_chat_list room inner join hrdb.hr_wx_hr_chat chat on room.id = chat.chatlist_id ");

        if(speaker == 0) {
            sb.append(" and room.wx_chat_time < chat.create_time ");
            sb.append(" and chat.speaker = ");
            sb.append(ChatSpeakerType.USER.getValue());
            sb.append(" where chat.sysuser_id = ");
            sb.append(id);

        } else {
            sb.append(" and room.hr_chat_time < chat.create_time ");
            sb.append(" and chat.speaker = ");
            sb.append(ChatSpeakerType.HR.getValue());
            sb.append(" where chat.hraccount_id = ");
            sb.append(id);
        }
        sb.append(" group by room.id ");

        return unReadSortRoomListDOList;
    }*/
}
