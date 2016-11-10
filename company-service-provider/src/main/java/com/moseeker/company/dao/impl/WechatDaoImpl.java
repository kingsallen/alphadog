package com.moseeker.company.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.company.dao.WechatDao;
import com.moseeker.db.hrdb.tables.HrWxWechat;
import com.moseeker.db.hrdb.tables.records.HrWxWechatRecord;

@Repository
public class WechatDaoImpl extends BaseDaoImpl<HrWxWechatRecord, HrWxWechat> implements WechatDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrWxWechat.HR_WX_WECHAT;
	}
}
