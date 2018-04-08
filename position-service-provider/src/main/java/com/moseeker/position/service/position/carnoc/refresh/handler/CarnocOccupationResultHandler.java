package com.moseeker.position.service.position.carnoc.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCarnocOccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.utils.PositionParamRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCarnocOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CarnocOccupationResultHandler extends AbstractOccupationResultHandler<DictCarnocOccupationDO> implements CarnocResultHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(CarnocOccupationResultHandler.class);

    @Override
    protected DictCarnocOccupationDO buildOccupation(List<String> texts, List<String> codes, Map<String, Integer> newCode, JSONObject msg) {
        DictCarnocOccupationDO temp = new DictCarnocOccupationDO();

        temp.setCodeOther(codes.get(codes.size() - 1));
        temp.setCode(newCode.get(temp.getCodeOther()));
        temp.setLevel((short) codes.size());
        temp.setName(PositionParamRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionParamRefreshUtils.parentCode(codes)));
        temp.setStatus((short) 1);

        return temp;
    }

    @Override
    protected boolean equals(DictCarnocOccupationDO oldData, DictCarnocOccupationDO newData) {
        return oldData.getName().equals(newData.getName())
                && oldData.getCodeOther().equals(newData.getCodeOther())
                && oldData.getLevel() == newData.getLevel();
    }
}
