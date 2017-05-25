package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictIndustry;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryDO;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<DictIndustryRecord> getIndustriesByCodes(List<Integer> industryCodes) {
        List<DictIndustryRecord> records = new ArrayList<>();
        SelectWhereStep<DictIndustryRecord> select = create.selectFrom(DictIndustry.DICT_INDUSTRY);
        SelectConditionStep<DictIndustryRecord> selectCondition = null;
        if(industryCodes != null && industryCodes.size() > 0) {
            for (int i = 0; i < industryCodes.size(); i++) {
                if (i == 0) {
                    selectCondition = select.where(DictIndustry.DICT_INDUSTRY.CODE.equal((int) (industryCodes.get(i))));
                } else {
                    selectCondition.or(DictIndustry.DICT_INDUSTRY.CODE.equal((int) (industryCodes.get(i))));
                }
            }
        }
        records = selectCondition.fetch();
        return records;
    }

    public DictIndustryRecord getIndustryByCode(int intValue) {
        DictIndustryRecord record = null;
        Result<DictIndustryRecord> result= create.selectFrom(DictIndustry.DICT_INDUSTRY).where(DictIndustry.DICT_INDUSTRY.CODE.equal((int)(intValue))).limit(1).fetch();
        if(result != null && result.size() > 0) {
            record = result.get(0);
        }
        return record;
    }
}
