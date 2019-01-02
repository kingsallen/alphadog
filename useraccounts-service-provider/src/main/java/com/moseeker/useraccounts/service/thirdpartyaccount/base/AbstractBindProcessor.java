package com.moseeker.useraccounts.service.thirdpartyaccount.base;

import com.moseeker.common.iface.IChannelType;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;

/**
 * @author cjm
 * @date 2018-11-28 9:55
 **/
public abstract class AbstractBindProcessor implements IChannelType {

    /**
     * 绑定前各渠道的特殊处理
     * @param hrId hrId
     * @param account 第三方账号信息
     * @author  cjm
     * @date  2018/11/28
     * @return HrThirdPartyAccountDO
     */
    public abstract HrThirdPartyAccountDO postProcessorBeforeBind(int hrId, HrThirdPartyAccountDO account) throws Exception;

    /**
     * 绑定后各渠道的特殊处理
     * @param hrId hrId
     * @param account 第三方账号信息
     * @author  cjm
     * @date  2018/11/28
     * @return HrThirdPartyAccountDO
     */
    public abstract HrThirdPartyAccountDO postProcessorAfterBind(int hrId, HrThirdPartyAccountDO account) throws Exception;

}
