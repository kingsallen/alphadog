package com.moseeker.servicemanager.web.controller.dict;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CollegeServices;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class CollegeController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(CollegeController.class);

	CollegeServices.Iface collegeServices = ServiceManager.SERVICE_MANAGER.getService(CollegeServices.Iface.class);

	@RequestMapping(value = "/dict/college/all", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		try {

			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			Response result = collegeServices.getResources(query);
			return ResponseLogNotification.success(request, result);

		} catch (Exception e) {

			return ResponseLogNotification.fail(request, e.getMessage());

		}
	}

    /**
     * 获取
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/dict/college", method = RequestMethod.GET)
    @ResponseBody
    public String getCollegeByDomestic(HttpServletRequest request, HttpServletResponse response) {
        try {
            // GET方法 通用参数解析并赋值
            Response result = collegeServices.getCollegeByDomestic();
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 根绝国家code获取国家对应的学校信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/dict/college/abroad", method = RequestMethod.GET)
    @ResponseBody
    public String getCollegeByAborad(HttpServletRequest request, HttpServletResponse response) {
        try {
            String parameterLevel = request.getParameter("country_id");
            int country_id = parameterLevel == null ? 0 : Integer.parseInt(parameterLevel);
            Response result = collegeServices.getCollegeByAbroad(country_id);
            return ResponseLogNotification.success(request, result);

        } catch (Exception e) {

            return ResponseLogNotification.fail(request, e.getMessage());

        }
    }
}
