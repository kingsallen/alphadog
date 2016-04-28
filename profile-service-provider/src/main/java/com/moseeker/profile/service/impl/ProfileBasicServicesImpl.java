package com.moseeker.profile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.jooq.types.UInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.db.dqv4.tables.records.ProfileBasicRecord;
import com.moseeker.db.dqv4.tables.records.ProfileProfileRecord;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.Profile;

@Service
public class ProfileBasicServicesImpl implements Iface {

	@Autowired
	private ProfileBasicDao<ProfileBasicRecord> dao;
	
	@Override
	public List<Basic> getBasics(CommonQuery query, Basic basic)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int postBasics(List<Basic> basics) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int putBasics(List<Basic> basics) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delBasics(List<Basic> basics) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int postBasic(Basic basic) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int putBasic(Basic basic) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delBasic(Basic basic) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/*@Override
	public List<Basic> getProfiles(CommonQuery query, Basic basic)
			throws TException {
		List<Basic> profiles = new ArrayList<>();
		try {
			//com.moseeker.db.dqv4.tables.records.ProfileProfileRecord record = formToDB(profile);
			
			Result<Record> profileDBs = dao.getProfiles(query);
			if(profileDBs != null && profileDBs.size() > 0) {
				for (Record r : profileDBs) {
					Profile profileResult = dbToStruct((com.moseeker.db.profileDB.tables.records.ProfileProfileRecord)r);
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
			List<ProfileProfileRecord> records = formToDB(profiles);
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
			List<ProfileProfileRecord> records = formToDB(profiles);
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
			List<ProfileProfileRecord> records = formToDB(profiles);
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
			ProfileProfileRecord record = formToDB(profile);
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
			ProfileProfileRecord record = formToDB(profile);
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
			ProfileProfileRecord records = formToDB(profile);
			result = dao.delProfile(records);
		} catch (SQLException e) {
			throw new TException(e.getMessage());
		}
		return result;
	}*/
	
	private Profile dbToStruct(com.moseeker.db.dqv4.tables.records.ProfileProfileRecord r) {
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

	private ProfileProfileRecord formToDB(Profile profile) {
		ProfileProfileRecord record = new ProfileProfileRecord();
		record.setId(UInteger.valueOf(profile.getId()));
		record.setUuid(profile.getUuid());
		record.setLang((byte)profile.getLang());
		record.setSource((byte)profile.getSource());
		record.setCompleteness((byte)profile.getCompleteness());
		record.setUserId(profile.getUser_id());
		return record;
	}
	
	private List<ProfileProfileRecord> formToDB(List<Profile> profiles) {
		List<ProfileProfileRecord> records = new ArrayList<>();
		if(profiles != null && profiles.size() > 0) {
			for(Profile profile : profiles) {
				records.add(formToDB(profile));
			}
		}
		return records;
	}

	public ProfileBasicDao<ProfileBasicRecord> getDao() {
		return dao;
	}

	public void setDao(ProfileBasicDao<ProfileBasicRecord> dao) {
		this.dao = dao;
	}
}
