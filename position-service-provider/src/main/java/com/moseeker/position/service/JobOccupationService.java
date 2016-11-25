package com.moseeker.position.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TBase;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.position.utils.ConvertUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionDao;
import com.moseeker.thrift.gen.position.struct.dao.JobOccupationCustom;

@Service
public class JobOccupationService {
	/*
	 * auth:zzt
	 * time:2016-11-21
	 * param:company_id
	 * function:查找公司的自定义的字段，包括自定义职能和自定义字段
	 */
	PositionDao.Iface positionDao =ServiceManager.SERVICEMANAGER
			.getService(PositionDao.Iface.class);
	public Response getCustomField(String param){
		JSONObject obj=JSONObject.parseObject(param);
		int company_id=obj.getIntValue("company_id");
		CommonQuery query=new CommonQuery();
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("company_id", company_id+"");
		map.put("status", "1");
		query.setEqualFilter(map);
		query.setPer_page(Integer.MAX_VALUE);
		try{
			Response result1=positionDao.getJobCustoms(query);
			List<? extends TBase> list1=ConvertUtils.convert(JobOccupationCustom.class, result1);
			Map<String,Object> map1= ConvertUtils.convertToJSON(list1, "自定义字段");
			Response result2=positionDao.getJobOccupations(query);
			List<? extends TBase> list2=ConvertUtils.convert(JobOccupationCustom.class, result2);
			Map<String,Object> map2= ConvertUtils.convertToJSON(list2, "自定义职位职能");
			Map<String,Object> hashmap=new HashMap<String,Object>();
			hashmap.put("occupation", map2);
			hashmap.put("custom", map1);
			return ResponseUtils.success(hashmap);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

}
