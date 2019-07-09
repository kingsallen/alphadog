package com.moseeker.servicemanager.web.controller.healthcheck;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import com.moseeker.thrift.gen.useraccounts.service.UserProviderService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 健康检查
 * <p>
 * <p>
 */
@Controller
@CounterIface
public class HealthCheckController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(HealthCheckController.class);

    UserProviderService.Iface userProviderService = ServiceManager.SERVICE_MANAGER.getService(UserProviderService.Iface.class);
    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICE_MANAGER.getService(SearchengineServices.Iface.class);
    ProfileServices.Iface profileService = ServiceManager.SERVICE_MANAGER.getService(ProfileServices.Iface.class);
    /**
     * service-manager 健康检查
     * <p>
     */
    @RequestMapping(value = "/servicemanager/health_check")
    @ResponseBody
    public String check(HttpServletRequest request, HttpServletResponse response) {
        try {
            return ResponseLogNotification.successJson(request, true);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/searchengine/health_check")
    @ResponseBody
    public String searchengineCheck(HttpServletRequest request, HttpServletResponse response) {
        try {
            return ResponseLogNotification.successJson(request, searchengineServices.healthCheck());
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/useraccounts/health_check")
    @ResponseBody
    public String useraccountsCheck(HttpServletRequest request, HttpServletResponse response) {
        try {
            return ResponseLogNotification.successJson(request,userProviderService.healthCheck() );
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/profile/health_check")
    @ResponseBody
    public String profileCheck(HttpServletRequest request, HttpServletResponse response) {
        try {
            return ResponseLogNotification.successJson(request,profileService.healthCheck() );
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}
