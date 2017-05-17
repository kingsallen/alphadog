package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileCompletenessDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Profile;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Service
@CounterIface
public class ProfileService extends BaseProfileService<Profile, ProfileProfileRecord> {

    Logger logger = LoggerFactory.getLogger(ProfileProjectExpService.class);

    @Autowired
    protected ProfileProfileDao dao;

    @Autowired
    protected UserUserDao userDao;

    @Autowired
    protected ProfileCompletenessDao completenessDao;

    @Autowired
    private UserSettingsDao settingDao;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;

    public Response getResource(CommonQuery query) throws TException {
        ProfileProfileRecord record = null;
        try {
            record = dao.getRecord(QueryConvert.commonQueryConvertToQuery(query));
            if (record != null) {
                Profile s = DBToStruct(record);
                if (record.getCompleteness().intValue() == 0 || record.getCompleteness().intValue() == 10) {
                    int completeness = completenessImpl.getCompleteness(record.getUserId().intValue(), record.getUuid(),
                            record.getId().intValue());
                    s.setCompleteness(completeness);
                }
                return ResponseUtils.success(s);
            }

        } catch (Exception e) {
            logger.error("getResource error", e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    public Response postResource(Profile struct) throws TException {
        struct.setUuid(UUID.randomUUID().toString());
        if (!struct.isSetDisable()) {
            struct.setDisable((short) Constant.ENABLE);
        }
        if (struct.getUser_id() > 0) {
            UserUserRecord user = userDao.getUserById(struct.getUser_id());
            if (user == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
            }
        } else {
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
        }
        return super.postResource(dao, struct);
    }

    public Response getCompleteness(int userId, String uuid, int profileId) throws TException {
        int totalComplementness = completenessImpl.getCompleteness(userId, uuid, profileId);
        return ResponseUtils.success(totalComplementness);
    }

    public Response reCalculateUserCompleteness(int userId, String mobile) throws TException {
        completenessImpl.reCalculateUserUserByUserIdOrMobile(userId, mobile);
        int totalComplementness = completenessImpl.getCompleteness(userId, null, 0);
        return ResponseUtils.success(totalComplementness);
    }

    public Response reCalculateUserCompletenessBySettingId(int id) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(id));
        try {
            UserSettingsRecord record = settingDao.getRecord(qu);
            if (record != null) {
                completenessImpl.reCalculateUserUserByUserIdOrMobile(record.getUserId().intValue(), null);
                int totalComplementness = completenessImpl.getCompleteness(record.getUserId().intValue(), null, 0);
                return ResponseUtils.success(totalComplementness);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
    }

    protected Profile DBToStruct(ProfileProfileRecord r) {
        return (Profile) BeanUtils.DBToStruct(Profile.class, r);
    }

    protected ProfileProfileRecord structToDB(Profile profile) throws ParseException {
        return (ProfileProfileRecord) BeanUtils.structToDB(profile, ProfileProfileRecord.class);
    }

    public Response getProfileByApplication(int companyId, int sourceId, int ats_status, boolean recommender, boolean dl_url_required) throws TException {
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        try {
            propertiesUtils.loadResource("setting.properties");
            String downloadUrl = propertiesUtils.get("GENERATE_USER_ID", String.class);
            String password = propertiesUtils.get("GENERATE_USER_PASSWORD", String.class);
            return dao.getResourceByApplication(downloadUrl, password, companyId, sourceId, ats_status, recommender, dl_url_required);
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }


    public Response getResources(CommonQuery query) throws TException {
        return super.getResources(dao, query, Profile.class);
    }

    public Response getPagination(CommonQuery query) throws TException {
        return super.getPagination(dao, query,Profile.class);
    }

    public Response postResources(List<Profile> resources) throws TException {
        return super.postResources(dao, resources);
    }

    public Response putResources(List<Profile> resources) throws TException {
        return super.putResources(dao, resources);
    }

    public Response delResources(List<Profile> resources) throws TException {
        return super.delResources(dao, resources);
    }

    public Response putResource(Profile profile) throws TException {
        return super.putResource(dao, profile);
    }

    public Response delResource(Profile profile) throws TException {
        return super.delResource(dao, profile);
    }
}
