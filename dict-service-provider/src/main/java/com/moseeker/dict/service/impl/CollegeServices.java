package com.moseeker.dict.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCollegeDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.struct.College;
import java.util.*;
import javax.annotation.Resource;
import org.apache.thrift.TException;
import org.jooq.Record;
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

    protected College DBToStruct(Record r) {
        College c = new College();
        c.setCollege_code((Integer) r.getValue("college_code"));
        c.setCollge_name((String)r.getValue("college_name"));
        c.setCollge_logo((String)r.getValue("college_logo"));
        c.setProvince_code((Integer) r.getValue("province_code"));
        c.setProvince_name((String)r.getValue("province_name"));
        return c;
    }

    protected List<College> DBsToStructs(List<Record> records) {
        List structs = new ArrayList<>();
        if(records != null && records.size() > 0) {
            for(Record r : records) {
                structs.add(DBToStruct(r));
            }
        }
        return structs;
    }

    @CounterIface
    public Response getResources(CommonQuery query) throws TException {
        String cachKey = genCachKey(query);
        String cachedResult = null;
        Response result = null;
        String patternString = "DICT_COLLEGE";
        int appid = 0; // query.appid
        try {
            cachedResult = redisClient.get(appid, patternString, cachKey, () -> {
                String r = null;
                try {
                    List joinedResult = this.dao.getJoinedResults(QueryConvert.commonQueryConvertToQuery(query));
                    List<College> structs = DBsToStructs(joinedResult);
                    Collection transformed = transformData(structs);
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
            List joinedResult = this.dao.getJoinedResults(QueryConvert.commonQueryConvertToQuery(query));
            List<College> structs = DBsToStructs(joinedResult);
            result = ResponseUtils.success(transformData(structs));
        }

        return result;

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
        collegeInfo.put("name", c.getCollge_name());
        collegeInfo.put("logo", c.getCollge_logo());
        collegesInProvince.add(collegeInfo);
    }
}

