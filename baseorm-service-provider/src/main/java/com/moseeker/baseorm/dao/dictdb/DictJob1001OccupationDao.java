package com.moseeker.baseorm.dao.dictdb;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictJob1001Occupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictJob1001OccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJob1001OccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DictJob1001OccupationDao extends AbstractDictOccupationDao<DictJob1001OccupationDO,DictJob1001OccupationRecord> {
    public DictJob1001OccupationDao() {
        super(DictJob1001Occupation.DICT_JOB1001_OCCUPATION, DictJob1001OccupationDO.class);
    }

    public DictJob1001OccupationDao(TableImpl<DictJob1001OccupationRecord> table, Class<DictJob1001OccupationDO> DictJob1001OccupationDOClass) {
        super(table, DictJob1001OccupationDOClass);
    }

    @Override
    protected Condition statusCondition() {
        return new Condition(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.STATUS.getName(), 1);
    }

    @Override
    protected Map<String, Object> queryEQParam(JSONObject obj) {
        Map<String, Object> paramMap=new HashMap<>();
        paramMap.put(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.CODE.getName(), obj.getInteger("code"));
        paramMap.put(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.PARENT_ID.getName(), obj.getInteger("parent_id"));
        paramMap.put(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.LEVEL.getName(), obj.getInteger("level"));
        return paramMap;
    }

    @Override
    protected boolean isTopOccupation(DictJob1001OccupationDO dictJob1001OccupationDO) {
        return dictJob1001OccupationDO!=null && dictJob1001OccupationDO.getParentId()>0;
    }

    @Override
    protected Condition conditionToSearchFather(DictJob1001OccupationDO dictJob1001OccupationDO) {
        return new Condition(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.CODE.getName(),dictJob1001OccupationDO.getParentId());
    }

    @Override
    protected String otherCodeName() {
        return DictJob1001Occupation.DICT_JOB1001_OCCUPATION.CODE.getName();
    }

    public int deleteAll(){
        Condition condition=new Condition("code",0, ValueOp.NEQ);
        return delete(condition);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB1001;
    }


    public int deleteAllBySubsite(String subsite){
        return delete(new Condition(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.SUBSITE.getName(),subsite));
    }
}
