package com.moseeker.servicemanager.web.controller.useraccounts;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.McdUatService;
import com.moseeker.thrift.gen.useraccounts.struct.McdUserTypeDO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by eddie on 2017/3/7.
 */
@Controller
@CounterIface
public class UserMcdUatEmployeeController {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    McdUatService.Iface mcduatservice = ServiceManager.SERVICEMANAGER.getService(McdUatService.Iface.class);

    @RequestMapping(value = "/mcdUser/bindUser", method = RequestMethod.POST)
    @ResponseBody
    public String getUserEmployeeByUserId(HttpServletRequest request) throws Exception {
        // 获取application实体对象

        McdUserTypeDO mcdUserTypeDO = ParamUtils.initModelForm(request, McdUserTypeDO.class);

        Response result = mcduatservice.getUserEmployeeInfoByUserType(mcdUserTypeDO);
        return ResponseLogNotification.success(request, result);
    }
}
