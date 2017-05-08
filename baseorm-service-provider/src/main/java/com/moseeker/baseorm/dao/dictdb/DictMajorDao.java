package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictMajor;
import com.moseeker.baseorm.db.dictdb.tables.records.DictMajorRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictMajorDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* DictMajorDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictMajorDao extends JooqCrudImpl<DictMajorDO, DictMajorRecord> {

    public DictMajorDao() {
        super(DictMajor.DICT_MAJOR, DictMajorDO.class);
    }

    public DictMajorDao(TableImpl<DictMajorRecord> table, Class<DictMajorDO> dictMajorDOClass) {
        super(table, dictMajorDOClass);
    }
}
