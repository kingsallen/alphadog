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

import com.moseeker.common.providerutils.QueryUtil;
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

	public ProfileCompletenessImpl getCompletenessImpl() {
		return completenessImpl;
	}

	public void setCompletenessImpl(ProfileCompletenessImpl completenessImpl) {
		this.completenessImpl = completenessImpl;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	public Response postResources(List<ProjectExp> structs) throws TException {
		Response response = super.postResources(structs);
		if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
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
		if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				/* 计算profile完成度 */
				completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<ProjectExp> structs) throws TException {
		QueryUtil qu = new QueryUtil();
		StringBuffer sb = new StringBuffer("[");
		structs.forEach(struct -> {
			sb.append(struct.getId());
			sb.append(",");
		});
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		qu.addEqualFilter("id", sb.toString());

		List<ProfileProjectexpRecord> projectExpRecords = null;
		try {
			projectExpRecords = dao.getResources(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Set<Integer> profileIds = new HashSet<>();
		if (projectExpRecords != null && projectExpRecords.size() > 0) {
			projectExpRecords.forEach(projectExp -> {
				profileIds.add(projectExp.getProfileId().intValue());
			});
		}
		Response response = super.delResources(structs);
		if (response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
			profileIds.forEach(profileId -> {
				/* 计算profile完成度 */
				completenessImpl.reCalculateProfileProjectExp(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response postResource(ProjectExp struct) throws TException {
		Response response = super.postResource(struct);
		if (response.getStatus() == 0)
			/* 计算profile完成度 */
			completenessImpl.reCalculateProfileProjectExpByProfileId(struct.getProfile_id());
		return response;
	}

	@Override
	public Response putResource(ProjectExp struct) throws TException {
		Response response = super.putResource(struct);
		if (response.getStatus() == 0)
			/* 计算profile完成度 */
			completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
		return response;
	}

	@Override
	public Response delResource(ProjectExp struct) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(struct.getId()));
		ProfileProjectexpRecord projectExpRecord = null;
		try {
			projectExpRecord = dao.getResource(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Response response = super.delResource(struct);
		if (response.getStatus() == 0 && projectExpRecord != null)
			/* 计算profile完成度 */
			completenessImpl.reCalculateProfileProjectExp(projectExpRecord.getProfileId().intValue(),
					projectExpRecord.getId().intValue());
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
