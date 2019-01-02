package com.moseeker.useraccounts.service.impl.vo;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractBindProcessor;
import org.springframework.stereotype.Component;

/**
 * @author cjm
 * @date 2018-11-28 10:06
 **/
@Component
public class DefaultBindProcessor extends AbstractBindProcessor {
    @Override
    public HrThirdPartyAccountDO postProcessorBeforeBind(int hrId, HrThirdPartyAccountDO account) {
        return account;
    }

    @Override
    public HrThirdPartyAccountDO postProcessorAfterBind(int hrId, HrThirdPartyAccountDO account) {
        return account;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.NONE;
    }
}
