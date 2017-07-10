package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictAlipaycampusJobcategory;
import com.moseeker.baseorm.db.dictdb.tables.records.DictAlipaycampusJobcategoryRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictAlipaycampusJobcategoryDO;
import org.jooq.impl.TableImpl;

public class DictAlipaycampusJobcategoryDao extends JooqCrudImpl<DictAlipaycampusJobcategoryDO, DictAlipaycampusJobcategoryRecord> {

    public DictAlipaycampusJobcategoryDao() {
        super(DictAlipaycampusJobcategory.DICT_ALIPAYCAMPUS_JOBCATEGORY, DictAlipaycampusJobcategoryDO.class);
    }

    public DictAlipaycampusJobcategoryDao(TableImpl<DictAlipaycampusJobcategoryRecord> table, Class<DictAlipaycampusJobcategoryDO> dictAlipaycampusJobcategoryDOClass) {
        super(table, dictAlipaycampusJobcategoryDOClass);
    }


}
