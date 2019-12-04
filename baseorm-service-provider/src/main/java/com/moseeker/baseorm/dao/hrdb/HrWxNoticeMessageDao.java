package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxNoticeMessageRecord;
import com.moseeker.baseorm.pojo.HrWxNoticeMessagePojo;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.configdb.tables.ConfigSysTemplateMessageLibrary.CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY;
import static com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE;

/**
* @author xxx
* HrWxNoticeMessageDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxNoticeMessageDao extends JooqCrudImpl<HrWxNoticeMessageDO, HrWxNoticeMessageRecord> {

    public HrWxNoticeMessageDao() {
        super(HR_WX_NOTICE_MESSAGE, HrWxNoticeMessageDO.class);
    }

    public HrWxNoticeMessageDao(TableImpl<HrWxNoticeMessageRecord> table, Class<HrWxNoticeMessageDO> hrWxNoticeMessageDOClass) {
        super(table, hrWxNoticeMessageDOClass);
    }

    public List<HrWxNoticeMessageRecord> getHrWxNoticeMessageDOByWechatIds(List<Integer> ids, int sysTemplateId){
        Result<HrWxNoticeMessageRecord> result = create.selectFrom(HR_WX_NOTICE_MESSAGE)
                .where(HR_WX_NOTICE_MESSAGE.WECHAT_ID.in(ids))
                .and(HR_WX_NOTICE_MESSAGE.NOTICE_ID.eq(sysTemplateId))
                .and(HR_WX_NOTICE_MESSAGE.DISABLE.eq((byte)0))
                .and(HR_WX_NOTICE_MESSAGE.STATUS.eq((byte)1))
                .fetch();
        return result;
    }

    public int updateSentDate(List<Integer> ids, int sysTemplateId,java.sql.Date date){
        return create.update(HR_WX_NOTICE_MESSAGE)
                .set(HR_WX_NOTICE_MESSAGE.SENT_DATE,date)
                .where(HR_WX_NOTICE_MESSAGE.WECHAT_ID.in(ids))
                .and(HR_WX_NOTICE_MESSAGE.NOTICE_ID.eq(sysTemplateId))
                .and(HR_WX_NOTICE_MESSAGE.DISABLE.eq((byte)0))
                .and(HR_WX_NOTICE_MESSAGE.STATUS.eq((byte)1))
                .execute();
    }



    public HrWxNoticeMessageRecord getHrWxNoticeMessageDOByWechatId(Integer id, int sysTemplateId){
        HrWxNoticeMessageRecord result = create.selectFrom(HR_WX_NOTICE_MESSAGE)
                .where(HR_WX_NOTICE_MESSAGE.WECHAT_ID.eq(id))
                .and(HR_WX_NOTICE_MESSAGE.NOTICE_ID.eq(sysTemplateId))
                .and(HR_WX_NOTICE_MESSAGE.DISABLE.eq((byte)0))
                .and(HR_WX_NOTICE_MESSAGE.STATUS.eq((byte)1))
                .fetchOne();
        return result;
    }

    public HrWxNoticeMessageDO getHrWxNoticeMessageDOWithoutStatus(Integer id, int sysTemplateId){
        return create.selectFrom(HR_WX_NOTICE_MESSAGE)
                .where(HR_WX_NOTICE_MESSAGE.WECHAT_ID.eq(id))
                .and(HR_WX_NOTICE_MESSAGE.NOTICE_ID.eq(sysTemplateId))
                .fetchOneInto(HrWxNoticeMessageDO.class);
    }

    public List<HrWxNoticeMessagePojo> listByWechatId(int wechatId) {
        return create
                .select(HR_WX_NOTICE_MESSAGE.ID,
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.TITLE,
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.SEND_CONDITION,
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.SENDTIME.as("send_time"),
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.SENDTO.as("send_to"),
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.SAMPLE,
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.FIRST,
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.PRIORITY,
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.REMARK,
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.SEND_FREQUENCY.as("frequency_options"),
                        HR_WX_NOTICE_MESSAGE.SEND_FREQUENCY.as("frequency_value"),
                        HR_WX_NOTICE_MESSAGE.FIRST.as("custom_first"),
                        HR_WX_NOTICE_MESSAGE.REMARK.as("custom_remark"),
                        CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.ID.as("configId"),
                        HR_WX_NOTICE_MESSAGE.STATUS)
                .from(HR_WX_NOTICE_MESSAGE)
                .leftJoin(CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY)
                .on(HR_WX_NOTICE_MESSAGE.NOTICE_ID.eq(CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.ID))
                .where(HR_WX_NOTICE_MESSAGE.WECHAT_ID.eq(wechatId))
                .and(HR_WX_NOTICE_MESSAGE.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                .and(CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.DISABLE.eq(AbleFlag.OLDENABLE.getValue()))
                .orderBy(CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY.PRIORITY.desc(), HR_WX_NOTICE_MESSAGE.ID.asc())
                .fetchInto(HrWxNoticeMessagePojo.class);
    }
}
