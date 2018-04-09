package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJobsDBOccupationDO;
import org.springframework.stereotype.Component;

@Component
public class JobsDBOccupationHandler extends AbstractOccupationHandler<DictJobsDBOccupationDO>{

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }
}
