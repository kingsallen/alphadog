package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserThirdpartyUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserThirdpartyUserRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserThirdpartyUserDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

/**
 * Created by eddie on 2017/3/7.
 */
@Repository
public class ThirdPartyUserDao extends JooqCrudImpl<UserThirdpartyUserDO, UserThirdpartyUserRecord> {

    public ThirdPartyUserDao() {
        super(UserThirdpartyUser.USER_THIRDPARTY_USER, UserThirdpartyUserDO.class);
    }

    public ThirdPartyUserDao(TableImpl<UserThirdpartyUserRecord> table, Class<UserThirdpartyUserDO> userThirdpartyUserDOClass) {
        super(table, userThirdpartyUserDOClass);
    }

    public Response putThirdPartyUser(ThirdPartyUser user) {
        try {
            UserThirdpartyUserRecord record = null;
            if (!user.isSetId()) {
                if (user.isSetUser_id() && user.isSetSource_id()) {
                	Query query=new Query.QueryBuilder().where("user_id",user.getUser_id()).and("source_id",user.getSource_id()).buildQuery();
                    UserThirdpartyUserRecord thirdPartyUser = getRecord(query);
                    if (thirdPartyUser != null) {
                        record = BeanUtils.structToDB(user, UserThirdpartyUserRecord.class);
                        record.setId(thirdPartyUser.getId());
                    }
                }
            } else {
                record = BeanUtils.structToDB(user, UserThirdpartyUserRecord.class);
            }

            if (record != null) {
                Timestamp updateTime = new Timestamp(System.currentTimeMillis());
                record.setUpdateTime(updateTime);
                int row = updateRecord(record);
                if (row > 0) {
                    return ResponseUtils.success(row);
                } else {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
                }
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
