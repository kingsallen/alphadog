package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeCertConfDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeePositionDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeSectionDao;
import com.moseeker.baseorm.dao.hrdb.HrGroupCompanyRelDao;
import com.moseeker.baseorm.dao.hrdb.HrImporterMonitorDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCertConf;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeePosition;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeSection;
import com.moseeker.baseorm.db.hrdb.tables.HrImporterMonitor;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.Category;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.company.constant.ResultMessage;
import com.moseeker.company.exception.ExceptionCategory;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.CompanyForVerifyEmployee;
import com.moseeker.thrift.gen.company.struct.CompanyOptions;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeePositionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeSectionDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HrCompanyDao companyDao;

    @Autowired
    protected HrWxWechatDao wechatDao;

    @Autowired
    HrGroupCompanyRelDao hrGroupCompanyRelDao;

    @Autowired
    HrEmployeeSectionDao hrEmployeeSectionDao;

    @Autowired
    HrEmployeePositionDao hrEmployeePositionDao;

    @Autowired
    HrEmployeeCertConfDao hrEmployeeCertConfDao;

    @Autowired
    UserEmployeeDao userEmployeeDao;

    @Autowired
    EmployeeEntity employeeEntity;

    @Autowired
    private HrImporterMonitorDao hrImporterMonitorDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    public Response getResource(CommonQuery query) throws TException {
        try {
            Hrcompany data = companyDao.getData(QueryConvert.commonQueryConvertToQuery(query), Hrcompany.class);
            return ResponseUtils.success(data);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    public Response getResources(CommonQuery query) throws TException {
        try {
            List<Hrcompany> list = companyDao.getDatas(QueryConvert.commonQueryConvertToQuery(query), Hrcompany.class);
            return ResponseUtils.success(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }

    }

    @CounterIface
    public Response getAllCompanies(CommonQuery query) {
        try {
            List<Hrcompany> structs = companyDao.getDatas(QueryConvert.commonQueryConvertToQuery(query), Hrcompany.class);
            if (!structs.isEmpty()) {
                return ResponseUtils.success(structs);
            }
        } catch (Exception e) {
            logger.error("getResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
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
                    boolean propertyIllegal = companyDao.checkPropertyIllegal(record.getScale());
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
     * 判断是否有权限发布职位
     *
     * @param companyId 公司编号
     * @param channel   渠道号
     * @return
     */
    @CounterIface
    public Response ifSynchronizePosition(int companyId, int channel) {
        Response response = ResultMessage.PROGRAM_EXHAUSTED.toResponse();
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("company_id", String.valueOf(companyId)).and("channel", String.valueOf(channel));
        try {
            ThirdPartAccountData data = hrThirdPartyAccountDao.getData(qu.buildQuery(), ThirdPartAccountData.class);
            if (data.getId() == 0 || data.getBinding() != 1) {
                response = ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
            }
            if (data.getRemain_num() == 0) {
                response = ResultMessage.THIRD_PARTY_ACCOUNT_HAVE_NO_REMAIN_NUM.toResponse();
            }
            if (data.getId() > 0 && data.binding == 1 && data.getRemain_num() > 0) {
                response = ResultMessage.SUCCESS.toResponse();
            } else {
                response = ResultMessage.THIRD_PARTY_ACCOUNT_UNBOUND.toResponse();
            }
        } catch (Exception e) {
            response = ResultMessage.PROGRAM_EXHAUSTED.toResponse();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return response;
    }

    /**
     * 查找公司所属的集团下的所有公司信息
     *
     * @param companyId 公司编号
     * @return 公司集合
     * @throws BIZException 业务异常
     */
    public List<CompanyForVerifyEmployee> getGroupCompanies(int companyId) throws BIZException {

        /** 如果是子公司的话，则查找母公司的所属集团 */
        companyId = findSuperCompanyId(companyId);

        HrGroupCompanyRelDO groupCompanyDO = findGroupCompanyRelByCompanyId(companyId);
        if (groupCompanyDO == null) {
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_NOT_BELONG_GROUPCOMPANY);
        }

        /** 查找集团下公司的编号 */
        List<Integer> companyIdList = employeeEntity.getCompanyIds(companyId);

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
     * 判断一家公司是否属于集团公司
     *
     * @param companyId 公司编号
     * @return 是否是集团公司 true:集团公司 false:非集团公司
     * @throws BIZException 业务异常
     */
    public boolean isGroupCompanies(int companyId) throws BIZException {

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
    private int findSuperCompanyId(int companyId) throws BIZException {
        /** 如果是子公司的话，则查找母公司的所属集团 */
        Query.QueryBuilder findCompanyInfo = new Query.QueryBuilder();
        findCompanyInfo.select("parent_id").select("id");
        findCompanyInfo.where("id", companyId);
        HrCompanyDO hrCompanyDO = companyDao.getData(findCompanyInfo.buildQuery());
        if (findCompanyInfo == null) {
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
    public CompanyOptions getCompanyOptions(Integer companyId) throws BIZException {
        CompanyOptions companyOptions = new CompanyOptions();
        try {
            if (companyId > 0) {
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
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_NOT_BELONG_GROUPCOMPANY);
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
    public Response addImporterMonitor(Integer comanyId, Integer hraccountId, Integer type, String file, Integer status, String message, String fileName) throws BIZException {
        Response response = new Response();
        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("HR账号", hraccountId, "不能为空", null, 1, 1000000);
        vu.addIntTypeValidate("公司编号", comanyId, "不能为空", null, 1, 1000000);
        vu.addIntTypeValidate("导入的数据类型", type, "不能为空", null, 0, 100);
        vu.addIntTypeValidate("导入状态", status, "不能为空", null, 0, 100);
        vu.addRequiredStringValidate("导入文件的绝对路径", file, "不能为空", null);
        vu.addRequiredStringValidate("操作信息", message, "不能为空", null);
        vu.addRequiredStringValidate("导入的文件", fileName, "不能为空", null);

        String errorMessage = vu.validate();
        if (!StringUtils.isNullOrEmpty(errorMessage)) {
            throw ExceptionFactory.buildException(ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getCode(), ExceptionCategory.ADD_IMPORTERMONITOR_PARAMETER.getMsg().replace("{MESSAGE}", errorMessage));
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
            if (StringUtils.isEmptyObject(temp)) {
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
    public HrImporterMonitorDO getImporterMonitor(Integer comanyId, Integer hraccountId, Integer type) throws BIZException {
        HrImporterMonitorDO hrImporterMonitorDO = new HrImporterMonitorDO();

        ValidateUtil vu = new ValidateUtil();
        vu.addIntTypeValidate("公司编号", comanyId, null, null, 1, 1000000);
        vu.addIntTypeValidate("导入类型", type, null, "不能为空", 0, 10);
        vu.addIntTypeValidate("HR账号", hraccountId, null, null, 0, 1000000);
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
        } else {
            throw ExceptionFactory.buildException(ExceptionCategory.IMPORTERMONITOR_EMPTY);
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
    public Response bindingSwitch(Integer companyId, Integer disable) throws BIZException {
        Response response = new Response();
        try {
            Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
            queryBuilder.where(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.COMPANY_ID.getName(), companyId);
            HrEmployeeCertConfDO hrEmployeeCertConfDO = hrEmployeeCertConfDao.getData(queryBuilder.buildQuery());
            //判断是否存在改员工认证配置信息
            if (!StringUtils.isEmptyObject(hrEmployeeCertConfDO)) {
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
            response = ResultMessage.SUCCESS.toResponse();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionFactory.buildException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS);
        }
        return response;
    }

    /**
     * 获取员工绑定配置信息
     *
     * @param companyId 公司ID
     * @return
     * @throws BIZException
     */
    public HrEmployeeCertConfDO getHrEmployeeCertConf(Integer companyId) throws BIZException {
        if (companyId == 0) {
            throw ExceptionFactory.buildException(ExceptionCategory.COMPANY_ID_EMPTY);
        }
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(HrEmployeeCertConf.HR_EMPLOYEE_CERT_CONF.COMPANY_ID.getName(), companyId);
        HrEmployeeCertConfDO hrEmployeeCertConfDO = hrEmployeeCertConfDao.getData(queryBuilder.buildQuery());
        if (StringUtils.isEmptyObject(hrEmployeeCertConfDO)) {
            throw ExceptionFactory.buildException(ExceptionCategory.HREMPLOYEECERTCONF_EMPTY);
        }
        return hrEmployeeCertConfDO;
    }

    /**
     * 修改员工绑定配置
     *
     * @param id          绑定配置信息编号
     * @param companyId   公司编号
     * @param authMode    绑定方式
     * @param emailSuffix 如果邮箱是验证字段，则不能为空
     * @param custom      自定义字段内容
     * @param customHint  自定义字段
     * @param questions   问答
     * @return 受影响行数
     */
    @Transactional
    public int updateHrEmployeeCertConf(Integer id, Integer companyId, Integer authMode, String emailSuffix, String custom, String customHint, String questions) throws BIZException {
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("id", id).and("company_id", companyId);
        HrEmployeeCertConfDO hrEmployeeCertConfDO = hrEmployeeCertConfDao.getData(query.buildQuery());
        if (hrEmployeeCertConfDO != null && hrEmployeeCertConfDO.getId() > 0) {
            Integer oldAuthMode = ((int) hrEmployeeCertConfDO.getAuthMode());
            if ((oldAuthMode == 2 || oldAuthMode == 4) && (authMode != 2 && authMode != 4)) {
                query.clear();
                query.select("id");
                query.where("company_id", companyId).and(new Condition("activation", 0, ValueOp.NEQ)).and(new Condition("custom_field", "", ValueOp.EQ));
                List<Integer> employeeIds = userEmployeeDao.getDatas(query.buildQuery(), Integer.class);
                employeeEntity.removeEmployee(employeeIds);
            }
            hrEmployeeCertConfDO.setAuthMode(authMode);
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
            return hrEmployeeCertConfDao.updateData(hrEmployeeCertConfDO);
        }
        return 0;
    }
}
