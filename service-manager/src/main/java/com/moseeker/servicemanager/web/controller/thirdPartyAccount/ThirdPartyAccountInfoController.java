package com.moseeker.servicemanager.web.controller.thirdPartyAccount;


import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.thirdpart.service.ThirdPartyAccountInfoService;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfo;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CounterIface
public class ThirdPartyAccountInfoController {

    private Logger logger = LoggerFactory.getLogger(ThirdPartyAccountInfoController.class);

    private ThirdPartyAccountInfoService.Iface thirdPartyAccountInfoServices = ServiceManager.SERVICEMANAGER.getService(ThirdPartyAccountInfoService.Iface.class);

    @RequestMapping(value = "/thirdPartyAccountInfo/getAllInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getAllInfo(HttpServletRequest request, HttpServletResponse response) {
        try{
            ThirdPartyAccountInfoParam param=ParamUtils.initModelForm(request, ThirdPartyAccountInfoParam.class);
            ThirdPartyAccountInfo info=thirdPartyAccountInfoServices.getAllInfo(param);
            return ResponseLogNotification.successJson(request,info);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.failJson(request, e);
        }
    }
}
