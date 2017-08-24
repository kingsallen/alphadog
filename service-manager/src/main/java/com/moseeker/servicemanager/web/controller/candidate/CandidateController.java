package com.moseeker.servicemanager.web.controller.candidate;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.candidate.service.CandidateService;
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
}
