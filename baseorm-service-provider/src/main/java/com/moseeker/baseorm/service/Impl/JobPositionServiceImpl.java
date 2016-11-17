package com.moseeker.baseorm.service.Impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.position.JobPositionDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.service.JobPositionService;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.Position;
@Service
public class JobPositionServiceImpl implements JobPositionService {
	@Autowired
	private JobPositionDao jobPositionDao;
	
	@Override
	public Response getJobPositionById(int id) {
		// TODO Auto-generated method stub
		CommonQuery query=new CommonQuery();
		HashMap map=new HashMap();
		map.put("id", id);
		map.put("limit", 1);
		query.setEqualFilter(map);
		try{
			JobPositionRecord record=jobPositionDao.getResource(query);
			if(record!=null){
				Position position=(Position) BeanUtils.DBToStruct(Position.class, record);
				return ResponseUtils.success(position); 
			}else{
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

	@Override
	public Response getJobPostion(CommonQuery query) {
		// TODO Auto-generated method stub
		try{
			JobPositionRecord record=jobPositionDao.getResource(query);
			if(record!=null){
				Position position=(Position) BeanUtils.DBToStruct(Position.class, record);
				return ResponseUtils.success(position); 
			}else{
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

}
