package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictJob1001OccupationDao;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJob1001OccupationDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Job1001OccupationHandler extends AbstractOccupationHandler<DictJob1001OccupationDO> {
    @Autowired
    private DictJob1001OccupationDao dictJob1001OccupationDao;

    @Override
    protected List<DictJob1001OccupationDO> getAllOccupation() {
        return dictJob1001OccupationDao.getAllOccupation();
    }

    @Override
    protected List<DictJob1001OccupationDO> getSingleOccupation(JSONObject obj) {
        return dictJob1001OccupationDao.getSingle(obj);
    }

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
    protected String parentKeyName() {
        return "parent_id";
    }
}
