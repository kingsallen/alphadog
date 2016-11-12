package com.moseeker.company.thrift;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices.Iface;
import com.moseeker.thrift.gen.company.struct.Hrcompany;

@Service
public class ThriftCompanyService implements Iface {
	
	@Autowired
    protected CompanyServicesImpl companyService;

	@Override
	public Response getResource(CommonQuery query) throws TException {
		return companyService.getResource(query);
	}

	@Override
	public Response getResources(CommonQuery query) throws TException {
		return companyService.getResources(query);
	}

	@Override
	public Response getAllCompanies(CommonQuery query) throws TException {
		return companyService.getAllCompanies(query);
	}

	@Override
	public Response add(Hrcompany company) throws TException {
		return companyService.add(company);
	}

	@Override
	public Response getWechat(long companyId, long wechatId) throws TException {
		// TODO Auto-generated method stub
		return companyService.getWechat(companyId, wechatId);
	}

}
