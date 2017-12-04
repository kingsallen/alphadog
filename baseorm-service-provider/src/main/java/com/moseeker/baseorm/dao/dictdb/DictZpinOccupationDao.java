package com.moseeker.baseorm.dao.dictdb;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictZhilianOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictZhilianOccupationRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DictZpinOccupationDao extends JooqCrudImpl<DictZhilianOccupationDO, DictZhilianOccupationRecord> {

    public DictZpinOccupationDao() {
        super(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION, DictZhilianOccupationDO.class);
    }

    public DictZpinOccupationDao(TableImpl<DictZhilianOccupationRecord> table, Class<DictZhilianOccupationDO> dictZhilianOccupationDOClass) {
        super(table, dictZhilianOccupationDOClass);
    }

    public List<DictZhilianOccupationDO> getAllOccupation(){
        Query query = new Query.QueryBuilder().where(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.STATUS.getName(), 1).buildQuery();
        return getDatas(query);
    }

    public List getSingle(JSONObject obj) {
        Integer level = obj.getInteger("level");
        Integer id = obj.getInteger("code");
        Integer parentId = obj.getInteger("parent_id");
        Query.QueryBuilder build = new Query.QueryBuilder();
        build.where(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.STATUS.getName(), 1);
        if (id != null) {
            build.and(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.CODE.getName(), id);
        }
        if (parentId != null) {
            build.and(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.PARENT_ID.getName(), parentId);
        }
        if (level != null) {
            build.and(DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION.LEVEL.getName(), level);
        }
        return getDatas(build.buildQuery());
    }

}
