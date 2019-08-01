package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrWxWechatQrcodeDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatQrcodeRecord;
import org.jooq.Configuration;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.hrdb.tables.HrWxWechatQrcode.HR_WX_WECHAT_QRCODE;
import static org.jooq.impl.DSL.using;

/**
 * @Auther: Rays
 * @Date: 2019/7/29 21:27
 * @Description:
 */
@Repository
public class HrWxWechatQrcodeJOOQDao extends HrWxWechatQrcodeDao {

    public HrWxWechatQrcodeJOOQDao(Configuration configuration) {
        super(configuration);
    }

    public HrWxWechatQrcode fetchByWechatIdAndScenes(String scenes, Integer wechatId){

        List<HrWxWechatQrcodeRecord> records = using(configuration()).selectFrom(HR_WX_WECHAT_QRCODE)
                .where(HR_WX_WECHAT_QRCODE.SCENE.eq(scenes))
                .and(HR_WX_WECHAT_QRCODE.WECHAT_ID.eq(wechatId))
                .fetch();
        if(records!=null&&records.size()>0){
            return records.get(0).into(HrWxWechatQrcode.class);
        }
        return null;
    }
}
