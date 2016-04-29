package com.moseeker.profile.dao;

import java.sql.SQLException;
import java.util.List;

import org.jooq.Record;
import org.jooq.Result;

import com.moseeker.thrift.gen.profile.struct.CommonQuery;

public interface BasicDao<T> {

	Result<Record> getProfiles(CommonQuery query) throws SQLException;
	
	int getProfileCount(CommonQuery query) throws SQLException ;

	int postProfiles(List<T> records) throws SQLException;

	int putProfiles(List<T> records) throws SQLException;

	int delProfiles(List<T> records) throws SQLException;

	int postProfile(T record) throws SQLException;

	int putProfile(T record) throws SQLException;

	int delProfile(T record) throws SQLException;

}
