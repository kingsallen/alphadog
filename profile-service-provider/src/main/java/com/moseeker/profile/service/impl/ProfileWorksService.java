package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileWorksDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Works;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@CounterIface
public class ProfileWorksService extends BaseProfileService<Works, ProfileWorksRecord> {

    Logger logger = LoggerFactory.getLogger(ProfileWorksService.class);

    @Autowired
    private ProfileWorksDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;


    public Response postResources(List<Works> structs) throws TException {
        Response response = super.postResources(dao, structs);
        /* 重新计算profile完整度 */
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
            updateUpdateTime(structs);
            Set<Integer> profileIds = new HashSet<>();
            structs.forEach(struct -> {
                if (struct.getProfile_id() > 0) {
                    profileIds.add(struct.getProfile_id());
                }
            });
            profileIds.forEach(profileId -> {
                completenessImpl.reCalculateProfileWorks(profileId, 0);
            });
            profileDao.updateUpdateTime(profileIds);
        }
        return response;
    }


    public Response putResources(List<Works> structs) throws TException {
        Response response = super.putResources(dao, structs);
        /* 重新计算profile完整度 */
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
            updateUpdateTime(structs);
            structs.forEach(struct -> {
                completenessImpl.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
            });
        }
        return response;
    }


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
            worksRecords = dao.getRecords(qu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Set<Integer> profileIds = new HashSet<>();
        if (worksRecords != null && worksRecords.size() > 0) {
            worksRecords.forEach(works -> {
                profileIds.add(works.getProfileId().intValue());
            });
        }
        Response response = super.delResources(dao, structs);
        /* 重新计算profile完整度 */
        if (response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
            updateUpdateTime(structs);
            profileIds.forEach(profileId -> {
                completenessImpl.reCalculateProfileWorks(profileId, 0);
            });
        }
        return response;
    }


    public Response postResource(Works struct) throws TException {
        Response response = super.postResource(dao, struct);
		/* 重新计算profile完整度 */
        if (response.getStatus() == 0 && struct != null) {
            Set<Integer> profileIds = new HashSet<>();
            profileIds.add(struct.getProfile_id());
            profileDao.updateUpdateTime(profileIds);
            completenessImpl.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
        }
        return response;
    }


    public Response putResource(Works struct) throws TException {
        Response response = super.putResource(dao, struct);
		/* 重新计算profile完整度 */
        if (response.getStatus() == 0 && struct != null) {
            updateUpdateTime(struct);
            completenessImpl.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
        }
        return response;
    }


    public Response delResource(Works struct) throws TException {
        Response response = super.delResource(dao, struct);
		/* 重新计算profile完整度 */
        if (response.getStatus() == 0 && struct != null) {
            updateUpdateTime(struct);
            completenessImpl.reCalculateProfileWorks(struct.getProfile_id(), struct.getId());
        }
        return response;
    }

    protected Works DBToStruct(ProfileWorksRecord r) {
        return (Works) BeanUtils.DBToStruct(Works.class, r);
    }


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
