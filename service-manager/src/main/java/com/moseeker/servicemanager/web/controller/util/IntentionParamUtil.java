package com.moseeker.servicemanager.web.controller.util;

import com.moseeker.common.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.thrift.gen.profile.struct.Intention;

/**
 * 
 * 解析参数中感兴趣的行业、城市、职能的特殊格式，将值补填到Intention中
 * <p>Company: MoSeeker</P>  
 * <p>date: Aug 22, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class IntentionParamUtil {

	/**
	 * 将参数中通用解析方式无法解析的行业、职能、城市信息补填到Intention中
	 *
	 * "positions[0][position_code]" -> "120202"
	 * "cities[0][city_code]" -> "310000"
	 *
	 * @param reqParams 从request中解析出来的参数
	 * @param intention 感兴趣信息
	 */
	public static void buildIntention(Map<String, Object> reqParams, Intention intention) {
		
		Map<Integer, Integer> industryCode = new HashMap<>();
		Map<String, Integer> industryName= new HashMap<>();
		
		Map<Integer, Integer> positionCode = new HashMap<>();
		Map<String, Integer> positionName= new HashMap<>();
		
		Map<Integer, Integer> cityCode = new HashMap<>();
		Map<String, Integer> cityName= new HashMap<>();
		if (reqParams != null) {
			for (Entry<String, Object> entry : reqParams.entrySet()) {
				//查找行业信息
				if(entry.getKey().startsWith("industries[")) {
					if(entry.getKey().contains("industry_code")) {
						industryCode.put(Integer.valueOf(entry.getKey().charAt(11)), BeanUtils.converToInteger(entry.getValue()));
					}
					if(entry.getKey().contains("industry_name")) {
						industryName.put((String)entry.getValue(), Integer.valueOf(entry.getKey().charAt(11)));
					}
				}
				//查找城市信息
				if(entry.getKey().startsWith("cities[")) {
					if(entry.getKey().contains("city_code")) {

						cityCode.put(Integer.valueOf(entry.getKey().charAt(7)), BeanUtils.converToInteger(entry.getValue()));
					}
					if(entry.getKey().contains("city_name")) {
                        cityName.put((String)entry.getValue(), Integer.valueOf(entry.getKey().charAt(7)));
					}
				}

                //查找城市信息
                if(entry.getKey().startsWith("city")) {
                    List<Map<String, Object>> cities = (List<Map<String,Object>>) entry.getValue();
                    if(!StringUtils.isEmptyList(cities)) {
                        for (int i = 0; i < cities.size(); i++) {
                            Map<String, Object> city = cities.get(i);
                            if (city.get("city_code") != null) {
                                cityCode.put(i, BeanUtils.converToInteger(city.get("city_code")));
                            }
                            if (city.get("city_name") != null) {
                                cityName.put((String) city.get("city_name"), i);
                            }
                        }
                    }
                }
				//查找职能信息
				if(entry.getKey().startsWith("positions[")) {
					if(entry.getKey().contains("position_code")) {
						positionCode.put(Integer.valueOf(entry.getKey().charAt(10)), BeanUtils.converToInteger(entry.getValue()));
					}
					if(entry.getKey().contains("position_name")) {
						positionName.put((String)entry.getValue(), Integer.valueOf(entry.getKey().charAt(10)));
					}
				}
			}
		}

		//拼装行业信息
		if(industryName.size() > 0) {
			for(Entry<String, Integer> entry : industryName.entrySet()) {
				if(intention.getIndustries() == null) {
					intention.setIndustries(new HashMap<String, Integer>());
				}
				int code = 0;
				if(industryCode.size() > 0) {
					if(industryCode.get(entry.getValue()) != null) {
						code = industryCode.get(entry.getValue());
					}
				}
				intention.getIndustries().put(entry.getKey(), code);
			}
		}else if(industryCode.size() > 0) {
            for(Entry<Integer, Integer> entry : industryCode.entrySet()) {
                if(intention.getIndustries() == null) {
                    intention.setIndustries(new HashMap<String, Integer>());
                }
                intention.getIndustries().put(String.valueOf(entry.getValue()), entry.getValue());
            }
        }

		//拼装职能信息
		if(positionName.size() > 0) {
			for(Entry<String, Integer> entry : positionName.entrySet()) {
				if(intention.positions == null) {
					intention.setPositions(new HashMap<String, Integer>());
				}
				int code = 0;
				if(positionCode.size() > 0) {
					if(positionCode.get(entry.getValue()) != null) {
						code = positionCode.get(entry.getValue());
					}
				}
				intention.getPositions().put(entry.getKey(), code);
			}
		}
		//拼装城市信息
		if(cityName.size() > 0) {
			for(Entry<String, Integer> entry : cityName.entrySet()) {
				if(intention.cities == null) {
					intention.setCities(new HashMap<String, Integer>());
				}
				int code = 0;
				if(cityCode.get(entry.getValue()) != null) {
					code = cityCode.get(entry.getValue());
				}
				intention.getCities().put(entry.getKey(), code);
			}
		}
	}
}
