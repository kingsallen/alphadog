package com.moseeker.servicemanager.web.controller.thirdPartyAccount;


import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CounterIface
public class ThirdPartyAccountInfoController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String getAllInfo(HttpServletRequest request, HttpServletResponse response) {
        try{
            ThirdPartyAccountInfoParam param=ParamUtils.initModelForm(request, ThirdPartyAccountInfoParam.class);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }
}
