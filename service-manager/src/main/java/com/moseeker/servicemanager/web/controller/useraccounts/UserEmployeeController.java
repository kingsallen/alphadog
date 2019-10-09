package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.PaginationUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.pojos.RadarUserInfo;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.useraccounts.form.*;
import com.moseeker.servicemanager.web.controller.useraccounts.vo.*;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyReferralConfDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.EmployeeResponse;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import com.moseeker.thrift.gen.useraccounts.struct.EmployeeForwardViewPage;
import com.moseeker.thrift.gen.useraccounts.struct.RadarInfo;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.moseeker.common.constants.Constant.EMPLOYEE_ACTIVATION_UNEMPLOYEE;

/**
 * Created by eddie on 2017/3/7.
 */
@Controller
@CounterIface
public class UserEmployeeController {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    UserEmployeeService.Iface service = ServiceManager.SERVICE_MANAGER.getService(UserEmployeeService.Iface.class);

    EmployeeService.Iface employeeService =  ServiceManager.SERVICE_MANAGER.getService(EmployeeService.Iface.class);


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
        Result result = null ;
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            logger.debug("POST /user/employee/unbind params:{}",params);
            int userId = params.getInt("user_id", 0);
            int companyId = params.getInt("company_id", 0);
            byte activationChange = params.getByte("activationChange",(byte)1);
            if (companyId == 0) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else if (userId == 0) {
                return ResponseLogNotification.fail(request, "员工Id不能为空");
            } else {
                EmployeeResponse employee = employeeService.getEmployee(userId,companyId);

                if(employee.employee ==null || employee.employee.id <=0){
                    return ResponseLogNotification.fail(request, "员工不存在");
                }

                switch (activationChange){
                    case EMPLOYEE_ACTIVATION_UNEMPLOYEE: result = employeeService.unemploy(employee.employee.id,userId, companyId);break;
                    default: result = employeeService.unbind(employee.employee.id,userId, companyId);break;
                }
                logger.debug("/user/employee/unbind result : {}",result);
                if(result == null || !result.success){
                    return ResponseLogNotification.fail(request, result.getMessage());
                }
                return ResponseLogNotification.successJson(request, result.employeeId);
            }
        } catch (BIZException e){
            logger.error("POST /user/employee/unbind error",e);
            return ResponseLogNotification.failJson(request,e);
        } catch (Exception e) {
            logger.error("POST /user/employee/unbind error",e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 员工认证
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/user/employee/bind", method = RequestMethod.POST)
    @ResponseBody
    public String bind(HttpServletRequest request,  HttpServletResponse response) {
        try {
            Params<String, Object> param = ParamUtils.parseRequestParam(request);
            logger.debug("POST /user/employee/bind params: {}",param);
            // 处理一下type，必须是int类型
            int type = param.getInt("type");
            param.put("type",type);

            //获取来源
            int bindSource = Integer.parseInt(param.get("appid").toString());
            BindingParams bindingParams = new JSONObject(){{
                putAll(param);
            }}.toJavaObject(BindingParams.class);
            Result result = employeeService.bind(bindingParams,bindSource);
            logger.debug("POST /user/employee/bind result:{}",result);
            if(!result.success){
                return ResponseLogNotification.fail(request, result.getMessage());
            }
            return ResponseLogNotification.successJson(request, result.employeeId);
        } catch (BIZException e){
            logger.error("POST /user/employee/bind error",e);
            return ResponseLogNotification.failJson(request,e);
        } catch (Exception e) {
            logger.error("POST /user/employee/bind error",e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 重新发送邮件
     * @param request
     * @return
     */
    @RequestMapping(value="/employee/resend/bind/email", method = RequestMethod.POST)
    @ResponseBody
    public String retrySendVerificationMail(HttpServletRequest request) throws Exception {

        Params<String, Object> param = ParamUtils.parseRequestParam(request);

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("用户", param.getInt("user_id"));
        validateUtil.addRequiredValidate("公司", param.getInt("company_id"));
        String result = validateUtil.validate();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(result)) {
            return ResponseLogNotification.fail(request, result);
        }

        //获取来源
        int source = 0 ;
        if(param.get("source") != null && StringUtils.isNotNullOrEmpty(param.get("source").toString())){
            source = Integer.parseInt(param.get("source").toString());
        }
        if(source == 0){
            source = Integer.parseInt(param.get("appid").toString());
        }
        employeeService.retrySendVerificationMail(param.getInt("user_id"),param.getInt("company_id"), source);
        return ResponseLogNotification.successJson(request, "SUCCESS");
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
                                logger.info("getContribution contributionDetail companyId:{}, openid:{}, userId:{}, " +
                                        "point:{}, rank:{}",
                                        contributionDetail.getCompanyId(), contributionDetail.getOpenid(),
                                        contributionDetail.getUserId(), contributionDetail.getPoint(),
                                        contributionDetail.getRank());
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
            }
            if(StringUtils.isNotNullOrEmpty(conf.getLink())){
                logger.info("===============text:{}",conf.getLink());
                vu.addRegExpressValidate("內推链接", conf.getLink(), FormCheck.getUrlExp(),null, "请输入正确的链接");
                vu.addStringLengthValidate("內推链接", conf.getLink(), null, "链接长度过长", 0, 2001);
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
    public String clearUpVoteWeekly(HttpServletRequest request) throws Exception {

        ParamUtils.parseRequestParam(request);
        employeeService.clearUpVoteWeekly();
        return com.moseeker.servicemanager.web.controller.Result.success(true).toJson();
    }

    @RequestMapping(value="/v1/employee/custom-field-values", method = RequestMethod.PATCH)
    @ResponseBody
    public String updateCustomFieldValues(@RequestBody CustomFieldValuesForm customFieldValuesForm) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("用户", customFieldValuesForm.getUserId());
        validateUtil.addRequiredValidate("公司", customFieldValuesForm.getCompanyId());
        validateUtil.addRequiredValidate("项目编号", customFieldValuesForm.getAppid());
        validateUtil.addRequiredOneValidate("补填信息", customFieldValuesForm.getCustomFieldValues());
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            employeeService.patchEmployeeCustomFieldValues(customFieldValuesForm.getUserId(),
                    customFieldValuesForm.getCompanyId(),
                    customFieldValuesForm.getCustomFieldValues()
                            .stream()
                            .collect(Collectors.toMap(EmployeeExtInfo::getId, EmployeeExtInfo::getOptions)));
            return com.moseeker.servicemanager.web.controller.Result.success("success").toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.validateFailed(result).toJson();
        }
    }

    @RequestMapping(value="/employee/addpoint", method = RequestMethod.POST)
    @ResponseBody
    public String userEmployeeAddPoint(HttpServletRequest request,  HttpServletResponse response) throws Exception {
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId= (int) params.get("company_id");
            int employeeId= (int) params.get("employee_id");
            UserEmployeePointsRecordDO data=JSON.parseObject(JSON.toJSONString(params.get("data")),UserEmployeePointsRecordDO.class);
            Response res=service.addUserEmployeePointRecord(employeeId,companyId,data);
            return ResponseLogNotification.successJson(request, res);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request,e.getMessage());
        }
    }

    @RequestMapping(value="/employee/list", method = RequestMethod.POST)
    @ResponseBody
    public String getUserEmployee(HttpServletRequest request,  HttpServletResponse response) throws Exception {
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId= (int) params.get("company_id");
            List<Integer> userIdList= (List<Integer>) params.get("user_ids");
            UserEmployeePointsRecordDO data=JSON.parseObject(JSON.toJSONString(params.get("data")),UserEmployeePointsRecordDO.class);
            Response res=service.getUserEmployeeList(companyId,userIdList);
            return ResponseLogNotification.successJson(request, res);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request,e.getMessage());
        }
    }
    @RequestMapping(value="/employee/user", method = RequestMethod.GET)
    @ResponseBody
    public String getUserEmployeeByUserId(HttpServletRequest request,  HttpServletResponse response) throws Exception {
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int userId=  Integer.parseInt(params.getString("user_id"));
            Response res=service.getUserEmployeeByuserId(userId);
            return ResponseLogNotification.successJson(request, res);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request,e.getMessage());
        }
    }
    @RequestMapping(value="/employee/user/company", method = RequestMethod.POST)
    @ResponseBody
    public String getUserEmployeeByCidListAndUserIdList(HttpServletRequest request,  HttpServletResponse response)throws Exception{
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<Integer> userIdList= (List<Integer>) params.get("userId");
            List<Integer> companyIdList= (List<Integer>) params.get("companyId");
            Response res=service.getUserEmployeeByUserIdListAndCompanyList(userIdList,companyIdList);
            return ResponseLogNotification.successJson(request, res);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request,e.getMessage());
        }
    }

    @RequestMapping(value="/employee/user/updateFromWorkwx", method = RequestMethod.POST)
    @ResponseBody
    public String updateEmployeeFromWorkwx(HttpServletRequest request,  HttpServletResponse response)throws Exception{
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            List<Integer> userIds= JSONArray.parseArray(params.getString("userIds"),int.class);
            int companyId =  Integer.parseInt(params.getString("companyId"));
            service.batchUpdateEmployeeFromWorkwx(userIds,companyId);
            return com.moseeker.servicemanager.web.controller.Result.SUCCESS;
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request,e.getMessage());
        }
    }


    @RequestMapping(value="/v1/contact/referral/info", method = RequestMethod.GET)
    @ResponseBody
    public String getPositionReferralInfo(@RequestParam("appid") int appid,
                                          @RequestParam("user_id") int userId,
                                          @RequestParam("position_id") int positionId) throws Exception {

        if (org.apache.commons.lang.StringUtils.isBlank(String.valueOf(appid))) {
            throw CommonException.PROGRAM_APPID_LOST;
        }
        com.moseeker.thrift.gen.useraccounts.struct.PositionReferralInfo info = service.getPositionReferralInfo(userId, positionId);
        PositionReferralInfo result = new PositionReferralInfo();
        logger.info("getPositionReferralInfo info:{}",info);
        BeanUtils.copyProperties(info, result);
        logger.info("getPositionReferralInfo result:{}",JSON.toJSONString(result));
        return com.moseeker.servicemanager.web.controller.Result.success(result).toJson();

    }

    @RequestMapping(value="/v1/radar/data", method = RequestMethod.GET)
    @ResponseBody
    public String fetchRadarIndexData(@RequestParam("appid") int appid, @RequestParam("company_id") int companyId,
                                      @RequestParam("user_id") int userId, @RequestParam( "page")int page,
                                      @RequestParam("size") int size
                                      ) throws Exception {

        if (org.apache.commons.lang.StringUtils.isBlank(String.valueOf(appid))) {
            throw CommonException.PROGRAM_APPID_LOST;
        }
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("员工user编号", userId);
        validateUtil.addRequiredValidate("公司编号", companyId);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            RadarInfo radarInfo = service.fetchRadarIndex(userId, companyId, page, size);
            return com.moseeker.servicemanager.web.controller.Result.success(copyRadarInfoVO(radarInfo)).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.validateFailed(result).toJson();
        }
    }

    @RequestMapping(value="/v1/employee/seek/recommend/card", method = RequestMethod.GET)
    @ResponseBody
    public String fetchSeekRecommendData(@RequestParam("appid") int appid, @RequestParam("company_id") int companyId,
                                      @RequestParam("user_id") int userId, @RequestParam(value = "page", defaultValue = "1")int page,
                                      @RequestParam(value = "size", defaultValue = "5") int size
    ) throws Exception {

        if (org.apache.commons.lang.StringUtils.isBlank(String.valueOf(appid))) {
            throw CommonException.PROGRAM_APPID_LOST;
        }
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("员工user编号", userId);
        validateUtil.addRequiredValidate("公司编号", companyId);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            RadarInfo radarInfo = service.fetchEmployeeSeekRecommendPage(userId, companyId, page, size);
            return com.moseeker.servicemanager.web.controller.Result.success(copyRadarInfoVO(radarInfo)).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.validateFailed(result).toJson();
        }
    }

    private RadarInfoVO copyRadarInfoVO(RadarInfo radarInfo){
        RadarInfoVO infoVO = new RadarInfoVO();
        if(!com.moseeker.common.util.StringUtils.isEmptyList(radarInfo.getUserList())){
            infoVO.setUserList( radarInfo.getUserList().stream().map(m ->{
                RadarUserVO userInfo = new RadarUserVO();
                BeanUtils.copyProperties(m, userInfo);
                return userInfo;
            }).collect(Collectors.toList()));
            infoVO.setPage(radarInfo.getPage());
            infoVO.setTotalCount(radarInfo.getTotalCount());
        }
        return infoVO;
    }

    @RequestMapping(value="/v1/employee/position/view", method = RequestMethod.GET)
    @ResponseBody
    public String fetchRadarIndexData(@RequestParam("appid") int appid, @RequestParam("company_id") int companyId,
                                      @RequestParam("user_id") int userId, @RequestParam(value = "page", defaultValue = "1")int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                      @RequestParam(value = "position_title", defaultValue = "") String positionTitle,
                                      @RequestParam(value = "order", defaultValue = "time") String order
    ) throws Exception {

        if (org.apache.commons.lang.StringUtils.isBlank(String.valueOf(appid))) {
            throw CommonException.PROGRAM_APPID_LOST;
        }
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("员工user编号", userId);
        validateUtil.addRequiredValidate("公司编号", companyId);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isBlank(result)) {
            EmployeeForwardViewPage viewPage = service.fetchEmployeeForwardView(userId, companyId, positionTitle, order, page, size);
            EmployeeForwardViewVO viewVO = new EmployeeForwardViewVO();
            viewVO.setPage(viewPage.getPage());
            viewVO.setTotalCount(viewPage.getTotalCount());
            if(!com.moseeker.common.util.StringUtils.isEmptyList(viewPage.getUserList())){
                List<EmployeeForwardViewPageVO> forwardViews = new ArrayList<>();
                viewPage.getUserList().forEach(view -> {
                    EmployeeForwardViewPageVO forwardView = new EmployeeForwardViewPageVO();
                    BeanUtils.copyProperties(view, forwardView);
                    if(!com.moseeker.common.util.StringUtils.isEmptyList(view.getChain())){
                        List<RadarUserInfo> connectionList = new ArrayList<>();
                        view.getChain().forEach(chain -> {
                            RadarUserInfo connection = new RadarUserInfo();
                            BeanUtils.copyProperties(chain, connection);
                            connectionList.add(connection);
                        });
                        forwardView.setChain(connectionList);
                    }
                    forwardViews.add(forwardView);
                });
                viewVO.setUserList(forwardViews);
            }
            return com.moseeker.servicemanager.web.controller.Result.success(viewVO).toJson();
        } else {
            return com.moseeker.servicemanager.web.controller.Result.validateFailed(result).toJson();
        }
    }


    /**
     * 中骏员工信息导入
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/sftp/employee/import", method = RequestMethod.POST)
    @ResponseBody
    public String atsEmployeeImport(HttpServletRequest request, @RequestBody  EmployeeBatchImportForm form) {
        logger.info("中骏员工信息导入 initDateTime");
        ValidateUtil validateUtil = new ValidateUtil();
        List<UserEmployeeDO> employeeDOS = form.getEmployees();
        validateUtil.addRequiredValidate("公司编号", employeeDOS);
        validateUtil.addRequiredOneValidate("员工信息列表",form.getEmployees());
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            return com.moseeker.servicemanager.web.controller.Result.validateFailed(result).toJson();
        }
        LocalDateTime initDateTime = LocalDateTime.now();
        try {
            Response res = employeeService.employeeSftpImport(form.getCompanyId(),employeeDOS);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            logger.error("中骏员工信息导入 异常",e);
            return ResponseLogNotification.fail(request, e.getMessage());
        } finally {
            logger.info("中骏员工信息导入 Duration:{}", Duration.between(initDateTime, LocalDateTime.now()).toMillis());
        }
    }

}
