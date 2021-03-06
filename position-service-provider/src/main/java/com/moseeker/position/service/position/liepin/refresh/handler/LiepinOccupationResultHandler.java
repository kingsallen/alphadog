package com.moseeker.position.service.position.liepin.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.utils.PositionParamRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LiepinOccupationResultHandler extends AbstractOccupationResultHandler<DictLiepinOccupationDO> implements LiepinResultHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(LiepinOccupationResultHandler.class);

    @Override
    protected DictLiepinOccupationDO buildOccupation(List<String> texts, List<String> codes, Map<String, Integer> newCode, JSONObject msg) {
        DictLiepinOccupationDO temp = new DictLiepinOccupationDO();

        temp.setOtherCode(codes.get(codes.size() - 1));
        temp.setCode(newCode.get(temp.getOtherCode()));
        temp.setLevel((short) codes.size());
        temp.setName(PositionParamRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionParamRefreshUtils.parentCode(codes)));
        temp.setStatus((short) 1);

        return temp;
    }

    @Override
    protected boolean equals(DictLiepinOccupationDO oldData, DictLiepinOccupationDO newData) {
        return oldData.getName().equals(newData.getName())
                && oldData.getOtherCode().equals(newData.getOtherCode())
                && oldData.getLevel() == newData.getLevel();
    }
}
