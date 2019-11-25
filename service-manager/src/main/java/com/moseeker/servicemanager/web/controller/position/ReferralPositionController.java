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
import com.moseeker.servicemanager.web.controller.referral.form.ReferralBonusForm;
import com.moseeker.servicemanager.web.controller.referral.vo.ReferralBonusStageData;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices;
import com.moseeker.thrift.gen.position.service.ReferralPositionServices;
import com.moseeker.thrift.gen.position.struct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    private ReferralPositionServices.Iface referralPositionService = ServiceManager.SERVICE_MANAGER.getService(ReferralPositionServices.Iface.class);

    private PositionServices.Iface positonServices = ServiceManager.SERVICE_MANAGER.getService(PositionServices.Iface.class);

    /**
     * 根据postionId 删除内推职位
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/position/del/", method = RequestMethod.POST)
    @ResponseBody
    public String delReferralPosition(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);

            ReferralPositionUpdateDataDO dataDO =  ParamUtils.initModelForm(params, ReferralPositionUpdateDataDO.class);

            logger.debug("ReferralPositionController putReferralPosition  dataDO : {}",JSON.toJSONString(dataDO)  );

            referralPositionService.delReferralPositions(dataDO);

            logger.debug("ReferralPositionController delReferralPosition  response Finished" );


            return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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

            ReferralPositionUpdateDataDO dataDO =  ParamUtils.initModelForm(params, ReferralPositionUpdateDataDO.class);

            logger.debug("ReferralPositionController1 putReferralPosition  dataDO : {}",JSON.toJSONString(dataDO)  );

            referralPositionService.putReferralPositions(dataDO);

            logger.debug("ReferralPositionController1 putReferralPosition  response Finished" );

            return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            return ResponseLogNotification.fail(request, e);
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

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Map<String,String> queryMapString = new HashMap<>();
            for(String key :params.keySet()) {
                queryMapString.put(key,params.get(key).toString());
            }

            //特殊处理，将page_from用page_num代替
            String page_num  =queryMapString.get("page_num");
            if(StringUtils.isNotNullOrEmpty(queryMapString.get("page_num"))) {
                queryMapString.put("page_from",page_num);
            } else {
                queryMapString.put("page_from","1");
            }

            //只看内推职位固定传is_referral=1
            queryMapString.put("is_referral","1");
            //该接口只看在招职位,固定传flag=0
            queryMapString.put("flag","0");

            List<WechatPositionListData> listData = positonServices.getReferralPositionList(queryMapString);
            
            logger.info("ReferralPositionController wechatPositionList  listData.size : {} queryMMapString : {} ",JSON.toJSONString(listData.size()),JSON.toJSONString(queryMapString) );

            return  com.moseeker.servicemanager.web.controller.Result.success(listData).toJson();

        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * HR端获职位列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/hr/position/list", method = RequestMethod.GET)
    @ResponseBody
    public String hrPositionList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);

            Map<String,String> queryMapString = new HashMap<>();
            for(String key :params.keySet()) {
                queryMapString.put(key,params.get(key).toString());
            }

            //特殊处理，将page_from用page_num代替
            String page_num  =queryMapString.get("page_num");
            if(StringUtils.isNotNullOrEmpty(queryMapString.get("page_num"))) {
                queryMapString.put("page_from",page_num);
            }else{
                queryMapString.put("page_from","1");
            }

            //该接口给flag默认值0
            String flag = queryMapString.get("flag");
            if(StringUtils.isNullOrEmpty(flag)) {
                queryMapString.put("flag","0");
            }
            String accountType = queryMapString.get("account_type");
            String accountId  =queryMapString.get("account_id");

            //如果HR使用子账号,那就只用publisher=accountId的所有职位,并将查询参数company_id设置为空
            if(StringUtils.isNotNullOrEmpty(accountType) && accountType.equals(String.valueOf(HRAccountType.SubAccount.getType())) && StringUtils.isNotNullOrEmpty(accountId)) {
                queryMapString.put("company_id","");
                queryMapString.put("publisher",accountId);
            }
            List<WechatPositionListData> listData = positonServices.getReferralPositionList(queryMapString);

            logger.info("ReferralPositionController hrPositionList  listData.size : {} queryMMapString : {} ",JSON.toJSONString(listData.size()),JSON.toJSONString(queryMapString) );

            return  com.moseeker.servicemanager.web.controller.Result.success(listData).toJson();

        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            return ResponseLogNotification.fail(request, e);
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
            return ResponseLogNotification.fail(request, e);
        }
    }


    /**
     * 内推职位设置内推奖金
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/position/bonus", method = RequestMethod.POST)
    @ResponseBody
    public String putReferralPositionBonus(HttpServletRequest request, HttpServletResponse response, @RequestBody ReferralBonusForm referralBonusForm) {
        try {

            logger.debug("ReferralPositionController1 putReferralPostionBonus  referralBonusForm : {}",JSON.toJSONString(referralBonusForm)  );

            Response result = referralPositionService.putReferralPositionBonus(convertReferralPositionBonusVO(referralBonusForm));

            logger.debug("ReferralPositionController1 putReferralPostionBonus  response Finished" );

            return ResponseLogNotification.success(request, result);

        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }



    /**
     * 根据PositionID获取内推奖金信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/referral/position/bonus", method = RequestMethod.GET)
    @ResponseBody
    public String getReferralPositionBonus(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            ValidateUtil validateUtil = new ValidateUtil();
            Integer positionId = params.getInt("position_id");
            validateUtil.addRequiredValidate("position_id", positionId);

            if (org.apache.commons.lang.StringUtils.isNotBlank(validateUtil.validate())) {
                return ResponseLogNotification.failJson(request, validateUtil.getResult());
            }

            logger.debug("ReferralPositionController1 getReferralPositionBonus  position_id : {}",JSON.toJSONString(positionId)  );

            ReferralPositionBonusVO rpcVO =  referralPositionService.getReferralPositionBonus(positionId);

            String jsonStr = JSON.toJSONString(rpcVO);

            com.moseeker.servicemanager.web.controller.position.vo.ReferralPositionBonusVO vo = JSON.parseObject(jsonStr,com.moseeker.servicemanager.web.controller.position.vo.ReferralPositionBonusVO.class);

            logger.debug("ReferralPositionController1 getReferralPositionBonus  response Finished" );

            return ResponseLogNotification.successJson(request, vo);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }


    /**
     * 根据user_id获取匹配的职位信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/v1/match/position", method = RequestMethod.GET)
    @ResponseBody
    public String getReferralPositionMatch(HttpServletRequest request, HttpServletResponse response) {
        try {

            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            ValidateUtil validateUtil = new ValidateUtil();
            Integer userId = params.getInt("user_id");
            Integer companyId = params.getInt("company_id");
            validateUtil.addRequiredValidate("user_id", userId);
            validateUtil.addRequiredValidate("company_id", companyId);
            if (org.apache.commons.lang.StringUtils.isNotBlank(validateUtil.validate())) {
                return ResponseLogNotification.failJson(request, validateUtil.getResult());
            }
            List<ReferralPositionMatchDO> match =  referralPositionService.getMatchPositionInfo(userId,companyId);
            return ResponseLogNotification.successJson(request, match);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    private ReferralPositionBonusVO convertReferralPositionBonusVO(ReferralBonusForm referralBonusForm) {
        Integer positionId = referralBonusForm.getPosition_id();
        List<ReferralBonusStageData> datas = referralBonusForm.getStage_data();
        String totalBonus =  referralBonusForm.getTotal_bonus();

        ReferralPositionBonusVO bonusVO = new ReferralPositionBonusVO();
        ReferralPositionBonusDO bonusDO = new ReferralPositionBonusDO();
        bonusDO.setPosition_id(positionId);
        bonusDO.setTotal_bonus(totalBonus);

        List<ReferralPositionBonusStageDetailDO> detailDOS = new ArrayList<>();
        for(ReferralBonusStageData data : datas) {
            ReferralPositionBonusStageDetailDO detailDO = new ReferralPositionBonusStageDetailDO();
            detailDO.setStage_bonus(data.getBonus());
            detailDO.setStage_proportion(data.getProportion());
            detailDO.setStage_type(data.getStage_type());
            detailDOS.add(detailDO);
        }
        bonusVO.setPosition_bonus(bonusDO);
        bonusVO.setBonus_details(detailDOS);

        return  bonusVO;
    }

}
