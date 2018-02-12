package com.moseeker.position.service.position.liepin.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.service.position.job51.refresh.handler.Job51ResultHandlerAdapter;
import com.moseeker.position.utils.PositionRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LiepinOccupationResultHandler extends AbstractOccupationResultHandler<DictLiepinOccupationDO> implements LiepinResultHandlerAdapter{
    Logger logger= LoggerFactory.getLogger(LiepinOccupationResultHandler.class);

    @Autowired
    DictLiepinOccupationDao occupationDao;

    @Override
    protected DictLiepinOccupationDO buildOccupation(List<String> texts, List<String> codes, Map<String, Integer> newCode, JSONObject msg) {
        DictLiepinOccupationDO temp=new DictLiepinOccupationDO();

        temp.setOtherCode(codes.get(codes.size()-1));
        temp.setCode(newCode.get(temp.getOtherCode()));
        temp.setLevel((short)codes.size());
        temp.setName(PositionRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionRefreshUtils.parentCode(codes)));
        temp.setStatus((short)1);

        return temp;
    }

    @Override
    protected void persistent(List<DictLiepinOccupationDO> data) {
        int delCount=occupationDao.deleteAll();
        logger.info("liepin delete old Occupation "+delCount);
        occupationDao.addAllData(data);
        logger.info("liepin insert success");
    }

    @Override
    public List<DictLiepinOccupationDO> getAll() {
        return occupationDao.getAllOccupation();
    }

    @Override
    public boolean equals(DictLiepinOccupationDO oldData, DictLiepinOccupationDO newData) {
        return oldData.getName().equals(newData.getName())
                && oldData.getOtherCode().equals(newData.getOtherCode())
                && oldData.getLevel() == newData.getLevel();
    }
}
