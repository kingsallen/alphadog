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
public class SyncTask extends ExecuteTask {
    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);

    @Override
    protected String getTaskName() {
        return "刷新";
    }

    @Override
    protected HrThirdPartyAccountDO request(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        return chaosService.synchronization(hrThirdPartyAccount, extras);
    }

    @Override
    public int getErrorCode(Exception e) {
        return 7;
    }
}
