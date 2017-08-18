package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.common.validation.rules.DateType;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.thrift.gen.employee.struct.RewardVOPageVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * HR账号服务
 * <p>
 * <p>
 * Created by zzh on 16/6/1.
 */
// @Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class UserHrAccountController {

    Logger logger = LoggerFactory.getLogger(UseraccountsController.class);

    UserHrAccountService.Iface userHrAccountService = ServiceManager.SERVICEMANAGER
            .getService(UserHrAccountService.Iface.class);

    /**
     * 注册HR发送验证码
     */
    @RequestMapping(value = "/hraccount/sendsignupcode", method = RequestMethod.POST)
    @ResponseBody
    public String sendMobileVerifiyCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取HR用户实体对象
            String mobile = request.getParameter("mobile");
            String code = request.getParameter("code");
            int source = Integer.valueOf(request.getParameter("source"));

            Response result = userHrAccountService.sendMobileVerifiyCode(mobile, code, source);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 添加HR账号
     * <p>
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount", method = RequestMethod.POST)
    @ResponseBody
    public String postUserHrAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取HR用户实体对象
            DownloadReport downloadReport = ParamUtils.initModelForm(request, DownloadReport.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredStringValidate("验证码", downloadReport.getCode(), null, null);
            vu.addRequiredStringValidate("公司名称", downloadReport.getCompany_name(), null, null);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                Response result = userHrAccountService.postResource(downloadReport);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 驼峰转下划线
     *
     * @param struct
     * @return
     */
    private Map<String, Object> thirdpartyAccountToMap(HrThirdPartyAccountDO struct) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", struct.getId());
        resultMap.put("channel", struct.getChannel());
        resultMap.put("username", struct.getUsername());
        resultMap.put("company_id", struct.getCompanyId());
        resultMap.put("member_name", struct.getMembername());
        resultMap.put("bound", struct.getBinding());
        resultMap.put("create_time", struct.getCreateTime());
        resultMap.put("update_time", struct.getCreateTime());
        resultMap.put("sync_time", struct.getSyncTime());
        resultMap.put("password", struct.getPassword());
        resultMap.put("remain_num", struct.getRemainNum());
        resultMap.put("remain_profile_num", struct.getRemainProfileNum());
        resultMap.put("error_message", struct.getErrorMessage());
        return resultMap;
    }

    @RequestMapping(value = "/thirdpartyaccount/bind", method = RequestMethod.POST)
    @ResponseBody
    public String bindThirdPartyAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            HrThirdPartyAccountDO struct = ParamUtils.initModelForm(params, HrThirdPartyAccountDO.class);
            if (params.get("member_name") != null) {
                struct.setMembername(params.get("member_name").toString());
            }
            logger.info("bind thirdParyAccount in controller params===========================" + JSON.toJSONString(struct));
            struct = userHrAccountService.bindThirdPartyAccount(params.getInt("user_id", 0), struct, params.getBoolean("sync", true));
            if (struct.getBinding() == 100) {
                struct.setBinding(Integer.valueOf(0).shortValue());
                Map<String, Object> result = thirdpartyAccountToMap(struct);
                result.put("mobile", struct.getErrorMessage());
                return ResponseLogNotification.failJson(request, 100, "需要验证手机号", result);
            } else {
                return ResponseLogNotification.successJson(request, thirdpartyAccountToMap(struct));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }

    /**
     * 帐号同步短信验证码确认发送
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/thirdpartyaccount/bind/confirm", method = RequestMethod.POST)
    @ResponseBody
    public String bindConfirm(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = new ValidateUtil();
            vu.addIntTypeValidate("account_id", params.get("account_id"), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("user_id", params.get("user_id"), null, null, 1, Integer.MAX_VALUE);
            vu.addRequiredValidate("confirm", params.get("confirm"));
            logger.info("确认发送短信验证码:{}", JSON.toJSONString(params));
            HrThirdPartyAccountDO struct = userHrAccountService.bindConfirm(params.getInt("user_id"), params.getInt("account_id"), params.getBoolean("confirm"));
            return ResponseLogNotification.successJson(request, thirdpartyAccountToMap(struct));
        } catch (Exception e) {
            return ResponseLogNotification.failJson(request, e);
        }
    }

    /**
     * 帐号同步发送短信验证码
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/thirdpartyaccount/bind/message", method = RequestMethod.POST)
    @ResponseBody
    public String bindMessage(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = new ValidateUtil();
            vu.addIntTypeValidate("account_id", params.get("account_id"), null, null, 1, Integer.MAX_VALUE);
            vu.addIntTypeValidate("user_id", params.get("user_id"), null, null, 1, Integer.MAX_VALUE);
            vu.addRequiredValidate("code", params.get("code"));
            logger.info("发送短信验证码:{}", JSON.toJSONString(params));
            HrThirdPartyAccountDO struct = userHrAccountService.bindMessage(params.getInt("user_id"), params.getInt("account_id"), params.getString("code"));
            return ResponseLogNotification.successJson(request, thirdpartyAccountToMap(struct));
        } catch (Exception e) {
            return ResponseLogNotification.failJson(request, e);
        }
    }


    @RequestMapping(value = "/thirdpartyaccount/refresh", method = RequestMethod.GET)
    @ResponseBody
    public String synchronizeThirdpartyAccount(HttpServletRequest request, HttpServletResponse response) {
        logger.info("/thirdpartyaccount/refresh start : {}", new DateTime().toString("YYYY-MM-dd HH:mm:ss SSS"));
        long startTime = System.currentTimeMillis();
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer id = params.getInt("id");
            if (id == null) {
                return ResponseLogNotification.fail(request, "id不能为空");
            }

            Integer userId = params.getInt("user_id", 0);

            if (userId == null) {
                return ResponseLogNotification.fail(request, "user_id不能为空");
            }

            HrThirdPartyAccountDO hrThirdPartyAccountDO = userHrAccountService.syncThirdPartyAccount(userId, id, params.getBoolean("sync", false));

            return ResponseLogNotification.success(request, ResponseUtils.success(thirdpartyAccountToMap(hrThirdPartyAccountDO)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            logger.info("/thirdpartyaccount/refresh start : {}", new DateTime().toString("YYYY-MM-dd HH:mm:ss SSS"));
            long allUseTime = System.currentTimeMillis() - startTime;
            logger.info("refresh thirdParyAccount in controller Use time===========================" + allUseTime);
        }
    }

    @RequestMapping(value = "/thirdpartyaccount/unbind", method = RequestMethod.POST)
    @ResponseBody
    public String unbindThirdPartyAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer accountId = params.getInt("account_id");
            if (accountId == null) {
                return ResponseLogNotification.fail(request, "account_id不能为空");
            }

            Integer userId = params.getInt("user_id", 0);

            if (userId == null) {
                return ResponseLogNotification.fail(request, "user_id不能为空");
            }

            userHrAccountService.unbindThirdPartyAccount(accountId, userId);

            return ResponseLogNotification.successJson(request, 1);
        } catch (Exception e) {
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/thirdpartyaccount/dispatch", method = RequestMethod.POST)
    @ResponseBody
    public String dispatchThirdPartyAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer accountId = params.getInt("account_id");
            if (accountId == null) {
                return ResponseLogNotification.fail(request, "account_id不能为空");
            }

            List<Integer> hrIds = (List<Integer>) params.get("hr_ids");

            if (hrIds == null) {
                return ResponseLogNotification.fail(request, "hr_ids不能为空");
            }

            ThirdPartyAccountInfo accountInfo = userHrAccountService.dispatchThirdPartyAccount(accountId, hrIds);

            return ResponseLogNotification.successJson(request, accountInfo);
        } catch (Exception e) {
            return ResponseLogNotification.failJson(request, e);
        }
    }

    /**
     * 获取hr常用筛选项
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/searchcondition", method = RequestMethod.GET)
    @ResponseBody
    public String getSearchCondition(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrAccountId = params.getInt("hr_account_id");
            int type = params.getInt("type", 0);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("hr账号", hrAccountId);
            if (StringUtils.isNullOrEmpty(vu.validate())) {
                Response result = userHrAccountService.getSearchCondition(hrAccountId, type);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 保存hr常用筛选项
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/searchcondition", method = RequestMethod.POST)
    @ResponseBody
    public String postSearchCondition(HttpServletRequest request, HttpServletResponse response) {
        try {
            SearchCondition searchCondition = ParamUtils.initModelForm(request, SearchCondition.class);
            ValidateUtil vu = new ValidateUtil();
            vu.addIntTypeValidate("hr账号", searchCondition.getHr_account_id(), null, "hr账号不能为空", 1, null);
            // 筛选名长度不能大于24字节
            if (searchCondition.getName() == null || "".equals(searchCondition.getName().trim()) || searchCondition.getName().getBytes("GBK").length > 24) {
                return ResponseLogNotification.fail(request, "筛选名不能超过24个字节");
            }
            if (StringUtils.isNullOrEmpty(vu.validate())) {
                Response result = userHrAccountService.postSearchCondition(searchCondition);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 删除hr常用筛选项
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/searchcondition", method = RequestMethod.DELETE)
    @ResponseBody
    public String DelSearchCondition(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrAccountId = params.getInt("hr_account_id");
            Integer id = params.getInt("id");
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("hr账号", hrAccountId, null, null);
            vu.addRequiredValidate("筛选项id", id, null, null);
            if (StringUtils.isNullOrEmpty(vu.validate())) {
                Response result = userHrAccountService.delSearchCondition(hrAccountId, id);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 将候选人加入人才库
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/talentpool/in", method = RequestMethod.POST)
    @ResponseBody
    public String joinTalentpool(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrAccountId = params.getInt("hr_account_id");
            List<Integer> applierIds = (List<Integer>) params.get("applier_ids");
            if (applierIds == null || applierIds.isEmpty()) {
                return ResponseLogNotification.fail(request, "候选人id为空");
            }
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("hr账号", hrAccountId, null, null);
            if (StringUtils.isNullOrEmpty(vu.validate())) {
                Response result = userHrAccountService.joinTalentpool(hrAccountId, applierIds);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 将候选人移出人才库
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/talentpool/out", method = RequestMethod.POST)
    @ResponseBody
    public String shiftOutTalentpool(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrAccountId = params.getInt("hr_account_id");
            List<Integer> applierIds = (List<Integer>) params.get("applier_ids");
            if (applierIds == null || applierIds.isEmpty()) {
                return ResponseLogNotification.fail(request, "候选人id为空");
            }
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("hr账号", hrAccountId, null, null);
            if (StringUtils.isNullOrEmpty(vu.validate())) {
                Response result = userHrAccountService.shiftOutTalentpool(hrAccountId, applierIds);
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 分页获取userHrAccount数据
     * 默认：可用账号 disable:1
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/userhraccount", method = RequestMethod.GET)
    @ResponseBody
    public String userHrAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer companyId = params.getInt("company_id");
            if (companyId == null) {
                return ResponseLogNotification.fail(request, "company_id为空");
            }
            int disable = params.getInt("disable", 1);
            int page = params.getInt("page", 1);
            int per_age = params.getInt("per_age", 20);
            Response result = userHrAccountService.userHrAccount(companyId, disable, page, per_age);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/nps/list", method = RequestMethod.GET)
    @ResponseBody
    public String npsList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            logger.info("/nps/list params:{}", params);
            ValidateUtil vu = new ValidateUtil();
            vu.addDateValidate("start_date", params.get("start_date"), DateType.shortDate, null, null);
            vu.addDateValidate("end_date", params.get("end_date"), DateType.shortDate, null, null);

            Integer page = params.getInt("page", 1);
            Integer pageSize = params.getInt("page_size", 500);

            if (StringUtils.isNullOrEmpty(vu.validate())) {
                HrNpsStatistic result = userHrAccountService.npsList(params.getString("start_date"), params.getString("end_date"), page, pageSize);
                logger.info("/nps/list result:{}", result);
                return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result)));
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/nps/status", method = RequestMethod.GET)
    @ResponseBody
    public String npsStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            logger.info("/nps/status params:{}", params);
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("user_id", params.get("user_id"), null, null);
            vu.addIntTypeValidate("user_id", params.get("user_id"), null, null, 0, Integer.MAX_VALUE);
            vu.addDateValidate("start_date", params.get("start_date"), DateType.shortDate, null, null);
            vu.addDateValidate("end_date", params.get("end_date"), DateType.shortDate, null, null);
            if (StringUtils.isNullOrEmpty(vu.validate())) {
                HrNpsResult result = userHrAccountService.npsStatus(params.getInt("user_id"), params.getString("start_date"), params.getString("end_date"));
                logger.info("/nps/status result:{}", result);
                return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result)));
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/nps/update", method = RequestMethod.POST)
    @ResponseBody
    public String npsUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            HrNpsUpdate npsUpdate = ParamUtils.initModelForm(request, HrNpsUpdate.class);
            logger.info("/nps/update params:{}", JSON.toJSON(npsUpdate));
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("user_id", npsUpdate.getUser_id(), null, null);
            if (StringUtils.isNullOrEmpty(vu.validate())) {
                HrNpsResult result = userHrAccountService.npsUpdate(npsUpdate);
                logger.info("/nps/update result:{}", JSON.toJSON(result));
                return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result)));
            } else {
                return ResponseLogNotification.fail(request, vu.validate());
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    //获取第三方账号列表
    @RequestMapping(value = "/hraccount/thirdparty", method = RequestMethod.GET)
    @ResponseBody
    public String getThirdPartyAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonQuery commonQuery = ParamUtils.initCommonQuery(request, CommonQuery.class);
            List<HrThirdPartyAccountDO> result = userHrAccountService.getThirdPartyAccounts(commonQuery);
            return ResponseLogNotification.success(request, ResponseUtils.success(result));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    //更新第三方账号
    @RequestMapping(value = "/hraccount/thirdparty", method = RequestMethod.PUT)
    @ResponseBody
    public String updateThirdPartyAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            HrThirdPartyAccountDO thirdPartyAccount = ParamUtils.initModelForm(request, HrThirdPartyAccountDO.class);
            if (!thirdPartyAccount.isSetId()) {
                if (!thirdPartyAccount.isSetId()) {
                    return ResponseLogNotification.fail(request, "id不能为空");
                }
            }
            int result = userHrAccountService.updateThirdPartyAccount(thirdPartyAccount);
            return ResponseLogNotification.success(request, ResponseUtils.success(result));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    // ------------------------------------- 以下接口为hr_354新增---------------------------------------


    /**
     * 员工取消认证 (支持批量操作)
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/unbind", method = RequestMethod.PUT)
    @ResponseBody
    public String unbindEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<Integer> ids = (ArrayList<Integer>) params.get("ids");
            int companyId = params.getInt("companyId", 0);
            if (ids == null || ids.isEmpty()) {
                return ResponseLogNotification.fail(request, "Ids不能为空");
            } else {
                // 权限判断
                Boolean permission = userHrAccountService.permissionJudgeWithUserEmployeeIdsAndCompanyId(ids, companyId);
                if (!permission) {
                    return ResponseLogNotification.fail(request, ConstantErrorCodeMessage.PERMISSION_DENIED);
                }
                boolean result = userHrAccountService.unbindEmployee(ids);
                return ResponseLogNotification.success(request, ResponseUtils.success(new HashMap<String, Object>() {{
                    put("result", result);
                }}));
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 删除员工 (支持批量操作)
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employee", method = RequestMethod.POST)
    @ResponseBody
    public String removeEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<Integer> ids = (ArrayList<Integer>) params.get("ids");
            int companyId = params.getInt("companyId", 0);
            if (ids == null || ids.isEmpty()) {
                return ResponseLogNotification.fail(request, "Ids不能为空");
            } else {
                // 权限判断
                Boolean permission = userHrAccountService.permissionJudgeWithUserEmployeeIdsAndCompanyId(ids, companyId);
                if (!permission) {
                    return ResponseLogNotification.failResponse(request, ConstantErrorCodeMessage.PERMISSION_DENIED);
                }
                boolean result = userHrAccountService.delEmployee(ids);
                return ResponseLogNotification.success(request, ResponseUtils.success(new HashMap<String, Object>() {{
                    put("result", result);
                }}));
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 获取员工积分列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/rewards", method = RequestMethod.GET)
    @ResponseBody
    public String getEmployeeRawards(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Params<String, Object> params = ParamUtils.parseRequestParam(request);
        int employeeId = params.getInt("employeeId", 0);
        int companyId = params.getInt("companyId", 0);
        int pageNumber = params.getInt("pageNumber", 0);
        int pageSize = params.getInt("pageSize", 0);
        if (employeeId == 0) {
            return ResponseLogNotification.fail(request, "员工Id不能为空");
        } else {
            // 权限判断
            Boolean permission = userHrAccountService.permissionJudgeWithUserEmployeeIdAndCompanyId(employeeId, companyId);
            if (!permission) {
                return ResponseLogNotification.failResponse(request, ConstantErrorCodeMessage.PERMISSION_DENIED);
            }
            RewardVOPageVO result = userHrAccountService.getEmployeeRewards(employeeId, companyId, pageNumber, pageSize);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result)));
        }

    }


    /**
     * 添加员工积分
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/reward/add", method = RequestMethod.PUT)
    @ResponseBody
    public String addEmployeeReward(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int employeeId = params.getInt("employeeId", 0);
            int points = params.getInt("points");
            String reason = params.getString("reason");
            int companyId = params.getInt("companyId", 0);
            if (employeeId == 0) {
                return ResponseLogNotification.fail(request, "员工Id不能为空");
            } else {
                // 权限判断
                Boolean permission = userHrAccountService.permissionJudgeWithUserEmployeeIdAndCompanyId(employeeId, companyId);
                if (!permission) {
                    return ResponseLogNotification.failResponse(request, ConstantErrorCodeMessage.PERMISSION_DENIED);
                }
                int result = userHrAccountService.addEmployeeReward(employeeId, companyId, points, reason);
                return ResponseLogNotification.success(request, ResponseUtils.success(new HashMap<String, Integer>() {{
                    put("totalPoint", result);
                }}));
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 获取列表number
     * 通过公司ID,查询认证员工和未认证员工数量
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/number", method = RequestMethod.GET)
    @ResponseBody
    public String getListNum(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String keyWord = params.getString("keyword");
            int companyId = params.getInt("companyId", 0);
            UserEmployeeNumStatistic userEmployeeNumStatistic = userHrAccountService.getListNum(keyWord, companyId);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(userEmployeeNumStatistic)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 员工列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employees", method = RequestMethod.GET)
    @ResponseBody
    public String employeeList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String keyWord = params.getString("keyword", "");
            int companyId = params.getInt("companyId", 0);
            int filter = params.getInt("filter", 0);
            String order = params.getString("order", "");
            String asc = params.getString("asc", "");
            String timeSpan = params.getString("timeSpan", "");
            int pageNumber = params.getInt("pageNumber", 0);
            int pageSize = params.getInt("pageSize", 0);
            UserEmployeeVOPageVO userEmployeeVOPageVO = userHrAccountService.employeeList(keyWord, companyId, filter, order, asc, pageNumber, pageSize, timeSpan);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(userEmployeeVOPageVO)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 员工信息导出
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/export", method = RequestMethod.POST)
    @ResponseBody
    public String employeeExport(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            int type = params.getInt("type", 0);
            List<Integer> userEmployees = (List<Integer>) params.get("userEmployees");
            List<UserEmployeeVO> userEmployeeVOS = userHrAccountService.employeeExport(userEmployees, companyId, type);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(userEmployeeVOS)));

        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 员工信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/details", method = RequestMethod.GET)
    @ResponseBody
    public String employeeDetails(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userEmployeeId = params.getInt("userEmployeeId", 0);
            int companyId = params.getInt("companyId", 0);
            UserEmployeeDetailVO userEmployeeDetailVO = userHrAccountService.userEmployeeDetail(userEmployeeId, companyId);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(userEmployeeDetailVO)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 更新公司员工信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/update", method = RequestMethod.PUT)
    @ResponseBody
    public String updateUserEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userEmployeeId = params.getInt("userEmployeeId", 0);
            String cname = params.getString("cname", "");
            String mobile = params.getString("mobile", "");
            String email = params.getString("email", "");
            String customField = params.getString("customField", "");
            int companyId = params.getInt("companyId", 0);
            Response res = userHrAccountService.updateUserEmployee(cname, mobile, email, customField, userEmployeeId, companyId);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 检查员工重复(批量导入之前验证)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/checkbatchinsert", method = RequestMethod.POST)
    @ResponseBody
    public String checkBatchInsert(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            Map userEmployees = UserHrAccountParamUtils.parseUserEmployeeDO((List<HashMap<String, Object>>) params.get("userEmployees"));
            ImportUserEmployeeStatistic res = userHrAccountService.checkBatchInsert(userEmployees, companyId);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(res)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 员工信息导入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/import", method = RequestMethod.POST)
    @ResponseBody
    public String employeeImport(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            int type = params.getInt("type", 0);
            int hraccountId = params.getInt("hraccountId", 0);
            String fileName = params.getString("fileName", "");
            String filePath = params.getString("filePath", "");
            Map userEmployees = UserHrAccountParamUtils.parseUserEmployeeDO((List<HashMap<String, Object>>) params.get("userEmployees"));
            Response res = userHrAccountService.employeeImport(userEmployees, companyId, filePath, fileName, type, hraccountId);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
