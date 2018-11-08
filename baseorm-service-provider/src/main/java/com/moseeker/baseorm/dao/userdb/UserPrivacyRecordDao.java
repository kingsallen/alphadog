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

    public int ifViewPrivacyProtocol(int userId) throws BIZException, TException {
        UserPrivacyRecordRecord record = null;
        try {
            record = create.selectFrom(UserPrivacyRecord.USER_PRIVACY_RECORD)
                    .where(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID.eq(userId)).fetchOne();
            logger.info(record.toString());
            //有记录，说明未阅读协议
            if (record != null) {
                return 0;
            }
            return 1;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return 0;
        }

    }

    /**
     * 根据userId删除记录
     *
     * @param userId user_user.id
     * @throws BIZException
     * @throws TException
     */
    public void deletePrivacyRecordByUserId(int userId) throws BIZException, TException {

        try {
            create.deleteFrom(UserPrivacyRecord.USER_PRIVACY_RECORD)
                    .where(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID.eq(userId)).execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 新用户插入隐私协议未阅读记录
     *
     * @param userId
     * @throws BIZException
     * @throws TException
     */
    public void insertPrivacyRecord(int userId) throws BIZException, TException {

        try {
            create.insertInto(UserPrivacyRecord.USER_PRIVACY_RECORD).set(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID, userId).execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }


    }

}
