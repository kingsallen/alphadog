package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.profiledb.ProfileDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.ProfileProfileDao;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * Created by moseeker on 2017/3/13.
 */
@Service
public class ProfileProfileDaoThriftService implements ProfileProfileDao.Iface {

    @Autowired
    ProfileDao profileDao;

    @Override
    public Response getResourceByApplication(String downloadApi, String password, int companyId, int sourceId, int atsStatus, boolean recommender, boolean dl_url_required) throws TException {
        try {
            return profileDao.getResourceByApplication(downloadApi, password, companyId, sourceId, atsStatus, recommender, dl_url_required);
        } catch (Exception e) {
            throw new TException(MessageFormat.format("getResourceByApplication查询错误:companyId={1}&sourceId={2}&atsStatus={3}&recommender={4}", companyId, sourceId, atsStatus, recommender));
        }
    }

}
