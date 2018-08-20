package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.PaginationUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.useraccounts.form.ApplyTypeAwardFrom;
import com.moseeker.servicemanager.web.controller.useraccounts.form.LeaderBoardTypeForm;
import com.moseeker.servicemanager.web.controller.useraccounts.vo.ContributionDetail;
import com.moseeker.servicemanager.web.controller.useraccounts.vo.LeaderBoardInfo;
import com.moseeker.servicemanager.web.controller.useraccounts.vo.LeaderBoardType;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyReferralConfDO;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.EmployeeResponse;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by eddie on 2017/3/7.
 */
@Controller
@CounterIface
public class UserEmployeeController {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    UserEmployeeService.Iface service = ServiceManager.SERVICEMANAGER.getService(UserEmployeeService.Iface.class);

    EmployeeService.Iface employeeService =  ServiceManager.SERVICEMANAGER.getService(EmployeeService.Iface.class);

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public UserEmployeeController(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }

    @RequestMapping(value = "/user/employee", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUserEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonQuery commonQuery = ParamUtils.initCommonQuery(request, CommonQuery.class);
            if (commonQuery.getEqualFilter() == null) commonQuery.setEqualFilter(new HashMap<>());
            String companyId = commonQuery.getEqualFilter().get("company_id");
            String customField = commonQuery.getEqualFilter().get("custom_field");
            String id = commonQuery.getEqualFilter().get("id");
            if (StringUtils.isNullOrEmpty(id)) {
                if (StringUtils.isNullOrEmpty(companyId)) {
                    return ResponseLogNotification.fail(request, "company_id不能为空");
                } else if (StringUtils.isNullOrEmpty(customField)) {
                    return ResponseLogNotification.fail(request, "custom_field不能为空");
                }
            }
            Response result = service.delUserEmployee(commonQuery);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/user/employee", method = RequestMethod.GET)
    @ResponseBody
    public String getUserEmployees(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonQuery commonQuery = ParamUtils.initCommonQuery(request, CommonQuery.class);
            Response result = service.getUserEmployees(commonQuery);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/user/employee/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getUserEmployee(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
        try {
            CommonQuery query = new CommonQuery();
            query.setEqualFilter(new HashMap<String, String>() {{
                put("id", String.valueOf(id));
            }});
            Response result = service.getUserEmployee(query);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/user/employee/batchhandler", method = RequestMethod.POST)
    @ResponseBody
    public String addOrUpdateUserEmployees(HttpServletRequest request, HttpServletResponse response) {
        UserEmployeeBatchForm batchForm = null;
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            batchForm = JSON.parseObject(JSON.toJSONString(params), UserEmployeeBatchForm.class);
            if (batchForm == null || batchForm.getData() == null || batchForm.getData().size() == 0) {
                return ResponseLogNotification.fail(request, "没有参数");
            }

            if (batchForm.isDel_not_include() && batchForm.getCompany_id() == 0) {
                return ResponseLogNotification.fail(request, "使用del_not_include参数必须指定company_id");
            }

            Response result = service.postPutUserEmployeeBatch(batchForm);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value="/user/employee/check", method = RequestMethod.GET)
    @ResponseBody
    public String checkUserIsEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("userId", 0);
            int companyId = params.getInt("companyId", 0);
            if (companyId == 0) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else if (userId == 0) {
                return ResponseLogNotification.fail(request, "员工Id不能为空");
            } else {
                boolean result = service.isEmployee(userId, companyId);
                return ResponseLogNotification.success(request, ResponseUtils.success(new HashMap<String, Object>(){{put("result", result);}}));
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value="/user/employee", method = RequestMethod.PUT)
    @ResponseBody
    public String putUserEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserEmployeeStruct userEmployee=ParamUtils.initModelForm(request, UserEmployeeStruct.class);
            Response res=service.putUserEmployee(userEmployee);
            return ResponseLogNotification.success(request,res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value="/employee/award", method = RequestMethod.POST)
    @ResponseBody
    public String addAward(HttpServletRequest request, @RequestBody ApplyTypeAwardFrom form) {
        try {
            service.addEmployeeAward(form.getApplicationIds(), form.getEventType());
            return ResponseLogNotification.successJson(request,"success");
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
     获取
     */
    @RequestMapping(value="/api/talentpool/forward/employee", method = RequestMethod.GET)
    @ResponseBody
    public String getEmailValidate(HttpServletRequest request,HttpServletResponse response) {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId=Integer.parseInt((String)params.get("company_id"));
            String email=(String)params.get("email");
            String page=(String)params.get("page_num");
            String pageSize=(String)params.get("page_size");
            if(StringUtils.isNullOrEmpty(page)){
                page="1";
            }
            if(StringUtils.isNullOrEmpty(pageSize)){
                pageSize="10";
            }
            Response res=service.getValidateUserEmployee(companyId,email,Integer.parseInt(page),Integer.parseInt(pageSize));
            return ResponseLogNotification.success(request,res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
    获取最近转发人员
     */
    @RequestMapping(value="/api/talentpool/forward/employee/history", method = RequestMethod.GET)
    @ResponseBody
    public String getUserEmployeePast(HttpServletRequest request,  HttpServletResponse response) {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            int hrId=Integer.parseInt((String)params.get("hr_id"));
            Response res=service.getPastUserEmployee(hrId);
            return ResponseLogNotification.success(request,res);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    解绑员工
     */
    @RequestMapping(value="/user/employee/unbind", method = RequestMethod.POST)
    @ResponseBody
    public String unbind(HttpServletRequest request,  HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);
            int companyId = params.getInt("company_id", 0);
            if (companyId == 0) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else if (userId == 0) {
                return ResponseLogNotification.fail(request, "员工Id不能为空");
            } else {
                EmployeeResponse employee = employeeService.getEmployee(userId,companyId);

                if(employee.employee ==null || employee.employee.id <=0){
                    return ResponseLogNotification.fail(request, "员工不存在");
                }

                Result result = employeeService.unbind(employee.employee.id,userId, companyId);
                if(!result.success){
                    return ResponseLogNotification.fail(request, result.getMessage());
                }
                return ResponseLogNotification.successJson(request, result.employeeId);
            }
        } catch (BIZException e){
            return ResponseLogNotification.failJson(request,e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    解绑员工
     */
    @RequestMapping(value="/user/employee/bind", method = RequestMethod.POST)
    @ResponseBody
    public String bind(HttpServletRequest request,  HttpServletResponse response) {
        try {
            Params<String, Object> param = ParamUtils.parseRequestParam(request);

            // 处理一下type，必须是int类型
            int type = param.getInt("type");
            param.put("type",type);

            BindingParams bindingParams = new JSONObject(){{
                putAll(param);
            }}.toJavaObject(BindingParams.class);

            Result result = employeeService.bind(bindingParams);
            if(!result.success){
                return ResponseLogNotification.fail(request, result.getMessage());
            }
            return ResponseLogNotification.successJson(request, result.employeeId);
        } catch (BIZException e){
            return ResponseLogNotification.failJson(request,e);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /*
    获取最近转发人员
     */
    @RequestMapping(value="/v1.0/employee/rank", method = RequestMethod.GET)
    @ResponseBody
    public String getContribution(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("company_id", 0);
            int pageSize = params.getInt("page_size", 0);
            int pageNo = params.getInt("page_no", 0);

            ValidateUtil validateUtil = new ValidateUtil();
            validateUtil.addIntTypeValidate("公司", companyId, null, null, 1,
                    null);
            if (org.apache.commons.lang.StringUtils.isNotBlank(validateUtil.validate())) {
                return ResponseLogNotification.failJson(request, validateUtil.getResult());

            } else {
                PaginationUtil<ContributionDetail> result = new PaginationUtil<>();
                com.moseeker.thrift.gen.useraccounts.struct.Pagination pagination = service.getContributions(companyId,
                        pageNo, pageSize);

                result.setPageNum(pagination.getPageNum());
                result.setPageSize(pagination.getPageSize());
                result.setTotalRow(pagination.getTotalRow());

                //数据转换
                if (pagination.getDetails() != null && pagination.getDetails().size() > 0) {
                    List<ContributionDetail> contributionDetails = pagination.getDetails()
                            .stream()
                            .map(employeeReferralContribution -> {
                                ContributionDetail contributionDetail = new ContributionDetail();
                                org.springframework.beans.BeanUtils.copyProperties(employeeReferralContribution,
                                        contributionDetail);
                                return contributionDetail;
                            }).collect(Collectors.toList());
                    result.setList(contributionDetails);
                }
                return com.moseeker.servicemanager.web.controller.Result.success(result).toJson();
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value="/v1.0/referral/conf", method = RequestMethod.POST)
    @ResponseBody
    public String updateReferralConf(HttpServletRequest request,  HttpServletResponse response) {
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            HrCompanyReferralConfDO conf = JSON.parseObject(JSON.toJSONString(params), HrCompanyReferralConfDO.class);
            ValidateUtil vu = new ValidateUtil();
            if(StringUtils.isNotNullOrEmpty(conf.getText())){
                logger.info("===============text:{}",conf.getText());
                vu.addSensitiveValidate("內推文案", conf.getText(), null, "文章中不能含有敏感词");
                vu.addStringLengthValidate("內推文案", conf.getText(), null, "文章的长度过长", 0, 5001);
            }
            if(StringUtils.isNotNullOrEmpty(conf.getLink())){
                logger.info("===============text:{}",conf.getLink());
                vu.addRegExpressValidate("內推链接", conf.getLink(), "^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$",null, "请输入正确的链接");
                vu.addStringLengthValidate("內推链接", conf.getLink(), null, "链接长度过长", 0, 501);
            }
            vu.addIntTypeValidate("内推政策优先级", (int)conf.getPriority(), null, null, 0,3);
            String message = vu.validate();
            logger.info("===============message:{}",message);
            if(StringUtils.isNullOrEmpty(message)) {
                employeeService.upsertCompanyReferralConf(conf);
                return ResponseLogNotification.successJson(request, null);
            }else{
                return ResponseLogNotification.failJson(request, message);
            }
        } catch (BIZException e){
            return ResponseLogNotification.fail(request,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value="/v1.0/referral/policy", method = RequestMethod.POST)
    @ResponseBody
    public String updateReferralPolicy(HttpServletRequest request,  HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId = params.getInt("user_id", 0);
            int companyId = params.getInt("company_id", 0);
            if (companyId == 0) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else if (userId == 0) {
                return ResponseLogNotification.fail(request, "员工Id不能为空");
            } else {
                employeeService.updsertCompanyReferralPocily(companyId, userId);
                return ResponseLogNotification.successJson(request, null);
            }
        } catch (BIZException e){
            return ResponseLogNotification.fail(request,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value="/v1.0/referral/conf", method = RequestMethod.GET)
    @ResponseBody
    public String getReferralConf(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("company_id", 0);
            if (companyId == 0) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            }else {
                Response confDO = employeeService.getCompanyReferralConf(companyId);
                return ResponseLogNotification.success(request,confDO);
            }
        } catch (BIZException e){
            return ResponseLogNotification.fail(request,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value="/v1/employee/{id}/count-upvote", method = RequestMethod.GET)
    @ResponseBody
    public String countUpVote(@PathVariable int id, HttpServletRequest request) throws Exception {

        if(org.apache.commons.lang.StringUtils.isBlank(request.getParameter("appid"))) {
            throw CommonException.PROGRAM_APPID_REQUIRED;
        }
        return com.moseeker.servicemanager.web.controller.Result.success(employeeService.countUpVote(id)).toJson();
    }

    @RequestMapping(value="/v1/employee/{id}/upvote/{colleague}", method = RequestMethod.POST)
    @ResponseBody
    public String upVote(@PathVariable int id, @PathVariable int colleague, HttpServletRequest request) throws Exception {

        ParamUtils.parseRequestParam(request);
        return com.moseeker.servicemanager.web.controller.Result.success(employeeService.upvote(id, colleague)).toJson();
    }

    @RequestMapping(value="/v1/employee/{id}/upvote/{colleague}", method = RequestMethod.DELETE)
    @ResponseBody
    public String cancelUpVote(@PathVariable int id, @PathVariable int colleague, HttpServletRequest request) throws Exception {

        ParamUtils.parseRequestParam(request);
        employeeService.removeUpvote(id, colleague);
        return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
    }

    @RequestMapping(value="/v1/employee/{id}/recent-upvote", method = RequestMethod.GET)
    @ResponseBody
    public String countRecentUpVote(@PathVariable int id, HttpServletRequest request) throws Exception {

        if(org.apache.commons.lang.StringUtils.isBlank(request.getParameter("appid"))) {
            throw CommonException.PROGRAM_APPID_REQUIRED;
        }
        return com.moseeker.servicemanager.web.controller.Result.success(employeeService.countRecentUpVote(id)).toJson();
    }

    @RequestMapping(value="/v1/employee/{id}/list-info", method = RequestMethod.GET)
    @ResponseBody
    public String leaderBoardInfo(@PathVariable int id, HttpServletRequest request) throws Exception {

        Params<String, Object> params = ParamUtils.parseRequestParam(request);
        int type = params.getInt("type", 0);
        LeaderBoardInfo leaderBoardInfo = LeaderBoardInfo.instanceFrom(employeeService.fetchLeaderBoardInfo(id, type));
        return com.moseeker.servicemanager.web.controller.Result.success(leaderBoardInfo).toJson();
    }

    @RequestMapping(value="/v1/employee/{id}/last-list-info", method = RequestMethod.GET)
    @ResponseBody
    public String lastLeaderBoardInfo(@PathVariable int id, HttpServletRequest request) throws Exception {

        Params<String, Object> params = ParamUtils.parseRequestParam(request);
        int type = params.getInt("type", 0);
        LeaderBoardInfo leaderBoardInfo = LeaderBoardInfo.instanceFrom(employeeService.fetchLastLeaderBoardInfo(id, type));
        return com.moseeker.servicemanager.web.controller.Result.success(leaderBoardInfo).toJson();
    }

    @RequestMapping(value="/v1/company/{id}/leader-board", method = RequestMethod.GET)
    @ResponseBody
    public String getLeaderBoardType(@PathVariable int id, HttpServletRequest request) throws Exception {

        ParamUtils.parseRequestParam(request);
        LeaderBoardType leaderBoardType = LeaderBoardType.instanceFrom(employeeService.fetchLeaderBoardType(id));
        return com.moseeker.servicemanager.web.controller.Result.success(leaderBoardType).toJson();
    }

    @RequestMapping(value="/v1/company/{id}/leader-board", method = RequestMethod.PATCH)
    @ResponseBody
    public String updateLeaderBoardType(@PathVariable int id, @RequestBody LeaderBoardTypeForm form) throws Exception {

        if (form.getAppid() <= 0) {
            throw CommonException.PROGRAM_APPID_REQUIRED;
        }
        employeeService.updateLeaderBoardType(id, form.getType());
        return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
    }

    @RequestMapping(value="/v1/company/{id}/employees-count", method = RequestMethod.GET)
    @ResponseBody
    public String countEmployee(@PathVariable int id, HttpServletRequest request) throws Exception {

        ParamUtils.parseRequestParam(request);
        return com.moseeker.servicemanager.web.controller.Result.success(employeeService.countEmplyee(id)).toJson();
    }

    @RequestMapping(value="/v1/employee/upvotes", method = RequestMethod.DELETE)
    @ResponseBody
    public String clearUpVoteWeekly(@PathVariable int id, HttpServletRequest request) throws Exception {

        ParamUtils.parseRequestParam(request);
        employeeService.clearUpVoteWeekly();
        return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
    }
}
