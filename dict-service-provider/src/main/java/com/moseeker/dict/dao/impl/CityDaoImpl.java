package com.moseeker.dict.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.dict.dao.CityDao;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.dictdb.tables.DictCity;
import com.moseeker.dict.pojo.CityPojo;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import org.jooq.*;
import org.jooq.Condition;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.*;

@Repository
public class CityDaoImpl extends BaseDaoImpl<DictCityRecord, DictCity> implements CityDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictCity.DICT_CITY;
    }

    public List<CityPojo> getCities(int level) {
        initJOOQEntity();
        List<CityPojo> cities = null;
        try(Connection conn = DBConnHelper.DBConn.getConn()) {
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn) ;
            Condition cond = null;
            if(level > 0)  { // all
                cond = DictCity.DICT_CITY.LEVEL.equal((byte) level);
            }
            cities = create.select().from(tableLike).where(cond).fetchInto(CityPojo.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return cities;
    }

    public List<CityPojo> getCitiesById(int id) {
        initJOOQEntity();
        List<CityPojo> cities = null;
        try(Connection conn = DBConnHelper.DBConn.getConn()) {
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn) ;
            int provinceCode = id / 1000 * 1000;
            Condition cond = DictCity.DICT_CITY.CODE.ge(UInteger.valueOf(provinceCode)).and(DictCity.DICT_CITY.CODE.lt(UInteger.valueOf(provinceCode+1000)));
            // cond = DictCity.DICT_CITY.CODE.between(UInteger.valueOf(provinceCode), UInteger.valueOf(provinceCode+1000));
            // TODO: investigate why between not working
            cities = create.select().from(tableLike).where(cond).fetchInto(CityPojo.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return cities;
    }
}