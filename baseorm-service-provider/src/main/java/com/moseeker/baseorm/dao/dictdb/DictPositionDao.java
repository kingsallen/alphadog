package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictPosition;
import com.moseeker.baseorm.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictPositionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author xxx
* DictPositionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictPositionDao extends JooqCrudImpl<DictPositionDO, DictPositionRecord> {

    public DictPositionDao() {
        super(DictPosition.DICT_POSITION, DictPositionDO.class);
    }

    public DictPositionDao(TableImpl<DictPositionRecord> table, Class<DictPositionDO> dictPositionDOClass) {
        super(table, dictPositionDOClass);
    }

    public List<DictPositionRecord> getIndustriesByParentCode(int parentCode) {
        return create.selectFrom(DictPosition.DICT_POSITION)
                .where(DictPosition.DICT_POSITION.PARENT.equal(parentCode)).fetch();
    }
}
