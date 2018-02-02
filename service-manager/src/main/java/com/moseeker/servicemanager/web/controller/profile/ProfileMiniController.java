package com.moseeker.servicemanager.web.controller.profile;

import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zztaiwll on 18/2/1.
 */
@Controller
public class ProfileMiniController {
    WholeProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER
            .getService(WholeProfileServices.Iface.class);
    @RequestMapping(value = "/api/mini/profile/list", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            if(map==null||map.isEmpty()){
                return  ResponseLogNotification.fail(request,"参数不能为空");
            }
            if(map.get("accountId")==null|| StringUtils.isNullOrEmpty(String.valueOf(map.get("accountId")))){
                return  ResponseLogNotification.fail(request,"accountId不能为空");
            }
            Map<String,String> requestParams=new HashMap<>();
            for(String key:map.keySet()){
                requestParams.put(key,String.valueOf(map.get(key)));
            }
            Response res=profileService.getProfileMiniList(requestParams);
            return ResponseLogNotification.success(request, res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
