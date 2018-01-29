package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Job51OccupationHandler extends AbstractOccupationHandler<Dict51jobOccupationDO> {
    @Override
    public JSONObject toJsonObject(Dict51jobOccupationDO occupation) {
        JSONObject obj=new JSONObject();

        obj.put("code", occupation.getCode());
        obj.put("parent_id", occupation.getParentId());
        obj.put("name", occupation.getName());
        obj.put("code_other", occupation.getCodeOther());
        obj.put("level", occupation.getLevel());
        obj.put("status", occupation.getStatus());

        return obj;
    }

    @Override
    public String parentKeyName() {
        return "parent_id";
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB51;
    }
}
