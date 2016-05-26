package com.moseeker.dict.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.dict.dao.CityDao;
import com.moseeker.dict.dao.CollegeDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CollegeServices.Iface;
import com.moseeker.thrift.gen.dict.struct.City;
import com.moseeker.thrift.gen.dict.struct.College;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class CollegeServicesImpl extends JOOQBaseServiceImpl<College, DictCollegeRecord> implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CollegeDao dao;

    @Override
    protected void initDao() {
        super.dao = this.dao;
    }

    @Override
    protected College DBToStruct(DictCollegeRecord r) {
        return (College) BeanUtils.DBToStruct(College.class, r);
    }

    @Override
    protected DictCollegeRecord structToDB(College c) {
        return (DictCollegeRecord) BeanUtils.structToDB(c, DictCollegeRecord.class);
    }

    /*
    @Override
    public Response getResources(CommonQuery query) throws TException {
        RedisClient rc = RedisClientFactory.getCacheClient();
        String cachKey = genCachKey(query);
        String cachedResult = null;
        Response result = null;
        String patternString = "DICT_CITY";
        int appid = 0; // query.appid
        try {
            // 缓存表project_appid字段为0可视为对一切app_id开放
            // 此处请求将appid设置为0, 城市字典表允许来自一切的app_id缓存
            cachedResult = rc.get(appid, patternString, cachKey, () -> {
                String r = null;
                try {
                    List<City> cities = super.getRawResources(query);
                    HashMap transformed = transformData(cities);
                    r = JSON.toJSONString(ResponseUtils.success(transformed));
                } catch (TException e) {
                    logger.error("getResources error", e);
                    ResponseUtils.fail(e.getMessage());
                }
                return r;
            });
            result = JSON.parseObject(cachedResult, Response.class);
        } catch (Exception e) {
            logger.error("CacheConfigNotExistException, appid: %d, cachkey: %s, pattern_string: %s", appid, cachKey, patternString);
            List<City> r = super.getRawResources(query);
            HashMap transformed = transformData(r);
            result = ResponseUtils.success(transformed);
        }

        return result;
    }

    private HashMap transformData(List<College> s) {

        DictCollegeHashMap dictCollege = new DictCollegeHashMap(s);
        HashMap hm = dictCollege.getHashMap();
        return hm;

    }

    private String genCachKey(CommonQuery query) {
        return "all";
    }

    */


}

