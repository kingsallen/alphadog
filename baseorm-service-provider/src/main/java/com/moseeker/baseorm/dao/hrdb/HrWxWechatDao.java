package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;

@Repository
public class HrWxWechatDao extends StructDaoImpl<HrWxWechatDO, HrWxWechatRecord, HrWxWechat> {

	@Override
	protected void initJOOQEntity() {
		tableLike = HrWxWechat.HR_WX_WECHAT;
	}
}
