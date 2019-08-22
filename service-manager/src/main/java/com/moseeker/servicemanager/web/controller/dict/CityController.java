package com.moseeker.servicemanager.web.controller.dict;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.servicemanager.common.ParamUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CityServices;

import java.util.List;
import java.util.Map;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class CityController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(CityController.class);

	CityServices.Iface cityServices = ServiceManager.SERVICE_MANAGER.getService(CityServices.Iface.class);

	@RequestMapping(value = "/dict/cities", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {
            String parameterLevel = request.getParameter("level");
            int level = parameterLevel == null ? 0 : Integer.parseInt(parameterLevel);
            Response result = cityServices.getAllCities(level);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/v2.0/dict/cities", method = RequestMethod.GET)
    @ResponseBody
    public String getDictCity(HttpServletRequest request, HttpServletResponse response) {
        try {
            String level = request.getParameter("level");
            String parameterUsing = request.getParameter("is_using");
            String parameterHotCity = request.getParameter("hot_city");
            int using = -1;
            if(parameterUsing != null){
                using = Integer.parseInt(parameterUsing);
            }
            int hotcity = -1;
            if(parameterHotCity != null){
                hotcity = Integer.parseInt(parameterHotCity);
            }
            Response result = cityServices.getAllCitiesByLevelOrUsing(level, using, hotcity);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

	@RequestMapping(value = "/dict/cities/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getById(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			Response result = cityServices.getCitiesById((int) id);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

    @RequestMapping(value = "/dict/provinceandcitys", method = RequestMethod.GET)
    @ResponseBody
    public String getProvinceAndCitys( HttpServletRequest request, HttpServletResponse response) {
        try {
            Response result = cityServices.getProvinceAndCity();
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    @RequestMapping(value = "/dict/province/citycode", method = RequestMethod.GET)
    @ResponseBody
    public String getProvinceCityCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            String cityCodes= (String) data.get("codes");
            List<Integer> codeList=ParamUtils.convertIntList(cityCodes);
            if(StringUtils.isEmptyList(codeList)){
                return ResponseLogNotification.fail(request, "省份code不能为空");
            }
            Response result = cityServices.getCityByProvince(codeList);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
