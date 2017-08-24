package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhangdi on 2017/8/11.
 */
@Service
public class BindTask extends ExecuteTask {
    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);

    @Override
    protected String getTaskName() {
        return "绑定";
    }

    @Override
    protected HrThirdPartyAccountDO request(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        return chaosService.binding(hrThirdPartyAccount, extras);
    }

    @Override
    public int getErrorCode(Exception e) {
        return 6;
    }

    protected HrThirdPartyAccountDO bindConfirm(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras, boolean confirm) throws Exception {
        return chaosService.bindConfirm(hrThirdPartyAccount, extras, confirm);
    }

    protected HrThirdPartyAccountDO bindMessage(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras, String code) throws Exception {
        return chaosService.bindMessage(hrThirdPartyAccount, extras, code);
    }
}
