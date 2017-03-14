package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.user.ThirdPartyUserDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserThirdpartyUserRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.thrift.gen.dao.service.ThirdPartyUserDao.Iface;

import java.sql.Timestamp;

/**
 * Created by eddie on 2017/3/7.
 */
@Service
public class ThirdPartyUserDaoThriftService implements Iface {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    ThirdPartyUserDao thirdPartyUserDao;

    @Override
    public Response putThirdPartyUser(ThirdPartyUser user) throws TException {
        try {
            int result = thirdPartyUserDao.putThirdPartyUser(user);
            return ResponseUtils.success(result);
        } catch (Exception e) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }
}
