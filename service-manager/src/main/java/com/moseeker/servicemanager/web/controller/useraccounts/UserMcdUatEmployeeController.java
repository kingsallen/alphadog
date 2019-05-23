package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.useraccounts.vo.McdUserType;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.McdUatService;
import com.moseeker.thrift.gen.useraccounts.struct.McdUserTypeDO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    McdUatService.Iface mcduatservice = ServiceManager.SERVICEMANAGER.getService(McdUatService.Iface.class);


    private SerializeConfig serializeConfig = new SerializeConfig();

    public UserMcdUatEmployeeController() {
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @RequestMapping(value = "/mcdUser/bindUser", method = RequestMethod.POST)
    @ResponseBody

    public String getUserEmployeeByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
            // 获取application实体对象


            McdUserTypeDO mcdUserTypeDO = ParamUtils.initModelForm(request, McdUserTypeDO.class);
            // 创建申请记录

            logger.info("请求参数---------》"+request.toString());

            logger.info("接口数据传入----------"+ mcdUserTypeDO.getCompany_id());



            Response result = mcduatservice.getUserEmployeeInfoByUserType(mcdUserTypeDO);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }


    }
}
