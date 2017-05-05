package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.profiledb.ProfileOtherDao;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.ProfileDBDao;
import com.moseeker.thrift.gen.dao.struct.ProfileOtherDO;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jack on 15/03/2017.
 */
@Service
public class ProfileDBDaoThriftService implements ProfileDBDao.Iface {

    @Autowired
    private ProfileOtherDao profileOtherDao;

    @Override
    public List<ProfileOtherDO> listProfileOther(CommonQuery query) throws CURDException, TException {
        return profileOtherDao.getDatas(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public ProfileOtherDO getProfileOther(CommonQuery query) throws CURDException, TException {
        return profileOtherDao.getData(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public ProfileOtherDO updateProfileOther(ProfileOtherDO profileOther) throws CURDException, TException {
        profileOtherDao.updateData(profileOther);
        return profileOther;
    }

    @Override
    public ProfileOtherDO saveProfileOther(ProfileOtherDO profileOther) throws CURDException, TException {
        profileOtherDao.addData(profileOther);
        return profileOther;
    }

    @Override
    public int deleteProfileOther(ProfileOtherDO profileOther) throws CURDException, TException {
        return profileOtherDao.deleteData(profileOther);
    }
}
