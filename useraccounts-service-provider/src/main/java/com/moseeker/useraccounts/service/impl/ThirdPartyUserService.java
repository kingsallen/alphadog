package com.moseeker.useraccounts.service.impl;

import com.moseeker.baseorm.dao.userdb.ThirdPartyUserDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StructSerializer;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.userdb.UserThirdpartyUserDO;
import com.moseeker.thrift.gen.useraccounts.service.ThirdPartyUserService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户登陆， 注册，合并等api的实现
 *
 * @author yaofeng
 * @email wangyaofeng@moseeker.com
 */
@Service
@CounterIface
public class ThirdPartyUserService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThirdPartyUserDao thirdPartyUserDao;

    public Response updateUser(ThirdPartyUser user) throws TException {
        return thirdPartyUserDao.putThirdPartyUser(user);
    }

    public Response get(Query query) throws TException {
        try {
            List<UserThirdpartyUserDO> list=thirdPartyUserDao.getDatas(query);
            return ResponseUtils.successWithoutStringify(StructSerializer.toString(list));
        } catch (Exception e) {
            logger.error("getResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
    }
}