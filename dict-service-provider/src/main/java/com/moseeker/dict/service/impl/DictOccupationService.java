package com.moseeker.dict.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.dict.service.impl.occupation.*;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.dict.enums.ConstantEnum;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

	public static Map<Class,AbstractOccupationHandler> map;

	@Autowired
	public DictOccupationService(List<AbstractOccupationHandler> list){
		map=list.stream().collect(Collectors.toMap(h->h.getClass(), h->h));
	}

	/*
	 * 查询第三方职位职能
	 */
	@CounterIface
	public JSONArray queryOccupation(String param) throws BIZException {
		logger.info("queryOccupation param :"+param);
		JSONObject obj=JSONObject.parseObject(param);
		int single_layer=obj.getIntValue("single_layer");
		int channel=obj.getIntValue("channel");
		try{
			AbstractOccupationHandler occupationHandler=getOccuaptionHandler(channel);

			if(single_layer==1){	//获取单一职能链
				return occupationHandler.getSingle(obj);
			}else{	//获取所有职能
				//先去redis查找缓存职位
				JSONArray redisOccupation=getRedisOccupation(channel);
				if(redisOccupation != null && !redisOccupation.isEmpty()){
					return redisOccupation;
				}else{
					//redis没有缓存职位，取数据库查询一遍并且缓存。
					JSONArray allOccupation=occupationHandler.getAll();
					logger.info("occupation handle result : {}",allOccupation);
					setRedisOccupation(channel,allOccupation);
					return allOccupation;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	public JSONArray getRedisOccupation(int channel){
		OccupationChannel occupationChannel=OccupationChannel.getInstance(channel);
		String result=redisClient.get(Constant.APPID_ALPHADOG,ConstantEnum.JOB_OCCUPATION_KEY.toString(),occupationChannel.key);
		logger.info("redis occupation : {}",result);
		return JSON.parseArray(result);
	}

	public void setRedisOccupation(int channel,JSONArray array){
		if(array!=null && !array.isEmpty()) {
			OccupationChannel occupationChannel = OccupationChannel.getInstance(channel);
			logger.info("set redis occupation : {}",array);
			redisClient.set(Constant.APPID_ALPHADOG, ConstantEnum.JOB_OCCUPATION_KEY.toString(), occupationChannel.key, array.toJSONString());
		}
	}

	public AbstractOccupationHandler getOccuaptionHandler(int channel){
		OccupationChannel occupationChannel=OccupationChannel.getInstance(channel);
		return map.get(occupationChannel.clazz);
	}

	private enum OccupationChannel{
		Job51(ChannelType.JOB51.getValue(),"51JobList", Job51OccupationHandler.class),
		ZhiLian(ChannelType.ZHILIAN.getValue(),"zPinList",ZhilianOccupationHandler.class),
		LiePin(ChannelType.LIEPIN.getValue(),"liePinList",LiepinOccupationHandler.class),
		VeryEast(ChannelType.VERYEAST.getValue(),"veryEastList",VeryEastOccupationHandler.class),
		Job1001(ChannelType.JOB1001.getValue(),"Job1001",Job1001OccupationHandler.class),
		JobsDB(ChannelType.JOBSDB.getValue(),"JobsDB",JobsDBOccupationHandler.class);

		OccupationChannel(int code,String key,Class<? extends AbstractOccupationHandler> clazz){
			this.code=code;
			this.key=key;
			this.clazz=clazz;
		}

		private int code;
		private String key;
		private Class clazz;

		public String key(){
			return key;
		}

		public static OccupationChannel getInstance(int code){
			for(OccupationChannel oc:values()){
				if(oc.code==code){
					return oc;
				}
			}
			return null;
		}
	}
}
