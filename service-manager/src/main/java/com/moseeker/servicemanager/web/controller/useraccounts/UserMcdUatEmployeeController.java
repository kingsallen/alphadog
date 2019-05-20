package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.useraccounts.service.McdUatService;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import org.slf4j.LoggerFactory;
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
public class UserMcdUatEmployeeController {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    McdUatService.Iface mcdService = ServiceManager.SERVICEMANAGER.getService(McdUatService.Iface.class);


    private SerializeConfig serializeConfig = new SerializeConfig();

    public UserMcdUatEmployeeController(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }


    @RequestMapping(value="/mcdUat/user", method = RequestMethod.GET)
    @ResponseBody
    public String getUserEmployeeByUserId(HttpServletRequest request) {
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId=  Integer.parseInt(params.getString("sysuser_id"));
            Response res=mcdService.getUserEmployeeByuserId(userId);
            return ResponseLogNotification.successJson(request, res);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request,e.getMessage());
        }
    }


}
