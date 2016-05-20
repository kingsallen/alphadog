package com.moseeker.dict.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.exception.CacheConfigNotExistException;
import com.moseeker.common.redis.RedisCallback;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CityServices;
import org.apache.thrift.TException;
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
//        RedisCallback cb = new GetResultFromDBCallback(this, query);
        try {
            cachedResult = rc.get(query.appid, "DICT_CITY", cachKey, () -> {
                String r = null;
                try {
                    r = JSON.toJSONString(super.getResources(query));
                } catch (TException e) {
                    // todo
                }
                return r;
            });
            result = JSON.parseObject(cachedResult, Response.class);
        } catch (CacheConfigNotExistException e) {
            logger.error(String.format("CacheConfigNotExistException, tempting key: %s"), cachKey);
            result = super.getResources(query);
        }
        return result;
    }

//    public Response getResourcesNoCache(CommonQuery query) throws TException {
//        return super.getResources(query);
//    }

    @Override
    protected DictCityRecord structToDB(City c) {
        return (DictCityRecord)BeanUtils.structToDB(c, DictCityRecord.class);
    }

    private String genCachKey(CommonQuery query) {
        return "WinterSoldier";
    }
}

//class GetResultFromDBCallback implements RedisCallback {
//
//    private CityServicesImpl service;
//    private CommonQuery query;
//
//    GetResultFromDBCallback(CityServicesImpl cs, CommonQuery query) {
//       this.service = cs;
//        this.query = query;
//    }
//
//    public String call() {
//        String r = null;
//        try {
//            r = ResponseToString(this.service.getResourcesNoCache(query));
//        } catch (TException e) {
//            // todo
//        }
//        return r;
//    }
//
//    private String ResponseToString(Response r) {
//        return JSON.toJSONString(r);
//    }
//}
