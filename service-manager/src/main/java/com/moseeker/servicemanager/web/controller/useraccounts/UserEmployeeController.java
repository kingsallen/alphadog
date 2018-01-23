package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.useraccounts.form.ApplyTypeAwardFrom;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeBatchForm;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by eddie on 2017/3/7.
 */
@Controller
@CounterIface
public class UserEmployeeController {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    UserEmployeeService.Iface service = ServiceManager.SERVICEMANAGER.getService(UserEmployeeService.Iface.class);

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
}
