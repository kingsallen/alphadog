package com.moseeker.baseorm.dao.dictdb;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictVeryeastOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictVeryeastOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictVeryEastOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DictVeryEastOccupationDao extends AbstractDictOccupationDao<DictVeryEastOccupationDO,DictVeryeastOccupationRecord> {
    public DictVeryEastOccupationDao() {
        super(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION, DictVeryEastOccupationDO.class);
    }

    public DictVeryEastOccupationDao(TableImpl<DictVeryeastOccupationRecord> table, Class<DictVeryEastOccupationDO> dictVeryEastOccupationDOClass) {
        super(table, dictVeryEastOccupationDOClass);
    }

    @Override
    protected Condition statusCondition() {
        return new Condition(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.STATUS.getName(), 1);
    }

    @Override
    protected Map<String, Object> queryEQParam(JSONObject obj) {
        Map<String, Object> map=new HashMap<>();
        map.put(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.CODE.getName(), obj.getIntValue("code"));
        map.put(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.PARENT_ID.getName(), obj.getIntValue("parent_id"));
        map.put(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.LEVEL.getName(), obj.getIntValue("level"));
        return map;
    }

    @Override
    protected boolean isTopOccupation(DictVeryEastOccupationDO dictVeryEastOccupationDO) {
        return dictVeryEastOccupationDO!=null && dictVeryEastOccupationDO.getParentId()==0;
    }

    @Override
    protected Condition conditionToSearchFather(DictVeryEastOccupationDO dictVeryEastOccupationDO) {
        return new Condition(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.CODE.getName(),dictVeryEastOccupationDO.getParentId());
    }

    @Override
    protected String otherCodeName() {
        return DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.CODE_OTHER.getName();
    }

    public int deleteAll(){
        Condition condition=new Condition(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.CODE.getName(),0, ValueOp.NEQ);
        return delete(condition);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.VERYEAST;
    }
}
