package com.moseeker.baseorm.dao.dictdb;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictLiepinOccupation;
import com.moseeker.baseorm.db.dictdb.tables.Dict_51jobOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictLiepinOccupationRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_51jobOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DictLiepinOccupationDao extends AbstractDictOccupationDao<DictLiepinOccupationDO, DictLiepinOccupationRecord> {

    public DictLiepinOccupationDao() {
        super(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION, DictLiepinOccupationDO.class);
    }

    public DictLiepinOccupationDao(TableImpl<DictLiepinOccupationRecord> table, Class<DictLiepinOccupationDO> dictLiepinOccupationDO) {
        super(table, dictLiepinOccupationDO);
    }

    @Override
    protected Condition statusCondition() {
        return new Condition(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.STATUS.getName(), 1);
    }

    @Override
    protected Map<String, Object> queryEQParam(JSONObject obj) {
        Map<String, Object> paramMap=new HashMap<>();
        paramMap.put(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.CODE.getName(), obj.getInteger("code"));
        paramMap.put(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.PARENT_ID.getName(), obj.getInteger("parent_id"));
        paramMap.put(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.LEVEL.getName(), obj.getInteger("level"));
        return paramMap;
    }

    @Override
    protected boolean isTopOccupation(DictLiepinOccupationDO dictLiepinOccupationDO) {
        return dictLiepinOccupationDO!=null && dictLiepinOccupationDO.getId()>0 && dictLiepinOccupationDO.getParentId()>0;
    }

    @Override
    protected Condition conditionToSearchFather(DictLiepinOccupationDO dictLiepinOccupationDO) {
        return new Condition(DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.CODE.getName(),dictLiepinOccupationDO.getParentId());
    }

    @Override
    protected String otherCodeName() {
        return DictLiepinOccupation.DICT_LIEPIN_OCCUPATION.OTHER_CODE.getName();
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.LIEPIN;
    }
}
