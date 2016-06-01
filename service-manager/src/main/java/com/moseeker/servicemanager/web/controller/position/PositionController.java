package com.moseeker.servicemanager.web.controller.position;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class PositionController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(PositionController.class);

    PositionServices.Iface positonServices = ServiceUtil.getService(PositionServices.Iface.class);

    @RequestMapping(value = "/positions", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {
            // GET方法 通用参数解析并赋值
            CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
            Response result = positonServices.getResources(query);

            return ResponseLogNotification.successWithParse(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/positions/verifyCustomize", method = RequestMethod.GET)
	@ResponseBody
	public String verifyRequires(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			
			//ImportCVForm form = ParamUtils.initModelForm(request, ImportCVForm.class);
			String userId = request.getParameter("user_id");
			String positionId = request.getParameter("position_id");
			ValidateUtil vu = new ValidateUtil();
			vu.addIntTypeValidate("用户编号", userId, null, null, 1, Integer.MAX_VALUE);
			vu.addIntTypeValidate("职位编号", positionId, null, null, 1, Integer.MAX_VALUE);
			String message = vu.validate();
			if(!StringUtils.isNullOrEmpty(message)) {
				Response result = positonServices.verifyCustomize(Integer.valueOf(userId), Integer.valueOf(positionId));
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, message);
			}
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
