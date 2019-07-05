package com.moseeker.servicemanager.web.controller.profile;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProjectExpServices;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@RestController
@CounterIface
public class ProjectExpController {

    Logger logger = LoggerFactory.getLogger(ProjectExpController.class);

    ProjectExpServices.Iface projectExpService = ServiceManager.SERVICE_MANAGER
            .getService(ProjectExpServices.Iface.class);

    @RequestMapping(value = "/profile/projectexp", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
        Response result = projectExpService.getResources(query);
        return ResponseLogNotification.success(request, result);
    }

    @RequestMapping(value = "/profile/projectexp", method = RequestMethod.POST)
    @ResponseBody
    public String post(HttpServletRequest request, HttpServletResponse response) {
        // PrintWriter writer = null;
        try {
            ProjectExp projectExp = ParamUtils.initModelForm(request, ProjectExp.class);
            Response result = projectExpService.postResource(projectExp);

            return ResponseLogNotification.success(request, result);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/profile/projectexp", method = RequestMethod.PUT)
    @ResponseBody
    public String put(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProjectExp profileimport = ParamUtils.initModelForm(request, ProjectExp.class);
            Response result = projectExpService.putResource(profileimport);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/profile/projectexp", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            ProjectExp profileimport = ParamUtils.initModelForm(request, ProjectExp.class);
            Response result = projectExpService.delResource(profileimport);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
