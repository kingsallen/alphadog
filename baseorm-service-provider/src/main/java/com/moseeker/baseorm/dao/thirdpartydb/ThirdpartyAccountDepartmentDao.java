package com.moseeker.baseorm.dao.thirdpartydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.thirdpartydb.tables.ThirdpartyAccountDepartment;
import com.moseeker.baseorm.db.thirdpartydb.tables.records.ThirdpartyAccountDepartmentRecord;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyAccountDepartmentDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
}
