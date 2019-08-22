package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrAtsPhaseBaseDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsPhaseBase;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.hrdb.tables.HrAtsPhaseBase.HR_ATS_PHASE_BASE;
import static org.jooq.impl.DSL.using;

/**
 * @Author: mdc
 * @Date: 2019-07-25 19:59
 */
@Repository
public class HrAtsPhaseBaseJOOQDaoImpl extends HrAtsPhaseBaseDao {
    public HrAtsPhaseBaseJOOQDaoImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public HrAtsPhaseBase fetchOneById(Integer id) {
        HrAtsPhaseBase hrAtsPhaseBase = using(configuration())
                .selectFrom(HR_ATS_PHASE_BASE)
                .where(HR_ATS_PHASE_BASE.ID.eq(id))
                .fetchOneInto(HrAtsPhaseBase.class);
        return hrAtsPhaseBase != null ? hrAtsPhaseBase : null;
    }
}
