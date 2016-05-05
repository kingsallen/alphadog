package com.moseeker.profile.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.BaseServiceImpl;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;
import com.moseeker.thrift.gen.profile.struct.BasicPagination;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

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
	}

	@Override
	public BasicPagination getPagination(CommonQuery query, Basic basic)
			throws TException {
		Pagination<Basic> pagination = getBasePagination(query, basic);
		BasicPagination bp = new BasicPagination();
		BeanUtils.copyProperties(pagination, bp);
		return bp;
	}
}
