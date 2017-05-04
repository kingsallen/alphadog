package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.records.DictZhilianOccupationRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* DictZhilianOccupationDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictZhilianOccupationDao extends JooqCrudImpl<DictZhilianOccupationDO, DictZhilianOccupationRecord> {


    public DictZhilianOccupationDao(TableImpl<DictZhilianOccupationRecord> table, Class<DictZhilianOccupationDO> dictZhilianOccupationDOClass) {
        super(table, dictZhilianOccupationDOClass);
    }
}
