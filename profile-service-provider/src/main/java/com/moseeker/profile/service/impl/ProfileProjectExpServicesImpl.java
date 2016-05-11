package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.profile.dao.ProjectExpDao;
import com.moseeker.thrift.gen.profile.service.ProjectExpServices.Iface;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;

@Service
public class ProfileProjectExpServicesImpl extends JOOQBaseServiceImpl<ProjectExp, ProfileProjectexpRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileProjectExpServicesImpl.class);

	@Autowired
	private ProjectExpDao dao;
	
	public ProjectExpDao getDao() {
		return dao;
	}

	public void setDao(ProjectExpDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected ProjectExp DBToStruct(ProfileProjectexpRecord r) {
		return (ProjectExp) BeanUtils.DBToStruct(ProjectExp.class, r);
	}

	@Override
	protected ProfileProjectexpRecord structToDB(ProjectExp projectExp) throws ParseException {
		return (ProfileProjectexpRecord) BeanUtils.structToDB(projectExp, ProfileProjectexpRecord.class);
	}
}
