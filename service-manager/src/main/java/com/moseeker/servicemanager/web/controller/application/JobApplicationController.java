package com.moseeker.servicemanager.web.controller.application;

import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 申请服务
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class JobApplicationController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(JobApplicationController.class);

    JobApplicationServices.Iface applicationService = ServiceUtil.getService(JobApplicationServices.Iface.class);

    /**
     * 用户申请
     * <p>
     *
     * */
    @RequestMapping(value = "/application", method = RequestMethod.POST)
    @ResponseBody
    public String post(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取application实体对象
            JobApplication jobApplication = ParamUtils.initModelForm(request, JobApplication.class);

            // 创建申请记录
            Response result = applicationService.postApplication(jobApplication);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 申请副本添加
     * <p>
     *
     * */
    @RequestMapping(value = "/application/other", method = RequestMethod.POST)
    @ResponseBody
    public String postJobResumeOther(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取application实体对象
            JobResumeOther jobResumeOther = ParamUtils.initModelForm(request, JobResumeOther.class);

            // 创建申请记录
            Response result = applicationService.postJobResumeOther(jobResumeOther);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 用户申请职位申请校验
     * <p>
     *
     * */
    @RequestMapping(value = "/application/check", method = RequestMethod.GET)
    @ResponseBody
    public String getApplicationByUserIdAndPositionId(HttpServletRequest request, HttpServletResponse response) {
        try {
            long positionId = Long.valueOf(request.getParameter("position_id"));
            long userId = Long.valueOf(request.getParameter("user_id"));
            long companyId = Long.valueOf(request.getParameter("company_id"));

            // 创建申请记录
            Response result = applicationService.getApplicationByUserIdAndPositionId(userId, positionId, companyId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 清除一个公司一个人申请次数限制的redis key 给sysplat用
     * <p>
     *
     * */
    @RequestMapping(value = "/application/clear", method = RequestMethod.POST)
    @ResponseBody
    public String deleteRedisKeyApplicationCheckCount(HttpServletRequest request, HttpServletResponse response) {
        try {
            long userId = Long.valueOf(request.getParameter("user_id"));
            long companyId = Long.valueOf(request.getParameter("company_id"));

            // 创建申请记录
            Response result = applicationService.deleteRedisKeyApplicationCheckCount(userId, companyId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 校验超出申请次数限制, 每月每家公司一个人只能申请3次
     * <p>
     *
     * */
    @RequestMapping(value = "/application/count/check", method = RequestMethod.POST)
    @ResponseBody
    public String validateUserApplicationCheckCountAtCompany(HttpServletRequest request, HttpServletResponse response) {
        try {
            long userId = Long.valueOf(request.getParameter("user_id"));
            long companyId = Long.valueOf(request.getParameter("company_id"));

            // 创建申请记录
            Response result = applicationService.validateUserApplicationCheckCountAtCompany(userId, companyId);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
