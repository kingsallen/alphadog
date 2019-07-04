package com.moseeker.servicemanager.web.controller.referral;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.referral.form.*;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.referral.struct.*;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class ReferralRadarController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ReferralService.Iface referralService = ServiceManager.SERVICEMANAGER.getService(ReferralService.Iface.class);

    @RequestMapping(value = "v1/referral/progress", method = RequestMethod.POST)
    public String progress(@RequestBody ProgressForm progressForm) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", progressForm.getUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", progressForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("progress", progressForm.getProgress(), -1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", progressForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", progressForm.getUserId());
        validateUtil.addRequiredValidate("progress", progressForm.getProgress());
        validateUtil.addRequiredValidate("appid", progressForm.getAppid());
        validateUtil.addRequiredValidate("companyId", progressForm.getCompanyId());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            if (progressForm.getPageNum() == null || progressForm.getPageNum() <= 0) {
                progressForm.setPageNum(1);
            }
            if (progressForm.getPageSize() == null || progressForm.getPageSize() <= 0) {
                progressForm.setPageSize(10);
            }
            ReferralProgressInfo progressInfo = new ReferralProgressInfo();
            BeanUtils.copyProperties(progressForm, progressInfo);
            String resultJson = referralService.getProgressBatch(progressInfo);
            JSONArray response = JSONArray.parseArray(resultJson);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    @RequestMapping(value = "v1/referral/progress/{applyId}", method = RequestMethod.GET)
    public String progress(@PathVariable(name = "applyId") Integer applyId,
                           @RequestParam(name = "user_id") Integer userId,
                           @RequestParam(name = "appid") Integer appid,
                           @RequestParam(name = "company_id") Integer companyId,
                           @RequestParam(name = "presentee_user_id") Integer presenteeUserId,
                           @RequestParam(name = "progress") Integer progress) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("候选人userId", userId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", appid, 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", companyId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("点击人userId", presenteeUserId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("progress", progress, 0, Integer.MAX_VALUE);

        validateUtil.addRequiredValidate("候选人userId", userId);
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("companyId", companyId);
        validateUtil.addRequiredValidate("点击人userId", presenteeUserId);
        validateUtil.addRequiredValidate("progress", progress);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralProgressQueryInfo queryInfo = new ReferralProgressQueryInfo();
            queryInfo.setCompanyId(companyId);
            queryInfo.setApplyId(applyId);
            queryInfo.setPresenteeUserId(presenteeUserId);
            queryInfo.setUserId(userId);
            queryInfo.setProgress(progress);
            String responseStr = referralService.getProgressByOne(queryInfo);
            JSONObject response = JSONObject.parseObject(responseStr);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    @RequestMapping(value = "v1/referral/progress/keyword", method = RequestMethod.GET)
    public String progressQueryKeyword(@RequestParam(name = "user_id") Integer userId,
                                       @RequestParam(name = "appid") Integer appid,
                                       @RequestParam(name = "company_id") Integer companyId,
                                       @RequestParam(name = "keyword") String keyword,
                                       @RequestParam(name = "progress") Integer progress) throws TException {
        logger.info("userId:{}, companyId:{}, keyword:{}, progress:{}", userId, companyId, keyword, progress);
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", userId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", appid, 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("progress", progress, 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", companyId, 1, Integer.MAX_VALUE);
        validateUtil.addStringLengthValidate("keyword", keyword.trim(), 1, 50);
        validateUtil.addRequiredValidate("员工userId", userId);
        validateUtil.addRequiredValidate("progress", progress);
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("companyId", companyId);
        validateUtil.addRequiredValidate("keyword", keyword);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralProgressInfo progressInfo = new ReferralProgressInfo();
            progressInfo.setCompanyId(companyId);
            progressInfo.setKeyword(keyword);
            progressInfo.setProgress(progress);
            progressInfo.setUserId(userId);
            String resultJson = referralService.progressQueryKeyword(progressInfo);
            logger.info("================resultJson:{}", resultJson);
            JSONArray response = JSONArray.parseArray(resultJson);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    @RequestMapping(value = "v1/referral/seek/check", method = RequestMethod.GET)
    public String checkSeekReferral(@RequestParam(name = "recom_user_id") Integer userId,
                                    @RequestParam(name = "appid") Integer appid,
                                    @RequestParam(name = "company_id") Integer companyId,
                                    @RequestParam(name = "presentee_user_id") Integer presenteeId,
                                    @RequestParam(name = "position_id") Integer positionId,
                                    @RequestParam(name = "psc") Integer parentChainId) throws TException {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", userId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", appid, 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", companyId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("presentee_user_id", presenteeId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("position_id", positionId, 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("psc", parentChainId, -1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", userId);
        validateUtil.addRequiredValidate("presentee_user_id", presenteeId);
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("companyId", companyId);
        validateUtil.addRequiredValidate("position_id", positionId);
        validateUtil.addRequiredValidate("psc", parentChainId);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            int referralId = referralService.checkSeekReferral(userId, presenteeId, positionId, companyId, parentChainId);
            JSONObject response = new JSONObject();
            response.put("referral_id", referralId);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 候选人打开职位连接判断推荐人是否是员工
     *
     * @param checkForm 检验转发链路的起点是否是员工
     * @return 推荐结果
     */
    @RequestMapping(value = "/v1/referral/employee/check", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmployee(@RequestBody CheckEmployeeForm checkForm) throws Exception {
        logger.info("checkForm:{}", checkForm);
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("转发人", checkForm.getRecomUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("转发链路parentChainId", checkForm.getParentChainId(), -1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", checkForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("职位id", checkForm.getPid(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("转发人id", checkForm.getPresenteeUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", checkForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("companyId", checkForm.getCompanyId());
        validateUtil.addRequiredValidate("转发人", checkForm.getRecomUserId());
        validateUtil.addRequiredValidate("转发链路parentChainId", checkForm.getParentChainId());
        validateUtil.addRequiredValidate("appid", checkForm.getAppid());
        validateUtil.addRequiredValidate("职位id", checkForm.getPid());
        validateUtil.addRequiredValidate("转发人id", checkForm.getPresenteeUserId());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            CheckEmployeeInfo checkInfo = new CheckEmployeeInfo();
            BeanUtils.copyProperties(checkForm, checkInfo);
            String jsonResult = referralService.checkEmployee(checkInfo);
            jsonResult = (jsonResult == null ? "" : jsonResult);
            logger.info("checkEmployee:{}", jsonResult);
            JSONObject response = JSONObject.parseObject(jsonResult);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 保存十分钟消息模板的sharechain记录
     *
     * @return 推荐结果
     */
    @RequestMapping(value = "/v1/referral/radar/saveTemp", method = RequestMethod.POST)
    @ResponseBody
    public String saveTenMinuteCandidateShareChain(@RequestBody CandidateTempForm tempForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("appid", tempForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", tempForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("员工userId", tempForm.getUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", tempForm.getUserId());
        validateUtil.addRequiredValidate("appid", tempForm.getAppid());
        validateUtil.addRequiredValidate("companyId", tempForm.getCompanyId());
        validateUtil.addRequiredValidate("转发时间戳",tempForm.getShareTime());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            if(System.currentTimeMillis()-tempForm.getShareTime()>10*60*1000){//判断是否超过十分钟
                return com.moseeker.servicemanager.web.controller.Result.fail("转发已经超过十分钟").toJson();
            }
            ReferralCardInfo cardInfo = new ReferralCardInfo();
            cardInfo.setTimestamp(tempForm.getShareTime());
            cardInfo.setUserId(tempForm.getUserId());
            cardInfo.setCompanyId(tempForm.getCompanyId());
//            BeanUtils.copyProperties(tempForm, cardInfo);
            referralService.saveTenMinuteCandidateShareChain(cardInfo);
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 点击人脉连连看按钮/点击分享的人脉连连看页面
     *
     * @param radarForm 连接人脉雷达的参数
     * @return 推荐结果
     */
    @RequestMapping(value = "/v1/referral/radar/connect", method = RequestMethod.POST)
    @ResponseBody
    public String connectRadar(@RequestBody ConnectRadarForm radarForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("转发人", radarForm.getRecomUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("被邀请连线人id", radarForm.getNextUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", radarForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("parentId", radarForm.getParentId(), -1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("人脉连连看id", radarForm.getChainId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", radarForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("转发人", radarForm.getRecomUserId());
        validateUtil.addRequiredValidate("被邀请连线人id", radarForm.getNextUserId());
        validateUtil.addRequiredValidate("appid", radarForm.getAppid());
        validateUtil.addRequiredValidate("人脉连连看id", radarForm.getChainId());
        validateUtil.addRequiredValidate("parentId", radarForm.getParentId());
        validateUtil.addRequiredValidate("companyId", radarForm.getCompanyId());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ConnectRadarInfo radarInfo = new ConnectRadarInfo();
            BeanUtils.copyProperties(radarForm, radarInfo);
            String jsonResult = referralService.connectRadar(radarInfo);
            jsonResult = (jsonResult == null ? "" : jsonResult);
            JSONObject response = JSONObject.parseObject(jsonResult);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.validateFailed(result).toJson();
        }
    }

    /**
     * 10分钟消息模板-我不熟悉
     *
     * @param inviteForm 跳过当前不熟悉的浏览人
     * @return 推荐结果
     */
    @RequestMapping(value = "/v1/referral/radar/ignore", method = RequestMethod.POST)
    @ResponseBody
    public String ignoreCurrentViewer(@RequestBody ReferralInviteForm inviteForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateInviteInfo(validateUtil, inviteForm);
        validateUtil.addRequiredValidate("timestamp", inviteForm.getTimestamp());
        validateUtil.addRequiredValidate("当前处理的endUserId", inviteForm.getEndUserId());
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralInviteInfo inviteInfo = new ReferralInviteInfo();
            BeanUtils.copyProperties(inviteForm, inviteInfo);
            referralService.ignoreCurrentViewer(inviteInfo);
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 10分钟消息模板-人脉筛选，获取卡片数据
     *
     * @return 推荐结果
     */
    @RequestMapping(value = "/v1/referral/radar/cards", method = RequestMethod.POST)
    @ResponseBody
    public String getRadarCards(@RequestBody ReferralCardForm referralCard) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", referralCard.getUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("companyId", referralCard.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addIntTypeValidate("appid", referralCard.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", referralCard.getUserId());
        validateUtil.addRequiredValidate("appid", referralCard.getAppid());
        validateUtil.addRequiredValidate("companyId", referralCard.getCompanyId());
        validateUtil.addRequiredValidate("timestamp", referralCard.getTimestamp());

        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            if (referralCard.getPageNumber() == null || referralCard.getPageNumber() <= 0) {
                referralCard.setPageNumber(1);
            }
            if (referralCard.getPageSize() == null || referralCard.getPageSize() <= 0) {
                referralCard.setPageSize(10);
            }
            ReferralCardInfo cardInfo = new ReferralCardInfo();
            BeanUtils.copyProperties(referralCard, cardInfo);
            String jsonResult = referralService.getRadarCards(cardInfo);
            jsonResult = (jsonResult == null ? "" : jsonResult);
            JSONArray response = JSONArray.parseArray(jsonResult);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 10分钟消息模板-邀请投递
     *
     * @param inviteForm 邀请浏览职位的候选人投递
     * @return 推荐结果
     */
    @RequestMapping(value = "/v1/referral/radar/invite", method = RequestMethod.POST)
    @ResponseBody
    public String inviteApplication(@RequestBody ReferralInviteForm inviteForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateInviteInfo(validateUtil, inviteForm);
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralInviteInfo inviteInfo = new ReferralInviteInfo();
            BeanUtils.copyProperties(inviteForm, inviteInfo);
            String jsonResult = referralService.inviteApplication(inviteInfo);
            jsonResult = (jsonResult == null ? "" : jsonResult);
            JSONObject response = JSONObject.parseObject(jsonResult);
            return Result.success(response).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    /**
     * 邀请投递不可触达候选人时，掉此接口将候选人标记为已处理
     *
     * @param referralStateForm 邀请投递或推荐它
     * @return 推荐结果
     */
    @RequestMapping(value = "/v1/referral/candidate/state", method = RequestMethod.POST)
    @ResponseBody
    public String handleCandidateState(@RequestBody ReferralStateForm referralStateForm) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addIntTypeValidate("员工userId", referralStateForm.getUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", referralStateForm.getUserId());

        validateUtil.addIntTypeValidate("appid", referralStateForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("appid", referralStateForm.getAppid());

        validateUtil.addIntTypeValidate("职位id", referralStateForm.getPid(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("职位id", referralStateForm.getPid());

        validateUtil.addIntTypeValidate("卡片处理状态", referralStateForm.getState(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("卡片处理状态", referralStateForm.getState());

        validateUtil.addIntTypeValidate("被邀请人id", referralStateForm.getEndUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("被邀请人id", referralStateForm.getEndUserId());

        validateUtil.addIntTypeValidate("公司id", referralStateForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("公司id", referralStateForm.getCompanyId());

        if(referralStateForm.getTimestamp() == null){
            referralStateForm.setTimestamp(0L);
        }
        String result = validateUtil.validate();
        if (StringUtils.isBlank(result)) {
            ReferralStateInfo referralStateInfo = new ReferralStateInfo();
            BeanUtils.copyProperties(referralStateForm, referralStateInfo);
            referralService.handleCandidateState(referralStateInfo);
            return Result.success().toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.fail(result).toJson();
        }
    }

    private void validateInviteInfo(ValidateUtil validateUtil, ReferralInviteForm inviteForm) {
        validateUtil.addIntTypeValidate("员工userId", inviteForm.getUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("员工userId", inviteForm.getUserId());

        validateUtil.addIntTypeValidate("appid", inviteForm.getAppid(), 0, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("appid", inviteForm.getAppid());

        validateUtil.addIntTypeValidate("职位id", inviteForm.getPid(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("职位id", inviteForm.getPid());

        validateUtil.addIntTypeValidate("被邀请人id", inviteForm.getEndUserId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("被邀请人id", inviteForm.getEndUserId());

        validateUtil.addIntTypeValidate("公司id", inviteForm.getCompanyId(), 1, Integer.MAX_VALUE);
        validateUtil.addRequiredValidate("公司id", inviteForm.getCompanyId());

        if(inviteForm.getTimestamp() == null){
            inviteForm.setTimestamp(0L);
        }
    }
}
