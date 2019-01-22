package com.moseeker.servicemanager.web.controller.referral;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.form.ProgressForm;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.referral.struct.ReferralProgressInfo;
import com.moseeker.thrift.gen.referral.struct.ReferralProgressQueryInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
public class ReferralRadarController {

    private ReferralService.Iface referralService =  ServiceManager.SERVICEMANAGER.getService(ReferralService.Iface.class);

    @RequestMapping(value = "v1/referral/progress", method = RequestMethod.POST)
    public String progress(@RequestBody ProgressForm progressForm) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", progressForm.getUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", progressForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("progress", progressForm.getProgress(), -1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", progressForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", progressForm.getUserId());
        validateUtil.addRequiredValidate("progress", progressForm.getProgress());
        validateUtil.addRequiredValidate("appid", progressForm.getAppid());
        validateUtil.addRequiredValidate("companyId", progressForm.getCompanyId());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            if(progressForm.getPageNum() == null || progressForm.getPageNum() <= 0){
                progressForm.setPageNum(1);
            }
            if(progressForm.getPageSize() == null || progressForm.getPageSize() <= 0){
                progressForm.setPageSize(10);
            }
            ReferralProgressInfo progressInfo = new ReferralProgressInfo();
            BeanUtils.copyProperties(progressForm, progressInfo);
            String resultJson = referralService.getProgressBatch(progressInfo);
            JSONArray response = JSONArray.parseArray(resultJson);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    @RequestMapping(value = "v1/referral/progress/{applyId}", method = RequestMethod.GET)
    public String progress(@PathVariable(name = "applyId") Integer applyId,
                           @RequestParam(name = "user_id") Integer userId,
                           @RequestParam(name = "appid") Integer appid,
                           @RequestParam(name = "company_id") Integer companyId,
                           @RequestParam(name = "presentee_user_id") Integer presenteeUserId,
                           @RequestParam(name = "progress") Integer progress) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("候选人userId", userId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", appid, 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", companyId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("点击人userId", presenteeUserId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("推荐进度progress", progress, 0, Integer.MAX_VALUE);

        validateUtil.addRequiredValidate("候选人userId", userId);
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("companyId", companyId);
        validateUtil.addRequiredValidate("点击人userId", presenteeUserId);
        validateUtil.addRequiredValidate("推荐进度progress", progress);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralProgressQueryInfo queryInfo = new ReferralProgressQueryInfo();
            queryInfo.setCompanyId(companyId);
            queryInfo.setApplyId(applyId);
            queryInfo.setPresenteeUserId(presenteeUserId);
            queryInfo.setProgress(progress);
            queryInfo.setUserId(userId);
            String responseStr = referralService.getProgressByOne(queryInfo);
            JSONObject response = JSONObject.parseObject(responseStr);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    @RequestMapping(value = "v1/referral/progress/keyword", method = RequestMethod.GET)
    public String progressQueryKeyword(@RequestParam(name = "user_id") Integer userId,
                                       @RequestParam(name = "appid") Integer appid,
                                       @RequestParam(name = "company_id") Integer companyId,
                                       @RequestParam(name = "keyword") String keyword,
                                       @RequestParam(name = "progress") Integer progress) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", userId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", appid, 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("progress", progress, 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", companyId, 1, Integer.MAX_VALUE);
        validateUtil.addStringLengthValidate("keyword", keyword.trim(), 1, 50);
        validateUtil.addRequiredValidate("员工userId", userId);
        validateUtil.addRequiredValidate("progress", progress);
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("companyId", companyId);
        validateUtil.addRequiredValidate("keyword", keyword);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralProgressInfo progressInfo = new ReferralProgressInfo();
            progressInfo.setCompanyId(companyId);
            progressInfo.setKeyword(keyword);
            progressInfo.setProgress(progress);
            progressInfo.setUserId(userId);
            String resultJson = referralService.progressQueryKeyword(progressInfo);
            JSONArray response = JSONArray.parseArray(resultJson);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    @RequestMapping(value = "v1/referral/seek/check", method = RequestMethod.GET)
    public String checkSeekReferral(@RequestParam(name = "recom_user_id") Integer userId,
                                       @RequestParam(name = "appid") Integer appid,
                                       @RequestParam(name = "company_id") Integer companyId,
                                       @RequestParam(name = "presentee_user_id") Integer presenteeId,
                                       @RequestParam(name = "position_id") Integer positionId,
                                       @RequestParam(name = "psc") Integer parentChainId) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", userId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", appid, 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", companyId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("presentee_user_id", presenteeId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("position_id", positionId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("psc", parentChainId, -1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", userId);
        validateUtil.addRequiredValidate("presentee_user_id", presenteeId);
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("companyId", companyId);
        validateUtil.addRequiredValidate("position_id", positionId);
        validateUtil.addRequiredValidate("psc", parentChainId);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            int referralId = referralService.checkSeekReferral(userId, presenteeId, positionId, companyId, parentChainId);
            JSONObject response = new JSONObject();
            response.put("referral_id", referralId);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }
}
