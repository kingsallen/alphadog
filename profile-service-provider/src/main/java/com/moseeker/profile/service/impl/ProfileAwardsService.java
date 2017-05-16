package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileAwardsDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Awards;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@CounterIface
public class ProfileAwardsService extends BaseProfileService<Awards, ProfileAwardsRecord> {

    Logger logger = LoggerFactory.getLogger(ProfileAwardsService.class);

    @Autowired
    private ProfileAwardsDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;

    public Response getResource(CommonQuery query) throws TException {
        return super.getResource(dao, query, Awards.class);
    }

    public Response getResources(CommonQuery query) throws TException {
        return getResources(dao,query,Awards.class);
    }

    public Response postResources(List<Awards> structs) throws TException {
        Response response = super.postResources(dao, structs);
        /* 计算profile完成度 */
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
            Set<Integer> profileIds = new HashSet<>();
            structs.forEach(struct -> {
                profileIds.add(struct.getProfile_id());
            });
            profileDao.updateUpdateTime(profileIds);
            profileIds.forEach(profileId -> {
                completenessImpl.reCalculateProfileAward(profileId, 0);
            });
        }
        return response;
    }

    public Response putResources(List<Awards> structs) throws TException {
        Response response = super.putResources(dao, structs);
        /* 计算profile完成度 */
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
            updateUpdateTime(structs);
            structs.forEach(struct -> {
                completenessImpl.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
            });
        }
        return response;
    }

    public Response delResources(List<Awards> structs) throws TException {
        QueryUtil qu = new QueryUtil();
        StringBuffer sb = new StringBuffer("[");
        structs.forEach(struct -> {
            sb.append(struct.getId());
            sb.append(",");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        qu.addEqualFilter("id", sb.toString());

        List<ProfileAwardsRecord> awardsRecords = null;
        try {
            awardsRecords = dao.getRecords(qu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Set<Integer> profileIds = new HashSet<>();
        if (awardsRecords != null && awardsRecords.size() > 0) {
            awardsRecords.forEach(award -> {
                profileIds.add(award.getProfileId().intValue());
            });
        }
        Response response = super.delResources(dao, structs);
        /* 计算profile完成度 */
        if (response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
            updateUpdateTime(structs);
            profileIds.forEach(profileId -> {
                completenessImpl.reCalculateProfileAward(profileId, 0);
            });
        }
        return response;
    }

    public Response postResource(Awards struct) throws TException {
        Response response = super.postResource(dao, struct);
        /* 计算profile完成度 */
        if (response.getStatus() == 0 && struct != null) {

            Set<Integer> profileIds = new HashSet<>();
            profileIds.add(struct.getProfile_id());
            profileDao.updateUpdateTime(profileIds);

            completenessImpl.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
        }
        return response;
    }

    public Response putResource(Awards struct) throws TException {
        Response response = super.putResource(dao, struct);
		/* 计算profile完成度 */
        if (response.getStatus() == 0 && struct != null) {
            updateUpdateTime(struct);
            completenessImpl.reCalculateProfileAward(struct.getProfile_id(), struct.getId());
        }
        return response;
    }

    public Response delResource(Awards struct) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(struct.getId()));
        ProfileAwardsRecord award = null;
        try {
            award = dao.getRecord(qu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Response response = super.delResource(dao, struct);
		/* 计算profile完成度 */
        if (response.getStatus() == 0 && award != null) {
            updateUpdateTime(struct);
            completenessImpl.reCalculateProfileAward(award.getProfileId().intValue(), award.getId().intValue());
        }
        return response;
    }


    private void updateUpdateTime(List<Awards> awards) {
        HashSet<Integer> awardIds = new HashSet<>();
        awards.forEach(award -> {
            awardIds.add(award.getId());
            logger.error("--------");
            logger.error("-----award.getId():" + award.getId() + "-------");
        });
        dao.updateProfileUpdateTime(awardIds);
    }

    private void updateUpdateTime(Awards award) {
        List<Awards> awards = new ArrayList<>();
        awards.add(award);
        updateUpdateTime(awards);
    }

    public Response getPagination(CommonQuery query) throws TException {
        return super.getPagination(dao, query);
    }
}
