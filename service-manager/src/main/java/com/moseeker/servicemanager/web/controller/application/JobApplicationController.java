package com.moseeker.servicemanager.web.controller.application;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.application.form.EmployeeProxyApplyForm;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.application.struct.JobResumeOther;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 申请服务
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Controller
@CounterIface
public class JobApplicationController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(JobApplicationController.class);

	JobApplicationServices.Iface applicationService = ServiceManager.SERVICEMANAGER
			.getService(JobApplicationServices.Iface.class);

	/**
	 * 用户申请
	 * <p>
	 *
	 */
	@RequestMapping(value = "/application", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取application实体对象
			JobApplication jobApplication = ParamUtils.initModelForm(request, JobApplication.class);
			logger.info("JobApplicationController jobApplication:{}", jobApplication);
			// 创建申请记录
			Response result = applicationService.postApplication(jobApplication);
			logger.info("JobApplicationController result:{}", result);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	/**
	 * 更新用户申请
	 * <p>
	 *
	 */
	@RequestMapping(value = "/application", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取application实体对象
			JobApplication jobApplication = ParamUtils.initModelForm(request, JobApplication.class);

			// 创建申请记录
			Response result = applicationService.putApplication(jobApplication);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	/**
	 * 删除用户申请
	 * <p>
	 *
	 */
	@RequestMapping(value = "/application/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 删除用户申请
			Response result = applicationService.deleteApplication(id);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	/**
	 * 申请副本添加
	 * <p>
	 *
	 */
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
	 */
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
	 */
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
	 */
	@RequestMapping(value = "/application/count/check", method = RequestMethod.POST)
	@ResponseBody
	public String validateUserApplicationCheckCountAtCompany(HttpServletRequest request, HttpServletResponse response) {
		try {

			Map<String, Object> paramMap = ParamUtils.parseRequestParam(request);

			long userId = Long.valueOf(paramMap.get("user_id").toString());
			long companyId = Long.valueOf(paramMap.get("company_id").toString());
			long poisiotnId = Long.valueOf(paramMap.get("position_id").toString());
			logger.info("JobApplicationController userId:{}, companyId:{}", userId, companyId);
			// 创建申请记录
			Response result = applicationService.validateUserApplicationCheckCountAtCompany(userId, companyId, poisiotnId);
			logger.info("JobApplicationController result:{}", result);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}



    @RequestMapping(value = "/application/type/count/check", method = RequestMethod.POST)
    @ResponseBody
    public String validateUserApplicationTypeCheckCountAtCompany(HttpServletRequest request, HttpServletResponse response) {
        try {

            Map<String, Object> paramMap = ParamUtils.parseRequestParam(request);

            long userId = Long.valueOf(paramMap.get("user_id").toString());
            long companyId = Long.valueOf(paramMap.get("company_id").toString());
            logger.info("JobApplicationController userId:{}, companyId:{}", userId, companyId);
            // 创建申请记录
            Response result = applicationService.validateUserApplicationTypeCheckCountAtCompany(userId, companyId);
            logger.info("JobApplicationController result:{}", result);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
	/**
	 * 招聘进度同步到第三方
	 */
	@RequestMapping(value = "/application/thirdpartyapplication", method = RequestMethod.GET)
	@ResponseBody
	public String getPositionForThirdParty(HttpServletRequest request, HttpServletResponse response) {
		try {
			Params<String, Object> params = ParamUtils.parseRequestParam(request);
			Integer channel = params.getInt("channel");
			String start_time = params.getString("start_time");
			String end_time = params.getString("end_time");
			Response res =  applicationService.getApplicationListForThirdParty(channel, start_time, end_time);
			return ResponseLogNotification.success(request, res);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseLogNotification.fail(request,e.getMessage());
		}
	}

	/**
	 * HR查看申请
	 */
	@RequestMapping(value = "/v1/applications/view", method = RequestMethod.POST)
	@ResponseBody
	public String viewApplications(HttpServletRequest request) {
		try {
			Params<String, Object> params = ParamUtils.parseRequestParam(request);
			List<Integer> applicationIds = (List<Integer>)params.get("application_ids");
			int hrId = params.getInt("hr_id");
			applicationService.viewApplications(hrId, applicationIds);
			return ResponseLogNotification.successJson(request, "success");
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return ResponseLogNotification.fail(request,e.getMessage());
		}
	}

    /**
	 * 获取HR有多少未读简历
	 */
	@RequestMapping(value = "/application/isView/count", method = RequestMethod.GET)
	@ResponseBody
	public String getAppliationIsViewCount(HttpServletRequest request, HttpServletResponse response) {
		try {
			Params<String, Object> params = ParamUtils.parseRequestParam(request);
			Integer accountId = params.getInt("accountId");
			Response res =  applicationService.getHrIsViewApplication(accountId);
			return ResponseLogNotification.success(request, res);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseLogNotification.fail(request,e.getMessage());
		}
	}

	/**
	 * 员工主动上传
	 */
	@RequestMapping(value = "/v1/application/proxy", method = RequestMethod.POST)
	@ResponseBody
	public String employeeProxyApply(@RequestBody EmployeeProxyApplyForm form) throws Exception {
		if (form.getAppid() <= 0) {
			throw CommonException.PROGRAM_APPID_LOST;

		}
		ValidateUtil validateUtil = new ValidateUtil();
		validateUtil.addRequiredOneValidate("职位", form.getPositionIds());
		validateUtil.addRequiredValidate("求职者", form.getApplierId());
		validateUtil.addRequiredValidate("推荐者", form.getReferenceId());
		validateUtil.addUpperLimitValidate("职位", form.getPositionIds());
		String result = validateUtil.validate();
		if (StringUtils.isNotBlank(result)) {
			throw CommonException.validateFailed(result);
		} else {
			List<Integer> applyIdList = applicationService.employeeProxyApply(form.getReferenceId(), form.getApplierId(), form.getPositionIds());
			return Result.success(applyIdList).toJson();
		}
	}
}
