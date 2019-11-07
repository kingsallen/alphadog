package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.moseeker.servicemanager.common.ParamUtils.parseRequestParam;

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

	CompanyServices.Iface companyServices = ServiceManager.SERVICE_MANAGER.getService(CompanyServices.Iface.class);

	/**
	 * 查询公众号信息
	 * @param company_id 公司编号
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


    /**
     * 查询公众号信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/company/wechat", method = RequestMethod.GET)
    @ResponseBody
    public String getWechatBySingnature(HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            String signature = params.getString("signature", "");
            Response result = companyServices.getWechatBySignature(signature, companyId);
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

    @RequestMapping(value = "/api/wechat/theme", method = RequestMethod.PATCH)
    @ResponseBody
    public String updateWechatTheme(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            String company_id=String.valueOf(data.get("company_id"));
            String status=String.valueOf(data.get("status"));
            if(StringUtils.isNullOrEmpty(status)){
                ResponseLogNotification.fail(request,"主题状态不可以为空");
            }
            if(StringUtils.isNullOrEmpty(company_id)){
                ResponseLogNotification.fail(request,"公司编号不可以为空");
            }
            Response result = companyServices.updateWechatThenm(Integer.parseInt(status), Integer.parseInt(company_id));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
