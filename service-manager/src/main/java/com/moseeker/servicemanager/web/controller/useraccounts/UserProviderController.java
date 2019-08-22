package com.moseeker.servicemanager.web.controller.useraccounts;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.useraccounts.service.UserProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserProviderController {
    Logger logger= LoggerFactory.getLogger(UserThirdPartyController.class);

    UserProviderService.Iface userProviderService
            = ServiceManager.SERVICE_MANAGER.getService(UserProviderService.Iface.class);

    @RequestMapping(value = "/v4/user/company/user", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyUser(HttpServletRequest request) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            String phone = params.getString("phone");
            int companyId = params.getInt("companyId");
            if (StringUtils.isNullOrEmpty(phone) || companyId == 0) {
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }

            UserUserDO result = userProviderService.getCompanyUser(0,phone,companyId);

            return ResponseLogNotification.successJson(request, result);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}
