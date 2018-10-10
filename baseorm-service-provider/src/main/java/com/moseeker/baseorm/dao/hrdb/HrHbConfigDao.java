package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrHbConfig;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrHbConfigDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
}
