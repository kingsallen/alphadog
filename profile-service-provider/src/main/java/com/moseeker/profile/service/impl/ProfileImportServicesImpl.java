package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.db.profiledb.tables.records.ProfileImportRecord;
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
		return super.postResource(struct);
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
		return super.putResource(struct);
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
}
