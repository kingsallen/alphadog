package com.moseeker.servicemanager.web.controller.useraccounts;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.ThirdPartyUserService;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by eddie on 2017/3/7.
 */
@Controller
@CounterIface
public class UserThirdPartyController {

    ThirdPartyUserService.Iface service = ServiceManager.SERVICEMANAGER.getService(ThirdPartyUserService.Iface.class);

    @RequestMapping(value = "/user/thirdparty", method = RequestMethod.POST)
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response) {
        try {
            ThirdPartyUser user = ParamUtils.initModelForm(request, ThirdPartyUser.class);
            if(!user.isSetId()) {
                if (!user.isSetUser_id()) {
                    return ResponseLogNotification.fail(request, "user_id不能为空");
                } else if (!user.isSetSource_id()) {
                    return ResponseLogNotification.fail(request, "source_id不能为空");
                }
            }
            Response result = service.updateUser(user);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
