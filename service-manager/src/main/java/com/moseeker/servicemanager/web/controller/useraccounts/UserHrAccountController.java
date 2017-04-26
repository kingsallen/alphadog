package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import com.moseeker.thrift.gen.useraccounts.struct.DownloadReport;
import com.moseeker.thrift.gen.useraccounts.struct.SearchCondition;
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
            BindAccountStruct struct = ParamUtils.initModelForm(request, BindAccountStruct.class);
            logger.info("bind thirdParyAccount in controller params==========================="+ JSON.toJSONString(struct));
            Response result = userHrAccountService.bind(struct);
            logger.info("bind thirdParyAccount in controller end==========================="+result.getData());
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            //do nothing
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

    @RequestMapping(value = "/thirdpartyaccount/refresh", method = RequestMethod.GET)
    @ResponseBody
    public String synchronizeThirdpartyAccount(HttpServletRequest request, HttpServletResponse response) {
        logger.info("/thirdpartyaccount/refresh start : {}", new DateTime().toString("YYYY-MM-dd HH:mm:ss SSS"));
        long startTime= System.currentTimeMillis();
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer userId = params.getInt("id");
            if (userId == null) {
                return ResponseLogNotification.fail(request, "id不能为空");
            }

            Response result = userHrAccountService.synchronizeThirdpartyAccount(userId);

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }finally{
            logger.info("/thirdpartyaccount/refresh start : {}", new DateTime().toString("YYYY-MM-dd HH:mm:ss SSS"));
            long allUseTime=System.currentTimeMillis()-startTime;
            logger.info("refresh thirdParyAccount in controller Use time==========================="+allUseTime);
        }
    }
}
