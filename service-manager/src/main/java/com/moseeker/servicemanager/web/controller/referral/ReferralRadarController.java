package com.moseeker.servicemanager.web.controller.referral;

import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.form.ProgressForm;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class ReferralRadarController {

    private ReferralService.Iface referralService =  ServiceManager.SERVICEMANAGER.getService(ReferralService.Iface.class);

    @RequestMapping(value = "v1/referral/progress", method = RequestMethod.POST)
    public String progress(@RequestBody ProgressForm progressForm){
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", tempForm.getUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", tempForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", tempForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", tempForm.getUserId());
        validateUtil.addRequiredValidate("appid", tempForm.getAppid());
        validateUtil.addRequiredValidate("companyId", tempForm.getCompanyId());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            referralService.getProgressBatch(cardInfo);
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    @RequestMapping(value = "v1/referral/progress/{pid}", method = RequestMethod.GET)
    public String progress(@PathVariable Integer pid, HttpRequest request){
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", tempForm.getUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", tempForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", tempForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", tempForm.getUserId());
        validateUtil.addRequiredValidate("appid", tempForm.getAppid());
        validateUtil.addRequiredValidate("companyId", tempForm.getCompanyId());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            referralService.getProgressByOne(cardInfo);
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }
}
