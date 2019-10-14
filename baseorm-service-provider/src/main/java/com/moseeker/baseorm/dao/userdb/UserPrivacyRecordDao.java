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

import java.util.Optional;

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
    public Optional<com.moseeker.baseorm.db.userdb.tables.pojos.UserPrivacyRecord> ifViewPrivacyProtocol(int userId) throws Exception {
        UserPrivacyRecordRecord record = create.selectFrom(UserPrivacyRecord.USER_PRIVACY_RECORD)
                .where(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID.eq(userId))
                .fetchOne();
        if (record != null) {
            return Optional.of(record.into(com.moseeker.baseorm.db.userdb.tables.pojos.UserPrivacyRecord.class));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 同意最新的隐私版本
     * 之前的代码如果同意了隐私协议则删除。本期由于需要区分同意了老版本和最新版本隐私协议，所以增加了隐私协议版本号。
     * 如果用户同意的隐私协议版本号不是最新，那么用户同意的就是老版本的隐私协议。
     *
     * @param userId user_user.id
     * @param version 隐私协议版本号
     * @throws Exception
     */
    public void agreeReleasePrivacy(int userId, byte version) throws Exception {
        UserPrivacyRecordRecord record = create.selectFrom(UserPrivacyRecord.USER_PRIVACY_RECORD)
                .where(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID.eq(userId))
                .fetchOne();
        //如果record 不存在那么添加最新版本的同意记录。
        if (record == null) {
            create.insertInto(UserPrivacyRecord.USER_PRIVACY_RECORD)
                    .columns(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID, UserPrivacyRecord.USER_PRIVACY_RECORD.VERSION)
                    .values(userId,version)
                    .onDuplicateKeyUpdate()
                    .set(UserPrivacyRecord.USER_PRIVACY_RECORD.VERSION, version)
                    .execute();
        } else {
            //如果已经存在并且版本号比指定的版本号小，那么更新到指定的版本号
            if (record.getVersion() < version) {
                create.update(UserPrivacyRecord.USER_PRIVACY_RECORD)
                        .set(UserPrivacyRecord.USER_PRIVACY_RECORD.VERSION, version)
                        .where(UserPrivacyRecord.USER_PRIVACY_RECORD.ID.eq(record.getId()))
                        .execute();
            }
        }
    }

    /**
     * 新用户插入隐私协议未阅读记录
     * 如果添加时触发唯一索引，那么更新成最新的版本号
     * @param userId 用户编号
     * @param version 版本号
     * @throws Exception
     */
    public void insertPrivacyRecord(int userId, byte version) throws Exception {

        create.insertInto(UserPrivacyRecord.USER_PRIVACY_RECORD)
                .columns(UserPrivacyRecord.USER_PRIVACY_RECORD.USER_ID, UserPrivacyRecord.USER_PRIVACY_RECORD.VERSION)
                .values(userId,version)
                .onDuplicateKeyIgnore()
                .execute();
    }

}
