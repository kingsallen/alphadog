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

	@Autowired
	private List<AbstractOccupationHandler> handlers;

	@Autowired
	private DefaultOccupationHandler defaultOccupationHandler;

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
		ChannelType occupationChannel=ChannelType.instaceFromInteger(channel);
		String result=redisClient.get(Constant.APPID_ALPHADOG,ConstantEnum.JOB_OCCUPATION_KEY.toString(),String.valueOf(occupationChannel.getValue()));
		logger.info("redis occupation : {}",result);
		return JSON.parseArray(result);
	}

	public void setRedisOccupation(int channel,JSONArray array){
		ChannelType occupationChannel=ChannelType.instaceFromInteger(channel);
		logger.info("set redis occupation : {}",array);
		redisClient.set(Constant.APPID_ALPHADOG, ConstantEnum.JOB_OCCUPATION_KEY.toString(), String.valueOf(occupationChannel.getValue()), array.toJSONString());
	}

	public AbstractOccupationHandler getOccuaptionHandler(int channel){
		ChannelType occupationChannel=ChannelType.instaceFromInteger(channel);
		if(handlers.stream().anyMatch(h->h.getChannelType() == occupationChannel)){
			return handlers.stream().filter(h->h.getChannelType() == occupationChannel).findFirst().get();
		}
		return defaultOccupationHandler;
	}
}
