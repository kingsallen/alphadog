package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrChatUnreadCount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChatUnreadCountRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrChatUnreadCountDO;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
     * @return
     */
    public List<Integer> fetchUserIdByHRId(int hrId) {
        Result<Record1<Integer>> result = create
                .select(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID)
                .from(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT)
                .where(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.HR_ID.eq(hrId))
                .groupBy(HrChatUnreadCount.HR_CHAT_UNREAD_COUNT.USER_ID)
                .fetch();
        if (result != null) {
            return result.stream().map(record -> record.value1()).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
