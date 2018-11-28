package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_58jobOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict58jobOccupationDO;
import org.springframework.stereotype.Component;

/**
 * @author cjm
 * @date 2018-11-26 9:55
 **/
@Component
public class Job58OccupationHandler extends AbstractOccupationHandler<Dict58jobOccupationDO> {
    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB58;
    }

    @Override
    public JSONObject toJsonObject(Dict58jobOccupationDO occupation) {
        JSONObject obj=new JSONObject();

        obj.put("code", occupation.getCode());
        obj.put("code_other", occupation.getCodeOther());
        obj.put("name", occupation.getName());
        obj.put("parent_id", occupation.getParentId());
        obj.put("level", occupation.getLevel());
        obj.put("status", occupation.getStatus());

        return obj;
    }
}
