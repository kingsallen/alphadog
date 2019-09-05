package com.moseeker.servicemanager.web.controller.thirdPartyAccount;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.thirdpart.service.ThirdPartyAccountInfoService;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfo;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyAccountInfoParam;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartyCommonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@CounterIface
public class ThirdPartyAccountInfoController {

    Logger logger = LoggerFactory.getLogger(ThirdPartyAccountInfoController.class);

    private ThirdPartyAccountInfoService.Iface thirdPartyAccountInfoServices = ServiceManager.SERVICE_MANAGER.getService(ThirdPartyAccountInfoService.Iface.class);

    @RequestMapping(value = "/thirdPartyAccountInfo/getAllInfo", method = RequestMethod.GET)
    @ResponseBody
    public String getAllInfo(HttpServletRequest request, HttpServletResponse response) {
        try{
            logger.info("getAllInfo start===============");
            ThirdPartyAccountInfoParam param=ParamUtils.initModelForm(request, ThirdPartyAccountInfoParam.class);
            logger.info("getAllInfo start param channel:{},hrId:{}",param.channel,param.hrId);
            ThirdPartyAccountInfo info=thirdPartyAccountInfoServices.getAllInfo(param);
            logger.info("getAllInfo data:{}",info);
            return ResponseLogNotification.successJson(request, JSONObject.parseObject(info.getJson()));
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/thirdParty/common/info", method = RequestMethod.POST)
    @ResponseBody
    public String postThirdPartyCommonInfo(HttpServletRequest request, HttpServletResponse response) {
        try{
            logger.info("putThirdPartyCommonInfo start===============");
            String strParam=ParamUtils.parseJsonParam(request);
            logger.info("putThirdPartyCommonInfo start param:{}",strParam);
            ThirdPartyCommonInfo param = JSON.parseObject(strParam).toJavaObject(ThirdPartyCommonInfo.class);
            int result=thirdPartyAccountInfoServices.postThirdPartyCommonInfo(param);
            logger.info("putThirdPartyCommonInfo param:{} result:{}",strParam,result);
            return ResponseLogNotification.successJson(request, result);
        }catch (Exception e){
            logger.error("putThirdPartyCommonInfo error"+e.getMessage(), e);
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/thirdParty/common/info", method = RequestMethod.GET)
    @ResponseBody
    public String getThirdPartyCommonInfo(HttpServletRequest request, HttpServletResponse response) {
        try{
            logger.info("getThirdPartyCommonInfo start===============");
            ThirdPartyCommonInfo param = ParamUtils.initModelForm(request,ThirdPartyCommonInfo.class);
            logger.info("getThirdPartyCommonInfo start param:{}",JSON.toJSONString(param));
            List<ThirdPartyCommonInfo> result=thirdPartyAccountInfoServices.getThirdPartyCommonInfo(param);
            logger.info("getThirdPartyCommonInfo param:{} result:{}",JSON.toJSONString(param),JSON.toJSONString(result));
            return ResponseLogNotification.successJson(request, result);
        }catch (Exception e){
            logger.error("putThirdPartyCommonInfo error"+e.getMessage(), e);
            return ResponseLogNotification.failJson(request, e);
        }
    }
}
