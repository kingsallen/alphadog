package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChatList;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatListRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatListDO;

/**
* @author xxx
* HrWxHrChatListDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxHrChatListDao extends StructDaoImpl<HrWxHrChatListDO, HrWxHrChatListRecord, HrWxHrChatList> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxHrChatList.HR_WX_HR_CHAT_LIST;
   }
}
