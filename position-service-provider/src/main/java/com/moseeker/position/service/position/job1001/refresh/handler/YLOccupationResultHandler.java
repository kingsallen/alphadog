package com.moseeker.position.service.position.job1001.refresh.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.dao.dictdb.DictJob1001OccupationDao;
import com.moseeker.common.util.MD5Util;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.utils.PositionRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJob1001OccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class YLOccupationResultHandler extends AbstractOccupationResultHandler<DictJob1001OccupationDO> implements YLResultHandlerAdapter {
    Logger logger= LoggerFactory.getLogger(YLOccupationResultHandler.class);

    @Autowired
    private DictJob1001OccupationDao occupationDao;

    @Autowired
    List<AbstractYLParamRefresher> subsiteList;

    @Override
    protected Map<String, Integer> generateNewKey(List<String> otherCodes,JSONObject msg) {
        String subsite=msg.getString("subsite");
        int DEFAULT_KEY_SEED=getSeed(subsite);
        return PositionRefreshUtils.generateNewKey(otherCodes.iterator(),DEFAULT_KEY_SEED,otherCodes.size());
    }

    @Override
    protected DictJob1001OccupationDO buildOccupation(List<String> texts,List<String> codes,Map<String, Integer> newCode,JSONObject msg) {
        DictJob1001OccupationDO temp=new DictJob1001OccupationDO();

        temp.setCodeOther(codes.get(codes.size()-1));
        temp.setCode(newCode.get(temp.getCodeOther()));
        temp.setLevel((short)codes.size());
        temp.setName(PositionRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionRefreshUtils.parentCode(codes)));
        temp.setStatus((short)1);
        temp.setSubsite(msg.getString("subsite"));


        return temp;
    }

    @Override
    @Transactional
    protected void persistent(List<DictJob1001OccupationDO> data) {
        if(data==null || data.isEmpty()){
            return;
        }
        int delCount=occupationDao.deleteAllBySubsite(data.get(0).getSubsite());
        logger.info("job1001 delete old Occupation "+delCount);
        List<DictJob1001OccupationDO> insertData=occupationDao.addAllData(data);
        logger.info("job1001 insert success size: {}",insertData.size());
    }

    @Override
    protected List<DictJob1001OccupationDO> getAll() {
        return occupationDao.getAllOccupation();
    }

    @Override
    protected boolean equals(DictJob1001OccupationDO oldData, DictJob1001OccupationDO newData) {
        //subsite字段不相同的话直接返回true，不需要比较，因为之比较subsite相同的职能
        if(!oldData.getSubsite().equals(newData.getSubsite())){
            return true;
        }
        return oldData.getName().equals(newData.getName())
                && oldData.getCodeOther().equals(newData.getCodeOther())
                && oldData.getLevel() == newData.getLevel();
    }

    @Override
    protected List<Occupation> toList(JSONObject msg) {
        TypeReference<List<List<String>>> typeRef
                = new TypeReference<List<List<String>>>() {};

        List<List<String>> occupations=JSON.parseObject(msg.getString(occupationKey()),typeRef);

        List<Occupation> result=new ArrayList<>();
        for(List<String> text:occupations){
            Occupation occupation=new Occupation();
            occupation.setText(text);
            occupation.setCode(new ArrayList<>());
            result.add(occupation);
        }

        String subsite=msg.getString("subsite");
        generateNewCode(result,subsite);

        return result;

    }

    private int getSeed(String subsite){
        int DEFAULT_KEY_SEED=0;

        for(int i=0;i<subsiteList.size();i++){
            String subsiteTemp=subsiteList.get(i).getSubsite();
            if(subsiteTemp.equals(subsite)){
                DEFAULT_KEY_SEED=subsiteList.get(i).getSeed();
            }
        }

        return DEFAULT_KEY_SEED;
    }
}
