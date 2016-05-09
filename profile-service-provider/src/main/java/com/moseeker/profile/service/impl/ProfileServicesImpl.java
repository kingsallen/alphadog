package com.moseeker.profile.service.impl;

import org.apache.thrift.TException;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.BaseServiceImpl;
import com.moseeker.common.util.Pagination;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.Education;
import com.moseeker.thrift.gen.profile.struct.Profile;
import com.moseeker.thrift.gen.profile.struct.ProfilePagination;

@Service
public class ProfileServicesImpl extends BaseServiceImpl<Profile> implements Iface {

	@Autowired
	protected ProfileDao<Profile> dao;
	
	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	public ProfilePagination getPagination(CommonQuery query)
			throws TException {
		Pagination<Profile> pagination = this.getBasePagination(query);
		ProfilePagination bp = new ProfilePagination();
		BeanUtils.copyProperties(pagination, bp);
		return bp;
	}

	public ProfileDao<Profile> getDao() {
		return dao;
	}

	public void setDao(ProfileDao<Profile> dao) {
		this.dao = dao;
	}
	
	@Test
	public void structTest() {
		Education education = new Education();
		java.lang.reflect.Field[] fields = education.getClass().getFields();
		for(int i=0; i< fields.length; i++) {
			System.out.println(fields[i].getName());
		}
	}
}
