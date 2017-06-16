package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountHrRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class HRThirdPartyAccountDao extends JooqCrudImpl<HrThirdPartyAccountDO, HrThirdPartyAccountRecord> {

    public HRThirdPartyAccountDao() {
        super(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT, HrThirdPartyAccountDO.class);
    }

    private static final String UPSERT_SQL = "insert into hrdb.hr_third_party_account(channel, username, password, membername, binding, company_id, remain_num, sync_time) select ?, ?, ?, ?, ?, ?, ?, ? from DUAL where not exists(select id from hrdb.hr_third_party_account where channel = ? and company_id = ?)";

    public HRThirdPartyAccountDao(TableImpl<HrThirdPartyAccountRecord> table, Class<HrThirdPartyAccountDO> hrThirdPartyAccountDOClass) {
        super(table, hrThirdPartyAccountDOClass);
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

    public List<HrThirdPartyAccountRecord> getThirdPartyBindingAccounts(Query query) {
        return getRecords(query);
    }

    /**
     * 修改第三方帐号信息
     *
     * @param account
     * @return
     */
    public int updatePartyAccountByCompanyIdChannel(ThirdPartAccountData account) {
        logger.info("updatePartyAccountByCompanyIdChannel");
        int count = 0;
        try {
            Date date = sdf.parse(account.getSync_time());
            HrThirdPartyAccountRecord record = create.selectFrom(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                    .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.COMPANY_ID
                            .equal((int) (account.getCompany_id())))
                    .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.equal((short) account.getChannel()))
                    .fetchOne();
            if (record != null) {
                logger.info("HrThirdPartyAccount.id:{}", record.getId().intValue());
                logger.info("remainume:{}", account.getRemain_num());
                record.setSyncTime(new Timestamp(date.getTime()));
                record.setRemainNum((int) (account.getRemain_num()));
                count = record.update();
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return count;
    }

    public Response upsertThirdPartyAccount(HrThirdPartyAccountDO account) {
        try {
            logger.info("upsertThirdPartyAccount");
            logger.info("upsertThirdPartyAccount account:{}", account);
            HrThirdPartyAccountRecord record = new HrThirdPartyAccountRecord();
            record.setBinding((short) account.getBinding());
            record.setChannel((short) account.getChannel());
            record.setCompanyId((int) (account.getCompanyId()));
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
    public int addThirdPartyAccount(int userId, HrThirdPartyAccountRecord record) {
        logger.info("添加第三方账号到数据库：" + userId + ":" + record.getMembername());
        int count = 0;
        create.attach(record);
        //添加第三方账号
        count = record.insert();
        //HR关联到第三方账号
        if (userId > 0) {
            logger.info("HR关联到第三方账号：" + userId + ":" + record.getMembername());
            HrThirdPartyAccountHrRecord hrThirdPartyAccountHrRecord = new HrThirdPartyAccountHrRecord();
            hrThirdPartyAccountHrRecord.setChannel(record.getChannel());
            hrThirdPartyAccountHrRecord.setHrAccountId(userId);
            hrThirdPartyAccountHrRecord.setThirdPartyAccountId(record.getId());
            create.attach(hrThirdPartyAccountHrRecord);
            count = hrThirdPartyAccountHrRecord.insert();
        }
        return count;
    }

    public HrThirdPartyAccountDO getThirdPartyAccountByUserId(int user_id, int channel) throws TException {
        try {
            logger.info("getThirdPartyAccountByUserId:user_id{},channel:{}", user_id, channel);
            Query query = new Query.QueryBuilder().where("hr_account_id",user_id).and("status",1).buildQuery();

            List<Integer> thirdPartyAccounts = create.select(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID).from(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR)
                    .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.eq(user_id))
                    .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.eq((byte) 1)).fetch(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID);

            if (thirdPartyAccounts != null && thirdPartyAccounts.size() > 0) {
                HrThirdPartyAccountDO data = create.select().from(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                        .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.in(thirdPartyAccounts))
                        .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.eq((short) channel))
                        .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.BINDING.eq((short) 1))
                        .fetchAnyInto(HrThirdPartyAccountDO.class);
                if (data != null) {
                    logger.info("getThirdPartyAccountByUserId:result:{}", data.getId());
                    return data;
                }
            }
            logger.info("getThirdPartyAccountByUserId:result:empty");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return new HrThirdPartyAccountDO();
    }

    public List<ThirdPartAccountData> getThirdPartyAccountsByUserId(int user_id) {
        logger.info("getThirdPartyAccountsByUserId:" + user_id);
        List<Integer> thirdPartyAccounts = create.select(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID).from(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR)
                .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.eq(user_id))
                .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.eq((byte) 1)).fetch(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID);

        if (thirdPartyAccounts != null && thirdPartyAccounts.size() > 0) {
            List<ThirdPartAccountData> datas = create.select().from(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                    .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.in(thirdPartyAccounts))
                    .fetchInto(ThirdPartAccountData.class);
            logger.info("getThirdPartyAccountsByUserId:size" + datas.size());
            return datas;
        }
        return new ArrayList<>();
    }


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
