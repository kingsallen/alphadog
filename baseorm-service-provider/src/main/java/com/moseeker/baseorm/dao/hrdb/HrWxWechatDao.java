package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import java.util.List;
import org.jooq.Result;
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

	public HrWxWechatDO getHrWxWechatByCompanyId(int company_id ){
        List<HrWxWechatDO> result = create.selectFrom(HrWxWechat.HR_WX_WECHAT)
                .where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.eq(company_id))
                .fetchInto(HrWxWechatDO.class);
        if(result != null && result.size()>0){
            return result.get(0);
        }
        return null;
    }
}
