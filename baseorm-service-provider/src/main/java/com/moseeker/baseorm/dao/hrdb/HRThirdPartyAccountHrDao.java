package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountHrRecord;
import com.moseeker.common.util.query.*;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
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
                .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 1)
                .orderBy(new OrderBy(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.CREATE_TIME.getName(), Order.DESC))
                .orderBy(new OrderBy(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.ID.getName(), Order.DESC))
                .buildQuery();

        return getDatas(query);

    }

    public HrThirdPartyAccountHrDO getData(long hrId,int channel){
        Query query = new Query.QueryBuilder()
                .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.getName(), hrId)
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.CHANNEL.getName(),channel)
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 1).buildQuery();
        return getData(query);
    }

    /**
     * 查询这些Hr绑定的某个渠道下的其它帐号
     * @param accountId 排除的第三方账号
     * @param hrIds HR的ID
     * @param channel 渠道号
     * @return
     */
    public List<HrThirdPartyAccountHrDO> getHrOtherBoundAcount(int accountId, List<Integer> hrIds,int channel){
        Query query = new Query.QueryBuilder()
                .where(new Condition(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.getName(), hrIds, ValueOp.IN))
                .and(new Condition(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId, ValueOp.NEQ))
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.CHANNEL.getName(), channel)
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 1)
                .buildQuery();
        return getDatas(query);
    }

    public int invalidByIds(List<Integer> ids){
        Update update =new Update.UpdateBuilder()
                .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                .where(new Condition(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.ID.getName(), ids, ValueOp.IN))
                .buildUpdate();
        return update(update);
    }

    public int invalidByThirdPartyAccountId(int accountId){
        Update update =new Update.UpdateBuilder()
                .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                .buildUpdate();
        return update(update);
    }

    public int invalidByIdsAccountId(List<Integer> ids,int accountId){
        Update update =new Update.UpdateBuilder()
                .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                .where(new Condition(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.ID.getName(), ids, ValueOp.IN))
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                .buildUpdate();
        return update(update);
    }

}
