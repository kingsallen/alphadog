package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.base.DefaultDictOccupationDao;
import com.moseeker.baseorm.db.dictdb.tables.DictJobsdbOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictJobsdbOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictJobsDBOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;


@Repository
public class DictJobsDBOccupationDao extends DefaultDictOccupationDao<DictJobsDBOccupationDO,DictJobsdbOccupationRecord> {

    public DictJobsDBOccupationDao() {
        super(DictJobsdbOccupation.DICT_JOBSDB_OCCUPATION, DictJobsDBOccupationDO.class);
    }

    public DictJobsDBOccupationDao(TableImpl<DictJobsdbOccupationRecord> table, Class<DictJobsDBOccupationDO> dictJobsDBOccupationDOClass) {
        super(table, dictJobsDBOccupationDOClass);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOBSDB;
    }


}
