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
     * 注册HR发送验证码
     *
     * */
    @RequestMapping(value = "/hraccount/sendsignupcode", method = RequestMethod.POST)
    @ResponseBody
    public String sendMobileVerifiyCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取HR用户实体对象
            String mobile = request.getParameter("mobile");
            String code = request.getParameter("code");
            int source = Integer.valueOf(request.getParameter("source"));

            Response result = userHrAccountService.sendMobileVerifiyCode(mobile, code, source);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


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
            // 验证码
            String code = request.getParameter("code");

            // 获取HR用户实体对象
            UserHrAccount userHrAccount = ParamUtils.initModelForm(request, UserHrAccount.class);

            Response result = userHrAccountService.postResource(userHrAccount, code);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
