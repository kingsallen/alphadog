package com.moseeker.baseorm.dao.dictdb;


import com.moseeker.baseorm.base.DefaultDictOccupationDao;
import com.moseeker.baseorm.db.dictdb.tables.DictVeryeastOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictVeryeastOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictVeryEastOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
