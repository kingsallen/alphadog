package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author xxx
 *         HrCompanyAccountDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class HrCompanyAccountDao extends JooqCrudImpl<HrCompanyAccountDO, HrCompanyAccountRecord> {

    @Autowired
    HrCompanyDao hrCompanyDao;

    public HrCompanyAccountDao() {
        super(HrCompanyAccount.HR_COMPANY_ACCOUNT, HrCompanyAccountDO.class);
    }

    public HrCompanyAccountDao(TableImpl<HrCompanyAccountRecord> table, Class<HrCompanyAccountDO> hrCompanyAccountDOClass) {
        super(table, hrCompanyAccountDOClass);
    }

    public HrCompanyDO getHrCompany(int hrId) {
        Query query = new Query.QueryBuilder().where("account_id", hrId).buildQuery();
        HrCompanyAccountDO hrCompanyAccountDO = getData(query);

        if (hrCompanyAccountDO == null) return null;

        query = new Query.QueryBuilder().where("id", hrCompanyAccountDO.getCompanyId()).buildQuery();

        return hrCompanyDao.getData(query);
    }
}
