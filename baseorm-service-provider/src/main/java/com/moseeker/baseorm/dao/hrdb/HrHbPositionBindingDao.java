package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbPositionBinding;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbPositionBindingRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbPositionBindingDO;
import org.jooq.Record2;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HrHbPositionBindingDao extends JooqCrudImpl<HrHbPositionBindingDO, HrHbPositionBindingRecord> {

    public HrHbPositionBindingDao() {
        super(HrHbPositionBinding.HR_HB_POSITION_BINDING, HrHbPositionBindingDO.class);
    }

    public HrHbPositionBindingDao(TableImpl<HrHbPositionBindingRecord> table, Class<HrHbPositionBindingDO> hrHbPositionBindingDOClass) {
        super(table, hrHbPositionBindingDOClass);
    }

    public List<Record2<Integer, String>> fetchPositionTitle(List<Integer> positionBindingIdList) {

        return create.select(HrHbPositionBinding.HR_HB_POSITION_BINDING.ID, JobPosition.JOB_POSITION.TITLE)
                .from(HrHbPositionBinding.HR_HB_POSITION_BINDING)
                .innerJoin(JobPosition.JOB_POSITION)
                .on(HrHbPositionBinding.HR_HB_POSITION_BINDING.POSITION_ID.eq(JobPosition.JOB_POSITION.ID))
                .where(HrHbPositionBinding.HR_HB_POSITION_BINDING.ID.in(positionBindingIdList))
                .fetch();
    }
}
