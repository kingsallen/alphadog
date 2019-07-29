package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.hrdb.utils.ChatSpeakerType;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChat;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatVoice;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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

    public Result listMessage(int roomId, int chatId, int pageSize) {
        Result chatRecordResult = null;
        if (chatId > 0) {
            chatRecordResult = create.select(HrWxHrChat.HR_WX_HR_CHAT.ORIGIN, HrWxHrChat.HR_WX_HR_CHAT.SPEAKER, HrWxHrChat.HR_WX_HR_CHAT.PIC_URL,
                            HrWxHrChat.HR_WX_HR_CHAT.MSG_TYPE, HrWxHrChat.HR_WX_HR_CHAT.CREATE_TIME, HrWxHrChat.HR_WX_HR_CHAT.ID,
                            HrWxHrChat.HR_WX_HR_CHAT.CONTENT,HrWxHrChat.HR_WX_HR_CHAT.BTN_CONTENT,HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID,
                            HrWxHrChat.HR_WX_HR_CHAT.PID, HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.SERVER_ID, HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.DURATION,
                            HrWxHrChat.HR_WX_HR_CHAT.COMPOUND_CONTENT)
                    .from(HrWxHrChat.HR_WX_HR_CHAT).leftJoin(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE).on(HrWxHrChat.HR_WX_HR_CHAT.ID.eq(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.CHAT_ID))
                    .where(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID.eq(roomId))
                    .and(HrWxHrChat.HR_WX_HR_CHAT.ID.lt(chatId))
                    .orderBy(HrWxHrChat.HR_WX_HR_CHAT.ID.desc())
                    .limit(pageSize)
                    .fetch();
        } else {
            chatRecordResult = create.select(HrWxHrChat.HR_WX_HR_CHAT.ORIGIN, HrWxHrChat.HR_WX_HR_CHAT.SPEAKER, HrWxHrChat.HR_WX_HR_CHAT.PIC_URL,
                            HrWxHrChat.HR_WX_HR_CHAT.MSG_TYPE, HrWxHrChat.HR_WX_HR_CHAT.CREATE_TIME, HrWxHrChat.HR_WX_HR_CHAT.ID,
                            HrWxHrChat.HR_WX_HR_CHAT.CONTENT,HrWxHrChat.HR_WX_HR_CHAT.BTN_CONTENT,HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID,
                            HrWxHrChat.HR_WX_HR_CHAT.PID, HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.SERVER_ID, HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.DURATION,
                            HrWxHrChat.HR_WX_HR_CHAT.COMPOUND_CONTENT)
                    .from(HrWxHrChat.HR_WX_HR_CHAT).leftJoin(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE).on(HrWxHrChat.HR_WX_HR_CHAT.ID.eq(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.CHAT_ID))
                    .where(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID.eq(roomId))
                    .orderBy(HrWxHrChat.HR_WX_HR_CHAT.ID.desc())
                    .limit(pageSize)
                    .fetch();
        }
//        if (chatRecordResult != null && chatRecordResult.size() > 0) {
//            chatVOList = BeanUtils.DBToStruct(ChatVO.class, chatRecordResult);
//        }
        return chatRecordResult;
    }

    /**
     * 查找聊天记录的数量
     * @param roomId
     * @param chatId
     * @return
     */
    public int countMessage(int roomId, int chatId) {
        if (chatId > 0) {
            return create.selectCount()
                    .from(HrWxHrChat.HR_WX_HR_CHAT)
                    .where(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID.eq(roomId))
                    .and(HrWxHrChat.HR_WX_HR_CHAT.ID.lt(chatId))
                    .fetchOne()
                    .value1();
        } else {
            return create.selectCount()
                    .from(HrWxHrChat.HR_WX_HR_CHAT)
                    .where(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID.eq(roomId))
                    .fetchOne()
                    .value1();
        }
    }

    public HrWxHrChatRecord fetchById(int chatId) {
        return create.selectFrom(HrWxHrChat.HR_WX_HR_CHAT)
                .where(HrWxHrChat.HR_WX_HR_CHAT.ID.eq(chatId))
                .fetchOne();
    }

    /**
     * 分页查找聊天室下的聊天记录
     *
     * @param roomId     聊天室id
     * @param startIndex 分页开始下标
     * @param pageSize   每页大小
     * @return Result
     * @author cjm
     * @date 2018/5/17
     */
    public Result listChat(int roomId, int startIndex, int pageSize) {
        return create.select(HrWxHrChat.HR_WX_HR_CHAT.ID, HrWxHrChat.HR_WX_HR_CHAT.CONTENT, HrWxHrChat.HR_WX_HR_CHAT.CREATE_TIME,
                HrWxHrChat.HR_WX_HR_CHAT.MSG_TYPE, HrWxHrChat.HR_WX_HR_CHAT.PIC_URL, HrWxHrChat.HR_WX_HR_CHAT.BTN_CONTENT,HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID,
                HrWxHrChat.HR_WX_HR_CHAT.SPEAKER, HrWxHrChat.HR_WX_HR_CHAT.ORIGIN, HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.SERVER_ID,
                HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.DURATION, HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.LOCAL_URL,
                HrWxHrChat.HR_WX_HR_CHAT.COMPOUND_CONTENT,HrWxHrChat.HR_WX_HR_CHAT.STATS)
                .from(HrWxHrChat.HR_WX_HR_CHAT)
                .leftJoin(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE)
                .on(HrWxHrChat.HR_WX_HR_CHAT.ID.eq(HrWxHrChatVoice.HR_WX_HR_CHAT_VOICE.CHAT_ID))
                .where(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID.eq(roomId))
                .orderBy(HrWxHrChat.HR_WX_HR_CHAT.ID.desc(), HrWxHrChat.HR_WX_HR_CHAT.CREATE_TIME.desc())
                .limit(startIndex, pageSize)
                .fetch();
    }

    /**
     * 获取求职者上一条记录的ID
     *
     * @param roomId 房间ID
     * @param chatId 主键ID
     * @return
     */
    public Integer getUserLastQuestionChatRecordId(int roomId, int chatId) {
         return create.select(HrWxHrChat.HR_WX_HR_CHAT.ID)
                .from(HrWxHrChat.HR_WX_HR_CHAT)
                .where(HrWxHrChat.HR_WX_HR_CHAT.CHATLIST_ID.eq(roomId))
                .and(HrWxHrChat.HR_WX_HR_CHAT.SPEAKER.eq((byte)ChatSpeakerType.USER.getValue()))
                .and(HrWxHrChat.HR_WX_HR_CHAT.ID.lt(chatId))
                .orderBy(HrWxHrChat.HR_WX_HR_CHAT.ID.desc())
                .limit(1).fetchOne().value1();
    }

    /**
     * 更新聊天记录的status
     *
     * @param chatId 主键ID
     * @param status 状态， 对应ChatStatus类
     */
    public void updateChatStatus(int chatId, byte status) {
        create.update(HrWxHrChat.HR_WX_HR_CHAT)
                .set(HrWxHrChat.HR_WX_HR_CHAT.STATUS, status)
                .where(HrWxHrChat.HR_WX_HR_CHAT.ID.eq(chatId))
                .execute();
    }

}
