package com.moseeker.dict.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictCountry;
import com.moseeker.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.dict.dao.DictCountryDao;
import com.moseeker.dict.pojo.DictCountryPojo;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public class DictCountryDaoImpl extends BaseDaoImpl<DictCountryRecord, DictCountry> implements DictCountryDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictCountry.DICT_COUNTRY;
    }

    /**
     * 获取国家字典
     *
     * @return
     * @throws Exception
     */
    public List<DictCountryPojo> getDictCountry() throws Exception{

        Connection conn = null;
        Condition condition = null;
        List<DictCountryPojo> dictCountryPojoList = null;

        try {
            initJOOQEntity();
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            condition = DictCountry.DICT_COUNTRY.CONTINENT_CODE.gt(UInteger.valueOf(0));

            dictCountryPojoList = create.select().from(DictCountry.DICT_COUNTRY).
                    where(condition).fetchInto(DictCountryPojo.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
        return dictCountryPojoList;
    }

}