package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Update;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountHrInfo;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhangdi on 2017/7/24.
 */
@Service
public class ThirdPartyAccountService {

    Logger logger = LoggerFactory.getLogger(ThirdPartyAccountService.class);

    @Autowired
    UserHrAccountDao hrAccountDao;

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    HRThirdPartyAccountHrDao thirdPartyAccountHrDao;

    @Autowired
    private ThirdPartyAccountSynctor thirdPartyAccountSynctor;

    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    @Autowired
    private HrCompanyDao hrCompanyDao;


    /**
     * 第三方账号绑定
     *
     * @param hrId
     * @param account
     * @return
     */
    public HrThirdPartyAccountDO bindThirdAccount(int hrId, HrThirdPartyAccountDO account, boolean sync) throws Exception {
        logger.info("-------bindThirdAccount--------{}:{}", hrId, JSON.toJSONString(account));
        // 判断Channel是否合法
        ChannelType channelType = ChannelType.instaceFromInteger(account.getChannel());

        if (channelType == null) {
            throw new BIZException(-1, "不支持的渠道类型：" + account.getChannel());
        }

        // 判断是否需要进行帐号绑定
        Query qu = new Query.QueryBuilder().where("id", String.valueOf(hrId)).buildQuery();

        UserHrAccountDO userHrAccount = hrAccountDao.getData(qu);

        if (userHrAccount == null || userHrAccount.getActivation() != Byte.valueOf("1") || userHrAccount.getDisable() != 1) {
            //没有找到该hr账号
            throw new BIZException(-1, "无效的HR帐号");
        }

        account.setCompanyId(userHrAccount.getCompanyId());

        //获取子公司简称和ID
        HrCompanyDO hrCompanyDO = hrCompanyAccountDao.getHrCompany(hrId);

        if (hrCompanyDO == null) {
            hrCompanyDO = hrCompanyDao.getCompanyById(userHrAccount.getCompanyId());

            if (hrCompanyDO == null) {
                throw new BIZException(-1, "无效的HR账号");
            }
        }

        int allowStatus = allowBind(userHrAccount, account);

        logger.info("bindThirdAccount allowStatus:{}", allowStatus);

        if (allowStatus > 0) {
            account.setId(allowStatus);
        }

        Map<String, String> extras = new HashMap<>();

        //智联的帐号同步带上子公司简称
        if (account.getChannel() == ChannelType.ZHILIAN.getValue()) {
            extras.put("company", hrCompanyDO.getAbbreviation());
        }

        //allowStatus==0,绑定之后将hrId和帐号关联起来，allowStatus==1,只绑定不关联
        HrThirdPartyAccountDO result = thirdPartyAccountSynctor.bindThirdPartyAccount(allowStatus == 0 ? hrId : 0, account, extras, sync);


        return result;
    }

    public int checkRebinding(HrThirdPartyAccountDO bindingAccount) throws BIZException {
        if (bindingAccount.getBinding() == 1 || bindingAccount.getBinding() == 3 || bindingAccount.getBinding() == 7) {
            throw new BIZException(-1, "已经绑定该帐号了");
        } else if (bindingAccount.getBinding() == 2 || bindingAccount.getBinding() == 6) {
            throw new BIZException(-1, "该帐号已经在绑定中了");
        } else {
            //重新绑定
            logger.info("重新绑定:{}", bindingAccount.getId());
            return bindingAccount.getId();
        }
    }

