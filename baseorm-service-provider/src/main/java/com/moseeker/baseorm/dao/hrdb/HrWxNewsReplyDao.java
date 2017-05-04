package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNewsReplyRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNewsReplyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxNewsReplyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxNewsReplyDao extends JooqCrudImpl<HrWxNewsReplyDO, HrWxNewsReplyRecord> {


    public HrWxNewsReplyDao(TableImpl<HrWxNewsReplyRecord> table, Class<HrWxNewsReplyDO> hrWxNewsReplyDOClass) {
        super(table, hrWxNewsReplyDOClass);
    }
}
