package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.common.constants.BindingStatus;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;

import java.util.List;

public interface BindState {
    HrThirdPartyAccountDO bind(int hrId,HrThirdPartyAccountDO thirdPartyAccountDO) throws Exception;

    HrThirdPartyAccountDO bindConfirm(int hrId, int accountId, boolean confirm) throws Exception;

    HrThirdPartyAccountDO bindMessage(int hrId, int accountId, String code) throws Exception;

    ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception;

    int delete(HrThirdPartyAccountDO thirdPartyAccountDO, UserHrAccountDO hrAccount) throws Exception;

    int updateBinding(HrThirdPartyAccountDO thirdPartyAccountDO) throws Exception;

    BindingStatus status();

}
