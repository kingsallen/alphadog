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

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.profile.dao.LanguageDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Language;

@Service
@CounterIface
public class ProfileLanguageService extends JOOQBaseServiceImpl<Language, ProfileLanguageRecord> {

	Logger logger = LoggerFactory.getLogger(ProfileLanguageService.class);

	@Autowired
	private LanguageDao dao;
	
	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;
	
	public LanguageDao getDao() {
		return dao;
	}

	public void setDao(LanguageDao dao) {
		this.dao = dao;
	}

	public ProfileDao getProfileDao() {
		return profileDao;
	}

	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
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
			
			profileDao.updateUpdateTime(profileIds);
		}
		return response;
	}

	@Override
	public Response putResources(List<Language> structs) throws TException {
		Response response = super.putResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			updateUpdateTime(structs);
			structs.forEach(struct -> {
				//计算profile完整度
				completenessImpl.recalculateprofileLanguage(0, struct.getId());
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Language> structs) throws TException {
		//dao.fetchProfileIds(structs);
		if(structs != null && structs.size() > 0) {
			try {
				QueryUtil qu = new QueryUtil();
				StringBuffer sb = new StringBuffer("[");
				structs.forEach(struct -> {
					sb.append(struct.getId());
					sb.append(",");
				});
				sb.deleteCharAt(sb.length()-1);
				sb.append("]");
				qu.addEqualFilter("id", sb.toString());
				
				List<ProfileLanguageRecord> languageRecords = dao.getResources(qu);
				Set<Integer> profileIds = new HashSet<>();
				if(languageRecords != null && languageRecords.size() > 0) {
					languageRecords.forEach(language -> {
						profileIds.add(language.getProfileId().intValue());
					});
				}
				Response response = super.delResources(structs);
				if(response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
					updateUpdateTime(structs);
					profileIds.forEach(profileId -> {
						//计算profile完整度
						completenessImpl.recalculateprofileLanguage(profileId, 0);
					});
				}
				return response;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public Response postResource(Language struct) throws TException {
		Response response = super.postResource(struct);
		if(response.getStatus() == 0) {
			
			Set<Integer> profileIds = new HashSet<>();
			profileIds.add(struct.getProfile_id());
			profileDao.updateUpdateTime(profileIds);
			
			completenessImpl.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response putResource(Language struct) throws TException {
		Response response = super.putResource(struct);
		if(response.getStatus() == 0) {
			updateUpdateTime(struct);
			completenessImpl.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
		}
		return response;
	}

	@Override
	public Response delResource(Language struct) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(struct.getId()));
		ProfileLanguageRecord language = null;
		try {
			language = dao.getResource(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Response response = super.delResource(struct);
		if(response.getStatus() == 0 && language != null) {
			updateUpdateTime(struct);
			completenessImpl.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
		}
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
	
	private void updateUpdateTime(List<Language> languages) {
		Set<Integer> languageIds = new HashSet<>();
		languages.forEach(language -> {
			languageIds.add(language.getId());
		});
		dao.updateProfileUpdateTime(languageIds);
	}

	private void updateUpdateTime(Language language) {
		List<Language> languages = new ArrayList<>();
		languages.add(language);
		updateUpdateTime(languages);
	}
}
