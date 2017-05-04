package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* DictIndustryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictIndustryDao extends JooqCrudImpl<DictIndustryDO, DictIndustryRecord> {


    public DictIndustryDao(TableImpl<DictIndustryRecord> table, Class<DictIndustryDO> dictIndustryDOClass) {
        super(table, dictIndustryDOClass);
    }
}
