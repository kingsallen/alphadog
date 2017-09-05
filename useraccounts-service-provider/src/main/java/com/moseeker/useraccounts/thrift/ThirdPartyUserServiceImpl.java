package com.moseeker.useraccounts.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.useraccounts.service.ThirdPartyUserService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import com.moseeker.useraccounts.service.impl.ThirdPartyUserService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (todo 可能没人用)
 * Created by eddie on 2017/3/7.
 */
@Service
public class ThirdPartyUserServiceImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    ThirdPartyUserService service;

    @Override
    public Response updateUser(ThirdPartyUser user) throws TException {
        try {
            return service.updateUser(user);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }
}
