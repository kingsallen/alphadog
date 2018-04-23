package com.moseeker.dict.service.impl.occupation;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCarnocOccupationDO;
import org.springframework.stereotype.Component;

@Component
public class CarnocOccupationHandler extends DefaultOccupationHandler<DictCarnocOccupationDO>{
    @Override
    public ChannelType getChannelType() {
        return ChannelType.CARNOC;
    }
}
