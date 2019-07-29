package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrWxWechatQrcodeDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrWxWechatQrcode;
import org.jooq.Configuration;

import java.util.List;

import static com.moseeker.baseorm.db.hrdb.tables.HrWxWechatQrcode.HR_WX_WECHAT_QRCODE;
import static org.jooq.impl.DSL.using;

/**
 * @Auther: Rays
 * @Date: 2019/7/29 21:27
 * @Description:
 */

public class HrWxWechatQrcodeJOOQDao extends HrWxWechatQrcodeDao {

    public HrWxWechatQrcodeJOOQDao(Configuration configuration) {
        super(configuration);
    }

    public HrWxWechatQrcode fetchByWechatIdAndScenes(List<String> scenes, Integer wechatId){
        return using(configuration()).selectFrom(HR_WX_WECHAT_QRCODE)
                .where(HR_WX_WECHAT_QRCODE.SCENE.in(scenes))
                .and(HR_WX_WECHAT_QRCODE.WECHAT_ID.eq(wechatId))
                .fetchOne().into(HrWxWechatQrcode.class);
    }
}
