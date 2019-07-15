package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrPointsConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrPointsConfRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrPointsConfDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrPointsConfDao extends JooqCrudImpl<HrPointsConfDO, HrPointsConfRecord> {

    public HrPointsConfDao() {
        super(HrPointsConf.HR_POINTS_CONF, HrPointsConfDO.class);
    }

    public HrPointsConfDao(TableImpl<HrPointsConfRecord> table, Class<HrPointsConfDO> hrPointsConfDOClass) {
        super(table, hrPointsConfDOClass);
    }

    public HrPointsConfRecord getEmployeeVerified(int companyId) {

        return create.selectFrom(HrPointsConf.HR_POINTS_CONF)
                .where(HrPointsConf.HR_POINTS_CONF.STATUS_NAME.eq(Constant.POINTS_CONF_EMPLOYEE_VERIFIED))
                .and(HrPointsConf.HR_POINTS_CONF.COMPANY_ID.eq(companyId))
                .limit(1)
                .fetchOne();
    }

    public HrPointsConfRecord getRefineAward(int companyId) {
        return create.selectFrom(HrPointsConf.HR_POINTS_CONF)
                .where(HrPointsConf.HR_POINTS_CONF.STATUS_NAME.eq(Constant.POINTS_CONF_EMPLOYEE_VERIFIED))
                .and(HrPointsConf.HR_POINTS_CONF.COMPANY_ID.eq(companyId))
                .limit(1)
                .fetchOne();
    }

}
