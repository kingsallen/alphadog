package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProviderServiceImpl {

    @Autowired
    UserAccountEntity userAccountEntity;

    public UserUserDO getCompanyUser(int appid, String phone, int companyId) throws BIZException, TException {
        UserUserRecord companyUser = userAccountEntity.getCompanyUser(phone, companyId);
        if (companyUser == null) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.USER_USER_NOTEXIST);
        }
        return companyUser.into(UserUserDO.class);
    }

}
