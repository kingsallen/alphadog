package com.moseeker.baseorm.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.baseorm.dao.jobdb.JobCustomDao;
import com.moseeker.baseorm.dao.jobdb.JobOccupationDao;
import com.moseeker.baseorm.service.PositionService;
import com.moseeker.baseorm.tool.OrmTools;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.dao.JobOccupationCustom;
@Service
public class PositionServiceImpl implements PositionService{
	/*
	 * auth:zzt
	 * time:2016-11-17
	 * param:CommonQuery
	 * function:获取job_occupation和job_custom
	 */
	@Autowired
	private JobCustomDao  customDao;
	@Autowired
	private JobOccupationDao occuPationdao;
	//获取job_occupation
	@Override
	public Response getJobOccupation(CommonQuery query) {
		// TODO Auto-generated method stub
		return OrmTools.getList(occuPationdao, query, new JobOccupationCustom());
	}
	//job_custom
	public Response getJobCustoms(CommonQuery query){
		return OrmTools.getList(customDao, query, new JobOccupationCustom());
	}

}
