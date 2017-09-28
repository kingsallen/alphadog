package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountCity;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountCompany;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountCityRecord;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountCompanyRecord;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCityDO;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountCompanyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ThirdpartyAccountCityDao 实现类 （groovy 生成）
* 2017-09-27
*/
@Repository
public class ThirdpartyAccountCompanyDao extends JooqCrudImpl<ThirdpartyAccountCompanyDO, ThirdpartyAccountCompanyRecord> {

    public ThirdpartyAccountCompanyDao() {
        super(ThirdpartyAccountCompany.THIRDPARTY_ACCOUNT_COMPANY, ThirdpartyAccountCompanyDO.class);
    }


    public ThirdpartyAccountCompanyDao(TableImpl<ThirdpartyAccountCompanyRecord> table, Class<ThirdpartyAccountCompanyDO> doClass) {
        super(table, doClass);
    }
}
