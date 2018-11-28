package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountHrDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.*;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Update;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.AbstractBindProcessor;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.BindProcessorFactory;
import com.moseeker.useraccounts.service.thirdpartyaccount.info.ThirdPartyAcountEntity;
import com.moseeker.entity.pojos.ThirdPartyAccountExt;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountHrInfo;
import com.moseeker.thrift.gen.useraccounts.struct.ThirdPartyAccountInfo;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.pojo.BindResult;
import com.moseeker.useraccounts.service.impl.UserHrAccountService;
import com.moseeker.useraccounts.service.thirdpartyaccount.operation.BindOperation;
import com.moseeker.useraccounts.service.thirdpartyaccount.util.BindCheck;
import com.moseeker.useraccounts.service.thirdpartyaccount.base.ThirdPartyAccountContext;
import com.moseeker.useraccounts.service.thirdpartyaccount.util.BindUtil;
import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private HrCompanyDao hrCompanyDao;

    @Autowired
    EmailNotification emailNotification;

    @Autowired
    ThirdPartyAcountEntity thridPartyAcountEntity;

    @Autowired
    ThirdPartyAccountContext stateContext;

    @Autowired
    BindOperation bindOperation;

    @Autowired
    UserHrAccountService userHrAccountService;

    @Autowired
    BindUtil bindUtil;

    @Autowired
    BindCheck bindCheck;

    @Autowired
    BindProcessorFactory bindProcessorFactory;

    /**
     * 第三方账号绑定
     *
     * @param hrId
     * @param account
     * @return
     */
    public HrThirdPartyAccountDO bindThirdAccount(int hrId, HrThirdPartyAccountDO account,boolean sync) throws Exception {
        UserHrAccountDO hrAccount = userHrAccountService.requiresNotNullAccount(hrId);
        account.setCompanyId(hrAccount.getCompanyId());
        // 前置处理，用于绑定前各渠道的特殊处理
        account = bindProcessorFactory.getBindProcessorByChannel(account.getChannel()).postProcessorBeforeBind(hrId, account);

        HrThirdPartyAccountDO oldAccount = thirdPartyAccountDao.getEQThirdPartyAccount(account);

        if(BindCheck.isSubUserHrAccount(hrAccount)){
            if(bindOperation.isAlreadyBindOtherAccount(hrAccount.getId(),account.getChannel())) {
                //已经绑定该渠道第三方账号，并且不是主账号，那么不允许绑定
                logger.info("子账号{}，并且已经绑定过该渠道第三方帐号", hrAccount);
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_BINDING_LIMIT);
            }
            if (BindCheck.isNotNullAccount(oldAccount) && oldAccount.getBinding()!=BindingStatus.UNDISPATCH.getValue()) {   //状态为9的数据是可以重新绑定的,因为9和0一样不显示
                //公司下已经有人绑定了这个第三方账号，则这个公司谁都不能再绑定这个账号了
                logger.info("子账号不能重新绑定第三方账号");
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.HRACCOUNT_ALREADY_BOUND);
            }
        }

        bindCheck.alreadyInRedis(account);  //验证是否正在绑定

        if ( BindCheck.isNotNullAccount(oldAccount) ) {
            logger.info("重新绑定:{}", oldAccount.getId());
            account = bindOperation.reuseOldThirdPartyAccount(account,oldAccount);
        }else{
            logger.info("账号{}之前没有被绑定过，可以在在数据库初始化一条新账号", account);
            account = bindOperation.useNewThirdPartyAccount(account);
        }

        try{
            HrThirdPartyAccountDO result = stateContext.bind(hrId,account);
            if (result.getBinding() != 100) {
                bindUtil.removeCache(account);
            }
            return result;
        } catch (Exception e) {
            bindUtil.removeCache(account);
            throw e;
        }
    }

    /**
     * 猎聘确认发送短信验证码
     *
     * @param accountId
     * @param hrId
     * @param confirm
     * @return
     */
    public HrThirdPartyAccountDO bindConfirm(int hrId, int accountId, boolean confirm) throws Exception {
        return stateContext.bindConfirm( hrId, accountId,confirm);
    }

    /**
     * 猎聘绑定确认发送验证码
     *
     * @param accountId
     * @param hrId
     * @param code
     * @return
     */
    public HrThirdPartyAccountDO bindMessage(int hrId, int accountId, String code) throws Exception {
        return stateContext.bindMessage(hrId,accountId,code);
    }

    /**
     * 第三方帐号分配
     *
     * @param accountId
     * @param hrIds
     */
    @CounterIface
    public ThirdPartyAccountInfo dispatch(int accountId, List<Integer> hrIds) throws Exception {
        return stateContext.dispatch(accountId,hrIds);
    }

    /**
     * 账号解绑，首先先查询账号在第三方平台表中是否存在，存在就进行解绑
     *
     * @param accountId
     * @param userId
     */
    @CounterIface
    public void unbindingAccount(int accountId, int userId) {
        logger.info("帐号解绑,accountId:{},userId:{}", accountId, userId);
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
            thirdPartyAccountHrDao.invalidByThirdPartyAccountId(accountId);
            thirdPartyAccount.setBinding((short)BindingStatus.UNDISPATCH.getValue());
            //设置更新时间
            FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
            thirdPartyAccount.setUpdateTime(sdf.format(new Date()));

            thirdPartyAccountDao.updateData(thirdPartyAccount);
        } else {

            //找到和该帐号绑定的HR
            List<HrThirdPartyAccountHrDO> binders = thirdPartyAccountHrDao.getBinders(thirdPartyAccount.getId());

            if (binders == null || binders.size() == 0) {
                logger.warn("子帐号解绑时和该账号没有关联关系,accountId:{},userId:{}", accountId, userId);
                return;
            }

            logger.info("子账号解绑解除");
            //如果是子账号的话，只是取消分配
            Update update = new Update.UpdateBuilder()
                    .set(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 0)
                    .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                    .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.getName(), userId)
                    .buildUpdate();
            int updateResult = thirdPartyAccountHrDao.update(update);

            if (updateResult == 0) {
                logger.warn("解绑时更新失败");
                return;
            }
        }
    }

    /**
     * 解除账号绑定关系，删除第三方账号
     * @param accountId 第三方账号ID
     */
    public Response deleteThirdPartyAccount(int accountId,int userId) throws Exception {
        logger.info("帐号删除,accountId:{}", accountId);

        HrThirdPartyAccountDO thirdPartyAccount = thirdPartyAccountDao.getAccountById(accountId);

        if (thirdPartyAccount == null || thirdPartyAccount.getBinding() == 0) {
            throw new CommonException(-1, "无效的第三方帐号");
        }

        UserHrAccountDO hrAccount = hrAccountDao.getValidAccount(userId);

        if (hrAccount == null) {
            throw new CommonException(-1, "无效的HR帐号");
        }

        stateContext.delete(thirdPartyAccount,hrAccount);

        return RespnoseUtil.SUCCESS.toResponse();
    }

    /**
     * 账号绑定结果处理程序
     * @param bindResult 账号绑定结果
     * @throws CommonException 业务异常
     */
    public void bingResultHandler(BindResult bindResult) throws CommonException {
        HrThirdPartyAccountDO accountDO = thirdPartyAccountDao.getAccountById(bindResult.getData().getAccountId());
        if (accountDO == null) {
            throw UserAccountException.THIRD_PARTY_ACCOUNT_NOTEXIST;
        }

        boolean changed = false;
        if (bindResult != null && bindResult.getStatus() != 0) {
            emailNotification.sendFailureMail(emailNotification.getMails(), accountDO);
            accountDO.setErrorMessage(bindResult.getMessage());
            changed = true;
            //发邮件cs
            //sendFailureMail(mails, hrThirdPartyAccount, extras);
        } else {
            if (accountDO.getBinding() != BindingStatus.BOUND.getValue()) {
                accountDO.setBinding((short) BindingStatus.BOUND.getValue());
                changed = true;
            }
        }
        if (changed)
            thirdPartyAccountDao.updateData(accountDO);
    }

    /**
     * 第三方账号的预填项信息
     * @param accountExt 账号绑定结果
     * @throws CommonException 业务异常
     */
    @Transactional
    public void thirdPartyAccountExtHandler(ThirdPartyAccountExt accountExt) throws CommonException {
        HrThirdPartyAccountDO accountDO = thirdPartyAccountDao.getAccountById(accountExt.getData().getAccountId());
        logger.info("获取第三方账号相关信息账号ID："+accountExt.getData().getAccountId()+",状态："+accountExt.getStatus());
        if (accountDO == null) {
            throw UserAccountException.THIRD_PARTY_ACCOUNT_NOTEXIST;
        }

        if (accountExt.getStatus() != 0) {

            HrCompanyDO companyDO = hrCompanyDao.getCompanyById(accountDO.getCompanyId());

            if (accountExt.getData().getOperationType() == 1) {
                emailNotification.sendThirdPartyAccountExtHandlerFailureMail(emailNotification.getMails(),accountDO, accountExt.getMessage(),companyDO);
            } else {
                emailNotification.sendThirdPartyAccountExtHandlerFailureMail(emailNotification.getDevMails(),accountDO, "职位同步之后获取第三方渠道扩展信息失败！",companyDO);
            }
        } else {
            thridPartyAcountEntity.saveAccountExt(accountExt.getData(), accountDO);
            logger.info("第三方账号相关信息绑定处理完成，准备更新"+accountDO.getBinding()+"为1");
            if (accountDO.getBinding() != BindingStatus.BOUND.getValue()) {
                accountDO.setBinding((short) BindingStatus.BOUND.getValue());
                logger.info(accountDO.getBinding()+"更新成功，状态为"+accountDO.getBinding());
            }

            // 职位同步时是否需要填写公司，暂时只有智联要
            if(accountExt.getData().isHas_company()){
                accountDO.setSyncRequireCompany(ThirdPartyAccountConf.REQUIRE_COMPANY.ON);
            } else{
                accountDO.setSyncRequireCompany(ThirdPartyAccountConf.REQUIRE_COMPANY.OFF);
            }

            // 职位同步时是否需要填写部门，暂时只有智联要
            if(accountExt.getData().isHas_departments()){
                accountDO.setSyncRequireDepartment(ThirdPartyAccountConf.REQUIRE_DEPARTMENT.ON);
            } else{
                accountDO.setSyncRequireDepartment(ThirdPartyAccountConf.REQUIRE_DEPARTMENT.OFF);
            }

            thirdPartyAccountDao.updateData(accountDO);
        }
    }

    /**
     *  查询第三方平台的账户,以及该帐号的分配人
     */
    public ThirdPartyAccountInfo getThridAccount(int accountId) {
        HrThirdPartyAccountDO thirdPartyAccount = thirdPartyAccountDao.getAccountById(accountId);

        if (thirdPartyAccount == null || thirdPartyAccount.getBinding() == 0) {
            return null;
        }

        return getThridAccount(thirdPartyAccount);
    }

    private ThirdPartyAccountInfo getThridAccount(HrThirdPartyAccountDO thirdPartyAccount) {

        if (thirdPartyAccount == null) {
            return null;
        }

        ThirdPartyAccountInfo accountInfo = new ThirdPartyAccountInfo();
        accountInfo.setId(thirdPartyAccount.getId());
        accountInfo.setChannel(thirdPartyAccount.getChannel());
        accountInfo.setBound(thirdPartyAccount.getBinding());
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
     * 获取已经在猎聘绑定的hr第三方账号信息，主要是为了提供猎聘token
     * @param
     * @author  cjm
     * @date  2018/5/30
     * @return
     */
    public Response getBoundThirdPartyAccountDO(int channel) throws Exception{
        List<HrThirdPartyAccountDO> hrThirdPartyAccountDOList = thirdPartyAccountDao.getBoundThirdPartyAccountDO(channel);
        if(hrThirdPartyAccountDOList == null || hrThirdPartyAccountDOList.size() == 0){
            return ResponseUtils.fail(ConstantErrorCodeMessage.THIRD_PARTY_ACCOUNT_NOT_EXIST);
        }
        return ResponseUtils.success(hrThirdPartyAccountDOList);
    }


    /**
     * 获取已经在仟寻绑定，但是未在猎聘绑定的第三方信息
     * @param
     * @author  cjm
     * @date  2018/5/30
     * @return
     */
    public List<HrThirdPartyAccountDO> getUnBindThirdPartyAccountDO(int channel) throws Exception{
        List<HrThirdPartyAccountDO> hrThirdPartyAccountDOList = thirdPartyAccountDao.getUnBindThirdPartyAccountDO(channel);
        if(hrThirdPartyAccountDOList == null){
            return new ArrayList<>();
        }
        return hrThirdPartyAccountDOList;
    }

    public String bindLiepinUserAccount(String liepinToken, Integer liepinUserId, Integer hrThirdAccountId) throws Exception {
        HrThirdPartyAccountDO hrThirdPartyAccountDO = thirdPartyAccountDao.getAccountById(hrThirdAccountId);
        if(hrThirdPartyAccountDO == null){
            return "failed";
        }
        if(hrThirdPartyAccountDO.getChannel() != 2){
            return "failed";
        }
        int row = thirdPartyAccountDao.updateBindToken(liepinToken, liepinUserId, hrThirdAccountId);
        if(row != 1){
            return "failed";
        }
        return "success";
    }
}
