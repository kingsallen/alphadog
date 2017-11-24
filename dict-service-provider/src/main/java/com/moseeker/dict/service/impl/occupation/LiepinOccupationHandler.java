package com.moseeker.dict.service.impl.occupation;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.dict.service.base.AbstractOccupationHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LiepinOccupationHandler  extends AbstractOccupationHandler<DictLiepinOccupationDO> {
    @Autowired
    DictLiepinOccupationDao occupationDao;

    @Override
    protected List<DictLiepinOccupationDO> getAllOccupation() {
        return occupationDao.getAllOccupation();
    }

    @Override
    protected List<DictLiepinOccupationDO> getSingleOccupation(JSONObject obj) {
        return occupationDao.getSingle(obj);
    }

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
    public String parentKeyName() {
        return "parent_id";
    }
}
