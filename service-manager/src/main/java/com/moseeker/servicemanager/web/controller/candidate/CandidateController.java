package com.moseeker.servicemanager.web.controller.candidate;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.candidate.service.CandidateService;
import com.moseeker.thrift.gen.candidate.struct.RecentPosition;
import com.moseeker.thrift.gen.common.struct.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangdi on 2017/8/24.
 */

@Controller
@CounterIface
public class CandidateController {

    CandidateService.Iface candidateService = ServiceManager.SERVICEMANAGER.getService(CandidateService.Iface.class);

    @RequestMapping(value = "/candidate/info", method = RequestMethod.GET)
    @ResponseBody
    public String getCandidateInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer accountId = params.getInt("account_id");
            Integer userId = params.getInt("user_id");
            Integer positionId = params.getInt("position_id");
            String message = "";
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("员工编号", accountId, null, null);
            vu.addRequiredValidate("用户编号", userId, null, null);
            vu.addRequiredValidate("职位编号", positionId, null, null);
            message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Response result = candidateService.getCandidateInfo(accountId, userId, positionId);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "api/v1/candidate/recent-view-record", method = RequestMethod.GET)
    @ResponseBody
    public String recentViewRecord(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer accountId = params.getInt("hr_id");
            Integer userId = params.getInt("user_id");
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("员工编号", accountId, null, null);
            vu.addRequiredValidate("用户编号", userId, null, null);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                RecentPosition result = candidateService.getRecentPosition(accountId, userId);
                return ResponseLogNotification.successJson(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/v1/candidate/application/referral", method = RequestMethod.POST)
    @ResponseBody
    public String addAppliationPscInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer applicationId = params.getInt("application_id");
            Integer pscId = params.getInt("psc_id");
            Integer directReferralUserId = params.getInt("direct_referral_user_id");
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("申请编号", applicationId, null, null);
            String message = vu.validate();
            if(pscId.intValue() <=0 && directReferralUserId.intValue()<=0){
                message = "传入参数有误！";
            }
            if (StringUtils.isNullOrEmpty(message)) {
                Response result = candidateService.addApplicationReferral(applicationId, pscId, directReferralUserId);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }


    @RequestMapping(value = "/v1/candidate/application/psc", method = RequestMethod.GET)
    @ResponseBody
    public String getAppliationPscInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer applicationId = params.getInt("application_id");
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("申请编号", applicationId, null, null);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Response result = candidateService.getApplicationReferral(applicationId);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/v1/candidate/position/info", method = RequestMethod.GET)
    @ResponseBody
    public String candidatePositionInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer userId = params.getInt("user_id");
            Integer companyId = params.getInt("company_id");
            Integer positionId = params.getInt("position_id");
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("候选人编号", userId);
            vu.addRequiredValidate("公司编号", companyId);
            vu.addRequiredValidate("职位编号", positionId);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Response result = candidateService.getPositionLayerInfo(userId, companyId, positionId);
                return ResponseLogNotification.successJson(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/v1/candidate/elastic/layer", method = RequestMethod.PUT)
    @ResponseBody
    public String closeElasticLayer(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer userId = params.getInt("user_id");
            Integer companyId = params.getInt("company_id");
            Integer type = params.getInt("type");
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("候选人编号", userId);
            vu.addRequiredValidate("公司编号", companyId);
            vu.addRequiredValidate("弹层类型", type);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Response result = candidateService.handerElasticLayer(userId, companyId, type);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }
}
