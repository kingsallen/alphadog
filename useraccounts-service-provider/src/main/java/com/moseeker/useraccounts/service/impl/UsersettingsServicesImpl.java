package com.moseeker.useraccounts.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UsersettingServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.Usersetting;
import com.moseeker.useraccounts.dao.UsersettingDao;

@Service
public class UsersettingsServicesImpl extends JOOQBaseServiceImpl<Usersetting, UserSettingsRecord> implements Iface {

	@Autowired
	protected UsersettingDao dao;
	
	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	public UsersettingDao getDao() {
		return dao;
	}

	public void setDao(UsersettingDao dao) {
		this.dao = dao;
	}
	
	@Override
	public Response postResources(List<Usersetting> structs) throws TException {
		Response response = super.postResources(structs);
		if(response.getStatus() == 0) {
			List<Integer> idArray = new ArrayList<Integer>();
			structs.forEach(settings -> {
				idArray.add(settings.getUser_id());
			});
			dao.updateProfileUpdateTimeByUserId(idArray);
		}
		return response;
	}

	@Override
	public Response putResources(List<Usersetting> structs) throws TException {
		Response response = super.putResources(structs);
		if(response.getStatus() == 0) {
			List<Integer> idArray = new ArrayList<Integer>();
			structs.forEach(settings -> {
				idArray.add(settings.getId());
			});
			dao.updateProfileUpdateTime(idArray);
		}
		return response;
	}

	@Override
	public Response delResources(List<Usersetting> structs) throws TException {
		Response response = super.delResources(structs);
		if(response.getStatus() == 0) {
			List<Integer> idArray = new ArrayList<Integer>();
			structs.forEach(settings -> {
				idArray.add(settings.getId());
			});
			dao.updateProfileUpdateTime(idArray);
		}
		return response;
	}

	@Override
	public Response postResource(Usersetting struct) throws TException {
		Response response = super.postResource(struct);
		if(response.getStatus() == 0) {
			List<Integer> idArray = new ArrayList<Integer>();
			idArray.add(struct.getId());
			dao.updateProfileUpdateTime(idArray);
		}
		return response;
	}

	@Override
	public Response putResource(Usersetting struct) throws TException {
		Response response = super.putResource(struct);
		if(response.getStatus() == 0) {
			List<Integer> idArray = new ArrayList<Integer>();
			idArray.add(struct.getId());
			dao.updateProfileUpdateTime(idArray);
		}
		return response;
	}

	@Override
	public Response delResource(Usersetting struct) throws TException {
		Response response = super.delResource(struct);
		if(response.getStatus() == 0) {
			List<Integer> idArray = new ArrayList<Integer>();
			idArray.add(struct.getId());
			dao.updateProfileUpdateTime(idArray);
		}
		return response;
	}

	@Override
	protected Usersetting DBToStruct(UserSettingsRecord r) {
		return (Usersetting)BeanUtils.DBToStruct(Usersetting.class, r);
	}

	@Override
	protected UserSettingsRecord structToDB(Usersetting usersetting)
			throws ParseException {
		return (UserSettingsRecord)BeanUtils.structToDB(usersetting, UserSettingsRecord.class);
	}
}
