package com.moseeker.profile.service.impl.retriveprofile.flow.common;

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
public class CommonProfileTaskParamUtil implements CouplerParamUtil<ProfilePojo, Integer, Integer> {

    @Autowired
    private DictCountryDao countryDao;

    @Override
    public ProfilePojo parseExecutorParam(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        List<DictCountryDO> countryDOList = countryDao.getDatas(new Query.QueryBuilder().buildQuery());
        ProfilePojo profilePojo = ProfilePojo.parseProfile(globalParam.getProfile(), countryDOList);
        return profilePojo;
    }

    @Override
    public void storeTaskResult(Integer tmpParam, ExecutorParam globalParam) throws CommonException {
        //do nothing
    }
}
