package com.moseeker.position.service.position.job51.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.utils.PositionRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class Job51OccupationResultHandler extends AbstractOccupationResultHandler<Dict51jobOccupationDO>{
    Logger logger= LoggerFactory.getLogger(Job51OccupationResultHandler.class);

    @Autowired
    Dict51OccupationDao occupationDao;

    @Override
    protected Dict51jobOccupationDO buildOccupation(List<String> texts, List<String> codes, Map<String, Integer> newCode, JSONObject msg) {
        Dict51jobOccupationDO temp=new Dict51jobOccupationDO();

        temp.setCodeOther(Integer.valueOf(codes.get(codes.size()-1)));
        temp.setCode(newCode.get(temp.getCodeOther()));
        temp.setLevel((short)codes.size());
        temp.setName(PositionRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionRefreshUtils.parentCode(codes)));
        temp.setStatus((short)1);

        return temp;
    }

    @Override
    protected void persistent(List<Dict51jobOccupationDO> data) {
        int delCount=occupationDao.deleteAll();
        logger.info("veryeast delete old Occupation "+delCount);
        occupationDao.addAllData(data);
        logger.info("veryeast insert success");
    }

    @Override
    protected String occupationKey() {
        return null;
    }

    @Override
    protected List<Occupation> toList(JSONObject msg) {
        return null;
    }
}
