package com.moseeker.useraccounts.thrift;

import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.useraccounts.service.UserProviderService;
import com.moseeker.useraccounts.service.impl.UserProviderServiceImpl;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProviderThriftServiceImpl implements UserProviderService.Iface {

    @Autowired
    private UserProviderServiceImpl userProviderService;

    @Override
    public UserUserDO getCompanyUser(int appid, String phone, int companyId) throws TException {
        try {
            return userProviderService.getCompanyUser(appid, phone, companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public UserUserDO storeChatBotUser(String profilePojo, int reference, int companyId, int source, int appid) throws TException {
        try {
            return userProviderService.storeChatBotUser(profilePojo, reference, companyId, source, appid);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}
