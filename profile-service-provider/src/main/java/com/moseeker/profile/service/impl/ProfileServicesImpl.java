package com.moseeker.profile.service.impl;

import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.BaseServiceImpl;
import com.moseeker.common.util.Pagination;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
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
	public ProfilePagination getPagination(CommonQuery query, Profile profile)
			throws TException {
		Pagination<Profile> pagination = this.getBasePagination(query, profile);
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
}
