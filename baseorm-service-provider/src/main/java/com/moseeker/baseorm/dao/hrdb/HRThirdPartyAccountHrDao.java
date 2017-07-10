package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountHrRecord;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.OrderBy;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * HR帐号数据库持久类
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Nov 9, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 *
 * @author wjf
 */
@Repository
public class HRThirdPartyAccountHrDao extends JooqCrudImpl<HrThirdPartyAccountHrDO, HrThirdPartyAccountHrRecord> {

    public HRThirdPartyAccountHrDao() {
        super(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR, HrThirdPartyAccountHrDO.class);
    }

    /**
     * 按照绑定时间倒序获取和某个第三方帐号绑定的第三方帐号
     * @param accountId
     * @return
     */
    public List<HrThirdPartyAccountHrDO> getBinders(int accountId) {
        Query query = new Query.QueryBuilder()
                .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.getName(), accountId)
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 1)
                .orderBy(new OrderBy(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.CREATE_TIME.getName(), Order.DESC))
                .buildQuery();

        return getDatas(query);

    }

}
