package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJob1001OccupationDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Job1001OccupationHandler extends AbstractOccupationHandler<DictJob1001OccupationDO> {
    @Override
    protected JSONObject toJsonObject(DictJob1001OccupationDO occupation) {
        JSONObject obj=new JSONObject();

        obj.put("code", occupation.getCode());
        obj.put("parent_id", occupation.getParentId());
        obj.put("name", occupation.getName());
        obj.put("code_other", occupation.getCodeOther());
        obj.put("level", occupation.getLevel());
        obj.put("status", occupation.getStatus());
        obj.put("subsite",occupation.getSubsite());

        return obj;
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB1001;
    }
}
