package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.thrift.gen.chat.struct.ChatVO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.max;

/**
* @author xxx
* HrWxHrChatDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxHrChatDao extends JooqCrudImpl<HrWxHrChatDO, HrWxHrChatRecord> {

    public HrWxHrChatDao() {
        super(HrWxHrChat.HR_WX_HR_CHAT, HrWxHrChatDO.class);
    }

    public HrWxHrChatDao(TableImpl<HrWxHrChatRecord> table, Class<HrWxHrChatDO> hrWxHrChatDOClass) {
        super(table, hrWxHrChatDOClass);
    }

    public List<Integer> lastMessageIdList(List<Integer> roomIdList) {
        Result<Record1<Integer>> result = create.select(max(HrWxHrChat.HR_WX_HR_CHAT.ID))
                .from(HrWxHrChat.HR_WX_HR_CHAT)
                .where(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID.in(roomIdList))
                .groupBy(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID)
                .fetch();

        if (result != null && result.size() > 0) {
            return result.stream().map(record -> record.value1()).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public List<ChatVO> listMessage(int roomId, int chatId, int pageSize) {

        List<ChatVO> chatVOList = new ArrayList<>();

        Result<HrWxHrChatRecord> chatRecordResult = create
                .selectFrom(HrWxHrChat.HR_WX_HR_CHAT)
                .where(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID.eq(roomId))
                .and(HrWxHrChat.HR_WX_HR_CHAT.ID.lt(chatId))
                .orderBy(HrWxHrChat.HR_WX_HR_CHAT.CREATE_TIME.desc())
                .limit(pageSize)
                .fetch();
        if (chatRecordResult != null && chatRecordResult.size() > 0) {
            chatVOList = BeanUtils.DBToStruct(ChatVO.class, chatRecordResult);
        }

        return chatVOList;
    }

    /**
     * 查找聊天记录的数量
     * @param roomId
     * @param chatId
     * @return
     */
    public int countMessage(int roomId, int chatId) {
        return create.selectCount()
                .from(HrWxHrChat.HR_WX_HR_CHAT)
                .where(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID.eq(roomId))
                .and(HrWxHrChat.HR_WX_HR_CHAT.ID.lt(chatId))
                .fetchOne()
                .value1();
    }
}
