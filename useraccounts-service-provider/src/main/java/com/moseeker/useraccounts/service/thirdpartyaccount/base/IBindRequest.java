package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.common.iface.IChannelType;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;

import java.util.Map;

public interface IBindRequest extends IChannelType{

    /**
     * 发送绑定请求，默认是ChaosHandler，即爬虫
     * @param hrThirdPartyAccount   账号数据
     * @param extras    额外参数
     * @return  绑定完成后的结果
     * @throws Exception
     */
    HrThirdPartyAccountDO bind(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception;
}
