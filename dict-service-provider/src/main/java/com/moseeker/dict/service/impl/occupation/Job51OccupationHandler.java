package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Job51OccupationHandler extends DefaultOccupationHandler<Dict51jobOccupationDO> {

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB51;
    }
}
