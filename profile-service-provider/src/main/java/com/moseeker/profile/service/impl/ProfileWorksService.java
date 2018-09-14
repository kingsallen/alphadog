package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileWorksDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.profile.service.impl.serviceutils.ProfileExtUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Works;

import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@CounterIface
public class ProfileWorksService {

    Logger logger = LoggerFactory.getLogger(ProfileWorksService.class);

    @Autowired
    private ProfileWorksDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileEntity profileEntity;

    @Autowired
    ProfileCompanyTagService profileCompanyTagService;

    public Response getResource(Query query) throws TException {
        Works data = dao.getData(query, Works.class);
        if (data != null) {
            return ResponseUtils.success(data);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    public Response getResources(Query query) throws TException {
        List<Works> dataList = dao.getDatas(query, Works.class);
        if (dataList != null) {
            return ResponseUtils.success(dataList);
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    public Response getPagination(Query query) throws TException {
        int totalRow = dao.getCount(query);
        List<?> datas = dao.getDatas(query);

        return ResponseUtils.success(ProfileExtUtils.getPagination(totalRow, query.getPageNum(), query.getPageSize(), datas));
    }

    @Transactional
    public Response postResources(List<Works> structs) throws TException {
        List<ProfileWorksRecord> records = BeanUtils.structToDB(structs, ProfileWorksRecord.class);
        records = dao.addAllRecord(records);
        /* 重新计算profile完整度 */
        updateUpdateTime(structs);
        Set<Integer> profileIds = new HashSet<>();
        structs.forEach(struct -> {
            if (struct.getProfile_id() > 0) {
                profileIds.add(struct.getProfile_id());
            }
        });
        profileIds.forEach(profileId -> {
            profileEntity.reCalculateProfileWorks(profileId, 0);
            profileCompanyTagService.handlerCompanyTag(profileId);
        });
        profileDao.updateUpdateTime(profileIds);

        return ResponseUtils.success("1");
    }

    @Transactional
    public Response putResources(List<Works> structs) throws TException {
        int[] result = dao.updateRecords(BeanUtils.structToDB(structs, ProfileWorksRecord.class));
        /* 重新计算profile完整度 */
        if (ArrayUtils.contains(result, 1)) {
            updateUpdateTime(structs);
            structs.forEach(struct -> {
                profileEntity.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
                profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
            });
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
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
        worksRecords = dao.getRecords(qu);
        Set<Integer> profileIds = new HashSet<>();
        if (worksRecords != null && worksRecords.size() > 0) {
            worksRecords.forEach(works -> {
                profileIds.add(works.getProfileId().intValue());
            });
        }
        if (worksRecords != null && worksRecords.size() > 0) {
            int[] result = dao.deleteRecords(BeanUtils.structToDB(structs, ProfileWorksRecord.class));
        /* 重新计算profile完整度 */
            if (ArrayUtils.contains(result, 1)) {
                updateUpdateTime(structs);
                profileIds.forEach(profileId -> {
                    profileEntity.reCalculateProfileWorks(profileId, 0);
                    profileCompanyTagService.handlerCompanyTag(profileId);
                });
                return ResponseUtils.success("1");
            }
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }

    @Transactional
    public Response postResource(Works struct) throws TException {
        ProfileWorksRecord record = dao.addRecord(BeanUtils.structToDB(struct, ProfileWorksRecord.class));
        /* 重新计算profile完整度 */
        Set<Integer> profileIds = new HashSet<>();
        profileIds.add(struct.getProfile_id());
        profileDao.updateUpdateTime(profileIds);
        profileEntity.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
        profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
        return ResponseUtils.success(String.valueOf(record.getId()));
    }

    @Transactional
    public Response putResource(Works struct) throws TException {
        int result = dao.updateRecord(BeanUtils.structToDB(struct, ProfileWorksRecord.class));
        /* 重新计算profile完整度 */
        if (result > 0) {
            updateUpdateTime(struct);
            profileEntity.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
            profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    @Transactional
    public Response delResource(Works struct) throws TException {
        int result = dao.deleteRecord(BeanUtils.structToDB(struct, ProfileWorksRecord.class));
        /* 重新计算profile完整度 */
        if (result > 0) {
            updateUpdateTime(struct);
            profileEntity.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
            profileCompanyTagService.handlerCompanyTag(struct.getProfile_id());
            return ResponseUtils.success("1");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
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
