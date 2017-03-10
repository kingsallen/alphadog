package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrChatUnreadCount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrChatUnreadCountRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.HrChatUnreadCountDO;

/**
 * Created by jack on 09/03/2017.
 */
@Repository
public class HrChatUnreadCountDao extends StructDaoImpl<HrChatUnreadCountDO, HrChatUnreadCountRecord, HrChatUnreadCount> {
}
