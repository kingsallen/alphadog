package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityMapRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DictCityMapDao extends JooqCrudImpl<DictCityMapDO, DictCityMapRecord> {

    @Autowired
    DictCityDao dictCityDao;

    public DictCityMapDao() {
        super(DictCityMap.DICT_CITY_MAP, DictCityMapDO.class);
    }

    public DictCityMapDao(TableImpl<DictCityMapRecord> table, Class<DictCityMapDO> dictCityMapDOClass) {
        super(table, dictCityMapDOClass);
    }

    public List<List<String>> getOtherCityFunllLevel(int...cityCodes){
        //找到所有city


        Query query = new Query.QueryBuilder().where(new Condition("code", Arrays.asList(cityCodes), ValueOp.IN)).buildQuery();

        return new ArrayList<>();
    }
}
