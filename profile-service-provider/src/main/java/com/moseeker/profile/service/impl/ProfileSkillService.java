package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileSkillDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfileValidation;
import com.moseeker.entity.biz.ValidationMessage;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Skill;

import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

@Service
@CounterIface
public class ProfileSkillService {

    Logger logger = LoggerFactory.getLogger(ProfileSkillService.class);

    @Autowired
    private ProfileSkillDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

    @Transactional
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

        List<ProfileSkillRecord> records = BeanUtils.structToDB(structs, ProfileSkillRecord.class);

        records = dao.addAllRecord(records);

        Set<Integer> profileIds = new HashSet<>();
        structs.forEach(struct -> {
            profileIds.add(struct.getProfile_id());
        });
        profileIds.forEach(profileId -> {
            profileEntity.reCalculateProfileSkill(profileId, 0);
        });
        profileDao.updateUpdateTime(profileIds);
        return ResponseUtils.success("1");
    }

    @Transactional
    public Response putResources(List<Skill> structs) throws TException {
        int[] result = dao.updateRecords(BeanUtils.structToDB(structs, ProfileSkillRecord.class));
        if (ArrayUtils.contains(result, 1)) {
            structs.forEach(struct -> {
                profileEntity.reCalculateProfileSkill(struct.getProfile_id(), struct.getId());
            });
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
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
        skillRecords = dao.getRecords(qu);
        Set<Integer> profileIds = new HashSet<>();
        if (skillRecords != null && skillRecords.size() > 0) {
            skillRecords.forEach(skill -> {
                profileIds.add(skill.getProfileId().intValue());
            });
        }

        if (skillRecords != null && skillRecords.size() > 0) {
            int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileSkillRecord.class));
            if (ArrayUtils.contains(result, 1)) {
                profileIds.forEach(profileId -> {
                    profileEntity.reCalculateProfileSkill(profileId, 0);
                });
                return ResponseUtils.success("1");
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    @Transactional
    public Response postResource(Skill struct) throws TException {
        ValidationMessage<Skill> vm = ProfileValidation.verifySkill(struct);
        if (!vm.isPass()) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
        }
        ProfileSkillRecord record = dao.addRecord(BeanUtils.structToDB(struct, ProfileSkillRecord.class));
        Set<Integer> profileIds = new HashSet<>();
        profileIds.add(struct.getProfile_id());
        profileDao.updateUpdateTime(profileIds);
        profileEntity.reCalculateProfileSkill(struct.getProfile_id(), struct.getId());
        return ResponseUtils.success(String.valueOf(record.getId()));
    }

    @Transactional
    public Response putResource(Skill struct) throws TException {
        int result = dao.updateRecord(BeanUtils.structToDB(struct, ProfileSkillRecord.class));
        if (result > 0) {
            updateUpdateTime(struct);
            profileEntity.reCalculateProfileSkill(struct.getProfile_id(), struct.getId());
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response delResource(Skill struct) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(struct.getId()));
        ProfileSkillRecord skillRecord = null;
        skillRecord = dao.getRecord(qu);
        if (skillRecord != null) {
            int result = dao.deleteRecord(skillRecord);
            if (result > 0) {
                updateUpdateTime(struct);
                profileEntity.reCalculateProfileSkill(skillRecord.getProfileId().intValue(),
                        skillRecord.getId().intValue());
                return ResponseUtils.success("1");
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
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

    public Response getResource(Query query) throws TException {
        Skill data = dao.getData(query, Skill.class);
        if (data != null) {
            return ResponseUtils.success(data);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    public Response getResources(Query query) throws TException {
        Skill data = dao.getData(query, Skill.class);
        if (data != null) {
            return ResponseUtils.success(data);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    public Response getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ResponseUtils.success(ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas));
    }
}
