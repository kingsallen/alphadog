package com.moseeker.profile.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.util.DateUtils;
import com.moseeker.db.dqv4.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

@Service
public class ProfileBasicServicesImpl extends BasicServiceImpl<ProfileBasicRecord, Basic> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileBasicServicesImpl.class);

	@Autowired
	private ProfileBasicDao<ProfileBasicRecord> dao;
	
	public ProfileBasicDao<ProfileBasicRecord> getDao() {
		return dao;
	}

	public void setDao(ProfileBasicDao<ProfileBasicRecord> dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		this.basicDao = this.dao;
	}

	@Override
	protected Basic dbToStruct(ProfileBasicRecord r) {
		Basic basic = null;
		if(r != null) {
			basic = new Basic();
			basic.setIdnumber(r.getIdnumber());
			basic.setAddress(r.getAddress());
			basic.setBirth(DateUtils.dateToNormalDate(r.getBirth()));
			basic.setCreate_time(DateUtils.dateToNormalDate(r.getCreateTime()));
			basic.setGender(r.getGender());
			basic.setHeight(r.getHeight().doubleValue());
			basic.setLocation(r.getLocation());
			basic.setMarriage(r.getMarriage());
			basic.setNationality(r.getNationality());
			basic.setProfile_id(r.getProfileId().intValue());
			basic.setQq(r.getQq());
			basic.setResidence(r.getResidence());
			basic.setUpdate_time(DateUtils.dateToNormalDate(r.getUpdateTime()));
			basic.setWeight(r.getWeight().doubleValue());
			basic.setWeixin(r.getWeixin());
		}
		return basic;
	}

	@Override
	protected ProfileBasicRecord formToDB(Basic basic) throws ParseException {
		ProfileBasicRecord record = null;
		if(basic != null) {
			record = new ProfileBasicRecord();
			record.setAddress(basic.getAddress());
			record.setBirth(new java.sql.Date(DateUtils.nomalDateToDate(basic.getBirth()).getTime()));
			record.setCreateTime(new Timestamp(DateUtils.nomalDateToDate(basic.getCreate_time()).getTime()));
			
		}
		return record;
	}

	@Override
	public List<Basic> getBasics(CommonQuery query, Basic basic)
			throws TException {
		return this.getProfiles(query, basic);
	}

	@Override
	public int postBasics(List<Basic> basics) throws TException {
		return this.postProfiles(basics);
	}

	@Override
	public int putBasics(List<Basic> basics) throws TException {
		return this.putProfiles(basics);
	}

	@Override
	public int delBasics(List<Basic> basics) throws TException {
		return this.delBasics(basics);
	}

	@Override
	public int postBasic(Basic basic) throws TException {
		return this.postProfile(basic);
	}

	@Override
	public int putBasic(Basic basic) throws TException {
		return this.putProfile(basic);
	}

	@Override
	public int delBasic(Basic basic) throws TException {
		return this.delProfile(basic);
	}
}
