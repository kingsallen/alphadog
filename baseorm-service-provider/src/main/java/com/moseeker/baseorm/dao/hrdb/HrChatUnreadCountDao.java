package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrChatUnreadCount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChatUnreadCountRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChatUnreadCountDO;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 查找hr的聊天用户
     * @param hrId
     * @param apply
     * @return
     */
    public List<Integer> fetchUserIdByHRId(int hrId, boolean apply) {
        byte applied = (byte) (apply? 1:0);
        Result<Record1<Integer>> result = create
                .select(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID)
                .from(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY.eq(applied))
                .groupBy(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID)
                .fetch();

        String sql = create
                .select(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID)
                .from(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY.eq(applied))
                .groupBy(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID).getSQL();
        logger.info("fetchUserIdByHRId sql:{}", sql);
        if (result != null) {
            return result.stream().map(record -> record.value1()).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public HrChatUnreadCountRecord fetchByHrIdAndUserId(int hrId, int userId) {
        return create.selectFrom(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID.eq(userId))
                .fetchOne();
    }

    public int countRoom(int hrId, List<Integer> userIdList, boolean apply) {
        byte applyByte = apply?(byte)1:(byte)0;
        if (userIdList == null && userIdList.size() == 0) {
            return create
                    .selectCount()
                    .from(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                    .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                    .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY.eq(applyByte))
                    .fetchOne()
                    .value1();
        } else {
            return create
                    .selectCount()
                    .from(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                    .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                    .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY.eq(applyByte))
                    .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID.in(userIdList))
                    .fetchOne()
                    .value1();
        }
    }

    public List<HrChatUnreadCountRecord> fetchRooms(int hrId, List<Integer> userIdList, boolean apply, Timestamp updateTime, int pageSize) {
        byte applyByte = apply?(byte)1:(byte)0;
        if (userIdList == null && userIdList.size() == 0) {

            if (updateTime == null) {
                return create
                        .selectFrom(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                        .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                        .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY.eq(applyByte))
                        .orderBy(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.UPDATE_TIME.desc())
                        .limit(pageSize)
                        .fetch();
            } else {
                return create
                        .selectFrom(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                        .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                        .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY.eq(applyByte))
                        .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.UPDATE_TIME.lt(updateTime))
                        .orderBy(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.UPDATE_TIME.desc())
                        .limit(pageSize)
                        .fetch();
            }

        } else {
            if (updateTime == null) {
                return create
                        .selectFrom(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                        .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                        .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY.eq(applyByte))
                        .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID.in(userIdList))
                        .orderBy(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.UPDATE_TIME.desc())
                        .limit(pageSize)
                        .fetch();
            } else {
                return create
                        .selectFrom(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                        .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                        .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY.eq(applyByte))
                        .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.UPDATE_TIME.lt(updateTime))
                        .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID.in(userIdList))
                        .orderBy(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.UPDATE_TIME.desc())
                        .limit(pageSize)
                        .fetch();
            }
        }
    }

    public HrChatUnreadCountRecord fetchById(int roomId) {
        return create.selectFrom(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.ROOM_ID.eq(roomId))
                .fetchOne();
    }

    public void updateApply(int publisher, int userId) {
        create.update(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                .set(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.APPLY, (byte)1)
                .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(publisher))
                .and(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID.eq(userId))
                .execute();
    }
}
