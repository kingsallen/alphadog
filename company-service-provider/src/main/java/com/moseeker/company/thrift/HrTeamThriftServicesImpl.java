package com.moseeker.company.thrift;

import com.moseeker.company.service.impl.HrTeamServicesImpl;
import com.moseeker.thrift.gen.company.service.HrTeamServices;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTeamDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/5/15.
 */
@Service
public class HrTeamThriftServicesImpl implements HrTeamServices.Iface {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    HrTeamServicesImpl hrTeamServices;

    @Override
    public List<HrTeamDO> getHrTeams(Map<String, String> query) throws TException {
        try {
            return hrTeamServices.getHrTeams(query);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }
}
