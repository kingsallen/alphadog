package com.moseeker.useraccounts.thrift;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UsersettingServices.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.Usersetting;
import com.moseeker.useraccounts.dao.UsersettingDao;
import com.moseeker.useraccounts.service.impl.UsersettingsService;

@Service
public class UsersettingsServicesImpl implements Iface {

	@Autowired
	private UsersettingsService service;
	
	public Response postResources(List<Usersetting> structs) throws TException {
		return service.postResources(structs);
	}

	public Response putResources(List<Usersetting> structs) throws TException {
		return service.putResources(structs);
	}

	public Response delResources(List<Usersetting> structs) throws TException {
		return service.delResources(structs);
	}

	public Response postResource(Usersetting struct) throws TException {
		return service.postResource(struct);
	}

	public Response putResource(Usersetting struct) throws TException {
		return service.putResource(struct);
	}

	public Response delResource(Usersetting struct) throws TException {
		return service.delResource(struct);
	}

	@Override
	public Response getResource(CommonQuery query) throws TException {
		return service.getResource(query);
	}
}
