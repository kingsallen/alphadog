package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.profile.dao.WorksDao;
import com.moseeker.thrift.gen.profile.service.WorksServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Works;

@Service
public class ProfileWorksServicesImpl extends JOOQBaseServiceImpl<Works, ProfileWorksRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileWorksServicesImpl.class);

	@Autowired
	private WorksDao dao;

	public WorksDao getDao() {
		return dao;
	}

	public void setDao(WorksDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Works DBToStruct(ProfileWorksRecord r) {
		return (Works) BeanUtils.DBToStruct(Works.class, r);
	}

	@Override
	protected ProfileWorksRecord structToDB(Works works) throws ParseException {
		return (ProfileWorksRecord) BeanUtils.structToDB(works, ProfileWorksRecord.class);
	}
}
