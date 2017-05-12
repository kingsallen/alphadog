package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.CityPojo;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author xxx
* DictCityDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCityDao extends JooqCrudImpl<DictCityDO, DictCityRecord> {

    public DictCityDao() {
        super(DictCity.DICT_CITY, DictCityDO.class);
    }

    public DictCityDao(TableImpl<DictCityRecord> table, Class<DictCityDO> dictCityDOClass) {
        super(table, dictCityDOClass);
    }

    public List<CityPojo> getCities(int level) {
        Condition cond = null;
        if(level > 0)  { // all
            cond = DictCity.DICT_CITY.LEVEL.equal((byte) level);
        }
        List<CityPojo> cities = create.select().from(table).where(cond).fetchInto(CityPojo.class);
        return cities;
    }

    public List<CityPojo> getCitiesById(int id) {
        int provinceCode = id / 1000 * 1000;
        Condition cond = DictCity.DICT_CITY.CODE.ge((int)(provinceCode)).and(DictCity.DICT_CITY.CODE.lt((int)(provinceCode+1000)));
        // cond = DictCity.DICT_CITY.CODE.between((int)(provinceCode), (int)(provinceCode+1000));
        // TODO: investigate why between not working
        List<CityPojo> cities = create.select().from(table).where(cond).fetchInto(CityPojo.class);
        return cities;
    }
}
