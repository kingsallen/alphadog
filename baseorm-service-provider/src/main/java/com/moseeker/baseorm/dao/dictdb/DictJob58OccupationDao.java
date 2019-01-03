package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.base.DefaultDictOccupationDao;
import com.moseeker.baseorm.db.dictdb.tables.Dict_58jobOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.Dict_58jobOccupationRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict58jobOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author cjm
 * @date 2018-11-26 16:48
 **/
@Repository
public class DictJob58OccupationDao extends DefaultDictOccupationDao<Dict58jobOccupationDO,Dict_58jobOccupationRecord> {

    public DictJob58OccupationDao() {
        super(Dict_58jobOccupation.DICT_58JOB_OCCUPATION, Dict58jobOccupationDO.class);
    }

    public DictJob58OccupationDao(TableImpl<Dict_58jobOccupationRecord> table, Class<Dict58jobOccupationDO> doClass) {
        super(table, doClass);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB58;
    }


}
