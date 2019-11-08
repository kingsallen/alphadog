package com.moseeker.servicemanager.web.controller.company;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.OmsSwitchEnum;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.Result;
import com.moseeker.servicemanager.web.controller.company.forms.Validator;
import com.moseeker.servicemanager.web.controller.company.vo.Authentication;
import com.moseeker.servicemanager.web.controller.company.vo.GDPRProtectedInfoVO;
import com.moseeker.servicemanager.web.controller.useraccounts.UserHrAccountParamUtils;
import com.moseeker.servicemanager.web.controller.util.Params;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.*;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyMobotConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;
import com.moseeker.thrift.gen.employee.service.EmployeeService;
import com.moseeker.thrift.gen.employee.struct.EmployeeVerificationConfResponse;
import com.moseeker.thrift.gen.employee.struct.RewardConfig;
import com.moseeker.thrift.gen.position.service.PositionServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.moseeker.servicemanager.common.ParamUtils.parseRequestParam;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class CompanyController {

    Logger logger = LoggerFactory.getLogger(CompanyController.class);

    CompanyServices.Iface companyServices = ServiceManager.SERVICE_MANAGER.getService(CompanyServices.Iface.class);

	private PositionServices.Iface positonServices = ServiceManager.SERVICE_MANAGER.getService(PositionServices.Iface.class);

    private EmployeeService.Iface employeeServices = ServiceManager.SERVICE_MANAGER.getService(EmployeeService.Iface.class);

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public CompanyController(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }


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
            return ResponseLogNotification.fail(request, e);
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
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/company", method = RequestMethod.POST)
    @ResponseBody
    public String addCompany(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> data = parseRequestParam(request);
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
            return ResponseLogNotification.fail(request, e);
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
            Params<String, Object> params = parseRequestParam(request);
            int companyId = params.getInt("companyId") != null ? params.getInt("companyId") : 0;
            CompanyOptions companyOptions = companyServices.getCompanyOptions(companyId);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(companyOptions)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            Params<String, Object> params = parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            if (companyId == 0) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else {
                List<RewardConfig> result = companyServices.getCompanyRewardConf(companyId);
                return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(result)));
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            Params<String, Object> params = parseRequestParam(request);
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
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            Params<String, Object> params = parseRequestParam(request);
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
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            Params<String, Object> params = parseRequestParam(request);
            int companyId = params.getInt("companyId") != null ? params.getInt("companyId") : 0;
            int hraccountId = params.getInt("hraccountId") != null ? params.getInt("hraccountId") : 0;
            int type = params.getInt("type") != null ? params.getInt("type") : 0;
            HrImporterMonitorDO hrImporterMonitorDO = companyServices.getImporterMonitor(companyId, hraccountId, type);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(hrImporterMonitorDO)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            Params<String, Object> params = parseRequestParam(request);
            int companyId = params.getInt("companyId") != null ? params.getInt("companyId") : 0;
            int disable = params.getInt("disable") != null ? params.getInt("disable") : 0;
            Response res = companyServices.bindingSwitch(companyId, disable);
            return ResponseLogNotification.success(request, res);
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            Params<String, Object> params = parseRequestParam(request);
            logger.debug("GET /hraccount/company/hremployeecertconf params : {}",params);
            int companyId = params.getInt("companyId", 0);
            int hraccountId = params.getInt("hraccountId", 0);
            int type = params.getInt("type", 0);
            CompanyCertConf companyCertConf = companyServices.getHrEmployeeCertConf(companyId, type, hraccountId);
            logger.debug("GET /hraccount/company/hremployeecertconf companyCertConf : {}",companyCertConf);
            return ResponseLogNotification.success(request, ResponseUtils.successWithoutStringify(BeanUtils.convertStructToJSON(companyCertConf)));
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/company/{id}/authentication", method = RequestMethod.GET)
    @ResponseBody
    public String getAuthentication(@PathVariable Integer id) throws Exception {

        EmployeeVerificationConfResponse authentication = employeeServices.getEmployeeVerificationConf(id);
        return Result.success(Authentication.clone(authentication)).toJson();
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
            Params<String, Object> params = parseRequestParam(request);
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
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 修改公司企业微信员工认证配置
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/employeebindconf/workwx", method = RequestMethod.PUT)
    @ResponseBody
    public String updateWorkWxConf(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = parseRequestParam(request);
            logger.debug("PUT /hraccount/company/employeebindconf/workwx params:{}" ,params);
            int companyId = params.getInt("companyId", 0);
            int hraccountId = params.getInt("hraccountId", 0);
            String corpid = params.getString("corpid");
            String secret = params.getString("secret");
            if (companyId == 0 ) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else if (StringUtils.isNullOrEmpty(corpid ) )  {
                return ResponseLogNotification.fail(request, "secret不能为空");
            } else {
                boolean result = companyServices.setWorkWechatEmployeeBindConf(companyId, hraccountId,corpid,secret);
                return ResponseLogNotification.success(request, ResponseUtils.success(new HashMap<String, Object>() {{
                    put("result", result);
                }}));
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }


    /**
     * 重新获取企业微信员工认证配置的access token
     *
     * @return
     */
    @RequestMapping(value = "/hraccount/company/refreshWorkWxToken/{companyId}", method = RequestMethod.POST)
    @ResponseBody
    public String requireWorkWxAccessToken(HttpServletRequest request,@PathVariable Integer companyId) {
        try {
            if (companyId == 0 ) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else {
                boolean result = companyServices.updateWorkWeChatConfToken(companyId);
                return ResponseLogNotification.successJson(request, result);
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 修改公司企业微信员工认证配置
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/hraccount/company/employeebindconf/workwx", method = RequestMethod.GET)
    @ResponseBody
    public String getWorkWxConf(HttpServletRequest request, HttpServletResponse response) {
        try {
            Params<String, Object> params = parseRequestParam(request);
            int companyId = params.getInt("companyId", 0);
            //int hraccountId = params.getInt("hraccountId", 0);
            if (companyId == 0 ) {
                return ResponseLogNotification.fail(request, "公司Id不能为空");
            } else {
                WorkWxCertConf result = companyServices.getWorkWechatEmployeeBindConf(companyId);
                // thrift接口的方法不可返回null，通过corpid==null转化
                if( result != null && result.getCorpid() == null){
                    result = null ;
                }
                return Result.success(result).toJson();
            }
        } catch (BIZException e) {
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
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
            return ResponseLogNotification.fail(request, ResponseUtils.fail(e.getCode(), e.getMessage()), e);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }
    /*
     * 获取pc端五个广告位
     */
    @RequestMapping(value = "/company/strictselection", method = RequestMethod.GET)
    @ResponseBody
    public String getPcBanner(HttpServletRequest request, HttpServletResponse response) {
        try{
            Map<String, Object> data = parseRequestParam(request);
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
            return ResponseLogNotification.fail(request, e);
        }
    }
    /*
     * 获取pc端仟寻推荐的企业
     */
    @RequestMapping(value = "/company/apolegamic", method = RequestMethod.GET)
    @ResponseBody
    public String getPcQXRecommendCompany(HttpServletRequest request, HttpServletResponse response) {
        try{
            Params<String, Object> params = parseRequestParam(request);
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
            return ResponseLogNotification.fail(request, e);
        }
    }
    /*
     * 获取pc端全部的企业
     */
    @RequestMapping(value = "/company/apolegamicall", method = RequestMethod.GET)
    @ResponseBody
    public String getPcQXRecommendAll(HttpServletRequest request, HttpServletResponse response) {
        try{
            Params<String, Object> params = parseRequestParam(request);
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
            return ResponseLogNotification.fail(request, e);
        }
    }
    //获取公司信息，包括团队信息
    @RequestMapping(value = "/company/details", method = RequestMethod.GET)
    @ResponseBody
    public String companyDetails(HttpServletRequest request, HttpServletResponse response) {
        try{
            Params<String, Object> params = parseRequestParam(request);
            Integer companyId = params.getInt("companyId");
            logger.info("param====companyId=={}",companyId);
            Response res=companyServices.companyDetails(companyId);
            return ResponseLogNotification.success(request,res);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
    /*
     * 获取pc单个公司信息，不带团队信息
     */
    @RequestMapping(value = "/company/info", method = RequestMethod.GET)
    @ResponseBody
    public String getPcCompanyInfo(HttpServletRequest request, HttpServletResponse response) {
        try{
            Params<String, Object> params = parseRequestParam(request);
            Integer companyId = params.getInt("companyId");
            logger.info("param====companyId=={}",companyId);
            Response res=companyServices.companyMessage(companyId);
            return ResponseLogNotification.success(request, res);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
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
        Params<String, Object> params = parseRequestParam(request);
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
    @RequestMapping(value = "/company/fortuneorpaid", method = RequestMethod.GET)
    @ResponseBody
    public String getFortuneOrPaid(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = parseRequestParam(request);
            Response result = companyServices.companyPaidOrFortune();
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 获取人才库状态
     *
     * @param request
     */
    @RequestMapping(value = "/api/switch/talentpool", method = RequestMethod.GET)
    @ResponseBody
    public String getTalentpoolStstus(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> params = parseRequestParam(request);
            String hrId=String.valueOf(params.get("hr_id"));
            String companyId=String.valueOf(params.get("company_id"));
            if(StringUtils.isNullOrEmpty(hrId)||"0".equals(hrId)){
                ResponseLogNotification.fail(request,"hr_id不可以为空或者为0");
            }
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(companyId)){
                ResponseLogNotification.fail(request,"company_id不可以为空或者为0");
            }
            Response result = companyServices.getTalentPoolStatus(Integer.parseInt(hrId),Integer.parseInt(companyId));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     * 根据公司ID获取公司配置，如果是子公司，查询母公司配置
     */
    @RequestMapping(value = "/api/hrcompany/conf", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyConf(HttpServletRequest request) {
        try {
            Params<String, Object> data = ParamUtils.parseRequestParam(request);
            int companyId = data.getInt("company_id");
            HrCompanyConfDO result = companyServices.getCompanyConfById(companyId);
            //驼峰转下划线
            HrCompanyConf underLineResult = JSON.parseObject(JSON.toJSONString(result, serializeConfig),HrCompanyConf.class);
            //转换json的时候去掉thrift结构体中的set方法
            JSONObject jsonResult = JSON.parseObject(BeanUtils.convertStructToJSON(underLineResult));
            return ResponseLogNotification.successJson(request, jsonResult);
        } catch (BIZException e) {
            return ResponseLogNotification.failJson(request, e);
        } catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }

    /*
       修改hr_company_conf
   */
    @RequestMapping(value = "/api/hrcompany/conf", method = RequestMethod.PATCH)
    @ResponseBody
    public String updateCompanyConf(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            HrCompanyConf companyConf = ParamUtils.initModelForm(data, HrCompanyConf.class);
            Response result = companyServices.updateHrCompanyConf(companyConf);
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }

    /*
       修改hr_company_conf
   */
    @RequestMapping(value = "/api/hrcompany/conf/status", method = RequestMethod.PATCH)
    @ResponseBody
    public String updateCompanyConfStatus(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            String company_id=String.valueOf(data.get("company_id"));
            String status=String.valueOf(data.get("status"));
            if(StringUtils.isNullOrEmpty(status)){
                ResponseLogNotification.fail(request,"人才库状态不可以为空");
            }
            if(StringUtils.isNullOrEmpty(company_id)){
                ResponseLogNotification.fail(request,"公司编号不可以为空");
            }
            Response result = companyServices.updateHrCompanyConfStatus(Integer.parseInt(status), Integer.parseInt(company_id));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/api/hrcompany/mobot/conf", method = RequestMethod.GET)
    @ResponseBody
    public String getMobotConf(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> data = parseRequestParam(request);
            int company_id = data.getInt("company_id");
            if (company_id <= 0) {
                ResponseLogNotification.fail(request, "公司编号不可以为空");
            }
            HrCompanyMobotConfDO result = companyServices.getMobotConf(company_id);
            return Result.success(result).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/api/hrcompany/mobot/conf", method = RequestMethod.POST)
    @ResponseBody
    public String updateMobotConf(HttpServletRequest request) throws Exception {
        try {
            Params<String, Object> data = parseRequestParam(request);
            HrCompanyMobotConfDO param = ParamUtils.initModelForm(data, HrCompanyMobotConfDO.class);
            HrCompanyMobotConfDO result = companyServices.updateMobotConf(param);
            return Result.success(result).toJson();
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/api/account/limit", method = RequestMethod.GET)
    @ResponseBody
    public String findSubAccountNum(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            String company_id=String.valueOf(data.get("company_id"));
            if(StringUtils.isNullOrEmpty(company_id)){
                ResponseLogNotification.fail(request,"公司编号不可以为空");
            }
            Response result = companyServices.findSubAccountNum(Integer.parseInt(company_id));
            return ResponseLogNotification.success(request, result);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/hrcompany/add", method = RequestMethod.POST)
    @ResponseBody
    public String addHrAccountAndCompany(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            String company_name=String.valueOf(data.get("company_name"));
            String mobile=String.valueOf(data.get("mobile"));
            String wxuserId=String.valueOf(data.get("wxuser_id"));
            String remote_ip=String.valueOf(data.get("remote_ip"));
            String source=String.valueOf(data.get("source"));
            String hr_source =String.valueOf(data.get("hr_source"));
            if(StringUtils.isNullOrEmpty(mobile)){
                ResponseLogNotification.fail(request,"注册手机号不可以为空");
            }
            if(StringUtils.isNullOrEmpty(company_name)){
                ResponseLogNotification.fail(request,"注册公司名称不可以为空");
            }
            if(StringUtils.isNullOrEmpty(wxuserId)){
                ResponseLogNotification.fail(request,"微信名称不可以为空");
            }
            logger.info("addHrAccountAndCompany hr注册参数：companyName={}, mobile={},wxuserId={}",company_name, mobile, wxuserId);
            Response result = companyServices.addHrAccountAndCompany(company_name, mobile, Integer.parseInt(wxuserId), remote_ip, Integer.parseInt(source), Integer.parseInt(hr_source));
            logger.info("addHrAccountAndCompany hr注册成功结果：{};提示信息：{}",result.getStatus(), result.getMessage());
            if (result.getStatus() == 0) {
                return ResponseLogNotification.success(request, result);
            } else {
                return ResponseLogNotification.fail(request, result);
            }
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }


    @RequestMapping(value = "/api/company/feature", method = RequestMethod.POST)
    @ResponseBody
    public String addCompanyFeature(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            HrCompanyFeatureDO DO=ParamUtils.initModelForm(data, HrCompanyFeatureDO.class);
            Response res=companyServices.addCompanyFeature(DO);
            return ResponseLogNotification.success(request,res);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
    @RequestMapping(value = "/api/company/feature/list", method = RequestMethod.POST)
    @ResponseBody
    public String addCompanyFeatureList(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            List<Map<String,Object>> list= (List<Map<String, Object>>) data.get("data");
            List<HrCompanyFeatureDO> dataList=new ArrayList<>();
            for(Map<String,Object> map:list){
                HrCompanyFeatureDO DO=ParamUtils.initModelForm(map, HrCompanyFeatureDO.class);
                dataList.add(DO);
            }
            Response res=companyServices.addCompanyFeatures(dataList);
            return ResponseLogNotification.success(request,res);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
    @RequestMapping(value = "/api/company/feature", method = RequestMethod.PUT)
    @ResponseBody
    public String updateCompanyFeature(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            HrCompanyFeatureDO DO=ParamUtils.initModelForm(data, HrCompanyFeatureDO.class);
            Response res=companyServices.updateCompanyFeature(DO);
            return ResponseLogNotification.success(request,res);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
    @RequestMapping(value = "/api/company/feature/list", method = RequestMethod.PUT)
    @ResponseBody
    public String updateCompanyFeatureList(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            List<Map<String,Object>> list= (List<Map<String, Object>>) data.get("data");
            List<HrCompanyFeatureDO> dataList=new ArrayList<>();
            for(Map<String,Object> map:list){
                HrCompanyFeatureDO DO=ParamUtils.initModelForm(map, HrCompanyFeatureDO.class);
                dataList.add(DO);
            }
            Response res=companyServices.updateCompanyFeatures(dataList);
            return ResponseLogNotification.success(request,res);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
    @RequestMapping(value = "/api/company/feature/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getSingleCompanyFeature(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        try {
            Response res= companyServices.getFeatureById(id);
            return  ResponseLogNotification.success(request,res);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
    @RequestMapping(value = "/api/company/feature", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyFeatureByCompanyId(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            String companyId=(String)data.get("company_id");
            if(StringUtils.isNullOrEmpty(companyId)||"0".equals(companyId)){
                return ResponseLogNotification.fail(request, "公司id不能为空或者为0");
            }
            Response res= companyServices.getFeatureByCompanyId(Integer.parseInt(companyId));
            return  ResponseLogNotification.success(request,res);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }
    @RequestMapping(value = "/api/company/feature/list", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyFeatureByIdList(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            String ids=(String)data.get("data");
            if(StringUtils.isNullOrEmpty(ids)){
                return ResponseLogNotification.fail(request, "福利id不能为空或者为0");
            }
            List<Integer> dataList=ParamUtils.convertIntList(ids);
            Response res= companyServices.getCompanyFeatureIdList(dataList);
            return  ResponseLogNotification.success(request,res);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/api/company/award", method = RequestMethod.GET)
    @ResponseBody
    public String getCompanyAward(HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> data = parseRequestParam(request);
            String companyId=(String)data.get("company_id");
            if(StringUtils.isNullOrEmpty(companyId)){
               companyId = "0";
            }
            Response confDO  = companyServices.getCompanyWechatList(Integer.parseInt(companyId));
            return ResponseLogNotification.success(request,confDO);
        }catch(Exception e){
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "v1/gdpr-examination", method = RequestMethod.GET)
    @ResponseBody
    public String getGDPRExamination(@RequestParam Integer appid, @RequestParam(value = "user_ids") List<Integer> userIds,
                                     @RequestParam(value = "company_id") Integer companyId) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredOneValidate("用户编号", userIds);
        validateUtil.addRequiredValidate("公司", companyId);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            return Result.validateFailed(result).toJson();
        } else {
            List<GDPRProtectedInfo> infos = companyServices.validateGDPR(userIds, companyId);
            List<GDPRProtectedInfoVO> results = infos
                    .stream()
                    .map(gdprProtectedInfo -> {
                        GDPRProtectedInfoVO gdprProtectedInfoVO = new GDPRProtectedInfoVO();
                        org.springframework.beans.BeanUtils.copyProperties(gdprProtectedInfo, gdprProtectedInfoVO);
                        return gdprProtectedInfoVO;
                    })
                    .collect(Collectors.toList());
            return Result.success(results).toJson();
        }
    }

    @RequestMapping(value = "v1/gdpr/{companyId}", method = RequestMethod.GET)
    @ResponseBody
    public String getGDPRSwitchStatus(@PathVariable Integer companyId, @RequestParam Integer appid) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("公司", companyId);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            return Result.validateFailed(result).toJson();
        } else {
            return Result.success(companyServices.fetchGDPRSwitch(companyId)).toJson();
        }
    }

    @RequestMapping(value = "v1/hr/{hrId}/gdpr", method = RequestMethod.GET)
    @ResponseBody
    public String getGDPRSwitchStatusByHR(@PathVariable Integer hrId, @RequestParam Integer appid) throws Exception {

        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("HR", hrId);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            return Result.validateFailed(result).toJson();
        } else {
            return Result.success(companyServices.fetchGDPRSwitchByHR(hrId)).toJson();
        }
    }


    /*
    *
    *获取当前公司的开关权限
    *@Param appid
    *@Param companyId 公司id
    *@Param moduleNames 各产品定义标识
    *
    * */
    @RequestMapping(value = "/api/company/switchCheck", method = RequestMethod.GET)
    @ResponseBody
    public String switchCheck(@RequestParam Integer appid,@RequestParam(name = "companyId" , required = false) Integer companyId, @RequestParam(name = "moduleName" , required = false) List<String> moduleNames) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            return Result.validateFailed(result).toJson();
        } else {
            return Result.success(companyServices.switchCheck(companyId,moduleNames)).toJson();
        }
    }

    /*
     *
     *获取当前公司的某个开关权限
     *@Param appid
     *@Param companyId 公司id
     *@Param moduleNames 各产品定义标识（单个）
     *
     * */
    @RequestMapping(value = "/api/company/switch", method = RequestMethod.GET)
    @ResponseBody
    public String companySwitch(@RequestParam Integer appid,@RequestParam(name = "companyId" , required = false) Integer companyId, @RequestParam String moduleName) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("开关标识", moduleName);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            return Result.validateFailed(result).toJson();
        } else {
            return Result.success(companyServices.companySwitch(companyId,moduleName)).toJson();
        }
    }

    /*
     *
     *获取当前公司的企业微信开关权限
     *@Param appid
     *@Param companyId 公司id
     *
     * */
    @RequestMapping(value = "/api/company/switch/workwx/{companyId}", method = RequestMethod.GET)
    @ResponseBody
    public String worxwxSwitch(@RequestParam Integer appid,@PathVariable(name = "companyId" ) Integer companyId) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            return Result.validateFailed(result).toJson();
        } else {
            CompanySwitchVO vo = companyServices.companySwitch(companyId, OmsSwitchEnum.WORK_WEICHAT.getName());
            return Result.success(vo != null && vo.getValid() == 1).toJson();
        }
    }

    /*
     *
     *添加新的公司开关权限
     *@Param appid
     *@Param CompanySwitchVO 公司开关对象
     *
     *
     * */
    @RequestMapping(value = "/api/company/switchPost", method = RequestMethod.POST)
    @ResponseBody
    public String switchPost(@RequestParam Integer appid,@RequestBody CompanySwitchVO companySwitchVO ) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("公司ID", companySwitchVO.getCompanyId());
        validateUtil.addRequiredValidate("产品定义标识", companySwitchVO.getKeyword());
        logger.info("params:"+companySwitchVO);
        logger.info("params:"+companySwitchVO.getKeyword());
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            return Result.validateFailed(result).toJson();
        } else {
            return Result.success(companyServices.switchPost(companySwitchVO)).toJson();
        }
    }

    /*
     *
     *更新当前公司的开关权限
     *@Param appid
     *@Param CompanySwitchVO 公司开关对象
     *
     * */
    @RequestMapping(value = "/api/company/switchPatch", method = RequestMethod.PATCH)
    @ResponseBody
    public String switchPatch(@RequestParam Integer appid,@RequestBody CompanySwitchVO companySwitchVO ) throws Exception {
        ValidateUtil validateUtil = new ValidateUtil();
        validateUtil.addRequiredValidate("appid", appid);
        validateUtil.addRequiredValidate("开关ID", companySwitchVO.getId());
        validateUtil.addRequiredValidate("公司ID", companySwitchVO.getCompanyId());
        validateUtil.addRequiredValidate("品定义标识", companySwitchVO.getKeyword());
        logger.info("params:"+companySwitchVO);
        logger.info("params:"+companySwitchVO.getKeyword());
        String result = validateUtil.validate();
        if (org.apache.commons.lang.StringUtils.isNotBlank(result)) {
            return Result.validateFailed(result).toJson();
        } else {
            return Result.success(companyServices.switchPatch(companySwitchVO)).toJson();
        }
    }
}
