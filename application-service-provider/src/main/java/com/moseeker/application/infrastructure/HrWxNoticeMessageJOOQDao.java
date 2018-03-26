package com.moseeker.application.infrastructure;

import static com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import static com.moseeker.baseorm.db.hrdb.tables.HrWxWechat.HR_WX_WECHAT;
import com.moseeker.baseorm.db.hrdb.tables.daos.HrWxNoticeMessageDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jooq.Configuration;
import static org.jooq.impl.DSL.using;

/**
 *
 * 由于历史遗留问题导致 HrWxWechatDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 *
 * Created by jack on 24/01/2018.
 */
public class HrWxNoticeMessageJOOQDao extends HrWxNoticeMessageDao {

    public HrWxNoticeMessageJOOQDao(Configuration configuration) {
        super(configuration);
    }


    public List<HrWxNoticeMessage> getCompanyIdAndNoticeByCompanyId(List<Integer> companyIdList, int  notice_id) {
        if (companyIdList != null && companyIdList.size() > 0) {
            List<HrWxWechatRecord> wechatList = using(configuration())
                    .select()
                    .from(HR_WX_WECHAT)
                    .where(HR_WX_WECHAT.COMPANY_ID.in(companyIdList))
                    .and(HR_WX_WECHAT.AUTHORIZED.eq((byte) 1))
                    .fetchInto(HrWxWechatRecord.class);
            if(wechatList != null&& wechatList.size() >0){
                List<Integer> wecharList = wechatList.stream().map(m -> m.getId()).collect(Collectors.toList());

                List<HrWxNoticeMessage> noticeMessageList = using(configuration())
                        .select()
                        .from(HR_WX_NOTICE_MESSAGE)
                        .where(HR_WX_NOTICE_MESSAGE.WECHAT_ID.in(wecharList))
                        .and(HR_WX_NOTICE_MESSAGE.NOTICE_ID.eq(notice_id))
                        .and(HR_WX_NOTICE_MESSAGE.DISABLE.eq((byte) 0))
                        .fetchInto(HrWxNoticeMessage.class);
                if(noticeMessageList != null){
                    return  noticeMessageList;
                }
            }

        }
        return new ArrayList<>();
    }
}
