package com.moseeker.company.thrift;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.Category;
import com.moseeker.company.exception.ExceptionFactory;
import com.moseeker.entity.CompanyConfigEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.company.struct.CompanyForVerifyEmployee;
import com.moseeker.thrift.gen.employee.struct.Result;
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
import com.moseeker.thrift.gen.company.struct.Hrcompany;

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

    @Override
    public boolean updateEmployeeBindConf(int id, int companyId, int authMode, String emailSuffix, String custom, String customHint, String questions) throws BIZException, TException {
        try {
            int result = service.updateHrEmployeeCertConf(id, companyId, authMode, emailSuffix, custom, customHint, questions);
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionFactory.buildException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS);
        }
        return false;
    }

    @Override
    public List<RewardConfig> getCompanyRewardConf(int companyId) throws BIZException, TException {
        List<RewardConfig> result = new ArrayList<>();
        try {
            result = companyConfigEntity.getRerawConfig(companyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionFactory.buildException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS);
        }
        return result;
    }

}

