package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by eddie on 2017/3/7.
 */
@Controller
public class UserEmployeeController {

    UserEmployeeService.Iface service = ServiceManager.SERVICEMANAGER.getService(UserEmployeeService.Iface.class);

    @RequestMapping(value = "/user/employee", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUserEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> filter = request.getParameterMap().entrySet().stream()
                    .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()[0]))
                    .collect(Collectors.toMap((t -> t.getKey()), (s -> s.getValue())));
            String companyId = filter.get("company_id");
            String customField = filter.get("custom_field");
            String id = filter.get("id");
            if (StringUtils.isNullOrEmpty(id)) {
                if (StringUtils.isNullOrEmpty(companyId)) {
                    return ResponseLogNotification.fail(request, "company_id不能为空");
                } else if (StringUtils.isNullOrEmpty(customField)) {
                    return ResponseLogNotification.fail(request, "custom_field不能为空");
                }
            }
            Response result = service.delUserEmployee(filter);
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
    public String getUserEmployees(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
        try {
            QueryUtil queryUtil = new QueryUtil();
            queryUtil.addEqualFilter("id",String.valueOf(id));
            Response result = service.getUserEmployee(queryUtil);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/user/employee/batchhandler", method = RequestMethod.POST)
    @ResponseBody
    public String addOrUpdateUserEmployees(HttpServletRequest request, HttpServletResponse response) {
        try {
            String raw = ParamUtils.getStringRaw(request);

            raw = StringUtils.isNotNullOrEmpty(raw)?request.getParameter("data"):raw;

            if(StringUtils.isNullOrEmpty(raw) ){
                return ResponseLogNotification.fail(request, "没有参数");
            }

            List<UserEmployeeStruct> employeeStructs = new ArrayList<>();
            if(raw.startsWith("[")) {
                employeeStructs = JSON.parseArray(raw, UserEmployeeStruct.class);
            }else if(raw.startsWith("{")){
                employeeStructs.add(JSON.parseObject(raw,UserEmployeeStruct.class));
            }

            Response result = service.postPutUserEmployeeBatch(employeeStructs);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
