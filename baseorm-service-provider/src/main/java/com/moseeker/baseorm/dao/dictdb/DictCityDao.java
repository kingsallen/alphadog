package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* DictCityDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCityDao extends JooqCrudImpl<DictCityDO, DictCityRecord> {


    public DictCityDao(TableImpl<DictCityRecord> table, Class<DictCityDO> dictCityDOClass) {
        super(table, dictCityDOClass);
    }
}
