package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictZhilianOccupationDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZhilianOccupationHandler extends AbstractOccupationHandler<DictZhilianOccupationDO> {

    @Override
    public ChannelType getChannelType() {
        return ChannelType.ZHILIAN;
    }
}
