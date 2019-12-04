package com.moseeker.servicemanager.web.controller.mall;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.mall.service.MallService;
import com.moseeker.thrift.gen.mall.struct.BaseMallForm;
import com.moseeker.thrift.gen.mall.struct.MallRuleForm;
import com.moseeker.thrift.gen.mall.struct.MallSwitchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 商城管理controller
 *
 * @author cjm
 * @date 2018-10-12 10:21
 **/
@Controller
@CounterIface
@RequestMapping("api/mall/manage")
public class MallController {

    private Logger logger = LoggerFactory.getLogger(MallController.class);

    MallService.Iface mallService = ServiceManager.SERVICE_MANAGER.getService(MallService.Iface.class);

    /**
     * 获取公司是否开通过积分商城
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/switch", method = RequestMethod.GET)
    @ResponseBody
    public String getMallSwitch(HttpServletRequest request) {
        try {
            BaseMallForm baseMallForm = ParamUtils.initModelForm(request, BaseMallForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("company_id", baseMallForm.getCompany_id());
            vu.addIntTypeValidate("company_id", baseMallForm.getCompany_id(), null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                int state = mallService.getMallSwitch(baseMallForm);
                Map<String, Integer> resultMap = new HashMap<>(1 >> 4);
                resultMap.put("state", state);
                return ResponseLogNotification.successJson(request, resultMap);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 开通或关闭商城功能
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/switch", method = RequestMethod.PUT)
    @ResponseBody
    public String openOrCloseMall(HttpServletRequest request) {
        try {
            MallSwitchForm mallSwitchForm = ParamUtils.initModelForm(request, MallSwitchForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("商城状态", mallSwitchForm.getState());
            vu.addRequiredValidate("company_id", mallSwitchForm.getCompany_id());
            vu.addRequiredValidate("hr_id", mallSwitchForm.getHr_id());
            vu.addIntTypeValidate("商城状态", (int)mallSwitchForm.getState(), null, null, 1, 3);
            vu.addIntTypeValidate("company_id", mallSwitchForm.getCompany_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("hr_id", mallSwitchForm.getHr_id(), null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                mallService.openOrCloseMall(mallSwitchForm);
                Map<String, Integer> resultMap = new HashMap<>(1 >> 4);
                resultMap.put("state", (int)mallSwitchForm.getState());
                return ResponseLogNotification.successJson(request, resultMap);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 获取默认领取规则
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/rule", method = RequestMethod.GET)
    @ResponseBody
    public String getDefaultRule(HttpServletRequest request) {
        try {
            BaseMallForm baseMallForm = ParamUtils.initModelForm(request, BaseMallForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("company_id", baseMallForm.getCompany_id());
            vu.addRequiredValidate("hr_id", baseMallForm.getHr_id());
            vu.addIntTypeValidate("company_id", baseMallForm.getCompany_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("hr_id", baseMallForm.getHr_id(), null, null, 1, Integer.MAX_VALUE);
            String rule = mallService.getDefaultRule(baseMallForm);
            Map<String, String> resultMap = new HashMap<>(1 >> 4);
            resultMap.put("rule", rule);
            return ResponseLogNotification.successJson(request, resultMap);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 更新默认领取规则
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/rule", method = RequestMethod.PUT)
    @ResponseBody
    public String updateDefaultRule(HttpServletRequest request) {
        try {
            MallRuleForm mallRuleForm = ParamUtils.initModelForm(request, MallRuleForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("company_id", mallRuleForm.getCompany_id());
            vu.addRequiredValidate("hr_id", mallRuleForm.getHr_id());
            vu.addRequiredValidate("默认领取规则", mallRuleForm.getRule());
            vu.addRequiredValidate("默认领取规则状态", mallRuleForm.getState());
            vu.addIntTypeValidate("company_id", mallRuleForm.getCompany_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("hr_id", mallRuleForm.getHr_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addStringLengthValidate("默认领取规则", mallRuleForm.getRule(), null, null, 0, 200);
            vu.addIntTypeValidate("默认领取规则状态", (int)mallRuleForm.getState(), null, null, 0, 2);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                mallService.updateDefaultRule(mallRuleForm);
                return ResponseLogNotification.successJson(request, null);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }
}
