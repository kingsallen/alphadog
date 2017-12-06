package com.moseeker.baseorm.dao.dictdb;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.Dict_51jobOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_51jobOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class Dict51OccupationDao extends AbstractDictOccupationDao<Dict51jobOccupationDO, Dict_51jobOccupationRecord> {

    public Dict51OccupationDao() {
        super(Dict_51jobOccupation.DICT_51JOB_OCCUPATION, Dict51jobOccupationDO.class);
    }

    public Dict51OccupationDao(TableImpl<Dict_51jobOccupationRecord> table, Class<Dict51jobOccupationDO> dict51jobOccupationDOClass) {
        super(table, dict51jobOccupationDOClass);
    }

    @Override
    protected Condition statusCondition() {
        return new Condition(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.STATUS.getName(), 1);
    }

    @Override
    protected Map<String, Object> queryEQParam(JSONObject obj) {
        Map<String, Object> paramMap=new HashMap<>();
        paramMap.put(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.CODE.getName(), obj.getInteger("code"));
        paramMap.put(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.PARENT_ID.getName(), obj.getInteger("parent_id"));
        paramMap.put(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.LEVEL.getName(), obj.getInteger("level"));
        return paramMap;
    }

    @Override
    protected boolean isTopOccupation(Dict51jobOccupationDO dict51jobOccupationDO) {
        return dict51jobOccupationDO!=null && dict51jobOccupationDO.getParentId()>0;
    }

    @Override
    protected Condition conditionToSearchFather(Dict51jobOccupationDO dict51jobOccupationDO) {
        return new Condition(Dict_51jobOccupation.DICT_51JOB_OCCUPATION.CODE.getName(),dict51jobOccupationDO.getParentId());
    }

    @Override
    protected String otherCodeName() {
        return Dict_51jobOccupation.DICT_51JOB_OCCUPATION.CODE_OTHER.getName();
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB51;
    }
}
