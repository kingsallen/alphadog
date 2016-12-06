package com.moseeker.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.dict.dao.CityDao;
import com.moseeker.dict.pojo.CityPojo;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
public class CityServices {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CityDao dao;

    @CounterIface
    public Response getResources(CommonQuery query) throws TException {
        Response result;
        try {
            String cachKey = "transformed";
            String patternString = "DICT_CITY";
            int appid = 0; // 允许所有app_id的请求缓存
            RedisClient rc = RedisClientFactory.getCacheClient();
            String cachedResult = rc.get(appid, patternString, cachKey, () -> {
                return JSON.toJSONString(this.getCitiesResponse(true, 0));
            });
            result = JSON.parseObject(cachedResult, Response.class);
        } catch (RedisException e) {
        		WarnService.notify(e);
        		result = this.getCitiesResponse(true, 0);
        } catch (CacheConfigNotExistException e) {
            logger.error(e.getMessage(), e);
            result = this.getCitiesResponse(true, 0);
        }
        return result;
    }

    @CounterIface
    public Response getCitiesResponse(boolean transform, int level) {
        List<CityPojo> cities = this.dao.getCities(level);
        if (transform) {
            HashMap transformed = transformData(cities);
            return ResponseUtils.success(transformed);
        } else {
            return ResponseUtils.success(cities);
        }
    }

    private HashMap transformData(List<CityPojo> s) {
        DictCityHashMap dictCity = new DictCityHashMap(s);
        HashMap hm = dictCity.getHashMap();
        return hm;
    }

    // --------------------- new api ----------------------------------
    @CounterIface
    public Response getAllCities(int level) {
        Response result;
        try {
            String cachKey = "raw_level_" + level;
            String patternString = "DICT_CITY";
            int appid = 0; // 允许所有app_id的请求缓存
            RedisClient rc = RedisClientFactory.getCacheClient();
            String cachedResult = rc.get(appid, patternString, cachKey, () -> {
                return JSON.toJSONString(this.getCitiesResponse(false, level));
            });
            result = JSON.parseObject(cachedResult, Response.class);
        } catch (RedisException e) {
        		WarnService.notify(e);
        		result = this.getCitiesResponse(false, level);
        } catch (CacheConfigNotExistException e) {
            logger.error(e.getMessage(), e);
            result = this.getCitiesResponse(false, level);
        }
        return result;
    }

    @CounterIface
    public Response getCitiesById(int id) throws TException {
        Response result;
        try {
            String cachKey = "raw_id_" + id;
            String patternString = "DICT_CITY";
            int appid = 0; // 允许所有app_id的请求缓存
            RedisClient rc = RedisClientFactory.getCacheClient();
            String cachedResult = rc.get(appid, patternString, cachKey, () -> {
                return JSON.toJSONString(this.getCitiesResponseById(id));
            });
            result = JSON.parseObject(cachedResult, Response.class);
        } catch (RedisException e) {
        		WarnService.notify(e);
            result = this.getCitiesResponseById(id);
        } catch (CacheConfigNotExistException e) {
            logger.error(e.getMessage(), e);
            result = this.getCitiesResponseById(id);
        }
        return result;
    }

    @CounterIface
    public Response getCitiesResponseById(int id) {
        List<CityPojo> cities = this.dao.getCitiesById(id);
        return ResponseUtils.success(cities);
    }
}

class DictCityHashMap {
    // TODO: 改成并行处理
    static final int PROVINCE = 1;
    static final int CITY = 2;
    static final int DISTRICT = 3;
    static final String[][] groups = {{"A", "G"}, {"H", "K"}, {"L", "S"}, {"T", "Z"}};
    private HashMap hm;

    public DictCityHashMap(List<CityPojo> s) {
        hm = new HashMap();
        if (s != null) {
            HashMap provinces = new HashMap();
            for (String[] group : groups) {
                String concatGroup = concatGroup(group);
                provinces.put(concatGroup, new ArrayList());
            }
            hm.put("86", provinces);
            for (CityPojo city : s) {
                DictCityHashMap.put(hm, city);
            }
        }
    }

    public HashMap getHashMap() {
        return this.hm;
    }

    static String concatGroup(String[] group) {
        return (group[0] + "-" + group[1]).toUpperCase();
    }

    static void put(HashMap hm, CityPojo city) {
        switch (city.level) {
            case PROVINCE: // province
                DictCityHashMap.putProvince(hm, city);
                break;
            case CITY: // city
                DictCityHashMap.putCity(hm, city);
                break;
            case DISTRICT: // district
                DictCityHashMap.putDistrict(hm, city);
                break;
        }
    }

    static String getGroup(String letter) {
        String g = null;
        for (String[] group : groups) {
            if (letter.compareToIgnoreCase(group[0]) >= 0 && letter.compareToIgnoreCase(group[1]) <= 0) {
                g = concatGroup(group);
                break;
            }
        }
        return g;
    }

    static void putProvince(HashMap hm, CityPojo city) {
        if (!hm.containsKey("" + city.code)) {
            hm.put("" + city.code, new HashMap());
        }
        // 按首字母分区的
        String initialLetter = city.ename.substring(0, 1).toUpperCase();
        String group = getGroup(initialLetter);
        List goupedProvinces = (List) ((HashMap) hm.get("86")).get(group);
        HashMap province = new HashMap();
        province.put("code", city.code + "");
        province.put("address", city.name);
        goupedProvinces.add(province);
    }

    static void putCity(HashMap hm, CityPojo city) {
        int provinceCode = city.code / 1000 * 1000;
        if (!hm.containsKey("" + provinceCode)) {
            hm.put("" + provinceCode, new HashMap());
        }
        HashMap p = (HashMap) hm.get("" + provinceCode);
        p.put("" + city.code, city.name);
    }

    static void putDistrict(HashMap hm, CityPojo city) {
        int provinceCode = city.code / 1000 * 1000;
        if (!hm.containsKey("" + provinceCode)) {
            hm.put("" + provinceCode, new HashMap());
        }
        int cityCode = city.code / 100 * 100;
        if (!hm.containsKey("" + cityCode)) {
            hm.put("" + cityCode, new HashMap());
        }

        HashMap c = (HashMap) hm.get("" + cityCode);
        c.put("" + city.code, city.name);
    }

}
