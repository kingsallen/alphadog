package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbPositionBinding;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbPositionBindingRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbPositionBindingDO;
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

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbPositionBinding> getHrHbPositionBindingListByPidListAndHbConfigList(List<Integer> pidList,List<Integer> hrHbIdList){
        List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbPositionBinding> list=create.selectFrom(HrHbPositionBinding.HR_HB_POSITION_BINDING)
                .where(HrHbPositionBinding.HR_HB_POSITION_BINDING.POSITION_ID.in(pidList))
                .and(HrHbPositionBinding.HR_HB_POSITION_BINDING.HB_CONFIG_ID.in(hrHbIdList))
                .fetchInto(com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbPositionBinding.class);
        return list;
    }
}
