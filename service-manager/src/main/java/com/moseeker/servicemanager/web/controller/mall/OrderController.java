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
import com.moseeker.thrift.gen.mall.struct.BaseMallForm;
import com.moseeker.thrift.gen.mall.struct.MallGoodsOrderUpdateForm;
import com.moseeker.thrift.gen.mall.struct.OrderForm;
import com.moseeker.thrift.gen.mall.struct.OrderSearchForm;
import emoji4j.EmojiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    OrderService.Iface orderService = ServiceManager.SERVICE_MANAGER.getService(OrderService.Iface.class);
    /**
     * hr拉取兑换记录列表
     * //todo  兑换次数和兑换数量
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "manage/order", method = RequestMethod.POST)
    @ResponseBody
    public String getCompanyOrderList(HttpServletRequest request) {
        try {
            OrderSearchForm orderSearchForm = ParamUtils.initModelForm(request, OrderSearchForm.class);
            Map<String, String> orderMap = orderService.getCompanyOrderList(orderSearchForm);
            Map<String, Object> resultMap = new HashMap<>(1 >> 4);
            resultMap.put("total_row", orderMap.get("total_row"));
            resultMap.put("list", JSONArray.parseArray(orderMap.get("list")));
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
            BaseMallForm baseMallForm = ParamUtils.initModelForm(request, BaseMallForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("company_id", baseMallForm.getCompany_id());
            vu.addRequiredValidate("employeeId", baseMallForm.getEmployee_id());
            vu.addIntTypeValidate("company_id", baseMallForm.getCompany_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("employeeId", baseMallForm.getEmployee_id(), null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                return ResponseLogNotification.successJson(request, JSONArray.parseArray(orderService.getEmployeeOrderList(baseMallForm)));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
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
            logger.info("/api/mall/visit/order orderForm:{}",orderForm);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("company_id", orderForm.getCompany_id());
            vu.addRequiredValidate("employeeId", orderForm.getEmployee_id());
            vu.addRequiredValidate("商城id", orderForm.getGoods_id());
            vu.addRequiredValidate("商品兑换数量", orderForm.getCount());
            if(2==orderForm.getDeliverType()){
                if(EmojiUtils.countEmojis(orderForm.getAddress())>0){
                    logger.warn("/api/mall/visit/order emoji are not permited orderForm:{}",orderForm);
                    return ResponseLogNotification.fail(request, "详细地址不能包含表情");
                }
                vu.addRequiredValidate("收件人", orderForm.getAddressee());
                vu.addRequiredValidate("province", orderForm.getProvince());
                vu.addRequiredValidate("city", orderForm.getCity());
                vu.addRequiredValidate("region", orderForm.getRegion());
                vu.addRequiredValidate("详细地址", orderForm.getAddress());
                vu.addStringLengthValidate("详细地址", orderForm.getAddress(),1,100);
                vu.addRequiredValidate("用户编号", orderForm.getUserId());
                vu.addRequiredValidate("手机号", orderForm.getMobile());
            }
            vu.addIntTypeValidate("company_id", orderForm.getCompany_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("employeeId", orderForm.getEmployee_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("商品兑换数量", orderForm.getCount(), null, null, 1, 99999);
            vu.addIntTypeValidate("商城id", orderForm.getGoods_id(), null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            logger.info("/api/mall/visit/order message:{}",message);
            if (StringUtils.isNullOrEmpty(message)) {
                orderService.confirmOrder(orderForm);
                logger.info("/api/mall/visit/order result:{}",ResponseLogNotification.successJson(request, null));
                return ResponseLogNotification.successJson(request, null);
            } else {
                logger.info("/api/mall/visit/order result:{}",ResponseLogNotification.fail(request, message));
                return ResponseLogNotification.fail(request, message);
            }
        }catch (BIZException e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.failJson(request, e.getCode(), e.getMessage(), null);
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
            MallGoodsOrderUpdateForm updateForm = ParamUtils.initModelForm(request, MallGoodsOrderUpdateForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("company_id", updateForm.getCompany_id());
            vu.addRequiredValidate("hr_id", updateForm.getHr_id());
            vu.addIntTypeValidate("company_id", updateForm.getCompany_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("hr_id", updateForm.getHr_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addRequiredValidate("商城状态", updateForm.getState());
            vu.addIntTypeValidate("商城状态", updateForm.getState(), null, null, 1, 3);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                if(updateForm.getIds() == null || updateForm.getIds().size() == 0){
                    throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"ids为空!");
                }
                orderService.updateOrder(updateForm);
                return ResponseLogNotification.successJson(request, null);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (BIZException e){
            return ResponseLogNotification.failJson(request, e.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 导出兑换记录
     * @author  cjm
     * @date  2018/10/23
     */
    @RequestMapping(value = "/manage/order/excel", method = RequestMethod.GET)
    @ResponseBody
    public String exportOrder(HttpServletRequest request) {
        try {
            BaseMallForm baseMallForm = ParamUtils.initModelForm(request, BaseMallForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("company_id", baseMallForm.getCompany_id());
            vu.addRequiredValidate("hr_id", baseMallForm.getHr_id());
            vu.addIntTypeValidate("company_id", baseMallForm.getCompany_id(), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("hr_id", baseMallForm.getHr_id(), null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                return ResponseLogNotification.successJson(request, orderService.exportOrder(baseMallForm));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    @GetMapping(value = "/address/preview")
    @ResponseBody
    public String previewMailAddress(HttpServletRequest request){
        try {
            Map<String,Object> params = ParamUtils.parseRequestParam(request);
            Integer mailId = Integer.valueOf(String.valueOf(params.get("mailId")));
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("mailId", mailId);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                return ResponseLogNotification.successJson(request, orderService.getAddressById(mailId));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
