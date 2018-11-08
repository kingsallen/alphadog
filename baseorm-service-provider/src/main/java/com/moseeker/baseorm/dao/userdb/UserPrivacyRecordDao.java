package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserPrivacyRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserPrivacyRecordRecord;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.apache.thrift.TException;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户隐私协议记录Dao
 *
 * Created by lee on 2018/11/7.
 */
@Repository
public class UserPrivacyRecordDao extends JooqCrudImpl<Object, UserPrivacyRecordRecord> {
    public UserPrivacyRecordDao() {
        super(UserPrivacyRecord.USER_PRIVACY_RECORD, Object.class);
    }

    public UserPrivacyRecordDao(TableImpl<UserPrivacyRecordRecord> table, Class<Object> objectClass) {
        super(table, objectClass);
    }

    /**
     * 查看用户是否有阅读隐私协议
     *
     * @param userId user_user.id
     * @return 1:未读，弹窗， 0：已读，不弹窗
     * @throws Exception
     */
    public int ifViewPrivacyProtocol(int userId) throws Exception {
        UserPrivacyRecordRecord record = null;
        record = create.selectFrom(UserPrivacyRecord.USER_PRIVACY_RECORD)
                .where(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID.eq(userId)).fetchOne();
        //有记录，说明未阅读协议，弹窗
        if (record != null) {
            return 1;
        }
        return 0;
    }

    /**
     * 根据userId删除记录
     *
     * @param userId user_user.id
     * @throws Exception
     */
    public void deletePrivacyRecordByUserId(int userId) throws Exception {
        create.deleteFrom(UserPrivacyRecord.USER_PRIVACY_RECORD)
                .where(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID.eq(userId)).execute();
    }

    /**
     * 新用户插入隐私协议未阅读记录
     *
     * @param userId
     * @throws Exception
     */
    public void insertPrivacyRecord(int userId) throws Exception {
        create.insertInto(UserPrivacyRecord.USER_PRIVACY_RECORD).set(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID, userId).execute();
    }

}
