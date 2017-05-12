package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictIndustryType;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryTypeDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author xxx
* DictIndustryTypeDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictIndustryTypeDao extends JooqCrudImpl<DictIndustryTypeDO, DictIndustryTypeRecord> {

    public DictIndustryTypeDao() {
        super(DictIndustryType.DICT_INDUSTRY_TYPE, DictIndustryTypeDO.class);
    }

    public DictIndustryTypeDao(TableImpl<DictIndustryTypeRecord> table, Class<DictIndustryTypeDO> dictIndustryTypeDOClass) {
        super(table, dictIndustryTypeDOClass);
    }

    public List<DictIndustryTypeRecord> getAll() {
        return create.selectFrom(DictIndustryType.DICT_INDUSTRY_TYPE).fetch();
    }
}
