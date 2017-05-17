package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.crud.Crud;
import com.moseeker.baseorm.dao.profiledb.ProfileCredentialsDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Credentials;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
@CounterIface
public class ProfileCredentialsService extends BaseProfileService<Credentials, ProfileCredentialsRecord> {

    Logger logger = LoggerFactory.getLogger(ProfileCredentialsService.class);

    @Autowired
    private ProfileCredentialsDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;

    public Response getResource(CommonQuery query) throws TException {
        return super.getResource(dao, query, Credentials.class);
    }

    public Response getResources(CommonQuery query) throws TException {
        return super.getResources(dao, query, Credentials.class);
    }

    public Response getPagination(CommonQuery query) throws TException {
        return super.getPagination(dao, query,Credentials.class);
    }

    public Response postResources(List<Credentials> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Iterator<Credentials> ic = structs.iterator();
            while (ic.hasNext()) {
                Credentials credential = ic.next();
                ValidationMessage<Credentials> vm = ProfileValidation.verifyCredential(credential);
                if (!vm.isPass()) {
                    ic.remove();
                }
            }
        }
        Response response = super.postResources(dao, structs);
        /* 重新计算profile完整度 */
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
            Set<Integer> profileIds = new HashSet<>();
            structs.forEach(struct -> {
                if (struct.getProfile_id() > 0)
                    profileIds.add(struct.getProfile_id());
            });
            profileDao.updateUpdateTime(profileIds);

            profileIds.forEach(profileId -> {
                completenessImpl.recalculateProfileCredential(profileId, 0);
            });
        }
        return response;
    }

    public Response putResources(List<Credentials> structs) throws TException {
        Response response = super.putResources(dao, structs);
        /* 计算profile完整度 */
        if (response.getStatus() == 0 && structs != null && structs.size() > 0) {
            Set<Integer> profileIds = new HashSet<>();
            if (structs != null && structs.size() > 0) {
                structs.forEach(credential -> {
                    profileIds.add(credential.getProfile_id());
                });
            }
            updateUpdateTime(structs);
            structs.forEach(struct -> {
                completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
            });
        }
        return response;
    }

    public Response delResources(List<Credentials> structs) throws TException {
        QueryUtil qu = new QueryUtil();
        StringBuffer sb = new StringBuffer("[");
        structs.forEach(struct -> {
            sb.append(struct.getId());
            sb.append(",");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        qu.addEqualFilter("id", sb.toString());

        List<ProfileCredentialsRecord> credentialRecords = null;
        try {
            credentialRecords = dao.getRecords(qu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Set<Integer> profileIds = new HashSet<>();
        if (credentialRecords != null && credentialRecords.size() > 0) {
            credentialRecords.forEach(credential -> {
                profileIds.add(credential.getProfileId().intValue());
            });
        }
        Response response = super.delResources(dao, structs);
		/* 计算profile完整度 */
        if (response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
            updateUpdateTime(structs);
            profileIds.forEach(profileId -> {
                completenessImpl.recalculateProfileCredential(profileId, 0);
            });
        }
        return response;
    }

    public Response postResource(Credentials struct) throws TException {
        ValidationMessage<Credentials> vm = ProfileValidation.verifyCredential(struct);
        if (!vm.isPass()) {
            return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
        }
        Response response = super.postResource(dao, struct);
		/* 计算profile完整度 */
        if (response.getStatus() == 0 && struct != null) {
            Set<Integer> profileIds = new HashSet<>();
            profileIds.add(struct.getProfile_id());
            profileDao.updateUpdateTime(profileIds);

            completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
        }
        return response;
    }

    public Response putResource(Credentials struct) throws TException {
        Response response = super.putResource(dao, struct);
		/* 计算profile完整度 */
        if (response.getStatus() == 0 && struct != null) {
            updateUpdateTime(struct);
            completenessImpl.recalculateProfileCredential(struct.getProfile_id(), struct.getId());
        }
        return response;
    }

    public Response delResource(Credentials struct) throws TException {
        QueryUtil qu = new QueryUtil();
        qu.addEqualFilter("id", String.valueOf(struct.getId()));
        ProfileCredentialsRecord credentialRecord = null;
        try {
            credentialRecord = dao.getRecord(qu);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        Response response = super.delResource(dao, struct);
		/* 计算profile完整度 */
        if (response.getStatus() == 0 && credentialRecord != null) {
            updateUpdateTime(struct);
            completenessImpl.recalculateProfileCredential(credentialRecord.getProfileId().intValue(),
                    credentialRecord.getId().intValue());
        }
        return response;
    }

    protected Credentials DBToStruct(ProfileCredentialsRecord r) {
        return (Credentials) BeanUtils.DBToStruct(Credentials.class, r);
    }

    protected ProfileCredentialsRecord structToDB(Credentials credentials) throws ParseException {
        return (ProfileCredentialsRecord) BeanUtils.structToDB(credentials, ProfileCredentialsRecord.class);
    }

    private void updateUpdateTime(List<Credentials> credentials) {
        Set<Integer> credentialIds = new HashSet<>();
        credentials.forEach(Credential -> {
            credentialIds.add(Credential.getId());
        });
        dao.updateProfileUpdateTime(credentialIds);
    }

    private void updateUpdateTime(Credentials credential) {
        List<Credentials> credentials = new ArrayList<>();
        credentials.add(credential);
        updateUpdateTime(credentials);
    }

}
