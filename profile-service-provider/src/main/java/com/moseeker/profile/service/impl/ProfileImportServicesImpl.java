package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.ProfileImportDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileImportServices.Iface;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;

@Service
public class ProfileImportServicesImpl extends JOOQBaseServiceImpl<ProfileImport, ProfileImportRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileImportServicesImpl.class);

	@Autowired
	private ProfileImportDao dao;

	@Autowired
	private ProfileDao profileDao;

	@Override
	public Response postResources(List<ProfileImport> structs) throws TException {
		Response response = super.postResources(structs);
		updateUpdateTime(structs, response);
		return response;
	}

	@Override
	public Response putResources(List<ProfileImport> structs) throws TException {
		Response response = super.putResources(structs);
		updateUpdateTime(structs, response);
		return response;
	}

	@Override
	public Response delResources(List<ProfileImport> structs) throws TException {
		Response response = super.delResources(structs);
		updateUpdateTime(structs, response);
		return response;
	}

	@Override
	public Response delResource(ProfileImport struct) throws TException {
		Response response = super.delResource(struct);
		updateUpdateTime(struct, response);
		return response;
	}

	@Override
	public Response postResource(ProfileImport struct) throws TException {
		try {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
			ProfileImportRecord repeat = dao.getResource(qu);
			if(repeat != null) {
				return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		Response response = super.postResource(struct);
		updateUpdateTime(struct, response);
		return response;
	}

	@Override
	public Response putResource(ProfileImport struct) throws TException {
		try {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
			ProfileImportRecord repeat = dao.getResource(qu);
			if(repeat == null) {
				return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DATA_NULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		Response response = super.putResource(struct);
		updateUpdateTime(struct, response);
		return response;
	}

	public ProfileImportDao getDao() {
		return dao;
	}

	public void setDao(ProfileImportDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected ProfileImport DBToStruct(ProfileImportRecord r) {
		return (ProfileImport) BeanUtils.DBToStruct(ProfileImport.class, r);
	}

	@Override
	protected ProfileImportRecord structToDB(ProfileImport profileImport) throws ParseException {
		return (ProfileImportRecord) BeanUtils.structToDB(profileImport, ProfileImportRecord.class);
	}
	
	private void updateUpdateTime(ProfileImport profileImport, Response response) {
		if(response.getStatus() == 0 && profileImport != null) {
			List<ProfileImport> profileImports = new ArrayList<>();
			profileImports.add(profileImport);
			updateUpdateTime(profileImports, response);
		}
	}
	
	private void updateUpdateTime(List<ProfileImport> profileImports, Response response) {
		if(response.getStatus() == 0 && profileImports != null && profileImports.size() > 0) {
			HashSet<Integer> profileIds = new HashSet<>();
			profileImports.forEach(profileImport -> {
				profileIds.add(profileImport.getProfile_id());
			});
			profileDao.updateUpdateTime(profileIds);
		}
	}
}
