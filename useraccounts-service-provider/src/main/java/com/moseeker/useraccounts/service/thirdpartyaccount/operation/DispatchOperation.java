package com.moseeker.useraccounts.service.thirdpartyaccount.operation;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.service.thirdpartyaccount.ThirdPartyAccountService;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.ChaosHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DispatchOperation {
    Logger logger= LoggerFactory.getLogger(DispatchOperation.class);

    @Autowired
    protected ChaosHandler chaosHandler;

    @Autowired
    protected HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    protected HRThirdPartyAccountHrDao thirdPartyAccountHrDao;

    @Autowired
    protected ThirdPartyAccountService thirdPartyAccountService;

    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        HrThirdPartyAccountDO thirdPartyAccount=thirdPartyAccountDao.getAccountById(accountId);

        logger.info("第三方帐号重新分配:{}:{}", thirdPartyAccount, hrIds);

        //作废无效的绑定关系
        invalidNotUsedRelationship(thirdPartyAccount,hrIds);

        //获取原来该帐号绑定的所有hr
        List<HrThirdPartyAccountHrDO> binders = thirdPartyAccountHrDao.getBinders(thirdPartyAccount.getId());

        if (hrIds == null || hrIds.size() == 0) {
            //取消所有hr与该账号的关联
            thirdPartyAccountHrDao.invalidByThirdPartyAccountId(thirdPartyAccount.getId());
        } else {
            if (binders == null || binders.size() == 0) {
                dispathTo(thirdPartyAccount, hrIds);
            } else {
                Set<Integer> lastboundIds = binders.stream().map(item -> item.getHrAccountId()).collect(Collectors.toSet());
                logger.info("lastboundIds : {}",lastboundIds);

                //取消分配的hr
                List<Integer> needCancelHrIds = lastboundIds.stream().filter(hrId-> !hrIds.contains(hrId)).collect(Collectors.toList());
                cancelDispath(thirdPartyAccount, needCancelHrIds);

                //新分配的hr
                List<Integer> newHrIds = hrIds.stream().filter(hrId-> !lastboundIds.contains(hrId)).collect(Collectors.toList());
                dispathTo(thirdPartyAccount, newHrIds);

            }
        }
        return thirdPartyAccountService.getThridAccount(thirdPartyAccount.getId());

//        return context.getBindState(thirdPartyAccount).dispatch(thirdPartyAccount,hrIds);
    }

    private void invalidNotUsedRelationship(HrThirdPartyAccountDO thirdPartyAccount, List<Integer> hrIds) throws BIZException {
        //检查这些Hr是否绑定的其它相同渠道的帐号
        List<HrThirdPartyAccountHrDO> otherBinders = thirdPartyAccountHrDao.getHrOtherBoundAcount(thirdPartyAccount.getId(),hrIds,thirdPartyAccount.getChannel());
        if ( otherBinders != null && !otherBinders.isEmpty()) {
            //检查其它帐号是否有效
            List<Integer> accountIds = otherBinders.stream().map(item -> item.getThirdPartyAccountId()).collect(Collectors.toList());
            List<HrThirdPartyAccountDO> otherAccounts = thirdPartyAccountDao.getAccountsById(accountIds);

            if (otherAccounts != null && otherAccounts.size() > 0) {
                throw new BIZException(-1, "已分配其他账号");
            } else {
                //错误的数据进行修复,将这些用户与无效的帐号的关联关系解除
                List<Integer> ids = otherBinders.stream().map(item -> item.getId()).collect(Collectors.toList());
                logger.info("第三方帐号关联关系修复:{}", ids);
                thirdPartyAccountHrDao.invalidByIds(ids);
            }
        }
    }

    /**
     * 取消分配HR给这些HR
     *
     * @param thirdPartyAccount
     * @param hrIds
     */
    private void cancelDispath(HrThirdPartyAccountDO thirdPartyAccount, List<Integer> hrIds) {
        logger.info("第三方帐号取消分配:{}:{}", thirdPartyAccount.getId(), hrIds);
        if (hrIds == null || hrIds.size() == 0) return;
        thirdPartyAccountHrDao.invalidByHrIdsAccountId(hrIds,thirdPartyAccount.getId());
    }

    /**
     * 分配给这些HR
     *
     * @param thirdPartyAccount
     * @param hrIds
     */
    private void dispathTo(HrThirdPartyAccountDO thirdPartyAccount, List<Integer> hrIds) {
        logger.info("第三方帐号分配给:{}:{}", thirdPartyAccount.getId(), hrIds);
        if (hrIds == null || hrIds.size() == 0 || thirdPartyAccount == null) return;

        //挑出已经分配给相同渠道的帐号
        List<HrThirdPartyAccountHrDO> relations = new ArrayList<>();
        HrThirdPartyAccountHrDO relation;
        for (int hrId : hrIds) {
            relation = new HrThirdPartyAccountHrDO();
            relation.setChannel(thirdPartyAccount.getChannel());
            relation.setHrAccountId(hrId);
            relation.setStatus(Integer.valueOf(1).byteValue());
            relation.setThirdPartyAccountId(thirdPartyAccount.getId());
            relations.add(relation);
        }
        thirdPartyAccountHrDao.addAllData(relations);
    }
}
