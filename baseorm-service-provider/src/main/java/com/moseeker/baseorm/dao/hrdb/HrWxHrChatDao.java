package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxHrChat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxHrChatRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;

/**
* @author xxx
* HrWxHrChatDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxHrChatDao extends StructDaoImpl<HrWxHrChatDO, HrWxHrChatRecord, HrWxHrChat> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxHrChat.HR_WX_HR_CHAT;
   }
}
