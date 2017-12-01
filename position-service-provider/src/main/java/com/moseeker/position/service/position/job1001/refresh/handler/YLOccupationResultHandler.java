package com.moseeker.position.service.position.job1001.refresh.handler;

import com.alibaba.fastjson.JSONObject;
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

import java.util.List;
import java.util.Map;

@Component
public class YLOccupationResultHandler extends AbstractOccupationResultHandler<DictJob1001OccupationDO> implements VEResultHandlerAdapter {
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
        int delCount=occupationDao.deleteAll();
        logger.info("job1001 delete old Occupation "+delCount);
        occupationDao.addAllData(data);
        logger.info("job1001 insert success");
    }

    @Override
    public String occupationKey() {
        return "occupation";
    }

}
