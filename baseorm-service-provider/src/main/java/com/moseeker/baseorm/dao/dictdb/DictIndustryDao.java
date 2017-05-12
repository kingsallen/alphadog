package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictIndustry;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author xxx
* DictIndustryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictIndustryDao extends JooqCrudImpl<DictIndustryDO, DictIndustryRecord> {

    public DictIndustryDao() {
        super(DictIndustry.DICT_INDUSTRY, DictIndustryDO.class);
    }

    public DictIndustryDao(TableImpl<DictIndustryRecord> table, Class<DictIndustryDO> dictIndustryDOClass) {
        super(table, dictIndustryDOClass);
    }

    public List<DictIndustryRecord> getIndustriesByType(int type) {
        return create.selectFrom(DictIndustry.DICT_INDUSTRY)
                .where(DictIndustry.DICT_INDUSTRY.TYPE.equal((int)(type))).fetch();
    }
}
