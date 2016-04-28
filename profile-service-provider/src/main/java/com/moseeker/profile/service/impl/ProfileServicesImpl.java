package com.moseeker.profile.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.db.profileDB.tables.records.ProfileRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.Profile;

@Service
public class ProfileServicesImpl implements Iface {

	@Autowired
	private ProfileDao<com.moseeker.db.profileDB.tables.records.ProfileRecord> dao;
	
	@Override
	public List<Profile> getProfiles(CommonQuery query, Profile profile)
			throws TException {
		List<Profile> profiles = new ArrayList<>();
		try {
			//com.moseeker.db.profileDB.tables.records.ProfileRecord record = formToDB(profile);
			
			Result<Record> profileDBs = dao.getProfiles(query);
			if(profileDBs != null && profileDBs.size() > 0) {
				for (Record r : profileDBs) {
					Profile profileResult = dbToStruct((com.moseeker.db.profileDB.tables.records.ProfileRecord)r);
					profiles.add(profileResult);
				}
			}
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		} finally {
			
		}
		return profiles;
	}

	@Override
	public int postProfiles(List<Profile> profiles) throws TException {
		int result = 0;
		try {
			List<ProfileRecord> records = formToDB(profiles);
			result = dao.postProfiles(records);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	@Override
	public int putProfiles(List<Profile> profiles) throws TException {
		int result = 0;
		try {
			List<ProfileRecord> records = formToDB(profiles);
			result = dao.putProfiles(records);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	@Override
	public int delProfiles(List<Profile> profiles) throws TException {
		int result = 0;
		try {
			List<ProfileRecord> records = formToDB(profiles);
			result = dao.delProfiles(records);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	@Override
	public int postProfile(Profile profile) throws TException {
		int result = 0;
		try {
			ProfileRecord record = formToDB(profile);
			result = dao.postProfile(record);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	@Override
	public int putProfile(Profile profile) throws TException {
		int result = 0;
		try {
			ProfileRecord record = formToDB(profile);
			result = dao.putProfile(record);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}

	@Override
	public int delProfile(Profile profile) throws TException {
		int result = 0;
		try {
			ProfileRecord records = formToDB(profile);
			result = dao.delProfile(records);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}
	
	private Profile dbToStruct(com.moseeker.db.profileDB.tables.records.ProfileRecord r) {
		Profile profile = null;
		if(r != null) {
			profile = new Profile();
			profile.setId(r.getId().intValue());
			profile.setUuid(r.getUuid());
			profile.setLang(r.getLang());
			profile.setSource(r.getSource());
			profile.setCompleteness(r.getCompleteness());
			profile.setUser_id(r.getUserId());
		}
		return profile;
	}

	private ProfileRecord formToDB(Profile profile) {
		ProfileRecord record = new ProfileRecord();
		record.setId(UInteger.valueOf(profile.getId()));
		record.setUuid(profile.getUuid());
		record.setLang((byte)profile.getLang());
		record.setSource((byte)profile.getSource());
		record.setCompleteness((byte)profile.getCompleteness());
		record.setUserId(profile.getUser_id());
		return record;
	}
	
	private List<ProfileRecord> formToDB(List<Profile> profiles) {
		List<ProfileRecord> records = new ArrayList<>();
		if(profiles != null && profiles.size() > 0) {
			for(Profile profile : profiles) {
				records.add(formToDB(profile));
			}
		}
		return records;
	}

	public ProfileDao<com.moseeker.db.profileDB.tables.records.ProfileRecord> getDao() {
		return dao;
	}

	public void setDao(
			ProfileDao<com.moseeker.db.profileDB.tables.records.ProfileRecord> dao) {
		this.dao = dao;
	}
}
