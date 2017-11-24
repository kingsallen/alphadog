package com.moseeker.baseorm.dao.dictdb;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictVeryeastOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictVeryeastOccupationRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictVeryEastOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DictVeryEastOccupationDao extends JooqCrudImpl<DictVeryEastOccupationDO,DictVeryeastOccupationRecord> {
    public DictVeryEastOccupationDao() {
        super(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION, DictVeryEastOccupationDO.class);
    }

    public DictVeryEastOccupationDao(TableImpl<DictVeryeastOccupationRecord> table, Class<DictVeryEastOccupationDO> dictVeryEastOccupationDOClass) {
        super(table, dictVeryEastOccupationDOClass);
    }

    public List<DictVeryEastOccupationDO> getAllOccupation(){
        Query query = new Query.QueryBuilder().where(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.STATUS.getName(), 1).buildQuery();
        return getDatas(query);
    }

    public List<DictVeryEastOccupationDO> getSingle(JSONObject obj) {
        Integer level = obj.getInteger("level");
        Integer id = obj.getInteger("code");
        Integer parentId = obj.getInteger("parent_id");
        Query.QueryBuilder build = new Query.QueryBuilder();
        build.where(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.STATUS.getName(), 1);
        if (id != null) {
            build.and(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.CODE.getName(), id);
        }
        if (parentId != null) {
            build.and(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.PARENT_ID.getName(), parentId);
        }
        if (level != null) {
            build.and(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION.LEVEL.getName(), level);
        }
        return getDatas(build.buildQuery());
    }

    public int deleteAll(){
        Condition condition=new Condition("code",0, ValueOp.NEQ);
        return delete(condition);
    }
}
