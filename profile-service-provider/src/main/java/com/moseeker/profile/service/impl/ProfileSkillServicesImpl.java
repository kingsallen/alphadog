package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.profile.dao.SkillDao;
import com.moseeker.thrift.gen.profile.service.SkillServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Skill;

@Service
public class ProfileSkillServicesImpl extends JOOQBaseServiceImpl<Skill, ProfileSkillRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileSkillServicesImpl.class);

	@Autowired
	private SkillDao dao;

	public SkillDao getDao() {
		return dao;
	}

	public void setDao(SkillDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Skill DBToStruct(ProfileSkillRecord r) {
		return (Skill) BeanUtils.DBToStruct(Skill.class, r);
	}

	@Override
	protected ProfileSkillRecord structToDB(Skill skill) throws ParseException {
		return (ProfileSkillRecord) BeanUtils.structToDB(skill, ProfileSkillRecord.class);
	}
}
