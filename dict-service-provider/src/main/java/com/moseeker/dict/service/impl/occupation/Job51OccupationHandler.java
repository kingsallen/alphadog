package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Job51OccupationHandler extends AbstractOccupationHandler<Dict51jobOccupationDO> {
    @Autowired
    private Dict51OccupationDao dict51OccupationDao;

    @Override
    protected List<Dict51jobOccupationDO> getAllOccupation() {
        return dict51OccupationDao.getAllOccupation();
    }

    @Override
    protected List<Dict51jobOccupationDO> getSingleOccupation(JSONObject obj) {
        return dict51OccupationDao.getSingle(obj);
    }

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
}
