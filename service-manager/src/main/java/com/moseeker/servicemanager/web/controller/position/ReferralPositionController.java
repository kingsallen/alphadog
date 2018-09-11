package com.moseeker.servicemanager.web.controller.position;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.QRCodeUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.position.bean.ReferralPointFlagVO;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.service.ReferralPositionServices;
import com.moseeker.thrift.gen.position.struct.WechatPositionListData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */
@Controller
@CounterIface
public class ReferralPositionController {

    private Logger logger = LoggerFactory.getLogger(PositionController.class);

    private ReferralPositionServices.Iface referralPositionService = ServiceManager.SERVICEMANAGER.getService(ReferralPositionServices.Iface.class);

    private PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);

    /**
     * 根据postionId 删除内推职位
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/position/", method = RequestMethod.DELETE)
    @ResponseBody
    public String delReferralPosition(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<Integer> pids = (List<Integer>)params.get("position_ids");
            if (CollectionUtils.isEmpty(pids)) {
                return ResponseLogNotification.fail(request, "position_ids不能为空");
            }

            referralPositionService.delReferralPositions(pids);

            return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 添加内推职位
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/position/", method = RequestMethod.PUT)
    @ResponseBody
    public String putReferralPosition(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<Integer> pids = (List<Integer>)params.get("position_ids");
            if (CollectionUtils.isEmpty(pids)) {
                return ResponseLogNotification.fail(request, "position_ids不能为空");
            }

            referralPositionService.putReferralPositions(pids);

            return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 生成内推二维码图片 base64格式
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/position/qrcode", method = RequestMethod.POST)
    @ResponseBody
    public String positionQrCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            ValidateUtil validateUtil = new ValidateUtil();
            String url = params.getString("url");
            String logo = params.getString("logo");
            int width  = params.getInt("width",256);
            int height = params.getInt("height",256);
            int ratio  = params.getInt("ratio",4);
            validateUtil.addRequiredStringValidate("网址", url);
            if (org.apache.commons.lang.StringUtils.isNotBlank(validateUtil.validate())) {
                return ResponseLogNotification.failJson(request, validateUtil.getResult());
            }
            String base64 = QRCodeUtil.generalQRCode(url,logo,width,height,ratio);
            return com.moseeker.servicemanager.web.controller.Result.success(base64).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 微信端获取内推职位列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/wechat/position/list", method = RequestMethod.GET)
    @ResponseBody
    public String wechatPositionList(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> map = ParamUtils.parseRequestParam(request);
            logger.info("map: " + map.toString());

            Map<String,String> queryMapString = new HashMap<>();
            for(String key :map.keySet()) {
                queryMapString.put(key,map.get(key).toString());
            }
            List<WechatPositionListData> listData = positonServices.getReferralPositionList(queryMapString);

            return  com.moseeker.servicemanager.web.controller.Result.success(listData).toJson();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * HR端获取内推职位列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/hr/position/list", method = RequestMethod.GET)
    @ResponseBody
    public String hrPositionList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> map = ParamUtils.parseRequestParam(request);
            logger.info("map: " + map.toString());

            Map<String,String> queryMapString = new HashMap<>();
            for(String key :map.keySet()) {
                queryMapString.put(key,map.get(key).toString());
            }
            List<WechatPositionListData> listData = positonServices.getReferralPositionList(queryMapString);

            return  com.moseeker.servicemanager.web.controller.Result.success(listData).toJson();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 根据companyID 设置内推职位是否开启积分奖励
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/company/config/pointsflag", method = RequestMethod.POST)
    @ResponseBody
    public String configPointsFlag(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            ValidateUtil validateUtil = new ValidateUtil();
            String companyId = params.getString("company_id");
            String flag = params.getString("flag");
            validateUtil.addRequiredStringValidate("company_id", companyId);
            validateUtil.addRequiredStringValidate("flag", flag);

            if (org.apache.commons.lang.StringUtils.isNotBlank(validateUtil.validate())) {
                return ResponseLogNotification.failJson(request, validateUtil.getResult());
            }

            referralPositionService.updatePointsConfig(Integer.valueOf(companyId),Integer.valueOf(flag));

            return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 根据companyID 查看只针对内推职位是否开启积分奖励
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/company/config/pointsflag", method = RequestMethod.GET)
    @ResponseBody
    public String getConfigPointsFlag(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            ValidateUtil validateUtil = new ValidateUtil();
            String companyId = params.getString("company_id");
            validateUtil.addRequiredStringValidate("company_id", companyId);

            if (org.apache.commons.lang.StringUtils.isNotBlank(validateUtil.validate())) {
                return ResponseLogNotification.failJson(request, validateUtil.getResult());
            }
            Response result  = referralPositionService.getPointsConfig(Integer.valueOf(companyId));

            ReferralCompanyConf referralCompanyConf =  JSON.parseObject(result.getData(), ReferralCompanyConf.class);

            ReferralPointFlagVO vo = new ReferralPointFlagVO(referralCompanyConf.getCompanyId(),Integer.valueOf(referralCompanyConf.getPositionPointsFlag()) );

            return ResponseLogNotification.successJson(request, vo);

        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
