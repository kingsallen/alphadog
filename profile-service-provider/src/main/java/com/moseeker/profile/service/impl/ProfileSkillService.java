package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileSkillDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Awards;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;
import com.moseeker.thrift.gen.profile.struct.Skill;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
@CounterIface
public class ProfileSkillService extends BaseProfileService<Skill, ProfileSkillRecord> {

    Logger logger = LoggerFactory.getLogger(ProfileSkillService.class);

    @Autowired
    private ProfileSkillDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;


    public Response postResources(List<Skill> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Iterator<Skill> is = structs.iterator();
            while (is.hasNext()) {
                Skill skill = is.next();
                ValidationMessage<Skill> vm = ProfileValidation.verifySkill(skill);
                if (!vm.isPass()) {
                    is.remove();
                }
            }
        }
        Response response = super.postResources(dao, structs);
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
            Set<Integer> profileIds = new HashSet<>();
            structs.forEach(struct -> {
                profileIds.add(struct.getProfile_id());
            });
            profileIds.forEach(profileId -> {
                completenessImpl.reCalculateProfileSkill(profileId, 0);
            });
            profileDao.updateUpdateTime(profileIds);
        }
        return response;
    }


    public Response putResources(List<Skill> structs) throws TException {
        Response response = super.putResources(dao, structs);
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
            structs.forEach(struct -> {
                completenessImpl.reCalculateProfileSkill(struct.getProfile_id(), struct.getId());
            });
        }
        return response;
    }


    public Response delResources(List<Skill> structs) throws TException {
        QueryUtil qu = new QueryUtil();
        StringBuffer sb = new StringBuffer("[");
        structs.forEach(struct -> {
            sb.append(struct.getId());
            sb.append(",");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        qu.addEqualFilter("id", sb.toString());

        List<ProfileSkillRecord> skillRecords = null;
        try {
            skillRecords = dao.getRecords(qu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Set<Integer> profileIds = new HashSet<>();
        if (skillRecords != null && skillRecords.size() > 0) {
            skillRecords.forEach(skill -> {
                profileIds.add(skill.getProfileId().intValue());
            });
        }
        Response response = super.delResources(dao, structs);
        if (response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
            profileIds.forEach(profileId -> {
                completenessImpl.reCalculateProfileSkill(profileId, 0);
            });
        }
        return response;
    }


    public Response postResource(Skill struct) throws TException {
        ValidationMessage<Skill> vm = ProfileValidation.verifySkill(struct);
        if (!vm.isPass()) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
        }
        Response response = super.postResource(dao, struct);
        if (response.getStatus() == 0) {
            Set<Integer> profileIds = new HashSet<>();
            profileIds.add(struct.getProfile_id());
            profileDao.updateUpdateTime(profileIds);
            completenessImpl.reCalculateProfileSkill(struct.getProfile_id(), struct.getId());
        }
        return response;
    }


    public Response putResource(Skill struct) throws TException {
        Response response = super.putResource(dao, struct);
        if (response.getStatus() == 0) {
            updateUpdateTime(struct);
            completenessImpl.reCalculateProfileSkill(struct.getProfile_id(), struct.getId());
        }
        return response;
    }


    public Response delResource(Skill struct) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(struct.getId()));
        ProfileSkillRecord skillRecord = null;
        try {
            skillRecord = dao.getRecord(qu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Response response = super.delResource(dao, struct);
        if (response.getStatus() == 0 && skillRecord != null) {
            updateUpdateTime(struct);
            completenessImpl.reCalculateProfileSkill(skillRecord.getProfileId().intValue(),
                    skillRecord.getId().intValue());
        }
        return response;
    }


    protected Skill DBToStruct(ProfileSkillRecord r) {
        return (Skill) BeanUtils.DBToStruct(Skill.class, r);
    }


    protected ProfileSkillRecord structToDB(Skill skill) throws ParseException {
        return (ProfileSkillRecord) BeanUtils.structToDB(skill, ProfileSkillRecord.class);
    }

    private void updateUpdateTime(List<Skill> skills) {
        Set<Integer> skillIds = new HashSet<>();
        skills.forEach(skill -> {
            skillIds.add(skill.getId());
        });
        dao.updateProfileUpdateTime(skillIds);
    }

    private void updateUpdateTime(Skill skill) {
        List<Skill> skills = new ArrayList<>();
        skills.add(skill);
        updateUpdateTime(skills);
    }

    public Response getResource(CommonQuery query) throws TException {
        return super.getResource(dao, query, Skill.class);
    }

    public Response getResources(CommonQuery query) throws TException {
        return getResources(dao,query,Awards.class);
    }

    public Response getPagination(CommonQuery query) throws TException {
        return super.getPagination(dao, query,Skill.class);
    }
}
