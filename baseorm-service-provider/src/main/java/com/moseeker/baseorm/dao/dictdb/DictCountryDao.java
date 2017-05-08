package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCountry;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* DictCountryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCountryDao extends JooqCrudImpl<DictCountryDO, DictCountryRecord> {

    public DictCountryDao() {
        super(DictCountry.DICT_COUNTRY, DictCountryDO.class);
    }

    public DictCountryDao(TableImpl<DictCountryRecord> table, Class<DictCountryDO> dictCountryDOClass) {
        super(table, dictCountryDOClass);
    }
}
