package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices;
import org.springframework.stereotype.Service;

/**
 * Created by zhangdi on 2017/6/16.
 */

@Service
public class ThirdPartyAccountSynctor {

    ChaosServices.Iface chaosService = ServiceManager.SERVICEMANAGER.getService(ChaosServices.Iface.class);

    public HrThirdPartyAccountDO syncThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount, boolean async) {
        return async ? asyncWithSyncThirdPartyAccount(hrId, thirdPartyAccount) : syncWithSyncThirdPartyAccount(hrId, thirdPartyAccount);
    }

    /**
     * 使用异步的方式去同步第三方账号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO asyncWithSyncThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount) {

    }


    /**
     * 使用同步的方式同步第三方账号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO syncWithSyncThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount) {
        chaosService.binding(thirdPartyAccount);
    }

}
