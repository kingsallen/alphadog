package com.moseeker.function.service.chaos.position;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.function.service.PositionEmailBuilder;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJob1001PositionDO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class VLPositionEmailBuilder implements PositionEmailBuilder<ThirdpartyJob1001PositionDO>{

    @Override
    public Map<String, String> message(ThirdpartyJob1001PositionDO position) {
        Map<String, String> map=new HashMap<>();
        map.put("【发布网站】",position.getSubsite());
        return map;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB1001;
    }
}
