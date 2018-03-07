package com.moseeker.baseorm.dao.dictdb;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.db.dictdb.tables.DictJobsdbOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictJobsdbOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJobsDBOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public class DictJobsDBOccupationDao extends AbstractDictOccupationDao<DictJobsDBOccupationDO,DictJobsdbOccupationRecord> {

    public DictJobsDBOccupationDao() {
        super(DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION, DictJobsDBOccupationDO.class);
    }

    public DictJobsDBOccupationDao(TableImpl<DictJobsdbOccupationRecord> table, Class<DictJobsDBOccupationDO> dictJobsDBOccupationDOClass) {
        super(table, dictJobsDBOccupationDOClass);
    }

    @Override
    protected Condition statusCondition() {
        return new Condition(DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION.STATUS.getName(), 1);
    }

    @Override
    protected Map<String, Object> queryEQParam(JSONObject obj) {
        Map<String, Object> map=new HashMap<>();
        map.put(DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION.CODE.getName(), obj.getIntValue("code"));
        map.put(DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION.PARENT_ID.getName(), obj.getIntValue("parent_id"));
        map.put(DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION.LEVEL.getName(), obj.getIntValue("level"));
        return map;
    }

    @Override
    protected boolean isTopOccupation(DictJobsDBOccupationDO dictJobsDBOccupationDO) {
        return dictJobsDBOccupationDO!=null && dictJobsDBOccupationDO.getParentId()==0;
    }

    @Override
    protected Condition conditionToSearchFather(DictJobsDBOccupationDO dictJobsDBOccupationDO) {
        return new Condition(DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION.CODE.getName(),dictJobsDBOccupationDO.getParentId());
    }

    @Override
    protected String otherCodeName() {
        return DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION.CODE_OTHER.getName();
    }

    public int deleteAll(){
        Condition condition=new Condition(DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION.CODE.getName(),0, ValueOp.NEQ);
        return delete(condition);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }


}
