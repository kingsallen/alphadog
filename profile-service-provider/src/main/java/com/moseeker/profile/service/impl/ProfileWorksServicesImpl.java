package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.profile.dao.WorksDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WorksServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Works;

@Service
public class ProfileWorksServicesImpl extends JOOQBaseServiceImpl<Works, ProfileWorksRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileWorksServicesImpl.class);

	@Autowired
	private WorksDao dao;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;

	@Override
	public Response postResources(List<Works> structs) throws TException {
		Response response = super.postResources(structs);
		/* 重新计算profile完整度 */
		if(response.getStatus() == 0 && structs != null && structs.size() >0) {
			updateUpdateTime(structs);
			Set<Integer> profileIds = new HashSet<>();
			structs.forEach(struct -> {
				if(struct.getProfile_id() > 0) {
					profileIds.add(struct.getProfile_id());
				}
			});
			profileIds.forEach(profileId -> {
				completenessImpl.reCalculateProfileWorks(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response putResources(List<Works> structs) throws TException {
		Response response = super.putResources(structs);
		/* 重新计算profile完整度 */
		if(response.getStatus() == 0 && structs != null && structs.size() >0) {
			updateUpdateTime(structs);
			structs.forEach(struct -> {
				completenessImpl.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Works> structs) throws TException {
		QueryUtil qu = new QueryUtil();
		StringBuffer sb = new StringBuffer("[");
		structs.forEach(struct -> {
			sb.append(struct.getId());
			sb.append(",");
		});
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		qu.addEqualFilter("id", sb.toString());

		List<ProfileWorksRecord> worksRecords = null;
		try {
			worksRecords = dao.getResources(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Set<Integer> profileIds = new HashSet<>();
		if (worksRecords != null && worksRecords.size() > 0) {
			worksRecords.forEach(works -> {
				profileIds.add(works.getProfileId().intValue());
			});
		}
		Response response = super.delResources(structs);
		/* 重新计算profile完整度 */
		if(response.getStatus() == 0 && profileIds != null && profileIds.size() >0) {
			updateUpdateTime(structs);
			profileIds.forEach(profileId -> {
				completenessImpl.reCalculateProfileWorks(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response postResource(Works struct) throws TException {
		Response response = super.postResource(struct);
		/* 重新计算profile完整度 */
		if(response.getStatus() == 0 && struct != null) {
			updateUpdateTime(struct);
			completenessImpl.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response putResource(Works struct) throws TException {
		Response response = super.putResource(struct);
		/* 重新计算profile完整度 */
		if(response.getStatus() == 0 && struct != null) {
			updateUpdateTime(struct);
			completenessImpl.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response delResource(Works struct) throws TException {
		Response response = super.delResource(struct);
		/* 重新计算profile完整度 */
		if(response.getStatus() == 0 && struct != null) {
			updateUpdateTime(struct);
			completenessImpl.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	public WorksDao getDao() {
		return dao;
	}

	public void setDao(WorksDao dao) {
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
	protected Works DBToStruct(ProfileWorksRecord r) {
		return (Works) BeanUtils.DBToStruct(Works.class, r);
	}

	@Override
	protected ProfileWorksRecord structToDB(Works works) throws ParseException {
		return (ProfileWorksRecord) BeanUtils.structToDB(works, ProfileWorksRecord.class);
	}
	
	private void updateUpdateTime(List<Works> works) {
		Set<Integer> workIds = new HashSet<>();
		works.forEach(work -> {
			workIds.add(work.getId());
		});
		dao.updateProfileUpdateTime(workIds);
	}

	private void updateUpdateTime(Works work) {
		List<Works> works = new ArrayList<>();
		works.add(work);
		updateUpdateTime(works);
	}
}
