package com.moseeker.servicemanager.service.impl;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.service.HRService;
import com.moseeker.servicemanager.service.vo.HRInfo;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: jack
 * @Date: 2018/8/10
 */
@Service
public class HRServiceImpl implements HRService {

    UserHrAccountService.Iface userHrAccountService = ServiceManager.SERVICE_MANAGER
            .getService(UserHrAccountService.Iface.class);

    public HRInfo getHR(int id) throws Exception {
        com.moseeker.thrift.gen.useraccounts.struct.HRInfo hrInfoStruct = userHrAccountService.getHR(id);
        HRInfo hrInfo = new HRInfo();
        BeanUtils.copyProperties(hrInfoStruct, hrInfo);
        return hrInfo;
    }
}
