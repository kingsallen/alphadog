package com.moseeker.servicemanager.web.controller.referral;

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
        validateUtil.addIntTypeValidate("progress", progressForm.getProgress(), 0, Integer.MAX_VALUE);
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
            String json = referralService.getProgressBatch(progressInfo);
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    @RequestMapping(value = "v1/referral/progress/{pid}", method = RequestMethod.GET)
    public String progress(@PathVariable(name = "pid") Integer positionId,
                           @RequestParam(name = "user_id") Integer userId,
                           @RequestParam(name = "appid") Integer appid,
                           @RequestParam(name = "company_id") Integer companyId,
                           @RequestParam(name = "recom_type") Integer recomType,
                           @RequestParam(name = "presentee_user_id") Integer presenteeUserId,
                           @RequestParam(name = "progress") Integer progress) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("候选人userId", userId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("positionId", positionId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", appid, 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", companyId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("点击人userId", presenteeUserId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("推荐类型recomType", recomType, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("推荐进度progress", progress, 0, Integer.MAX_VALUE);

        validateUtil.addRequiredValidate("候选人userId", userId);
        validateUtil.addRequiredValidate("positionId", positionId);
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("companyId", companyId);
        validateUtil.addRequiredValidate("点击人userId", presenteeUserId);
        validateUtil.addRequiredValidate("推荐类型recomType", recomType);
        validateUtil.addRequiredValidate("推荐进度progress", progress);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralProgressQueryInfo queryInfo = new ReferralProgressQueryInfo();
            queryInfo.setCompanyId(companyId);
            queryInfo.setPid(positionId);
            queryInfo.setPresenteeUserId(presenteeUserId);
            queryInfo.setProgress(progress);
            queryInfo.setRecomType(recomType);
            queryInfo.setUserId(userId);
            String json = referralService.getProgressByOne(queryInfo);
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }
}
