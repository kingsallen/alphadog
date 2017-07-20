package com.moseeker.position.service.position;

import com.moseeker.baseorm.redis.RedisCallback;
import com.moseeker.baseorm.redis.RedisClient;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.position.service.position.pojo.ThirdPartyCityPojo;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 城市转换工具
 * @author wjf
 *
 */
@Component
public class CityChangeUtil {

//    @Resource(name = "cacheClient")
    private RedisClient redisClient;

	/**
	 * 根据仟寻的城市编号(code)查询51的城市信息
	 * @param code
	 * @return
	 */
	public ThirdPartyCityPojo getJob51City(String code, RedisCallback callback) {
		HashMap<String, ThirdPartyCityPojo> cities = fetchCities(callback);
		return cities.get(code+"_"+ChannelType.JOB51.getValue());
	}
	
	/**
	 * 根据仟寻的城市编号（code）查询智联的城市信息
	 * @param code
	 * @return
	 */
	public ThirdPartyCityPojo getZhilianCity(String code, RedisCallback callback) {
		HashMap<String, ThirdPartyCityPojo> cities = fetchCities(callback);
		return cities.get(code+"_"+ChannelType.ZHILIAN.getValue());
	}

	private HashMap<String, ThirdPartyCityPojo> fetchCities(RedisCallback callback) {
		HashMap<String, ThirdPartyCityPojo> cities = new HashMap<>();
		String cityJson = redisClient.get(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.THIRD_PARTY_CITY.toString(), null, callback);
		if(cityJson != null) {
			List<ThirdPartyCityPojo> pojos = JSON.parseArray(cityJson, ThirdPartyCityPojo.class);
			if(pojos != null && pojos.size() > 0) {
				pojos.forEach(pojo -> {
					cities.put(pojo.getMoseekerCode()+"_"+pojo.getChannel(), pojo);
				});
			}
		}
		
		return cities;
	}
}
