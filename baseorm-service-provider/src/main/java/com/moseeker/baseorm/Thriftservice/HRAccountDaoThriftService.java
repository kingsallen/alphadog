package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.HRAccountDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.UserHrAccountDao.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import java.text.ParseException;

/**
 * 提供hr帐号表的单表操作
 * <p>Company: MoSeeker</P>
 * <p>date: Nov 8, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @author wjf
 */
@Service
public class HRAccountDaoThriftService extends JOOQBaseServiceImpl<UserHrAccount, UserHrAccountRecord> implements Iface {

    private Logger logger = LoggerFactory.getLogger(ChannelType.class);

    @Autowired
    private HRAccountDao hraccountDao;

    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

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


    public HRAccountDao getHraccountDao() {
        return hraccountDao;
    }

    public void setHraccountDao(HRAccountDao hraccountDao) {
        this.hraccountDao = hraccountDao;
    }

    @Override
    public Response getThirdPartyAccount(CommonQuery query) throws TException {
        try {
            HrThirdPartyAccountRecord record = hrThirdPartyAccountDao.getResource(query);
            if (record != null) {
                return ResponseUtils.success(record.intoMap());
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        } finally {
            //do nothing
        }
    }

    @Override
    public Response createThirdPartyAccount(BindAccountStruct account) throws TException {

        try {
            HrThirdPartyAccountRecord record = new HrThirdPartyAccountRecord();
            record.setBinding((short) account.getBinding());
            record.setChannel((short) account.getChannel());
            record.setCompanyId(UInteger.valueOf(account.getCompany_id()));
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCreateTime(now);
            record.setMembername(account.getMember_name());
            record.setPassword(account.getPassword());
            record.setRemainNum(UInteger.valueOf(account.getRemainNum()));
            record.setSyncTime(now);
            record.setUsername(account.getUsername());
            hrThirdPartyAccountDao.postResource(record);
            HashMap<String, Object> map = new HashMap<>();
            map.put("remain_num", account.getRemainNum());
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
    public Response upsertThirdPartyAccount(BindAccountStruct account) throws TException {
        try {
            HrThirdPartyAccountRecord record = new HrThirdPartyAccountRecord();
            record.setBinding((short) account.getBinding());
            record.setChannel((short) account.getChannel());
            record.setCompanyId(UInteger.valueOf(account.getCompany_id()));
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCreateTime(now);
            record.setMembername(account.getMember_name());
            record.setPassword(account.getPassword());
            record.setRemainNum(UInteger.valueOf(account.getRemainNum()));
            record.setSyncTime(now);
            record.setBinding((short) 1);
            record.setUsername(account.getUsername());
            int count = hrThirdPartyAccountDao.upsertResource(record);
            if (count == 0) {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("remain_num", account.getRemainNum());
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
            if (records != null && records.size() > 0) {
                List<UserHrAccount> structs = DBsToStructs(records);
                return ResponseUtils.success(structs);
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

    @Override
    protected void initDao() {
        this.dao = hraccountDao;
    }

    @Override
    protected UserHrAccountRecord structToDB(UserHrAccount userHrAccount) throws ParseException {
        return (UserHrAccountRecord) BeanUtils.structToDB(userHrAccount, UserHrAccountRecord.class);
    }

    @Override
    protected UserHrAccount DBToStruct(UserHrAccountRecord userHrAccountRecord) {
        return (UserHrAccount) BeanUtils.DBToStruct(UserHrAccount.class, userHrAccountRecord);

    }
}