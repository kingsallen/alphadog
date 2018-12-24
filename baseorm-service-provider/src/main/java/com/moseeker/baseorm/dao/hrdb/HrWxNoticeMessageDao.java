package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNoticeMessageRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import java.util.List;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxNoticeMessageDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxNoticeMessageDao extends JooqCrudImpl<HrWxNoticeMessageDO, HrWxNoticeMessageRecord> {

    public HrWxNoticeMessageDao() {
        super(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE, HrWxNoticeMessageDO.class);
    }

    public HrWxNoticeMessageDao(TableImpl<HrWxNoticeMessageRecord> table, Class<HrWxNoticeMessageDO> hrWxNoticeMessageDOClass) {
        super(table, hrWxNoticeMessageDOClass);
    }

    public List<HrWxNoticeMessageDO> getHrWxNoticeMessageDOByWechatIds(List<Integer> ids, int sysTemplateId){
        List<HrWxNoticeMessageDO> result = create.selectFrom(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE)
                .where(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID.in(ids))
                .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID.eq(sysTemplateId))
                .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE.eq((byte)0))
                .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.STATUS.eq((byte)1))
                .fetchInto(HrWxNoticeMessageDO.class);
        return result;
    }

    public HrWxNoticeMessageDO getHrWxNoticeMessageDOByWechatId(Integer id, int sysTemplateId){
        HrWxNoticeMessageDO result = create.selectFrom(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE)
                .where(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID.eq(id))
                .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID.eq(sysTemplateId))
                .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE.eq((byte)0))
                .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.STATUS.eq((byte)1))
                .fetchOneInto(HrWxNoticeMessageDO.class);
        return result;
    }
}
