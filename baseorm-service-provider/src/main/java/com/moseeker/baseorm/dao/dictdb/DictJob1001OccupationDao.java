package com.moseeker.baseorm.dao.dictdb;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictJob1001Occupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictJob1001OccupationRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJob1001OccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DictJob1001OccupationDao extends JooqCrudImpl<DictJob1001OccupationDO,DictJob1001OccupationRecord> {
    public DictJob1001OccupationDao() {
        super(DictJob1001Occupation.DICT_JOB1001_OCCUPATION, DictJob1001OccupationDO.class);
    }

    public DictJob1001OccupationDao(TableImpl<DictJob1001OccupationRecord> table, Class<DictJob1001OccupationDO> DictJob1001OccupationDOClass) {
        super(table, DictJob1001OccupationDOClass);
    }

    public List<DictJob1001OccupationDO> getAllOccupation(){
        Query query = new Query.QueryBuilder().where(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.STATUS.getName(), 1).buildQuery();
        return getDatas(query);
    }

    public List<DictJob1001OccupationDO> getSingle(JSONObject obj) {
        Integer level = obj.getInteger("level");
        Integer id = obj.getInteger("code");
        Integer parentId = obj.getInteger("parent_id");
        Query.QueryBuilder build = new Query.QueryBuilder();
        build.where(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.STATUS.getName(), 1);
        if (id != null) {
            build.and(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.CODE.getName(), id);
        }
        if (parentId != null) {
            build.and(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.PARENT_ID.getName(), parentId);
        }
        if (level != null) {
            build.and(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.LEVEL.getName(), level);
        }
        return getDatas(build.buildQuery());
    }

    public int deleteAll(){
        Condition condition=new Condition("code",0, ValueOp.NEQ);
        return delete(condition);
    }
}
