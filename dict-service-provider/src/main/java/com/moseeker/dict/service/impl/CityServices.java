package com.moseeker.dict.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.dictdb.CityPojo;
import javax.annotation.Resource;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.swing.StringUIClientPropertyKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityServices {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DictCityDao dao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @CounterIface
    public Response getResources(CommonQuery query) throws TException {
        Response result;
        try {
            String cachKey = "transformed";
            String patternString = "DICT_CITY";
            int appid = 0; // 允许所有app_id的请求缓存
            String cachedResult = redisClient.get(appid, patternString, cachKey, () -> {
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

    @CounterIface
    public Response getCitiesResponseByLevelUsingHot(boolean transform, String level, int is_using, int hot_city) {
        List<CityPojo> cities = this.dao.getCitiesByLevelUsingHot(level, is_using, hot_city);
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
            String cachedResult = redisClient.get(appid, patternString, cachKey, () -> {
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
    public Response getAllCitiesByLevelOrUsing(String level, int is_using, int hot_city) {
        Response result;
        try {
//            String cachKey = "raw_levelUsingHot_" + level+is_using+hot_city;
//            String patternString = "DICT_CITY";
//            int appid = 0; // 允许所有app_id的请求缓存
//            String cachedResult = redisClient.get(appid, patternString, cachKey, () -> {
//                return JSON.toJSONString(this.getCitiesResponseByLevelUsingHot(false, level, is_using, hot_city));
//            });
//            result = JSON.parseObject(cachedResult, Response.class);
            result = this.getCitiesResponseByLevelUsingHot(false, level, is_using, hot_city);
        } catch (RedisException e) {
            WarnService.notify(e);
            result = this.getCitiesResponseByLevelUsingHot(false, level, is_using, hot_city);
        } catch (CacheConfigNotExistException e) {
            logger.error(e.getMessage(), e);
            result = this.getCitiesResponseByLevelUsingHot(false, level, is_using, hot_city);
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
            String cachedResult = redisClient.get(appid, patternString, cachKey, () -> {
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

    @CounterIface
    public Map<String,Object> getProvinceCity(){
        Map<String,Object> result=new HashMap<>();
        Query query=new Query.QueryBuilder().where("is_using",1).and(new Condition("level",1,ValueOp.GT)).buildQuery();
        List<Map<String,Object>> list=dao.getMaps(query);
        if(!StringUtils.isEmptyList(list)){
            List<Map<String,Object>> hotCityList=this.getHotCity();
            result.put("hot_city",hotCityList);
            List<Integer> codeList=this.getOutCityCodeList();
            List<Map<String,Object>> provinceList=this.getProvince(codeList);
            if(!StringUtils.isEmptyList(provinceList)){
                List<Map<String,Object>> dataList=this.handlerProvinceCityData(provinceList,list,codeList);
                result.put("city_data",dataList);
            }
        }
        return result;
    }
    @CounterIface
    public Response getCityCodeByProvine(List<Integer> codeList) throws CommonException {
        List<Map<String,Object>> list=dao.getCityCodeByProvine(codeList);
        return ResponseUtils.success(list);
    }
    private List<Integer> getOutCityCodeList(){
        List<Integer> codeList=new ArrayList<>();
        codeList.add(110000);
        codeList.add(111111);
        codeList.add(120000);
        codeList.add(233333);
        codeList.add(310000);
        codeList.add(500000);
        codeList.add(810000);
        codeList.add(820000);
        return codeList;
    }

    /*
     获取热门城市
     */
    private List<Map<String,Object>> getHotCity(){
        Query query=new Query.QueryBuilder().where("is_using",1).and("hot_city",1).buildQuery();
        List<Map<String,Object>> list=dao.getMaps(query);
        return list;
    }
    /*
     获取省份城市数据
     */
    private List<Map<String,Object>> getProvince(List<Integer> codeList){
        Query query=new Query.QueryBuilder().where("level",1).buildQuery();
        List<Map<String,Object>> list=dao.getMaps(query);
        if(!StringUtils.isEmptyList(list)){
            List<Map<String,Object>> result=new ArrayList<>();
            for(Map<String,Object> data:list){
                byte level= (byte) data.get("level");
                int code=(int) data.get("code");
                if(level==1){
                    if(!codeList.contains(code)){
                        result.add(data);
                    }
                }
            }
            return result;
        }
        return null;
    }
    /*
     处理数据，将城市数据归给省份数据下
     */
    private List<Map<String,Object>> handlerProvinceCityData(List<Map<String,Object>> provinceList,List<Map<String,Object>> list,List<Integer> codeList){
        if(StringUtils.isEmptyList(provinceList)||StringUtils.isEmptyList(list)){
            return null;
        }
        for(Map<String,Object> map:provinceList){
            int code= (int) map.get("code");
            List<Map<String,Object>> cityList=new ArrayList<>();
            for(Map<String,Object> data:list){
                int coityCode= (int) data.get("code");
                int level=Integer.parseInt(String.valueOf(data.get("level")));
                if(coityCode>code&&coityCode<code+10000&&level>1&&!codeList.contains(coityCode)){
                    cityList.add(data);
                }
            }
            map.put("child_city",cityList);

        }
        return provinceList;
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
