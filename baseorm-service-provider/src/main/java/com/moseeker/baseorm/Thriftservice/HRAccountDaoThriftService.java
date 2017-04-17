package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.baseorm.dao.userdb.UserHRAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.UserHrAccountDao.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserHrAccount;

import org.apache.thrift.TException;
import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            record.setCompanyId((int)(account.getCompany_id()));
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCreateTime(now);
            record.setMembername(account.getMember_name());
            record.setPassword(account.getPassword());
            record.setRemainNum((int)(account.getRemainNum()));
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
            record.setCompanyId((int)(account.getCompany_id()));
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setCreateTime(now);
            record.setMembername(account.getMember_name());
            record.setPassword(account.getPassword());
            record.setRemainNum((int)(account.getRemainNum()));
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
            List<UserHrAccount> datas = new ArrayList<>();
            if (records != null && records.size() > 0) {
                records.forEach(record -> {
                    UserHrAccount data = (UserHrAccount) BeanUtils.DBToStruct(UserHrAccount.class, record);
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
