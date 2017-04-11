package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.db.userdb.tables.UserThirdpartyUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserThirdpartyUserRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by eddie on 2017/3/7.
 */
@Service
public class ThirdPartyUserDao extends BaseDaoImpl<UserThirdpartyUserRecord, UserThirdpartyUser> {
    @Override
    protected void initJOOQEntity() {
        tableLike = UserThirdpartyUser.USER_THIRDPARTY_USER;
    }

    public Response putThirdPartyUser(ThirdPartyUser user) {
        try {
            UserThirdpartyUserRecord record = null;
            if (!user.isSetId()) {
                if (user.isSetUser_id() && user.isSetSource_id()) {
                    QueryUtil queryUtil = new QueryUtil();
                    queryUtil.addEqualFilter("user_id", String.valueOf(user.getUser_id()));
                    queryUtil.addEqualFilter("source_id", String.valueOf(user.getSource_id()));
                    UserThirdpartyUserRecord thirdPartyUser = getResource(queryUtil);
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
                int row = putResource(record);
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
