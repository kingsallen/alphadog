package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.CompanyOptions;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class CompanyController {

    Logger logger = LoggerFactory.getLogger(CompanyController.class);

    CompanyServices.Iface companyServices = ServiceManager.SERVICEMANAGER.getService(CompanyServices.Iface.class);

    @RequestMapping(value = "/company", method = RequestMethod.GET)
    @ResponseBody
    public String getcompany(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
            Response result = companyServices.getResources(query);
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

    @RequestMapping(value = "/company/all", method = RequestMethod.GET)
    @ResponseBody
    public String getAllCompany(HttpServletRequest request, HttpServletResponse response) {
        try {
            CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
            Response result = companyServices.getAllCompanies(query);
            if (result.getStatus() == 0) {
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, result);
            }
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    @ResponseBody
    public String addCompany(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            Hrcompany company = ParamUtils.initModelForm(data, Hrcompany.class);
            if (company.getSource() == 0) {
                Integer appid = BeanUtils.converToInteger(data.get("appid"));
                if (appid == null) {
                    appid = 0;
                }
                if (appid.intValue() == Constant.APPID_C) {
                    company.setSource(Constant.COMPANY_SOURCE_PC_EDITING);
                }
                if (appid.intValue() == Constant.APPID_QX || appid.intValue() == Constant.APPID_PLATFORM) {
                    company.setSource(Constant.COMPANY_SOURCE_PC_EDITING);
                }
            }
            Response result = companyServices.add(company);
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
     * 获取公司部门与职能信息(员工认证补填字段显示)
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/employeeoptions", method = RequestMethod.GET)
    @ResponseBody
    public String employeeDetails(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId") != null ? params.getInt("companyId") : 0;
            CompanyOptions companyOptions = companyServices.getCompanyOptions(companyId);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(companyOptions)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 添加员工认证模板
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/importermonitor", method = RequestMethod.POST)
    @ResponseBody
    public String addImporterMonitor(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId") != null ? params.getInt("companyId") : 0;
            int hraccountId = params.getInt("hraccountId") != null ? params.getInt("hraccountId") : 0;
            int type = params.getInt("type") != null ? params.getInt("type") : 0;
            String file = params.getString("file") != null ? params.getString("file") : "";
            int status = params.getInt("status") != null ? params.getInt("status") : 0;
            String message = params.getString("message") != null ? params.getString("message") : "";
            String fileName = params.getString("fileName") != null ? params.getString("fileName") : "";
            Response res = companyServices.addImporterMonitor(companyId, hraccountId, type, file, status, message, fileName);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 获取员工认证模板
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/importermonitor", method = RequestMethod.GET)
    @ResponseBody
    public String getImporterMonitor(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId") != null ? params.getInt("companyId") : 0;
            int hraccountId = params.getInt("hraccountId") != null ? params.getInt("hraccountId") : 0;
            int type = params.getInt("type") != null ? params.getInt("type") : 0;
            HrImporterMonitorDO hrImporterMonitorDO = companyServices.getImporterMonitor(companyId, hraccountId, type);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(hrImporterMonitorDO)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 公司员工认证开关
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/bindingswitch", method = RequestMethod.POST)
    @ResponseBody
    public String bindingSwitch(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId") != null ? params.getInt("companyId") : 0;
            int disable = params.getInt("disable") != null ? params.getInt("disable") : 0;
            Response res = companyServices.bindingSwitch(companyId, disable);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 获取员工认证配置
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/hremployeecertconf", method = RequestMethod.GET)
    @ResponseBody
    public String getHrEmployeeCertConf(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId") != null ? params.getInt("companyId") : 0;
            HrEmployeeCertConfDO hrEmployeeCertConfDO = companyServices.getHrEmployeeCertConf(companyId);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(hrEmployeeCertConfDO)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
