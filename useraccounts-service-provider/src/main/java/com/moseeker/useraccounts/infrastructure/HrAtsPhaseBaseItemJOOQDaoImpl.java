package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrAtsPhaseBaseItemDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsPhaseBaseItem;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.hrdb.tables.HrAtsPhaseBaseItem.HR_ATS_PHASE_BASE_ITEM;
import static org.jooq.impl.DSL.using;

/**
 * @Author: mdc
 * @Date: 2019-07-25 19:59
 */
@Repository
public class HrAtsPhaseBaseItemJOOQDaoImpl extends HrAtsPhaseBaseItemDao {
    public HrAtsPhaseBaseItemJOOQDaoImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public HrAtsPhaseBaseItem fetchOneById(Integer id) {
        HrAtsPhaseBaseItem hrAtsPhaseBaseItem = using(configuration())
                .selectFrom(HR_ATS_PHASE_BASE_ITEM)
                .where(HR_ATS_PHASE_BASE_ITEM.ID.eq(id))
                .fetchOneInto(HrAtsPhaseBaseItem.class);
        return hrAtsPhaseBaseItem != null ? hrAtsPhaseBaseItem : null;
    }
}
