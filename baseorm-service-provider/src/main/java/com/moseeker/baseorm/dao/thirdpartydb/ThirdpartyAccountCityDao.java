package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountCity;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountCityRecord;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ThirdpartyAccountCityDao 实现类 （groovy 生成）
* 2017-09-27
*/
@Repository
public class ThirdpartyAccountCityDao extends JooqCrudImpl<ThirdpartyAccountCityDO, ThirdpartyAccountCityRecord> {

    public ThirdpartyAccountCityDao() {
        super(ThirdpartyAccountCity.THIRDPARTY_ACCOUNT_CITY, ThirdpartyAccountCityDO.class);
    }


    public ThirdpartyAccountCityDao(TableImpl<ThirdpartyAccountCityRecord> table, Class<ThirdpartyAccountCityDO> doClass) {
        super(table, doClass);
    }
}
