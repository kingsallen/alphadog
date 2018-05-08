package com.moseeker.baseorm.dao.dictdb;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.base.DefaultDictOccupationDao;
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
public class DictJob1001OccupationDao extends DefaultDictOccupationDao<DictJob1001OccupationDO,DictJob1001OccupationRecord> {
    public DictJob1001OccupationDao() {
        super(DictJob1001Occupation.DICT_JOB1001_OCCUPATION, DictJob1001OccupationDO.class);
    }

    public DictJob1001OccupationDao(TableImpl<DictJob1001OccupationRecord> table, Class<DictJob1001OccupationDO> DictJob1001OccupationDOClass) {
        super(table, DictJob1001OccupationDOClass);
    }


    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB1001;
    }


    public int deleteAllBySubsite(String subsite){
        return delete(new Condition(DictJob1001Occupation.DICT_JOB1001_OCCUPATION.SUBSITE.getName(),subsite));
    }
}
