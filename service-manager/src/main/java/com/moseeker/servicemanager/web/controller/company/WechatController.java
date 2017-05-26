package com.moseeker.servicemanager.web.controller.company;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;

/**
 * 
 * 公众号接口服务 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 2, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Controller
@CounterIface
public class WechatController {

	Logger logger = LoggerFactory.getLogger(WechatController.class);

	CompanyServices.Iface companyServices = ServiceManager.SERVICEMANAGER.getService(CompanyServices.Iface.class);

	/**
	 * 查询公众号信息
	 * @param company_id 公司编号
	 * @param wechat_id 共帐号编号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/company/{company_id}/wechat", method = RequestMethod.GET)
	@ResponseBody
	public String getWechat(@PathVariable long company_id, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// todo 以后可能需要支持通用查询
			Response result = companyServices.getWechat(company_id, 0);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			// do nothing
		}
	}
}
