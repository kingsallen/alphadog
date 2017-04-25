package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.service.PositionService;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.JobDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.PositionDetails;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobDBDaoThriftService implements Iface {

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private PositionService position;

    @Override
    public List<JobPositionDO> getPositions(CommonQuery query) throws TException {
        return positionDao.getPositions(query);
    }

    @Override
    public JobPositionDO getPosition(CommonQuery query) throws CURDException, TException {
        return positionDao.findResource(query);
    }

    @Override
    public List<JobApplicationDO> getApplications(CommonQuery query) throws TException {
        return applicationDao.getApplications(query);
    }

    @Override
    public JobApplicationDO getApplication(CommonQuery query) throws CURDException, TException {
        return applicationDao.findResource(query);
    }

    @Override
    public Response getJobCustoms(CommonQuery query) throws TException {
        return position.getJobCustoms(query);
    }

    @Override
    public Response getJobOccupations(CommonQuery query) throws TException {
        return position.getJobOccupation(query);
    }

    @Override
    public PositionDetails positionDetails(int positionId) throws TException {
        return positionDao.positionDetails(positionId);
    }

    @Override
    public List<PositionDetails> hotPositionDetailsList(CommonQuery query) throws TException {
        return positionDao.hotPositionDetailsList(query);
    }

    @Override
    public List<PositionDetails> similarityPositionDetailsList(CommonQuery query) throws TException {
        return positionDao.similarityPositionDetailsList(query);
    }


}
