package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountCity;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountCityRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author xxx
* ThirdpartyAccountCityDao 实现类 （groovy 生成）
* 2017-09-27
*/
@Repository
public class ThirdpartyAccountCityDao extends JooqCrudImpl<ThirdpartyAccountCityDO, ThirdpartyAccountCityRecord> {

    @Autowired
    HRThirdPartyAccountHrDao hrThirdPartyAccountHrDao;

    public ThirdpartyAccountCityDao() {
        super(ThirdpartyAccountCity.THIRDPARTY_ACCOUNT_CITY, ThirdpartyAccountCityDO.class);
    }


    public ThirdpartyAccountCityDao(TableImpl<ThirdpartyAccountCityRecord> table, Class<ThirdpartyAccountCityDO> doClass) {
        super(table, doClass);
    }

    public List<ThirdpartyAccountCityDO> getCityByAccountId(int accountId){
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyAccountCity.THIRDPARTY_ACCOUNT_CITY.ACCOUNT_ID.getName(), accountId).buildQuery();
        return getDatas(query);
    }
}
