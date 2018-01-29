package com.moseeker.position.service.position.veryeast.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictVeryEastOccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.utils.PositionRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictVeryEastOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class VEOccupationResultHandler extends AbstractOccupationResultHandler<DictVeryEastOccupationDO> implements VEResultHandlerAdapter {
    Logger logger= LoggerFactory.getLogger(VEOccupationResultHandler.class);

    @Autowired
    private DictVeryEastOccupationDao occupationDao;

    @Override
    public DictVeryEastOccupationDO buildOccupation(List<String> texts,List<String> codes,Map<String, Integer> newCode,JSONObject msg) {
        DictVeryEastOccupationDO temp=new DictVeryEastOccupationDO();

        temp.setCodeOther(codes.get(codes.size()-1));
        temp.setCode(newCode.get(temp.getCodeOther()));
        temp.setLevel((short)codes.size());
        temp.setName(PositionRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionRefreshUtils.parentCode(codes)));
        temp.setStatus((short)1);

        return temp;
    }

    @Override
    @Transactional
    public void persistent(List<DictVeryEastOccupationDO> data) {
        int delCount=occupationDao.deleteAll();
        logger.info("veryeast delete old Occupation "+delCount);
        occupationDao.addAllData(data);
        logger.info("veryeast insert success");
    }

    @Override
    public String occupationKey() {
        return "occupation";
    }

    @Override
    protected List<Occupation> toList(JSONObject msg) {
        return msg.getJSONArray(occupationKey()).toJavaList(Occupation.class);
    }

}
