package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.base.DefaultDictOccupationDao;
import com.moseeker.baseorm.db.dictdb.tables.DictCarnocOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCarnocOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCarnocOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DictCarnocOccupationDao extends DefaultDictOccupationDao<DictCarnocOccupationDO,DictCarnocOccupationRecord> {

    public DictCarnocOccupationDao() {
        super(DictCarnocOccupation.DICT_CARNOC_OCCUPATION, DictCarnocOccupationDO.class);
    }

    public DictCarnocOccupationDao(TableImpl<DictCarnocOccupationRecord> table, Class<DictCarnocOccupationDO> dictJobsDBOccupationDOClass) {
        super(table, dictJobsDBOccupationDOClass);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.CARNOC;
    }

}
