package com.moseeker.company.thrift;

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
	public Response synchronizeThirdpartyAccount(int id, byte channel) throws TException {
		// TODO Auto-generated method stub
		return service.synchronizeThirdpartyAccount(id, channel);
	}

	@Override
	public Response ifSynchronizePosition(int companyId, int channel) throws TException {
		// TODO Auto-generated method stub
		return service.ifSynchronizePosition(companyId, channel);
	}	
}

