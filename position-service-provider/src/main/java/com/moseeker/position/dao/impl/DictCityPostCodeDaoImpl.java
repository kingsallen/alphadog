package com.moseeker.position.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictCityPostcode;
import com.moseeker.db.dictdb.tables.records.DictCityPostcodeRecord;
import com.moseeker.position.dao.DictCityPostCodeDao;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by yuyunfeng on 2017/3/16.
 */
@Repository
public class DictCityPostCodeDaoImpl extends BaseDaoImpl<DictCityPostcodeRecord, DictCityPostcode> implements DictCityPostCodeDao {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictCityPostcode.DICT_CITY_POSTCODE;
    }

    @Override
    public DictCityPostcodeRecord fuzzyGetCityPostCode(String postCode) {
        Connection conn = null;
        DictCityPostcodeRecord dictCityPostcodeRecord = null;
        if (postCode != null) {
            try {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                Condition condition = DictCityPostcode.DICT_CITY_POSTCODE.field(DictCityPostcode.DICT_CITY_POSTCODE.POSTCODE).like(postCode + "%");
                Result<Record> result = create.select().from(DictCityPostcode.DICT_CITY_POSTCODE).where(condition).fetch();
                if (result != null && result.size() > 0) {
                    dictCityPostcodeRecord = (DictCityPostcodeRecord) result.get(0);
                }
            } catch (Exception e) {
                logger.error("error", e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    //do nothing
                }
            }
        }
        return dictCityPostcodeRecord;
    }

}
