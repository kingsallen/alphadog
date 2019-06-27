package com.moseeker.servicemanager.web.controller.healthcheck;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.application.form.EmployeeProxyApplyForm;
import com.moseeker.servicemanager.web.controller.application.vo.ApplicationRecord;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.ApplicationRecordsForm;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.employee.struct.Employee;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import com.moseeker.thrift.gen.useraccounts.service.UserProviderService;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.moseeker.servicemanager.common.ParamUtils.parseRequestParam;

/**
 * 健康检查
 * <p>
 * <p>
 */
@Controller
@CounterIface
public class HealthCheckController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(HealthCheckController.class);

    UserProviderService.Iface userProviderService = ServiceManager.SERVICEMANAGER.getService(UserProviderService.Iface.class);
    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
    ProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER.getService(ProfileServices.Iface.class);
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