    /**
     * 是否允许执行绑定
     * <0,主张号已绑定，
     * 0,正常绑定，
     * >0,复用帐号
     */
    @CounterIface
    public int allowBind(UserHrAccountDO hrAccount, HrThirdPartyAccountDO thirdPartyAccount) throws Exception {

        HrThirdPartyAccountDO bindingAccount = thirdPartyAccountDao.getThirdPartyAccountByUserId(hrAccount.getId(), thirdPartyAccount.getChannel());

        //如果当前hr已经绑定了该帐号
        if (bindingAccount != null && bindingAccount.getUsername().equals(thirdPartyAccount.getUsername())) {
            return checkRebinding(bindingAccount);
        }

        //主账号或者没有绑定第三方账号，检查公司下该渠道已经绑定过相同的第三方账号
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("company_id", hrAccount.getCompanyId());
        qu.and("channel", thirdPartyAccount.getChannel());
        qu.and("username", thirdPartyAccount.getUsername());
        qu.and(new Condition("binding", 0, ValueOp.NEQ));//有效的状态
        List<HrThirdPartyAccountDO> datas = thirdPartyAccountDao.getDatas(qu.buildQuery());

        logger.info("allowBind:相同名字的帐号:{}", JSON.toJSONString(datas));

        HrThirdPartyAccountDO data = null;

        for (HrThirdPartyAccountDO d : datas) {
            ///数据库中username是不区分大小写的，如果大小写不同，那么认为不是一个账号
            if (d.getUsername().equals(thirdPartyAccount.getUsername())) {
                data = d;
                break;
            }
        }

        if (data == null || data.getId() == 0) {
            //检查该用户是否绑定了其它相同渠道的账号
            logger.info("该用户绑定渠道{}的帐号:{}", thirdPartyAccount.getChannel(), JSON.toJSONString(bindingAccount));
            if (bindingAccount != null && bindingAccount.getId() > 0 && bindingAccount.getBinding() != 0) {
                if (hrAccount.getAccountType() == 0) {
                    logger.info("主张号已经绑定该渠道第三方帐号");
                    //如果主账号已经绑定该渠道第三方账号，那么绑定人为空,并允许绑定
                    return -1;
                } else {
                    logger.info("已经绑定过该渠道第三方帐号");
                    //已经绑定该渠道第三方账号，并且不是主账号，那么不允许绑定
                    throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_BINDING_LIMIT);
                }
            } else {
                return 0;
            }
        } else {
            //主张号发现已经有子帐号已经绑定了这个帐号
            if (hrAccount.getAccountType() == 0) {
                return checkRebinding(data);
            }

            logger.info("这个帐号已经被其它人绑定了");
            //公司下已经有人绑定了这个第三方账号，则这个公司谁都不能再绑定这个账号了
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_ALREADY_BOUND);
        }
    }


    /**
     * 同步第三方账号信息
     *
     * @param id
     * @return
     */

    public HrThirdPartyAccountDO synchronizeThirdpartyAccount(int hrId, int id, boolean sync) throws Exception {
        //查找第三方帐号
        Query qu = new Query.QueryBuilder().where("id", id).buildQuery();

        HrThirdPartyAccountDO hrThirdPartyAccount = thirdPartyAccountDao.getData(qu);

        UserHrAccountDO hrAccountDO = hrAccountDao.getValidAccount(hrId);

        if (hrAccountDO == null) {
            throw new BIZException(-1, "无效的HR帐号");
        }

        if (hrThirdPartyAccount == null || hrThirdPartyAccount.getBinding() == 0) {
            throw new BIZException(-1, "无效的第三方帐号");
        }

        Map<String, String> extras = new HashMap<>();
        //智联的帐号
        if (hrThirdPartyAccount.getChannel() == ChannelType.ZHILIAN.getValue()) {
            buildZhilianCompany(hrAccountDO, hrThirdPartyAccount, extras);
        }

        //如果是绑定状态，则进行
        hrThirdPartyAccount = thirdPartyAccountSynctor.syncThirdPartyAccount(hrThirdPartyAccount, extras, sync);

        //刷新成功
        return hrThirdPartyAccount;
    }

    private void buildZhilianCompany(UserHrAccountDO hrAccountDO, HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws BIZException {
        List<HrThirdPartyAccountHrDO> binders = thirdPartyAccountHrDao.getBinders(hrThirdPartyAccount.getId());

        int finalHrId = hrAccountDO.getId();//如果该帐号没有分配给任何人，谁刷新的使用谁的所属公司的简称

        if (binders != null && binders.size() > 0) {
            finalHrId = binders.get(0).getHrAccountId();//默认选择第一个关联的hr帐号

            for (HrThirdPartyAccountHrDO binder : binders) {
                if (binder.getHrAccountId() == hrAccountDO.getId()) {
                    finalHrId = binder.getHrAccountId();//如果该帐号关联了自己，那么选择自己的公司简称
                    logger.info("buildZhilianCompany,使用自己的所在公司的简称");
                    break;
                }
            }
        }

        HrCompanyDO companyDO = hrCompanyAccountDao.getHrCompany(finalHrId);

        if (hrAccountDO.getId() != finalHrId) {
            hrAccountDO = hrAccountDao.getValidAccount(finalHrId);
        }

        if (companyDO == null) {
            companyDO = hrCompanyDao.getCompanyById(hrAccountDO.getCompanyId());
        }

        if (companyDO == null) {
            throw new BIZException(-1, "无效的HR帐号");
        }

        extras.put("company", companyDO.getAbbreviation());
    }

    /**
     * 账号解绑，首先先查询账号在第三方平台表中是否存在，存在就进行解绑
     *
     * @param accountId
     * @param userId
     */
    public void unbindingAccount(int accountId, int userId) {
        UserHrAccountDO hrAccount = hrAccountDao.getValidAccount(userId);

        if (hrAccount == null) {
            throw new CommonException(-1, "无效的HR帐号");
        }

        HrThirdPartyAccountDO thirdPartyAccount = thirdPartyAccountDao.getAccountById(accountId);

        if (thirdPartyAccount == null || thirdPartyAccount.getBinding() == 0) {
            throw new CommonException(-1, "无效的第三方帐号");
        }

        //如果是主账号,接触所有关系
        if (hrAccount.getAccountType() == 0 || hrAccount.getAccountType() == 2) {
            logger.info("主账号解绑解除");
            //解除第三方账号和HR的关系
            Update update = new Update.UpdateBuilder()
                    .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                    .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                    .buildUpdate();
            thirdPartyAccountHrDao.update(update);
            thirdPartyAccount.setBinding(Integer.valueOf(0).shortValue());
            thirdPartyAccountDao.updateData(thirdPartyAccount);
        } else {
            logger.info("子账号解绑解除");
            //如果是子账号的话，只是取消分配
            Update update = new Update.UpdateBuilder()
                    .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                    .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                    .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.getName(), userId)
                    .buildUpdate();
            thirdPartyAccountHrDao.update(update);
        }
    }

    /*
     *  查询第三方平台的账户,以及该帐号的分配人
     */
    public ThirdPartyAccountInfo getThridAccount(int accountId) {
        HrThirdPartyAccountDO thirdPartyAccount = thirdPartyAccountDao.getAccountById(accountId);

        if (thirdPartyAccount == null || thirdPartyAccount.getBinding() == 0) {
            return null;
        }

        ThirdPartyAccountInfo accountInfo = new ThirdPartyAccountInfo();
        accountInfo.setChannel(thirdPartyAccount.getChannel());
        accountInfo.setBound(thirdPartyAccount.getBinding());
        accountInfo.setMembername(thirdPartyAccount.getMembername());
        accountInfo.setUsername(thirdPartyAccount.getUsername());
        accountInfo.setCompany_id(thirdPartyAccount.getCompanyId());
        accountInfo.setSync_time(thirdPartyAccount.getSyncTime());
        accountInfo.setRemain_num(thirdPartyAccount.getRemainNum());
        accountInfo.setRemain_profile_num(thirdPartyAccount.getRemainProfileNum());
        accountInfo.setHrs(new ArrayList<>());

        //找到和该帐号绑定的HR
        List<HrThirdPartyAccountHrDO> binders = thirdPartyAccountHrDao.getBinders(thirdPartyAccount.getId());

        if (binders == null || binders.size() == 0) {
            return accountInfo;
        }

        Set<Integer> hrIds = binders.stream().map(accountHr -> accountHr.getHrAccountId()).collect(Collectors.toSet());

        Query query = new Query.QueryBuilder()
                .where(new Condition(UserHrAccount.USER_HR_ACCOUNT.ID.getName(), hrIds, ValueOp.IN))
                .and(UserHrAccount.USER_HR_ACCOUNT.ACTIVATION.getName(), 1)
                .and(UserHrAccount.USER_HR_ACCOUNT.DISABLE.getName(), 1).buildQuery();
        List<UserHrAccountDO> hrs = hrAccountDao.getDatas(query);
        if (hrs == null || hrs.size() == 0) {
            return accountInfo;
        }

        Map<Integer, UserHrAccountDO> hrMaps = hrs.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));

        ThirdPartyAccountHrInfo hrInfo;
        UserHrAccountDO hr;
        for (HrThirdPartyAccountHrDO binder : binders) {
            hr = hrMaps.get(binder.getHrAccountId());
            if (hr == null) continue;
            hrInfo = new ThirdPartyAccountHrInfo();
            hrInfo.setUsername(hr.getUsername());
            hrInfo.setMobile(hr.getMobile());
            hrInfo.setId(hr.getId());
            accountInfo.getHrs().add(hrInfo);
        }
        return accountInfo;
    }

    /**
     * 取消分配HR给这些HR
     *
     * @param thirdPartyAccount
     * @param hrIds
     */
    public void cancelDispath(HrThirdPartyAccountDO thirdPartyAccount, List<Integer> hrIds) {
        logger.info("第三方帐号取消分配:{}:{}", thirdPartyAccount.getId(), hrIds);
        if (hrIds == null || hrIds.size() == 0) return;
        Update update = new Update.UpdateBuilder()
                .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                .where(new Condition(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.getName(), hrIds, ValueOp.IN))
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), thirdPartyAccount.getId())
                .buildUpdate();
        thirdPartyAccountHrDao.update(update);
    }

    /**
     * 分配给这些HR
     *
     * @param thirdPartyAccount
     * @param hrIds
     */
    public void dispathTo(HrThirdPartyAccountDO thirdPartyAccount, List<Integer> hrIds) {
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

    /**
     * 第三方帐号分配
     *
     * @param accountId
     * @param hrIds
     */
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) {

        logger.info("第三方帐号重新分配:{}:{}", accountId, hrIds);
        HrThirdPartyAccountDO thirdPartyAccount = thirdPartyAccountDao.getAccountById(accountId);

        if (thirdPartyAccount == null || thirdPartyAccount.getBinding() == 0) {
            throw new CommonException(-1, "无效的帐号");
        }

        //绑定中，密码错误以及程序绑定失败的话不允许分配
        if (thirdPartyAccount.getBinding() == 2 || thirdPartyAccount.getBinding() == 6) {
            throw new CommonException(-1, "帐号正在同步中,无法分配，请稍后再试！");
        }

        if (thirdPartyAccount.getBinding() == 4) {
            throw new CommonException(-1, "用户名密码错误,无法分配，请稍后再试！");
        }

        //检查这些Hr是否绑定的其它相同渠道的帐号
        Query query = new Query.QueryBuilder()
                .where(new Condition(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.getName(), hrIds, ValueOp.IN))
                .and(new Condition(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId, ValueOp.NEQ))
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.CHANNEL.getName(), thirdPartyAccount.getChannel())
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 1)
                .buildQuery();
        List<HrThirdPartyAccountHrDO> otherBinders = thirdPartyAccountHrDao.getDatas(query);
        if (otherBinders != null && otherBinders.size() > 0) {
            //检查其它帐号是否有效
            Set<Integer> accountIds = otherBinders.stream().map(item -> item.getThirdPartyAccountId()).collect(Collectors.toSet());
            query = new Query.QueryBuilder()
                    .where(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.getName(), accountIds, ValueOp.IN))
                    .and(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING.getName(), 0, ValueOp.NEQ))
                    .buildQuery();
            List<HrThirdPartyAccountDO> otherAccounts = thirdPartyAccountDao.getDatas(query);
            if (otherAccounts != null && otherAccounts.size() > 0) {
                throw new CommonException(-1, "存在用户已经分配给其它相同渠道，请刷新页面！");
            } else {
                //错误的数据进行修复,将这些用户与无效的帐号的关联关系解除
                Set<Integer> relationIds = otherBinders.stream().map(item -> item.getId()).collect(Collectors.toSet());
                logger.info("第三方帐号关联关系修复:{}", relationIds);
                Update update = new Update.UpdateBuilder()
                        .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                        .where(new Condition(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.ID.getName(), relationIds, ValueOp.IN))
                        .buildUpdate();
                thirdPartyAccountHrDao.update(update);
            }
        }

        //第三方账号
        if (hrIds == null || hrIds.size() == 0) {
            //取消所有hr与该账号的关联
            Update update = new Update.UpdateBuilder()
                    .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                    .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                    .buildUpdate();
            thirdPartyAccountHrDao.update(update);
        } else {
            //获取原来该帐号绑定的所有
            List<HrThirdPartyAccountHrDO> binders = thirdPartyAccountHrDao.getBinders(thirdPartyAccount.getId());

            if (binders == null || binders.size() == 0) {
                dispathTo(thirdPartyAccount, hrIds);
                return getThridAccount(accountId);
            }

            Set<Integer> binderIds = new HashSet<>();
            binders.stream().map(item -> item.getHrAccountId()).collect(Collectors.toSet());

            //新分配的hr
            List<Integer> newHrIds = new ArrayList<>();
            //取消分配的hr
            List<Integer> canceledHrIds = new ArrayList<>();

            for (Integer hrId : hrIds) {
                if (!binderIds.contains(hrId)) {
                    newHrIds.add(hrId);
                }
            }

            dispathTo(thirdPartyAccount, newHrIds);

            for (Integer hrId : binderIds) {
                if (!hrIds.contains(hrId)) {
                    canceledHrIds.add(hrId);
                }
            }

            cancelDispath(thirdPartyAccount, canceledHrIds);
        }
        return getThridAccount(accountId);
    }
}
