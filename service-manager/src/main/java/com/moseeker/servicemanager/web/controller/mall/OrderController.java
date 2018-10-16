package com.moseeker.servicemanager.web.controller.mall;

import com.alibaba.fastjson.JSONArray;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.mall.service.OrderService;
import com.moseeker.thrift.gen.mall.struct.OrderForm;
import com.moseeker.thrift.gen.mall.struct.OrderSearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 订单controller
 *
 * @author cjm
 * @date 2018-10-12 10:43
 **/
@Controller
@CounterIface
@RequestMapping("api/mall")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(MallController.class);

    OrderService.Iface orderService = ServiceManager.SERVICEMANAGER.getService(OrderService.Iface.class);
    /**
     * hr拉取兑换记录列表
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "manage/order", method = RequestMethod.POST)
    @ResponseBody
    public String getCompanyOrderList(HttpServletRequest request) {
        try {
            OrderSearchForm orderSearchForm = ParamUtils.initModelForm(request, OrderSearchForm.class);
            Map<String, String> resultMap = orderService.getCompanyOrderList(orderSearchForm);
            return ResponseLogNotification.successJson(request, resultMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 员工拉取兑换记录列表
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "visit/order", method = RequestMethod.GET)
    @ResponseBody
    public String getEmployeeOrderList(HttpServletRequest request) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            Integer employeeId = Integer.parseInt(String.valueOf(map.get("employee_id")));
            return ResponseLogNotification.successJson(request, orderService.getEmployeeOrderList(employeeId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 立即兑换
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/visit/order", method = RequestMethod.POST)
    @ResponseBody
    public String confirmOrder(HttpServletRequest request) {
        try {
            OrderForm orderForm = ParamUtils.initModelForm(request, OrderForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("商品兑换数量", orderForm.getCount());
            vu.addRequiredValidate("商城id", orderForm.getGoods_id());
            vu.addIntTypeValidate("商品兑换数量", orderForm.getCount(), null, null, 1, 99999);
            vu.addIntTypeValidate("商城id", orderForm.getGoods_id(), null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                orderService.confirmOrder(orderForm);
                return ResponseLogNotification.successJson(request, null);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 兑换发放或不发放
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/manage/order", method = RequestMethod.PUT)
    @ResponseBody
    public String updateOrder(HttpServletRequest request) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("商城状态", map.get("state"));
            vu.addIntTypeValidate("商城状态", map.get("state"), null, null, 1, 2);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Integer state = Integer.parseInt(String.valueOf(map.get("state")));
                Integer companyId = Integer.parseInt(String.valueOf(map.get("companyId")));
                List<Integer> list = JSONArray.parseArray(String.valueOf(map.get("ids")), Integer.class);
                if(list == null || list.size() == 0){
                    throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"ids为空!");
                }
                orderService.updateOrder(list, companyId, state);
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
