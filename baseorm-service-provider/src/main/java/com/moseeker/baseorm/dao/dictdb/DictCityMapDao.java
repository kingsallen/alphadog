package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityMapRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DictCityMapDao extends JooqCrudImpl<DictCityMapDO, DictCityMapRecord> {

    public DictCityMapDao() {
        super(DictCityMap.DICT_CITY_MAP, DictCityMapDO.class);
    }

    public DictCityMapDao(TableImpl<DictCityMapRecord> table, Class<DictCityMapDO> dictCityMapDOClass) {
        super(table, dictCityMapDOClass);
    }
}
