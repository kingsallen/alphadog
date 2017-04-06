package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;

@Repository
public class HrWxWechatDao extends BaseDaoImpl<HrWxWechatRecord, HrWxWechat> {

	@Override
	protected void initJOOQEntity() {
		tableLike = HrWxWechat.HR_WX_WECHAT;
	}
}
