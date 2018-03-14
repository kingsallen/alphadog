package com.moseeker.function.service.chaos.position;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.function.service.PositionEmailBuilder;
import com.moseeker.function.service.chaos.util.PositionSyncMailUtil;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyJobsDBPositionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JobsDBPositionEmailBuilder implements PositionEmailBuilder<ThirdpartyJobsDBPositionDO> {
    @Autowired
    PositionSyncMailUtil positionSyncMailUtil;

    @Override
    public Map<String, String> message(ThirdpartyJobsDBPositionDO position) throws BIZException {
        Map<String, String> map=new HashMap<>();
        map.put("【summary1】",position.getSummary1());
        map.put("【summary2】",position.getSummary2());
        map.put("【summary3】",position.getSummary3());

        map.put("【二级地址】",position.getChildAddressName());
        map.put("【职能2】",positionSyncMailUtil.getOccupation(getChannelType().getValue(),position.getOccupationExt1()));
        map.put("【职能3】",positionSyncMailUtil.getOccupation(getChannelType().getValue(),position.getOccupationExt2()));
        return map;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }
}
