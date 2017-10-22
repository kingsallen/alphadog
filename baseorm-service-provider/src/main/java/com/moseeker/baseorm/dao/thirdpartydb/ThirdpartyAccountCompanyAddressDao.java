package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountCity;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountCompanyAddress;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountCityRecord;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountCompanyAddressRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyAddressDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author xxx
* ThirdpartyAccountCityDao 实现类 （groovy 生成）
* 2017-09-27
*/
@Repository
public class ThirdpartyAccountCompanyAddressDao extends JooqCrudImpl<ThirdpartyAccountCompanyAddressDO, ThirdpartyAccountCompanyAddressRecord> {

    public ThirdpartyAccountCompanyAddressDao() {
        super(ThirdpartyAccountCompanyAddress.THIRDPARTY_ACCOUNT_COMPANY_ADDRESS, ThirdpartyAccountCompanyAddressDO.class);
    }


    public ThirdpartyAccountCompanyAddressDao(TableImpl<ThirdpartyAccountCompanyAddressRecord> table, Class<ThirdpartyAccountCompanyAddressDO> doClass) {
        super(table, doClass);
    }

    public List<ThirdpartyAccountCompanyAddressDO> getAddressByAccountId(int accountId) {
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyAccountCompanyAddress.THIRDPARTY_ACCOUNT_COMPANY_ADDRESS.ACCOUNT_ID.getName(),accountId).buildQuery();

        return getDatas(query);
    }
}
