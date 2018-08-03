package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserReferralRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserReferralRecordRecord;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.util.query.Query;
import org.jooq.Param;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.userdb.tables.UserReferralRecord.USER_REFERRAL_RECORD;
import static org.jooq.impl.DSL.param;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.selectOne;

/**
 *
 * 员工主动内推表操作类
 * @Author: jack
 * @Date: 2018/8/3
 */
@Repository
public class UserReferralRecordDao extends JooqCrudImpl<UserReferralRecord, UserReferralRecordRecord> {

    public UserReferralRecordDao() {
        super(USER_REFERRAL_RECORD, UserReferralRecord.class);
    }

    public UserReferralRecordDao(TableImpl<UserReferralRecordRecord> table,
                                 Class<UserReferralRecord> clazz) {
        super(table, clazz);
    }

    public UserReferralRecordRecord insertIfNotExist(int referee, Integer companyId, Long mobile) {

        Query query=new Query.QueryBuilder().where("mobile",phone).and("country_code",86)
                .and("source", UserSource.TALENT_UPLOAD.getValue()).and("is_disable",0)
                .buildQuery();
        Param<Integer> refereeParam = param(USER_REFERRAL_RECORD.REFEREE_ID.getName(), referee);
        Param<Integer> companyParam = param(USER_REFERRAL_RECORD.COMPANY_ID.getName(), companyId);
        int execute = create.insertInto(
                USER_REFERRAL_RECORD,
                USER_REFERRAL_RECORD.REFEREE_ID,
                USER_REFERRAL_RECORD.COMPANY_ID
        ).select(
                select(
                        refereeParam,
                        companyParam
                ).whereNotExists(
                        selectOne()
                        .from(UserUser.USER_USER)
                        .where(
                                UserUser.USER_USER.MOBILE.eq(mobile)
                                .and(UserUser.USER_USER.COUNTRY_CODE.eq("86"))
                                .and(UserUser.USER_USER.IS_DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                                .and(UserUser.USER_USER.SOURCE.eq((short) UserSource.TALENT_UPLOAD.getValue()))
                        ).or(
                                UserUser.USER_USER
                        )
                )
        ).execute();
        if (execute == 0) {
        }
    }
}
