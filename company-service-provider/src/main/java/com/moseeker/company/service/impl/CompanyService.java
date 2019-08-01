package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.constant.ValidGeneralType;
import com.moseeker.baseorm.dao.campaigndb.CampaignPcBannerDao;
import com.moseeker.baseorm.dao.configdb.ConfigOmsSwitchManagementDao;
import com.moseeker.baseorm.dao.configdb.ConfigSysPointsConfTplDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.baseorm.db.configdb.tables.pojos.ConfigOmsSwitchManagement;
import com.moseeker.baseorm.db.hrdb.tables.*;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyFeatureRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.OmsSwitchEnum;
import com.moseeker.common.exception.Category;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.MD5Util;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.company.constant.ResultMessage;
import com.moseeker.company.exception.CompanyException;
import com.moseeker.company.exception.CompanySwitchException;
import com.moseeker.company.exception.ExceptionCategory;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.company.service.impl.CompanySwitchHandler.AbstractCompanySwitchHandler;
import com.moseeker.company.service.impl.CompanySwitchHandler.CompanySwitchFactory;
import com.moseeker.company.service.impl.vo.GDPRProtectedInfoVO;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.*;
import com.moseeker.thrift.gen.dao.struct.campaigndb.CampaignPcBannerDO;
import com.moseeker.thrift.gen.dao.struct.configdb.ConfigSysPointsConfTplDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.employee.struct.RewardConfig;
import com.moseeker.thrift.gen.mq.service.MqService;
import com.moseeker.thrift.gen.mq.struct.SmsType;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@CounterIface
@Service
public class CompanyService {

    private static Logger logger = LoggerFactory.getLogger(CompanyService.class);


    // private ChaosServices.Iface chaosService = ServiceManager.SERVICE_MANAGER.getService(ChaosServices.Iface.class);

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private HrWxWechatDao wechatDao;

    @Autowired
    private HrWxTemplateMessageDao messageDao;

    @Autowired
    private HrWxNoticeMessageDao noticeDao;

    @Autowired
    private HrGroupCompanyRelDao hrGroupCompanyRelDao;

    @Autowired
    private HrEmployeeSectionDao hrEmployeeSectionDao;

    @Autowired
    private HrEmployeePositionDao hrEmployeePositionDao;

    @Autowired
    private HrEmployeeCertConfDao hrEmployeeCertConfDao;


    @Autowired
    private HrSuperaccountApplyDao superaccountApplyDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private HrPointsConfDao hrPointsConfDao;

    @Autowired
    private HrImporterMonitorDao hrImporterMonitorDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    @Autowired
    private CampaignPcBannerDao campaignPcBannerDao;

    @Autowired
    private HrEmployeeCustomFieldsDao hrEmployeeCustomFieldsDao;

    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;

    @Autowired
    private HrCompanyAccountDao companyAccountDao;

    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private ConfigSysPointsConfTplDao configSysPointsConfTplDao;

    @Autowired
    private HrCompanyFeatureDao hrCompanyFeatureDao;

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private ConfigOmsSwitchManagementDao configOmsSwitchManagementDao;

    @Autowired
    CompanySwitchFactory companySwitchFactory;

    MqService.Iface mqServer = ServiceManager.SERVICE_MANAGER.getService(MqService.Iface.class);

