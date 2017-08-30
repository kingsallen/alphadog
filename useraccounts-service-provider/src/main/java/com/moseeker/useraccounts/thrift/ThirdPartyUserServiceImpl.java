package com.moseeker.useraccounts.thrift;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.ThirdPartyUserService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import com.moseeker.useraccounts.service.impl.ThirdPartyUserService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by eddie on 2017/3/7.
 */
@Service
@CounterIface
public class ThirdPartyUserServiceImpl implements Iface {

    @Autowired
    ThirdPartyUserService service;

    @Override
    public Response updateUser(ThirdPartyUser user) throws TException {
        return service.updateUser(user);
    }
}
