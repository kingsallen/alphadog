package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.ThirdPartyUserDao;
import com.moseeker.thrift.gen.useraccounts.service.ThirdPartyUserService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 用户登陆， 注册，合并等api的实现
 *
 * @author yaofeng
 * @email wangyaofeng@moseeker.com
 */
@Service
@CounterIface
public class ThirdPartyUserService implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    ThirdPartyUserDao.Iface thirdPartyUserDao = ServiceManager.SERVICEMANAGER.getService(ThirdPartyUserDao.Iface.class);


    @Override
    public Response updateUser(ThirdPartyUser user) throws TException {
        return thirdPartyUserDao.putThirdPartyUser(user);
    }
}