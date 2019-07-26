package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrAtsProcessCompanyItemDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrAtsProcessCompanyItem;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.hrdb.tables.HrAtsProcessCompanyItem.HR_ATS_PROCESS_COMPANY_ITEM;
import static org.jooq.impl.DSL.using;

/**
 * @Author: mdc
 * @Date: 2019-07-25 20:01
 */
@Repository
public class HrAtsProcessCompanyItemJOOQDaoImpl extends HrAtsProcessCompanyItemDao {
    public HrAtsProcessCompanyItemJOOQDaoImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public HrAtsProcessCompanyItem fetchOneById(Integer id) {
        HrAtsProcessCompanyItem hrAtsProcessCompanyItem = using(configuration())
                .selectFrom(HR_ATS_PROCESS_COMPANY_ITEM)
                .where(HR_ATS_PROCESS_COMPANY_ITEM.ID.eq(id))
                .fetchOneInto(HrAtsProcessCompanyItem.class);
        return hrAtsProcessCompanyItem != null ? hrAtsProcessCompanyItem : null;
    }
}
