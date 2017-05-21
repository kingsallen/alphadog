package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileLanguageDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Awards;
import com.moseeker.thrift.gen.profile.struct.Language;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
@CounterIface
public class ProfileLanguageService extends BaseProfileService<Language, ProfileLanguageRecord> {

	Logger logger = LoggerFactory.getLogger(ProfileLanguageService.class);

	@Autowired
	private ProfileLanguageDao dao;

	@Autowired
	private ProfileProfileDao profileDao;

	@Autowired
	private ProfileCompletenessImpl completenessImpl;


	public Response postResources(List<Language> structs) throws TException {
		if(structs != null && structs.size() > 0) {
			Iterator<Language> ic = structs.iterator();
			while(ic.hasNext()) {
				Language language = ic.next();
				ValidationMessage<Language> vm = ProfileValidation.verifyLanguage(language);
				if(!vm.isPass()) {
					ic.remove();
				}
			}
		}
		Response response = super.postResources(dao,structs);
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


	public Response putResources(List<Language> structs) throws TException {
		Response response = super.putResources(dao,structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			updateUpdateTime(structs);
			structs.forEach(struct -> {
				//计算profile完整度
				completenessImpl.recalculateprofileLanguage(0, struct.getId());
			});
		}
		return response;
	}


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

				List<ProfileLanguageRecord> languageRecords = dao.getRecords(qu);
				Set<Integer> profileIds = new HashSet<>();
				if(languageRecords != null && languageRecords.size() > 0) {
					languageRecords.forEach(language -> {
						profileIds.add(language.getProfileId().intValue());
					});
				}
				Response response = super.delResources(dao,structs);
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


	public Response postResource(Language struct) throws TException {
		ValidationMessage<Language> vm = ProfileValidation.verifyLanguage(struct);
		if(!vm.isPass()) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
		}
		Response response = super.postResource(dao,struct);
		if(response.getStatus() == 0) {

			Set<Integer> profileIds = new HashSet<>();
			profileIds.add(struct.getProfile_id());
			profileDao.updateUpdateTime(profileIds);

			completenessImpl.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
		}
		return response;
	}


	public Response putResource(Language struct) throws TException {
		Response response = super.putResource(dao,struct);
		if(response.getStatus() == 0) {
			updateUpdateTime(struct);
			completenessImpl.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
		}
		return response;
	}


	public Response delResource(Language struct) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(struct.getId()));
		ProfileLanguageRecord language = null;
		try {
			language = dao.getRecord(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Response response = super.delResource(dao,struct);
		if(response.getStatus() == 0 && language != null) {
			updateUpdateTime(struct);
			completenessImpl.recalculateprofileLanguage(struct.getProfile_id(), struct.getId());
		}
		return response;
	}


	protected Language DBToStruct(ProfileLanguageRecord r) {
		return (Language) BeanUtils.DBToStruct(Language.class, r);
	}


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

	public Response getResource(CommonQuery query) throws TException {
		return super.getResource(dao, query, Language.class);
	}

	public Response getResources(CommonQuery query) throws TException {
		return getResources(dao,query,Awards.class);
	}

	public Response getPagination(CommonQuery query) throws TException {
		return super.getPagination(dao, query,Language.class);
	}
}
