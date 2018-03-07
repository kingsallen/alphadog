package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.common.constants.BindingStatus;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;

import java.util.List;
import java.util.Map;

public interface BindState {
    HrThirdPartyAccountDO bind(int hrId,HrThirdPartyAccountDO thirdPartyAccountDO) throws Exception;

    HrThirdPartyAccountDO bindConfirm(int hrId, int accountId, boolean confirm) throws Exception;

    HrThirdPartyAccountDO bindMessage(int hrId, int accountId, String code) throws Exception;

    HrThirdPartyAccountDO bindChaosHandle(HrThirdPartyAccountDO thirdPartyAccountDO) throws Exception;

    ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception;

    int delete(HrThirdPartyAccountDO thirdPartyAccountDO) throws Exception;

    int updateBinding(HrThirdPartyAccountDO thirdPartyAccountDO) throws Exception;

    BindingStatus status();

}
