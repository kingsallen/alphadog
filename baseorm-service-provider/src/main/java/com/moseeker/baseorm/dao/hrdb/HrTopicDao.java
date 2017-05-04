package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTopicRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTopicDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrTopicDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrTopicDao extends JooqCrudImpl<HrTopicDO, HrTopicRecord> {


    public HrTopicDao(TableImpl<HrTopicRecord> table, Class<HrTopicDO> hrTopicDOClass) {
        super(table, hrTopicDOClass);
    }
}
