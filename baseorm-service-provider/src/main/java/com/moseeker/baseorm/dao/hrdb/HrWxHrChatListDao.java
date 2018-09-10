package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import static org.jooq.impl.DSL.sum;

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

    /**
     * 计算HR未读聊天数量
     * @param hrId
     * @return
     */
    public int countUnreadMessage(int hrId) {
        return create.select(sum(HrWxHrChatList.HR_WX_HR_CHAT_LIST.HR_UNREAD_COUNT))
                .from(HrWxHrChatList.HR_WX_HR_CHAT_LIST)
                .where(HrWxHrChatList.HR_WX_HR_CHAT_LIST.HRACCOUNT_ID.eq(hrId))
                .fetchOne()
                .value1()
                .intValue();
    }
    /**
     * 查出公司id和accessToken
     * @param roomId 聊天室id
     * @author  cjm
     * @date  2018/5/14
     * @return Result
     */
    public Result getCompanyIdAndTokenByRoomId(int roomId) {
        return create.select(HrWxWechat.HR_WX_WECHAT.COMPANY_ID, HrWxWechat.HR_WX_WECHAT.ACCESS_TOKEN)
                .from(HrWxHrChatList.HR_WX_HR_CHAT_LIST)
                .join(HrCompanyAccount.HR_COMPANY_ACCOUNT)
                .on(HrWxHrChatList.HR_WX_HR_CHAT_LIST.HRACCOUNT_ID.eq(HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID))
                .join(HrWxWechat.HR_WX_WECHAT)
                .on(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.eq(HrCompanyAccount.HR_COMPANY_ACCOUNT.COMPANY_ID))
                .where(HrWxHrChatList.HR_WX_HR_CHAT_LIST.ID.eq(roomId))
                .fetch();
    }


    public int updateWelcomeStatusByHrAccountId(int hrId){
        return create.update(HrWxHrChatList.HR_WX_HR_CHAT_LIST)
                .set(HrWxHrChatList.HR_WX_HR_CHAT_LIST.WELCOME_STATUS, (byte)1)
                .where(HrWxHrChatList.HR_WX_HR_CHAT_LIST.HRACCOUNT_ID.eq(hrId))
                .execute();
    }
}
