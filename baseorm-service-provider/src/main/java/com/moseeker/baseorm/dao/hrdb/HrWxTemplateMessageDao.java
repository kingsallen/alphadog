package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxTemplateMessageRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<HrWxTemplateMessageDO> getHrWxTemplateMessageDOByWechatIds(List<Integer> ids, int sysTemplateId){
        List<HrWxTemplateMessageDO> result = create.selectFrom(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE)
                .where(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WECHAT_ID.in(ids))
                .and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.SYS_TEMPLATE_ID.eq(sysTemplateId))
                .fetchInto(HrWxTemplateMessageDO.class);
        return result;
    }

    public HrWxTemplateMessageDO getHrWxTemplateMessageDOByWechatId(Integer wechatId, int sysTemplateId){
        HrWxTemplateMessageDO result = create.selectFrom(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE)
                .where(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WECHAT_ID.eq(wechatId))
                .and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.SYS_TEMPLATE_ID.eq(sysTemplateId))
                .and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.DISABLE.eq(0))
                .fetchOneInto(HrWxTemplateMessageDO.class);
        return result;
    }
}
