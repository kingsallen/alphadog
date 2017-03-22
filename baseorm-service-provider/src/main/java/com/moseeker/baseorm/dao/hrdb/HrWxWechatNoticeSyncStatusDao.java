package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxWechatNoticeSyncStatus;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatNoticeSyncStatusRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatNoticeSyncStatusDO;

/**
* @author xxx
* HrWxWechatNoticeSyncStatusDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxWechatNoticeSyncStatusDao extends StructDaoImpl<HrWxWechatNoticeSyncStatusDO, HrWxWechatNoticeSyncStatusRecord, HrWxWechatNoticeSyncStatus> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxWechatNoticeSyncStatus.HR_WX_WECHAT_NOTICE_SYNC_STATUS;
   }
}
