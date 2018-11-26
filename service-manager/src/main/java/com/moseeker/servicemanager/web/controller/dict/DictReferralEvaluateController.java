package com.moseeker.servicemanager.web.controller.dict;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictConstanService;
import com.moseeker.thrift.gen.dict.service.DictReferralEvaluteService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 字典常量服务
 * <p>
 *
 * Created by zzh on 16/5/27.
 */
// @Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class DictReferralEvaluateController {


	Logger logger = org.slf4j.LoggerFactory.getLogger(CityController.class);

	DictReferralEvaluteService.Iface service = ServiceManager.SERVICEMANAGER
			.getService(DictReferralEvaluteService.Iface.class);

	@RequestMapping(value = "/dict/constant", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		try {
            Map<String,Object> params = ParamUtils.parseRequestParam(request);
            if(params.get("code")!=null){
                Response result = service.getDictReferralEvalute((Integer)params.get("code"));
                return ResponseLogNotification.success(request, result);
            }else {
                return ResponseLogNotification.fail(request, "code参数有误！");
            }

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
