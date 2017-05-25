package com.moseeker.dict.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictZpinOccupationDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.dict.enums.ConstantEnum;
import com.moseeker.thrift.gen.common.struct.Response;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DictOccupationService {
	/**
	 *   查询dict的职位字典
	 * @param single_layer 是否只返回一层
	 * @param channel 渠道类型
	 * @return
	 * @Auth zzt
	 * @time 2016－11－17
	 */
	Logger logger = org.slf4j.LoggerFactory.getLogger(DictOccupationService.class);

	@Autowired
    private Dict51OccupationDao dict51OccupationDao;

	@Autowired
    private DictZpinOccupationDao dictZpinOccupationDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

	/*
	 * 查询第三方职位职能
	 */
	public Response queryOccupation(String param){
		JSONObject obj=JSONObject.parseObject(param);
		int single_layer=obj.getIntValue("single_layer");
		int channel=obj.getIntValue("channel");
		try{
			if(single_layer==1){
				Integer level=obj.getInteger("level") ;
				Integer id=obj.getInteger("code");
				Integer parentId=obj.getInteger("parent_id");
				QueryUtil query=new QueryUtil();
				query.setPageSize(Integer.MAX_VALUE);
				HashMap<String,String> map=new HashMap<String,String>();
				map.put("status", "1");
				if(id!=null){
					map.put("code", String.valueOf(id));
				}
				if(parentId!=null){
					map.put("parent_id",String.valueOf(parentId));
				}
				if(level!=null){
					map.put("level", String.valueOf(level));
				}
				query.setEqualFilter(map);
				if(channel==1){
					return ResponseUtils.success(dict51OccupationDao.getSingle(query));
				}else if(channel==3){
					return ResponseUtils.success(dictZpinOccupationDao.getSingle(query));
				}
			}else{
				if(channel==1){
					String key="51JobList";
					String result=redisClient.get(Constant.APPID_ALPHADOG,ConstantEnum.JOB_OCCUPATION_KEY.toString(),key);
					if(!StringUtils.isEmpty(result)){
						Response res=JSONObject.toJavaObject(JSONObject.parseObject(result), Response.class);
						return res;
					}else{
						Response res=ResponseUtils.success(dict51OccupationDao.getAll());
						if(res.getStatus()==0&&!StringUtils.isEmpty(res.getData())&&!"[]".equals(res.getData())){
							redisClient.set(Constant.APPID_ALPHADOG,ConstantEnum.JOB_OCCUPATION_KEY.toString(),key,JSONObject.toJSONString(res));
						}
						return res;
					}
				}else if(channel==3){
					String key="zPinList";
					String result=redisClient.get(Constant.APPID_ALPHADOG,ConstantEnum.JOB_OCCUPATION_KEY.toString(),key);
					if(!StringUtils.isEmpty(result)){
						Response res=JSONObject.toJavaObject(JSONObject.parseObject(result), Response.class);
						return res;
					}else{
						Response res=ResponseUtils.success(dictZpinOccupationDao.getAll());
						if(res.getStatus()==0&&!StringUtils.isEmpty(res.getData())&&!"[]".equals(res.getData())){
							redisClient.set(Constant.APPID_ALPHADOG,ConstantEnum.JOB_OCCUPATION_KEY.toString(),key ,JSONObject.toJSONString(res));
						}
						return res;
					}
				}
			}
			}catch(Exception e){
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
	}
	
}
