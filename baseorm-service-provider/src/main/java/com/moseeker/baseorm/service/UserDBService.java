package com.moseeker.baseorm.service;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;

/**
 * Created by moseeker on 2017/3/15.
 */
public interface UserDBService {
    Response putThirdPartyUser(ThirdPartyUser user);
}
