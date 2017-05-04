package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* DictConstantDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictConstantDao extends JooqCrudImpl<DictConstantDO, DictConstantRecord> {


    public DictConstantDao(TableImpl<DictConstantRecord> table, Class<DictConstantDO> dictConstantDOClass) {
        super(table, dictConstantDOClass);
    }
}
