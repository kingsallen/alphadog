package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrCompanyConfDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyConf;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf.HR_COMPANY_CONF;
import static org.jooq.impl.DSL.using;

/**
 * @Author: mdc
 * @Date: 2019-07-25 19:27
 */
@Repository
public class HrCompanyConfJOOQDaoImpl extends HrCompanyConfDao {
    public HrCompanyConfJOOQDaoImpl(Configuration configuration) {
        super(configuration);
    }

    @Override
    public HrCompanyConf fetchOneByCompanyId(Integer companyId) {
        HrCompanyConf hrCompanyConf = using(configuration())
                .selectFrom(HR_COMPANY_CONF)
                .where(HR_COMPANY_CONF.COMPANY_ID.eq(companyId))
                .fetchOneInto(HrCompanyConf.class);
        return hrCompanyConf != null ? hrCompanyConf : null;
    }
}
