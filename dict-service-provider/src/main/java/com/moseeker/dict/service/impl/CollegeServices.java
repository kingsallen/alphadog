package com.moseeker.dict.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCollegeDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.struct.College;
import com.moseeker.thrift.gen.dict.struct.CollegeBasic;
import com.moseeker.thrift.gen.dict.struct.CollegeProvince;
import java.util.*;
import javax.annotation.Resource;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TSimpleJSONProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegeServices {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DictCollegeDao dao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @CounterIface
    public Response getResources(CommonQuery query) throws TException {
        String cachedResult = null;
        Response result = null;
        try {
            List<College> collegeList = this.dao.getJoinedResults(QueryConvert.commonQueryConvertToQuery(query));
            Collection transformed = transformData(collegeList);
            cachedResult = JSON.toJSONString(ResponseUtils.success(transformed));
        } catch (Exception e) {
            logger.error("getResources error", e);
            ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
        result = JSON.parseObject(cachedResult, Response.class);
        return result;
    }

    @CounterIface
    public Response getCollegeByDomestic() throws TException {
        String cachedResult = null;
        Response result = null;
        int appid = 0; // query.appid
        try {
            cachedResult = redisClient.get(appid, KeyIdentifier.DICT_COLLEGE_COUNTRY.toString(), Constant.CHINA_CODE, () -> {
                String r = null;
                try {
                    List<CollegeProvince> joinedResult = this.dao.getCollegeByDomestic();
                    Collection transformed = transformDataProvince(joinedResult);
                    r = JSON.toJSONString(ResponseUtils.success(transformed));
                } catch (Exception e) {
                    logger.error("getResources error", e);
                    ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
                }
                return r;
            });
            result = JSON.parseObject(cachedResult, Response.class);

        } catch(RedisException e){
            WarnService.notify(e);
        } catch(Exception e) {
            e.printStackTrace();
            List<CollegeProvince> joinedResult = this.dao.getCollegeByDomestic();
            result = ResponseUtils.success(transformDataProvince(joinedResult));
        }
        return result;
    }

    @CounterIface
    public Response getCollegeByAbroad(int countryCode) throws TException {
        String cachedResult = null;
        Response result = null;
        int appid = 0; // query.appid
        try {
            cachedResult = redisClient.get(appid, KeyIdentifier.DICT_COLLEGE_COUNTRY.toString(), String.valueOf(countryCode), () -> {
                String r = null;
                try {
                    List<CollegeBasic> joinedResult = this.dao.getCollegeByAbroad(countryCode);
                    r = JSON.toJSONString(ResponseUtils.success(joinedResult));
                } catch (Exception e) {
                    logger.error("getResources error", e);
                    ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
                }
                return r;
            });
            result = JSON.parseObject(cachedResult, Response.class);

        } catch(RedisException e){
            WarnService.notify(e);
        } catch(Exception e) {
            e.printStackTrace();
            List<CollegeBasic> joinedResult = this.dao.getCollegeByAbroad(countryCode);
            result = ResponseUtils.success(joinedResult);
        }

        return result;
    }


    private Collection transformDataProvince(List<CollegeProvince> s) {
        DictCollegeProvinceHashMap dictCollege = new DictCollegeProvinceHashMap(s);
        Collection hm = dictCollege.getList();
        return hm;
    }

    private Collection transformData(List<College> s) {
        DictCollegeHashMap dictCollege = new DictCollegeHashMap(s);
        Collection hm = dictCollege.getList();
        return hm;
    }

    private String genCachKey(CommonQuery query) {
        return "all";
    }

}

class DictCollegeProvinceHashMap {

    private HashMap hm;

    public DictCollegeProvinceHashMap(List<CollegeProvince> colleges) {
        hm = new HashMap();
        for(CollegeProvince college: colleges) {
            put(hm, college);
        }
    }

    public HashMap getHashMap(){
        return this.hm;
    }

    public Collection getList() {
        return this.hm.values();
    }

    static void put(HashMap hm, CollegeProvince c) {
        int provinceCode = c.getProvince_code();
        if(!hm.containsKey(provinceCode)) {
            Map m = new HashMap();
            m.put("code", c.getProvince_code());
            m.put("name", c.getProvince_name());
            m.put("children", new ArrayList());
            hm.put(provinceCode, m);
        }
        List collegesInProvince = (List)(((Map)(hm.get(provinceCode))).get("children"));
        Map collegeInfo = new HashMap();
        collegeInfo.put("code", c.getCollege_code());
        collegeInfo.put("name", c.getCollege_name());
        collegeInfo.put("logo", c.getCollege_logo());
        collegesInProvince.add(collegeInfo);
    }
}

class DictCollegeHashMap {

    private HashMap hm;

    public DictCollegeHashMap(List<College> colleges) {
        hm = new HashMap();
        for(College college: colleges) {
            put(hm, college);
        }
    }

    public HashMap getHashMap(){
        return this.hm;
    }

    public Collection getList() {
        return this.hm.values();
    }

    static void put(HashMap hm, College c) {
        int countryCode = c.getCountry_code();
        if(!hm.containsKey(countryCode)) {
            Map m = new HashMap();
            m.put("code", c.getCountry_code());
            m.put("name", c.getCountry_name());
            m.put("ename", c.getCountry_ename());
            m.put("children", new ArrayList());
            hm.put(countryCode, m);
        }
        List collegesInProvince = (List)(((Map)(hm.get(countryCode))).get("children"));
        Map collegeInfo = new HashMap();
        collegeInfo.put("code", c.getCollege_code());
        collegeInfo.put("name", c.getCollege_name());
        collegeInfo.put("logo", c.getCollege_logo());
        collegesInProvince.add(collegeInfo);
    }
}
