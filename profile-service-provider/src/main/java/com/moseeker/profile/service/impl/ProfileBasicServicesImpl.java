package com.moseeker.profile.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.thrift.TException;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.DateUtils;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;
import com.moseeker.thrift.gen.profile.struct.ProviderResult;

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
	public ProviderResult getPagination(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return null;
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
			record.setAddress(basic.getAddress());
			record.setBirth(new java.sql.Date(DateUtils.nomalDateToDate(basic.getBirth()).getTime()));
			record.setCreateTime(new Timestamp(DateUtils.nomalDateToDate(basic.getCreate_time()).getTime()));
			record.setGender((byte)basic.getGender());
			record.setHeight(BigDecimal.valueOf(basic.getHeight()));
			record.setIdnumber(basic.getIdnumber());
			record.setMarriage((byte)basic.getMarriage());
			record.setNationality(basic.getNationality());
			record.setProfileId(UInteger.valueOf(basic.getProfile_id()));
			record.setQq(basic.getQq());
			record.setResidence((byte)basic.getResidence());
			record.setResidencetype((byte)basic.getResidencetype());
			record.setUpdateTime(new Timestamp(DateUtils.nomalDateToDate(basic.getUpdate_time()).getTime()));
			record.setWeight(BigDecimal.valueOf(basic.getWeight()));
			record.setWeixin(basic.getWeixin());
		}
		return record;
	}
}
