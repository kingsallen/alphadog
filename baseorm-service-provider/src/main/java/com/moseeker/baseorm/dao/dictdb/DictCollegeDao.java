package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.DictCollege;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* DictCollegeDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCollegeDao extends JooqCrudImpl<DictCollegeDO, DictCollegeRecord> {

    public DictCollegeDao() {
        super(DictCollege.DICT_COLLEGE, DictCollegeDO.class);
    }

    public DictCollegeDao(TableImpl<DictCollegeRecord> table, Class<DictCollegeDO> dictCollegeDOClass) {
        super(table, dictCollegeDOClass);
    }

    @SuppressWarnings("unchecked")
    public List getJoinedResults(Query query) {
       return create.select(DictCollege.DICT_COLLEGE.CODE.as("college_code"),
                DictCollege.DICT_COLLEGE.NAME.as("college_name"),
                DictCollege.DICT_COLLEGE.LOGO.as("college_logo"),
                DictCity.DICT_CITY.CODE.as("province_code"),
                DictCity.DICT_CITY.NAME.as("province_name"))
                .from(DictCollege.DICT_COLLEGE)
                .join(DictCity.DICT_CITY)
                .on(DictCollege.DICT_COLLEGE.PROVINCE.equal(DictCity.DICT_CITY.CODE)).fetch();
    }

    public List<DictCollegeRecord> getCollegesByIDs(List<Integer> ids) {

        List<DictCollegeRecord> records = new ArrayList<>();
        Connection conn = null;
        try {
            if(ids != null && ids.size() > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                SelectWhereStep<DictCollegeRecord> select = create.selectFrom(DictCollege.DICT_COLLEGE);
                SelectConditionStep<DictCollegeRecord> selectCondition = null;
                for(int i=0; i<ids.size(); i++) {
                    if(i == 0) {
                        selectCondition = select.where(DictCollege.DICT_COLLEGE.CODE.equal((int)(ids.get(i))));
                    } else {
                        selectCondition.or(DictCollege.DICT_COLLEGE.CODE.equal((int)(ids.get(i))));
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

    public DictCollegeRecord getCollegeByID(int code) {
        DictCollegeRecord record = null;
        Connection conn = null;
        try {
            if(code > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                Result<DictCollegeRecord> result = create.selectFrom(DictCollege.DICT_COLLEGE)
                        .where(DictCollege.DICT_COLLEGE.CODE.equal((int)(code))).limit(1).fetch();
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
