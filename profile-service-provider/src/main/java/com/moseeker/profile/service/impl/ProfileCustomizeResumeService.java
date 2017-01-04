package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.dao.CustomizeResumeDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;

@Service
@CounterIface
public class ProfileCustomizeResumeService extends JOOQBaseServiceImpl<CustomizeResume, ProfileOtherRecord> {
	
	Logger logger = LoggerFactory.getLogger(ProfileCustomizeResumeService.class);

	@Autowired
	private CustomizeResumeDao dao;
	
	@Autowired
	private ProfileDao profileDao;

	public CustomizeResumeDao getDao() {
		return dao;
	}

	public void setDao(CustomizeResumeDao dao) {
		this.dao = dao;	
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	public Response postResources(List<CustomizeResume> structs) throws TException {
		if(structs != null && structs.size() > 0) {
			Iterator<CustomizeResume> icr = structs.iterator();
			while(icr.hasNext()) {
				CustomizeResume cr = icr.next();
				ValidationMessage<CustomizeResume> vm = verifyCustomizeResume(cr);
				if(!vm.isPass()) {
					icr.remove();
				}
			}
		}
		Response response = super.postResources(structs);
		updateUpdateTime(structs, response);
		return response;
	}

	@Override
	public Response putResources(List<CustomizeResume> structs) throws TException {
		Response response = super.putResources(structs);
		updateUpdateTime(structs, response);
		return response;
	}

	@Override
	public Response delResources(List<CustomizeResume> structs) throws TException {
		Response response = super.delResources(structs);
		updateUpdateTime(structs, response);
		return response;
	}

	@Override
	public Response delResource(CustomizeResume struct) throws TException {
		Response response = super.delResource(struct);
		updateUpdateTime(struct, response);
		return response;
	}

	@Override
	public Response postResource(CustomizeResume struct) throws TException {
		try {
			ValidationMessage<CustomizeResume> vm = verifyCustomizeResume(struct);
			if(!vm.isPass()) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}'}", vm.getResult()));
			}
			
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
			ProfileOtherRecord repeat = dao.getResource(qu);
			if(repeat != null) {
				return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		Response response = super.postResource(struct);
		updateUpdateTime(struct, response);
		return response;
	}

	@Override
	public Response putResource(CustomizeResume struct) throws TException {
		try {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
			ProfileOtherRecord repeat = dao.getResource(qu);
			if(repeat == null) {
				return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DATA_NULL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		Response response = super.putResource(struct);
		updateUpdateTime(struct, response);
		return response;
	}
	
	public ValidationMessage<CustomizeResume> verifyCustomizeResume(CustomizeResume customizeResume) {
		ValidationMessage<CustomizeResume> vm = new ValidationMessage<>();
		if(StringUtils.isNullOrEmpty(customizeResume.getOther())) {
			vm.addFailedElement("其他字段", "未填写其他字段的内容");
		}
		return vm;
	}

	@Override
	protected CustomizeResume DBToStruct(ProfileOtherRecord r) {
		return (CustomizeResume)BeanUtils.DBToStruct(CustomizeResume.class, r);
	}

	@Override
	protected ProfileOtherRecord structToDB(CustomizeResume customizeResume) throws ParseException {
		return (ProfileOtherRecord)BeanUtils.structToDB(customizeResume, ProfileOtherRecord.class);
	}
	
	private void updateUpdateTime(CustomizeResume customizeResume, Response response) {
		if(response.getStatus() == 0) {
			List<CustomizeResume> customizeResumes = new ArrayList<>(1);
			customizeResumes.add(customizeResume);
			updateUpdateTime(customizeResumes, response);
		}
	}
	
	private void updateUpdateTime(List<CustomizeResume> customizeResumes, Response response) {
		if(response.getStatus() == 0) {
			HashSet<Integer> profileIds = new HashSet<>();
			customizeResumes.forEach(customizeResume -> {
				profileIds.add(customizeResume.getProfile_id());
			});
			profileDao.updateUpdateTime(profileIds);
		}
	}
}
