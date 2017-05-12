package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCountry;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryPojo;
import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
}
