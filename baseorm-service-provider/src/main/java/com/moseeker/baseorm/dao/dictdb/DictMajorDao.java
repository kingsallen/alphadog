package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictMajor;
import com.moseeker.baseorm.db.dictdb.tables.records.DictMajorRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictMajorDO;
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
* DictMajorDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictMajorDao extends JooqCrudImpl<DictMajorDO, DictMajorRecord> {

    public DictMajorDao() {
        super(DictMajor.DICT_MAJOR, DictMajorDO.class);
    }

    public DictMajorDao(TableImpl<DictMajorRecord> table, Class<DictMajorDO> dictMajorDOClass) {
        super(table, dictMajorDOClass);
    }

    public List<DictMajorRecord> getMajorsByIDs(List<String> ids) {

        List<DictMajorRecord> records = new ArrayList<>();
        Connection conn = null;
        try {
            if(ids != null && ids.size() > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                SelectWhereStep<DictMajorRecord> select = create.selectFrom(DictMajor.DICT_MAJOR);
                SelectConditionStep<DictMajorRecord> selectCondition = null;
                for(int i=0; i<ids.size(); i++) {
                    if(i == 0) {
                        selectCondition = select.where(DictMajor.DICT_MAJOR.CODE.equal(ids.get(i)));
                    } else {
                        selectCondition.or(DictMajor.DICT_MAJOR.CODE.equal(ids.get(i)));
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

    public DictMajorRecord getMajorByID(String code) {
        DictMajorRecord record = null;
        Connection conn = null;
        try {
            if(!StringUtils.isNullOrEmpty(code)) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                Result<DictMajorRecord> result = create.selectFrom(DictMajor.DICT_MAJOR)
                        .where(DictMajor.DICT_MAJOR.CODE.equal(code)).limit(1).fetch();
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
