package com.moseeker.profile.service.impl;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.BaseServiceImpl;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.ProviderResult;

@Service
public class ProfileBasicServicesImpl extends BaseServiceImpl<Basic> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileBasicServicesImpl.class);

	@Autowired
	private ProfileBasicDao<Basic> dao;
	
	public ProfileBasicDao<Basic> getDao() {
		return dao;
	}

	public void setDao(ProfileBasicDao<Basic> dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	public ProviderResult getPagination(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return null;
	}
}
