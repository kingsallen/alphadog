package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountJob1001Subsite;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountJob1001SubsiteRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountJob1001SubsiteDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThirdPartyAccountJob1001SubsiteDao extends JooqCrudImpl<ThirdpartyAccountJob1001SubsiteDO,ThirdpartyAccountJob1001SubsiteRecord> {

    public ThirdPartyAccountJob1001SubsiteDao() {
        super(ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE, ThirdpartyAccountJob1001SubsiteDO.class);
    }

    public ThirdPartyAccountJob1001SubsiteDao(TableImpl<ThirdpartyAccountJob1001SubsiteRecord> table, Class<ThirdpartyAccountJob1001SubsiteDO> doClass) {
        super(table, doClass);
    }

    public List<ThirdpartyAccountJob1001SubsiteDO> getAddressByAccountId(int accountId) {
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyAccountJob1001Subsite.THIRDPARTY_ACCOUNT_JOB1001_SUBSITE.ACCOUNT_ID.getName(),accountId).buildQuery();

        return getDatas(query);
    }
}
