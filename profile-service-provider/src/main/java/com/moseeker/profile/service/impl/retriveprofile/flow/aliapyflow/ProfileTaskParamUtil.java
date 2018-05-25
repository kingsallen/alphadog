package com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow;

import com.moseeker.baseorm.dao.dictdb.DictCountryDao;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;
import com.moseeker.profile.service.impl.retriveprofile.executor.CouplerParamUtil;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * profile任务参数生成工具。
 * Created by jack on 20/07/2017.
 */
@Component
public class ProfileTaskParamUtil implements CouplerParamUtil<ProfilePojo, Integer, Boolean> {

    @Autowired
    private DictCountryDao countryDao;

    @Override
    public ProfilePojo parseExecutorParam(Boolean tmpParam, ExecutorParam globalParam) throws CommonException {
        AliPayRetrievalParam aliPayRetrievalParam = (AliPayRetrievalParam)globalParam;
        List<DictCountryDO> countryDOList = countryDao.getDatas(new Query.QueryBuilder().buildQuery());
        ProfilePojo profilePojo = ProfilePojo.parseProfile(aliPayRetrievalParam.getProfile(), countryDOList);
        return profilePojo;
    }

    @Override
    public void storeTaskResult(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        //do nothing
    }
}
