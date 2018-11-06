package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbPositionBinding;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbPositionBindingRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbPositionBindingDO;
import org.jooq.InsertSetMoreStep;
import org.jooq.InsertSetStep;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public List<Record2<Integer, String>> fetchPositionTitle(List<Integer> positionBindingIdList) {

        return create.select(HrHbPositionBinding.HR_HB_POSITION_BINDING.ID, JobPosition.JOB_POSITION.TITLE)
                .from(HrHbPositionBinding.HR_HB_POSITION_BINDING)
                .innerJoin(JobPosition.JOB_POSITION)
                .on(HrHbPositionBinding.HR_HB_POSITION_BINDING.POSITION_ID.eq(JobPosition.JOB_POSITION.ID))
                .where(HrHbPositionBinding.HR_HB_POSITION_BINDING.ID.in(positionBindingIdList))
                .fetch();
    }

    public int countByActivityId(Integer activityId) {
        return create.selectCount()
                .from(HrHbPositionBinding.HR_HB_POSITION_BINDING)
                .where(HrHbPositionBinding.HR_HB_POSITION_BINDING.HB_CONFIG_ID.eq(activityId))
                .fetchOne().value1();
    }

    public Result<HrHbPositionBindingRecord> fetchByActivity(Integer activityId) {

        return create.selectFrom(HrHbPositionBinding.HR_HB_POSITION_BINDING)
                .where(HrHbPositionBinding.HR_HB_POSITION_BINDING.HB_CONFIG_ID.eq(activityId))
                .fetch();
    }

    public void deleteByActivityId(Integer activityId) {
        create.deleteFrom(HrHbPositionBinding.HR_HB_POSITION_BINDING)
                .where(HrHbPositionBinding.HR_HB_POSITION_BINDING.HB_CONFIG_ID.eq(activityId))
                .execute();
    }

    public List<HrHbPositionBindingRecord> insert(List<HrHbPositionBindingRecord> bindings) {
        if (bindings != null && bindings.size() > 0) {
            InsertSetStep<HrHbPositionBindingRecord> insertSetStep =
                    create.insertInto(HrHbPositionBinding.HR_HB_POSITION_BINDING);
            InsertSetMoreStep<HrHbPositionBindingRecord> insertSetMoreStep = null;
            for (HrHbPositionBindingRecord record : bindings) {
                if (insertSetMoreStep == null) {
                    insertSetMoreStep = insertSetStep.set(record);
                } else {
                    insertSetMoreStep = insertSetMoreStep.newRecord().set(record);
                }

            }
            return insertSetMoreStep.returning().fetch();
        } else {
            return new ArrayList<>();
        }
    }
}
