package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.base.DefaultDictOccupationDao;
import com.moseeker.baseorm.db.dictdb.tables.Dict_51jobOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_51jobOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class Dict51OccupationDao extends DefaultDictOccupationDao<Dict51jobOccupationDO, Dict_51jobOccupationRecord> {

    public Dict51OccupationDao() {
        super(Dict_51jobOccupation.DICT_51JOB_OCCUPATION, Dict51jobOccupationDO.class);
    }

    public Dict51OccupationDao(TableImpl<Dict_51jobOccupationRecord> table, Class<Dict51jobOccupationDO> dict51jobOccupationDOClass) {
        super(table, dict51jobOccupationDOClass);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB51;
    }
}
