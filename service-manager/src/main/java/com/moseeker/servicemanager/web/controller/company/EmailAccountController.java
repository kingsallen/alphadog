package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.company.service.TalentpoolServices;
import com.moseeker.thrift.gen.company.struct.EmailAccountConsumptionForm;
import com.moseeker.thrift.gen.company.struct.EmailAccountForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jack on 2018/4/25.
 */
@Controller
@CounterIface
public class EmailAccountController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    TalentpoolServices.Iface service= ServiceManager.SERVICE_MANAGER.getService(TalentpoolServices.Iface.class);

    @RequestMapping(value = "/api/company/email/monitor", method = RequestMethod.GET)
    @ResponseBody
    public String getEmailAccounts(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId=params.getInt("company_id", 0);
            String companyName=params.getString("company_name", "");
            int pageSize = params.getInt("page_size",10);
            int pageNumber = params.getInt("page_number", 1);
            EmailAccountForm emailAccountForm = service.fetchEmailAccounts(companyId, companyName, pageNumber, pageSize);
            return ResponseLogNotification.successJson(request, emailAccountForm);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/api/company/email/purchase", method = RequestMethod.GET)
    @ResponseBody
    public String getEmailAccountConsumption(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("company_id", 0);
            byte type = params.getByte("type", (byte)0);
            String startDate = params.getString("start_date", "");
            String endDate = params.getString("end_date", "");
            int pageSize = params.getInt("page_size",10);
            int pageNumber = params.getInt("page_number", 1);
            EmailAccountConsumptionForm emailAccountConsumptionForm = service.fetchEmailAccountConsumption(companyId, type, pageNumber, pageSize, startDate, endDate);
            return ResponseLogNotification.successJson(request, emailAccountConsumptionForm);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/api/company/email/purchase", method = RequestMethod.POST)
    @ResponseBody
    public String rechargeEmailAccount(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("company_id", 0);
            int lost = params.getInt("lost",0);

            int id = service.rechargeEmailAccount(companyId, lost);
            return ResponseLogNotification.successJson(request, id);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.failJson(request, e);
        }
    }

    @RequestMapping(value = "/api/company/email/purchase", method = RequestMethod.PATCH)
    @ResponseBody
    public String updateEmailAccountRechargeValue(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int id = params.getInt("id", 0);
            int lost = params.getInt("lost",0);

            service.updateEmailAccountRechargeValue(id, lost);
            return ResponseLogNotification.successJson(request, null);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.failJson(request, e);
        }
    }
}
