package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountHrRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountHrDO;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public int upsertResource(HrThirdPartyAccountRecord record) {
        logger.info("HRThirdPartyAccountDao upsertResource");
        logger.info("HRThirdPartyAccountDao upsertResource channel:{}, company_id:{}", record.getChannel(), record.getCompanyId());
        logger.info("HRThirdPartyAccountDao upsertResource record:{}", record);
        int count = create.execute(UPSERT_SQL, record.getChannel(), record.getUsername(), record.getPassword(),
                record.getMembername(), record.getBinding(), record.getCompanyId().intValue(),
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
            dbrecord.setMembername(record.getMembername());
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
            record.setMembername(account.getMembername());
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

    public HrThirdPartyAccountDO getThirdPartyAccountByUserId(int user_id, int channel) throws TException {
        logger.info("getThirdPartyAccountByUserId:user_id{},channel:{}", user_id, channel);
        Query query = new Query.QueryBuilder().where("hr_account_id", user_id).and("status", 1).and("channel", channel).buildQuery();
        HrThirdPartyAccountHrDO hrThirdPartyAccountHr = thirdPartyAccountHrDao.getData(query);
        if (hrThirdPartyAccountHr != null) {
            query = new Query.QueryBuilder()
                    .where("id", hrThirdPartyAccountHr.getThirdPartyAccountId())
                    .and(new Condition("binding", 0, ValueOp.NEQ))
                    .buildQuery();
            return getData(query);
        }
        return null;
    }

    public List<HrThirdPartyAccountDO> getThirdPartyAccountsByUserId(int user_id) {
        logger.info("getThirdPartyAccountsByUserId:" + user_id);
        Query query = new Query.QueryBuilder().select("third_party_account_id").where("hr_account_id", user_id).and("status", 1).buildQuery();

        //所有绑定的第三方帐号的ID的合集
        List<HrThirdPartyAccountHrDO> thirdPartyAccounts = thirdPartyAccountHrDao.getDatas(query);

        if (thirdPartyAccounts == null || thirdPartyAccounts.size() == 0) {
            return new ArrayList<>();
        }

        Set<Integer> thirdPartyAccountIds = new HashSet<>();

        for (HrThirdPartyAccountHrDO thirdPartyAccountHrDO : thirdPartyAccounts) {
            thirdPartyAccountIds.add(thirdPartyAccountHrDO.getThirdPartyAccountId());
        }

        Short[] valiableBinding = new Short[]{(short) 1, (short) 3, (short) 7};//有效的状态:已绑定，刷新中，刷新程序错误

        query = new Query.QueryBuilder()
                .where(new Condition("id", thirdPartyAccountIds, ValueOp.IN))
                .and(new Condition("binding", Arrays.asList(valiableBinding), ValueOp.IN))
                .buildQuery();

        List<HrThirdPartyAccountDO> hrThirdPartyAccountDOS = getDatas(query);

        return hrThirdPartyAccountDOS == null ? new ArrayList<>() : hrThirdPartyAccountDOS;
    }


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
