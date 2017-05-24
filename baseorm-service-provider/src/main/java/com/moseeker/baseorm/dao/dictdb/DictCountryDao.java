package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCountry;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryPojo;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* DictCountryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCountryDao extends JooqCrudImpl<DictCountryDO, DictCountryRecord> {

    public DictCountryDao() {
        super(DictCountry.DICT_COUNTRY, DictCountryDO.class);
    }

    public DictCountryDao(TableImpl<DictCountryRecord> table, Class<DictCountryDO> dictCountryDOClass) {
        super(table, dictCountryDOClass);
    }

    /**
     * 获取国家字典
     *
     * @return
     * @throws Exception
     */
    public List<DictCountryPojo> getDictCountry() throws Exception{

        Condition condition = DictCountry.DICT_COUNTRY.CONTINENT_CODE.gt((int)(0));

        return create.select().from(DictCountry.DICT_COUNTRY).
                where(condition).fetchInto(DictCountryPojo.class);
    }

    public List<DictCountryRecord> getCountresByIDs(List<Integer> ids) {

        List<DictCountryRecord> records = new ArrayList<>();
        if(ids != null && ids.size() > 0) {
            SelectWhereStep<DictCountryRecord> select = create.selectFrom(DictCountry.DICT_COUNTRY);
            SelectConditionStep<DictCountryRecord> selectCondition = null;
            for(int i=0; i<ids.size(); i++) {
                if(i == 0) {
                    selectCondition = select.where(DictCountry.DICT_COUNTRY.ID.equal((int)(ids.get(i))));
                } else {
                    selectCondition.or(DictCountry.DICT_COUNTRY.ID.equal((int)(ids.get(i))));
                }
            }
            records = selectCondition.fetch();
        }
        return records;
    }

    public DictCountryRecord getCountryByID(int nationality_code) {
        DictCountryRecord record = null;
        if(nationality_code > 0) {
            Result<DictCountryRecord> result = create.selectFrom(DictCountry.DICT_COUNTRY)
                    .where(DictCountry.DICT_COUNTRY.ID.equal((int)(nationality_code))).limit(1).fetch();
            if(result != null && result.size() > 0) {
                record = result.get(0);
            }
        }
        return record;
    }
}
