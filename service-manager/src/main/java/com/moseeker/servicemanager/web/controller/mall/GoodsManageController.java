package com.moseeker.servicemanager.web.controller.mall;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.mall.service.GoodsService;
import com.moseeker.thrift.gen.mall.struct.GoodSearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    GoodsService.Iface goodsService = ServiceManager.SERVICEMANAGER.getService(GoodsService.Iface.class);

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
                if(0 == goodSearchForm.getState()){
                    goodSearchForm.setState((byte)9);
                }
                return ResponseLogNotification.successJson(request, goodsService.getGoodsList(goodSearchForm));
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
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = validateRequireInfo(map);
            vu.addRequiredValidate("库存", map.get("stock"));
            vu.addIntTypeValidate("库存", map.get("stock"), null, null, 0, 99999);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                MallGoodsInfoDO mallGoodsInfoDO = ParamUtils.initModelForm(map, MallGoodsInfoDO.class);
                goodsService.addGood(mallGoodsInfoDO);
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
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = validateRequireInfo(map);
            vu.addRequiredValidate("商品id", map.get("id"));
            vu.addIntTypeValidate("商品id", map.get("id"), null, null, 0, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                MallGoodsInfoDO mallGoodsInfoDO = ParamUtils.initModelForm(map, MallGoodsInfoDO.class);
                goodsService.editGood(mallGoodsInfoDO);
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
     * todo 希望改成批量
     * 修改商品状态（上下架）
     * @author  cjm
     * @date  2018/10/12
     */
    @RequestMapping(value = "/goods/{goodId}/state", method = RequestMethod.PUT)
    @ResponseBody
    public String updateGoodState(@PathVariable Integer goodId, HttpServletRequest request) {
        try {
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("商品id", goodId);
            vu.addRequiredValidate("商品状态", map.get("state"));
            vu.addIntTypeValidate("商品id", goodId, null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("商品状态", map.get("state"), null, null, 0, 1);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Integer companyId = Integer.parseInt(String.valueOf(map.get("company_id")));
                Integer state = Integer.parseInt(String.valueOf(map.get("state")));
                return ResponseLogNotification.successJson(request, goodsService.updateGoodState(goodId, companyId, state));
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
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("库存", map.get("stock"));
            vu.addIntTypeValidate("库存", map.get("stock"), null, null, 1, Integer.MAX_VALUE);
            vu.addRequiredValidate("商品id", map.get("goodId"));
            vu.addIntTypeValidate("商品id", map.get("goodId"), null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Integer companyId = Integer.parseInt(String.valueOf(map.get("company_id")));
                Integer stock = Integer.parseInt(String.valueOf(map.get("stock")));
                return ResponseLogNotification.successJson(request, goodsService.updateGoodStock(goodId, companyId, stock));
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
            Map<String, Object> map = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("商品id", goodId);
            vu.addIntTypeValidate("商品id", goodId, null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Integer companyId = Integer.parseInt(String.valueOf(map.get("company_id")));
                return ResponseLogNotification.successJson(request, goodsService.getGoodDetail(goodId, companyId));
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
     * @param   map 商品参数map
     * @author  cjm
     * @date  2018/10/12
     * @return 返回校验信息
     */
    private ValidateUtil validateRequireInfo(Map<String, Object> map){

        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredValidate("主图url", map.get("pic_url"));
        vu.addRequiredValidate("公司id", map.get("company_id"));
        vu.addRequiredValidate("标题", map.get("title"));
        vu.addRequiredValidate("积分", map.get("credit"));
        vu.addRequiredValidate("详情", map.get("detail"));
        vu.addRequiredValidate("领取规则", map.get("rule"));

        vu.addIntTypeValidate("积分", map.get("credit"), null, null, 0, Integer.MAX_VALUE);
        vu.addIntTypeValidate("公司id", map.get("company_id"), null, null, 1, Integer.MAX_VALUE);
        vu.addStringLengthValidate("标题", map.get("title"), null, null, 1, 16);
        vu.addStringLengthValidate("主图url", map.get("pic_url"), null, null, 1, 2000);
        vu.addStringLengthValidate("详情", map.get("detail"), null, null, 1, 2000);
        vu.addStringLengthValidate("领取规则", map.get("rule"), null, null, 1, 200);
        return vu;
    }
}
