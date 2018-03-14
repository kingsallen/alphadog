package com.moseeker.position.service.position.job51.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.utils.PositionParamRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Job51OccupationResultHandler extends AbstractOccupationResultHandler<Dict51jobOccupationDO> implements Job51ResultHandlerAdapter{
    Logger logger= LoggerFactory.getLogger(Job51OccupationResultHandler.class);

    @Autowired
    Dict51OccupationDao occupationDao;



    @Override
    protected Dict51jobOccupationDO buildOccupation(List<String> texts, List<String> codes, Map<String, Integer> newCode, JSONObject msg) {
        Dict51jobOccupationDO temp=new Dict51jobOccupationDO();

        temp.setCodeOther(codes.get(codes.size()-1));
        temp.setCode(newCode.get(temp.getCodeOther()));
        temp.setLevel((short)codes.size());
        temp.setName(PositionParamRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionParamRefreshUtils.parentCode(codes)));
        temp.setStatus((short)1);

        return temp;
    }

    @Override
    protected void persistent(List<Dict51jobOccupationDO> data) {
        int delCount=occupationDao.deleteAll();
        logger.info("job51 delete old Occupation "+delCount);
        occupationDao.addAllData(data);
        logger.info("job51 insert success");
    }

    @Override
    protected List<Dict51jobOccupationDO> getAll() {
        return occupationDao.getAllOccupation();
    }

    @Override
    protected boolean equals(Dict51jobOccupationDO oldData, Dict51jobOccupationDO newData) {
        return oldData.getName().equals(newData.getName())
                && oldData.getCodeOther().equals(newData.getCodeOther())
                && oldData.getLevel() == newData.getLevel();
    }

    /*@Override
    protected List<Occupation> toList(JSONObject msg) {
        TypeReference<List<List<String>>> typeRef
                = new TypeReference<List<List<String>>>() {};

        List<List<String>> occupations= JSON.parseObject(msg.getString(occupationKey()),typeRef);

        List<Occupation> result=new ArrayList<>();
        for(List<String> text:occupations){
            Occupation occupation=new Occupation();
            occupation.setText(text);
            occupation.setCode(new ArrayList<>());
            result.add(occupation);
        }

        generateNewCode(result);

        return result;

    }*/
}
