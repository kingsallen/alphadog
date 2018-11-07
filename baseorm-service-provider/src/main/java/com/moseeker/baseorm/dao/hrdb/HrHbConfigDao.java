package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.constant.ActivityStatus;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbConfig;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbConfigDO;
import org.jooq.Field;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateSetMoreStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HrHbConfigDao extends JooqCrudImpl<HrHbConfigDO, HrHbConfigRecord> {

    public HrHbConfigDao() {
        super(HrHbConfig.HR_HB_CONFIG, HrHbConfigDO.class);
    }

    public HrHbConfigDao(TableImpl<HrHbConfigRecord> table, Class<HrHbConfigDO> hrHbConfigDOClass) {
        super(table, hrHbConfigDOClass);
    }

    public List<HrHbConfigRecord> fetchByIdList(List<Integer> configIdList) {
        return create
                .selectFrom(HrHbConfig.HR_HB_CONFIG)
                .where(HrHbConfig.HR_HB_CONFIG.ID.in(configIdList))
                .fetch();
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbConfig> getHrHbConfigByCompanyId(int companyId,int status,List<Integer> typeList){
        List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbConfig> list=create.selectFrom(HrHbConfig.HR_HB_CONFIG)
                .where(HrHbConfig.HR_HB_CONFIG.COMPANY_ID.eq(companyId))
                .and(HrHbConfig.HR_HB_CONFIG.STATUS.eq((byte)status))
                .and(HrHbConfig.HR_HB_CONFIG.TYPE.in(typeList))
                .fetchInto(com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbConfig.class);
        return list;
    }

    public boolean update(HrHbConfigRecord hrHbConfig) {
        UpdateSetFirstStep<HrHbConfigRecord> updateSetFirstStep = create.update(HrHbConfig.HR_HB_CONFIG);
        UpdateSetMoreStep moreStep = null;
        for (Field field : hrHbConfig.fields()) {
            if (field.getName().equals(HrHbConfig.HR_HB_CONFIG.ID.getName())) {
                continue;
            }
            if (field.getValue(hrHbConfig) != null) {
                if (moreStep == null) {
                    moreStep = updateSetFirstStep.set(field, field.getValue(hrHbConfig));
                } else {
                    moreStep = moreStep.set(field, field.getValue(hrHbConfig));
                }
            }
        }
        return moreStep.where(HrHbConfig.HR_HB_CONFIG.ID.eq(hrHbConfig.getId()))
                .and(HrHbConfig.HR_HB_CONFIG.STATUS.notEqual(ActivityStatus.Running.getValue()))
                .execute() == 1;
    }

    public boolean updateStatus(Integer id, byte value) {
        return create.update(HrHbConfig.HR_HB_CONFIG)
                .set(HrHbConfig.HR_HB_CONFIG.STATUS, value)
                .where(HrHbConfig.HR_HB_CONFIG.ID.eq(id))
                .execute() == 1;
    }

    public boolean updateCheckValue(Integer id, byte value) {
        return create.update(HrHbConfig.HR_HB_CONFIG)
                .set(HrHbConfig.HR_HB_CONFIG.CHECKED, value)
                .where(HrHbConfig.HR_HB_CONFIG.ID.eq(id))
                .execute() == 1;
    }

    public HrHbConfigRecord fetchById(Integer id) {
        return create
                .selectFrom(HrHbConfig.HR_HB_CONFIG)
                .where(HrHbConfig.HR_HB_CONFIG.ID.eq(id))
                .fetchOne();
    }

    public List<HrHbConfigRecord> fetchActiveByCompanyIdExceptId(Integer companyId, Integer id, byte type) {
        return create
                .selectFrom(HrHbConfig.HR_HB_CONFIG)
                .where(HrHbConfig.HR_HB_CONFIG.COMPANY_ID.eq(companyId))
                .and(HrHbConfig.HR_HB_CONFIG.ID.ne(id))
                .and(HrHbConfig.HR_HB_CONFIG.TYPE.eq(type))
                .and(HrHbConfig.HR_HB_CONFIG.STATUS.notIn(new ArrayList<Byte>(){{add((byte)-1);add((byte)5);}}))
                .fetch();

    }
}
