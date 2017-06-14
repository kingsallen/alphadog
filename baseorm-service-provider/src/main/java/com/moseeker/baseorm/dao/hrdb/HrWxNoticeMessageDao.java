package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNoticeMessageRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxNoticeMessageDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxNoticeMessageDao extends JooqCrudImpl<HrWxNoticeMessageDO, HrWxNoticeMessageRecord> {

    public HrWxNoticeMessageDao() {
        super(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE, HrWxNoticeMessageDO.class);
    }

    public HrWxNoticeMessageDao(TableImpl<HrWxNoticeMessageRecord> table, Class<HrWxNoticeMessageDO> hrWxNoticeMessageDOClass) {
        super(table, hrWxNoticeMessageDOClass);
    }
}
