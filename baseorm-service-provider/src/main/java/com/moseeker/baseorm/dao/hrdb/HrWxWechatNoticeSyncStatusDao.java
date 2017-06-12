package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechatNoticeSyncStatus;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatNoticeSyncStatusRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatNoticeSyncStatusDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxWechatNoticeSyncStatusDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxWechatNoticeSyncStatusDao extends JooqCrudImpl<HrWxWechatNoticeSyncStatusDO, HrWxWechatNoticeSyncStatusRecord> {

    public HrWxWechatNoticeSyncStatusDao() {
        super(HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS, HrWxWechatNoticeSyncStatusDO.class);
    }

    public HrWxWechatNoticeSyncStatusDao(TableImpl<HrWxWechatNoticeSyncStatusRecord> table, Class<HrWxWechatNoticeSyncStatusDO> hrWxWechatNoticeSyncStatusDOClass) {
        super(table, hrWxWechatNoticeSyncStatusDOClass);
    }
}
