package com.moseeker.dict.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisCallback;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CityServices;
import org.apache.thrift.TException;
import org.jooq.util.derby.sys.Sys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.thrift.gen.dict.struct.City;
import com.moseeker.thrift.gen.dict.service.CityServices.Iface;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.dict.dao.CityDao;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;

import java.util.HashMap;
import java.util.List;


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
        } catch (CacheConfigNotExistException e) {
            logger.error(String.format("CacheConfigNotExistException, tempting key: %s"), cachKey);
            List<City> r = super.getRawResources(query);
            HashMap transformed = transformData(r);
            result = ResponseUtils.success(transformed);
        }

        return result;
    }

    private HashMap transformData(List<City> s) {
        HashMap hm = new HashMap();
        hm.put("86", new HashMap());
        for (City city : s) {
            CityUtils.put(hm, city);
        }
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

class CityUtils {

    static final int PROVINCE = 1;
    static final int CITY = 2;
    static final int DISTRICT = 3;

    static void put(HashMap hm, City city) {
        switch (city.level) {
            case PROVINCE: // province
                CityUtils.putProvince(hm, city);
                break;
            case CITY: // city
                CityUtils.putCity(hm, city);
                break;
            case DISTRICT: // district
                CityUtils.putDistrict(hm, city);
                break;
        }
    }

    static void putProvince(HashMap hm, City city) {
        if (!hm.containsKey("" + city.code)) {
            hm.put("" + city.code, new HashMap());
        }
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




