package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictVeryEastOccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictVeryEastOccupationDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VeryEastOccupationHandler extends DefaultOccupationHandler<DictVeryEastOccupationDO> {

    @Override
    public ChannelType getChannelType() {
        return ChannelType.VERYEAST;
    }
}
