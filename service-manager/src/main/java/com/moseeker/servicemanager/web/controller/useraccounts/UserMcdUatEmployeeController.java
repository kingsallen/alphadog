package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.McdUatService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by eddie on 2017/3/7.
 */
@Controller
@CounterIface
public class UserMcdUatEmployeeController {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    McdUatService.Iface mcduatservice = ServiceManager.SERVICEMANAGER.getService(McdUatService.Iface.class);


    private SerializeConfig serializeConfig = new SerializeConfig();

    public UserMcdUatEmployeeController(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @RequestMapping(value = "/mcdUser/bindUser", method = RequestMethod.GET)
    @ResponseBody
   /* public String getUserEmployeeByUserId(@RequestParam("appid") int appid,
        @RequestParam("sysuser_id") int sysuserId) throws Exception {*/
    public String getUserEmployeeByUserId(@RequestParam("sysuser_id") int sysuserId) throws Exception {
        try{
            Response userInfo = mcduatservice.getUserEmployeeByuserId(sysuserId);
            logger.info("getUserEmployeeByUserId userInfo:{}", userInfo);
            return Result.success(userInfo).toJson();
           }catch(Exception e){
            logger.error("getUserEmployeeByUserId 接口异常"+e.getMessage(),e);
            return String.valueOf(Result.validateFailed(e.getMessage()));
        }
    }


}
