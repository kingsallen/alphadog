package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountHrRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.BindingStatus;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 第三方帐号数据访问层
 */
@Repository
public class HRThirdPartyAccountDao extends JooqCrudImpl<HrThirdPartyAccountDO, HrThirdPartyAccountRecord> {

    @Autowired
    HRThirdPartyAccountHrDao thirdPartyAccountHrDao;

    public HRThirdPartyAccountDao() {
        super(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT, HrThirdPartyAccountDO.class);
    }

    private static final String UPSERT_SQL = "insert into hrdb.hr_third_party_account(channel, username, password, membername, binding, company_id, remain_num, sync_time) select ?, ?, ?, ?, ?, ?, ?, ? from DUAL where not exists(select id from hrdb.hr_third_party_account where channel = ? and company_id = ?)";

    public HRThirdPartyAccountDao(TableImpl<HrThirdPartyAccountRecord> table, Class<HrThirdPartyAccountDO> hrThirdPartyAccountDOClass) {
        super(table, hrThirdPartyAccountDOClass);
    }

    public HrThirdPartyAccountDO getAccountById(int accountId) {
        Query query = new Query.QueryBuilder().where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.getName(), accountId).buildQuery();
        return getData(query);
    }

    public List<HrThirdPartyAccountDO> getAccountsById(List<Integer> accountIds){
        Query query = new Query.QueryBuilder()
                .where(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.getName(), accountIds, ValueOp.IN))
                .and(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING.getName(), 0, ValueOp.NEQ))
                .buildQuery();
        return getDatas(query);
    }

    public HrThirdPartyAccountDO getEQThirdPartyAccount(HrThirdPartyAccountDO thirdPartyAccount){
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("company_id", thirdPartyAccount.getCompanyId());
        qu.and("channel", thirdPartyAccount.getChannel());
        qu.and("username", thirdPartyAccount.getUsername());
        qu.and(new Condition("binding", 0, ValueOp.NEQ));//有效的状态

        List<HrThirdPartyAccountDO> datas = getDatas(qu.buildQuery());

        HrThirdPartyAccountDO data = null;

        for (HrThirdPartyAccountDO d : datas) {
            ///数据库中username是不区分大小写的，如果大小写不同，那么认为不是一个账号
            if (d.getUsername().equals(thirdPartyAccount.getUsername())) {
                data = d;
                break;
            }
        }

        return data;
    }

    public int upsertResource(HrThirdPartyAccountRecord record) {
        logger.info("HRThirdPartyAccountDao upsertResource");
        logger.info("HRThirdPartyAccountDao upsertResource channel:{}, company_id:{}", record.getChannel(), record.getCompanyId());
        logger.info("HRThirdPartyAccountDao upsertResource record:{}", record);
        int count = create.execute(UPSERT_SQL, record.getChannel(), record.getUsername(), record.getPassword(),
                record.getBinding(), record.getCompanyId().intValue(),
                record.getRemainNum().intValue(), record.getSyncTime(), record.getChannel(),
                record.getCompanyId().intValue());
        logger.info("HRThirdPartyAccountDao count:{}", count);

        if (count == 0) {
            HrThirdPartyAccountRecord dbrecord = create.selectFrom(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                    .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.COMPANY_ID.equal(record.getCompanyId())
                            .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.equal(record.getChannel())))
                    .fetchOne();
            dbrecord.setUsername(record.getUsername());
            dbrecord.setPassword(record.getPassword());
            dbrecord.setBinding(record.getBinding());
            dbrecord.setRemainNum(record.getRemainNum());
            dbrecord.setSyncTime(record.getSyncTime());
            count = dbrecord.update();
        }
        return count;
    }

    public Response upsertThirdPartyAccount(HrThirdPartyAccountDO account) {
        try {
            logger.info("upsertThirdPartyAccount");
            logger.info("upsertThirdPartyAccount account:{}", account);
            HrThirdPartyAccountRecord record = new HrThirdPartyAccountRecord();
            record.setBinding(account.getBinding());
            record.setChannel(account.getChannel());
            record.setCompanyId(account.getCompanyId());
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCreateTime(now);
            record.setPassword(account.getPassword());
            record.setRemainNum((int) (account.getRemainNum()));
            record.setSyncTime(now);
            record.setBinding((short) 1);
            record.setUsername(account.getUsername());
            logger.info("upsertThirdPartyAccount record:{}", record);
            logger.info("upsertThirdPartyAccount channel:{}, company_id:{}", account.getChannel(), account.getCompanyId());
            int count = upsertResource(record);
            logger.info("upsertThirdPartyAccount count:{}", count);
            if (count == 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("remain_num", account.getRemainNum());
            DateTime dt = new DateTime(now.getTime());
            map.put("sync_time", dt.toString("yyyy-MM-dd HH:mm:ss"));
            logger.info("upsertThirdPartyAccount result:{}", map);
            return ResponseUtils.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {

        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public HrThirdPartyAccountDO addThirdPartyAccount(int userId, HrThirdPartyAccountDO hrThirdPartyAccount) throws TException {
        logger.info("添加第三方账号到数据库：" + userId + ":" + BeanUtils.convertStructToJSON(hrThirdPartyAccount));
        //添加第三方账号
        HrThirdPartyAccountDO thirdPartyAccount = addData(hrThirdPartyAccount);
        //HR关联到第三方账号
        if (userId > 0) {
            dispatchAccountToHr(hrThirdPartyAccount, userId);
        }
        return thirdPartyAccount;
    }


    public int dispatchAccountToHr(HrThirdPartyAccountDO hrThirdPartyAccount, int hrId) {
        logger.info("HR关联到第三方账号：" + hrId + ":" + hrThirdPartyAccount.getId());
        if (hrId == 0 || hrThirdPartyAccount.getId() == 0) {
            return 0;
        }
        HrThirdPartyAccountHrRecord hrThirdPartyAccountHrRecord = new HrThirdPartyAccountHrRecord();
        hrThirdPartyAccountHrRecord.setChannel(hrThirdPartyAccount.getChannel());
        hrThirdPartyAccountHrRecord.setHrAccountId(hrId);
        hrThirdPartyAccountHrRecord.setThirdPartyAccountId(hrThirdPartyAccount.getId());
        create.attach(hrThirdPartyAccountHrRecord);
        return hrThirdPartyAccountHrRecord.insert();
    }

    public HrThirdPartyAccountDO getThirdPartyAccountByUserId(int user_id, int channel) {
        logger.info("getThirdPartyAccountByUserId:user_id{},channel:{}", user_id, channel);
        Query query = new Query.QueryBuilder()
                .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.getName(), user_id)
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 1)
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.CHANNEL.getName(), channel)
                .buildQuery();
        List<HrThirdPartyAccountHrDO> hrThirdPartyAccountHrDOList = thirdPartyAccountHrDao.getDatas(query);
        if (hrThirdPartyAccountHrDOList != null && hrThirdPartyAccountHrDOList.size() > 0) {

            List<Integer> thirdPartyAccountIdList = hrThirdPartyAccountHrDOList
                    .stream()
                    .map(hrThirdPartyAccountHrDO -> hrThirdPartyAccountHrDO.getThirdPartyAccountId())
                    .collect(Collectors.toList());

            logger.info("getThirdPartyAccountByUserId thirdPartyAccountIdList:{}", thirdPartyAccountIdList);

            Condition condition = new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.getName(), thirdPartyAccountIdList, ValueOp.IN);
            query = new Query.QueryBuilder()
                    .where(condition)
                    .and(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING.getName(), BindingStatus.UNBIND.getValue(), ValueOp.NEQ))
                    .buildQuery();
            HrThirdPartyAccountDO accountDO =  getData(query);
            if (accountDO != null) {
                logger.info("getThirdPartyAccountByUserId id: id{}, channel:{}", accountDO.getId(), channel);
            } else {
                logger.info("getThirdPartyAccountByUserId accountDO is null");
            }
            return accountDO;
        }
        return null;
    }

    public List<HrThirdPartyAccountDO> getThirdPartyAccountsByUserId(List<Integer> user_ids) {
        logger.info("getThirdPartyAccountsByUserId:" + user_ids);
        Query query = new Query.QueryBuilder()
                .where(new Condition(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.getName(), user_ids,ValueOp.IN))
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.getName(), 1)
                .buildQuery();

        //所有绑定的第三方帐号的ID的合集
        List<HrThirdPartyAccountHrDO> thirdPartyAccounts = thirdPartyAccountHrDao.getDatas(query);

        if (thirdPartyAccounts == null || thirdPartyAccounts.size() == 0) {
            return new ArrayList<>();
        }

        Set<Integer> thirdPartyAccountIds = new HashSet<>();

        for (HrThirdPartyAccountHrDO thirdPartyAccountHrDO : thirdPartyAccounts) {
            thirdPartyAccountIds.add(thirdPartyAccountHrDO.getThirdPartyAccountId());
        }

        query = new Query.QueryBuilder()
                .where(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.getName(), thirdPartyAccountIds, ValueOp.IN))
                .and(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING.getName(), BindingStatus.UNBIND.getValue(), ValueOp.NEQ))
                .buildQuery();

        List<HrThirdPartyAccountDO> hrThirdPartyAccountDOS = getDatas(query);

        return hrThirdPartyAccountDOS == null ? new ArrayList<>() : hrThirdPartyAccountDOS;
    }

    private FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取同一渠道的第三方账号信息
     * @param
     * @author  cjm
     * @date  2018/6/1
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<HrThirdPartyAccountDO> getBoundThirdPartyAccountDO(int channel){
        Query query = new Query.QueryBuilder()
                .where(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.getName(), (short)channel))
                .and(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING.getName(), BindingStatus.BOUND.getValue()))
                .and(new Condition(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT2.getName(), "", ValueOp.NEQ))
                .buildQuery();
        return getDatas(query);
    }

    /**
     * 获取同一渠道的为绑定的第三方账号信息
     * @param
     * @author  cjm
     * @date  2018/6/1
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<HrThirdPartyAccountDO> getUnBindThirdPartyAccountDO(int channel){
        return create.selectFrom(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.eq((short)channel))
                .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING.eq((short)BindingStatus.BOUND.getValue()))
                .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT2.eq(""))
                .fetchInto(HrThirdPartyAccountDO.class);
    }

    public int updateBindToken(String liepinToken, Integer liepinUserId, Integer hrThirdAccountId) {
        return create.update(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                .set(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT, String.valueOf(liepinUserId))
                .set(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.EXT2, liepinToken)
                .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.eq(hrThirdAccountId))
                .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.eq((short)2))
                .execute();
    }

    /**
     * 获取需要刷新token的账号信息
     * @param
     * @author  cjm
     * @date  2018/6/15
     * @return
     */
    public List<HrThirdPartyAccountDO> getRefreshAccounts(int channel, int bindState) {
        return create.selectFrom(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.eq((short)channel))
                .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING.eq((short)bindState))
                .fetchInto(HrThirdPartyAccountDO.class);
    }

//    public void updateBindState(int hrAccountId, int state) {
//        create.update(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
//                .set(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING, (short)state)
//                .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.eq(hrAccountId))
//                .execute();
//    }
}
