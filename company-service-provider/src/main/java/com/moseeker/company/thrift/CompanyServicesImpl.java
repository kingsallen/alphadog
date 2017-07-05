package com.moseeker.company.thrift;

import com.moseeker.common.exception.Category;
import com.moseeker.company.exception.ExceptionCategory;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.company.struct.CompanyForVerifyEmployee;

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
        } catch (Exception e) {
            if (e instanceof BIZException) {
                throw e;
            } else {
                logger.error(e.getMessage(), e);
                throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
            }
        }
    }

    @Override
    public boolean isGroupCompanies(int companyId) throws BIZException, TException {
        try {
            return service.isGroupCompanies(companyId);
        } catch (Exception e) {
            if (e instanceof BIZException) {
                throw e;
            } else {
                logger.error(e.getMessage(), e);
                throw ExceptionFactory.buildException(Category.PROGRAM_EXCEPTION);
            }
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
        return service.getCompanyOptions(companyId);
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
        return service.addImporterMonitor(comanyId, hraccountId, type, file, status, message, fileName);
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
        return service.getImporterMonitor(comanyId, hraccountId, type);
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
        return service.bindingSwitch(companyId, disable);
    }

    /**
     * 获取员工绑定配置信息
     *
     * @param companyId 公司ID
     * @return
     * @throws
     */
    @Override
    public HrEmployeeCertConfDO getHrEmployeeCertConf(int companyId) throws BIZException, TException {
        return service.getHrEmployeeCertConf(companyId);
    }
}

