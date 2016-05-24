package com.moseeker.useraccounts.service.impl;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
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
	protected Usersetting DBToStruct(UserSettingsRecord r) {
		return (Usersetting)BeanUtils.DBToStruct(Usersetting.class, r);
	}

	@Override
	protected UserSettingsRecord structToDB(Usersetting usersetting)
			throws ParseException {
		return (UserSettingsRecord)BeanUtils.structToDB(usersetting, UserSettingsRecord.class);
	}
}
