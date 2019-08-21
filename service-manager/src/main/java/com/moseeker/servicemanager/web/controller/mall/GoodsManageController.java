package com.moseeker.servicemanager.web.controller.mall;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.mall.service.GoodsService;
import com.moseeker.thrift.gen.mall.struct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理controller
 *
 * @author cjm
 * @date 2018-10-11 18:31
 **/
@Controller
@CounterIface
@RequestMapping("api/mall/manage")
public class GoodsManageController {

    private Logger logger = LoggerFactory.getLogger(GoodsManageController.class);

    GoodsService.Iface goodsService = ServiceManager.SERVICE_MANAGER.getService(GoodsService.Iface.class);

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
            vu.addIntTypeValidate("页面大小", goodSearchForm.getPage_size(), null, null, 1, 21);

            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                if(0 == goodSearchForm.getState()){
                    goodSearchForm.setState((byte)9);
                }
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

    /**
     * 添加商品
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/goods", method = RequestMethod.POST)
    @ResponseBody
    public String addGood(HttpServletRequest request) {
        try {
            MallGoodsInfoForm mallGoodsInfoForm = ParamUtils.initModelForm(request, MallGoodsInfoForm.class);
            ValidateUtil vu = validateRequireInfo(mallGoodsInfoForm);
            vu.addRequiredValidate("库存", mallGoodsInfoForm.getStock());
            vu.addIntTypeValidate("库存", mallGoodsInfoForm.getStock(), null, null, 0, 100000);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                goodsService.addGood(mallGoodsInfoForm);
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
     * 编辑商品
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/goods", method = RequestMethod.PUT)
    @ResponseBody
    public String editGood(HttpServletRequest request) {
        try {
            MallGoodsInfoForm mallGoodsInfoForm = ParamUtils.initModelForm(request, MallGoodsInfoForm.class);
            ValidateUtil vu = validateRequireInfo(mallGoodsInfoForm);
            vu.addRequiredValidate("商品id", mallGoodsInfoForm.getId());
            vu.addIntTypeValidate("商品id", mallGoodsInfoForm.getId(), null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                goodsService.editGood(mallGoodsInfoForm);
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
     * 批量修改商品状态（上下架）
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/goods/state", method = RequestMethod.PUT)
    @ResponseBody
    public String updateGoodState(HttpServletRequest request) {
        try {
            MallGoodsStateForm mallGoodsStateForm = ParamUtils.initModelForm(request, MallGoodsStateForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("商品状态", mallGoodsStateForm.getState());
            vu.addIntTypeValidate("商品状态", (int)mallGoodsStateForm.getState(), null, null, 1, 3);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                List<Integer> goodsIds = goodsService.updateGoodState(mallGoodsStateForm);
                List<Map<String, Integer>> resultList = new ArrayList<>();
                for(Integer goodId : goodsIds){
                    Map<String, Integer> map = new HashMap<>(1 >> 4);
                    map.put("id", goodId);
                    map.put("state", (int)mallGoodsStateForm.getState());
                    resultList.add(map);
                }
                return ResponseLogNotification.successJson(request, resultList);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 修改商品库存
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "goods/{goodId}/stock", method = RequestMethod.PUT)
    @ResponseBody
    public String updateGoodStock(@PathVariable Integer goodId, HttpServletRequest request) {
        try {
            MallGoodsStockForm mallGoodsStockForm = ParamUtils.initModelForm(request, MallGoodsStockForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("库存", mallGoodsStockForm.getStock());
            vu.addIntTypeValidate("库存", mallGoodsStockForm.getStock(), null, null, -99999, 100000);
            vu.addRequiredValidate("商品id", goodId);
            vu.addIntTypeValidate("商品id", goodId, null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                mallGoodsStockForm.setGood_id(goodId);
                Map<String, Integer> map = new HashMap<>(1 >> 4);
                map.put("stock", goodsService.updateGoodStock(mallGoodsStockForm));
                map.put("id", goodId);
                return ResponseLogNotification.successJson(request, map);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 获取商品详情
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "goods/{goodId}", method = RequestMethod.GET)
    @ResponseBody
    public String getGoodDetail(@PathVariable Integer goodId, HttpServletRequest request) {
        try {
            MallGoodsIdForm mallGoodsIdForm = ParamUtils.initModelForm(request, MallGoodsIdForm.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("商品id", goodId);
            vu.addIntTypeValidate("商品id", goodId, null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                mallGoodsIdForm.setGood_id(goodId);
                String goodStr = goodsService.getGoodDetail(mallGoodsIdForm);
                return ResponseLogNotification.successJson(request, JSONObject.parseObject(goodStr));
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 校验商品信息完整性，编辑商品时由于不能编辑库存，故不校验商品的库存
     * @param   mallGoodsInfoForm 商品参数form
     * @author  cjm
     * @date  2018/10/12
     * @return 返回校验信息
     */
    private ValidateUtil validateRequireInfo(MallGoodsInfoForm mallGoodsInfoForm) throws BIZException {

        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredValidate("主图url", mallGoodsInfoForm.getPic_url());
        vu.addRequiredValidate("公司id", mallGoodsInfoForm.getCompany_id());
        vu.addRequiredValidate("标题", mallGoodsInfoForm.getTitle());
        vu.addRequiredValidate("积分", mallGoodsInfoForm.getCredit());
        vu.addRequiredValidate("详情", mallGoodsInfoForm.getDetail());
        vu.addRequiredValidate("领取规则", mallGoodsInfoForm.getRule());

        if(mallGoodsInfoForm.getDetail().length() > 5000){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_GOODS_DETAIL_TOO_LARGE);
        }
        if(mallGoodsInfoForm.getRule().length()> 2000){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_GOODS_RULE_TOO_LARGE);
        }

        vu.addIntTypeValidate("积分", mallGoodsInfoForm.getCredit(), null, null, 0, 1000000);
        vu.addIntTypeValidate("公司id", mallGoodsInfoForm.getCompany_id(), null, null, 1, Integer.MAX_VALUE);
        vu.addIntTypeValidate("标题", getStringLength(mallGoodsInfoForm.getTitle()), null, null, 1, 33);
        vu.addStringLengthValidate("主图url", mallGoodsInfoForm.getPic_url(), null, null, 1, 2001);
        vu.addStringLengthValidate("详情", mallGoodsInfoForm.getDetail(), null, null, 1, 5001);
        vu.addStringLengthValidate("领取规则", mallGoodsInfoForm.getRule(), null, null, 1, 2001);
        return vu;
    }

    /**
     * 获取商品标题长度，一个汉字等于两个字母
     * @param str 商品标题
     * @author  cjm
     * @date  2018/10/30
     * @return 标题长度
     */
    private int getStringLength(String str){
        if(StringUtils.isNullOrEmpty(str)){
            return 0;
        }
        int len = 0;
        for (int i=0; i<str.length(); i++) {
            if (str.charAt(i)>127 || str.charAt(i)==94) {
                len += 2;
            } else {
                len ++;
            }
        }
        return len;
    }
}
