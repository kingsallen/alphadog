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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CityDaoImpl extends BaseDaoImpl<DictCityRecord, DictCity> implements CityDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictCity.DICT_CITY;
    }

    public List<CityPojo> getCities() {
        initJOOQEntity();
        List<CityPojo> cities = null;
        try(Connection conn = DBConnHelper.DBConn.getConn()) {
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn) ;
            cities = create.select().from(tableLike).fetchInto(CityPojo.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return cities;
    }

}