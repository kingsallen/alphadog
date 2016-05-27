package com.moseeker.position.service.impl;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.position.dao.PositionDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.service.PositionServices.Iface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServicesImpl extends JOOQBaseServiceImpl<Position, JobPositionRecord> implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected PositionDao dao;

    @Override
    protected void initDao() {
        super.dao = this.dao;
    }

    @Override
    protected Position DBToStruct(JobPositionRecord r) {
        return (Position) BeanUtils.DBToStruct(Position.class, r);
    }

    @Override
    protected JobPositionRecord structToDB(Position p) {
        return (JobPositionRecord) BeanUtils.structToDB(p, JobPositionRecord.class);
    }

    @Override
    public Response getRecommendedPositions(int pid) {

        List<JobPositionRecord> recommendPositons = this.dao.getRecommendedPositions(pid);

        return ResponseUtils.success(DBsToStructs(recommendPositons));

    }

}