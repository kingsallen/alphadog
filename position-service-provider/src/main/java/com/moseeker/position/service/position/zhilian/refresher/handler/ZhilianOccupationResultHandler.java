package com.moseeker.position.service.position.zhilian.refresher.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictZhilianOccupationDao;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.utils.PositionRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ZhilianOccupationResultHandler extends AbstractOccupationResultHandler<DictZhilianOccupationDO> implements ZhilianResultHandlerAdapter {
    Logger logger= LoggerFactory.getLogger(ZhilianOccupationResultHandler.class);

    @Autowired
    DictZhilianOccupationDao occupationDao;

    @Override
    protected DictZhilianOccupationDO buildOccupation(List<String> texts, List<String> codes, Map<String, Integer> newCode, JSONObject msg) {
        DictZhilianOccupationDO temp=new DictZhilianOccupationDO();

        temp.setCodeOther(Integer.valueOf(codes.get(codes.size()-1)));
        temp.setCode(newCode.get(temp.getCodeOther()));
        temp.setLevel((short)codes.size());
        temp.setName(PositionRefreshUtils.lastString(texts));
        temp.setParentId(newCode.get(PositionRefreshUtils.parentCode(codes)));
        temp.setStatus((short)1);

        return temp;
    }

    @Override
    protected void persistent(List<DictZhilianOccupationDO> data) {
        int delCount=occupationDao.deleteAll();
        logger.info("zhilian delete old Occupation "+delCount);
        occupationDao.addAllData(data);
        logger.info("zhilian insert success");
    }
}
