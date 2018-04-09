package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolCompanyTagDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolCompanyTagUserDao;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyTagUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 18/4/9.
 */
@Service
public class CompanyTagService {
    @Autowired
    private TalentpoolCompanyTagUserDao talentpoolCompanyTagUserDao;
    @Autowired
    private TalentpoolCompanyTagDao talentpoolCompanyTagDao;
    @Resource(name = "cacheClient")
    private RedisClient client;

    SearchengineServices.Iface service = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
    private static String COMPANYTAG_ES_STATUS="COMPANY_TAG_ES_STATUS";

    /*
      type=0 新增标签
      type=1 修改标签
      type=2 删除标签
     */
    public void handlerCompanyTag(List<Integer> tagIdList, int type) throws TException {
        if(type==2){//删除标签只需要执行删除操作即可
            talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
        }else{
            //新增标签，不用调用删除原有表中的标签和人才的对应关系，只需要增加就可以
            Map<String,Object> map=talentpoolCompanyTagDao.getTagById(tagIdList.get(0));
            Map<String,String> params=new HashMap<>();
            if(map!=null&&!map.isEmpty()){
                for(String key:map.keySet()){
                    params.put(key,String.valueOf(map.get(key)));
                }
                List<Integer> userIdList=service.queryCompanyTagUserIdList(params);
                if(type==0){
                    if(!StringUtils.isEmptyList(userIdList)) {
                        List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
                        for (Integer userId : userIdList) {
                            TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                            record.setTagId(tagIdList.get(0));
                            record.setUserId(userId);
                            list.add(record);
                        }
                        talentpoolCompanyTagUserDao.batchAddTagAndUser(list);
                    }

                }else if(type==1){//修改标签需要把表中原有的数据全部删除，
                    talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
                    if(!StringUtils.isEmptyList(userIdList)){
                        List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
                        for (Integer userId : userIdList) {
                            TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                            record.setTagId(tagIdList.get(0));
                            record.setUserId(userId);
                            list.add(record);
                        }
                        talentpoolCompanyTagUserDao.batchAddTagAndUser(list);
                    }

                }
        }

        //更新es中tag_id和人才的关系
        if(!StringUtils.isEmptyList(tagIdList)){
            for(Integer tagId:tagIdList){
                Map<String,Object> result=new HashMap<>();
                result.put("tagId",tagId);
                result.put("type",type);
                client.lpush(Constant.APPID_ALPHADOG,
                        "ES_UPDATE_INDEX_COMPANYTAG_ID", JSON.toJSONString(result));
                //将这个的tag置位更新状态0是更新 1是更新完成
                client.set(Constant.APPID_ALPHADOG, COMPANYTAG_ES_STATUS,
                        String.valueOf(tagId), String.valueOf(0));
            }

        }
        }


    }

}
