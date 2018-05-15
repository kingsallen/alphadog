package com.moseeker.baseorm.dao.dictdb;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.base.AbstractDictOccupationDao;
import com.moseeker.baseorm.base.DefaultDictOccupationDao;
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
public class DictVeryEastOccupationDao extends DefaultDictOccupationDao<DictVeryEastOccupationDO,DictVeryeastOccupationRecord> {
    public DictVeryEastOccupationDao() {
        super(DictVeryeastOccupation.DICT_VERYEAST_OCCUPATION, DictVeryEastOccupationDO.class);
    }

    public DictVeryEastOccupationDao(TableImpl<DictVeryeastOccupationRecord> table, Class<DictVeryEastOccupationDO> dictVeryEastOccupationDOClass) {
        super(table, dictVeryEastOccupationDOClass);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.VERYEAST;
    }
}
