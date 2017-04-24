package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.hrdb.CompanyDao;
import com.moseeker.baseorm.service.HrCompanyService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.service.CompanyDao.Iface;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提供公司数据以及公司相关数据的查询
 * @author wjf
 *
 */
@Service
public class CompanyThriftService implements Iface {
	
	private Logger logger = LoggerFactory.getLogger(CompanyThriftService.class);
	
	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private HrCompanyService hrCompanyService;
	
	@Override
	public HrCompanyDO getCompany(CommonQuery query) throws TException {
		return companyDao.getCompany(query);
	}

	@Override
	public List<Hrcompany> getCompanies(CommonQuery query) throws TException {
		return companyDao.getCompanies(query);
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
    /*
     * (non-Javadoc)
     * @see com.moseeker.thrift.gen.dao.service.CompanyDao.Iface#getHrCompanyConfig(com.moseeker.thrift.gen.common.struct.CommonQuery)
     * auth:zzt
     * time:2016 12 7
     * function:查询hrcompanyconf
     */
	@Override
	public Response getHrCompanyConfig(CommonQuery query){
		return hrCompanyService.getCompanyConf(query);
	}
}
