package com.moseeker.dict.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.dict.dao.CityDao;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.dictdb.tables.DictCity;
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

    @SuppressWarnings("unchecked")
    public List<DictCityRecord> getResources(CommonQuery query) throws Exception {
        List<DictCityRecord> records = null;
        Connection conn = null;
        try {
            initJOOQEntity();
            records = new ArrayList<>();
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            SelectJoinStep<Record> table = create.select().from(tableLike);

            Result<Record> result = table.fetch();

            if (result != null && result.size() > 0) {
                for (Record r : result) {
                    records.add((DictCityRecord) r);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        } finally {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return records;
    }

}