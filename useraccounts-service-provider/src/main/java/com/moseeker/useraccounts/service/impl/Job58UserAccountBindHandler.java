package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.IBindRequest;

import java.util.Map;

/**
 * @author cjm
 * @date 2018-11-21 14:13
 **/
public class Job58UserAccountBindHandler implements IBindRequest {
    @Override
    public HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        // todo code刷token，access_token, refresh_token获取

        // todo code刷用户信息，获取username

        // todo 保存code, username

        return null;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB58;
    }
}
