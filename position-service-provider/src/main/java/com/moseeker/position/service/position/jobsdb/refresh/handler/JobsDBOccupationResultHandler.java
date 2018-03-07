package com.moseeker.position.service.position.jobsdb.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictJobsDBOccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.handler.AbstractOccupationResultHandler;
import com.moseeker.position.utils.PositionRefreshUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJobsDBOccupationDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class JobsDBOccupationResultHandler extends AbstractOccupationResultHandler<DictJobsDBOccupationDO> implements JobsDBResultHandlerAdapter {
    Logger logger= LoggerFactory.getLogger(JobsDBOccupationResultHandler.class);

    @Autowired
    private DictJobsDBOccupationDao occupationDao;

    @Override
    protected DictJobsDBOccupationDO buildOccupation(List<String> texts,List<String> codes,Map<String, Integer> newCode,JSONObject msg) {
        DictJobsDBOccupationDO temp=new DictJobsDBOccupationDO();

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
    protected void persistent(List<DictJobsDBOccupationDO> data) {
        int delCount=occupationDao.deleteAll();
        logger.info("jobsdb delete old Occupation "+delCount);
        occupationDao.addAllData(data);
        logger.info("jobsdb insert success");
    }

    @Override
    protected List<DictJobsDBOccupationDO> getAll() {
        return occupationDao.getAllOccupation();
    }

    @Override
    protected boolean equals(DictJobsDBOccupationDO oldData, DictJobsDBOccupationDO newData) {
        return oldData.getName().equals(newData.getName())
                && oldData.getCodeOther().equals(newData.getCodeOther())
                && oldData.getLevel() == newData.getLevel();
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }
}
