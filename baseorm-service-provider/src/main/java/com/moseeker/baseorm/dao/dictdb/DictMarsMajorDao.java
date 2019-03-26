package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictIndustryType;
import com.moseeker.baseorm.db.dictdb.tables.DictMarsMajor;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.baseorm.db.dictdb.tables.records.DictMarsMajorRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryTypeDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictMarsMajorDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author xxx
* DictIndustryTypeDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictMarsMajorDao extends JooqCrudImpl<DictMarsMajorDO, DictMarsMajorRecord> {

    public DictMarsMajorDao() {
        super(DictMarsMajor.DICT_MARS_MAJOR, DictMarsMajorDO.class);
    }

    public DictMarsMajorDao(TableImpl<DictMarsMajorRecord> table, Class<DictMarsMajorDO> dictMarsMajorDOClass) {
        super(table, dictMarsMajorDOClass);
    }

    public List<DictMarsMajorRecord> getAll() {
        return create.selectFrom(DictMarsMajor.DICT_MARS_MAJOR).fetch();
    }

    public List<DictMarsMajorRecord> getIndustriesByType(Integer type) {
        return create.selectFrom(DictMarsMajor.DICT_MARS_MAJOR)
                .where(DictMarsMajor.DICT_MARS_MAJOR.PARENT_ID.equal(type)).fetch();
    }

}
