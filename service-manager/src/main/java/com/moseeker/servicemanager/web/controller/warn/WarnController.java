package com.moseeker.servicemanager.web.controller.warn;

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
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.warn.service.WarnSetService;
import com.moseeker.thrift.gen.warn.struct.WarnBean;
@Controller
@CounterIface
public class WarnController {
	Logger logger = org.slf4j.LoggerFactory.getLogger(WarnController.class);

	WarnSetService.Iface warnService = ServiceManager.SERVICE_MANAGER
			.getService(WarnSetService.Iface.class);
	@RequestMapping(value = "/sendWarn", method = RequestMethod.POST)
	@ResponseBody
	public String sendWarn(HttpServletRequest request, HttpServletResponse response){
		
		Response result =new Response();
		try{
			WarnBean bean=ParamUtils.initModelForm(request, WarnBean.class);
			warnService.sendOperator(bean);
			result.setStatus(0);
			result.setMessage("send success");
			return ResponseLogNotification.success(request, result);
		}catch(Exception e){
			e.printStackTrace();
			result.setStatus(1);
			result.setMessage("send faile");
			return ResponseLogNotification.success(request, result);
		}
	}

}
