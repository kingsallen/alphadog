package com.moseeker.baseorm.service.Impl;

import com.moseeker.baseorm.dao.userdb.ThirdPartyUserDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserThirdpartyUserRecord;
import com.moseeker.baseorm.service.UserDBService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by moseeker on 2017/3/15.
 */
@Service
public class UserDBServiceImpl implements UserDBService {

    @Autowired
    ThirdPartyUserDao thirdPartyUserDao;
    @Autowired
    UserEmployeeDao userEmployeeDao;

    @Override
    public Response putThirdPartyUser(ThirdPartyUser user) {
        try {
            UserThirdpartyUserRecord record = null;
            if (!user.isSetId()) {
                if (user.isSetUser_id() && user.isSetSource_id()) {
                    QueryUtil queryUtil = new QueryUtil();
                    queryUtil.addEqualFilter("user_id", String.valueOf(user.getUser_id()));
                    queryUtil.addEqualFilter("source_id", String.valueOf(user.getSource_id()));
                    UserThirdpartyUserRecord thirdPartyUser = thirdPartyUserDao.getResource(queryUtil);
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
                int row = thirdPartyUserDao.putResource(record);
                if (row > 0) {
                    return ResponseUtils.success(row);
                } else {
                    return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
                }
            } else {
                //没有的话添加
                record = BeanUtils.structToDB(user, UserThirdpartyUserRecord.class);
                thirdPartyUserDao.postResource(record);
                return ResponseUtils.success(1);
            }
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
