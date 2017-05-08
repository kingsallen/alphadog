package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrMedia;
import com.moseeker.baseorm.db.hrdb.tables.records.HrMediaRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrMediaDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrMediaDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrMediaDao extends JooqCrudImpl<HrMediaDO, HrMediaRecord> {

    public HrMediaDao() {
        super(HrMedia.HR_MEDIA, HrMediaDO.class);
    }

    public HrMediaDao(TableImpl<HrMediaRecord> table, Class<HrMediaDO> hrMediaDOClass) {
        super(table, hrMediaDOClass);
    }
}
