package com.moseeker.servicemanager.web.controller.mall;

import com.alibaba.fastjson.JSONArray;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.mall.service.GoodsService;
import com.moseeker.thrift.gen.mall.struct.GoodSearchForm;
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
 * 商品展示controller
 *
 * @author cjm
 * @date 2018-10-12 14:25
 **/
@Controller
@CounterIface
@RequestMapping("api/mall/visit")
public class GoodsShowController {

    GoodsService.Iface goodsService = ServiceManager.SERVICE_MANAGER.getService(GoodsService.Iface.class);

    private Logger logger = LoggerFactory.getLogger(GoodsShowController.class);

    /**
     * 获取商品列表
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ResponseBody
    public String getGoodsList(HttpServletRequest request) {
        try {
            GoodSearchForm goodSearchForm = ParamUtils.initModelForm(request, GoodSearchForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("页码", goodSearchForm.getPage_number());
            vu.addRequiredValidate("页面大小", goodSearchForm.getPage_size());
            vu.addIntTypeValidate("页码", goodSearchForm.getPage_number(), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("页面大小", goodSearchForm.getPage_size(), null, null, 1, 20);

            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                goodSearchForm.setState((byte)2);
                Map<String, Object> resultMap = new HashMap<>(1 >> 4);
                Map<String, String> tempMap = goodsService.getGoodsList(goodSearchForm);
                resultMap.put("goods_list", JSONArray.parseArray(tempMap.get("goods_list")));
                resultMap.put("total_row", tempMap.get("total_row"));
                return ResponseLogNotification.successJson(request, resultMap);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
