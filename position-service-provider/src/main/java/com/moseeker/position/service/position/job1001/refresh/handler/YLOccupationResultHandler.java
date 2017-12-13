package com.moseeker.position.service.position.job1001.refresh.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.dao.dictdb.DictJob1001OccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.service.position.veryeast.refresh.handler.VEResultHandlerAdapter;
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

    @Override
    public DictJob1001OccupationDO buildOccupation(List<String> texts,List<String> codes,Map<Integer, Integer> newCode,JSONObject msg) {
        DictJob1001OccupationDO temp=new DictJob1001OccupationDO();

        temp.setCodeOther(PositionRefreshUtils.lastCode(codes));
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
    public void persistent(List<DictJob1001OccupationDO> data) {
        if(data==null || data.isEmpty()){
            return;
        }
        int delCount=occupationDao.deleteAllBySubsite(data.get(0).getSubsite());
        logger.info("job1001 delete old Occupation "+delCount);
        occupationDao.addAllData(data);
        logger.info("job1001 insert success");
    }

    @Override
    public String occupationKey() {
        return "occupation";
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

        generateNewCode(result);

        return result;

    }
}
