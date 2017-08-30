package com.moseeker.company.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.Category;
import com.moseeker.common.exception.CommonException;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.entity.CompanyConfigEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.company.struct.CompanyCertConf;
import com.moseeker.thrift.gen.company.struct.CompanyForVerifyEmployee;
import com.moseeker.thrift.gen.employee.struct.RewardConfig;

import java.util.ArrayList;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.company.service.impl.CompanyService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices.Iface;
import com.moseeker.thrift.gen.company.struct.CompanyOptions;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrImporterMonitorDO;

import java.util.List;

@Service
public class CompanyServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyService service;

    @Autowired
    private CompanyConfigEntity companyConfigEntity;

    public Response getAllCompanies(CommonQuery query) {
        return service.getAllCompanies(query);
    }

    @Override
    public Response add(Hrcompany company) throws TException {
        return service.add(company);
    }

    @Override
    public Response getResource(CommonQuery query) throws TException {
        return service.getResource(query);
    }

    @Override
    public Response getResources(CommonQuery query) throws TException {
        return service.getResources(query);
    }

    @Override
    public Response getWechat(long companyId, long wechatId) throws TException {
        // TODO Auto-generated method stub
        return service.getWechat(companyId, wechatId);
    }

    @Override
    public List<CompanyForVerifyEmployee> getGroupCompanies(int companyId) throws BIZException, TException {
        try {
            return service.getGroupCompanies(companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public boolean isGroupCompanies(int companyId) throws BIZException, TException {
        try {
            return service.isGroupCompanies(companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 更新公司员工认证配置
     *
     * @param companyId
     * @param authMode
     * @param emailSuffix
     * @param custom
     * @param customHint
     * @param questions
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public boolean updateEmployeeBindConf(int companyId, int authMode, String emailSuffix, String custom, String customHint, String questions, String filePath, String fileName, int type, int hraccountId) throws BIZException, TException {
        try {
            int result = service.updateHrEmployeeCertConf(companyId, authMode, emailSuffix, custom, customHint, questions, filePath, fileName, type, hraccountId);
            if (result > 0) {
                return true;
            }
            return false;
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 获取公司员工认证配置
     *
     * @param companyId 公司ID
     * @return
     * @throws
     */
    @Override
    public CompanyCertConf getHrEmployeeCertConf(int companyId, int type, int accountId) throws BIZException, TException {
        try {
            return service.getHrEmployeeCertConf(companyId, type, accountId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 获取公司积分配置
     *
     * @param companyId
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public List<RewardConfig> getCompanyRewardConf(int companyId) throws BIZException, TException {
        List<RewardConfig> result = new ArrayList<>();
        try {
            result = companyConfigEntity.getRerawConfig(companyId, false);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionFactory.buildException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS);
        }
        return result;
    }

    /**
     * 更新公司积分配置
     *
     * @param companyId
     * @param rewardConfigs
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public Response updateCompanyRewardConf(int companyId, List<RewardConfig> rewardConfigs) throws BIZException, TException {
        try {
            return service.updateCompanyRewardConf(companyId, rewardConfigs);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }


    /**
     * 获取公司部门与职能信息(员工认证补填字段显示)
     *
     * @param companyId
     * @return
     * @throws BIZException
     * @throws TException
     */
    @Override
    public CompanyOptions getCompanyOptions(int companyId) throws BIZException, TException {
        try {
            return service.getCompanyOptions(companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
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
    @Override
    public Response addImporterMonitor(int comanyId, int hraccountId, int type, String file, int status, String message, String fileName) throws BIZException, TException {
        try {
            return service.addImporterMonitor(comanyId, hraccountId, type, file, status, message, fileName);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }

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
    @Override
    public HrImporterMonitorDO getImporterMonitor(int comanyId, int hraccountId, int type) throws BIZException, TException {
        try {
            return service.getImporterMonitor(comanyId, hraccountId, type);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    /**
     * 员工认证开关
     *
     * @param companyId 公司Id
     * @param disable   是否开启 0开启 1关闭
     * @return
     */
    @Override
    public Response bindingSwitch(int companyId, int disable) throws BIZException, TException {
        try {
            return service.bindingSwitch(companyId, disable);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }
}

