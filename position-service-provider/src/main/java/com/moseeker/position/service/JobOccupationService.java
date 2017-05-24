package com.moseeker.position.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.thrift.TBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRCompanyConfDao;
import com.moseeker.baseorm.dao.jobdb.JobCustomDao;
import com.moseeker.baseorm.dao.jobdb.JobOccupationDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.position.utils.ConvertUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HRCompanyConfData;
import com.moseeker.thrift.gen.position.struct.dao.JobOccupationCustom;

@Service
public class JobOccupationService {
	/*
	 * auth:zzt
	 * time:2016-11-21
	 * param:company_id
	 * function:查找公司的自定义的字段，包括自定义职能和自定义字段
	 */
	@Autowired
	private HRCompanyConfDao hrCompantDao;
	@Autowired
    private JobCustomDao customDao;
    @Autowired
    private JobOccupationDao occuPationdao;
    
	public Response getCustomField(String param){
		JSONObject obj=JSONObject.parseObject(param);
		int company_id=obj.getIntValue("company_id");
		Query query=new Query.QueryBuilder().where("company_id",company_id).where("status",1).buildQuery();
		try{
			HRCompanyConfData hrconf=getHRCompanyConf(company_id);
			Response result1=customDao.getJobCustoms(query);
			List<? extends TBase> list1=ConvertUtils.convert(JobOccupationCustom.class, result1);
			Map<String,Object> map1= ConvertUtils.convertToJSON(list1, hrconf.job_custom_title);
			Response result2=occuPationdao.getJobOccupations(query);
			List<? extends TBase> list2=ConvertUtils.convert(JobOccupationCustom.class, result2);
			Map<String,Object> map2= ConvertUtils.convertToJSON(list2, hrconf.getJob_occupation());
			Map<String,Object> hashmap=new HashMap<String,Object>();
			hashmap.put("occupation", map2);
			hashmap.put("custom", map1);
			return ResponseUtils.success(hashmap);
		}catch(Exception e){
			e.printStackTrace();
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}
	  private  HRCompanyConfData getHRCompanyConf(int company_id){
		  HRCompanyConfData data=null;
		  Query query=new Query.QueryBuilder().where("company_id", company_id).buildQuery();
		  try {
			  data=hrCompantDao.getData(query,HRCompanyConfData.class);
			if(data!=null){
				if(StringUtils.isNullOrEmpty(data.getJob_custom_title())){
					data.setJob_custom_title("自定义字段");
				}
				if(StringUtils.isNullOrEmpty(data.getJob_occupation())){
					data.setJob_occupation("定义职位职能");
				}
				return data;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(data==null){
		  data=new HRCompanyConfData();
		  data.setJob_custom_title("自定义字段");
		  data.setJob_occupation("定义职位职能");
	    }
		  return data;
	  }
}
