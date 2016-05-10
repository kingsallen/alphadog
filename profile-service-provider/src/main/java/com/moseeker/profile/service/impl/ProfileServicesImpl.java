package com.moseeker.profile.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.BaseServiceImpl;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Education;
import com.moseeker.thrift.gen.profile.struct.Profile;

@Service
public class ProfileServicesImpl extends BaseServiceImpl<Profile> implements Iface {

	@Autowired
	protected ProfileDao<Profile> dao;
	
	@Override
	protected void initDao() {
		super.dao = this.dao;
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
