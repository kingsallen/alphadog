package com.moseeker.baseorm.dao.malldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.malldb.tables.MallOrderOperation;
import com.moseeker.baseorm.db.malldb.tables.records.MallOrderOperationRecord;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderOperationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author cjm
 * @date 2018-10-11 18:42
 **/
@Repository
public class MallOrderOperationDao extends JooqCrudImpl<MallOrderOperationDO, MallOrderOperationRecord> {

    public MallOrderOperationDao() {
        super(MallOrderOperation.MALL_ORDER_OPERATION, MallOrderOperationDO.class);
    }

    public MallOrderOperationDao(TableImpl<MallOrderOperationRecord> table, Class<MallOrderOperationDO> mallOrderOperationDOClass) {
        super(table, mallOrderOperationDOClass);
    }
}
