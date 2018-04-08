package com.moseeker.position.service.position.veryeast.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictVeryEastOccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.utils.PositionParamRefreshUtils;
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
    Logger logger = LoggerFactory.getLogger(VEOccupationResultHandler.class);

    @Override
    protected DictVeryEastOccupationDO buildOccupation(List<String> texts, List<String> codes, Map<String, Integer> newCode, JSONObject msg) {
        DictVeryEastOccupationDO temp = new DictVeryEastOccupationDO();

        temp.setCodeOther(codes.get(codes.size() - 1));
        temp.setCode(newCode.get(temp.getCodeOther()));
        temp.setLevel((short) codes.size());
        temp.setName(PositionParamRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionParamRefreshUtils.parentCode(codes)));
        temp.setStatus((short) 1);

        return temp;
    }

    @Override
    protected boolean equals(DictVeryEastOccupationDO oldData, DictVeryEastOccupationDO newData) {
        return oldData.getName().equals(newData.getName())
                && oldData.getCodeOther().equals(newData.getCodeOther())
                && oldData.getLevel() == newData.getLevel();
    }
}
