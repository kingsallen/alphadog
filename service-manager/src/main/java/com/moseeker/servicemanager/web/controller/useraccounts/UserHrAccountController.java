package com.moseeker.servicemanager.web.controller.useraccounts;

import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HR账号服务
 * <p>
 *
 * Created by zzh on 16/6/1.
 */
@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class UserHrAccountController {

    Logger logger = LoggerFactory.getLogger(UseraccountsController.class);

    UserHrAccountService.Iface userHrAccountService = ServiceUtil.getService(UserHrAccountService.Iface.class);

    /**
     * 添加HR账号
     * <p>
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount", method = RequestMethod.POST)
    @ResponseBody
    public String postUserHrAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取HR用户实体对象
            UserHrAccount userHrAccount = ParamUtils.initModelForm(request, UserHrAccount.class);

            Response result = userHrAccountService.postResource(userHrAccount);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
