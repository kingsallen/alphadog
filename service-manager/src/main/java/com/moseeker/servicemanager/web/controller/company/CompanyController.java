package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.company.forms.Validator;
import com.moseeker.servicemanager.web.controller.useraccounts.UserHrAccountParamUtils;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.CompanyCertConf;
import com.moseeker.thrift.gen.company.struct.CompanyForVerifyEmployee;
import com.moseeker.thrift.gen.company.struct.CompanyOptions;
import com.moseeker.thrift.gen.company.struct.HrEmployeeCustomFieldsVO;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;
import com.moseeker.thrift.gen.employee.struct.RewardConfig;
import com.moseeker.thrift.gen.position.service.PositionServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class CompanyController {

    Logger logger = LoggerFactory.getLogger(CompanyController.class);

    CompanyServices.Iface companyServices = ServiceManager.SERVICEMANAGER.getService(CompanyServices.Iface.class);

	private PositionServices.Iface positonServices = ServiceManager.SERVICEMANAGER.getService(PositionServices.Iface.class);

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
     * 获取公司积分配置信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/rewardconfig", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyRewardConf(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            if (companyId == 0) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else {
                List<RewardConfig> result = companyServices.getCompanyRewardConf(companyId);
                return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result)));
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 更新公司积分配置信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/rewardconfig", method = RequestMethod.PUT)
    @ResponseBody
    public String updateCompanyRewardConf(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            if (companyId == 0) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else {
                if (params.get("rewardConfigs") != null) {
                    List<HashMap<String, Object>> datas = (List<HashMap<String, Object>>) params.get("rewardConfigs");
                    List<RewardConfig> cs = new ArrayList<>();
                    if (datas != null) {
                        datas.forEach(rewardConfig -> {
                            try {
                                RewardConfig c = ParamUtils.initModelForm(rewardConfig, RewardConfig.class);
                                cs.add(c);
                            } catch (Exception e) {
                                e.printStackTrace();
                                LoggerFactory.getLogger(UserHrAccountParamUtils.class).error(e.getMessage(), e);
                            }
                        });
                    }
                    Response result = companyServices.updateCompanyRewardConf(companyId, cs);
                    return ResponseLogNotification.success(request, result);
                } else {
                    return ResponseLogNotification.fail(request, "积分配置信息为空!");
                }
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
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
     * 获取公司员工认证配置
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
            int companyId = params.getInt("companyId", 0);
            int hraccountId = params.getInt("hraccountId", 0);
            int type = params.getInt("type", 0);
            CompanyCertConf companyCertConf = companyServices.getHrEmployeeCertConf(companyId, type, hraccountId);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(companyCertConf)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 修改公司员工认证配置
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/employeebindconf", method = RequestMethod.PUT)
    @ResponseBody
    public String updateEmployeeBindConf(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            Integer authMode = params.getInt("authMode");
            String emailSuffix = params.getString("emailSuffix");
            String custom = params.getString("custom");
            String customHint = params.getString("customHint");
            String questions = params.getString("questions");
            int type = params.getInt("type", 0);
            int hraccountId = params.getInt("hraccountId", 0);
            String fileName = params.getString("fileName", "");
            String filePath = params.getString("filePath", "");
            if (companyId == 0 || authMode == null) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else if (authMode == null) {
                return ResponseLogNotification.fail(request, "认证方式不能为空");
            } else {
                boolean result = companyServices.updateEmployeeBindConf(companyId, authMode, emailSuffix, custom, customHint, questions, filePath, fileName, type, hraccountId);
                return ResponseLogNotification.success(request, ResponseUtils.success(new HashMap<String, Object>() {{
                    put("result", result);
                }}));
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    /**
     * 查找公司账号的集团账号信息
     *
     * @param request
     * @return 查询结果
     */
    @RequestMapping(value = "/groupcompanies/{companyId}", method = RequestMethod.GET)
    @ResponseBody
    public String getGroupCompanies(@PathVariable("companyId") int companyId, HttpServletRequest request) {
        try {
            List<CompanyForVerifyEmployee> companyForVerifyEmployeeList = companyServices.getGroupCompanies(companyId);
            return ResponseLogNotification.success(request,
                    ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(companyForVerifyEmployeeList)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 判断一家公司是否是集团公司GroupCompany
     *
     * @param request
     * @return 查询结果
     */
    @RequestMapping(value = "/validator/groupcompany", method = RequestMethod.POST)
    @ResponseBody
    public String validator(HttpServletRequest request) {
        try {
            Validator validator = ParamUtils.initModelForm(request, Validator.class);

            boolean result = companyServices.isGroupCompanies(validator.getCompanyId());

            return ResponseLogNotification.success(request,
                    ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
     * 获取pc端五个广告位
     */
    @RequestMapping(value = "/company/strictselection", method = RequestMethod.GET)
    @ResponseBody
    public String getPcBanner(HttpServletRequest request, HttpServletResponse response) {
        try{
            Map<String, Object> data = ParamUtils.parseRequestParam(request);
            String page=(String) data.get("page");
            String pageSize=(String) data.get("pagesize");
            if(page==null){
                page="1";
            }
            if(pageSize==null){
                pageSize="15";
            }
            Response res=companyServices.getPcBanner(Integer.parseInt(page), Integer.parseInt(pageSize));
            return ResponseLogNotification.success(request, res);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
     * 获取pc端仟寻推荐的企业
     */
    @RequestMapping(value = "/company/apolegamic", method = RequestMethod.GET)
    @ResponseBody
    public String getPcQXRecommendCompany(HttpServletRequest request, HttpServletResponse response) {
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer page = params.getInt("page");
            Integer pageSize = params.getInt("pageSize");
            if(page==null){
                page=1;
            }
            if(pageSize==null){
                pageSize=15;
            }
            Response res=positonServices.getPcRecommandCompany(page,pageSize);
            return ResponseLogNotification.success(request, res);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
     * 获取pc端全部的企业
     */
    @RequestMapping(value = "/company/apolegamicall", method = RequestMethod.GET)
    @ResponseBody
    public String getPcQXRecommendAll(HttpServletRequest request, HttpServletResponse response) {
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer page = params.getInt("page");
            Integer pageSize = params.getInt("pageSize");
            if(page==null){
                page=1;
            }
            if(pageSize==null){
                pageSize=15;
            }
            Response res=positonServices.getPcRecommandCompanyAll(page,pageSize);
            return ResponseLogNotification.success(request, res);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    //获取公司信息，包括团队信息
    @RequestMapping(value = "/company/details", method = RequestMethod.GET)
    @ResponseBody
    public String companyDetails(HttpServletRequest request, HttpServletResponse response) {
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer companyId = params.getInt("companyId");
            logger.info("param====companyId=={}",companyId);
            Response res=companyServices.companyDetails(companyId);
            return ResponseLogNotification.success(request,res);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
    /*
     * 获取pc单个公司信息，不带团队信息
     */
    @RequestMapping(value = "/company/info", method = RequestMethod.GET)
    @ResponseBody
    public String getPcCompanyInfo(HttpServletRequest request, HttpServletResponse response) {
        try{
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Integer companyId = params.getInt("companyId");
            logger.info("param====companyId=={}",companyId);
            Response res=companyServices.companyMessage(companyId);
            return ResponseLogNotification.success(request, res);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }


    /**
     * 公司员工认证后补填字段配置信息列表
     *
     * @param request
     */
    @RequestMapping(value = "/company/customfields", method = RequestMethod.GET)
    @ResponseBody
    public String getCustomfields(HttpServletRequest request) throws Exception {
        Params<String, Object> params = ParamUtils.parseRequestParam(request);
        int companyId = params.getInt("companyId", 0);
        List<HrEmployeeCustomFieldsVO> result = companyServices.getHrEmployeeCustomFields(companyId);
        return ResponseLogNotification.success(request,
                ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result)));

    }

    /**
     * 获取付费和五百强的公司信息
     *
     * @param request
     */
    @RequestMapping(value = "/company/fortuneandpaid", method = RequestMethod.GET)
    @ResponseBody
    public String getFortuneOrPaid(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = ParamUtils.parseRequestParam(request);
            Response result = companyServices.companyPaidAndFortune();
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
