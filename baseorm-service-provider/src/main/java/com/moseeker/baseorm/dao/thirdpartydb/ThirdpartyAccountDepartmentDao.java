package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountDepartment;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountDepartmentRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author xxx
* ThirdpartyAccountCityDao 实现类 （groovy 生成）
* 2017-09-27
*/
@Repository
public class ThirdpartyAccountDepartmentDao extends JooqCrudImpl<ThirdpartyAccountDepartmentDO, ThirdpartyAccountDepartmentRecord> {

    public ThirdpartyAccountDepartmentDao() {
        super(ThirdpartyAccountDepartment.THIRDPARTY_ACCOUNT_DEPARTMENT, ThirdpartyAccountDepartmentDO.class);
    }


    public ThirdpartyAccountDepartmentDao(TableImpl<ThirdpartyAccountDepartmentRecord> table, Class<ThirdpartyAccountDepartmentDO> doClass) {
        super(table, doClass);
    }

    public List<ThirdpartyAccountDepartmentDO> getDepartmentByAccountId(int accountId) {
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyAccountDepartment.THIRDPARTY_ACCOUNT_DEPARTMENT.ACCOUNT_ID.getName(),accountId).buildQuery();

        return getDatas(query);
    }

    public ThirdpartyAccountDepartmentDO getDepartmentById(int id) {
        Query query = new Query.QueryBuilder()
                .where(ThirdpartyAccountDepartment.THIRDPARTY_ACCOUNT_DEPARTMENT.ACCOUNT_ID.getName(),id).buildQuery();

        return getData(query);
    }
}
