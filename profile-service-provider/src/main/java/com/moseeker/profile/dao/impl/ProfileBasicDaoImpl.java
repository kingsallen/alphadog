package com.moseeker.profile.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.DateUtils;
import com.moseeker.db.profiledb.tables.ProfileBasic;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.thrift.gen.profile.struct.Basic;

@Repository
public class ProfileBasicDaoImpl extends BaseDaoImpl<ProfileBasicRecord, ProfileBasic, Basic> implements
	ProfileBasicDao<Basic> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileBasic.PROFILE_BASIC;
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
