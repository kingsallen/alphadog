package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.profile.dao.ProjectExpDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProjectExpServices.Iface;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;

@Service
public class ProfileProjectExpServicesImpl extends JOOQBaseServiceImpl<ProjectExp, ProfileProjectexpRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileProjectExpServicesImpl.class);

	@Autowired
	private ProjectExpDao dao;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;
	
	public ProjectExpDao getDao() {
		return dao;
	}

	public void setDao(ProjectExpDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	public Response postResources(List<ProjectExp> structs) throws TException {
		Response response = super.postResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			Set<Integer> profileIds = new HashSet<Integer>();
			structs.forEach(struct -> {
				profileIds.add(struct.getProfile_id());
			});
			profileIds.forEach(profileId -> {
				/* 计算profile完成度 */
				completenessImpl.reCalculateProfileProjectExpByProfileId(profileId);
			});
		}
		return response;
	}

	@Override
	public Response putResources(List<ProjectExp> structs) throws TException {
		Response response = super.putResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				/* 计算profile完成度 */
				completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<ProjectExp> structs) throws TException {
		Response response = super.delResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				/* 计算profile完成度 */
				completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response postResource(ProjectExp struct) throws TException {
		Response response = super.postResource(struct);
		if(response.getStatus() == 0)
			/* 计算profile完成度 */
			completenessImpl.reCalculateProfileProjectExpByProfileId(struct.getProfile_id());
		return response;
	}

	@Override
	public Response putResource(ProjectExp struct) throws TException {
		Response response = super.putResource(struct);
		if(response.getStatus() == 0)
			/* 计算profile完成度 */
			completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
		return response;
	}

	@Override
	public Response delResource(ProjectExp struct) throws TException {
		Response response = super.delResource(struct);
		if(response.getStatus() == 0)
			/* 计算profile完成度 */
			completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
		return response;
	}

	@Override
	protected ProjectExp DBToStruct(ProfileProjectexpRecord r) {
		Map<String, String> equalRules = new HashMap<>();
		equalRules.put("start_date", "start");
		equalRules.put("end_date", "end");
		return (ProjectExp) BeanUtils.DBToStruct(ProjectExp.class, r, equalRules);
	}

	@Override
	protected ProfileProjectexpRecord structToDB(ProjectExp projectExp) throws ParseException {
		Map<String, String> equalRules = new HashMap<>();
		equalRules.put("start_date", "start");
		equalRules.put("end_date", "end");
		return (ProfileProjectexpRecord) BeanUtils.structToDB(projectExp, ProfileProjectexpRecord.class, equalRules);
	}
}
