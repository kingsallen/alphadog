package com.moseeker.entity.biz;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.configdb.ConfigSysCvTplDao;
import com.moseeker.baseorm.dao.dictdb.DictCollegeDao;
import com.moseeker.baseorm.dao.dictdb.DictCountryDao;
import com.moseeker.baseorm.dao.dictdb.DictIndustryTypeDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysCvTplRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.biz.ProfileExtParam;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class ProfileParseUtil {

    @Autowired
    private DictCountryDao countryDao;

    @Autowired
    private DictIndustryTypeDao industryTypeDao;

    @Autowired
    private DictCollegeDao collegeDao;

    @Autowired
    private ConfigSysCvTplDao configSysCvTplDao;

    /**
     * 初始化解析简历需要的参数
     * @return
     */
    public ProfileExtParam initParseProfileParam(){
        ProfileExtParam extParam = new ProfileExtParam();
        extParam.setCountryDOList(countryDao.getAll());
        extParam.setDictIndustryTypeDOList(industryTypeDao.getAll());
        extParam.setCollegeMap(collegeDao.getCollegeMap());
        return extParam;
    }

    public void handerSortOtherList(List<ProfileOtherRecord> otherRecords){
        if(!StringUtils.isEmptyList(otherRecords)){
            List<String> orderList=this.getConfigSysCvTplList();
            for(ProfileOtherRecord record:otherRecords){
                String other=record.getOther();
                if(StringUtils.isNotNullOrEmpty(other)){
                    String otherData=this.handlerSortOther(other,orderList);
                    record.setOther(otherData);
                }
            }
        }
    }
    public void handerSortCustomizeResumeOtherList(List<CustomizeResume> datas){
        if(!StringUtils.isEmptyList(datas)){
            List<String> orderList=this.getConfigSysCvTplList();
            for(CustomizeResume record:datas){
                String other=record.getOther();
                if(StringUtils.isNotNullOrEmpty(other)){
                    String otherData=this.handlerSortOther(other,orderList);
                    record.setOther(otherData);
                }
            }
        }
    }

    public void handerSortCustomizeResumeOther(CustomizeResume resume){
        if(resume!=null){
            List<String> orderList=this.getConfigSysCvTplList();
            String other=resume.getOther();
            if(StringUtils.isNotNullOrEmpty(other)){
                String otherData=this.handlerSortOther(other,orderList);
                resume.setOther(otherData);
            }

        }
    }

    public void handerSortprofileOtherMap(Map<String,Object> resume){
        if(resume!=null){
            List<String> orderList=this.getConfigSysCvTplList();
            String other=(String)resume.get("other");
            if(StringUtils.isNotNullOrEmpty(other)){
                String otherData=this.handlerSortOther(other,orderList);
                resume.put("other",otherData);
            }

        }
    }
    /*
    处理other
     */
    private String handlerSortOther(String other,List<String> dataList){
        Map<String,Object> result=new LinkedHashMap<>();
        Map<String,Object> data= JSON.parseObject(other);
        if(dataList!=null&&dataList.size()>0){
            for(String field:dataList){
                for(String key:data.keySet()){
                    if(field.equals(key)){
                        result.put(field,data.get(key));
                    }
                }
            }
        }
        if(result!=null&&!result.isEmpty()){
            return JSON.toJSONString(result);
        }
        //原来是return other
        return null;
    }

    private List<String> getConfigSysCvTplList(){
        Query query=new Query.QueryBuilder().where("disable",0).orderBy("priority", Order.ASC).buildQuery();
        List<ConfigSysCvTplRecord> list=configSysCvTplDao.getRecords(query);
        List<String> result=new LinkedList<>();
        if(list!=null&&list.size()>0){
            for(ConfigSysCvTplRecord record:list){
                result.add(record.getFieldName());
            }
            return result;
        }
        return null;
    }

}
