package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.dictdb.CityPojo;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<DictCityRecord> getCitiesByCodes(List<Integer> cityCodes) {

        List<DictCityRecord> records = new ArrayList<>();
        Connection conn = null;
        try {
            if(cityCodes != null && cityCodes.size() > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                SelectWhereStep<DictCityRecord> select = create.selectFrom(DictCity.DICT_CITY);
                SelectConditionStep<DictCityRecord> selectCondition = null;
                for(int i=0; i<cityCodes.size(); i++) {
                    if(i == 0) {
                        selectCondition = select.where(DictCity.DICT_CITY.CODE.equal((int)(cityCodes.get(i))));
                    } else {
                        selectCondition.or(DictCity.DICT_CITY.CODE.equal((int)(cityCodes.get(i))));
                    }
                }
                records = selectCondition.fetch();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                //do nothing
            }
        }

        return records;
    }

    public DictCityRecord getCityByCode(int city_code) {
        DictCityRecord record = null;
        Connection conn = null;
        try {
            if(city_code > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                Result<DictCityRecord> result = create.selectFrom(DictCity.DICT_CITY)
                        .where(DictCity.DICT_CITY.CODE.equal((int)(city_code)))
                        .limit(1).fetch();
                if(result != null && result.size() > 0) {
                    record = result.get(0);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                //do nothing
            }
        }
        return record;
    }
}
