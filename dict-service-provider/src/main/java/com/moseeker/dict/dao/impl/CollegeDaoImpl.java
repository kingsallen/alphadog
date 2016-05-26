package com.moseeker.dict.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictCity;
import com.moseeker.db.dictdb.tables.DictCollege;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.dict.dao.CityDao;
import com.moseeker.dict.dao.CollegeDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CollegeDaoImpl extends BaseDaoImpl<DictCollegeRecord, DictCollege> implements CollegeDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictCollege.DICT_COLLEGE;
    }

    @SuppressWarnings("unchecked")
    public List<DictCollegeRecord> getResources(CommonQuery query) throws Exception {
        List<DictCollegeRecord> records = null;
        Connection conn = null;
        try {
            // needs to join table
            initJOOQEntity();
            records = new ArrayList<>();
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            SelectJoinStep<Record> table = create.select().from(tableLike);
            Result<Record> result = table.fetch();

            if (result != null && result.size() > 0) {
                for (Record r : result) {
                    records.add((DictCollegeRecord) r);
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