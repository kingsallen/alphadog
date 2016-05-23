package com.moseeker.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.moseeker.db.dictdb.tables.DictCity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.thrift.TException;
import com.alibaba.fastjson.JSON;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.struct.City;
import com.moseeker.thrift.gen.dict.service.CityServices.Iface;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.dict.dao.CityDao;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;


@Service
public class CityServicesImpl extends JOOQBaseServiceImpl<City, DictCityRecord> implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CityDao dao;

    @Override
    protected void initDao() {
        super.dao = this.dao;
    }

    @Override
    protected City DBToStruct(DictCityRecord r) {
        return (City) BeanUtils.DBToStruct(City.class, r);
    }

    @Override
    public Response getResources(CommonQuery query) throws TException {
        RedisClient rc = RedisClientFactory.getCacheClient();
        String cachKey = genCachKey(query);
        String cachedResult = null;
        Response result = null;
        try {
            cachedResult = rc.get(query.appid, "DICT_CITY", cachKey, () -> {
                String r = null;
                try {
                    List<City> cities = super.getRawResources(query);
                    HashMap transformed = transformData(cities);
                    r = JSON.toJSONString(ResponseUtils.success(transformed));
                } catch (TException e) {
                    // todo
                }
                return r;
            });
            result = JSON.parseObject(cachedResult, Response.class);
        } catch (Exception e) {
            logger.error(String.format("CacheConfigNotExistException, tempting key: %s"), cachKey);
            List<City> r = super.getRawResources(query);
            HashMap transformed = transformData(r);
            result = ResponseUtils.success(transformed);
        }

        return result;
    }

    private HashMap transformData(List<City> s) {

        DictCityHashMap dictCity = new DictCityHashMap(s);
        HashMap hm = dictCity.getHashMap();
        return hm;

    }

    @Override
    protected DictCityRecord structToDB(City c) {
        return (DictCityRecord) BeanUtils.structToDB(c, DictCityRecord.class);
    }

    private String genCachKey(CommonQuery query) {
        return "all";
    }
}

class DictCityHashMap {

    static final int PROVINCE = 1;
    static final int CITY = 2;
    static final int DISTRICT = 3;
    static final String[][] groups = {{"A", "G"}, {"H", "K"}, {"L", "S"}, {"T", "Z"}};
    private HashMap hm;

    public DictCityHashMap(List<City> s) {
        hm = new HashMap();
        HashMap provinces = new HashMap();
        for(String[] group: groups) {
            String concatGroup = concatGroup(group);
            provinces.put(concatGroup, new ArrayList());
        }
        hm.put("86", provinces);
        for (City city : s) {
            DictCityHashMap.put(hm, city);
        }
    }

    public HashMap getHashMap(){
        return this.hm;
    }

    static String concatGroup(String[] group) {
        return (group[0] + "-" + group[1]).toUpperCase();
    }

    static void put(HashMap hm, City city) {
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
        for(String[] group: groups) {
            if(letter.compareToIgnoreCase(group[0]) >= 0 && letter.compareToIgnoreCase(group[1]) <= 0) {
                g = concatGroup(group);
                break;
            }
        }
        return g;
    }

    static void putProvince(HashMap hm, City city) {
        if (!hm.containsKey("" + city.code)) {
            hm.put("" + city.code, new HashMap());
        }
        // 按首字母分区的
        String initialLetter = city.ename.substring(0, 1).toUpperCase();
        System.out.println(initialLetter);
        String group = getGroup(initialLetter);
        List goupedProvinces = (List)((HashMap)hm.get("86")).get(group);
        HashMap province = new HashMap();
        province.put("code", city.code+"");
        province.put("address", city.name);
        goupedProvinces.add(province);
    }

    static void putCity(HashMap hm, City city) {
        int provinceCode = city.code / 1000 * 1000;
        if (!hm.containsKey("" + provinceCode)) {
            hm.put("" + provinceCode, new HashMap());
        }
        HashMap p = (HashMap) hm.get("" + provinceCode);
        p.put("" + city.code, city.name);
    }

    static void putDistrict(HashMap hm, City city) {
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
