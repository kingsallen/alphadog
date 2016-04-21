package com.moseeker.profile.dao;

import java.sql.SQLException;
import java.util.List;

import org.jooq.Record;
import org.jooq.Result;

import com.moseeker.db.profileDB.tables.records.ProfileRecord;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

public interface ProfileDao<T> {

	Result<Record> getProfiles(CommonQuery query, ProfileRecord record) throws SQLException ;

	int postProfiles(List<ProfileRecord> records) throws SQLException;

	int putProfiles(List<ProfileRecord> records) throws SQLException;

	int delProfiles(List<ProfileRecord> records) throws SQLException;

	int postProfile(ProfileRecord record) throws SQLException;

	int putProfile(ProfileRecord record) throws SQLException;

	int delProfile(ProfileRecord records) throws SQLException;

}