    public Response getResource(CommonQuery query) throws TException {
        try {
            Hrcompany data = companyDao.getData(QueryConvert.commonQueryConvertToQuery(query), Hrcompany.class);
            if(data!=null){
                int companyId=data.getId();
                List<HrCompanyFeature> list=hrCompanyFeatureDao.getFeatureListByCompanyId(companyId);
                if(!StringUtils.isEmptyList(list)){
                    String feature="";
                    for(HrCompanyFeature hrCompanyFeature:list){
                        feature+=hrCompanyFeature.getFeature()+"#";
                    }
                    if(StringUtils.isNotNullOrEmpty(feature)){
                        feature=feature.substring(0,feature.lastIndexOf("#"));
                        data.setFeature(feature);
                    }
                }
            }
            return ResponseUtils.success(data);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResources(CommonQuery query) throws TException {
        try {
            List<Hrcompany> list = companyDao.getDatas(QueryConvert.commonQueryConvertToQuery(query), Hrcompany.class);
            list=this.handlerCompanyFeature(list);
            return ResponseUtils.success(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

    }
    private List<Hrcompany> handlerCompanyFeature(List<Hrcompany> list){
        if(StringUtils.isEmptyList(list)){
            return new ArrayList<>();
        }
        List<Integer> companyIdList=new ArrayList<>();
        for(Hrcompany hrcompany:list){
            companyIdList.add(hrcompany.getId());
        }
        if(StringUtils.isEmptyList(companyIdList)){
            return list;
        }
        List<HrCompanyFeature> dataList=hrCompanyFeatureDao.getFeatureListByCompanyIdList(companyIdList);
        if(StringUtils.isEmptyList(dataList)){
            return list;
        }
        for(Hrcompany hrcompany:list){
            int companyId=hrcompany.getId();
            String feature="";
            for(HrCompanyFeature hrCompanyFeature:dataList){
                int id=hrCompanyFeature.getCompanyId();
                if(id==companyId){
                    feature+=hrCompanyFeature.getFeature()+"#";
                }
            }
            if(StringUtils.isNotNullOrEmpty(feature)){
                feature=feature.substring(0,feature.lastIndexOf("#"));
                hrcompany.setFeature(feature);
            }
        }
        return list;
    }


    @CounterIface
    public Response getAllCompanies(CommonQuery query) {
        try {
            List<Hrcompany> structs = companyDao.getDatas(QueryConvert.commonQueryConvertToQuery(query), Hrcompany.class);
            if (!structs.isEmpty()) {
                structs=this.handlerCompanyFeature(structs);
                return ResponseUtils.success(structs);
            }
        } catch (Exception e) {
            logger.error("getResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    @CounterIface
    public Response add(Hrcompany company) throws TException {
        ValidateUtil vu = new ValidateUtil();
        vu.addRequiredStringValidate("公司名称", company.getName(), null, null);
        vu.addRequiredValidate("来源", company.getSource());
        String message = vu.validate();
        if (StringUtils.isNullOrEmpty(message)) {
            boolean repeatName = companyDao.checkRepeatNameWithSuperCompany(company.getName());
            if (repeatName) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NAME_REPEAT);
            } else {
                try {
                    HrCompanyRecord record = BeanUtils.structToDB(company, HrCompanyRecord.class);
                    boolean scaleIllegal = companyDao.checkScaleIllegal(record.getScale());
                    if (!scaleIllegal) {
                        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
                    }
                    boolean propertyIllegal = companyDao.checkPropertyIllegal(record.getProperty());
                    if (!propertyIllegal) {
                        return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_PROPERTIY_ELLEGAL);
                    }
                    int companyId = companyDao.addRecord(record).getId();
                    return ResponseUtils.success(String.valueOf(companyId));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", message));
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
    }

    /**
     * @param companyId
     * @param wechatId
     * @return
     */
    @CounterIface
    public Response getWechat(long companyId, long wechatId) {

        if (companyId == 0 && wechatId == 0) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.setPageSize(Integer.MAX_VALUE);
        if (wechatId > 0) {
            qu.where("id", String.valueOf(wechatId));
        } else if (companyId > 0) {
            qu.where("company_id", String.valueOf(companyId));
        }
        try {
            HrWxWechatRecord record = wechatDao.getRecord(qu.buildQuery());
            if (record == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            } else {
                return ResponseUtils.success(record.intoMap());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    /**
     * 查找公司所属的集团下的所有公司信息
     *
     * @param companyId 公司编号
     * @return 公司集合
     * @throws BIZException 业务异常
     */
    public List<CompanyForVerifyEmployee> getGroupCompanies(int companyId) throws Exception {

        logger.info("CompanyService getGroupCompanies companyId:{}", companyId);
        /** 如果是子公司的话，则查找母公司的所属集团 */
        companyId = findSuperCompanyId(companyId);
        logger.info("CompanyService getGroupCompanies companyId:{}", companyId);
        HrGroupCompanyRelDO groupCompanyDO = findGroupCompanyRelByCompanyId(companyId);
        logger.info("CompanyService getGroupCompanies groupCompanyDO:{}", JSONObject.toJSONString(groupCompanyDO));
        if (groupCompanyDO == null) {
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_NOT_BELONG_GROUPCOMPANY);
        }

        /** 查找集团下公司的编号 */
        List<Integer> companyIdList = employeeEntity.getCompanyIds(companyId);
        logger.info("CompanyService getGroupCompanies companyIdList:{}", JSONObject.toJSONString(companyIdList));
        if (companyIdList == null || companyIdList.size() == 0) {
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_NOT_BELONG_GROUPCOMPANY);
        }

        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.clear();
        Condition condition = Condition.buildCommonCondition("id", companyIdList, ValueOp.IN);
        queryBuilder.where(condition);
        List<HrCompanyDO> companyDOList = companyDao.getDatas(queryBuilder.buildQuery());
        if (companyDOList == null || companyDOList.size() == 0) {
            throw ExceptionFactory.buildException(Category.PROGRAM_DATA_EMPTY);
        }
        /** 查找公司信息 */
        queryBuilder.clear();
        queryBuilder.select("id").select("company_id").select("signature");
        Condition condition1 = Condition.buildCommonCondition("company_id", companyIdList, ValueOp.IN);
        queryBuilder.where(condition1);

        List<HrWxWechatDO> hrWxWechatDOList = wechatDao.getDatas(queryBuilder.buildQuery());

        /** 拼装结果 */
        List<CompanyForVerifyEmployee> companyForVerifyEmployeeList = companyDOList.stream().map(companyDO -> {
            CompanyForVerifyEmployee companyForVerifyEmployee = new CompanyForVerifyEmployee();
            companyForVerifyEmployee.setId(companyDO.getId());
            companyForVerifyEmployee.setName(companyDO.getName());
            companyForVerifyEmployee.setAbbreviation(companyDO.getAbbreviation());
            return companyForVerifyEmployee;
        }).collect(Collectors.toList());

        if (companyForVerifyEmployeeList != null && companyForVerifyEmployeeList.size() > 0
                && hrWxWechatDOList != null && hrWxWechatDOList.size() > 0) {
            for (CompanyForVerifyEmployee companyForVerifyEmployee : companyForVerifyEmployeeList) {
                for (HrWxWechatDO wechatDO : hrWxWechatDOList) {
                    if (companyForVerifyEmployee.getId() == wechatDO.getCompanyId()) {
                        companyForVerifyEmployee.setSignature(wechatDO.getSignature());
                        break;
                    }
                }
            }
        }

        return companyForVerifyEmployeeList;
    }


    /**
     * 更新公司积分配置
     *
     * @return
     */
    @Transactional
    public Response updateCompanyRewardConf(Integer companyId, List<RewardConfig> rewardConfigs) throws Exception {
        if (companyId == 0) {
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_ID_EMPTY);
        }
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrCompany.HR_COMPANY.ID.getName(), companyId);
        // 判断公司信息是否正确
        HrCompanyDO hrCompanyDO = companyDao.getData(queryBuilder.buildQuery());
        if (hrCompanyDO == null) {
            throw ExceptionFactory.buildException(Category.COMPANY_DATA_EMPTY);
        }
        // 更新数据
        if (!StringUtils.isEmptyList(rewardConfigs)) {
            List<HrPointsConfDO> hrPointsConfDOS = new ArrayList<>();
            for (RewardConfig rewardConfig : rewardConfigs) {
                if (rewardConfig.getId() == 0) {
                    continue;
                }
                HrPointsConfDO hrPointsConfDO = new HrPointsConfDO();
                hrPointsConfDO.setReward(rewardConfig.getPoints());
                hrPointsConfDO.setCompanyId(companyId);
                hrPointsConfDO.setId(rewardConfig.getId());
                hrPointsConfDOS.add(hrPointsConfDO);
            }
            hrPointsConfDao.updateDatas(hrPointsConfDOS);
        }
        Response response = ResultMessage.SUCCESS.toResponse();
        return response;
    }

    /**
     * 判断一家公司是否属于集团公司
     *
     * @param companyId 公司编号
     * @return 是否是集团公司 true:集团公司 false:非集团公司
     * @throws BIZException 业务异常
     */
    public boolean isGroupCompanies(int companyId) throws Exception {

        /** 如果是子公司的话，则查找母公司的所属集团 */
        companyId = findSuperCompanyId(companyId);

        HrGroupCompanyRelDO hrGroupCompanyRelDO = findGroupCompanyRelByCompanyId(companyId);
        if (hrGroupCompanyRelDO != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 如果是子公司的，那么则查找对应母公司的公司编号
     *
     * @param companyId 公司编号
     * @return 公司编号
     */
    private int findSuperCompanyId(int companyId) throws Exception {
        /** 如果是子公司的话，则查找母公司的所属集团 */
        Query.QueryBuilder findCompanyInfo = new Query.QueryBuilder();
        findCompanyInfo.select("parent_id").select("id");
        findCompanyInfo.where("id", companyId);
        HrCompanyDO hrCompanyDO = companyDao.getData(findCompanyInfo.buildQuery());
        if (hrCompanyDO == null) {
            throw ExceptionFactory.buildException(Category.PROGRAM_DATA_EMPTY);
        }
        if (hrCompanyDO.getParentId() > 0) {
            companyId = hrCompanyDO.getParentId();
        }
        return companyId;
    }

    private HrGroupCompanyRelDO findGroupCompanyRelByCompanyId(int companyId) {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where("company_id", companyId);
        return hrGroupCompanyRelDao.getData(queryBuilder.buildQuery());
    }

    /**
     * 获取公司部门与职能信息(员工认证补填字段显示)
     *
     * @param companyId 公司ID
     * @return companyOptions
     * @throws BIZException
     */
    public CompanyOptions getCompanyOptions(Integer companyId) throws Exception {
        CompanyOptions companyOptions = new CompanyOptions();
        if (companyId == 0) {
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_ID_EMPTY);
        }
        //员工部门信息
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrEmployeeSection.HR_EMPLOYEE_SECTION.COMPANY_ID.getName(), companyId);
        List<HrEmployeeSectionDO> hrEmployeeSectionDOS = hrEmployeeSectionDao.getDatas(queryBuilder.buildQuery());
        if (!StringUtils.isEmptyList(hrEmployeeSectionDOS)) {
            companyOptions.setHrEmployeeSection(hrEmployeeSectionDOS);
        }
        // 员工职能信息
        queryBuilder.clear();
        queryBuilder.where(HrEmployeePosition.HR_EMPLOYEE_POSITION.COMPANY_ID.getName(), companyId);
        List<HrEmployeePositionDO> hrEmployeePositionDOS = hrEmployeePositionDao.getDatas(queryBuilder.buildQuery());
        if (!StringUtils.isEmptyList(hrEmployeePositionDOS)) {
            companyOptions.setHrEmployeePosition(hrEmployeePositionDOS);
        }
        return companyOptions;
    }

    /**
     * 添加公司员工认证模板数据
     *
     * @param comanyId    公司编号
     * @param hraccountId HR ID
     * @param type        导入的数据类型 要导入的表：0：user_employee 1: job_position 2:hr_company
     * @param file        文件的绝对路径
     * @param status      导入的状态
     * @param message     操作信息
     * @param fileName    导入的文件名
     * @return response
     */
    public Response addImporterMonitor(Integer comanyId, Integer hraccountId, Integer type, String file, Integer status, String message, String fileName) throws Exception {
        Response response = new Response();
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("HR账号", hraccountId, "不能为空", null, 1, null);
        vu.addIntTypeValidate("公司编号", comanyId, "不能为空", null, 1, null);
        vu.addIntTypeValidate("导入的数据类型", type, "不能为空", null, 0, 100);
        vu.addIntTypeValidate("导入状态", status, "不能为空", null, 0, 100);
        vu.addRequiredStringValidate("导入文件的绝对路径", file, "不能为空", null);
        vu.addRequiredStringValidate("操作信息", message, "不能为空", null);
        vu.addRequiredStringValidate("导入的文件", fileName, "不能为空", null);

        String errorMessage = vu.validate();
        if (!StringUtils.isNullOrEmpty(errorMessage)) {
            throw ExceptionFactory.buildException(ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getCode(),
                    ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getMsg().replace("{MESSAGE}", errorMessage));
        }

        HrImporterMonitorDO hrImporterMonitorDO = new HrImporterMonitorDO();
        hrImporterMonitorDO.setCompanyId(comanyId);
        hrImporterMonitorDO.setHraccountId(hraccountId);
        hrImporterMonitorDO.setType(type);
        hrImporterMonitorDO.setFile(file);
        hrImporterMonitorDO.setMessage(message);
        hrImporterMonitorDO.setStatus(status);
        hrImporterMonitorDO.setName(fileName);
        hrImporterMonitorDO.setSys(2);
        HrImporterMonitorDO temp = hrImporterMonitorDao.addData(hrImporterMonitorDO);
        try {
            if (temp != null) {
                throw ExceptionFactory.buildException(ExceptionCategory.ADD_IMPORTERMONITOR_FAILED);
            } else {
                response = ResultMessage.SUCCESS.toResponse();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionFactory.buildException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS);
        }
        return response;
    }

    /**
     * 查找公司认证模板数据（取最新一条数据）
     *
     * @param comanyId    公司Id
     * @param hraccountId hrId
     * @param type        要导入的表：0：user_employee 1: job_position 2:hr_company
     * @return
     * @throws BIZException
     */
    public HrImporterMonitorDO getImporterMonitor(Integer comanyId, Integer hraccountId, Integer type) throws Exception {
        HrImporterMonitorDO hrImporterMonitorDO = new HrImporterMonitorDO();

        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("公司编号", comanyId, null, null, 1, null);
        vu.addIntTypeValidate("导入类型", type, null, "不能为空", 0, 10);
        vu.addIntTypeValidate("HR账号", hraccountId, null, null, 1, null);
        String errorMessage = vu.validate();
        if (!StringUtils.isNullOrEmpty(errorMessage)) {
            throw ExceptionFactory.buildException(ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getCode(), ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getMsg().replace("{MESSAGE}", errorMessage));
        }
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrImporterMonitor.HR_IMPORTER_MONITOR.COMPANY_ID.getName(), comanyId).and(HrImporterMonitor.HR_IMPORTER_MONITOR.TYPE.getName(), type);
        queryBuilder.and(HrImporterMonitor.HR_IMPORTER_MONITOR.HRACCOUNT_ID.getName(), hraccountId);
        // 时间的倒序
        queryBuilder.orderBy(HrImporterMonitor.HR_IMPORTER_MONITOR.UPDATE_TIME.getName(), Order.DESC);
        List<HrImporterMonitorDO> hrImporterMonitorDOS = hrImporterMonitorDao.getDatas(queryBuilder.buildQuery());
        if (!StringUtils.isEmptyList(hrImporterMonitorDOS)) {
            hrImporterMonitorDO = hrImporterMonitorDOS.get(0);
        }
        return hrImporterMonitorDO;
    }

    /**
     * 员工认证开关
     *
     * @param companyId 公司Id
     * @param disable   是否开启 0开启 1关闭
     * @return
     */
    public Response bindingSwitch(Integer companyId, Integer disable) throws Exception {
        try {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.COMPANY_ID.getName(), companyId);
            HrEmployeeCertConfDO hrEmployeeCertConfDO = hrEmployeeCertConfDao.getData(queryBuilder.buildQuery());
            //判断是否存在改员工认证配置信息
            if (hrEmployeeCertConfDO != null) {
                hrEmployeeCertConfDO.setDisable(disable);
                hrEmployeeCertConfDao.updateData(hrEmployeeCertConfDO);
            } else {
                hrEmployeeCertConfDO = new HrEmployeeCertConfDO();
                hrEmployeeCertConfDO.setCompanyId(companyId);
                hrEmployeeCertConfDO.setEmailSuffix("");
                hrEmployeeCertConfDao.addData(hrEmployeeCertConfDO);
            }
            queryBuilder.clear();
            List<Integer> activations = new ArrayList<>();
            activations.add(3);
            activations.add(4);
            queryBuilder.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.getName(), companyId)
                    .and(new Condition(UserEmployee.USER_EMPLOYEE.ACTIVATION.getName(), activations, ValueOp.IN))
                    .and(new Condition(UserEmployee.USER_EMPLOYEE.DISABLE.getName(), 1, ValueOp.NEQ));
            //如果公司下存在未认证的员工，需要置为删除状态
            List<UserEmployeeDO> list = userEmployeeDao.getDatas(queryBuilder.buildQuery());
            if (!StringUtils.isEmptyList(list)) {
                list.forEach(userEmployeeDO -> {
                    userEmployeeDO.setDisable(1);
                });
                // 数据太大会造成性能不行，有待提高
                userEmployeeDao.updateDatas(list);
            }
            return ResultMessage.SUCCESS.toResponse();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionFactory.buildException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS);
        }
    }

    /**
     * 获取公司认证配置信息
     *
     * @param companyId 公司ID
     * @return 公司认证配置信息
     * @throws BIZException
     */
    public CompanyCertConf getHrEmployeeCertConf(Integer companyId, Integer type, Integer hraccountId) throws Exception {
        CompanyCertConf companyCertConf = new CompanyCertConf();
        if (companyId == 0) {
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_ID_EMPTY);
        }
        List<Integer> companyIds = employeeEntity.getCompanyIds(companyId);
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(new Condition(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.COMPANY_ID.getName(), companyIds, ValueOp.IN));
        List<HrEmployeeCertConfDO> hrEmployeeCertConfDO = hrEmployeeCertConfDao.getDatas(queryBuilder.buildQuery());
        if (StringUtils.isEmptyList(hrEmployeeCertConfDO)) {
            hrEmployeeCertConfDO = new ArrayList<>();
        }
        HrImporterMonitorVO hrImporterMonitorVO = new HrImporterMonitorVO();
        HrImporterMonitorDO hrImporterMonitorDO = getImporterMonitor(companyId, hraccountId, type);
        org.springframework.beans.BeanUtils.copyProperties(hrImporterMonitorDO, hrImporterMonitorVO);
        hrImporterMonitorVO.setFileName(hrImporterMonitorDO.getName());
        companyCertConf.setHrImporterMonitor(hrImporterMonitorVO);
        companyCertConf.setHrEmployeeCertConf(hrEmployeeCertConfDO);

        return companyCertConf;
    }

    /**
     * 更新公司员工认证配置
     *
     * @param companyId   公司编号
     * @param authMode    绑定方式
     * @param emailSuffix 如果邮箱是验证字段，则不能为空
     * @param custom      自定义字段内容
     * @param customHint  自定义字段
     * @param questions   问答
     * @return 受影响行数
     */
    @Transactional
    public int updateHrEmployeeCertConf(Integer companyId, Integer authMode, String emailSuffix, String custom, String customHint, String questions, String filePath, String fileName, Integer type, Integer hraccountId) throws Exception {
        if (companyId == 0) {
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_ID_EMPTY);
        }
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("company_id", companyId)
                .and(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.DISABLE.getName(), 0);
        HrEmployeeCertConfDO hrEmployeeCertConfDO = hrEmployeeCertConfDao.getData(query.buildQuery());
        // 员工认证信息为空需要新建
        int falg = 0;
        if (hrEmployeeCertConfDO == null) {
            hrEmployeeCertConfDO = new HrEmployeeCertConfDO();
            hrEmployeeCertConfDO.setCompanyId(companyId);
            falg = 1;
        } else {
            Integer oldAuthMode = ((int) hrEmployeeCertConfDO.getAuthMode());
            if ((oldAuthMode == 2 || oldAuthMode == 4) && (authMode != 2 && authMode != 4)) {
                query.clear();
                query.select("id");
                query.where("company_id", companyId).and(new Condition("activation", 0, ValueOp.NEQ)).and(new Condition("custom_field", "", ValueOp.EQ));
                List<Integer> employeeIds = userEmployeeDao.getDatas(query.buildQuery(), Integer.class);
                employeeEntity.removeEmployee(employeeIds);
            }
        }
        hrEmployeeCertConfDO.setAuthMode(authMode);
        hrEmployeeCertConfDO.setCompanyId(companyId);
        if (StringUtils.isNotNullOrEmpty(emailSuffix)) {
            hrEmployeeCertConfDO.setEmailSuffix(emailSuffix);
        }
        if (StringUtils.isNotNullOrEmpty(questions)) {
            hrEmployeeCertConfDO.setQuestions(questions);
        }
        if (StringUtils.isNotNullOrEmpty(custom)) {
            hrEmployeeCertConfDO.setCustom(custom);
        }
        if (StringUtils.isNotNullOrEmpty(customHint)) {
            hrEmployeeCertConfDO.setCustomHint(customHint);
        }
        try {
            if (falg == 0) {
                hrEmployeeCertConfDao.updateData(hrEmployeeCertConfDO);
            } else if (falg == 1) {
                hrEmployeeCertConfDao.addData(hrEmployeeCertConfDO);
            }
        } catch (Exception e) {
            return 0;
        }

        // 开始添加导入日志数据
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("导入的数据类型", type, "不能为空", null, 0, 100);
        vu.addIntTypeValidate("HR账号", hraccountId, "不能为空", null, 1, 1000000);
        vu.addStringLengthValidate("导入文件的绝对路径", filePath, null, null, 0, 257);
        vu.addStringLengthValidate("导入的文件名", fileName, null, null, 0, 257);

        String errorMessage = vu.validate();
        if (!StringUtils.isNullOrEmpty(errorMessage)) {
            throw ExceptionFactory.buildException(ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getCode(),
                    ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getMsg().replace("{MESSAGE}", errorMessage));
        }
        try {
            HrImporterMonitorDO hrImporterMonitorDO = new HrImporterMonitorDO();
            hrImporterMonitorDO.setFile(filePath);
            hrImporterMonitorDO.setSys(2);
            hrImporterMonitorDO.setCompanyId(companyId);
            hrImporterMonitorDO.setName(fileName);
            hrImporterMonitorDO.setStatus(2);
            hrImporterMonitorDO.setType(type);
            hrImporterMonitorDO.setMessage("导入成功");
            hrImporterMonitorDO.setHraccountId(hraccountId);
            hrImporterMonitorDao.addData(hrImporterMonitorDO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionFactory.buildException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS);
        }
        return 1;
    }
    /*
     * 获取pc端获取banner图
     */
    @CounterIface
    public Response getPcBannerByPage(int page,int pageSize) throws Exception{
        Query query=new Query.QueryBuilder().setPageNum(page).setPageSize(pageSize).buildQuery();
        List<CampaignPcBannerDO> list=campaignPcBannerDao.getDatas(query);
        if(StringUtils.isEmptyList(list)){
            ResponseUtils.success("");
        }
        return ResponseUtils.success(list);
    }

