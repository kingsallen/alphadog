package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxTemplateMessageRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxTemplateMessageDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxTemplateMessageDao extends JooqCrudImpl<HrWxTemplateMessageDO, HrWxTemplateMessageRecord> {

    public HrWxTemplateMessageDao() {
        super(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE, HrWxTemplateMessageDO.class);
    }

    public HrWxTemplateMessageDao(TableImpl<HrWxTemplateMessageRecord> table, Class<HrWxTemplateMessageDO> hrWxTemplateMessageDOClass) {
        super(table, hrWxTemplateMessageDOClass);
    }
}
