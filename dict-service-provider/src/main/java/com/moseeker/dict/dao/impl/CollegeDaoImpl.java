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
    public List getJoinedResults(CommonQuery query) {
        List<Record> records = null;
        try (Connection conn = DBConnHelper.DBConn.getConn())
        {
            // needs to
            initJOOQEntity();
            records = new ArrayList<Record>();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Result<? extends Record> result = create
                    .select(DictCollege.DICT_COLLEGE.CODE.as("college_code"),
                            DictCollege.DICT_COLLEGE.NAME.as("college_name"),
                            DictCity.DICT_CITY.CODE.as("province_code"),
                            DictCity.DICT_CITY.NAME.as("province_name"))
                    .from(DictCollege.DICT_COLLEGE)
                    .join(DictCity.DICT_CITY)
                    .on(DictCollege.DICT_COLLEGE.PROVINCE.equal(DictCity.DICT_CITY.CODE))
                    .fetch();

            if (result != null && result.size() > 0) {
                for (Record r : result) {
                    records.add(r);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return records;
    }

}