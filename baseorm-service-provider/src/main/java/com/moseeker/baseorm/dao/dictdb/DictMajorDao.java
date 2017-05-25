package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictMajor;
import com.moseeker.baseorm.db.dictdb.tables.records.DictMajorRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictMajorDO;
import java.util.List;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author xxx
 *         DictMajorDao 实现类 （groovy 生成）
 *         2017-03-21
 */
@Repository
public class DictMajorDao extends JooqCrudImpl<DictMajorDO, DictMajorRecord> {

    public DictMajorDao() {
        super(DictMajor.DICT_MAJOR, DictMajorDO.class);
    }

    public DictMajorDao(TableImpl<DictMajorRecord> table, Class<DictMajorDO> dictMajorDOClass) {
        super(table, dictMajorDOClass);
    }

    public List<DictMajorDO> getMajorsByIDs(List<String> ids) {
        List<DictMajorDO> result = null;
        if (ids != null && ids.size() > 0) {
            Query q = new Query.QueryBuilder().where(Condition.buildCommonCondition("code", ids, ValueOp.IN)).buildQuery();
            result = getDatas(q);
        }

        return result;
    }

    public DictMajorDO getMajorByID(String code) {
        return getData(new Query.QueryBuilder().where("code",code).buildQuery());
    }
}
