package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProjectexpDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.OrderBy;
import com.moseeker.common.util.query.Query;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Credentials;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
@CounterIface
public class ProfileProjectExpService extends BaseProfileService<ProjectExp, ProfileProjectexpRecord> {

    Logger logger = LoggerFactory.getLogger(ProfileProjectExpService.class);

    @Autowired
    private ProfileProjectexpDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;

    public Response getResource(CommonQuery query) throws TException {
        return super.getResource(dao, query, ProjectExp.class);
    }

    public Response getPagination(CommonQuery query) throws TException {
        return super.getPagination(dao, query,ProjectExp.class);
    }

    public Response getResources(CommonQuery query) throws TException {
        try {
            // 按照结束时间倒序
            Query query1 = QueryConvert.commonQueryConvertToQuery(query);
            query1.getOrders().add(new OrderBy("end_until_now", Order.DESC));
            query1.getOrders().add(new OrderBy("start", Order.DESC));


            List<ProjectExp> structs = dao.getDatas(query1, ProjectExp.class);

            if (!structs.isEmpty()) {
                return ResponseUtils.success(structs);
            }

        } catch (Exception e) {
            logger.error("getResources error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }


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
        Response response = super.postResources(dao,structs);
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
            Set<Integer> profileIds = new HashSet<Integer>();
            structs.forEach(struct -> {
                profileIds.add(struct.getProfile_id());
            });

            profileDao.updateUpdateTime(profileIds);

            profileIds.forEach(profileId -> {
                /* 计算profile完成度 */
                completenessImpl.reCalculateProfileProjectExpByProfileId(profileId);
            });
        }
        return response;
    }


    public Response putResources(List<ProjectExp> structs) throws TException {
        Response response = super.putResources(dao,structs);
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {

            updateUpdateTime(structs);

            structs.forEach(struct -> {
                /* 计算profile完成度 */
                completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
            });
        }
        return response;
    }


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
            projectExpRecords = dao.getRecords(qu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Set<Integer> profileIds = new HashSet<>();
        if (projectExpRecords != null && projectExpRecords.size() > 0) {
            projectExpRecords.forEach(projectExp -> {
                profileIds.add(projectExp.getProfileId().intValue());
            });
        }
        Response response = super.delResources(dao,structs);
        if (response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {

            updateUpdateTime(structs);

            profileIds.forEach(profileId -> {
				/* 计算profile完成度 */
                completenessImpl.reCalculateProfileProjectExp(profileId, 0);
            });
        }
        return response;
    }


    public Response postResource(ProjectExp struct) throws TException {
        ValidationMessage<ProjectExp> vm = ProfileValidation.verifyProjectExp(struct);
        if (!vm.isPass()) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
        }
        Response response = super.postResource(dao,struct);
        if (response.getStatus() == 0) {
            Set<Integer> profileIds = new HashSet<>();
            profileIds.add(struct.getProfile_id());
            profileDao.updateUpdateTime(profileIds);
			/* 计算profile完成度 */
            completenessImpl.reCalculateProfileProjectExpByProfileId(struct.getProfile_id());
        }
        return response;
    }


    public Response putResource(ProjectExp struct) throws TException {
        Response response = super.putResource(dao,struct);
        if (response.getStatus() == 0) {
            updateUpdateTime(struct);
			/* 计算profile完成度 */
            completenessImpl.reCalculateProfileProjectExpByProjectExpId(struct.getId());
        }
        return response;
    }


    public Response delResource(ProjectExp struct) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(struct.getId()));
        ProfileProjectexpRecord projectExpRecord = null;
        try {
            projectExpRecord = dao.getRecord(qu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Response response = super.delResource(dao,struct);
        if (response.getStatus() == 0 && projectExpRecord != null) {
            updateUpdateTime(struct);
			/* 计算profile完成度 */
            completenessImpl.reCalculateProfileProjectExp(projectExpRecord.getProfileId().intValue(),
                    projectExpRecord.getId().intValue());
        }
        return response;
    }


    protected ProjectExp DBToStruct(ProfileProjectexpRecord r) {
        Map<String, String> equalRules = new HashMap<>();
        equalRules.put("start", "start_date");
        equalRules.put("end", "end_date");
        return (ProjectExp) BeanUtils.DBToStruct(ProjectExp.class, r, equalRules);
    }


    protected ProfileProjectexpRecord structToDB(ProjectExp projectExp) throws ParseException {
        Map<String, String> equalRules = new HashMap<>();
        equalRules.put("start", "start_date");
        equalRules.put("end", "end_date");
        return (ProfileProjectexpRecord) BeanUtils.structToDB(projectExp, ProfileProjectexpRecord.class, equalRules);
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
