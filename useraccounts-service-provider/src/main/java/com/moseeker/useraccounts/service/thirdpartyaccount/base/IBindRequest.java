package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.common.iface.IChannelType;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;

import java.util.Map;

public interface IBindRequest extends IChannelType{

    HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception;
}
