package com.moseeker.servicemanager.web.controller.useraccounts;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.useraccounts.form.UserEmployeeBatch;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserEmployeeService;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by eddie on 2017/3/7.
 */
@Controller
@CounterIface
public class UserEmployeeController {

    UserEmployeeService.Iface service = ServiceManager.SERVICEMANAGER.getService(UserEmployeeService.Iface.class);

    @RequestMapping(value = "/user/employee", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUserEmployee(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> filter = ParamUtils.parseRequestParam(request).entrySet().stream()
                    .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().toString()))
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
            CommonQuery query = new CommonQuery();
            query.setEqualFilter(filter);
            Response result = service.delUserEmployee(query);
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
            query.setEqualFilter(new HashMap<String, String>(){{put("id", String.valueOf(id));}});
            Response result = service.getUserEmployee(query);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/user/employee/batchhandler", method = RequestMethod.POST)
    @ResponseBody
    public String addOrUpdateUserEmployees(HttpServletRequest request, HttpServletResponse response) {
        UserEmployeeBatch batch = null;
        try {
            Map<String, Object> params = ParamUtils.parseRequestParam(request);
            batch = JSON.parseObject(JSON.toJSONString(params), UserEmployeeBatch.class);
            if (batch == null || batch.getData() == null || batch.getData().size() == 0) {
                return ResponseLogNotification.fail(request, "没有参数");
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, "参数错误");
        }
        try {
            Response result = service.postPutUserEmployeeBatch(batch.getData());
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
            int userId = params.getInt("userId");
            int compnayId = params.getInt("compnayId");
            if (compnayId == 0) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else if (userId == 0) {
                return ResponseLogNotification.fail(request, "员工Id不能为空");
            } else {
                boolean result = service.isEmployee(userId, compnayId);
                return ResponseLogNotification.success(request, ResponseUtils.success(new HashMap<String, Object>(){{put("result", result);}}));
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
