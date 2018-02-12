package com.moseeker.application.infrastructure;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrWxWechatDao;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.moseeker.baseorm.db.hrdb.tables.HrWxWechat.HR_WX_WECHAT;
import static org.jooq.impl.DSL.using;

/**
 *
 * 由于历史遗留问题导致 HrWxWechatDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 *
 * Created by jack on 24/01/2018.
 */
public class WechatJOOQDao extends HrWxWechatDao {

    public WechatJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 查找公司的signature信息，返回company_id 和 signature
     * @param companyIdList 公司编号
     * @return 公司的signature信息集合
     */
    public List<Record2<Integer,String>> getCompanyIdAndSignatureByCompanyId(List<Integer> companyIdList) {
        if (companyIdList != null && companyIdList.size() > 0) {
            Result<Record2<Integer, String>> result = using(configuration())
                    .select(HR_WX_WECHAT.COMPANY_ID, HR_WX_WECHAT.SIGNATURE)
                    .from(HR_WX_WECHAT)
                    .where(HR_WX_WECHAT.COMPANY_ID.in(companyIdList))
                    .and(HR_WX_WECHAT.AUTHORIZED.eq((byte) 1))
                    .fetch();
            if (result != null) {
                return result;
            }
        }
        return new ArrayList<>();
    }
}
