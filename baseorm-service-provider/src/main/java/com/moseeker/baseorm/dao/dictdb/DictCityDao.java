package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.CityPojo;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.jooq.Condition;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
     * @param cityDO
     * @return
     */
    public List<DictCityDO> getMoseekerCityLevel(DictCityDO cityDO) {
        List<DictCityDO> cityLevels = new ArrayList<>();
        if (cityDO == null) {
            return cityLevels;
        }
        //如果给的城市的level为0，那么取它的上一级做为最后一级
        if (cityDO.getLevel() == 0) {
            DictCityDO upperLevel = getUpperLevel(cityDO.getCode());
            return getMoseekerCityLevel(upperLevel);
        } else if (cityDO.getLevel() == 1) {
            //如果级别为1，那么直接返回
            cityLevels.add(cityDO);
        } else {
            cityLevels.add(0, cityDO);
            DictCityDO upperLevel = null;
            while ((upperLevel = getUpperLevel(cityDO.getCode())) != null) {
                cityLevels.add(0, upperLevel);
                if (upperLevel.getLevel() == 1) {
                    break;
                }
            }
        }

        return cityLevels;
    }


    /**
     * 获取更高的级别
     * 例子:徐家汇->上海
     *
     * @return
     */
    public DictCityDO getUpperLevel(int code) {

        if (code <= 0) {
            return null;
        }

        int newLevelCode;

        int divide = 10;

        while ((newLevelCode = (code / divide) * divide) == code) {
            divide *= 10;
        }

        Query query = new Query.QueryBuilder().where("code", newLevelCode).buildQuery();

        DictCityDO upperLevel = getData(query);

        //找不到父级或者父级的level=0那么继续向上找
        if (upperLevel == null || upperLevel.getLevel() == 0) {
            return getUpperLevel(newLevelCode);
        }

        return upperLevel;
    }

    public List<List<DictCityDO>> getFullCity(List<DictCityDO> citys) {
        List<List<DictCityDO>> fullCitys = new ArrayList<>();
        for (DictCityDO city : citys) {
            fullCitys.add(getMoseekerCityLevel(city));
        }

        return fullCitys;
    }
}
