package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProjectexpDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.OrderBy;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;

import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@CounterIface
public class ProfileProjectExpService {

    Logger logger = LoggerFactory.getLogger(ProfileProjectExpService.class);

    @Autowired
    private ProfileProjectexpDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;

    public Response getResource(Query query) throws TException {
        ProjectExp data = dao.getData(query, ProjectExp.class);
        if (data != null) {
            return ResponseUtils.success(data);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    public Response getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ResponseUtils.success(ProfileUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas));
    }

    public Response getResources(Query query) throws TException {
        // 按照结束时间倒序
        query.getOrders().add(new OrderBy("end_until_now", Order.DESC));
        query.getOrders().add(new OrderBy("start", Order.DESC));


        List<ProjectExp> structs = dao.getDatas(query, ProjectExp.class);

        if (!structs.isEmpty()) {
            return ResponseUtils.success(structs);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    @Transactional
    public Response postResources(List<ProjectExp> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Iterator<ProjectExp> ipe = structs.iterator();
            while (ipe.hasNext()) {
                ProjectExp pe = ipe.next();
                ValidationMessage<ProjectExp> vm = ProfileValidation.verifyProjectExp(pe);
                if (!vm.isPass()) {
                    ipe.remove();
                }
            }
        }
        if (structs != null && structs.size() > 0) {
            List<ProfileProjectexpRecord> records = dao.addAllRecord(structsToDBs(structs));

            Set<Integer> profileIds = new HashSet<Integer>();
            structs.forEach(struct -> {
                profileIds.add(struct.getProfile_id());
            });

            profileDao.updateUpdateTime(profileIds);

            profileIds.forEach(profileId -> {
                /* 计算profile完成度 */
                completenessImpl.reCalculateProfileProjectExpByProfileId(profileId);
            });
            return ResponseUtils.success("1");
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
        }
    }

    @Transactional
    public Response putResources(List<ProjectExp> structs) throws TException {
        int[] result = dao.updateRecords(structsToDBs(structs));
        if (ArrayUtils.contains(result, 1)) {

            updateUpdateTime(structs);

            structs.forEach(struct -> {
                /* 计算profile完成度 */
                completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
            });

            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
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
        projectExpRecords = dao.getRecords(qu);
        Set<Integer> profileIds = new HashSet<>();
        if (projectExpRecords != null && projectExpRecords.size() > 0) {
            projectExpRecords.forEach(projectExp -> {
                profileIds.add(projectExp.getProfileId().intValue());
            });
        }
        if (projectExpRecords != null && projectExpRecords.size() > 0) {
            int[] result = dao.deleteRecords(structsToDBs(structs));
            if (ArrayUtils.contains(result, 1)) {

                updateUpdateTime(structs);

                profileIds.forEach(profileId -> {
                /* 计算profile完成度 */
                    completenessImpl.reCalculateProfileProjectExp(profileId, 0);
                });
                return ResponseUtils.success("1");
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    @Transactional
    public Response postResource(ProjectExp struct) throws TException {
        ValidationMessage<ProjectExp> vm = ProfileValidation.verifyProjectExp(struct);
        if (!vm.isPass()) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
        }
        ProfileProjectexpRecord record = dao.addRecord(structToDB(struct));
        Set<Integer> profileIds = new HashSet<>();
        profileIds.add(struct.getProfile_id());
        profileDao.updateUpdateTime(profileIds);
            /* 计算profile完成度 */
        completenessImpl.reCalculateProfileProjectExpByProfileId(struct.getProfile_id());
        return ResponseUtils.success(String.valueOf(record.getId()));
    }

    @Transactional
    public Response putResource(ProjectExp struct) throws TException {
        int result = dao.updateRecord(structToDB(struct));
        if (result > 0) {
            updateUpdateTime(struct);
            /* 计算profile完成度 */
            completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response delResource(ProjectExp struct) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(struct.getId()));
        ProfileProjectexpRecord projectExpRecord = null;
        projectExpRecord = dao.getRecord(qu);
        if (projectExpRecord != null) {
            int result = dao.deleteRecord(projectExpRecord);
            if (result > 0) {
                updateUpdateTime(struct);
            /* 计算profile完成度 */
                completenessImpl.reCalculateProfileProjectExp(projectExpRecord.getProfileId().intValue(),
                        projectExpRecord.getId().intValue());
                return ResponseUtils.success("1");
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }


    protected ProjectExp DBToStruct(ProfileProjectexpRecord r) {
        Map<String, String> equalRules = new HashMap<>();
        equalRules.put("start", "start_date");
        equalRules.put("end", "end_date");
        return BeanUtils.DBToStruct(ProjectExp.class, r, equalRules);
    }


    protected ProfileProjectexpRecord structToDB(ProjectExp projectExp) {
        Map<String, String> equalRules = new HashMap<>();
        equalRules.put("start", "start_date");
        equalRules.put("end", "end_date");
        return BeanUtils.structToDB(projectExp, ProfileProjectexpRecord.class, equalRules);
    }

    protected List<ProjectExp> DBsToStructs(List<ProfileProjectexpRecord> records) {
        List<ProjectExp> structs = new ArrayList<>();
        if (records != null && records.size() > 0) {
            for (ProfileProjectexpRecord r : records) {
                structs.add(DBToStruct(r));
            }
        }
        return structs;
    }

    protected List<ProfileProjectexpRecord> structsToDBs(List<ProjectExp> records) {
        List<ProfileProjectexpRecord> structs = new ArrayList<>();
        if (records != null && records.size() > 0) {
            for (ProjectExp r : records) {
                structs.add(structToDB(r));
            }
        }
        return structs;
    }

    private void updateUpdateTime(List<ProjectExp> projectExps) {
        Set<Integer> projectExpIds = new HashSet<>();
        projectExps.forEach(projectExp -> {
            projectExpIds.add(projectExp.getId());
        });
        dao.updateProfileUpdateTime(projectExpIds);
    }

    private void updateUpdateTime(ProjectExp projectExp) {
        List<ProjectExp> projectExps = new ArrayList<>();
        projectExps.add(projectExp);
        updateUpdateTime(projectExps);
    }
}
