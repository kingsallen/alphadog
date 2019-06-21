package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.common.validation.rules.DateType;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.service.HRService;
import com.moseeker.servicemanager.service.vo.HRInfo;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrAppExportFieldsDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.employee.struct.RewardVOPageVO;
import com.moseeker.thrift.gen.profile.service.ProfileOtherThriftService;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    ProfileOtherThriftService.Iface profileOtherService = ServiceManager.SERVICEMANAGER.getService(ProfileOtherThriftService.Iface.class);

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public UserHrAccountController() {
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @Autowired
    private HRService hrService;


    /**
     * 更新手机号
     */
    @RequestMapping(value = "/hraccount/mobile", method = RequestMethod.PUT)
    @ResponseBody
    public String updateMobile(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params params = ParamUtils.parseRequestParam(request);
            Integer hrId = params.getInt("hrId");
            String mobile = params.getString("mobile");

            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("hr帐号", hrId, null, null);
            vu.addRequiredStringValidate("手机号", mobile, null, null);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                userHrAccountService.updateMobile(hrId, mobile);
                return ResponseLogNotification.successJson(request, true);
            } else {
                return ResponseLogNotification.failJson(request, message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.failJson(request, e);
        }
    }



    /**
     * 添加子账号记录
     */
    @RequestMapping(value = "/hraccount/add", method = RequestMethod.POST)
    @ResponseBody
    public String addAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            ValidateUtil vu = new ValidateUtil();
            vu.addSensitiveValidate("用户名称", params.getString("username"), null, null);
            vu.addStringLengthValidate("用户名称", params.getString("username"), null, null, 0, 60);
            vu.addRequiredValidate("手机号码", params.getString("mobile"), null, null);
            vu.addStringLengthValidate("手机号码", params.getString("mobile"), null, null, 0, 30);
            vu.addStringLengthValidate("邮箱", params.getString("email"), null, null, 0, 50);
            vu.addStringLengthValidate("密码", params.getString("password"), null, null, 0, 64);
            vu.addIntTypeValidate("来源", params.get("source"), null, null, 0, 99);
            vu.addIntTypeValidate("登录次数", params.get("loginCount"), null, null, 0, Integer.MAX_VALUE);
            vu.addIntTypeValidate("账号类型", params.get("accountType"), null, null, 0, 9);
            vu.addStringLengthValidate("注册IP", params.get("registerIp"), null, null, 0, 50);
            vu.addStringLengthValidate("最后登录IP", params.get("lastLoginIp"), null, null, 0, 50);
            vu.addRequiredValidate("公司", params.get("companyId"), null, null);
            vu.addIntTypeValidate("公司", params.get("companyId"), null, null, 0, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                UserHrAccountDO accountForm = ParamUtils.initModelForm(params, UserHrAccountDO.class);
                return ResponseLogNotification.successJson(request, userHrAccountService.addAccount(accountForm));
            } else {
                return ResponseLogNotification.failJson(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.failJson(request, e);
        }
    }

    /**
     * 检查是否允许添加子账号
     */
    @RequestMapping(value = "/hraccount/allowadd", method = RequestMethod.GET)
    @ResponseBody
    public String ifAddSubAccountAllowed(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params params = ParamUtils.parseRequestParam(request);
            Integer hrId = params.getInt("hrId");
            ValidateUtil vu = new ValidateUtil();
            vu.addRequiredValidate("hr帐号", hrId, null, null);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                return ResponseLogNotification.successJson(request, userHrAccountService.ifAddSubAccountAllowed(hrId));
            } else {
                return ResponseLogNotification.failJson(request, message);
            }
        } catch (Exception e) {
            return ResponseLogNotification.failJson(request, e);
        }
    }

    /**
     * 添加子账号
     */
    @RequestMapping(value = "/hraccount/subaccount", method = RequestMethod.POST)
    @ResponseBody
    public String addSubAccount(HttpServletRequest request) {
        try {
            UserHrAccountDO accountForm = ParamUtils.initModelForm(request, UserHrAccountDO.class);
            int userHRAccountId = userHrAccountService.addSubAccount(accountForm);
            return ResponseLogNotification.successJson(request, userHRAccountId);
        } catch (Exception e) {
            return ResponseLogNotification.failJson(request, e);
        }
    }

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
            logger.info("bind thirdParyAccount in controller params===========================" + JSON.toJSONString(struct));
            struct = userHrAccountService.bindThirdPartyAccount(params.getInt("user_id", 0), struct, params.getBoolean("sync", true));
            logger.info("bind third party account struct {}", struct);
            if (struct.getBinding() == 100) {
                struct.setBinding(Integer.valueOf(0).shortValue());
                Map<String, Object> result = thirdpartyAccountToMap(struct);
                result.put("mobile", struct.getErrorMessage());
                return ResponseLogNotification.failJson(request, 100, "需要验证手机号", result);
            } else {
                String result = ResponseLogNotification.successJson(request, thirdpartyAccountToMap(struct));
                logger.info("bind third party account result {}", result);
                return result;
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

            throw new UnsupportedOperationException("Abandoned function!!!");
//            HrThirdPartyAccountDO hrThirdPartyAccountDO = userHrAccountService.syncThirdPartyAccount(userId, id, params.getBoolean("sync", false));

//            return ResponseLogNotification.success(request, ResponseUtils.success(thirdpartyAccountToMap(hrThirdPartyAccountDO)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
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

    @RequestMapping(value = "/thirdpartyaccount", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteThirdPartyAccount(HttpServletRequest request, HttpServletResponse response) {
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

            userHrAccountService.deleteThirdPartyAccount(accountId, userId);

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
            logger.info("dispath account_id : {},hr_ids : {}", accountId, hrIds);

            if (hrIds == null) {
                return ResponseLogNotification.fail(request, "hr_ids不能为空");
            }

            ThirdPartyAccountInfo accountInfo = userHrAccountService.dispatchThirdPartyAccount(accountId, hrIds);

            logger.info("dispatch result : {}", accountInfo);

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
    @RequestMapping(value = "/v1/employees", method = RequestMethod.GET)
    @ResponseBody
    public String employeeList(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String keyWord = params.getString("keyword", "");
            int companyId = params.getInt("companyId", 0);
            int filter = params.getInt("filter", 0);
            String order = params.getString("order", "");
            String asc = params.getString("asc", "");
            String email_isvalid = params.getString("email_isvalid", "");
            int pageNumber = params.getInt("pageNumber", 0);
            int pageSize = params.getInt("pageSize", 0);
            int balanceType = params.getInt("balanceType",0);
            String timespan = params.getString("timespan", "");
            String selectedIids = params.getString("selectedIds");

            logger.info("UserHrAccountController employeeList params:{}", JSONObject.toJSONString(params));
            UserEmployeeVOPageVO userEmployeeVOPageVO = userHrAccountService.getEmployees(keyWord, companyId, filter,
                    order, asc, pageNumber, pageSize, email_isvalid,balanceType, timespan, selectedIids);
            return ResponseLogNotification.success(request,
                    ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(userEmployeeVOPageVO)));
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
    public String getEmployees(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String keyWord = params.getString("keyword", "");
            int companyId = params.getInt("companyId", 0);
            int filter = params.getInt("filter", 0);
            String order = params.getString("order", "");
            String asc = params.getString("asc", "");
            String timeSpan = params.getString("timespan", "");
            String email_isvalid = params.getString("email_isvalid", "");
            int pageNumber = params.getInt("pageNumber", 0);
            int pageSize = params.getInt("pageSize", 0);
            logger.info("UserHrAccountController getEmployees timeSpan:{}", timeSpan);
            UserEmployeeVOPageVO userEmployeeVOPageVO = userHrAccountService.employeeList(keyWord, companyId, filter, order, asc, pageNumber, pageSize, timeSpan, email_isvalid);
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
     * warnings:目前发现没有调用方调用该接口。员工导出接口调用的是@UserHrAccountController.employeeList接口用于获取员工数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/employee/export", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
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
            String customFieldValues = null;
            if (params.get("customFieldValues") != null) {
                List t = (List) params.get("customFieldValues");
                customFieldValues = JSONObject.toJSONString(t);
            }
            int companyId = params.getInt("companyId", 0);
            Response res = userHrAccountService.updateUserEmployee(cname, mobile, email, customField, userEmployeeId, companyId, customFieldValues);
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
            Map<Integer, UserEmployeeDO> userEmployees = UserHrAccountParamUtils.parseUserEmployeeDO((List<HashMap<String, Object>>) params.get("userEmployees"));
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
            Map<Integer, UserEmployeeDO> userEmployees = UserHrAccountParamUtils.parseUserEmployeeDO((List<HashMap<String, Object>>) params.get("userEmployees"));
            Response res = userHrAccountService.employeeImport(userEmployees, companyId, filePath, fileName, type, hraccountId);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 员工信息导入修改
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/employee/updatemsg/batch", method = RequestMethod.POST)
    @ResponseBody
    public String batchUpdate(HttpServletRequest request) throws Exception {
        Params<String, Object> params = ParamUtils.parseRequestParam(request);
        Integer companyId = params.getInt("companyId", 0);
        Integer type = params.getInt("type", 0);
        Integer hraccountId = params.getInt("hraccountId", 0);
        String fileName = params.getString("fileName", "");
        String filePath = params.getString("filePath", "");
        logger.info("userEmployees:{}", params.get("userEmployees"));

        List<UserEmployeeDO> userEmployees = UserHrAccountParamUtils.parseEmployees((List<Map<String, Object>>)params.get("userEmployees"));

        ImportUserEmployeeStatistic importUserEmployeeStatistic = userHrAccountService.updateEmployee(userEmployees, companyId, filePath, fileName, type, hraccountId);
        return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(importUserEmployeeStatistic)));
    }

    /**
     * 获取自定义字段元数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/hraccount/custom/metadata", method = RequestMethod.GET)
    @ResponseBody
    public String getCustomMetaData(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            boolean selectAll = params.getBoolean("selectAll", false);
            return ResponseLogNotification.success(request, profileOtherService.getCustomMetaData(companyId, selectAll));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 获取自定义申请导出字段
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/hraccount/export/fields", method = RequestMethod.GET)
    @ResponseBody
    public String getExportFields(HttpServletRequest request) {
        try {
            ValidateUtil vu = new ValidateUtil();
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            int userHrAccountId = params.getInt("userHrAccountId", 0);
            vu.addIntTypeValidate("companyId", companyId, null, null, 1, Integer.MAX_VALUE);
            String message = vu.validate();
            if (StringUtils.isNullOrEmpty(message)) {
                List<HrAppExportFieldsDO> resultList = userHrAccountService.getExportFields(companyId, userHrAccountId);
                return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(resultList)));
            } else {
                return ResponseLogNotification.failJson(request, message);
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 获取自定义申请导出字段
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/hraccount/company/info", method = RequestMethod.GET)
    @ResponseBody
    public String getHrAccountCompanyInfo(HttpServletRequest request) {
        try {
            ValidateUtil vu = new ValidateUtil();
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int account_id = params.getInt("accountId", 0);
            int wechat_id = params.getInt("wechatId", 41);
            String unionId = params.getString("unionId");
            Response res = userHrAccountService.getHrCompanyInfo(wechat_id, unionId, account_id);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 设置HR聊天是否托管给智能招聘助手
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/hraccount/leveltomobot", method = RequestMethod.PUT)
    @ResponseBody
    public String switchChatLeaveToMobot(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int account_id = params.getInt("account_id");
            byte leave_to_mobot = params.getByte("leave_to_mobot");
            UserHrAccountDO res = userHrAccountService.switchChatLeaveToMobot(account_id, leave_to_mobot);

            //驼峰转下划线
            UserHrAccount underLineResult = JSON.parseObject(JSON.toJSONString(res, serializeConfig, SerializerFeature.DisableCircularReferenceDetect), UserHrAccount.class);
            //转换json的时候去掉thrift结构体中的set方法
            JSONObject jsonResult = JSON.parseObject(BeanUtils.convertStructToJSON(underLineResult));

            return ResponseLogNotification.successJson(request, jsonResult);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/hraccount/getThirdPartyAccountDO", method = RequestMethod.POST)
    @ResponseBody
    public String getThirdPartyAccountDO(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int channel = params.getInt("channel");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("hr账号的指定渠道", channel, null, null);
            validateUtil.addIntTypeValidate("hr账号的指定渠道", channel, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isNullOrEmpty(message)) {
                Response response = userHrAccountService.getThirdPartyAccountDO(channel);
                if (null != response) {
                    return ResponseLogNotification.success(request, response);
                }
            } else {
                logger.info("==================message:{}================", message);
                return ResponseLogNotification.fail(request, message);
            }
        } catch (BIZException e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseLogNotification.failJson(request, "后台异常");
    }

    @RequestMapping(value = "/hraccount/getUnBindThirdPartyAccountDO", method = RequestMethod.POST)
    @ResponseBody
    public String getUnBindThirdPartyAccountDO(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int channel = params.getInt("channel");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("hr账号的指定渠道", channel, null, null);
            validateUtil.addIntTypeValidate("hr账号的指定渠道", channel, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isNullOrEmpty(message)) {
                List<HrThirdPartyAccountDO> hrAccountList = userHrAccountService.getUnBindThirdPartyAccountDO(channel);
                if (null != hrAccountList) {
                    return ResponseLogNotification.successJson(request, hrAccountList);
                }
            } else {
                logger.info("==================message:{}================", message);
                return ResponseLogNotification.fail(request, message);
            }
        } catch (BIZException e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseLogNotification.failJson(request, "后台异常");
    }

    @RequestMapping(value = "/hraccount/saveLiepinAccount", method = RequestMethod.POST)
    @ResponseBody
    public String bindLiepinAccount(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer hrThirdAccountId = params.getInt("hr_third_account_id");
            String liepinToken = params.getString("liepin_token");
            Integer liepinUserId = params.getInt("liepin_user_id");
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("hr第三方账号id", hrThirdAccountId, null, null);
            validateUtil.addIntTypeValidate("hr第三方账号id", hrThirdAccountId, null, null, 1, Integer.MAX_VALUE);
            validateUtil.addRequiredValidate("hr第三方账号绑定返回的token", liepinToken, null, null);
            validateUtil.addStringLengthValidate("hr第三方账号绑定返回的token", liepinToken, null, null, 1, 255);
            validateUtil.addRequiredValidate("hr第三方账号绑定返回的猎聘用户id", liepinUserId, null, null);
            validateUtil.addIntTypeValidate("hr第三方账号绑定返回的猎聘用户id", liepinUserId, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isNullOrEmpty(message)) {
                String result = userHrAccountService.bindLiepinUserAccount(liepinToken, liepinUserId, hrThirdAccountId);
                if (StringUtils.isNotNullOrEmpty(result)) {
                    if ("success".equals(result)) {
                        return ResponseLogNotification.successJson(request, result);
                    } else {
                        return ResponseLogNotification.failJson(request, result);
                    }

                } else {
                    return ResponseLogNotification.failJson(request, "保存猎聘账号信息失败");
                }

            } else {
                logger.info("==================message:{}================", message);
                return ResponseLogNotification.fail(request, message);
            }
        } catch (BIZException e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseLogNotification.failJson(request, "后台异常");
    }

    @RequestMapping(value = "/hr/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getHR(@PathVariable int id, HttpServletRequest request) throws Exception {
        try {
            HRInfo hrInfo = hrService.getHR(id);
            return Result.success(hrInfo).toJson();
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.failJson(request, "后台异常");
        }
    }


    /**
     * 招聘管理-查询是否需要二次提醒
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/hr/application/notice", method = RequestMethod.GET)
    @ResponseBody
    public String getApplicationNotice(HttpServletRequest request) {
        try {
            ValidateUtil vu = new ValidateUtil();
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int account_id = params.getInt("hr_account_id", 0);
            Response res = userHrAccountService.getApplicationNotify(account_id);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 招聘管理-设置是否需要二次提醒
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/v1/hr/application/notice", method = RequestMethod.PUT)
    @ResponseBody
    public String putApplicationNotice(HttpServletRequest request) {
        try {
            ValidateUtil vu = new ValidateUtil();
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int account_id = params.getInt("hr_account_id", 0);
            boolean flag = params.getBoolean("flag", true);

            Response res = userHrAccountService.setApplicationNotify(account_id,flag);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/v1/hrAccount/getJob58BindResult", method = RequestMethod.GET)
    @ResponseBody
    public String getThirdPartyAccountBindResult(HttpServletRequest request) {
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String key = params.getString("ext2");
            int channel = params.getInt("channel", 0);
            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addRequiredValidate("58账号key", key, null, null);
            validateUtil.addStringLengthValidate("58账号key", key, null, null, 0, 500);
            validateUtil.addRequiredValidate("渠道channel", channel, null, null);
            validateUtil.addIntTypeValidate("渠道channel", channel, null, null, 1, Integer.MAX_VALUE);

            String message = validateUtil.validate();

            if (StringUtils.isNullOrEmpty(message)) {
                HrThirdPartyAccountDO accountBindResult=userHrAccountService.getJob58BindResult(channel, key);
                int status = accountBindResult.getBinding();
                JSONObject result = new JSONObject();
                result.put("status", status);
                return ResponseLogNotification.successJson(request, result);
            } else {
                logger.info("==================message:{}================", message);
                return ResponseLogNotification.fail(request, message);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return ResponseLogNotification.failJson(request, e);
        }
    }
}
