package com.moseeker.servicemanager.web.controller.useraccounts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.useraccounts.service.UserCenterService;
import com.moseeker.thrift.gen.useraccounts.struct.ApplicationRecordsForm;
import com.moseeker.thrift.gen.useraccounts.struct.FavPositionForm;

@Controller
public class UserCenterController {

	Logger logger = LoggerFactory.getLogger(UseraccountsController.class);

	UserCenterService.Iface userCenterService = ServiceManager.SERVICEMANAGER
			.getService(UserCenterService.Iface.class);
	
	@RequestMapping(value = "/user/{id}/applications", method = RequestMethod.GET)
	@ResponseBody
	public String applications(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
			List<ApplicationRecordsForm> forms = userCenterService.getApplications(id);
			return ResponseLogNotification.success(request, ResponseUtils.success(forms));
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			//do nothing
		}
	}
	
	@RequestMapping(value = "/user/{id}/fav-position", method = RequestMethod.GET)
	@ResponseBody
	public String favPositions(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
		try {
			List<FavPositionForm> forms = userCenterService.getFavPositions(id);
			return ResponseLogNotification.success(request, ResponseUtils.success(forms));
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			//do nothing
		}
	}
}
