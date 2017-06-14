package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxImageReply;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxImageReplyRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxImageReplyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxImageReplyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxImageReplyDao extends JooqCrudImpl<HrWxImageReplyDO, HrWxImageReplyRecord> {

    public HrWxImageReplyDao() {
        super(HrWxImageReply.HR_WX_IMAGE_REPLY, HrWxImageReplyDO.class);
    }

    public HrWxImageReplyDao(TableImpl<HrWxImageReplyRecord> table, Class<HrWxImageReplyDO> hrWxImageReplyDOClass) {
        super(table, hrWxImageReplyDOClass);
    }
}
