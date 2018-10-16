package com.moseeker.baseorm.dao.malldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.malldb.tables.MallOrderHrOperation;
import com.moseeker.baseorm.db.malldb.tables.records.MallOrderHrOperationRecord;
import com.moseeker.thrift.gen.dao.struct.malldb.MallOrderHrOperationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author cjm
 * @date 2018-10-11 18:42
 **/
@Repository
public class MallGoodsAssignHrOperationDao extends JooqCrudImpl<MallOrderHrOperationDO, MallOrderHrOperationRecord> {

    public MallGoodsAssignHrOperationDao() {
        super(MallOrderHrOperation.MALL_ORDER_HR_OPERATION, MallOrderHrOperationDO.class);
    }

    public MallGoodsAssignHrOperationDao(TableImpl<MallOrderHrOperationRecord> table, Class<MallOrderHrOperationDO> mallOrderHrOperationDOClass) {
        super(table, mallOrderHrOperationDOClass);
    }
}
