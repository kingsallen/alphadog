package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.service.UserDBService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.ThirdPartyUserDao.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by eddie on 2017/3/7.
 */
@Service
public class ThirdPartyUserDaoThriftService implements Iface {
    @Autowired
    UserDBService userDBService;

    @Override
    public Response putThirdPartyUser(ThirdPartyUser user) throws TException {
        return userDBService.putThirdPartyUser(user);
    }
}
