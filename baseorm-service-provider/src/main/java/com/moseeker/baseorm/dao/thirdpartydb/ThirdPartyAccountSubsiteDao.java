package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountSubsite;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountSubsiteRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountSubsiteDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThirdPartyAccountSubsiteDao extends JooqCrudImpl<ThirdpartyAccountSubsiteDO,ThirdpartyAccountSubsiteRecord> {

    public ThirdPartyAccountSubsiteDao() {
        super(ThirdpartyAccountSubsite.THIRDPARTY_ACCOUNT_SUBSITE, ThirdpartyAccountSubsiteDO.class);
    }

    public ThirdPartyAccountSubsiteDao(TableImpl<ThirdpartyAccountSubsiteRecord> table, Class<ThirdpartyAccountSubsiteDO> doClass) {
        super(table, doClass);
    }

    public List<ThirdpartyAccountSubsiteDO> getAddressByAccountId(int accountId) {
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyAccountSubsite.THIRDPARTY_ACCOUNT_SUBSITE.ACCOUNT_ID.getName(),accountId).buildQuery();

        return getDatas(query);
    }
}
