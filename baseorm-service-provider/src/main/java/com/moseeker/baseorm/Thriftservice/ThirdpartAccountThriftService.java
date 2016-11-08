package com.moseeker.baseorm.Thriftservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.ThirdPartPositionDao;
import com.moseeker.baseorm.service.ThirdpartAccountService;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService.Iface;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartAccount;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartPosition;
@Service
public class ThirdpartAccountThriftService implements Iface{
	@Autowired
	private ThirdpartAccountService service;
	@Autowired
	private ThirdPartPositionDao dao;
	@Override
	public Response addThirdPartAccount(ThirdPartAccount account) throws TException {
		// TODO Auto-generated method stub
		return service.add_ThirdPartAccount(account);
	}

	@Override
	public Response updateThirdPartAccount(ThirdPartAccount account) throws TException {
		// TODO Auto-generated method stub
		return service.update_ThirdPartAccount(account);
	}

	@Override
	public Response addThirdPartPosition(ThirdPartPosition position) throws TException {
		// TODO Auto-generated method stub
		return service.add_ThirdPartPosition(position);
	}

	@Override
	public Response updateThirdPartPosition(ThirdPartPosition position) throws TException {
		// TODO Auto-generated method stub
		return service.update_ThirdPartPosition(position);
	}

	@Override
	public Response addThirdPartPositions(List<ThirdPartPosition> positions)  {
		// TODO Auto-generated method stub
		return service.add_ThirdPartPositions(positions);
	}

	@Override
	public Response updateThirdPartPositions(List<ThirdPartPosition> positions) throws TException {
		// TODO Auto-generated method stub
		return service.update_ThirdPartPositions(positions);
	}

	@Override
	public Response getSingleThirdPartAccount(int companyId) throws TException {
		// TODO Auto-generated method stub
		Response result= service.getSingleAccountByCompanyId(companyId);
		return result;
	}

}
