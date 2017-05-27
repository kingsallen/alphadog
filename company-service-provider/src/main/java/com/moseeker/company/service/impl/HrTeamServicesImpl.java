package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.hrdb.HrTeamDao;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by moseeker on 2017/5/15.
 */
@Service
public class HrTeamServicesImpl {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    HrTeamDao hrTeamDao;

    public List<HrTeamDO> getHrTeams(Map<String, String> query) throws Exception {
        return hrTeamDao.getHrTeams(query);
    }
}
