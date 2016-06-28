package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.profile.dao.LanguageDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.LanguageServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Language;

@Service
public class ProfileLanguageServicesImpl extends JOOQBaseServiceImpl<Language, ProfileLanguageRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileLanguageServicesImpl.class);

	@Autowired
	private LanguageDao dao;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;
	
	public LanguageDao getDao() {
		return dao;
	}

	public void setDao(LanguageDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}
	
	@Override
	public Response postResources(List<Language> structs) throws TException {
		Response response = super.postResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			Set<Integer> profileIds = new HashSet<>();
			structs.forEach(struct -> {
				profileIds.add(struct.getProfile_id());
			});
			profileIds.forEach(profileId -> {
				//计算profile完整度
				completenessImpl.recalculateprofileLanguage(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response putResources(List<Language> structs) throws TException {
		Response response = super.putResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				//计算profile完整度
				completenessImpl.recalculateprofileLanguage(0, struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Language> structs) throws TException {
		Response response = super.delResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			structs.forEach(struct -> {
				//计算profile完整度
				completenessImpl.recalculateprofileLanguage(0, struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response postResource(Language struct) throws TException {
		Response response = super.postResource(struct);
		if(response.getStatus() == 0)
			completenessImpl.recalculateprofileLanguage(0, struct.getId());
		return response;
	}

	@Override
	public Response putResource(Language struct) throws TException {
		Response response = super.putResource(struct);
		if(response.getStatus() == 0)
			completenessImpl.recalculateprofileLanguage(0, struct.getId());
		return response;
	}

	@Override
	public Response delResource(Language struct) throws TException {
		Response response = super.delResource(struct);
		if(response.getStatus() == 0)
			completenessImpl.recalculateprofileLanguage(0, struct.getId());
		return response;
	}

	@Override
	protected Language DBToStruct(ProfileLanguageRecord r) {
		return (Language) BeanUtils.DBToStruct(Language.class, r);
	}

	@Override
	protected ProfileLanguageRecord structToDB(Language language) throws ParseException {
		return (ProfileLanguageRecord) BeanUtils.structToDB(language, ProfileLanguageRecord.class);
	}
}