    /**
     * 获取公司员工认证后补填字段配置信息列表
     *
     * @param companyId
     * @return
     */
    public List<HrEmployeeCustomFieldsVO> getHrEmployeeCustomFields(Integer companyId) {
        if (companyId == 0) {
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_ID_EMPTY);
        }
        List<HrEmployeeCustomFieldsVO> hrEmployeeCustomFieldsVOS = new ArrayList<>();
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.DISABLE.getName(), 0)
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID.getName(), companyId)
                .and(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.STATUS.getName(), 0);
        List<HrEmployeeCustomFieldsDO> list = hrEmployeeCustomFieldsDao.getDatas(queryBuilder.buildQuery());
        for (HrEmployeeCustomFieldsDO hrEmployeeCustomFieldsDO : list) {
            HrEmployeeCustomFieldsVO hrEmployeeCustomFieldsVO = new HrEmployeeCustomFieldsVO();
            hrEmployeeCustomFieldsVO.setFname(hrEmployeeCustomFieldsDO.getFname());
            hrEmployeeCustomFieldsVO.setId(hrEmployeeCustomFieldsDO.getId());
            hrEmployeeCustomFieldsVO.setOption_type(hrEmployeeCustomFieldsDO.getOption_type());
            String fvaluesTemp = hrEmployeeCustomFieldsDO.getFvalues();
            if (fvaluesTemp != null) {
                List fvalues = JSONObject.parseArray(fvaluesTemp);
                hrEmployeeCustomFieldsVO.setFvalues(fvalues);
            }
            hrEmployeeCustomFieldsVOS.add(hrEmployeeCustomFieldsVO);
        }
        return hrEmployeeCustomFieldsVOS;
    }
    /*
      获取talent_pool的状态是否开启
      @auth :zzt
      @params: hrId:user_hr_account.id
               companyId:hr_company.id
      @return 0:未开启
              1：开启
              2：无此主账号
              3：无此公司配置信息
     */
    @CounterIface
    public int getTalentPoolSwitch(int hrId,int companyId){
        HrCompanyRecord companyRecord=this.getCompanyById(companyId);
        if(companyRecord.getType()!=0){
            return -3;
        }
        int count=this.validateHrAndCompany(hrId,companyId);
        if(count==0){
            return -1;
        }
        HrCompanyConfRecord record=this.getHrCompanyConfRecordByCompanyId(companyId);
        if(record==null){
            return -2;
        }
        int talentPoolStatus=record.getTalentpoolStatus();
        return talentPoolStatus;
    }
    /*
     获取此账号是不是此公司的账号
     */
    private int validateHrAndCompany(int hrId,int companyId){
        Query query=new Query.QueryBuilder().where("id",hrId).and("company_id",companyId).and("activation",1).and("disable",1).buildQuery();
        int count =userHrAccountDao.getCount(query);
        return count;
    }
    /*
    根据id获取公司的信息
     */
    private HrCompanyRecord getCompanyById(int companyId){
        Query query=new Query.QueryBuilder().where("id",companyId).buildQuery();
        HrCompanyRecord record=companyDao.getRecord(query);
        return record;
    }

    /*
      根据公司id获取公司配置
     */
    private HrCompanyConfRecord getHrCompanyConfRecordByCompanyId(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        HrCompanyConfRecord hrCompanyConfRecord=hrCompanyConfDao.getRecord(query);
        return hrCompanyConfRecord;
    }

    /*
      根据公司ID查询公司配置，如果是子公司，查询母公司配置
     */
    @CounterIface
    public HrCompanyConfDO getHrCompanyConfById(int companyId) throws BIZException {
        if(companyId <= 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
        HrCompanyDO companyDO = companyDao.getCompanyById(companyId);
        if(companyDO == null || (companyDO.getParentId()!= 0 && companyDO.getDisable() == 0)) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
        }
        //公司如果为子公司，要查询母公司的配置
        if(companyDO.getParentId() != 0 ){
            companyId = companyDO.getParentId();
        }
        HrCompanyConfDO companyConfDO = hrCompanyConfDao.getHrCompanyConfByCompanyId(companyId);
        if(companyConfDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRCOMPANY_CONF_NOTEXIST);
        }
        return companyConfDO;
    }


    /*
      修改hr_company_conf
     */
    @CounterIface
    public Response updateHrCompanyConf(com.moseeker.thrift.gen.company.struct.HrCompanyConf hrCompanyConf){
        HrCompanyConfRecord record=BeanUtils.structToDB(hrCompanyConf,HrCompanyConfRecord.class);
        int result=hrCompanyConfDao.updateRecord(record);
        if(result==0){
            return ResponseUtils.fail(1,"操作失败");
        }
        return ResponseUtils.success("");
    }

    public Response findSubAccountNum(int companyId){
        HrSuperaccountApplyDO superaccountApply = superaccountApplyDao.getByCompanyId(companyId);
        Map<String, Object> params = new HashMap<>();
        if(superaccountApply != null) {
            params.put("account_limit", superaccountApply.getAccountLimit());
        }
        return ResponseUtils.success(params);
    }

    @Transactional
    public Response upsertWechatTheme(int companyId, int status){
        HrWxWechatDO wechatDO = wechatDao.getHrWxWechatByCompanyId(companyId);
        if(wechatDO != null){
            wechatDO.setShowCustomTheme(status);
            wechatDao.updateData(wechatDO);
            return  ResponseUtils.success("");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    public List<HrCompanyWechatDO> getCompanyInfoByTemplateRank(int companyId){
        String timeStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        timeStr = timeStr+"-01 00:00:00";
        logger.info("===============time:{}",timeStr);
        Date date = null;
        try {
            date = DateUtils.shortTimeToDate(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long startTime = System.currentTimeMillis();
        //获取本月有积分增加的员工map 员工编号 = 积分增加只
        Map<Integer, Integer> employeePointsMap = employeeEntity.getEmployeeAwardSum(date);
        Set<Integer> employeeIdList = employeePointsMap.keySet();
        long pointTime = System.currentTimeMillis();
        logger.info("getCompanyInfoByTemplateRank ============== point:{}",pointTime - startTime);
        //获取本月有积分增加的员工列表
        List<UserEmployeeDO> employeeDOList = employeeEntity.getUserEmployeeByIdList(employeeIdList);
        long employeeTime = System.currentTimeMillis();
        logger.info("getCompanyInfoByTemplateRank ============== employee:{}",employeeTime - pointTime);
        if(!StringUtils.isEmptyList(employeeDOList)) {
            List<Integer> companyList = employeeDOList.stream().map(m -> m.getCompanyId()).collect(Collectors.toList());
            List<Integer> companyIdList = hrGroupCompanyRelDao.getGroupCompanyRelDoByCompanyIds(companyList);
            long companyTime = System.currentTimeMillis();
            logger.info("getCompanyInfoByTemplateRank ============== groupCompany :{}",companyTime - employeeTime);
            //获取本月有积分增加员工对应公司认证员工数量 公司编号 = 认证员工数量
            Map<Integer, Integer> companyEmployeeMap = employeeEntity.getEmployeeNum(companyIdList);
            long employeeNumTime = System.currentTimeMillis();
            logger.info("getCompanyInfoByTemplateRank ============== employeeNum :{}", employeeNumTime - companyTime);
            //获取对应公众号信息
            List<HrWxWechatDO> wechatDOList = wechatDao.getHrWxWechatByCompanyIds(companyIdList);
            long wechatTime = System.currentTimeMillis();
            logger.info("getCompanyInfoByTemplateRank ============== wechat :{}", wechatTime - employeeNumTime);
            if(!StringUtils.isEmptyList(wechatDOList)) {
                List<Integer> wechatIdList = wechatDOList.stream().map(m -> m.getId()).collect(Collectors.toList());
                List<HrWxTemplateMessageDO> messageDOList = messageDao
                        .getHrWxTemplateMessageDOByWechatIds(wechatIdList, Constant.AWARD_RANKING);
                long messageTime = System.currentTimeMillis();
                logger.info("getCompanyInfoByTemplateRank ============== message :{}", messageTime - wechatTime);
                //筛选出来排名通知消息模板为开的公众号开关
                List<HrWxNoticeMessageDO> noticeList = noticeDao.getHrWxNoticeMessageDOByWechatIds(wechatIdList, Constant.AWARD_RANKING);
                long noticeTime = System.currentTimeMillis();
                logger.info("getCompanyInfoByTemplateRank ============== notice :{}", noticeTime - messageTime);
                if(!StringUtils.isEmptyList(noticeList)) {
                    wechatIdList = noticeList.stream().map(m -> m.getWechatId()).collect(Collectors.toList());
                    wechatDOList = wechatDao.getHrWxWechatByIds(wechatIdList);
                    companyIdList = wechatDOList.stream().map(m -> m.getCompanyId()).collect(Collectors.toList());
                    long companyIdTime = System.currentTimeMillis();
                    logger.info("getCompanyInfoByTemplateRank ============== companyIdTime :{}", companyIdTime - noticeTime);
                    return handerCompanyWechatInfo(companyId, companyIdList, wechatDOList, messageDOList, companyEmployeeMap);
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * hr注册时添加hr和公司数据，完成公司积分设置
     * @param companyName   公司名称
     * @param mobile    电话号码
     * @param wxuserId  微信编号
     * @param remoteIp  登录ip
     * @param source    来源
     * @return
     * @throws Exception
     */
    @Transactional
    public Response addHrAccountAndCompany(String companyName, String mobile, int wxuserId, String remoteIp, byte source, byte hr_source) throws Exception {
        //是否和超级公司名相同
        boolean repeatName = companyDao.checkRepeatNameWithSuperCompany(companyName);
        if(!repeatName) {

            Query query = new Query.QueryBuilder().where(UserHrAccount.USER_HR_ACCOUNT.MOBILE.getName(), mobile)
                    .or(UserHrAccount.USER_HR_ACCOUNT.WXUSER_ID.getName(), wxuserId).buildQuery();
            UserHrAccountDO accountDO = userHrAccountDao.getData(query);
            if (accountDO != null) {
                throw ExceptionFactory.buildException(Category.ACCOUNT_DATA_IN);
            }
            HrCompanyDO companyDO = new HrCompanyDO();
            companyDO.setType((byte) 1);
            companyDO.setName(companyName);
            companyDO.setSource(source);
            int companyId = companyDao.addData(companyDO).getId();
            if(companyId <= 0)
                throw ExceptionFactory.buildException(Category.PROGRAM_VALIDATE_REQUIRED);
            String[] passwordArray = this.genPassword(6);
            UserHrAccountDO accountDO1 = new UserHrAccountDO();
            accountDO1.setMobile(mobile);
            accountDO1.setCompanyId(companyId);
            accountDO1.setPassword(passwordArray[1]);
            accountDO1.setWxuserId(wxuserId);
            accountDO1.setLastLoginIp(remoteIp);
            accountDO1.setRegisterIp(remoteIp);
            accountDO1.setSource(hr_source);
            accountDO1.setLoginCount(0);
            int hrId = userHrAccountDao.addData(accountDO1).getId();
            if(hrId <= 0)
                throw ExceptionFactory.buildException(Category.PROGRAM_VALIDATE_REQUIRED);
            HrCompanyDO companyDO1 = companyDao.getCompanyById(companyId);
            companyDO1.setHraccountId(hrId);
            companyDao.updateData(companyDO1);
            HrCompanyAccountDO companyAccountDO = new HrCompanyAccountDO();
            companyAccountDO.setAccountId(hrId);
            companyAccountDO.setCompanyId(companyId);
            companyAccountDao.addData(companyAccountDO);

            //公司积分配置
            Query configQuery = new Query.QueryBuilder().where(new Condition(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.AWARD.getName(), 0, ValueOp.GT)).buildQuery();
            List<ConfigSysPointsConfTplDO> tplDOList = configSysPointsConfTplDao.getDatas(configQuery);
            List<HrPointsConfDO> pointsConfList = new ArrayList<>();
            if(tplDOList!= null && tplDOList.size()>0){
                for(ConfigSysPointsConfTplDO tplDO : tplDOList) {
                    HrPointsConfDO confDO = new HrPointsConfDO();
                    confDO.setCompanyId(companyId);
                    confDO.setStatusName(tplDO.getStatus());
                    confDO.setReward(tplDO.getAward());
                    confDO.setDescription(tplDO.getDescription());
                    confDO.setTemplateId(tplDO.getId());
                    confDO.setTag(tplDO.getTag()+"");
                    pointsConfList.add(confDO);
                }
                hrPointsConfDao.addAllData(pointsConfList);
            }
            //发送消息给HRgit
            Map<String, String> data = new HashMap<>();
            data.put("mobile", mobile);
            data.put("code", passwordArray[0]);
            Response response =  mqServer.sendSMS(SmsType.EMPLOYEE_MERGE_ACCOUNT_SMS, mobile, data, "2", remoteIp);
//            logger.info("addHrAccountAndCompany hr注册成功短信发送结果：{};提示信息：{}",response.getStatus(), response.getMessage());
            Map<String, Object> map = new HashMap();
            map.put("hr_id", hrId);
            logger.info("addHrAccountAndCompany hr注册成功编号：{}",hrId);
            return ResponseUtils.success(map);
        }else{
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NAME_REPEAT);
        }

    }

    private String[] genPassword(int length) {
        String[] passwordArray = new String[2];
        String plainPassword = StringUtils.getRandomString(length);
        passwordArray[0] = plainPassword;
        passwordArray[1] = MD5Util.encryptSHA(MD5Util.md5(plainPassword));
        return passwordArray;
    }

    /*
     根据id查询公司的福利
     */
    @CounterIface
    public HrCompanyFeature getCompanyFeatureById(int id){
        HrCompanyFeature result=hrCompanyFeatureDao.getFeatureListById(id);
        return result;
    }
    /*
     根据公司查询公司福利特色
     */
    @CounterIface
    public List<HrCompanyFeature> getCompanyFeatureByCompanyId(int companyId){
        List<HrCompanyFeature> list=new ArrayList<>();
        com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany hrCompanyDO=companyDao.getHrCompanyById(companyId);
        if(hrCompanyDO==null){
            return new ArrayList<>();
        }
        if(hrCompanyDO.getParentId()>0){
            list=hrCompanyFeatureDao.getFeatureListByCompanyId(companyId);
        }else{
            List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany> companyList=companyDao.getChildHrCompanyById(companyId);
            List<Integer> idList=this.getCompanyIdList(companyList);
            if(StringUtils.isEmptyList(idList)){
                list=hrCompanyFeatureDao.getFeatureListByCompanyId(companyId);
            }else{
                idList.add(companyId);
                list=hrCompanyFeatureDao.getFeatureListByCompanyIdList(idList);
            }
        }
        return list;
    }
    private List<Integer> getCompanyIdList(List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany> companyList){
        if(StringUtils.isEmptyList(companyList)){
            return null;
        }
        List<Integer> list=new ArrayList<>();
        for(com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany hrCompany:companyList){
            list.add(hrCompany.getId());
        }
        return list;
    }

    @CounterIface
    public List<HrCompanyFeature> getCompanyFeatureByIdList(List<Integer> dataList){
        List<HrCompanyFeature> list=hrCompanyFeatureDao.getFeatureListByIdList(dataList);
        return list;
    }
    /*
     单个修改
     */
    @CounterIface
    public int updateCompanyFeature(HrCompanyFeatureDO hrCompanyFeatureDO){
        if(hrCompanyFeatureDO==null){
            return 0;
        }
        if(hrCompanyFeatureDO.isSetFeature()){
            if(StringUtils.isNullOrEmpty(hrCompanyFeatureDO.getFeature())){
                return 0;
            }
        }
        HrCompanyFeatureRecord hrCompanyFeatureRecord=BeanUtils.structToDB(hrCompanyFeatureDO, HrCompanyFeatureRecord.class);
        int result =hrCompanyFeatureDao.updateRecord(hrCompanyFeatureRecord);
        return result;
    }
    /*
     批量修改
     */
    @CounterIface
    public int updateCompanyFeatureList(List<HrCompanyFeatureDO> dataList){
        if(StringUtils.isEmptyList(dataList)){
            return 0;
        }
        List<HrCompanyFeatureDO> recordList=new ArrayList<>();
        for(HrCompanyFeatureDO DO:dataList){
            if(DO.isSetFeature()){
                if(StringUtils.isNullOrEmpty(DO.getFeature())){
                    continue;
                }
            }
            recordList.add(DO);
        }
        if(StringUtils.isEmptyList(recordList)){
            return 0;
        }
        List<HrCompanyFeatureRecord> list=BeanUtils.structToDB(dataList,HrCompanyFeatureRecord.class);
        int[] result =hrCompanyFeatureDao.updateRecords(list);
        if(result[0]>0) {
            return 1;
        }
        return 0;

    }
    /*
     单个增加
     */
    @CounterIface
    public int addCompanyFeature(HrCompanyFeatureDO hrCompanyFeatureDO){
        if(hrCompanyFeatureDO==null){
            return 0;
        }
        if(StringUtils.isNullOrEmpty(hrCompanyFeatureDO.getFeature())){
            return 0;
        }
        Query query=new Query.QueryBuilder().where("company_id",hrCompanyFeatureDO.getCompany_id()).and("disable",1).buildQuery();
        int count=hrCompanyFeatureDao.getCount(query);
        if((count+1)>20){
            return -1;
        }
        hrCompanyFeatureDO.setDisable(1);
        HrCompanyFeatureRecord hrCompanyFeatureRecord=BeanUtils.structToDB(hrCompanyFeatureDO, HrCompanyFeatureRecord.class);
        HrCompanyFeatureRecord result=hrCompanyFeatureDao.addRecord(hrCompanyFeatureRecord);
        return result.getId();
    }
    /*
     批量增加
     */
    @CounterIface
    public int addCompanyFeatureList(List<HrCompanyFeatureDO> dataList){
        if(StringUtils.isEmptyList(dataList)){
            return 0;
        }
        List<HrCompanyFeatureRecord> list=BeanUtils.structToDB(dataList,HrCompanyFeatureRecord.class);
        List<HrCompanyFeatureRecord> recordList=new ArrayList<>();
        if(!StringUtils.isEmptyList(list)){
            for(HrCompanyFeatureRecord record:list){
                String feature=record.getFeature();
                if(StringUtils.isNotNullOrEmpty(feature)){
                    recordList.add(record);
                }
            }
        }

        if(!StringUtils.isEmptyList(recordList)){
            Query query=new Query.QueryBuilder().where("company_id",recordList.get(0).getCompanyId()).and("disable",1).buildQuery();
            int count=hrCompanyFeatureDao.getCount(query);
            if((count+recordList.size())>20){
                return -1;
            }
            List<HrCompanyFeatureRecord> featureRecords=hrCompanyFeatureDao.addAllRecord(recordList);
            logger.info("======================= addCompanyFeatureList Id:{}",featureRecords);

            return 1;
        }

        return 0;
    }

    private List<HrCompanyWechatDO> handerCompanyWechatInfo(int companyid, List<Integer> companyIds, List<HrWxWechatDO> wechatDOList,
                                                            List<HrWxTemplateMessageDO> messageDOList, Map<Integer, Integer> params){
        long startTime = System.currentTimeMillis();
        if(!StringUtils.isEmptyList(companyIds) && params!=null){
            logger.info("===============params:{}",params);
            List<HrCompanyWechatDO> companyWechatDOList = new ArrayList<>();
            for(Integer companyId: companyIds){
                if(companyid != 0 && companyid != companyId){
                    continue;
                }
                HrCompanyWechatDO companyWechatDO = new HrCompanyWechatDO();
                companyWechatDO.setCompanyId(companyId);
                int wechatId = 0;
                if(!StringUtils.isEmptyList(wechatDOList)){
                    for(HrWxWechatDO wechatDO : wechatDOList){
                        if(wechatDO.getCompanyId() == companyId) {
                            wechatId = wechatDO.getId();
                            companyWechatDO.setAccessToken(wechatDO.getAccessToken());
                            companyWechatDO.setSignature(wechatDO.getSignature());
                            companyWechatDO.setWechatId(wechatId);
                        }
                    }
                }
                if(wechatId == 0){
                    continue;
                }
                if(!StringUtils.isEmptyList(messageDOList)){
                    for(HrWxTemplateMessageDO messageDO : messageDOList){
                        if(messageDO.getWechatId() == wechatId){
                            companyWechatDO.setTemplateId(messageDO.getWxTemplateId());
                            companyWechatDO.setTopcolor(messageDO.getTopcolor());
                        }
                    }
                }
                if(params.get(companyId)!=null) {
                    companyWechatDO.setEmployeeCount(params.get(companyId));
                }
                companyWechatDOList.add(companyWechatDO);
            }
            long endTime = System.currentTimeMillis();
            logger.info("getCompanyInfoByTemplateRank ============== handerCompanyWechatInfo :{}", endTime - startTime);
            return companyWechatDOList;
        }
        return new ArrayList<>();
    }

    /**
     * 校验用户在指定的公司下触发GDPR隐私条框保护机制
     * @param userIds 用户信息自己和
     * @param companyId 公司信息集合
     * @return
     * @throws CompanyException
     */
    public List<GDPRProtectedInfoVO> validateGDPR(List<Integer> userIds, int companyId) throws CompanyException {
        HrCompanyConfDO companyConfDO = hrCompanyConfDao.getHrCompanyConfByCompanyId(companyId);
        if (companyConfDO == null || companyConfDO.getIsOpenGdpr() == 0) {
            return userIds.stream().map(userId -> {
                GDPRProtectedInfoVO gdprProtectedInfoVO = new GDPRProtectedInfoVO();
                gdprProtectedInfoVO.setUserId(userId);
                gdprProtectedInfoVO.setTrigger(false);
                return gdprProtectedInfoVO;
            }).collect(Collectors.toList());
        }
        List<JobApplication> applicationList = applicationDao.fetchByApplierId(userIds, companyId);
        List<Integer> positionIdList = applicationList
                .stream()
                .map(JobApplication::getPositionId)
                .collect(Collectors.toList());
        List<JobPosition> positionList = positionDao.fetchPosition(positionIdList);

        return userIds
                .stream()
                .map(userId -> {
                    GDPRProtectedInfoVO gdprProtectedInfoVO = new GDPRProtectedInfoVO();
                    gdprProtectedInfoVO.setUserId(userId);
                    //查找该用户投递过的但是未被删除的职位。如果存在则表示不触发 GDPR 条款保护。
                    Optional<JobPosition> NotDeletePositionOp = positionList
                            .stream()
                            .filter(jobPosition -> {
                                Optional<JobApplication> optional = applicationList
                                        .stream()
                                        .filter(jobApplication -> jobApplication.getApplierId().equals(userId)
                                                && jobApplication.getPositionId().equals(jobPosition.getId()))
                                        .findAny();
                                return optional.isPresent() && jobPosition.getStatus() != 1;

                            })
                            .findAny();
                    if (NotDeletePositionOp.isPresent()) {
                        gdprProtectedInfoVO.setTrigger(false);
                    } else {
                        gdprProtectedInfoVO.setTrigger(true);
                    }
                    return gdprProtectedInfoVO;
                })
                .collect(Collectors.toList());
    }

    /**
     * 查询指定公司GDPR是否开启
     * @param companyId 公司编号
     * @return
     */
    public boolean fetchGDPRSwitch(int companyId) throws CompanyException {
        HrCompanyConfDO companyConfDO = hrCompanyConfDao.getHrCompanyConfByCompanyId(companyId);
        return companyConfDO != null && companyConfDO.getIsOpenGdpr() == 1;
    }

    /**
     * 查询指定HR所属的公司GDPR是否开启
     * @param hrId HR编号
     * @return
     * @throws CompanyException
     */
    public boolean fetchGDPRSwitchByHR(int hrId) throws CompanyException {
        UserHrAccountDO hrAccountDO = userHrAccountDao.getValidAccount(hrId);
        if (hrAccountDO == null || hrAccountDO.getCompanyId() == 0) {
            throw CompanyException.COMPANY_HR_NOT_EXISTS;
        }
        return fetchGDPRSwitch(hrAccountDO.getCompanyId());
    }

    /**
     * 判断当前公司id是否合法
     * @param companyId 公司ID
     *
     * @return
     * @throws CompanyException
     */
    public HrCompanyDO checkParentCompanyIsValid(int companyId)throws CompanyException {
        HrCompanyDO hrCompanyDO = companyDao.getCompanyById(companyId);
        if (hrCompanyDO == null || hrCompanyDO.getId()<=0) {
            throw CompanyException.COMPANY_NOT_EXISTS;
        }
        if(hrCompanyDO.getParentId()!=0){
            hrCompanyDO = companyDao.getCompanyById(hrCompanyDO.getParentId());
        }
        return hrCompanyDO;
    }

    /**
     * 获取当前公司的开关权限
     * @param companyId 公司ID
     * @param moduleNames HR编号
     * @return
     * @throws CompanyException
     */
    public List<CompanySwitchVO> switchCheck(int companyId, List<String> moduleNames) {
        if(companyId!=0){
            HrCompanyDO hrCompanyDO = checkParentCompanyIsValid(companyId);
            companyId = hrCompanyDO.getId();
        }
        List<Integer> moduleList = new ArrayList<>();
        if(moduleNames!=null){
            moduleList = moduleNames.stream().map(str ->{
                return toOmsSwitchValue(str);
            } ).collect(Collectors.toList());
        }
       List<ConfigOmsSwitchManagement> switchList = configOmsSwitchManagementDao.getValidOmsSwitchListByParams(companyId,moduleList);
       return  switchList.stream().map(configOmsSwitchManagementDO -> {
           CompanySwitchVO companySwitchVO = new CompanySwitchVO();
           companySwitchVO.setId(configOmsSwitchManagementDO.getId());
           companySwitchVO.setCompanyId(configOmsSwitchManagementDO.getCompanyId());
           companySwitchVO.setKeyword(getOmsSwitch(configOmsSwitchManagementDO.getModuleName()).getName());
           companySwitchVO.setFieldValue(configOmsSwitchManagementDO.getModuleParam());
           companySwitchVO.setValid(configOmsSwitchManagementDO.getIsValid());
           return companySwitchVO;
       }).collect(Collectors.toList());
    }



    /*
     *
     *添加新的公司开关权限
     *@Param appid
     *@Param CompanySwitchVO 公司开关对象
     *
     *
     * */
    @Transactional
    public CompanySwitchVO switchPost(CompanySwitchVO companySwitchVO) {

        HrCompanyDO hrCompanyDO = checkParentCompanyIsValid(companySwitchVO.getCompanyId());
        companySwitchVO.setCompanyId(hrCompanyDO.getId());
        int moduleId = toOmsSwitchValue(companySwitchVO.getKeyword());
        ConfigOmsSwitchManagement configOmsSwitchManagementDO = new ConfigOmsSwitchManagement();
        ConfigOmsSwitchManagement configOmsSwitchManagement = configOmsSwitchManagementDao.getOmsSwitchByParams(hrCompanyDO.getId(),moduleId);
        if(configOmsSwitchManagement!=null){
            //判断开关是否可用，当前可用抛出已存在异常
            if(configOmsSwitchManagement.getIsValid().equals(ValidGeneralType.valid.getValue())){
                throw CompanySwitchException.COMPANY_SWITCH_EXISTS;
            }
            //判断开关是否可用，当前不可用，只需要更新当前开关
            if(configOmsSwitchManagement.getIsValid().equals(ValidGeneralType.invalid.getValue())){
                configOmsSwitchManagementDO = configOmsSwitchManagement;
                configOmsSwitchManagementDO.setIsValid(ValidGeneralType.valid.getValue());
                configOmsSwitchManagementDO.setModuleParam(companySwitchVO.getFieldValue());
                Integer i = configOmsSwitchManagementDao.update(configOmsSwitchManagementDO);
                //如果更新成功，返回开关对象
                if(i>0){
                    companySwitchVO.setId(configOmsSwitchManagementDO.getId());
                    companySwitchVO.setValid(configOmsSwitchManagementDO.getIsValid());
                    AbstractCompanySwitchHandler abstractCompanySwitchHandler = companySwitchFactory.getService(getOmsSwitch(configOmsSwitchManagementDO.getModuleName()));
                    if(abstractCompanySwitchHandler!=null) {
                        abstractCompanySwitchHandler.rabbitmq(companySwitchVO);
                    }
                    return companySwitchVO;
                }else{
                    //如果更新失败，抛出异常
                    throw CommonException.PROGRAM_PUT_FAILED;
                }
            }
        }
        configOmsSwitchManagementDO.setCompanyId(hrCompanyDO.getId());
        configOmsSwitchManagementDO.setModuleName(moduleId);
        configOmsSwitchManagementDO.setModuleParam(companySwitchVO.getFieldValue());
        Integer id = configOmsSwitchManagementDao.add(configOmsSwitchManagementDO);
        companySwitchVO.setId(id);
        AbstractCompanySwitchHandler abstractCompanySwitchHandler = companySwitchFactory.getService(getOmsSwitch(configOmsSwitchManagementDO.getModuleName()));
        if(abstractCompanySwitchHandler!=null) {
            abstractCompanySwitchHandler.rabbitmq(companySwitchVO);
        }
        return companySwitchVO;
    }


    /*
     *
     *更新当前公司的开关权限
     *@Param appid
     *@Param CompanySwitchVO 公司开关对象
     *
     * */
    @Transactional
    public CompanySwitchVO switchPatch(CompanySwitchVO companySwitchVO) {
        HrCompanyDO hrCompanyDO = checkParentCompanyIsValid(companySwitchVO.getCompanyId());
        companySwitchVO.setCompanyId(hrCompanyDO.getId());
        int moduleId = toOmsSwitchValue(companySwitchVO.getKeyword());
        ConfigOmsSwitchManagement configOmsSwitchManagement =configOmsSwitchManagementDao.getValidOmsSwitchByParams(companySwitchVO.getId(),hrCompanyDO.getId(),moduleId);
        if(configOmsSwitchManagement==null){
            throw CompanySwitchException.SWITCH_NOT_EXISTS;
        }
        Boolean flag = false;
        if(companySwitchVO.getFieldValue()!=configOmsSwitchManagement.getModuleParam()) {
            configOmsSwitchManagement.setModuleParam(companySwitchVO.getFieldValue());
            flag = true;
        }
        if(companySwitchVO.getValid()!=configOmsSwitchManagement.getIsValid()) {
            configOmsSwitchManagement.setIsValid(companySwitchVO.getValid());
            flag = true;
        }
        //判断是否有参数修改
        if(flag) {
            Integer i = configOmsSwitchManagementDao.update(configOmsSwitchManagement);
            //如果更新成功，返回开关对象
            if(i>0){
                AbstractCompanySwitchHandler abstractCompanySwitchHandler = companySwitchFactory.getService(getOmsSwitch(configOmsSwitchManagement.getModuleName()));
                if(abstractCompanySwitchHandler!=null) {
                    abstractCompanySwitchHandler.rabbitmq(companySwitchVO);
                }
                return companySwitchVO;
            }else{
                //如果更新失败，抛出异常
                throw CommonException.PROGRAM_PUT_FAILED;
            }
        }
        throw CompanySwitchException.COMPANY_SWITCH_EXISTS;
    }

    public CompanySwitchVO companySwitch(int companyId, String moduleNames) {
        if(companyId==0){
            throw CommonException.PROGRAM_PARAM_NOTEXIST;
        }
        HrCompanyDO hrCompanyDO = checkParentCompanyIsValid(companyId);
        companyId = hrCompanyDO.getId();
        ConfigOmsSwitchManagement configOmsSwitchManagementDO = configOmsSwitchManagementDao.getOmsSwitchByParams(companyId,toOmsSwitchValue(moduleNames));
        if(configOmsSwitchManagementDO==null&&companyId!=0){
            ConfigOmsSwitchManagement configOmsSwitchManagement = new ConfigOmsSwitchManagement();
            configOmsSwitchManagement.setCompanyId(companyId);
            configOmsSwitchManagement.setModuleName(toOmsSwitchValue(moduleNames));
            configOmsSwitchManagement.setIsValid((byte)0);
            configOmsSwitchManagementDao.add(configOmsSwitchManagement);
            configOmsSwitchManagementDO = configOmsSwitchManagementDao.getOmsSwitchByParams(companyId,toOmsSwitchValue(moduleNames));
        }
        CompanySwitchVO companySwitchVO = new CompanySwitchVO();
        if(configOmsSwitchManagementDO != null){
            companySwitchVO.setId(configOmsSwitchManagementDO.getId());
            companySwitchVO.setCompanyId(configOmsSwitchManagementDO.getCompanyId());
            companySwitchVO.setKeyword(getOmsSwitch(configOmsSwitchManagementDO.getModuleName()).getName());
            companySwitchVO.setFieldValue(configOmsSwitchManagementDO.getModuleParam());
            companySwitchVO.setValid(configOmsSwitchManagementDO.getIsValid());
        }
        return companySwitchVO;
    }

    private static int toOmsSwitchValue(String moduleNames){
        OmsSwitchEnum omsSwitchEnum = OmsSwitchEnum.instanceFromName(moduleNames);
        if(omsSwitchEnum == null){
            throw CompanySwitchException.MODULE_NAME_NOT_EXISTS;
        }
        return omsSwitchEnum.getValue();
    }
    private static OmsSwitchEnum getOmsSwitch(Integer value){
        if (value == null){
            throw CompanySwitchException.SWITCH_NOT_EXISTS;
        }
        OmsSwitchEnum omsSwitchEnum = OmsSwitchEnum.instanceFromValue(value);
        if(omsSwitchEnum == null){
            throw CompanySwitchException.SWITCH_NOT_EXISTS;
        }
        return omsSwitchEnum;
    }
}