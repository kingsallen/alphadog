package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCollege;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* DictCollegeDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCollegeDao extends JooqCrudImpl<DictCollegeDO, DictCollegeRecord> {

    public DictCollegeDao() {
        super(DictCollege.DICT_COLLEGE, DictCollegeDO.class);
    }

    public DictCollegeDao(TableImpl<DictCollegeRecord> table, Class<DictCollegeDO> dictCollegeDOClass) {
        super(table, dictCollegeDOClass);
    }
}
