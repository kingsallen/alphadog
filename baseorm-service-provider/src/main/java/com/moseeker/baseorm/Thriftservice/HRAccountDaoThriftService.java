package com.moseeker.baseorm.Thriftservice;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHRAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccount;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyAccountHr;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.UserHrAccountDao.Iface;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 提供hr帐号表的单表操作
 * <p>Company: MoSeeker</P>
 * <p>date: Nov 8, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @author wjf
 */
@Service
public class HRAccountDaoThriftService implements Iface {

    private Logger logger = LoggerFactory.getLogger(ChannelType.class);

    @Autowired
    private UserHRAccountDao hraccountDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    @Autowired
    private HRThirdPartyPositionDao thirdPartyPositionDao;

    @Override
    public Response getAccount(CommonQuery query) throws TException {
        try {
            UserHrAccountRecord record = hraccountDao.getResource(query);
            if (record != null) {
                return ResponseUtils.success(record.intoMap());
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        } finally {
            //do nothing
        }
    }


    private void copy(ThirdPartAccountData data, HrThirdPartyAccountRecord record) {
        data.setId(record.getId());
        data.setBinding(record.getBinding());
        data.setChannel(record.getChannel());
        data.setCompany_id(record.getCompanyId().intValue());
        data.setMembername(record.getMembername());
        data.setUsername(record.getUsername());
        data.setPassword(record.getPassword());
        data.setRemain_num(record.getRemainNum().intValue());
        data.setRemain_num(record.getRemainNum().intValue());
        data.setSync_time(new DateTime(record.getSyncTime()).toString("yyyy-MM-dd"));
        data.setUsername(record.getUsername());
    }

    @Override
    public ThirdPartAccountData getThirdPartyAccount(CommonQuery query) throws TException {
        logger.info("getThirdPartyAccount");
        ThirdPartAccountData data = new ThirdPartAccountData();
        try {
            HrThirdPartyAccountRecord record = hrThirdPartyAccountDao.getResource(query);
            if (record != null) {
                copy(data, record);
            }
            logger.info("data:" + JSON.toJSONString(data));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return data;
    }

    @Override
    public List<ThirdPartAccountData> getThirdPartyAccountsByUserId(int user_id) throws TException {
        try {
            Connection conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            List<Integer> thirdPartyAccounts = create.select(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID).from(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR)
                    .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.eq(user_id))
                    .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.eq((byte) 1)).fetch(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID);

            if (thirdPartyAccounts != null && thirdPartyAccounts.size() > 0) {
                List<ThirdPartAccountData> datas = create.select().from(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                        .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.in(thirdPartyAccounts))
                        .fetchInto(ThirdPartAccountData.class);
                return datas;
            }

            return null;
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public ThirdPartAccountData getThirdPartyAccountByUserId(int user_id, int channel) throws TException {
        try {
            Connection conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            List<Integer> thirdPartyAccounts = create.select(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID).from(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR)
                    .where(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.HR_ACCOUNT_ID.eq(user_id))
                    .and(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.STATUS.eq((byte) 1)).fetch(HrThirdPartyAccountHr.HR_THIRD_PARTY_ACCOUNT_HR.THIRD_PARTY_ACCOUNT_ID);

            if (thirdPartyAccounts != null && thirdPartyAccounts.size() > 0) {
                ThirdPartAccountData data = create.select().from(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT)
                        .where(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.ID.in(thirdPartyAccounts))
                        .and(HrThirdPartyAccount.HR_THIRD_PARTY_ACCOUNT.CHANNEL.eq((short) channel))
                        .fetchOneInto(ThirdPartAccountData.class);
                return data;
            }

            return null;
        } catch (Exception e) {
            throw new TException(e);
        }
    }

    @Override
    public List<ThirdPartAccountData> getThirdPartyBindingAccounts(CommonQuery query) throws TException {
        List<ThirdPartAccountData> datas = new ArrayList<>();
        try {
            List<HrThirdPartyAccountRecord> records = hrThirdPartyAccountDao.getThirdPartyBindingAccounts(query);
            if (records != null && records.size() > 0) {
                records.forEach(r -> {
                    ThirdPartAccountData data = new ThirdPartAccountData();
                    copy(data, r);
                    datas.add(data);
                });
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return datas;
    }

    /**
     * 查询第三方职位
     */
    @Override
    public List<ThirdPartyPositionData> getThirdPartyPositions(CommonQuery query) throws TException {

        return thirdPartyPositionDao.getThirdPartyPositions(query);
    }

    @Override
    public Response upsertThirdPartyPositions(List<ThirdPartyPositionData> positions) throws TException {

        return thirdPartyPositionDao.upsertThirdPartyPositions(positions);
    }

    @Override
    public Response updatePartyAccountByCompanyIdChannel(ThirdPartAccountData account) throws TException {
        int count = hrThirdPartyAccountDao.updatePartyAccountByCompanyIdChannel(account);
        if (count > 0) {
            return ResponseUtils.success(null);
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
    }

    public void copyToRecord(BindAccountStruct account, HrThirdPartyAccountRecord record) {
        record.setBinding((short) account.getBinding());
        record.setChannel((short) account.getChannel());
        record.setCompanyId(UInteger.valueOf(account.getCompany_id()));
        record.setMembername(account.getMember_name());
        record.setPassword(account.getPassword());
        record.setRemainNum(UInteger.valueOf(account.getRemainNum()));
        record.setRemainProfileNum(account.getRemainProfileNum());
        record.setBinding((short) 1);
        record.setUsername(account.getUsername());
    }

    /**
     * 添加第三方账号
     *
     * @param userId
     * @param account
     * @return
     * @throws TException
     */
    @Override
    public Response addThirdPartyAccount(int userId, BindAccountStruct account) throws TException {
        try {
            HrThirdPartyAccountRecord record = new HrThirdPartyAccountRecord();
            copyToRecord(account, record);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCreateTime(now);
            record.setUpdateTime(now);
            record.setSyncTime(now);
            int count = hrThirdPartyAccountDao.addThirdPartyAccount(userId, record);
            if (count == 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("remain_num", account.getRemainNum());
            map.put("remain_profile_num", account.getRemainProfileNum());
            DateTime dt = new DateTime(now.getTime());
            map.put("sync_time", dt.toString("yyyy-MM-dd HH:mm:ss"));
            return ResponseUtils.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {

        }
    }

    /**
     * 更新第三方账号
     *
     * @param accountId 第三方账号ID
     * @param account
     * @return
     * @throws TException
     */
    @Override
    public Response updateThirdPartyAccount(int accountId, BindAccountStruct account) throws TException {
        try {
            HrThirdPartyAccountRecord record = new HrThirdPartyAccountRecord();
            copyToRecord(account, record);
            record.setId(accountId);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setUpdateTime(now);
            record.setSyncTime(now);
            int count = hrThirdPartyAccountDao.updateThirdPartyAccount(record);
            if (count == 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("remain_num", account.getRemainNum());
            map.put("remain_profile_num", account.getRemainProfileNum());
            DateTime dt = new DateTime(now.getTime());
            map.put("sync_time", dt.toString("yyyy-MM-dd HH:mm:ss"));


            return ResponseUtils.success(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        } finally {

        }
    }

    @Override
    public Response getAccounts(CommonQuery query) throws TException {
        try {
            List<UserHrAccountRecord> records = hraccountDao.getResources(query);
            List<UserHrAccount> datas = new ArrayList<>();
            if (records != null && records.size() > 0) {
                records.forEach(record -> {
                    UserHrAccount data = BeanUtils.DBToStruct(UserHrAccount.class, record);
                    datas.add(data);
                });
                return ResponseUtils.success(datas);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        } finally {
        }
    }
}
