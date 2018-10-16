package com.moseeker.servicemanager.web.controller.mall;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.mall.service.MallService;
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

    MallService.Iface mallService = ServiceManager.SERVICEMANAGER.getService(MallService.Iface.class);

    /**
     * 获取公司是否开通过积分商城
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/switch", method = RequestMethod.GET)
    @ResponseBody
    public String getMallSwitch(HttpServletRequest request) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            Integer companyId = Integer.parseInt(String.valueOf(map.get("company_id")));
            int state = mallService.getMallSwitch(companyId);
            Map<String, Integer> resultMap = new HashMap<>(1 >> 4);
            resultMap.put("state", state);
            return ResponseLogNotification.successJson(request, resultMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
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

            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("商城状态", map.get("state"));
            vu.addIntTypeValidate("商城状态", map.get("state"), null, null, 1, 3);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Integer companyId = Integer.parseInt(String.valueOf(map.get("company_id")));
                Integer state = Integer.parseInt(String.valueOf(map.get("state")));
                mallService.openOrCloseMall(companyId, state);
                Map<String, Integer> resultMap = new HashMap<>(1 >> 4);
                resultMap.put("state", state);
                return ResponseLogNotification.successJson(request, resultMap);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
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
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            Integer companyId = Integer.parseInt(String.valueOf(map.get("company_id")));
            String rule = mallService.getDefaultRule(companyId);
            Map<String, String> resultMap = new HashMap<>(1 >> 4);
            resultMap.put("rule", rule);
            return ResponseLogNotification.successJson(request, resultMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
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
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("默认领取规则", map.get("rule"));
            vu.addRequiredValidate("默认领取状态", map.get("state"));
            vu.addIntTypeValidate("默认领取规则", map.get("state"), null, null, 1, 2);
            vu.addStringLengthValidate("默认领取状态", map.get("rule"), null, null, 0, 200);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Integer companyId = Integer.parseInt(String.valueOf(map.get("company_id")));
                Integer state = Integer.parseInt(String.valueOf(map.get("state")));
                String rule = String.valueOf(map.get("rule"));
                mallService.updateDefaultRule(companyId, state, rule);
                return ResponseLogNotification.successJson(request, null);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
