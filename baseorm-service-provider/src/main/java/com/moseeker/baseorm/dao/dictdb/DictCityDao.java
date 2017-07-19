package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.CityPojo;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.jooq.Condition;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author xxx
 *         DictCityDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class DictCityDao extends JooqCrudImpl<DictCityDO, DictCityRecord> {

    public DictCityDao() {
        super(DictCity.DICT_CITY, DictCityDO.class);
    }

    public DictCityDao(TableImpl<DictCityRecord> table, Class<DictCityDO> dictCityDOClass) {
        super(table, dictCityDOClass);
    }

    public List<DictCityDO> getCitys(Collection<Integer> cityCodes) {
        if (cityCodes == null || cityCodes.size() == 0) {
            return new ArrayList<>();
        }
        Query query = new Query.QueryBuilder().where(new com.moseeker.common.util.query.Condition(DictCity.DICT_CITY.CODE.getName(), cityCodes, ValueOp.IN)).buildQuery();
        return getDatas(query);
    }

    public List<CityPojo> getCities(int level) {
        Condition cond = null;
        if (level > 0) { // all
            cond = DictCity.DICT_CITY.LEVEL.equal((byte) level);
        }
        List<CityPojo> cities = create.select().from(table).where(cond).fetchInto(CityPojo.class);
        return cities;
    }

    public List<CityPojo> getCitiesById(int id) {
        int provinceCode = id / 1000 * 1000;
        Condition cond = DictCity.DICT_CITY.CODE.ge((int) (provinceCode)).and(DictCity.DICT_CITY.CODE.lt((int) (provinceCode + 1000)));
        // cond = DictCity.DICT_CITY.CODE.between((int)(provinceCode), (int)(provinceCode+1000));
        // TODO: investigate why between not working
        List<CityPojo> cities = create.select().from(table).where(cond).fetchInto(CityPojo.class);
        return cities;
    }

    public List<DictCityRecord> getCitiesByCodes(List<Integer> cityCodes) {

        List<DictCityRecord> records = new ArrayList<>();
        SelectWhereStep<DictCityRecord> select = create.selectFrom(DictCity.DICT_CITY);
        SelectConditionStep<DictCityRecord> selectCondition = null;
        for (int i = 0; i < cityCodes.size(); i++) {
            if (i == 0) {
                selectCondition = select.where(DictCity.DICT_CITY.CODE.equal((int) (cityCodes.get(i))));
            } else {
                selectCondition.or(DictCity.DICT_CITY.CODE.equal((int) (cityCodes.get(i))));
            }
        }
        if (selectCondition != null) {
            records = selectCondition.fetch();
        }
        return records;
    }

    public DictCityRecord getCityByCode(int city_code) {
        DictCityRecord record = null;
        Result<DictCityRecord> result = create.selectFrom(DictCity.DICT_CITY)
                .where(DictCity.DICT_CITY.CODE.equal((int) (city_code)))
                .limit(1).fetch();
        if (result != null && result.size() > 0) {
            record = result.get(0);
        }
        return record;
    }


    /**
     * 获取完整的城市级别
     * 例:徐家汇 -> 上海，徐家汇
     *
     * @param city
     * @return
     */
    public List<DictCityDO> getMoseekerLevels(DictCityDO city) {

        if (city == null || city.getCode() == 0) {
            return new ArrayList<>();
        }

        int divide = 10;

        Set<Integer> allCodes = new HashSet<>();
        allCodes.add(city.getCode());
        while (city.getCode() / divide > 0) {
            allCodes.add((city.getCode() / divide) * divide);
            divide *= 10;
        }

        if (allCodes.size() == 0) {
            List<DictCityDO> fullLevels = new ArrayList<>();
            fullLevels.add(city);
            return fullLevels;
        }
        Query query = new Query.QueryBuilder()
                .where(new com.moseeker.common.util.query.Condition(DictCity.DICT_CITY.CODE.getName(), allCodes, ValueOp.IN))
                .orderBy(DictCity.DICT_CITY.CODE.getName(), Order.ASC)
                .buildQuery();

        List<DictCityDO> allCities = getDatas(query);

        Iterator<DictCityDO> cityDOIterator = allCities.iterator();
        allCodes.clear();
        Set<Byte> uniqLevels = new HashSet<>();
        while (cityDOIterator.hasNext()) {
            DictCityDO cityD = cityDOIterator.next();
            if(cityD.getLevel() == 0){
                cityDOIterator.remove();
            }else if (uniqLevels.contains(cityD.getLevel())) {
                cityDOIterator.remove();
            } else {
                uniqLevels.add(cityD.getLevel());
            }
        }

        return allCities;
    }

    public List<List<DictCityDO>> getFullCity(List<DictCityDO> citys) {
        List<List<DictCityDO>> fullCitys = new ArrayList<>();
        for (DictCityDO city : citys) {
            fullCitys.add(getMoseekerLevels(city));
        }

        return fullCitys;
    }
}
