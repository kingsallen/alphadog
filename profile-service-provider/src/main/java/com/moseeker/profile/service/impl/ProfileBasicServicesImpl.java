package com.moseeker.profile.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.DateUtils;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;

@Service
public class ProfileBasicServicesImpl extends JOOQBaseServiceImpl<Basic, ProfileBasicRecord> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileBasicServicesImpl.class);

	@Autowired
	private ProfileBasicDao dao;
	
	public ProfileBasicDao getDao() {
		return dao;
	}

	public void setDao(ProfileBasicDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Basic DBToStruct(ProfileBasicRecord r) {
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
	protected ProfileBasicRecord structToDB(Basic basic)
			throws ParseException {
		ProfileBasicRecord record = null;
		if(basic != null) {
			record = new ProfileBasicRecord();
			if(basic.isSetAddress()) {
				record.setAddress(basic.getAddress());
			}
			if(basic.isSetBirth() && basic.getBirth() != null) {
				record.setBirth(new java.sql.Date(DateUtils.nomalDateToDate(basic.getBirth()).getTime()));
			}
			if(basic.isSetCreate_time() && basic.getCreate_time() != null) {
				record.setCreateTime(new Timestamp(DateUtils.nomalDateToDate(basic.getCreate_time()).getTime()));
			}
			if(basic.isSetLocation()) {
				record.setLocation(basic.getLocation());
			}
			if(basic.isSetGender()) {
				record.setGender((byte)basic.getGender());
			}
			if(basic.isSetHeight()) {
				record.setHeight(BigDecimal.valueOf(basic.getHeight()));
			}
			if(basic.isSetIdnumber()) {
				record.setIdnumber(basic.getIdnumber());
			}
			if(basic.isSetMarriage()) {
				record.setMarriage((byte)basic.getMarriage());
			}
			if(basic.isSetNationality()) {
				record.setNationality(basic.getNationality());
			}
			if(basic.isSetProfile_id()) {
				record.setProfileId(UInteger.valueOf(basic.getProfile_id()));
			}
			if(basic.isSetQq()) {
				record.setQq(basic.getQq());
			}
			if(basic.isSetResidence()) {
				record.setResidence((byte)basic.getResidence());
			}
			if(basic.isSetResidencetype()) {
				record.setResidencetype((byte)basic.getResidencetype());
			}
			if(basic.isSetUpdate_time() && basic.getUpdate_time() != null) {
				record.setUpdateTime(new Timestamp(DateUtils.nomalDateToDate(basic.getUpdate_time()).getTime()));
			}
			if(basic.isSetWeight()) {
				record.setWeight(BigDecimal.valueOf(basic.getWeight()));
			}
			if(basic.isSetWeight()) {
				record.setWeixin(basic.getWeixin());
			}
		}
		return record;
	}
}
