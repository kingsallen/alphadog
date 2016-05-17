package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileInternshipRecord;
import com.moseeker.profile.dao.InternshipDao;
import com.moseeker.thrift.gen.profile.service.InternshipServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Internship;

@Service
public class ProfileInternshipServicesImpl extends JOOQBaseServiceImpl<Internship, ProfileInternshipRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileInternshipServicesImpl.class);

	@Autowired
	private InternshipDao dao;

	
	public InternshipDao getDao() {
		return dao;
	}

	public void setDao(InternshipDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Internship DBToStruct(ProfileInternshipRecord r) {
		Map<String, String> equalRules = new HashMap<>();
		equalRules.put("start_date", "start");
		equalRules.put("end_date", "end");
		return (Internship) BeanUtils.DBToStruct(Internship.class, r, equalRules);
	}

	@Override
	protected ProfileInternshipRecord structToDB(Internship internship) throws ParseException {
		Map<String, String> equalRules = new HashMap<>();
		equalRules.put("start_date", "start");
		equalRules.put("end_date", "end");
		return (ProfileInternshipRecord) BeanUtils.structToDB(internship, ProfileInternshipRecord.class, equalRules);
	}
}
