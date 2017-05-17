package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.profiledb.ProfileImportDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Awards;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@CounterIface
public class ProfileImportService extends BaseProfileService<ProfileImport, ProfileImportRecord> {

    Logger logger = LoggerFactory.getLogger(ProfileImportService.class);

    @Autowired
    private ProfileImportDao dao;

    @Autowired
    private ProfileProfileDao profileDao;


    public Response postResources(List<ProfileImport> structs) throws TException {
        Response response = super.postResources(dao,structs);
        updateUpdateTime(structs, response);
        return response;
    }


    public Response putResources(List<ProfileImport> structs) throws TException {
        Response response = super.putResources(dao,structs);
        updateUpdateTime(structs, response);
        return response;
    }


    public Response delResources(List<ProfileImport> structs) throws TException {
        Response response = super.delResources(dao,structs);
        updateUpdateTime(structs, response);
        return response;
    }


    public Response delResource(ProfileImport struct) throws TException {
        Response response = super.delResource(dao,struct);
        updateUpdateTime(struct, response);
        return response;
    }


    public Response postResource(ProfileImport struct) throws TException {
        try {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
            ProfileImportRecord repeat = dao.getRecord(qu);
            if (repeat != null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        Response response = super.postResource(dao,struct);
        updateUpdateTime(struct, response);
        return response;
    }


    public Response putResource(ProfileImport struct) throws TException {
        try {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
            ProfileImportRecord repeat = dao.getRecord(qu);
            if (repeat == null) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DATA_NULL);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        Response response = super.putResource(dao,struct);
        updateUpdateTime(struct, response);
        return response;
    }

    public ProfileImportDao getDao() {
        return dao;
    }

    public void setDao(ProfileImportDao dao) {
        this.dao = dao;
    }

    protected ProfileImport DBToStruct(ProfileImportRecord r) {
        return (ProfileImport) BeanUtils.DBToStruct(ProfileImport.class, r);
    }


    protected ProfileImportRecord structToDB(ProfileImport profileImport) throws ParseException {
        return (ProfileImportRecord) BeanUtils.structToDB(profileImport, ProfileImportRecord.class);
    }

    private void updateUpdateTime(ProfileImport profileImport, Response response) {
        if (response.getStatus() == 0 && profileImport != null) {
            List<ProfileImport> profileImports = new ArrayList<>();
            profileImports.add(profileImport);
            updateUpdateTime(profileImports, response);
        }
    }

    private void updateUpdateTime(List<ProfileImport> profileImports, Response response) {
        if (response.getStatus() == 0 && profileImports != null && profileImports.size() > 0) {
            HashSet<Integer> profileIds = new HashSet<>();
            profileImports.forEach(profileImport -> {
                profileIds.add(profileImport.getProfile_id());
            });
            profileDao.updateUpdateTime(profileIds);
        }
    }

    public Response getResource(CommonQuery query) throws TException {
        return super.getResource(dao, query, ProfileImport.class);
    }

    public Response getResources(CommonQuery query) throws TException {
        return getResources(dao,query,Awards.class);
    }

    public Response getPagination(CommonQuery query) throws TException {
        return super.getPagination(dao, query,ProfileImport.class);
    }
}
