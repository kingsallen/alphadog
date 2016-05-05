package com.moseeker.profile.service.impl;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.BaseServiceImpl;
import com.moseeker.common.util.Pagination;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;
import com.moseeker.thrift.gen.profile.struct.BasicPagination;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.Profile;
import com.moseeker.thrift.gen.profile.struct.ProfilePagination;

@Service
public class ProfileServicesImpl extends BaseServiceImpl<Profile> implements Iface {

	@Autowired
	private ProfileDao<Profile> dao;
	
	@Override
	protected void initDao() {
		//this.dao = profileDao;
	}

	@Override
	public ProfilePagination getPagination(CommonQuery query, Profile profile)
			throws TException {
		Pagination<Profile> pagination = this.getBasePagination(query, profile);
		ProfilePagination bp = new ProfilePagination();
		BeanUtils.copyProperties(pagination, bp);
		return bp;
	}
}
