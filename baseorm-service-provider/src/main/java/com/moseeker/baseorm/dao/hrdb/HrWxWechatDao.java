package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HrWxWechatDao extends JooqCrudImpl<HrWxWechatDO, HrWxWechatRecord> {

    public HrWxWechatDao() {
        super(HrWxWechat.HR_WX_WECHAT, HrWxWechatDO.class);
    }

	public HrWxWechatDao(TableImpl<HrWxWechatRecord> table, Class<HrWxWechatDO> hrWxWechatDOClass) {
		super(table, hrWxWechatDOClass);
	}
}
