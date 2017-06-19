package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.RespnoseUtil;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.common.validation.rules.DateType;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.struct.*;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

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

    @RequestMapping(value = "/hraccount/binding", method = RequestMethod.POST)
    @ResponseBody
    public String bindThirdPartyAccount(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            HrThirdPartyAccountDO struct = ParamUtils.initModelForm(params,HrThirdPartyAccountDO.class);
            logger.info("bind thirdParyAccount in controller params===========================" + JSON.toJSONString(struct));
            struct = userHrAccountService.bindThirdpartyAccount(params.getInt("user_id", 0), struct);
            //同步情况下走下面的代码
            return ResponseLogNotification.success(request, ResponseUtils.success(struct));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
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

            HrThirdPartyAccountDO hrThirdPartyAccountDO = userHrAccountService.syncThirdpartyAccount(id);

            return ResponseLogNotification.success(request, ResponseUtils.success(hrThirdPartyAccountDO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            logger.info("/thirdpartyaccount/refresh start : {}", new DateTime().toString("YYYY-MM-dd HH:mm:ss SSS"));
            long allUseTime = System.currentTimeMillis() - startTime;
            logger.info("refresh thirdParyAccount in controller Use time===========================" + allUseTime);
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
}
