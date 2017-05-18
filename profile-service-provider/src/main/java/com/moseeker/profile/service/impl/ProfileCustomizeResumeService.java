package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.utils.ProfileValidation;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
@CounterIface
public class ProfileCustomizeResumeService extends BaseProfileService<CustomizeResume, ProfileOtherRecord> {

    Logger logger = LoggerFactory.getLogger(ProfileCustomizeResumeService.class);

    @Autowired
    private ProfileOtherDao dao;

    @Autowired
    private ProfileProfileDao profileDao;

    public Response postResources(List<CustomizeResume> structs) throws TException {
        if (structs != null && structs.size() > 0) {
            Iterator<CustomizeResume> icr = structs.iterator();
            while (icr.hasNext()) {
                CustomizeResume cr = icr.next();
                ValidationMessage<CustomizeResume> vm = ProfileValidation.verifyCustomizeResume(cr);
                if (!vm.isPass()) {
                    icr.remove();
                }
            }
        }
        Response response = super.postResources(dao, structs);
        updateUpdateTime(structs, response);
        return response;
    }

    public Response putResources(List<CustomizeResume> structs) throws TException {
        Response response = super.putResources(dao, structs);
        updateUpdateTime(structs, response);
        return response;
    }

    public Response delResources(List<CustomizeResume> structs) throws TException {
        Response response = super.delResources(dao, structs);
        updateUpdateTime(structs, response);
        return response;
    }

    public Response delResource(CustomizeResume struct) throws TException {
        Response response = super.delResource(dao, struct);
        updateUpdateTime(struct, response);
        return response;
    }

    public Response postResource(CustomizeResume struct) throws TException {
        try {
            ValidationMessage<CustomizeResume> vm = ProfileValidation.verifyCustomizeResume(struct);
            if (!vm.isPass()) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vm.getResult()));
            }

            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
            ProfileOtherRecord repeat = dao.getRecord(qu);
            if (repeat != null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
        Response response = super.postResource(dao, struct);
        updateUpdateTime(struct, response);
        return response;
    }

    public Response putResource(CustomizeResume struct) throws TException {
        try {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
            ProfileOtherRecord repeat = dao.getRecord(qu);
            if (repeat == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DATA_NULL);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {
            //do nothing
        }
        Response response = super.putResource(dao, struct);
        updateUpdateTime(struct, response);
        return response;
    }

    private void updateUpdateTime(CustomizeResume customizeResume, Response response) {
        if (response.getStatus() == 0) {
            List<CustomizeResume> customizeResumes = new ArrayList<>(1);
            customizeResumes.add(customizeResume);
            updateUpdateTime(customizeResumes, response);
        }
    }

    private void updateUpdateTime(List<CustomizeResume> customizeResumes, Response response) {
        if (response.getStatus() == 0) {
            HashSet<Integer> profileIds = new HashSet<>();
            customizeResumes.forEach(customizeResume -> {
                profileIds.add(customizeResume.getProfile_id());
            });
            profileDao.updateUpdateTime(profileIds);
        }
    }
}
