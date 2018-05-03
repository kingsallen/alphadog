package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LiepinOccupationHandler  extends AbstractOccupationHandler<DictLiepinOccupationDO> {
    @Override
    public JSONObject toJsonObject(DictLiepinOccupationDO occupation) {
        JSONObject obj=new JSONObject();

        obj.put("code", occupation.getCode());
        obj.put("parent_id", occupation.getParentId());
        obj.put("name", occupation.getName());
        obj.put("code_other", occupation.getOtherCode());
        obj.put("level", occupation.getLevel());
        obj.put("status", occupation.getStatus());

        return obj;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.LIEPIN;
    }
}
