package com.moseeker.baseorm.dao.dictdb;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.db.dictdb.tables.DictCarnocOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCarnocOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCarnocOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DictCarnocOccupationDao extends AbstractDictOccupationDao<DictCarnocOccupationDO,DictCarnocOccupationRecord> {

    public DictCarnocOccupationDao() {
        super(DictCarnocOccupation.DICT_CARNOC_OCCUPATION, DictCarnocOccupationDO.class);
    }

    public DictCarnocOccupationDao(TableImpl<DictCarnocOccupationRecord> table, Class<DictCarnocOccupationDO> dictJobsDBOccupationDOClass) {
        super(table, dictJobsDBOccupationDOClass);
    }

    @Override
    protected Condition statusCondition() {
        return new Condition(DictCarnocOccupation.DICT_CARNOC_OCCUPATION.STATUS.getName(), 1);
    }

    @Override
    protected Map<String, Object> queryEQParam(JSONObject obj) {
        Map<String, Object> map=new HashMap<>();
        map.put(DictCarnocOccupation.DICT_CARNOC_OCCUPATION.CODE.getName(), obj.getIntValue("code"));
        map.put(DictCarnocOccupation.DICT_CARNOC_OCCUPATION.PARENT_ID.getName(), obj.getIntValue("parent_id"));
        map.put(DictCarnocOccupation.DICT_CARNOC_OCCUPATION.LEVEL.getName(), obj.getIntValue("level"));
        return map;
    }

    @Override
    protected boolean isTopOccupation(DictCarnocOccupationDO dictCarnocOccupationDO) {
        return dictCarnocOccupationDO!=null && dictCarnocOccupationDO.getParentId()==0;
    }

    @Override
    protected Condition conditionToSearchFather(DictCarnocOccupationDO dictCarnocOccupationDO) {
        return new Condition(DictCarnocOccupation.DICT_CARNOC_OCCUPATION.CODE.getName(),dictCarnocOccupationDO.getParentId());
    }


    @Override
    protected String otherCodeName() {
        return DictCarnocOccupation.DICT_CARNOC_OCCUPATION.CODE_OTHER.getName();
    }

    @Override
    public int deleteAll(){
        Condition condition=new Condition(DictCarnocOccupation.DICT_CARNOC_OCCUPATION.STATUS.getName(),1);
        return delete(condition);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.CARNOC;
    }
}
