package com.moseeker.baseorm.Thriftservice;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.ProfileDao;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * Created by moseeker on 2017/3/13.
 */
@Service
public class ProfileDaoThriftService implements ProfileDao.Iface {

    @Autowired
    com.moseeker.baseorm.dao.profile.ProfileDao profileDao;
    @Override
    public Response getResourceByApplication(int companyId, int sourceId, int atsStatus, boolean recommender, boolean dl_url_required) throws TException {
        try {
            return profileDao.getResourceByApplication(companyId, sourceId, atsStatus, recommender,dl_url_required);
        } catch (Exception e) {
            e.printStackTrace();
            throw new TException(MessageFormat.format("getResourceByApplication查询错误", "companyId="+companyId+"&sourceId="+sourceId+"&atsStatus="+atsStatus+"&recommender="+recommender));
        }
    }
}
